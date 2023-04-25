package com.sunmnet.mediaroom.tabsp.controll.version2;

import android.databinding.DataBindingUtil;

import android.view.View;
import android.widget.CompoundButton;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sunmnet.mediaroom.common.BaseActivity;
import com.sunmnet.mediaroom.device.bean.AbstractDevice;
import com.sunmnet.mediaroom.device.bean.Device;
import com.sunmnet.mediaroom.device.bean.DeviceType;
import com.sunmnet.mediaroom.device.bean.IDevice;
import com.sunmnet.mediaroom.device.bean.StateEventType;
import com.sunmnet.mediaroom.device.controll.Controller;
import com.sunmnet.mediaroom.device.events.DeviceLoadedEvent;
import com.sunmnet.mediaroom.device.events.DeviceNotifyEvent;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.TabspApplication;
import com.sunmnet.mediaroom.tabsp.databinding.DisplayItem;
import com.sunmnet.mediaroom.tabsp.databinding.InteracitveController;
import com.sunmnet.mediaroom.tabsp.ui.adapter.DeviceHolder;
import com.sunmnet.mediaroom.tabsp.ui.adapter.HolderAdapter;
import com.sunmnet.mediaroom.tabsp.ui.adapter.IHolder;
import com.sunmnet.mediaroom.tabsp.ui.dispatch.fragmentDispatcher.AbstractFragmentDispatcher;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 显示控制
 * */
@Route(path = Version2Dispatcher.CONTROLLER_VERSION2_DISPLAY)
public class DisplayControllDispatcher extends AbstractFragmentDispatcher {
    BaseActivity activity;
    HolderAdapter adapter;
    InteracitveController binding;
    Map<String, DeviceHolder> deviceHolders = new HashMap<>();

    @Override
    public int getLayout() {
        return R.layout.tabsp_version2_display_layout;
    }

    private void setDevices(DeviceType type, List<IDevice> devices) {
        List<IDevice> interacitvies = Controller.getInstance().getDevicesByDeviceType(DeviceType.INTERACTIVE);
        if (interacitvies != null && interacitvies.size() > 0) {
            devices.addAll(interacitvies);
        }
    }

    @Override
    public void dispatch(View view) {
        binding = DataBindingUtil.bind(view);
        List<IDevice> devices = new ArrayList<>();
        setDevices(DeviceType.INTERACTIVE, devices);
        setDevices(DeviceType.SOUND, devices);
        setDevices(DeviceType.PROJECTOR, devices);
        if (devices.size() > 0) {
            adapter = new HolderAdapter(R.layout.tabsp_version2_display_item, new DisplayFactory());
            adapter.setData(devices);
            binding.setAdapter(adapter);
        }

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    private void listenToUpdate(int type, IDevice device) {
        DeviceNotifyEvent event = new DeviceNotifyEvent();
        StateEventType eventType = new StateEventType();
        eventType.type = type;
        eventType.key = device.getDeviceCode();
        event.setEventType(eventType);
        EventBus.getDefault().post(event);
    }


    @Override
    public void release() {
        //取消相关监听
        List<Device> devices = this.adapter.getData();
        for (int i = 0, size = devices.size(); i < size; i++) {
            listenToUpdate(4, devices.get(i));
        }

        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public <E> void dispatch(View view, E e) {
        if (e instanceof BaseActivity) this.activity = (BaseActivity) e;
        this.dispatch(view);
    }

    private class DisplayFactory implements IHolder.HolderFactory {
        DisplayItem displayItem;

        @Override
        public IHolder newHolder() {
            return new DeviceHolder<DisplayItem>() {
                CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            Controller.getInstance().open(property);
                        } else Controller.getInstance().close(property);
                    }
                };

                @Override
                public void callUpdate(String code) {
                    if (binding != null) binding.controllSwitcher.post(this);
                }

                @Override
                public void run() {
                    setProperty(this.property);
                }

                @Override
                public void setProperty(DisplayItem displayItem, AbstractDevice device) {
                    DisplayControllDispatcher.this.deviceHolders.put(device.getDeviceCode(), this);
                    this.binding = displayItem;
                    this.property = device;
                    String name = property.getDeviceName() + TabspApplication.getInstance().getString(R.string.name_controll);
                    binding.controllName.setText(name);
                    register(device);
                    setProperty(device);
                }

                private void setProperty(AbstractDevice device) {
                    if (device.isProcessing()) {
                        binding.controllProcessing.setVisibility(View.VISIBLE);
                        return;
                    } else binding.controllProcessing.setVisibility(View.GONE);
                    binding.controllSwitcher.setOnCheckedChangeListener(null);
                    binding.controllSwitcher.setChecked(device.isOpened());
                    binding.controllSwitcher.setOnCheckedChangeListener(onCheckedChangeListener);
                }
            };
        }
    }

    /**
     * 设备重新加载，界面数据也重新加载, 事件触发源在DeviceControllerImpl.java文件
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDeviceLoaded(DeviceLoadedEvent event) {
        List<IDevice> devices = new ArrayList<>();
        setDevices(DeviceType.INTERACTIVE, devices);
        setDevices(DeviceType.SOUND, devices);
        setDevices(DeviceType.PROJECTOR, devices);
        if (devices.size() > 0) {
            adapter.setData(devices);
            adapter.notifyDataSetChanged();
        }
    }
}
