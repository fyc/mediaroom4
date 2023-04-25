package com.sunmnet.mediaroom.tabsp.controll.version1;

import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sunmnet.mediaroom.common.BaseActivity;
import com.sunmnet.mediaroom.common.tools.JacksonUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ThreadUtils;
import com.sunmnet.mediaroom.device.bean.CustomDevice;
import com.sunmnet.mediaroom.device.bean.DeviceType;
import com.sunmnet.mediaroom.device.bean.IDevice;
import com.sunmnet.mediaroom.device.bean.StateEventType;
import com.sunmnet.mediaroom.device.bean.protocol.SerialControl;
import com.sunmnet.mediaroom.device.controll.Controller;
import com.sunmnet.mediaroom.device.events.DeviceLoadedEvent;
import com.sunmnet.mediaroom.device.events.DeviceNotifyEvent;
import com.sunmnet.mediaroom.devicebean.tag.CommuTag;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.customization.CustomPath;
import com.sunmnet.mediaroom.tabsp.databinding.CurtainDeviceAdapter;
import com.sunmnet.mediaroom.tabsp.ui.adapter.BindingAdapter;
import com.sunmnet.mediaroom.tabsp.ui.adapter.factory.CurtainDeviceFactory;
import com.sunmnet.mediaroom.tabsp.ui.dispatch.fragmentDispatcher.AbstractFragmentDispatcher;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Create by WangJincheng on 2021/6/15
 * 自定义窗帘
 */

@Route(path = CustomPath.CONTROLLER_DEVICE_CUSTOM_MENU_CURTAIN)
public class CustomCurtainDispatch extends AbstractFragmentDispatcher {

    private View contentView;
    private BindingAdapter adapter;
    private BaseActivity activity;
    private CurtainDeviceAdapter binding;
    private String type;
    private String prevType;
    private DeviceType deviceType;
    private String parentConnect;

    @Override
    public int getLayout() {
        return R.layout.tabsp_device_curtain_layout;
    }

    @OnClick({R.id.batch_open, R.id.batch_close, R.id.batch_pause})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.batch_open:
                RunningLog.run("[CustomDeviceDispatch] open all device：" + deviceType.getDeviceType());
                Controller.getInstance().open(deviceType);
                break;
            case R.id.batch_close:
                RunningLog.run("[CustomDeviceDispatch] close all device：" + deviceType.getDeviceType());
                Controller.getInstance().close(deviceType);
                break;
            case R.id.batch_pause:
                RunningLog.run("[CustomDeviceDispatch] pause all device：" + deviceType.getDeviceType());
                Runnable runnable = () -> {
                    try {
                        pauseAllCurtain();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                };
                ThreadUtils.schedule(runnable, 0, TimeUnit.SECONDS);
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
        adapter = new BindingAdapter(R.layout.tabsp_device_curtain_item_layout, new CurtainDeviceFactory());
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
            adapter = new BindingAdapter(R.layout.tabsp_device_curtain_item_layout, new CurtainDeviceFactory());
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
     * 暂停所有窗帘
     */
    private void pauseAllCurtain() throws InterruptedException {
        List<IDevice> devices = Controller.getInstance().getDevicesByDeviceType(deviceType);
        for (IDevice device : devices) {
            CustomDevice customDevice = (CustomDevice) device;
            List<CustomDevice.ComponentItem> functionList = customDevice.getFunctionList();
            for (CustomDevice.ComponentItem componentItem : functionList) {
                if (componentItem.getName().contains("暂停")) {
                    RunningLog.run("发送窗帘暂停指令");
                    SerialControl serialControl = new SerialControl();
                    serialControl.setOperateMethod(3);
                    List<SerialControl.DevicesBean> controlDevices = new LinkedList<>();
                    serialControl.setDevices(controlDevices);
                    SerialControl.DevicesBean controlDevice = new SerialControl.DevicesBean();
                    controlDevice.setDeviceCode(customDevice.getDeviceCode());
                    controlDevice.setDeviceType(customDevice.getDeviceTypeCode());
                    controlDevice.setControlCmd(componentItem.getCode());
                    controlDevices.add(controlDevice);
                    Controller.getInstance().sendMsg(CommuTag.SERIAL_CONTROL, JacksonUtil.objToJsonStr(serialControl));
                    Thread.sleep(100);
                }
            }
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
