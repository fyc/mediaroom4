package com.sunmnet.mediaroom.tabsp.controll.version1;

import android.databinding.DataBindingUtil;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sunmnet.mediaroom.common.BaseActivity;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ToastUtil;
import com.sunmnet.mediaroom.device.bean.CustomDevice;
import com.sunmnet.mediaroom.device.bean.DeviceType;
import com.sunmnet.mediaroom.device.bean.IDevice;
import com.sunmnet.mediaroom.device.bean.StateEventType;
import com.sunmnet.mediaroom.device.controll.Controller;
import com.sunmnet.mediaroom.device.events.DeviceLoadedEvent;
import com.sunmnet.mediaroom.device.events.DeviceNotifyEvent;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.bean.MenuEntity;
import com.sunmnet.mediaroom.tabsp.common.Constants;
import com.sunmnet.mediaroom.tabsp.databinding.DeviceAdapter;
import com.sunmnet.mediaroom.tabsp.ui.adapter.BindingAdapter;
import com.sunmnet.mediaroom.tabsp.ui.adapter.DeviceHolder;
import com.sunmnet.mediaroom.tabsp.ui.adapter.deviceFactory.DeviceFactory;
import com.sunmnet.mediaroom.tabsp.ui.dispatch.fragmentDispatcher.AbstractFragmentDispatcher;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * 自定义设备界面
 */
@Route(path = Constants.ROUTERPATH_CONTROLL_DEVICE_CUSTOM_MENU)
public class CustomDeviceMenuDispatch extends AbstractFragmentDispatcher {
    private View contentView;
    private BindingAdapter adapter;
    private BaseActivity activity;
    private DeviceAdapter binding;
    private String type;
    private String prevType;
    private DeviceType deviceType;
    private String parentConnect;

    // 全开全关使能标志
    private boolean enableAllOpenClose = true;
    // 全开全关操作间隔时间4秒
    private CountDownTimer allCountDownTimer = new CountDownTimer(4000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            // 使能全开全关
            enableAllOpenClose = true;
        }
    };

    @Override
    public int getLayout() {
        return R.layout.tabsp_device_common_layout;
    }


    @OnItemClick(R.id.commondevicegrid)
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        DeviceHolder holder = (DeviceHolder) view.getTag();
        CustomDevice customDevice = (CustomDevice) holder.getProperty();
        List<CustomDevice.ComponentItem> functionList = customDevice.getFunctionList();
        List<CustomDevice.ComponentItem> sourceList = customDevice.getSignalSourceList();
        if (functionList.isEmpty() && sourceList.isEmpty()) {
            Controller.getInstance().reverse(holder.getProperty());
            holder.callUpdate(holder.getProperty().getDeviceCode());
        } else {
            MenuEntity entity = new MenuEntity(Constants.ROUTERPATH_CONTROLL_DEVICE_CUSTOM_DETAIL, type, Constants.ROUTERPATH_CONTROLL_DEVICE_CUSTOM_MENU);
            entity.addData("device", customDevice);
            EventBus.getDefault().post(entity);
        }
    }

    @OnClick({R.id.batch_open, R.id.batch_close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.batch_open:
                if (enableAllOpenClose) {
                    enableAllOpenClose = false;
                    allCountDownTimer.start();
                    ToastUtil.show(contentView.getContext(), "执行全开操作");

                    RunningLog.run("[CustomDeviceDispatch] open all device：" + deviceType.getDeviceType());
                    Controller.getInstance().open(deviceType);
                } else {
                    ToastUtil.show(contentView.getContext(), "操作执行中，请稍等。");
                }
                break;
            case R.id.batch_close:
                if (enableAllOpenClose) {
                    enableAllOpenClose = false;
                    allCountDownTimer.start();
                    ToastUtil.show(contentView.getContext(), "执行全关操作");

                    RunningLog.run("[CustomDeviceDispatch] close all device：" + deviceType.getDeviceType());
                    Controller.getInstance().close(deviceType);
                } else {
                    ToastUtil.show(contentView.getContext(), "操作执行中，请稍等。");
                }
                break;
        }
    }

    @Override
    public void dispatch(View view) {
        super.dispatch(view);
        this.contentView = view;
    }

    @Override
    public <E> void dispatch(View view, E e) {
        super.dispatch(view, e);
        if (e instanceof BaseActivity) {
            this.activity = (BaseActivity) e;
        }
        if (this.contentView != view && view != null) {
            this.dispatch(this.type, view);
        }
    }

    @Override
    public void dispatch(String type) {
        super.dispatch(type);
        this.prevType = this.type;
        this.type = type;
        deviceType = DeviceType.getCustomType(type);
        if (deviceType == null) {
            return;
        }
        if (this.contentView != null) {
            this.dispatch(type, contentView);
        }
    }

    @Override
    public void dispatch(String type, View view) {
        super.dispatch(type, view);
        this.contentView = view;
        RunningLog.run(this.toString() + " dispatch(View view)");
        ButterKnife.bind(this, view);
        binding = DataBindingUtil.bind(view);
        if (TextUtils.isEmpty(parentConnect)) {
            binding.backBtn.setVisibility(View.GONE);
        } else {
            binding.deviceSettingText.setText(deviceType.getDeviceTypeName());
            binding.backBtn.setVisibility(View.VISIBLE);
        }
        List<IDevice> devices = Controller.getInstance().getDevicesByDeviceType(deviceType);
        adapter = new BindingAdapter(R.layout.tabsp_device_item_layout, new DeviceFactory());
        if (devices != null && devices.size() > 0) {
            adapter.setData(devices);
        } else {
            adapter.setData(Collections.emptyList());
        }
        binding.setAdapter(adapter);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onReady() {
        RunningLog.run(this.toString() + " onReady");
        if (lifeCycleState.get() == RELEASE) {
            adapter = new BindingAdapter(R.layout.tabsp_device_item_layout, new DeviceFactory());
            List<IDevice> devicesByDeviceType = Controller.getInstance().getDevicesByDeviceType(deviceType);
            adapter.setData(devicesByDeviceType);
            binding.setAdapter(adapter);
        }
        super.onReady();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void dispatch(String type, String parent) {
        super.dispatch(type, parent);
        RunningLog.run(this.toString() + " dispatch( " + type + " , " + parent + " )");
        parentConnect = parent;
        this.dispatch(type);
    }

    @Override
    public void release() {
        super.release();
        RunningLog.run(this.toString() + " release");
        unregisterType(prevType);
        unregisterType(type);
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        allCountDownTimer.cancel();
        enableAllOpenClose = true;
    }

    @Override
    public void invisible() {
        super.invisible();
        RunningLog.run(this.toString() + " invisible");
        unregisterType(prevType);
    }

    void unregisterType(String type) {
        if (type != null) {
            DeviceNotifyEvent event = new DeviceNotifyEvent();
            StateEventType eventType = new StateEventType();
            eventType.type = 4;
            eventType.key = type;
            event.setEventType(eventType);
            EventBus.getDefault().post(event);
        }
    }

    /**
     * 设备重新加载，界面数据也重新加载, 事件触发源在DeviceControllerImpl.java文件
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDeviceLoaded(DeviceLoadedEvent event) {
        List<IDevice> devices = Controller.getInstance().getDevicesByDeviceType(deviceType);
        if (devices != null && devices.size() > 0) {
            adapter.setData(devices);
        } else {
            adapter.setData(Collections.emptyList());
        }
        adapter.notifyDataSetChanged();
    }
}
