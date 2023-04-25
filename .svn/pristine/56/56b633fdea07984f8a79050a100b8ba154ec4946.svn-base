package com.sunmnet.mediaroom.tabsp.controll.version1;

import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sunmnet.mediaroom.common.BaseActivity;
import com.sunmnet.mediaroom.common.tools.JacksonUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.device.bean.CustomDevice;
import com.sunmnet.mediaroom.device.bean.DeviceState;
import com.sunmnet.mediaroom.device.bean.DeviceType;
import com.sunmnet.mediaroom.device.bean.IDevice;
import com.sunmnet.mediaroom.device.bean.protocol.SerialControl;
import com.sunmnet.mediaroom.device.controll.Controller;
import com.sunmnet.mediaroom.device.events.DeviceLoadedEvent;
import com.sunmnet.mediaroom.devicebean.tag.CommuTag;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.TabspApplication;
import com.sunmnet.mediaroom.tabsp.bean.MenuEntity;
import com.sunmnet.mediaroom.tabsp.common.Constants;
import com.sunmnet.mediaroom.tabsp.databinding.CustomBinding;
import com.sunmnet.mediaroom.tabsp.databinding.CustomDeviceItem;
import com.sunmnet.mediaroom.tabsp.databinding.DeviceItem;
import com.sunmnet.mediaroom.tabsp.ui.adapter.AbstractHolder;
import com.sunmnet.mediaroom.tabsp.ui.adapter.BindingAdapter;
import com.sunmnet.mediaroom.tabsp.ui.adapter.deviceFactory.CustomDeviceComponentFactory;
import com.sunmnet.mediaroom.tabsp.ui.adapter.deviceFactory.ListViewDeviceFactory;
import com.sunmnet.mediaroom.tabsp.ui.dispatch.fragmentDispatcher.AbstractFragmentDispatcher;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * 自定义设备界面
 */
@Route(path = Constants.ROUTERPATH_CONTROLL_DEVICE_CUSTOM)
public class CustomDeviceDispatch extends AbstractFragmentDispatcher {
    private String parentConnect;
    private View contentView;
    private BindingAdapter adapter;
    private BaseActivity activity;
    private CustomBinding binding;
    private String type;
    private String prevType;
    private DeviceType deviceType;

    private CustomDevice commonDevice;

    private AbstractHolder<DeviceItem, CustomDevice> prevDevice;

    @Override
    public int getLayout() {
        return R.layout.tabsp_custom_device_layout;
    }

    @OnItemClick(R.id.deviceListView)
    public void onListViewItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //选中设备时的逻辑
        AbstractHolder<DeviceItem, CustomDevice> holder = (AbstractHolder<DeviceItem, CustomDevice>) view.getTag();
        if (prevDevice != holder) {
            if (prevDevice != null) {
                prevDevice.setSelected(false);
            }
            holder.setSelected(true);
            prevDevice = holder;
        }
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
        if (prevDevice != null) {
            CustomDevice customDevice = prevDevice.getProperty();
            SerialControl serialControl = new SerialControl();
            serialControl.setOperateMethod(3);
            List<SerialControl.DevicesBean> controlDevices = new LinkedList<>();
            serialControl.setDevices(controlDevices);
            if (customDevice == commonDevice) {
                List<IDevice> devices = Controller.getInstance().getDevicesByDeviceType(deviceType);
                for (IDevice device : devices) {
                    SerialControl.DevicesBean controlDevice = new SerialControl.DevicesBean();
                    controlDevice.setDeviceCode(device.getDeviceCode());
                    controlDevice.setDeviceType(device.getDeviceTypeCode());
                    controlDevice.setControlCmd(holder.getProperty().getCode());
                    controlDevices.add(controlDevice);
                }
            } else {
                SerialControl.DevicesBean controlDevice = new SerialControl.DevicesBean();
                controlDevice.setDeviceCode(customDevice.getDeviceCode());
                controlDevice.setDeviceType(customDevice.getDeviceTypeCode());
                controlDevice.setControlCmd(holder.getProperty().getCode());
                controlDevices.add(controlDevice);
            }
            Controller.getInstance().sendMsg(CommuTag.SERIAL_CONTROL, JacksonUtil.objToJsonStr(serialControl));
        } else {
            RunningLog.run("[CustomDeviceDispatch] prevDevice == null");
        }
    }

    @OnClick({R.id.switcher_open, R.id.switcher_close, R.id.back_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.switcher_open:
                if (prevDevice != null) {
                    CustomDevice customDevice = prevDevice.getProperty();
                    if (customDevice == commonDevice) {
                        RunningLog.run("[CustomDeviceDispatch] open all device：" + deviceType.getDeviceType());
                        Controller.getInstance().open(deviceType);
                    } else {
                        RunningLog.run("[CustomDeviceDispatch] open device: " + customDevice.getDeviceCode());
                        Controller.getInstance().open(customDevice);
                    }
                } else {
                    RunningLog.run("[CustomDeviceDispatch] prevDevice == null");
                }
                break;
            case R.id.switcher_close:
                if (prevDevice != null) {
                    CustomDevice customDevice = prevDevice.getProperty();
                    if (customDevice == commonDevice) {
                        RunningLog.run("[CustomDeviceDispatch] close all device：" + deviceType.getDeviceType());
                        Controller.getInstance().close(deviceType);
                    } else {
                        RunningLog.run("[CustomDeviceDispatch] close device：" + customDevice.getDeviceCode());
                        Controller.getInstance().close(customDevice);
                    }
                } else {
                    RunningLog.run("[CustomDeviceDispatch] prevDevice == null");
                }
                break;
            case R.id.back_btn:
                MenuEntity menuEntity = new MenuEntity(parentConnect, null, null);
                EventBus.getDefault().post(menuEntity);
                break;
        }
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
        this.prevType = this.type;
        this.type = type;
        deviceType = DeviceType.getCustomType(type);
        if (deviceType == null) {
            return;
        }
        commonDevice = new CustomDevice(deviceType, TabspApplication.getInstance().getString(R.string.name_globle_controll), null);
        commonDevice.setDeviceState(DeviceState.CLOSED);
        if (this.contentView != null) {
            this.dispatch(type, contentView);
        }
    }

    @Override
    public void dispatch(String type, View view) {
        this.contentView = view;
        RunningLog.run(getClass().getSimpleName() + " dispatch(View view)");
        ButterKnife.bind(this, view);
        binding = DataBindingUtil.bind(view);
        binding.setTitleName(deviceType.getDeviceTypeName());
        if (TextUtils.isEmpty(parentConnect)) {
            binding.backBtn.setVisibility(View.GONE);
        } else {
            binding.backBtn.setVisibility(View.VISIBLE);
        }
        List<IDevice> devices = Controller.getInstance().getDevicesByDeviceType(deviceType);
        if (devices != null && devices.size() > 0) {
            ListViewDeviceFactory.init(this.activity, R.attr.colorPrimary, R.attr.device_black_text_color);

            prevDevice = null;
            adapter = new BindingAdapter(R.layout.tabsp_device_listviewitem_layout, new ListViewDeviceFactory());
            List<IDevice> newDevices = new ArrayList<>();
            newDevices.add(commonDevice);
            newDevices.addAll(devices);
            adapter.setData(newDevices);
            binding.setDeviceAdapter(adapter);
            CustomDevice device = (CustomDevice) devices.get(0);
            List<CustomDevice.ComponentItem> functionList = device.getFunctionList();
            BindingAdapter functionAdapter = new BindingAdapter(R.layout.tabsp_custom_device_item_layout, new CustomDeviceComponentFactory());
            functionAdapter.setData(functionList);
            binding.setFunctionAdapter(functionAdapter);
            if (functionList.size() == 0) {
                binding.functionLayout.setVisibility(View.GONE);
            } else {
                binding.functionLayout.setVisibility(View.VISIBLE);
            }

            List<CustomDevice.ComponentItem> sourceList = device.getSignalSourceList();
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
            binding.deviceListView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    binding.deviceListView.performItemClick(binding.deviceListView.getChildAt(0), 0, binding.deviceListView.getItemIdAtPosition(0));
                }
            }, 80);
        }

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void dispatch(String type, String parent) {
        RunningLog.run(getClass().getSimpleName() + " dispatch(String type, String parent)");
        this.parentConnect = parent;
        this.dispatch(type);
    }

    @Override
    public void release() {
        super.release();
        RunningLog.run(getClass().getSimpleName() + " release");
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
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

            prevDevice = null;
            List<IDevice> newDevices = new ArrayList<>();
            newDevices.add(commonDevice);
            newDevices.addAll(devices);
            adapter.setData(newDevices);
            adapter.notifyDataSetChanged();

            CustomDevice device = (CustomDevice) devices.get(0);
            List<CustomDevice.ComponentItem> functionList = device.getFunctionList();
            binding.getFunctionAdapter().setData(functionList);
            binding.getFunctionAdapter().notifyDataSetChanged();
            if (functionList.size() == 0) {
                binding.functionLayout.setVisibility(View.GONE);
            } else {
                binding.functionLayout.setVisibility(View.VISIBLE);
            }

            List<CustomDevice.ComponentItem> sourceList = device.getSignalSourceList();
            binding.getSourceAdapter().setData(sourceList);
            binding.getSourceAdapter().notifyDataSetChanged();

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
}
