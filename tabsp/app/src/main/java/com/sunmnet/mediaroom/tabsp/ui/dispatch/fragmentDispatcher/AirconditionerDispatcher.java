package com.sunmnet.mediaroom.tabsp.ui.dispatch.fragmentDispatcher;

import android.databinding.DataBindingUtil;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sunmnet.mediaroom.common.BaseActivity;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ToastUtil;
import com.sunmnet.mediaroom.device.bean.Airconditioner;
import com.sunmnet.mediaroom.device.bean.DeviceState;
import com.sunmnet.mediaroom.device.bean.DeviceType;
import com.sunmnet.mediaroom.device.bean.IDevice;
import com.sunmnet.mediaroom.device.bean.StateEventType;
import com.sunmnet.mediaroom.device.controll.Controller;
import com.sunmnet.mediaroom.device.events.DeviceLoadedEvent;
import com.sunmnet.mediaroom.device.events.DeviceNotifyEvent;
import com.sunmnet.mediaroom.device.listeners.DeviceStateNotifier;
import com.sunmnet.mediaroom.devicebean.new2.BaseDeviceDto;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.TabspApplication;
import com.sunmnet.mediaroom.tabsp.bean.SettingMode;
import com.sunmnet.mediaroom.tabsp.common.Constants;
import com.sunmnet.mediaroom.tabsp.databinding.AirconditionerBinding;
import com.sunmnet.mediaroom.tabsp.databinding.DeviceItem;
import com.sunmnet.mediaroom.tabsp.ui.adapter.AbstractHolder;
import com.sunmnet.mediaroom.tabsp.ui.adapter.BindingAdapter;
import com.sunmnet.mediaroom.tabsp.ui.adapter.deviceFactory.ListViewDeviceFactory;
import com.sunmnet.mediaroom.tabsp.ui.adapter.deviceFactory.ModeSelectFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 空调控制界面
 * */
@Route(path = Constants.ROUTERPATH_CONTROLL_DEVICE_AIRCONDITIONER)
public class AirconditionerDispatcher extends AbstractFragmentDispatcher implements View.OnClickListener, DeviceStateNotifier {
    BaseActivity activity;
    Airconditioner airconditioner;
    AirconditionerBinding airconditionerBinding;
    List<SettingMode> modes;
    AbstractHolder<DeviceItem, SettingMode> prevMode;
    AbstractHolder<DeviceItem, Airconditioner> prevAirconditioner;
    Map<String, Integer> modeIndex = new HashMap<>();
    AdapterView.OnItemClickListener listviewItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            AbstractHolder<DeviceItem, Airconditioner> holder = (AbstractHolder<DeviceItem, Airconditioner>) view.getTag();
            if (prevAirconditioner != holder) {
                Airconditioner.Setting setting = holder.getProperty().getSetting();
                airconditionerBinding.setSetting(setting);
                if (prevAirconditioner != null) {
                    prevAirconditioner.setSelected(false);
                }
                holder.setSelected(true);
                onSelectAirconditioner(holder.getProperty());
                prevAirconditioner = holder;
            }
        }
    };

    private void onSelectAirconditioner(Airconditioner airconditioner) {
        //做模式的选中
        Airconditioner.Setting setting = airconditioner.getSetting();
        if (prevMode != null && !setting.getMode().equals(prevMode.getProperty().getMode())) {
            prevMode.setSelected(false);
            int index = modeIndex.get(setting.getMode());
            View view = airconditionerBinding.airconditionerMode.getChildAt(index);
            if (view != null && view.getTag() != null) {
                AbstractHolder holder = (AbstractHolder) view.getTag();
                holder.setSelected(true);
                prevMode = holder;
            }
        }
    }

    private void onAirconditionerChanged(Airconditioner airconditioner) {
        airconditionerBinding.setSetting(airconditioner.getSetting());
        String state = airconditioner.getControllString();
        Controller.getInstance().setState(airconditioner, state);
    }

    AdapterView.OnItemClickListener gridviewItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            AbstractHolder<DeviceItem, SettingMode> holder = (AbstractHolder<DeviceItem, SettingMode>) view.getTag();
            if (prevMode != holder && prevAirconditioner != null) {
                if (prevAirconditioner.getProperty().getSetting().getDeviceState().getStateValue().equals(DeviceState.CLOSED.getStateValue())) {
                    ToastUtil.show(activity, "请先点击\"开启\"按钮");
                    return;
                }
                if (prevMode != null) {
                    prevMode.setSelected(false);
                }
                holder.setSelected(true);
                onSelectMode(holder.getProperty());
                prevMode = holder;
            }
        }
    };

    private void onSelectMode(SettingMode mode) {
        if (prevAirconditioner != null) {
            Airconditioner airconditioner = prevAirconditioner.getProperty();
            airconditioner.getSetting().setMode(mode.getMode());
            onAirconditionerChanged(airconditioner);
        }
    }

    public AirconditionerDispatcher() {
        airconditioner = new Airconditioner(DeviceType.AIRCONDITIONER);
        BaseDeviceDto deviceDto = new BaseDeviceDto();
        deviceDto.setDeviceName(TabspApplication.getInstance().getString(R.string.name_common_setting));
        airconditioner.setProperty(deviceDto);
        Airconditioner.Setting setting = new Airconditioner.Setting();
        setting.setMode("1");
        setting.setTempeture("25");
        setting.setDeviceState(DeviceState.CLOSED);
        airconditioner.setSetting(setting);
    }

    @OnClick({R.id.switcher_open, R.id.switcher_close, R.id.airconditioner_plus, R.id.airconditioner_minus})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.switcher_close:
                setAirconditionerState(DeviceState.CLOSED);
                break;
            case R.id.switcher_open:
                setAirconditionerState(DeviceState.OPENED);
                break;
            case R.id.airconditioner_plus:
                setAirconditionerState(1);
                break;
            case R.id.airconditioner_minus:
                setAirconditionerState(-1);
                break;
        }
    }

    private void setAirconditionerState(DeviceState state) {
        if (prevAirconditioner != null) {
            Airconditioner airconditioner = prevAirconditioner.getProperty();
            airconditioner.getSetting().setDeviceState(state);
            onAirconditionerChanged(airconditioner);
        }
    }

    private void setAirconditionerState(int tempeture) {
        if (prevAirconditioner != null) {
            Airconditioner airconditioner = prevAirconditioner.getProperty();
            if (airconditioner.getSetting().getDeviceState().getStateValue().equals(DeviceState.CLOSED.getStateValue())) {
                ToastUtil.show(activity, "请先点击\"开启\"按钮");
                return;
            }
            int value = Integer.parseInt(airconditioner.getSetting().getTempeture());
            value += tempeture;
            if (value >= 32) value = 32;
            if (value <= 16) value = 16;
            airconditioner.getSetting().setTempeture(value + "");
            onAirconditionerChanged(airconditioner);
        }
    }

    private SettingMode createAirconditionerMode(int string) {
        SettingMode mode = null;
        String name = TabspApplication.getInstance().getString(string);
        switch (string) {
            case R.string.name_airconditioner_heat:
                mode = new SettingMode(name, R.drawable.aircondtioner_mode_heat_on, R.drawable.aircondtioner_mode_heat, "5");
                break;
            case R.string.name_airconditioner_cold:
                mode = new SettingMode(name, R.drawable.aircondtioner_mode_cold_on, R.drawable.aircondtioner_mode_cold, "2");
                break;
            case R.string.name_airconditioner_fan:
                mode = new SettingMode(name, R.drawable.aircondtioner_mode_fan_on, R.drawable.aircondtioner_mode_fan, "4");
                break;
            case R.string.name_airconditioner_xeransis:
                mode = new SettingMode(name, R.drawable.aircondtioner_mode_xeransis_on, R.drawable.aircondtioner_mode_xeransis, "3");
                break;
            case R.string.name_airconditioner_auto:
                mode = new SettingMode(name, R.drawable.aircondtioner_mode_auto_on, R.drawable.aircondtioner_mode_auto, "1");
                break;
        }
        modeIndex.put(mode.getMode(), modes.size());
        return mode;
    }

    @Override
    public int getLayout() {
        return R.layout.tabsp_airconditioner_layout;
    }

    @Override
    public <E> void dispatch(View view, E e) {
        if (e instanceof BaseActivity) this.activity = (BaseActivity) e;
        dispatch(view);
    }

    @Override
    public void dispatch(View view) {
        List<IDevice> devices = Controller.getInstance().getDevicesByDeviceType(DeviceType.AIRCONDITIONER);
        if (devices != null) {
            ButterKnife.bind(view);
            List<IDevice> airconditioners = new ArrayList<>();
            airconditioners.add(airconditioner);
            airconditioners.addAll(devices);
            airconditionerBinding = DataBindingUtil.bind(view);
            airconditionerBinding.devicelist.setOnItemClickListener(this.listviewItemClick);
            airconditionerBinding.airconditionerMode.setOnItemClickListener(this.gridviewItemClick);
            airconditionerBinding.airconditionerMinus.setOnClickListener(this);
            airconditionerBinding.airconditionerPlus.setOnClickListener(this);
            airconditionerBinding.switcherClose.setOnClickListener(this);
            airconditionerBinding.switcherOpen.setOnClickListener(this);
            ListViewDeviceFactory.init(this.activity, R.attr.colorPrimary, R.attr.device_black_text_color);
            BindingAdapter adapter = new BindingAdapter(R.layout.tabsp_device_listviewitem_layout, new ListViewDeviceFactory());
            adapter.setData(airconditioners);
            airconditionerBinding.setAdapter(adapter);
            airconditionerBinding.devicelist.postDelayed(new Runnable() {
                @Override
                public void run() {
                    airconditionerBinding.devicelist.performItemClick(airconditionerBinding.devicelist.getChildAt(0), 0, airconditionerBinding.devicelist.getItemIdAtPosition(0));
                }
            }, 50);

            modes = new ArrayList<>();
            modes.add(createAirconditionerMode(R.string.name_airconditioner_heat));
            modes.add(createAirconditionerMode(R.string.name_airconditioner_cold));
            modes.add(createAirconditionerMode(R.string.name_airconditioner_fan));
            modes.add(createAirconditionerMode(R.string.name_airconditioner_xeransis));
            modes.add(createAirconditionerMode(R.string.name_airconditioner_auto));

            airconditionerBinding.airconditionerMode.setNumColumns(this.modes.size());
            BindingAdapter modeAdapter = new BindingAdapter(R.layout.tabsp_mode_item_layout, new ModeSelectFactory());
            modeAdapter.setData(this.modes);
            airconditionerBinding.setSettingAdapter(modeAdapter);
            // 初始化空调模式
            airconditionerBinding.airconditionerMode.postDelayed(() -> {
                int childCount = airconditionerBinding.airconditionerMode.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    ((AbstractHolder<DeviceItem, Airconditioner>) airconditionerBinding.airconditionerMode.getChildAt(i).getTag()).setSelected(false);
                }
            }, 50);
        }

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    private void registerEvent(int type, IDevice airconditioner) {
        DeviceNotifyEvent event = new DeviceNotifyEvent();
        event.setMessage(this);
        StateEventType eventType = new StateEventType();
        eventType.type = type;
        eventType.key = airconditioner.getDeviceCode();
        event.setEventType(eventType);
        EventBus.getDefault().post(event);
    }

    @Override
    public void release() {
        super.release();
        RunningLog.run("AirconditionerDispatcher  回收资源");
        List<IDevice> devices = Controller.getInstance().getDevicesByDeviceType(DeviceType.AIRCONDITIONER);
        if (devices != null) {
            for (IDevice device : devices
            ) {
                registerEvent(0, device);
            }
        }

        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public String getListenerKey() {
        return null;
    }

    @Override
    public void callUpdate() {

    }

    @Override
    public void onReady() {
        RunningLog.run("AircondirionerDispatcher.onReady...");
        List<IDevice> devices = Controller.getInstance().getDevicesByDeviceType(DeviceType.AIRCONDITIONER);
        for (int i = 0; i < devices.size(); i++) {
            IDevice airconditioner = (IDevice) devices.get(i);
            registerEvent(1, airconditioner);
        }
    }

    @Override
    public void callUpdate(String code) {
        if (prevAirconditioner != null && prevAirconditioner.getProperty().getDeviceCode().equals(code)) {
            if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
                onSelectAirconditioner(prevAirconditioner.getProperty());
            } else airconditionerBinding.devicelist.post(new Runnable() {
                @Override
                public void run() {
                    Airconditioner.Setting setting = prevAirconditioner.getProperty().getSetting();
                    airconditionerBinding.setSetting(setting);
                    int index = modeIndex.get(setting.getMode());
                    View view = airconditionerBinding.airconditionerMode.getChildAt(index);
                    if (view != null && view.getTag() != null) {
                        AbstractHolder holder = (AbstractHolder) view.getTag();
                        if (prevMode != holder) {
                            if (prevMode != null) prevMode.setSelected(false);
                        }
                        holder.setSelected(true);
                        prevMode = holder;
                    }
                }
            });
        }
    }

    /**
     * 设备重新加载，界面数据也重新加载, 事件触发源在DeviceControllerImpl.java文件
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDeviceLoaded(DeviceLoadedEvent event) {
        List<IDevice> devices = Controller.getInstance().getDevicesByDeviceType(DeviceType.AIRCONDITIONER);
        if (devices != null) {
            List<IDevice> airconditioners = new ArrayList<>();
            airconditioners.add(airconditioner);
            airconditioners.addAll(devices);
            airconditionerBinding.getAdapter().setData(airconditioners);
            airconditionerBinding.getAdapter().notifyDataSetChanged();
        }
    }
}
