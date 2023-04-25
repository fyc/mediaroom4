package com.sunmnet.mediaroom.brand;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.permissionx.guolindev.PermissionX;
import com.sunmnet.mediaroom.brand.api.ApiManager;
import com.sunmnet.mediaroom.brand.bean.ProgramLayoutBean;
import com.sunmnet.mediaroom.brand.bean.UpgradeInfo;
import com.sunmnet.mediaroom.brand.bean.config.CustomConfig;
import com.sunmnet.mediaroom.brand.bean.event.ClickEvent;
import com.sunmnet.mediaroom.brand.bean.event.PlayEvent;
import com.sunmnet.mediaroom.brand.bean.event.RefreshConfigEvent;
import com.sunmnet.mediaroom.brand.bean.event.RefreshControlEvent;
import com.sunmnet.mediaroom.brand.bean.event.SwipeCardEvent;
import com.sunmnet.mediaroom.brand.bean.play.ProgramInfo;
import com.sunmnet.mediaroom.brand.bean.request.UpgradeResultRequest;
import com.sunmnet.mediaroom.brand.bean.response.LongResponse;
import com.sunmnet.mediaroom.brand.bean.response.ObjectResponse;
import com.sunmnet.mediaroom.brand.common.base.DeviceApp;
import com.sunmnet.mediaroom.brand.data.file.ProgramResource;
import com.sunmnet.mediaroom.brand.data.sharepref.BrandConfig;
import com.sunmnet.mediaroom.brand.data.sharepref.UpgradePref;
import com.sunmnet.mediaroom.brand.fragment.NotificationFragment;
import com.sunmnet.mediaroom.brand.fragment.dialog.FaceAttendanceDialogFragment;
import com.sunmnet.mediaroom.brand.fragment.dialog.QueryCourseDialogFragment;
import com.sunmnet.mediaroom.brand.fragment.dialog.QueryExamRoomDialogFragment;
import com.sunmnet.mediaroom.brand.fragment.dialog.SwipeCardAttendanceDialog;
import com.sunmnet.mediaroom.brand.impl.device.BrandDevice;
import com.sunmnet.mediaroom.brand.impl.play.AbstractPlayable;
import com.sunmnet.mediaroom.brand.impl.play.Notification;
import com.sunmnet.mediaroom.brand.impl.play.Program;
import com.sunmnet.mediaroom.brand.impl.play.TemplateProgram;
import com.sunmnet.mediaroom.brand.impl.swipeCard.SwipeCardContext;
import com.sunmnet.mediaroom.brand.interfaces.play.IPlayable;
import com.sunmnet.mediaroom.brand.rx.SingleTaskObserver;
import com.sunmnet.mediaroom.brand.utils.DisplayUtil;
import com.sunmnet.mediaroom.brand.utils.FileResourceUtil;
import com.sunmnet.mediaroom.brand.utils.SyncTimeHelper;
import com.sunmnet.mediaroom.brand.utils.TimeSwitchHelper;
import com.sunmnet.mediaroom.common.enums.TimeEventType;
import com.sunmnet.mediaroom.common.events.TimeEvent;
import com.sunmnet.mediaroom.common.tools.HexUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ToastUtil;
import com.sunmnet.mediaroom.device.controll.Controller;
import com.sunmnet.mediaroom.devicebean.tag.CommuTag;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

public class MainActivity extends AppCompatActivity {
    // public static MainActivity topActivity;
    private ArrayList<Map<String, Object>> dataList = new ArrayList<>();
    private ArrayList<Integer> carousels = new ArrayList<>();//播放时间，单位：秒

    private int position = 0;

    private ViewPager viewPager;
    private MainFragmentAdapter fragmentAdapter;

    private AbstractPlayable playingProgram;
    private Notification playingNotification;
    private View welcomeView;

    private static final int HANDLER_START_TIMER = 0x01;
    private static final String HANDLER_START_TIMER_OBJ = "start_timer";

    // 要申请的权限
    private static final List<String> permissionList = new ArrayList<>();

    static {
        permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        permissionList.add(Manifest.permission.READ_PHONE_STATE);
        permissionList.add(Manifest.permission.CAMERA);
    }

    final Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HANDLER_START_TIMER:
                    if (isFinishing())
                        return;
                    position++;
                    showProgramFragment();
                    startTimer();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        RunningLog.run("MainActivity is creating");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_program);
        // 动态申请权限
        PermissionX.init(this).permissions(
                permissionList
        ).onExplainRequestReason((scope, deniedList) -> {
                String message = "需要您同意以下权限软件才能正常使用";
                scope.showRequestReasonDialog(deniedList, message, "确定", "取消");
            }
        ).request((allGranted, grantedList, deniedList) -> {
            if (allGranted) {
                welcomeView = findViewById(R.id.welcome);
                refreshConfig(null);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                viewPager = (ViewPager) findViewById(R.id.viewpager);
                showWelcome();

                //延迟加载
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!isFinishing()) {
                            //延迟加载节目
                            RunningLog.run("界面启动完成，开始初始化节目");
//                    PlayableHolder.getHolder().addPlayableListener(MainActivity.this);
                            if (!EventBus.getDefault().isRegistered(MainActivity.this)) {
                                EventBus.getDefault().register(MainActivity.this);
                            }
                            PlayableManager.getInstance().reload();
                        }
                    }
                }, 2000);

                syncTime();
                checkUpgradeResponse();

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!isFinishing()) {
                            RunningLog.run("界面启动完成，设置定时开关机任务");
                            TimeSwitchHelper helper = new TimeSwitchHelper(MainActivity.this);
                            helper.setTimeSwitchConfig();
                        }
                    }
                }, 30000);
            } else {
                Toast.makeText(MainActivity.this, "您拒绝了如下权限：" + deniedList, Toast.LENGTH_SHORT).show();
            }
        });
        // 设置刷卡功能为默认状态
        SwipeCardContext.getInstance().setSwipeState(SwipeCardContext.NORMAL_STATE);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            try {
                Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                String cardId = HexUtil.bytesToHex(tag.getId()).toUpperCase();
                RunningLog.run("读卡成功,卡片id：" + cardId);
                ToastUtil.show(this,"刷卡成功");
                Controller.getInstance().sendMsg(CommuTag.BRAND_UNLOCK_TABSP, cardId);
                // 发送刷卡事件
                EventBus.getDefault().post(new SwipeCardEvent(cardId));
            } catch (Exception e) {
                RunningLog.error(e);
            }
        }
    }

    @Subscribe
    public void onSwipeCard(SwipeCardEvent cardEvent) {
        ToastUtil.show(this, "刷卡成功");
        RunningLog.run("mainActivity收到刷卡信息");
//        Controller.getInstance().sendMsg(CommuTag.DEVICE_SP_CLOCKON, cardEvent.getCardId());
        // 打开门禁
        openDoor(cardEvent.getCardId());
    }

    @Override
    protected void onPause() {
        RunningLog.run("MainActivity is pausing");
        super.onPause();
        BrandDevice.getInstance().unregisterSwipeCard(this);
    }

    @Override
    protected void onResume() {
        RunningLog.run("MainActivity is resuming");
        super.onResume();
        //海康设备隐藏系统状态条
        Intent intent = new Intent("com.outform.hidebar");
        sendBroadcast(intent);
        DisplayUtil.hideNavigationBar(getWindow());
        BrandDevice.getInstance().registerSwipeCard(this);
    }

    private void startTimer() {
        stopTimer();
        position = position % dataList.size();
        long delayTime = carousels.get(position) * 1000;
        if (delayTime <= 0)
            delayTime = 10000;
        Message message = Message.obtain();
        message.what = HANDLER_START_TIMER;
        message.obj = HANDLER_START_TIMER_OBJ;
        mHandler.sendMessageDelayed(message, delayTime);
    }

    private void stopTimer() {
        mHandler.removeCallbacksAndMessages(HANDLER_START_TIMER_OBJ);
    }

    private void cleanProgramFragments() {
        dataList.clear();
        carousels.clear();
        position = 0;
        viewPager.setAdapter(null);
    }

    private void showProgramFragment() {
        if (dataList.size() == 0)
            return;
        position = position % dataList.size();
        viewPager.setCurrentItem(position, false);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        //super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void finish() {
//        PlayableHolder.getHolder().removePlayableListener(this);
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.finish();
    }

    @Override
    public void onBackPressed() {
        //手动退出界面时显示海康设备的状态条
        Intent intent = new Intent("com.outform.unhidebar");
        sendBroadcast(intent);
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        RunningLog.run("MainActivity is destroying");
//        PlayableHolder.getHolder().removePlayableListener(this);
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
        stopTimer();
    }

    public void onPlayStart(IPlayable playable) {
        if (playable == null || isFinishing())
            return;
        if (playable instanceof Program) {
            RunningLog.run("正在开始播放节目");
            Program program = (Program) playable;
            stopTimer();
            cleanProgramFragments();
            Map<String, ProgramResource> array = program.getProgramResources();
            for (ProgramInfo programInfo : program.getRule().getProgram()) {
                ProgramLayoutBean layoutBean = array.get(programInfo.getProgramId()).getProgramLayoutBean();
                HashMap<String, Object> map = new HashMap<>();
                map.put("bean", layoutBean);
                map.put("path", FileResourceUtil.getProgramResourcePath(programInfo.getProgramId(), programInfo.getResFileMd5()));
                if (programInfo.getExamId() != null) {
                    map.put("examId", programInfo.getExamId());
                } else {
                    map.put("examId", "");
                }
                dataList.add(map);
                carousels.add(program.getProgramCarousel(programInfo.getProgramId()));
            }
            if (fragmentAdapter == null) {
                fragmentAdapter = new MainFragmentAdapter(dataList, getSupportFragmentManager());
            }
            viewPager.setAdapter(fragmentAdapter);
            showProgramFragment();
            startTimer();
            this.playingProgram = program;
        } else if (playable instanceof Notification) {
            RunningLog.run("正在开始播放通知");
            Notification notification = (Notification) playable;
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            NotificationFragment fragment = NotificationFragment.newInstance(notification.getRule());
            transaction.replace(R.id.notificationLayout, fragment, "notification");
            transaction.commitAllowingStateLoss();
            playingNotification = notification;
        } else if (playable instanceof TemplateProgram) {
            TemplateProgram templateProgram = (TemplateProgram) playable;
            stopTimer();
            cleanProgramFragments();
            TemplateFragmentAdapter templateFragmentAdapter = new TemplateFragmentAdapter(getSupportFragmentManager(), templateProgram.getRule().getTemplateType(), templateProgram.getProgramResource().getProgramResourcePath());
            viewPager.setAdapter(templateFragmentAdapter);
            showProgramFragment();
            this.playingProgram = templateProgram;
        }
        hideWelcome();
    }

    public void onPlayStop(IPlayable playable) {
        if (playable == null || isFinishing())
            return;
        if (playable instanceof Program || playable instanceof TemplateProgram) {
            if (playingProgram != null && playable.getPlayId().equals(playingProgram.getPlayId())) {
                RunningLog.run("正在停止播放节目");
                stopTimer();
                cleanProgramFragments();
                playingProgram = null;
            }
        } else if (playable instanceof Notification) {
            if (playingNotification != null && playable.getPlayId().equals(playingNotification.getPlayId())) {
                RunningLog.run("正在停止播放通知");
                Fragment fragment = getSupportFragmentManager().findFragmentByTag("notification");
                if (fragment != null) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.remove(fragment);
                    transaction.commitAllowingStateLoss();
                }
                playingNotification = null;
            }
        }

        if (playingNotification == null && playingProgram == null) {
            showWelcome();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPlayStateChange(PlayEvent playEvent) {
        switch (playEvent.getStatus()) {
            case PlayEvent.START_PLAY:
                onPlayStart(playEvent.getPlayable());
                break;
            case PlayEvent.STOP_PLAY:
                onPlayStop(playEvent.getPlayable());
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnTimeEvent(TimeEvent event) {
        RunningLog.run("接收TimeEvent: " + event.getEventType() + " , message: " + event.getMessage());
        if (event.getEventType() == TimeEventType.RESET) {
            if (PlayableManager.getInstance().isStartingProgram() || PlayableManager.getInstance().isPlayingNotification()) {
                PlayableManager.getInstance().reload();
            }
        } else {
            RunningLog.run("正在刷新节目数据");
            EventBus.getDefault().post(new RefreshControlEvent());
        }
    }

    private void showWelcome() {
        welcomeView.setVisibility(View.VISIBLE);
    }

    private void hideWelcome() {
        welcomeView.setVisibility(View.GONE);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onControlClick(ClickEvent clickEvent) {
        switch (clickEvent.getEventType()) {
            case QUERY_COURSE:
                Fragment fragment1 = getSupportFragmentManager().findFragmentByTag("queryCourse");
                if (fragment1 != null)
                    return;
                QueryCourseDialogFragment queryCourseDialogFragment = new QueryCourseDialogFragment();
                queryCourseDialogFragment.show(getSupportFragmentManager(), "queryCourse");
                break;
            case FACE_ATTEND:
                Fragment fragment2 = getSupportFragmentManager().findFragmentByTag("faceAttend");
                if (fragment2 != null)
                    return;
                FaceAttendanceDialogFragment fragmentDialog = new FaceAttendanceDialogFragment();
                fragmentDialog.show(getSupportFragmentManager(), "faceAttend");
                break;
            case QUERY_EXAM:
                Fragment fragment3 = getSupportFragmentManager().findFragmentByTag("faceAttend");
                if (fragment3 != null)
                    return;
                QueryExamRoomDialogFragment queryExamRoomDialogFragment = new QueryExamRoomDialogFragment();
                queryExamRoomDialogFragment.show(getSupportFragmentManager(), "queryExam");
                break;
            case SWIPE_CARD_ATTEND:
                Fragment fragment4 = getSupportFragmentManager().findFragmentByTag("swipeCardAttend");
                if (fragment4 != null)
                    return;
                SwipeCardAttendanceDialog fragmentDialog2 = new SwipeCardAttendanceDialog();
                fragmentDialog2.show(getSupportFragmentManager(), "swipeCardAttend");
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshConfig(RefreshConfigEvent configEvent) {
        CustomConfig config = BrandConfig.getInstance().getCustomConfig(this);
        if (config != null && !TextUtils.isEmpty(config.getHomeScreen())) {
            Glide.with(this).load(config.getHomeScreen()).into(new CustomTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    welcomeView.setBackground(resource);
                }

                @Override
                public void onLoadCleared(@Nullable Drawable placeholder) {

                }
            });
        }
    }


    private void checkUpgradeResponse() {
        if (DeviceApp.getApp().isRegistered()) {
            UpgradeInfo upgradeInfo = UpgradePref.getInstance().getUpgradeInfo(this);
            if (upgradeInfo != null && !upgradeInfo.isRespondedResult()) {
                RunningLog.run("发送升级结果给平台");
                UpgradeResultRequest request = new UpgradeResultRequest();
                request.setClassroomCode(DeviceApp.getApp().getClassroomCode());
                if (upgradeInfo.isUpgradeSuccess()) {
                    request.setResult("00");
                } else {
                    request.setResult("01");
                }
                request.setUuid(upgradeInfo.getUpgradeId());
                try {
                    PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                    request.setVersion(packageInfo.versionName);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                Observable<ObjectResponse> observable = ApiManager.getSysApi().respondUpgradeResult(request);
                observable.subscribe(new SingleTaskObserver<ObjectResponse>() {
                    @Override
                    public void onNext(ObjectResponse response) {
                        if (response == null) {
                            RunningLog.run("发送升级结果返回数据解析错误");
                            return;
                        }
                        RunningLog.run("发送升级结果:" + response.getSuccess() + "  " + response.getMsg());
                        if (response.isSuccess()) {
                            UpgradeInfo upgradeInfo1 = UpgradePref.getInstance().getUpgradeInfo(MainActivity.this);
                            upgradeInfo1.setRespondedResult(true);
                            UpgradePref.getInstance().saveUpgradeInfo(MainActivity.this, upgradeInfo1);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        RunningLog.run("发送升级结果失败: " + e.getMessage());
                    }
                });
            }
        }
    }

    private void syncTime() {
        if (DeviceApp.getApp().isRegistered()) {
            RunningLog.run("正在同步平台时间");
            ApiManager.getSysApi().getSystemTime()
                .subscribe(new SingleTaskObserver<LongResponse>() {
                    @Override
                    public void onNext(LongResponse response) {
                        if (response == null) {
                            RunningLog.debug("获取平台时间数据解析出错：null");
                            return;
                        }
                        if (!response.isSuccess() || response.getObj() == null) {
                            RunningLog.debug("获取平台时间失败，success: " + response.isSuccess() + " obj:" + response.getObj());
                            return;
                        }
                        Long timeStamp = response.getObj();
                        SyncTimeHelper helper = new SyncTimeHelper();
                        boolean updateResult = false;
                        try {
                            updateResult = helper.syncTime(timeStamp);
                        } catch (Exception e) {
                            e.printStackTrace();
                            RunningLog.error("修改系统时间失败：" + e.getMessage());
                        }
                        if (!updateResult) {
                            //refresh
                            EventBus.getDefault().post(new RefreshControlEvent());
                        } else {
                            //reload
                            EventBus.getDefault().post(new TimeEvent(TimeEventType.RESET));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        RunningLog.debug("获取平台时间失败：" + e.getMessage());
                    }
                });
        }
    }

    /**
     * 打开门禁
     * @param cardNumber 卡号
     */
    private void openDoor(String cardNumber) {
        SwipeCardContext.getInstance().openDoor(this, cardNumber);
        SwipeCardContext.getInstance().recovery();
    }
}
