package com.sunmnet.mediaroom.tabsp.controll.version3;

import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.sunmnet.mediaroom.common.events.Event;
import com.sunmnet.mediaroom.common.events.EventType;
import com.sunmnet.mediaroom.common.tools.CommonUtil;
import com.sunmnet.mediaroom.common.tools.DateUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.TabspApplication;
import com.sunmnet.mediaroom.tabsp.bean.TabspConfig;
import com.sunmnet.mediaroom.tabsp.common.Constants;
import com.sunmnet.mediaroom.tabsp.ui.dispatch.fragmentDispatcher.AbstractFragmentDispatcher;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = Constants.ROUTERPATH_CONTROLL_V3_TITLE)
public class V3TitleDispatcher extends AbstractFragmentDispatcher {
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    @BindView(R.id.classRoomName)
    TextView classRoomName;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.time)
    Chronometer time;
    @BindView(R.id.tabsp_logo)
    ImageView logo;
    int detailState = 0;
    @BindView(R.id.detail_changer)
    ImageView imageView;

    @OnClick({R.id.languageswitcher, R.id.detail_switcher})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.languageswitcher:
                Event event = new Event();
                event.setEventType(EventType.CHANGE_LANGUAGE);
                EventBus.getDefault().post(event);
                break;
            case R.id.detail_switcher:
                Version3ActivityDispatcher.DetailChanger changer = new Version3ActivityDispatcher.DetailChanger();
                if (detailState == 0) {
                    detailState = 1;
                    changer.detailState = detailState;
                    CommonUtil.loadBackgroundImage(view.getContext(), R.drawable.mediaroom4_version3_normal_back, imageView);
                } else {
                    detailState = 0;
                    changer.detailState = detailState;
                    CommonUtil.loadBackgroundImage(view.getContext(), R.drawable.mediaroom4_version3_detail_btn, imageView);
                }
                EventBus.getDefault().post(changer);
                break;
        }
    }

    private String getDateOfWeek(int week) {
        String[] weeks = new String[]{
                "周日", "周一", "周二", "周三", "周四", "周五", "周六",
                "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday, Saturday",
        };
        return weeks[week];
    }

    private String getDate(Date date) {
        String datestr = null;
        TabspConfig config = TabspApplication.getInstance().getConfig();
        calendar.setTime(date);
        if (config.getLang() == Locale.ENGLISH) {
            datestr = DateUtil.getNowDateString("yyyy-MM-dd");
            datestr += "  " + getDateOfWeek(calendar.get(Calendar.DAY_OF_WEEK) - 1 + 7);
        } else {
            datestr = DateUtil.getNowDateString("MM月dd日");
            datestr += "  " + getDateOfWeek(calendar.get(Calendar.DAY_OF_WEEK) - 1);
        }
        return datestr;
    }

    @Override
    public void invisible() {
    }

    @Override
    public void onReady() {
        time.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                Date dt = new Date();
                date.setText(getDate(dt));
                chronometer.setText(sdf.format(dt));
            }
        });
        time.start();
    }

    @Override
    public <E> void dispatch(View view, E e) {
        super.dispatch(view, e);
        ButterKnife.bind(this, view);
        CommonUtil.loadBackgroundImage(view.getContext(), R.drawable.mediaroom4_version3_detail_btn, imageView);
        classRoomName.setText(TabspApplication.getInstance().getConfig().getRegisterInfo().getClassroomName());
        CommonUtil.loadResourceIntoImage(TabspApplication.getInstance(), TabspApplication.getInstance().getConfig().getBaseLogo(), R.drawable.tabsp_main_logo, logo);
    }

    @Override
    public int getLayout() {
        return R.layout.tabsp_version3_title_layout;
    }
}
