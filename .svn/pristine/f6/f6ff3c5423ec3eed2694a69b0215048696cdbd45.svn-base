package com.sunmnet.mediaroom.tabsp.controll.version1;

import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sunmnet.mediaroom.common.BaseActivity;
import com.sunmnet.mediaroom.common.tools.JacksonUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.device.bean.CustomDevice;
import com.sunmnet.mediaroom.device.bean.DeviceType;
import com.sunmnet.mediaroom.device.bean.StateEventType;
import com.sunmnet.mediaroom.device.bean.protocol.SerialControl;
import com.sunmnet.mediaroom.device.controll.Controller;
import com.sunmnet.mediaroom.device.events.DeviceNotifyEvent;
import com.sunmnet.mediaroom.device.listeners.DeviceStateNotifier;
import com.sunmnet.mediaroom.devicebean.tag.CommuTag;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.bean.MenuEntity;
import com.sunmnet.mediaroom.tabsp.common.Constants;
import com.sunmnet.mediaroom.tabsp.databinding.CustomDetailBinding;
import com.sunmnet.mediaroom.tabsp.databinding.CustomDeviceItem;
import com.sunmnet.mediaroom.tabsp.ui.adapter.AbstractHolder;
import com.sunmnet.mediaroom.tabsp.ui.adapter.BindingAdapter;
import com.sunmnet.mediaroom.tabsp.ui.adapter.deviceFactory.CustomDeviceComponentFactory;
import com.sunmnet.mediaroom.tabsp.ui.dispatch.fragmentDispatcher.AbstractFragmentDispatcher;

import org.greenrobot.eventbus.EventBus;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * 自定义设备界面
 */
@Route(path = Constants.ROUTERPATH_CONTROLL_DEVICE_CUSTOM_DETAIL)
public class CustomDeviceDetailDispatch extends AbstractFragmentDispatcher implements DeviceStateNotifier {
    private String parentConnect;
    private View contentView;
    private BaseActivity activity;
    private CustomDetailBinding binding;
    private DeviceType deviceType;
    private String type;
    private CustomDevice customDevice;

    @Override
    public int getLayout() {
        return R.layout.tabsp_custom_device_detail_layout;
    }

    @OnItemClick({R.id.source, R.id.function})
    public void onComponentItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //选中设备时的逻辑
        AbstractHolder<CustomDeviceItem, CustomDevice.ComponentItem> holder = (AbstractHolder<CustomDeviceItem, CustomDevice.ComponentItem>) view.getTag();
        CustomDevice.ComponentItem property = holder.getProperty();
        if (property == null) {
            RunningLog.run("[CustomDeviceDispatch] Holder.getProperty()==null");
            return;
        }
        if (customDevice != null) {
            SerialControl serialControl = new SerialControl();
            serialControl.setOperateMethod(3);
            List<SerialControl.DevicesBean> controlDevices = new LinkedList<>();
            serialControl.setDevices(controlDevices);
            SerialControl.DevicesBean controlDevice = new SerialControl.DevicesBean();
            controlDevice.setDeviceCode(customDevice.getDeviceCode());
            controlDevice.setDeviceType(customDevice.getDeviceTypeCode());
            controlDevice.setControlCmd(holder.getProperty().getCode());
            controlDevices.add(controlDevice);
            Controller.getInstance().sendMsg(CommuTag.SERIAL_CONTROL, JacksonUtil.objToJsonStr(serialControl));
        }
    }

    @OnClick({R.id.back_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_btn:
                MenuEntity menuEntity = new MenuEntity(parentConnect, type, null);
                EventBus.getDefault().post(menuEntity);
                break;
        }
    }

    @OnCheckedChanged({R.id.switcher})
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (!buttonView.isPressed())
            return;
        Controller.getInstance().reverse(customDevice);
        callUpdate();
    }

    @Override
    public void dispatch(View view) {
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
        this.contentView = view;
        RunningLog.run(toString() + " dispatch(View view)");
        ButterKnife.bind(this, view);
        binding = DataBindingUtil.bind(view);
        binding.setTitleName(deviceType.getDeviceTypeName());
        if (TextUtils.isEmpty(parentConnect)) {
            binding.backBtn.setVisibility(View.GONE);
        } else {
            binding.backBtn.setVisibility(View.VISIBLE);
        }
        binding.switcher.setChecked(customDevice.isOpened());
        if (customDevice.isProcessing()) {
            binding.progress.setVisibility(View.VISIBLE);
        } else {
            binding.progress.setVisibility(View.GONE);
        }
        if (customDevice != null) {
            List<CustomDevice.ComponentItem> functionList = customDevice.getFunctionList();
            BindingAdapter functionAdapter = new BindingAdapter(R.layout.tabsp_custom_device_item_layout, new CustomDeviceComponentFactory());
            functionAdapter.setData(functionList);
            binding.setFunctionAdapter(functionAdapter);
            if (functionList.size() == 0) {
                binding.functionLayout.setVisibility(View.GONE);
            } else {
                binding.functionLayout.setVisibility(View.VISIBLE);
            }

            List<CustomDevice.ComponentItem> sourceList = customDevice.getSignalSourceList();
            BindingAdapter sourceAdapter = new BindingAdapter(R.layout.tabsp_custom_device_item_layout, new CustomDeviceComponentFactory());
            sourceAdapter.setData(sourceList);
            binding.setSourceAdapter(sourceAdapter);
            if (sourceList.size() == 0) {
                binding.sourceLayout.setVisibility(View.GONE);
            } else {
                binding.sourceLayout.setVisibility(View.VISIBLE);
            }

            if (sourceList.size() == 0 && functionList.size() == 0) {
                binding.customControlLayout.setVisibility(View.GONE);
            } else {
                binding.customControlLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void dispatch(String type, String parent) {
        RunningLog.run(toString() + " dispatch(String type, String parent)");
        this.parentConnect = parent;
        this.dispatch(type);
    }

    @Override
    public void onReady() {
        RunningLog.run(toString() + " onReady");
        super.onReady();
        register();
    }

    @Override
    public void invisible() {
        RunningLog.run(toString() + " invisible");
        super.invisible();
        unregister();
    }

    @Override
    public void setDataMap(Map<String, Object> map) {
        super.setDataMap(map);
        customDevice = (CustomDevice) map.get("device");
    }

    @Override
    public String getListenerKey() {
        return null;
    }

    @Override
    public void callUpdate() {
        binding.switcher.post(new Runnable() {
            @Override
            public void run() {
                binding.switcher.setChecked(customDevice.isOpened());
                if (customDevice.isProcessing()) {
                    binding.progress.setVisibility(View.VISIBLE);
                } else {
                    binding.progress.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void callUpdate(String code) {
        callUpdate();
    }

    protected void register() {
        if (customDevice != null) {
            DeviceNotifyEvent event = new DeviceNotifyEvent();
            event.setMessage(this);
            StateEventType eventType = new StateEventType();
            eventType.type = 3;
            eventType.key = customDevice.getDeviceCode();
            event.setEventType(eventType);
            EventBus.getDefault().post(event);
        }
    }

    protected void unregister() {
        if (customDevice != null) {
            DeviceNotifyEvent event = new DeviceNotifyEvent();
            event.setMessage(this);
            StateEventType eventType = new StateEventType();
            eventType.type = 2;
            eventType.key = customDevice.getDeviceCode();
            event.setEventType(eventType);
            EventBus.getDefault().post(event);
        }
    }
}
