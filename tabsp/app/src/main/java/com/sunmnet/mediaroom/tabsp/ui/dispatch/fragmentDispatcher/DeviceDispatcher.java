package com.sunmnet.mediaroom.tabsp.ui.dispatch.fragmentDispatcher;

import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ThreadUtils;
import com.sunmnet.mediaroom.common.tools.ToastUtil;
import com.sunmnet.mediaroom.device.DeviceBuilder;
import com.sunmnet.mediaroom.device.bean.Device;
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
import com.sunmnet.mediaroom.tabsp.ui.adapter.IHolder;
import com.sunmnet.mediaroom.tabsp.ui.adapter.deviceFactory.DeviceFactory;
import com.sunmnet.mediaroom.tabsp.ui.adapter.deviceFactory.DimmerFactory;
import com.sunmnet.mediaroom.tabsp.ui.adapter.deviceFactory.DoorFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
/**
 * 设备操作界面  灯光、门锁、其他可控设备
 * */
@Route(path = Constants.ROUTERPATH_CONTROLL_DEVICE)
public class DeviceDispatcher extends AbstractFragmentDispatcher implements Runnable {
    View contentView;
    BindingAdapter adapter;
    Object locker = null;
    String type;
    @BindView(R.id.commondevicegrid)
    GridView deviceList;
    @BindView(R.id.device_setting_text)
    TextView setting;
    @BindView(R.id.back_btn)
    View back;
    static Map<DeviceType, BindingAdapter> adapters = new HashMap<>();
    DeviceAdapter deviceAdapterBinding;
    String parentConnect;
    String prevType;

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

    public DeviceDispatcher() {

    }

    @Override
    public void dispatch(View view) {
        this.contentView = view;
        if (this.locker != null) {
            synchronized (this.locker) {
                this.locker.notifyAll();
            }
            this.locker = null;
        }
    }

    @Override
    public <E> void dispatch(View view, E e) {
        super.dispatch(view, e);
        if (this.contentView != view && view != null) {
            this.dispatch(this.type, view);
        }
    }

    @Override
    public void dispatch(String type) {
        this.prevType = this.type;
        this.type = type;
        if (this.contentView == null) {
            this.locker = new Object();
            ThreadUtils.execute(this);
        } else this.dispatch(type, contentView);
    }

    @Override
    public void dispatch(String type, String parent) {
        this.parentConnect = parent;
        this.dispatch(type);
    }

    private BindingAdapter getAdapter(DeviceType type, Class clazz, int layout) {
        BindingAdapter adapter = null;
        if (adapters.containsKey(type)) {
            adapter = adapters.get(type);
        } else {
            try {
                IHolder.HolderFactory factory = (IHolder.HolderFactory) clazz.newInstance();
                adapter = new BindingAdapter(layout, factory);
                adapters.put(type, adapter);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
        return adapter;
    }

    @OnClick({R.id.back_btn, R.id.batch_close, R.id.batch_open})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_btn:
                MenuEntity menuEntity = new MenuEntity(parentConnect, null, null);
                EventBus.getDefault().post(menuEntity);
                break;
            case R.id.batch_open:
                if (enableAllOpenClose) {
                    enableAllOpenClose = false;
                    allCountDownTimer.start();
                    ToastUtil.show(contentView.getContext(), "执行全开操作");

                    DeviceType type = DeviceBuilder.getDeviceType(this.type);
                    if (type.getDeviceType().equalsIgnoreCase(DeviceType.LIGHT.getDeviceType())) {
                        List<IDevice> lights = Controller.getInstance().getDevicesByDeviceType(DeviceType.LIGHT);
                        List<IDevice> dimmers = Controller.getInstance().getDevicesByDeviceType(DeviceType.DIMMER);
                        if (lights != null && !lights.isEmpty()) {
                            Controller.getInstance().open(type);
                        }
                        if (dimmers != null && !dimmers.isEmpty()) {
                            try {
                                Thread.sleep(200);
                                Controller.getInstance().open(DeviceType.DIMMER);
                            } catch (InterruptedException e) {
                                RunningLog.error(e.getMessage());
                                e.printStackTrace();
                            }
                        }
                    } else {
                        Controller.getInstance().open(type);
                    }

                    adapter.notifyDataSetChanged();
                } else {
                    ToastUtil.show(contentView.getContext(), "操作执行中，请稍等。");
                }
                break;
            case R.id.batch_close:
                if (enableAllOpenClose) {
                    enableAllOpenClose = false;
                    allCountDownTimer.start();
                    ToastUtil.show(contentView.getContext(), "执行全关操作");

                    batchClose();
                    adapter.notifyDataSetChanged();
                } else {
                    ToastUtil.show(contentView.getContext(), "操作执行中，请稍等。");
                }
                break;
        }
    }

    @Override
    public void release() {
        //this.contentView = null;
        RunningLog.run("DeviceDispatcher.onRelease()");
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        allCountDownTimer.cancel();
        enableAllOpenClose = true;
    }

    private void batchClose() {
        DeviceType type1 = DeviceBuilder.getDeviceType(this.type);
        if (type1.getDeviceType().equalsIgnoreCase(DeviceType.LIGHT.getDeviceType())) {
            List<IDevice> lights = Controller.getInstance().getDevicesByDeviceType(DeviceType.LIGHT);
            List<IDevice> dimmers = Controller.getInstance().getDevicesByDeviceType(DeviceType.DIMMER);
            if (lights != null && !lights.isEmpty()) {
                Controller.getInstance().close(type1);
            }
            if (dimmers != null && !dimmers.isEmpty()) {
                try {
                    Thread.sleep(200);
                    Controller.getInstance().close(DeviceType.DIMMER);
                } catch (InterruptedException e) {
                    RunningLog.error(e.getMessage());
                    e.printStackTrace();
                }
            }
        } else {
            Controller.getInstance().close(type1);
        }
    }

    @OnItemClick(R.id.commondevicegrid)
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        DeviceHolder holder = (DeviceHolder) view.getTag();
        Controller.getInstance().reverse(holder.getProperty());
        holder.callUpdate(holder.getProperty().getDeviceCode());
    }

    @Override
    public void dispatch(String deviceType, View view) {
        if (view != null) {
            RunningLog.run("正在部署" + deviceType);
            this.contentView = view;
            ButterKnife.bind(this, view);
            deviceAdapterBinding = DataBindingUtil.bind(view);
            DeviceType type = DeviceBuilder.getDeviceType(deviceType);
            if (this.parentConnect != null) {
                RunningLog.run("标题是：" + setting);
                setting.setText(DeviceBuilder.getDeviceTypeName(type.getDeviceType()));
                back.setVisibility(View.VISIBLE);
            } else {
                back.setVisibility(View.GONE);
            }
            List<IDevice> devices = null;
            if (type == DeviceType.DIMMER || type == DeviceType.LIGHT) {
                List<IDevice> lights = Controller.getInstance().getDevicesByDeviceType(DeviceType.LIGHT);
                List<IDevice> dimmers = Controller.getInstance().getDevicesByDeviceType(DeviceType.DIMMER);
                devices = new ArrayList<>();
                if (lights != null) devices.addAll(lights);
                if (dimmers != null) {
                    devices.addAll(dimmers);
                }
                if (contentView.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    deviceList.setNumColumns(5);
                } else {
                    deviceList.setNumColumns(3);
                }
                adapter = getAdapter(type, DimmerFactory.class, R.layout.tabsp_light_item_layout);
            } else if (type == DeviceType.DOOR) {
                devices = Controller.getInstance().getDevicesByDeviceType(type);
                adapter = getAdapter(type, DoorFactory.class, R.layout.tabsp_door_item_layout);
                if (contentView.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    deviceList.setNumColumns(2);
                } else {
                    deviceList.setNumColumns(1);
                }
            } else {
                devices = Controller.getInstance().getDevicesByDeviceType(type);
                adapter = getAdapter(type, DeviceFactory.class, R.layout.tabsp_device_item_layout);
                if (contentView.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    deviceList.setNumColumns(5);
                } else {
                    deviceList.setNumColumns(3);
                }
            }
            RunningLog.run("数据长度：" + devices.size());
            adapter.setData(devices);
            adapter.notifyDataSetChanged();
            deviceAdapterBinding.setAdapter(adapter);
            RunningLog.run("binding是：" + deviceAdapterBinding);
        } else {
            locker = new Object();
            ThreadUtils.execute(this);
        }
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public int getLayout() {
        return R.layout.tabsp_device_common_layout;
    }

    @Override
    public void invisible() {
        List<Device> devices = this.adapter.getData();
        if (devices != null && prevType != null) {
            DeviceNotifyEvent event = new DeviceNotifyEvent();
            StateEventType eventType = new StateEventType();
            eventType.type = 4;
            eventType.key = prevType;
            event.setEventType(eventType);
            EventBus.getDefault().post(event);
        }
    }

    @Override
    public void run() {
        while (contentView == null || type == null) {
            synchronized (locker) {
                try {
                    locker.wait(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        contentView.post(new Runnable() {
            @Override
            public void run() {
                dispatch(type, contentView);
            }
        });
    }

    /**
     * 设备重新加载，界面数据也重新加载, 事件触发源在DeviceControllerImpl.java文件
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDeviceLoaded(DeviceLoadedEvent event) {
        DeviceType type = DeviceBuilder.getDeviceType(this.type);
        List<IDevice> devices = null;
        if (type == DeviceType.DIMMER || type == DeviceType.LIGHT) {
            List<IDevice> lights = Controller.getInstance().getDevicesByDeviceType(DeviceType.LIGHT);
            List<IDevice> dimmers = Controller.getInstance().getDevicesByDeviceType(DeviceType.DIMMER);
            devices = new ArrayList<>();
            if (lights != null) devices.addAll(lights);
            if (dimmers != null) {
                devices.addAll(dimmers);
            }
        } else if (type == DeviceType.DOOR) {
            devices = Controller.getInstance().getDevicesByDeviceType(type);
        } else {
            devices = Controller.getInstance().getDevicesByDeviceType(type);
        }
        adapter.setData(devices);
        adapter.notifyDataSetChanged();
    }
}
