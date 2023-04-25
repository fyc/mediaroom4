package com.sunmnet.mediaroom.tabsp.ui.dispatch;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.sunmnet.mediaroom.common.BaseActivity;
import com.sunmnet.mediaroom.common.events.Event;
import com.sunmnet.mediaroom.common.events.EventType;
import com.sunmnet.mediaroom.common.tools.CommonUtil;
import com.sunmnet.mediaroom.common.tools.GsonUtil;
import com.sunmnet.mediaroom.common.tools.JacksonUtil;
import com.sunmnet.mediaroom.common.tools.OkHttpUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.SharePrefUtil;
import com.sunmnet.mediaroom.common.tools.ToastUtil;
import com.sunmnet.mediaroom.device.bean.LoginEvent;
import com.sunmnet.mediaroom.device.controll.Controller;
import com.sunmnet.mediaroom.devicebean.tag.CommuTag;
import com.sunmnet.mediaroom.tabsp.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

import com.sunmnet.mediaroom.tabsp.TabspApplication;
import com.sunmnet.mediaroom.tabsp.bean.DialogInfo;
import com.sunmnet.mediaroom.tabsp.bean.LoginQrCodeOperateResult;
import com.sunmnet.mediaroom.tabsp.bean.QrLoginLinkResult;
import com.sunmnet.mediaroom.tabsp.bean.TabspConfig;
import com.sunmnet.mediaroom.tabsp.common.Constants;
import com.sunmnet.mediaroom.tabsp.controll.AbstractActivityUiDispatcher;
import com.sunmnet.mediaroom.tabsp.controll.service.Dispatcher;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 首页登录  等待刷卡界面
 */

@Route(path = Constants.ROUTERPATH_LOGIN_WAITING)
public class WaitForLoginDispatcher extends AbstractActivityUiDispatcher {
    @BindView(R.id.login_container)
    View contentView;
    @BindView(R.id.qrcode)
    ImageView qrcode;
    @BindView(R.id.switchLogintype)
    View switchLoginType;
    @BindView(R.id.tabsp_logo)
    ImageView logo;
    @BindView(R.id.spinning)
    View spinning;
    BaseActivity activity;
    @BindView(R.id.qrcode_container)
    View qrcodeContainer;
    @BindView(R.id.allowcard)
    View allowcard;
    @BindView(R.id.tips)
    TextView qrCodeTips;

    // 获取二维码参数线程
    private Thread requestQrCodeThread;
    // 登录二维码的uuid
    private volatile String loginQrCodeUuid = "";

    private Runnable requestQrCodeRunnable = () -> {
        try {
            String configStr = SharePrefUtil.getString(WaitForLoginDispatcher.this.activity, TabspApplication.DB_NAME, TabspApplication.DB_CONFIG_KEY);
            if (configStr != null && !configStr.isEmpty()) {
                // 修改提示
                qrCodeTips.setText(R.string.scan_qr_code_tips);
                qrCodeTips.setVisibility(View.INVISIBLE);

                TabspConfig tabspConfig = JacksonUtil.jsonStrToBean(configStr, TabspConfig.class);
                if (tabspConfig != null) {
                    // 有deviceCode和classroomCode信息
                    if (!Thread.currentThread().isInterrupted()) {
                        Map<String, String> params = new LinkedHashMap<>();
                        params.put("deviceCode", tabspConfig.getRegisterInfo().getDeviceCode());
                        params.put("classroomCode", tabspConfig.getRegisterInfo().getClassroomCode());
                        params.put("target", "3");

                        Response response = OkHttpUtil.get("http://" + tabspConfig.getRegisterInfo().getPlatformIp() + ":"+
                                tabspConfig.getRegisterInfo().getPlatformPort() +"/sys-svr/api/getQrLoginLink",
                                params, (Map<String, String>) null);
                        if (response.body() != null) {
                            String responseStr = response.body().string();
                            RunningLog.info("获取登录码参数请求返回:" + responseStr);
                            QrLoginLinkResult qrLoginLinkResult =
                                    JacksonUtil.jsonStrToBean(responseStr, QrLoginLinkResult.class);
                            String message = qrLoginLinkResult.getObj();
                            RunningLog.info("二维码登录链接展示" + message);
                            byte[] image = CommonUtil.createQRCode(message, 142, 142);
                            if (image != null && image.length > 0) {
                                EventBus.getDefault().post(image);
                            }
                            // 设置UUID
                            String[] split = message.split("\\?");
                            RunningLog.run("郑州，split:" + split.length);
                            if (split.length > 1) {
                                String paramsString = split[1];
                                String[] paramsSplit = paramsString.split("&");
                                for (String s : paramsSplit) {
                                    if (s.startsWith("uuid")) {
                                        loginQrCodeUuid = s.replace("uuid=", "");
                                        RunningLog.run("郑州，登录UUID:" + loginQrCodeUuid);
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                // 无deviceCode和classroomCode信息
                qrCodeTips.setText(R.string.register_panel_first);
                qrCodeTips.setVisibility(View.VISIBLE);
            }
        } catch (IOException e) {
            e.printStackTrace();
            RunningLog.info("请求登录二维码参数线程停止");
        }
    };

    @Override
    public void dispatch(BaseActivity activity) {
        super.dispatch(activity);
        activity.setContentView(getLayout());
        ButterKnife.bind(this, activity);
        TabspConfig config = TabspApplication.getInstance().getConfig();
        //加载背景图
        CommonUtil.loadBackgroundImageWithoutCache(activity, config.getBaseLoginBackground(), R.drawable.activity_login_enter_background, contentView);
        //加载logo
        CommonUtil.loadResourceIntoImageWithoutCache(activity, config.getLoginLogo(), R.drawable.tabsp_logo, logo);
        //加载二维码
        this.activity = activity;
        if (config.isAccount()) {
            switchLoginType.setVisibility(View.VISIBLE);
        }
        if (config.isQrcode() && config.isSwipCard()) {
            spinning.setVisibility(View.VISIBLE);
        }
        if (config.isQrcode()) {
            qrcodeContainer.setVisibility(View.VISIBLE);
        }
        if (config.isSwipCard()) {
            allowcard.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getLayout() {
        return R.layout.tabsp_login_default;
    }

    @OnClick({R.id.switchLogintype, R.id.languageswitcher, R.id.tabsp_logo, R.id.tips})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.switchLogintype:
                ARouter.getInstance().build(Constants.ROUTERPATH_ACTIVITY).withString(Dispatcher.PAGE_KEY, Dispatcher.PAGE_REDIRECT_LOGIN_ENTER).navigation();
                break;
            case R.id.languageswitcher:
                Event event = new Event();
                event.setEventType(EventType.CHANGE_LANGUAGE);
                EventBus.getDefault().post(event);
                break;
            case R.id.tabsp_logo:
                openEditMode();
                break;
            case R.id.tips:
                // 隐藏提示内容, 显示二维码
                qrCodeTips.setVisibility(View.INVISIBLE);
                qrcode.setVisibility(View.VISIBLE);
                // 刷新二维码
                startRefreshQrCodeThread();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loadQRCode(byte[] image) {
        Glide.with(TabspApplication.getInstance()).load(image).centerCrop().into(qrcode);
        EventBus.getDefault().removeStickyEvent(image);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginQrCodeResult(LoginQrCodeOperateResult result) {
        RunningLog.info("郑州，收到操作二维码结果:" + result.getSocketMessage().toString());
        if (result.getSocketMessage() == null) return;
        if (result.getSocketMessage().getUuid().equals(loginQrCodeUuid)) {
            RunningLog.info("收到操作二维码结果， uuid相符");
            // 执行登录操作
            LoginQrCodeOperateResult.OperateResult operateResult = result.getOperateResult();
            LinkedHashMap<String, String> json  = new LinkedHashMap<>();
            json.put("type", "2");
            json.put("accountId", operateResult.getAccountDto().getCode());
            Controller.getInstance().sendMsg(CommuTag.TABSP_EVENT, GsonUtil.objToJsonStr(json));
            RunningLog.run("郑州，已执行扫码解锁面板操作");
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onLoginResult(LoginEvent event) {
        switch (event.getEventType()) {
            case ON_CLASS:
                RunningLog.run("WaitingForLoginDispatcher." + event.getEventType());
                EventBus.getDefault().postSticky(event.getMessage());
                ARouter.getInstance().build(Constants.ROUTERPATH_ACTIVITY).withString(Dispatcher.PAGE_KEY, Dispatcher.PAGE_REDIRECT_CONTENT).navigation();
                this.activity.finish();
                release();
                break;
            case ON_LOGIN:
                RunningLog.run("WaitingForLoginDispatcher." + event.getEventType());
                ARouter.getInstance().build(Constants.ROUTERPATH_ACTIVITY).withString(Dispatcher.PAGE_KEY, Dispatcher.PAGE_REDIRECT_CONTENT).navigation();
                this.activity.finish();
                release();
                break;
            case ON_CLASSOVER:
                if (TabspApplication.getInstance().getConfig().getClassOverCountdown() != 0) {
                    DialogInfo dialogInfo = new DialogInfo(DialogInfo.DialogType.DEVICE_CLOSING,
                            null, TabspApplication.getInstance().getConfig().getClassOverCountdown());
                    Event<DialogInfo, EventType> dialogEvent = new Event<>();
                    dialogEvent.setMessage(dialogInfo);
                    dialogEvent.setEventType(EventType.DIALOG_EVENT);
                    EventBus.getDefault().post(dialogEvent);
                    EventBus.getDefault().removeStickyEvent(event);
                }
                break;
            case ON_LOGIN_FAILURE:
                ToastUtil.show(WaitForLoginDispatcher.this.activity, event.getMessage().getMessgae());
                break;
        }
    }

    @Override
    public void release() {
        TabspConfig config = TabspApplication.getInstance().getConfig();
        if (config.isQrcode()) {
            // 停止刷新二维码线程
            stopRefreshQrCodeThread();
        }
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onReady() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        TabspConfig config = TabspApplication.getInstance().getConfig();
        if (config.isQrcode()) {
            // 配置了扫二维码登录
            // 设置空间的显示与隐藏
            qrcode.setVisibility(View.VISIBLE);
            qrCodeTips.setVisibility(View.INVISIBLE);

            /**
             * 加载二维码
             * */
            // 调用接口获取二维码，定时刷新二维码，监听扫码登录状态
            startRefreshQrCodeThread();
        }
    }

    /**
     * 启动刷新二维码线程
     */
    private void startRefreshQrCodeThread() {
        if (requestQrCodeThread != null && requestQrCodeThread.isAlive()) {
            requestQrCodeThread.interrupt();
            try {
                requestQrCodeThread.join();
                requestQrCodeThread = null;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        requestQrCodeThread = new Thread(requestQrCodeRunnable);
        requestQrCodeThread.setDaemon(true);
        requestQrCodeThread.start();
    }

    /**
     * 停止刷新二维码线程
     */
    private void stopRefreshQrCodeThread() {
        if (requestQrCodeThread != null && requestQrCodeThread.isAlive()) {
            requestQrCodeThread.interrupt();
            try {
                requestQrCodeThread.join();
                requestQrCodeThread = null;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
