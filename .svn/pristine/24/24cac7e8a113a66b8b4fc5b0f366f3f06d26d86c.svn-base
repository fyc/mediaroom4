package com.sunmnet.mediaroom.tabsp.controll.version3;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sunmnet.mediaroom.common.BaseActivity;
import com.sunmnet.mediaroom.common.tools.CommonUtil;
import com.sunmnet.mediaroom.device.bean.DeviceType;
import com.sunmnet.mediaroom.device.controll.Controller;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.common.Constants;
import com.sunmnet.mediaroom.tabsp.controll.version3.adapters.TypeAdapter;
import com.sunmnet.mediaroom.tabsp.ui.dispatch.fragmentDispatcher.AbstractFragmentDispatcher;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = Constants.ROUTERPATH_CONTROLL_V3_DEVICE_OPT)
public class Version3DeviceControllDispatcher extends AbstractFragmentDispatcher {
    BaseActivity activity;
    @BindView(R.id.deviceTypeName)
    TextView textView;
    DeviceType type;
    TypeAdapter adapter = null;
    @BindView(R.id.device_menu)
    ListView listView;
    DeviceType prevType;
    int selected, unselected;
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 1, sticky = true)
    public void onSwitchDeviceType(DeviceType type) {
        prevType = this.type;
        this.type = type;
        textView.setText(type.getDeviceTypeName());
        if (isNeedNewInstance()) {
            this.adapter = new TypeAdapter(type,selected,unselected);
            this.listView.setAdapter(adapter);
        } else {
            this.adapter.changeDeviceType(type);
        }
        adapter.notifyDataSetChanged();
    }

    private boolean isSpecialDevice(DeviceType type) {
        return type == DeviceType.AIRCONDITIONER || type == DeviceType.FRESHAIR || type == DeviceType.INTERACTIVE;
    }

    private boolean isNeedNewInstance() {
        if (prevType == null) return true;
        else {
            if (isSpecialDevice(prevType)) {
                //如果上一个是特殊设备，当前这个是普通设备，返回true
                if (!isSpecialDevice(this.type)) return true;
                //如果上一个是特殊设备，当前这个也是特殊设备，都是要重新加载，直接返回false
            } else {
                //如果上一个是普通设备，当前这个是特殊设备 返回true
                if (isSpecialDevice(this.type)) return true;
            }
        }
        return false;
    }

    @OnClick({R.id.batch_open, R.id.batch_close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.batch_close:
                Controller.getInstance().close(this.type);
                break;
            case R.id.batch_open:
                Controller.getInstance().open(this.type);
                break;
        }
    }
    @Override
    public void dispatch(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    public <E> void dispatch(View view, E e) {
        if (e != null && e instanceof BaseActivity) {
            this.activity = (BaseActivity) e;
            selected = CommonUtil.getColorByAttribute(activity, R.attr.common_text_color);
            unselected = CommonUtil.getColorByAttribute(activity, R.attr.device_black_text_color);
        }
        this.dispatch(view);
    }

    @Override
    public int getLayout() {
        return R.layout.tabsp_version3_device_controll_layout;
    }

    @Override
    public void release() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onReady() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }
}
