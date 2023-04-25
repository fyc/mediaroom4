package com.sunmnet.mediaroom.tabsp.version1;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sunmnet.mediaroom.common.BaseActivity;
import com.sunmnet.mediaroom.common.bean.course.CourseSchedule;
import com.sunmnet.mediaroom.common.events.Event;
import com.sunmnet.mediaroom.common.events.EventType;
import com.sunmnet.mediaroom.common.events.TimeEvent;
import com.sunmnet.mediaroom.common.service.BellService;
import com.sunmnet.mediaroom.common.tools.CommonUtil;
import com.sunmnet.mediaroom.common.tools.CourseHelper;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.SharePrefUtil;
import com.sunmnet.mediaroom.common.tools.ToastUtil;
import com.sunmnet.mediaroom.device.bean.DeviceScene;
import com.sunmnet.mediaroom.device.bean.EnvirmentDevice;
import com.sunmnet.mediaroom.device.bean.OtherSetting;
import com.sunmnet.mediaroom.device.controll.Controller;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.TabspApplication;
import com.sunmnet.mediaroom.tabsp.bean.DialogInfo;
import com.sunmnet.mediaroom.tabsp.controll.version1.MainPageSceneHolder;
import com.sunmnet.mediaroom.tabsp.databinding.MainpageBinding;
import com.sunmnet.mediaroom.tabsp.databinding.SceneItem;
import com.sunmnet.mediaroom.tabsp.eventbus.events.MediaVgeSourceSetEvent;
import com.sunmnet.mediaroom.tabsp.interfaces.DialogListener;
import com.sunmnet.mediaroom.tabsp.ui.adapter.AbstractHolder;
import com.sunmnet.mediaroom.tabsp.ui.adapter.HolderAdapter;
import com.sunmnet.mediaroom.tabsp.ui.adapter.IHolder;
import com.sunmnet.mediaroom.tabsp.ui.dispatch.fragmentDispatcher.AbstractFragmentDispatcher;
import com.sunmnet.mediaroom.util.JsonUtils;
import com.sunmnet.mediaroom.util.bean.course.CourseDto;
import com.sunmnet.mediaroom.util.bean.course.LessonDto;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

import static com.sunmnet.mediaroom.tabsp.controll.version1.Version1Dispatcher.CONTROLLER_VERSION1_MAINPAGE;
/**
 * 首页、快捷方式、当前课表、环境数据、音量等
 * */
@Route(path = CONTROLLER_VERSION1_MAINPAGE)
public class MainPageDispatch extends AbstractFragmentDispatcher implements IHolder.HolderFactory {
    BaseActivity baseActivity;
    MainpageBinding binding;
    @BindView(R.id.volumn_value)
    TextView volumnTextView;
    @BindView(R.id.volumn_minus_btn)
    ImageView volumnMinusBtn;
    @BindView(R.id.volumn_plus_btn)
    ImageView volumnPlusBtn;
    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.classon_onekey)
    LinearLayout classOnOneKey;
    @BindView(R.id.classover_onekey)
    LinearLayout classOverOneKey;
    @BindView(R.id.iv_class_on_logo)
    ImageView classOnOneKeyLogo;
    @BindView(R.id.iv_class_over_logo)
    ImageView classOverOneKeyLogo;
    @BindView(R.id.tv_class_on_text)
    TextView classOnText;
    @BindView(R.id.tv_class_over_text)
    TextView classOverText;

    int selected, unselected;

    // volum音量设置键超时处理
    private CountDownTimer volumCountDownTimer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            if (volumnMinusBtn != null && volumnPlusBtn != null && progressBar != null) {
                volumnMinusBtn.setEnabled(true);
                volumnPlusBtn.setEnabled(true);
                progressBar.setVisibility(View.GONE);
            }
        }
    };

    // 一键上下课使能标志
    private boolean enableClass = true;
    // 一键上下课操作间隔时间4秒
    private CountDownTimer classCountDownTimer = new CountDownTimer(4000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            // 使能一键上下课
            enableClass = true;
        }
    };

    @Override
    public <E> void dispatch(View view, E e) {
        if (e instanceof BaseActivity) {
            this.baseActivity = (BaseActivity) e;
            selected = CommonUtil.getColorByAttribute(baseActivity, R.attr.common_text_color);
            unselected = CommonUtil.getColorByAttribute(baseActivity, R.attr.device_black_text_color);
        }
        if (view != null) {
            dispatch(view);
        }
    }

    CourseSchedule courseSchedule;

    private void optVolumn(int value) {
        int cacheValue = 0;
        try {
            cacheValue = Integer.parseInt(volumnTextView.getText().toString());
        } catch (Exception e) {
            RunningLog.error(e);
        }
        cacheValue += value;
        if (cacheValue > 100) cacheValue = 100;
        if (cacheValue < 0) cacheValue = 0;
        String volumn = cacheValue + "";
        Controller.getInstance().setVolumn(volumn);
    }

    AbstractHolder<SceneItem, DeviceScene> prevSelected;

    @OnItemClick(R.id.controll_scene_grid)
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        AbstractHolder<SceneItem, DeviceScene> holder = (AbstractHolder) view.getTag();
        if (holder != prevSelected) {
            if (prevSelected != null) {
                prevSelected.setSelected(false);
            }
            holder.setSelected(true);
            prevSelected = holder;
            Controller.getInstance().setScene(holder.getProperty());
        }
    }

    @OnClick({R.id.volumn_plus_btn, R.id.volumn_minus_btn, R.id.classon_onekey, R.id.classover_onekey})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.volumn_plus_btn:
                volumCountDownTimer.start();
                volumnMinusBtn.setEnabled(false);
                volumnPlusBtn.setEnabled(false);
                progressBar.setVisibility(View.VISIBLE);
                optVolumn(5);
                break;
            case R.id.volumn_minus_btn:
                volumCountDownTimer.start();
                volumnMinusBtn.setEnabled(false);
                volumnPlusBtn.setEnabled(false);
                progressBar.setVisibility(View.VISIBLE);
                optVolumn(-5);
                break;
            case R.id.classon_onekey:
                if (enableClass) {
                    enableClass = false;
                    classCountDownTimer.start();
                    ToastUtil.show(baseActivity, "执行一键上课操作");
                    classOnOneKey.setBackgroundResource(R.drawable.btn_forward_effect);
                    classOnOneKeyLogo.setImageResource(R.drawable.drawable_one_key_on_class_select);
                    classOnText.setTextColor(baseActivity.getResources().getColorStateList(R.color.color_one_key_class_text_select_color));
                    classOverOneKey.setBackgroundResource(R.drawable.btn_reverse_effective_two);
                    classOverOneKeyLogo.setImageResource(R.drawable.drawable_one_key_over_class_unselect);
                    classOverText.setTextColor(baseActivity.getResources().getColorStateList(R.color.color_one_key_class_text_unselect_color));
                    // 切换中控信号源为本地电脑
                    EventBus.getDefault().postSticky(new MediaVgeSourceSetEvent(2));
                    Controller.getInstance().classOn();
                } else {
                    ToastUtil.show(baseActivity, "操作执行中，请稍等。");
                }
                break;
            case R.id.classover_onekey:
                if (enableClass) {
                    enableClass = false;
                    classCountDownTimer.start();
                    ToastUtil.show(baseActivity, "执行一键下课操作");
                    classOverOneKey.setBackgroundResource(R.drawable.btn_forward_effect);
                    classOverOneKeyLogo.setImageResource(R.drawable.drawable_one_key_over_class_select);
                    classOverText.setTextColor(baseActivity.getResources().getColorStateList(R.color.color_one_key_class_text_select_color));
                    classOnOneKey.setBackgroundResource(R.drawable.btn_reverse_effective_two);
                    classOnOneKeyLogo.setImageResource(R.drawable.drawable_one_key_on_class_unselect);
                    classOnText.setTextColor(baseActivity.getResources().getColorStateList(R.color.color_one_key_class_text_unselect_color));
                    // 切换中控信号源为本地电脑
                    EventBus.getDefault().postSticky(new MediaVgeSourceSetEvent(2));
                    //如果开启了防误触提示
                    if (TabspApplication.getInstance().getConfig().isAntiTouchMode()) {
                        //如果开启了设备评价
                        DialogInfo dialogInfo = new DialogInfo(DialogInfo.DialogType.CONFIRM_CLASSOVER, new DialogListener() {
                            @Override
                            public <T> void onConfirmed(T t) {
                                Controller.getInstance().classOver();
                            }

                            @Override
                            public void onCancel() {

                            }
                        });
                        Event<DialogInfo, EventType> classOver = new Event<>();
                        classOver.setMessage(dialogInfo);
                        classOver.setEventType(EventType.DIALOG_EVENT);
                        EventBus.getDefault().post(classOver);
                    } else {
                        Controller.getInstance().classOver();
                    }
                } else {
                    ToastUtil.show(baseActivity, "操作执行中，请稍等。");
                }
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onClassTimeChange(TimeEvent timeEvent) {
        switch (timeEvent.getEventType()) {
            case CLASS_OVER:
            case CLASS_ON:
                loadCourse();
                break;
        }
    }

    public void loadCourse(CourseSchedule courseSchedule) {
        try {
            if (binding != null) {
                if (courseSchedule.getCourseName() == null) courseSchedule.setCourseName("休息中");
                if (courseSchedule.getClassNo() != null) {
                    if ("-1".equals(courseSchedule.getClassNo())) {
                        courseSchedule.setClassNo("");
                        courseSchedule.setCourseTime(" ");
                    } else {
                        courseSchedule.setClassNo(CourseSchedule.formateClassNo(courseSchedule.getClassNo()));
                    }
                }

                RunningLog.run("设置课程：" + JsonUtils.objectToJson(courseSchedule));
                binding.setCourse(courseSchedule);
            } else RunningLog.run("当前没有课程");
        } catch (Exception e) {
            RunningLog.warn(e);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED, sticky = true)
    public void loadOtherSetting(OtherSetting setting) {
        if (this.volumnTextView != null && setting.getVolumn() != null && setting.getVgaMode() != null && setting.getVgaMode().equals("1")) {
            this.volumnTextView.setText(setting.getVolumn());
            this.volumCountDownTimer.cancel();
            this.volumnMinusBtn.setEnabled(true);
            this.volumnPlusBtn.setEnabled(true);
            this.progressBar.setVisibility(View.GONE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEnvirmentChanged(EnvirmentDevice envirmentDevice) {
        binding.setEnvirment(envirmentDevice);
    }

    private void loadCourse() {
        CourseDto courseDto = CourseHelper.getDefault().getCourseDto();
        if (courseDto != null) {
            List<LessonDto> lessonsDtoList = courseDto.getLessons();
            if (lessonsDtoList == null || lessonsDtoList.isEmpty()) {
                // 没有课程数据
                courseSchedule = new CourseSchedule();
                courseSchedule.setCourseName("暂无课表信息");
                courseSchedule.setClassNo("-1");
            } else {
                courseSchedule = CourseHelper.getDefault().getCourseSchedule(new Date(), 0, "0");
                if (courseSchedule == null) {
                    courseSchedule = new CourseSchedule();
                    courseSchedule.setCourseName("休息中");
                    courseSchedule.setClassNo("-1");
                }
            }
        } else {
            // 没有课程数据
            courseSchedule = new CourseSchedule();
            courseSchedule.setCourseName("暂无课表信息");
            courseSchedule.setClassNo("-1");
        }
        loadCourse(courseSchedule);
    }

    /**
     * 加载功放音量缓存值
     */
    private void loadPowerAmplifierValue() {
        String paValue = SharePrefUtil.getString(this.baseActivity.getApplicationContext(), TabspApplication.DB_NAME, TabspApplication.LAST_PA_VOLUME_VALUE);
        if (paValue != null && this.volumnTextView != null) {
            this.volumnTextView.setText(paValue);
        }
    }

    @Override
    public void dispatch(View view) {
        ButterKnife.bind(this, view);
        binding = DataBindingUtil.bind(view);
        CommonUtil.loadBackgroundImage(TabspApplication.getInstance(), R.drawable.black_board, binding.courseLayout.courseholder);
        List<DeviceScene> caches = Controller.getInstance().getScenes();
        HolderAdapter adapter = new HolderAdapter(R.layout.tabsp_version1_scene_item_layout, this);
        adapter.setData(caches);
        binding.setConstrollSceneAdapter(adapter);
        // 开启更新课程信息定时器服务
        CourseDto courseDto = CourseHelper.getDefault().getCourseDto();
        if (courseDto != null) {
            baseActivity.startService(new Intent(baseActivity, BellService.class));
        }
    }

    @Override
    public void release() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        volumCountDownTimer.cancel();
        classCountDownTimer.cancel();
        enableClass = true;
    }

    @Override
    public void onReady() {
        // 加载课程信息，防止熄屏后没有收到更新课程信息事件，而导致课程信息显示没有更细的问题
        loadCourse();
        // 加载功放音量缓存值
        loadPowerAmplifierValue();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public int getLayout() {
        return R.layout.tabsp_version1_mainpage_layout;
    }

    @Override
    public IHolder newHolder() {
        return new MainPageSceneHolder(selected, unselected);
    }
}
