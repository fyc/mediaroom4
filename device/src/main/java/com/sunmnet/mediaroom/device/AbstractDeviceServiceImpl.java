package com.sunmnet.mediaroom.device;

import android.view.Menu;

import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.device.bean.DeviceState;
import com.sunmnet.mediaroom.device.bean.DeviceType;
import com.sunmnet.mediaroom.device.bean.IDevice;
import com.sunmnet.mediaroom.device.bean.StateEventType;
import com.sunmnet.mediaroom.device.controll.service.DeviceService;
import com.sunmnet.mediaroom.device.events.DeviceChangeEvent;
import com.sunmnet.mediaroom.device.events.DeviceNotifyEvent;
import com.sunmnet.mediaroom.device.listeners.DeviceStateNotifier;
import com.sunmnet.mediaroom.devicebean.new2.device.ai.SuDto;
import com.sunmnet.mediaroom.util.bean.BaseDeviceDto;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 设备操作服务的父类，设备类型或操作指令不同继承此类，并实现服务
 */
public abstract class AbstractDeviceServiceImpl implements DeviceService<IDevice, DeviceType> {
    protected Map<DeviceType, List<IDevice>> devices = new HashMap<>();
    protected Map<DeviceType, Map<String, IDevice>> mapDevices = new HashMap<>();
    protected Map<String, List<DeviceStateNotifier>> notifiers = new HashMap<>();
    protected SuDto suDto;

    protected <T> void notifyProperty(T t) {

    }

    @Override
    public void setDeviceSettings(String name, Map<String, String> params) {

    }

    @Override
    public void setState(IDevice device, DeviceState state) {

    }

    @Override
    public void setSu(SuDto su) {
        suDto = su;
    }

    @Override
    public SuDto getSu() {
        return suDto;
    }

    @Override
    public List<IDevice> getDevices() {
        List<IDevice> devices = new ArrayList<>();
        Iterator<List<IDevice>> iterator = this.devices.values().iterator();
        while (iterator.hasNext()) {
            devices.addAll(iterator.next());
        }
        return devices;
    }

    @Override
    public List<IDevice> getDevicesByDeviceType(DeviceType tag) {
        if (devices.containsKey(tag)) {
            return devices.get(tag);
        }
        return null;
    }

    @Override
    public void open(DeviceType deviceTypeCode) {
        List<IDevice> devices = getDevicesByDeviceType(deviceTypeCode);
        if (devices != null) {
            for (int i = 0; i < devices.size(); i++) {
                devices.get(i).setDeviceState(DeviceState.OPENNING);
            }
            //生成指令执行关闭
        }
    }

    @Override
    public void close(DeviceType deviceTypeCode) {
        List<IDevice> devices = getDevicesByDeviceType(deviceTypeCode);
        if (devices != null) {
            for (int i = 0; i < devices.size(); i++) {
                devices.get(i).setDeviceState(DeviceState.CLOSING);
            }
            //生成指令执行关闭
        }
    }

    @Override
    public void open(IDevice deviceInfo) {
        beforeOpen(deviceInfo);
    }

    protected IDevice setState(DeviceType deviceType, String deviceCode, String status) {
        if (this.mapDevices.containsKey(deviceType)) {
            Map<String, IDevice> cache = this.mapDevices.get(deviceType);
            if (cache.containsKey(deviceCode)) {
                DeviceState deviceState = DeviceBuilder.getDeviceStateByState(status);
                IDevice<BaseDeviceDto> device = cache.get(deviceCode);
                device.getProperty().setDeviceState(status);
                device.setDeviceState(deviceState);
                return device;
            }
        }
        return null;
    }

    private void onDeviceUpdate(List<String> values) {
        List<DeviceType> deviceTypes = new ArrayList<>();
        for (String info : values
        ) {
            String[] states = info.split(",");
            if (states.length == 2) {
                String deviceCode = states[0];
                String status = states[1];
                String deviceTypeCode = deviceCode.split("_")[0];
                DeviceType deviceType = DeviceBuilder.getDeviceType(deviceTypeCode);
                if (deviceType != null) {
                    setState(deviceType, deviceCode, status);
                    if (this.notifiers.containsKey(deviceCode)) {
                        postDeviceUpdate(this.notifiers.get(deviceCode), deviceCode);
                    }
                    if (!deviceTypes.contains(deviceType)) deviceTypes.add(deviceType);
                }
            } else
                RunningLog.warn("AbstractDeviceServiceImpl.onDeviceUpdate 无法解析设备状态!!:" + info);
        }
        postUpdate(deviceTypes);
    }

    protected void postDeviceUpdate(List<DeviceStateNotifier> notifiers, String code) {
        for (DeviceStateNotifier notify : notifiers) {
            notify.callUpdate(code);
        }
    }

    protected void postUpdate(List<DeviceType> deviceTypeCodes) {
        for (int i = 0, size = deviceTypeCodes.size(); i < size; i++) {
            String deviceTypeCode = deviceTypeCodes.get(i).getDeviceType();
            if (this.notifiers.containsKey(deviceTypeCode)) {
                List<DeviceStateNotifier> notifiers = this.notifiers.get(deviceTypeCode);
                postDeviceUpdate(notifiers, deviceTypeCode);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onRegisterEvents(DeviceNotifyEvent event) {
        StateEventType eventTypeValue = event.getEventType();
        DeviceStateNotifier notifier = event.getMessage();
        String key = eventTypeValue.key;
        switch (eventTypeValue.type) {
            case 0://取消监听
            case 2://监听具体设备
                if (this.notifiers.containsKey(key) && this.notifiers.get(key) != null) {
                    this.notifiers.get(key).remove(notifier);
                }
                break;
            case 1://注册监听
            case 3:
                List<DeviceStateNotifier> notifiers = null;
                if (!this.notifiers.containsKey(key)) {
                    notifiers = new ArrayList<>();
                    this.notifiers.put(key, notifiers);
                } else notifiers = this.notifiers.get(key);
                if (!notifiers.contains(notifier)) notifiers.add(notifier);
                break;
            case 4://取消指定设备类型下的设备监听
                String devicetypeCode = key;
                DeviceType deviceType = DeviceBuilder.getDeviceType(devicetypeCode);
                if (deviceType != null) {
                    List<IDevice> devices = getDevicesByDeviceType(deviceType);
                    if (devices != null) {
                        for (int i = 0, size = devices.size(); i < size; i++) {
                            IDevice device = devices.get(i);
                            if (this.notifiers.containsKey(device.getDeviceCode())) {
                                this.notifiers.get(device.getDeviceCode()).clear();
                                this.notifiers.remove(device.getDeviceCode());
                            }
                        }
                    }
                }
                break;
        }
    }

    @Subscribe(sticky = true)
    public void onDeviceChange(DeviceChangeEvent changeEvent) {
        //该事件只能执行一次，内容不会互相存在
        if (changeEvent.getUpdates().size() > 0) {
            this.onDeviceUpdate(changeEvent.getUpdates());
            return;
        }
        if (changeEvent.getDevices() != null && changeEvent.getDevices().size() > 0) {
            Map<DeviceType, Map<String, IDevice>> devices = changeEvent.getDevices();
            Map<DeviceType, List<IDevice>> deviceTypeListMap = new HashMap<>();
            Iterator<DeviceType> typeKeys = devices.keySet().iterator();
            while (typeKeys.hasNext()) {
                DeviceType type = typeKeys.next();
                List<IDevice> deviceList = new ArrayList<>(devices.get(type).values());
                DeviceBuilder.sort(deviceList);
                deviceTypeListMap.put(type, deviceList);
            }
            this.devices = deviceTypeListMap;
            this.mapDevices = devices;
            /*if (deviceTypeListMap.containsKey(DeviceType.RECORDED) && deviceTypeListMap.get(DeviceType.RECORDED).size() > 0) {
                RecordEntity recordEntity = new RecordEntity();
                IDevice device = deviceTypeListMap.get(DeviceType.RECORDED).get(0);
                RecordedDeviceDto record = (RecordedDeviceDto) device.getProperty();
                recordEntity.setHost(record.getDeviceIp());
                recordEntity.setPassword(record.getPassword());
                recordEntity.setPort(record.getDevicePort());
                recordEntity.setUserName(record.getUsername());
                RecordMaker.initRecord(Integer.parseInt(record.getChannelCode()), recordEntity);
                try {
                    IRecord initialize = RecordMaker.getRecord();
                    EventBus.getDefault().postSticky(initialize);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }*/
            //通知更新
            return;
        }
    }

    @Override
    public void close(IDevice deviceInfo) {
        beforeClose(deviceInfo);
        //deviceInfo.setDeviceState(DeviceState.CLOSING);
        //转发控制指令
    }

    protected void beforeClose(IDevice deviceInfo) {
        deviceInfo.setDeviceState(DeviceState.CLOSING);
    }

    protected void beforeOpen(IDevice deviceInfo) {
        deviceInfo.setDeviceState(DeviceState.OPENNING);
    }


    @Override
    public void reverse(IDevice deviceInfo) {
        switch (deviceInfo.getDeviceState()) {
            case OPENNONET:
            case OPENED:
                this.close(deviceInfo);
                break;
            case CLOSED:
            case ERROR:
                this.open(deviceInfo);
                break;
        }
    }

    @Override
    public void setState(IDevice device, String state) {
        DeviceState state1 = DeviceState.CUSTOM;
        state1.setStateValue(state);
        setState(device, state1);
    }

}
