package com.sunmnet.mediaroom.device.version4;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sunmnet.mediaroom.common.BaseApplication;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.SharePrefUtil;
import com.sunmnet.mediaroom.device.AbstractDeviceServiceImpl;
import com.sunmnet.mediaroom.device.CustomDeviceIcon;
import com.sunmnet.mediaroom.device.DeviceBuilder;
import com.sunmnet.mediaroom.device.DeviceTag;
import com.sunmnet.mediaroom.device.bean.Airconditioner;
import com.sunmnet.mediaroom.device.bean.ControlBean;
import com.sunmnet.mediaroom.device.bean.DeviceState;
import com.sunmnet.mediaroom.device.bean.DeviceType;
import com.sunmnet.mediaroom.device.bean.Dimmer;
import com.sunmnet.mediaroom.device.bean.EnvirmentDevice;
import com.sunmnet.mediaroom.device.bean.FreshAir;
import com.sunmnet.mediaroom.device.bean.IDevice;
import com.sunmnet.mediaroom.device.bean.protocol.DeviceStateJson;
import com.sunmnet.mediaroom.device.controll.service.ITransformer;
import com.sunmnet.mediaroom.device.events.DeviceLoadedEvent;
import com.sunmnet.mediaroom.device.events.StateChangeEvent;
import com.sunmnet.mediaroom.devicebean.new2.BaseDeviceDto;
import com.sunmnet.mediaroom.devicebean.new2.DeviceJson;
import com.sunmnet.mediaroom.devicebean.new2.device.iot.MasterDeviceDto;
import com.sunmnet.mediaroom.devicebean.new2.device.iot.ZigebeeDto;
import com.sunmnet.mediaroom.devicebean.new2.template.ITemplate;
import com.sunmnet.mediaroom.devicebean.new2.utils.JsonUtils;
import com.sunmnet.mediaroom.devicebean.tag.CommuTag;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DeviceControllerImpl extends AbstractDeviceServiceImpl {
    public static final String DEVICE_DB_NAME = "device_config_json";
    public static final String DEVICE_CONFIG_INFO = "device_config_info"; // 平台设备配置信息

    ITransformer sender;

    public DeviceControllerImpl(ITransformer sender) {
        this.sender = sender;
        EventBus.getDefault().register(this);
    }

    @Override
    protected <T> void notifyProperty(T t) {
        EventBus.getDefault().post(t);
    }

    @Override
    public void setDeviceSettings(String name, Map<String, String> params) {
        JsonObject controll = new JsonObject();
        controll.addProperty("operateMethod", 3);
        JsonArray jsonArray = new JsonArray();
        JsonObject object = new JsonObject();
        if (params != null) {
            Iterator<Map.Entry<String, String>> entry = params.entrySet().iterator();
            while (entry.hasNext()) {
                Map.Entry<String, String> values = entry.next();
                object.addProperty(values.getKey(), values.getValue());
            }
        }
        jsonArray.add(object);
        controll.add(name, jsonArray);
        String req = controll.toString();
        this.sender.sendMsg(CommuTag.DEVICE_SETTINGS, req);
    }

    @Override
    public void open(IDevice deviceInfo) {
        deviceInfo.setDeviceState(DeviceState.OPENNING);
        doControll(deviceInfo, DeviceState.OPENED.getStateValue());
    }

    @Override
    public void close(IDevice deviceInfo) {
        deviceInfo.setDeviceState(DeviceState.CLOSING);
        doControll(deviceInfo, DeviceState.CLOSED.getStateValue());
    }

    private void doControll(IDevice iDeviceInfo, String state) {
        this.doControll(iDeviceInfo, state, CommuTag.DEVICE_OPERATE);
    }

    private void doControll(IDevice iDeviceInfo, String state, String commuTag) {
        ControlBean.OperateDevicesBean operateDevicesBean = new ControlBean.OperateDevicesBean();
        if (iDeviceInfo.getDeviceCode() != null)
            operateDevicesBean.setDeviceCode(iDeviceInfo.getDeviceCode());
        operateDevicesBean.setDeviceType(iDeviceInfo.getDeviceTypeCode());
        operateDevicesBean.setControl(state);
        ControlBean controlBean = new ControlBean();
        controlBean.setOperateMethod(2);
        controlBean.setOperateDevices(Arrays.asList(new ControlBean.OperateDevicesBean[]{operateDevicesBean}));
        this.sender.sendMsg(commuTag, JsonUtils.toJson(controlBean));
    }

    private void doControll(DeviceType deviceType, String state) {
        this.doControll(deviceType, state, CommuTag.DEVICE_OPERATE);
    }

    private void doControll(DeviceType deviceType, String state, String deviceOperate) {
        ControlBean.OperateDevicesBean operateDevicesBean = new ControlBean.OperateDevicesBean();
        operateDevicesBean.setDeviceType(deviceType.getDeviceType());
        operateDevicesBean.setControl(state);
        ControlBean controlBean = new ControlBean();
        controlBean.setOperateMethod(3);
        controlBean.setOperateDevices(Arrays.asList(new ControlBean.OperateDevicesBean[]{operateDevicesBean}));
        this.sender.sendMsg(deviceOperate, JsonUtils.toJson(controlBean));
    }

    private void sendMessage(String message) {
        if (this.sender != null) this.sender.sendMsg(message);
    }

    @Override
    public void open(DeviceType deviceTypeCode) {
        super.open(deviceTypeCode);
        controllAll(deviceTypeCode, DeviceState.OPENED);
    }

    private void controllAll(DeviceType deviceType, DeviceState targetstate) {
        this.doControll(deviceType, targetstate.getStateValue());
    }

    @Override
    public void close(DeviceType deviceTypeCode) {
        super.close(deviceTypeCode);
        controllAll(deviceTypeCode, DeviceState.CLOSED);
    }

    @Override
    public void setState(IDevice device, String state) {
        RunningLog.run("设置空调：" + device.getDeviceName() + "," + state);
        this.doControll(device, state);
    }

    @Override
    public void setState(IDevice iDevice, DeviceState state) {

    }

    @Override
    public void release() {
        EventBus.getDefault().unregister(this);
    }

    private void onStateChange(DeviceType deviceType, String deviceCode, DeviceStateJson stateJson) {
        if (this.mapDevices.containsKey(deviceType)) {
            Map<String, IDevice> devs = this.mapDevices.get(deviceType);
            if (devs.containsKey(deviceCode)) {
                IDevice device = devs.get(deviceCode);
                if (device instanceof Airconditioner) {
                    ((Airconditioner) device).setSetting(stateJson.getAirConditionerState());
                } else if (device instanceof FreshAir) {
                    ((FreshAir) device).setMode(stateJson.getFreshairState());
                } else if (device instanceof Dimmer) {
                    ((Dimmer) device).setState(stateJson.getState(), stateJson.getStateValue());
                } else {
                    device.setDeviceState(DeviceBuilder.getDeviceStateByState(stateJson.getState()));
                }
            }
        }
    }

    @Subscribe
    public void onDeviceUpdate(StateChangeEvent stateChangeEvent) {
        List<DeviceStateJson> updates = stateChangeEvent.deviceStateJsons;
        List<DeviceType> deviceTypes = new ArrayList<>();
        for (int i = 0, size = updates.size(); i < size; i++) {
            DeviceStateJson status = updates.get(i);
            DeviceType deviceType = DeviceBuilder.getDeviceType(status.getDeviceType());
            if (deviceType != null) {
                String deviceCode = status.getDeviceCode();
                //setState(deviceType, deviceCode, status.getState());
                onStateChange(deviceType, deviceCode, status);
                if (this.notifiers.containsKey(deviceCode)) {
                    postDeviceUpdate(this.notifiers.get(deviceCode), deviceCode);
                }
                if (!deviceTypes.contains(deviceType)) deviceTypes.add(deviceType);
            } else if (DeviceTag.DEVICE_AIRQUALITY.equals(status.getDeviceType())) {
                EnvirmentDevice device = new EnvirmentDevice();
                device.setTempture("温      度：" + status.getTemperature() + "℃");
                device.setAqi(" A Q I ：" + status.getAqi() + "ug/m³");
                device.setPm(" P M2.5 ：" + status.getPm25() + "ug/m³");
                device.setHourAqi(" A Q I ：" + status.getHourAqi() + " " + airquality(status.getHourAqi()));
                device.setHumidity("湿      度：" + status.getHumidity() + "%RH");
                EventBus.getDefault().post(device);
            } else
                RunningLog.warn("AbstractDeviceServiceImpl.onDeviceUpdate 无法解析设备状态!!:" + status.getDeviceName());
        }
        postUpdate(deviceTypes);

    }

    private String airquality(String state) {
        if (state != null && !state.equals("")) {
            try {
                int value = Integer.parseInt(state);
                if (value <= 1000) {
                    return BaseApplication.getInstance().getString(com.sunmnet.mediaroom.common.R.string.excellent);
                } else if (value > 1000 && value <= 2000) {
                    return BaseApplication.getInstance().getString(com.sunmnet.mediaroom.common.R.string.good);
                } else if (value > 2000 && value <= 5000) {
                    return BaseApplication.getInstance().getString(com.sunmnet.mediaroom.common.R.string.satisfactory);
                } else {
                    return BaseApplication.getInstance().getString(com.sunmnet.mediaroom.common.R.string.fail);
                }
            } catch (Exception e) {
                RunningLog.error(e);
            }
        }
        return "";
    }

    @Override
    public List<IDevice> getDevicesByDeviceType(DeviceType tag) {
        if (super.devices.isEmpty()) {
            loadDeiceFromSP();
        }
        if (super.devices.containsKey(tag)) {
            return super.devices.get(tag);
        }
        return null;
    }

    /**
     * 从SharePreference加载设备配置信息
     */
    private void loadDeiceFromSP() {
        String deviceJsonStr = SharePrefUtil.getString(BaseApplication.getInstance().getApplicationContext(), DEVICE_DB_NAME, DEVICE_CONFIG_INFO);
        if (deviceJsonStr == null) return;
        DeviceJson deviceJson = JsonUtils.fromJson(deviceJsonStr, DeviceJson.class);
        super.mapDevices = new HashMap<>();
        super.devices = new HashMap<>();
        Map<String, ZigebeeDto> zigebeeDtoMap = deviceJson.getZigebeeDevices();
        Iterator<ZigebeeDto> iterator = zigebeeDtoMap.values().iterator();
        while (iterator.hasNext()) {
            List<MasterDeviceDto> masters = iterator.next().getMasterDevices();
            for (int i = 0, size = masters.size(); i < size; i++) {
                onLoadDevice(masters.get(i));
            }
        }
        //更新自定义设备类型图标
        Map<String, String> iconParams = deviceJson.getIconParams();
        CustomDeviceIcon defaultIcon = DeviceBuilder.getCustomDeviceIcon("");
        if (iconParams != null) {
            List<DeviceType> modifiedType = new LinkedList<>();
            for (Map.Entry<String, String> entry : iconParams.entrySet()) {
                DeviceType customType = DeviceType.getCustomType(entry.getKey());
                CustomDeviceIcon customDeviceIcon = DeviceBuilder.getCustomDeviceIcon(entry.getValue());
                if (customType != null) {
                    customType.setOpenIcon(customDeviceIcon.getOpenIconResId());
                    customType.setCloseIcon(customDeviceIcon.getCloseIconResId());
                    customType.setMenuSelectedIconResId(customDeviceIcon.getMenuSelectedResId());
                    customType.setMenuUnselectedIconResId(customDeviceIcon.getMenuUnselectedResId());
                } else {
                    customType = new DeviceType(entry.getKey(), customDeviceIcon.getOpenIconResId(), customDeviceIcon.getCloseIconResId());
                    customType.setMenuSelectedIconResId(customDeviceIcon.getMenuSelectedResId());
                    customType.setMenuUnselectedIconResId(customDeviceIcon.getMenuUnselectedResId());
                    DeviceType.updateCustomType(customType);
                }
                modifiedType.add(customType);
            }
            List<DeviceType> resetTypeList = new ArrayList<>(DeviceType.getCustomTypeList());
            resetTypeList.removeAll(modifiedType);
            for (DeviceType deviceType : resetTypeList) {
                deviceType.setOpenIcon(defaultIcon.getOpenIconResId());
                deviceType.setCloseIcon(defaultIcon.getCloseIconResId());
                deviceType.setMenuSelectedIconResId(defaultIcon.getMenuSelectedResId());
                deviceType.setMenuUnselectedIconResId(defaultIcon.getMenuUnselectedResId());
            }
        } else {
            List<DeviceType> customTypeList = DeviceType.getCustomTypeList();
            for (DeviceType deviceType : customTypeList) {
                deviceType.setOpenIcon(defaultIcon.getOpenIconResId());
                deviceType.setCloseIcon(defaultIcon.getCloseIconResId());
                deviceType.setMenuSelectedIconResId(defaultIcon.getMenuSelectedResId());
                deviceType.setMenuUnselectedIconResId(defaultIcon.getMenuUnselectedResId());
            }
        }

        for (int i = 0, size = deviceJson.getDevices().size(); i < size; i++) {
            onLoadDevice(deviceJson.getDevices().get(i));
        }
        Map<String, List<ITemplate>> templates = deviceJson.getTemplates();
        for (List<ITemplate> templates1 : templates.values()) {
            for (int i = 0, size = templates1.size(); i < size; i++) {
                BaseDeviceDto deviceDto = (BaseDeviceDto) templates1.get(i);
                onLoadDevice(deviceDto);
            }
        }
        setSu(deviceJson.getSu());;
        /*DeviceType type = DeviceBuilder.getDeviceType(com.sunmnet.mediaroom.devicebean.enums.DeviceType.RECORDED_BROAD_CAST);
        if (mapDevices.containsKey(type)) {
            Map<String, IDevice> devices = mapDevices.get(type);
            List<IDevice> recordDevices = new ArrayList<>(devices.values());
            if (recordDevices!=null&&recordDevices.size()>0){
                IDevice dev=recordDevices.get(0);
                BaseDeviceDto recordedBroadCastDto= (BaseDeviceDto) dev.getProperty();
                EventBus.getDefault().post(recordedBroadCastDto);
            }
        }*/
        EventBus.getDefault().postSticky(new DeviceLoadedEvent());
    }

    @Subscribe
    public synchronized void onLoadDevice(DeviceJson deviceJson) {
        String deviceJsonStr = JsonUtils.toJson(deviceJson);
        SharePrefUtil.saveValue(BaseApplication.getInstance().getApplicationContext(), DEVICE_DB_NAME, DEVICE_CONFIG_INFO, deviceJsonStr);
        mapDevices = new HashMap<>();
        devices = new HashMap<>();
        Map<String, ZigebeeDto> zigebeeDtoMap = deviceJson.getZigebeeDevices();
        Iterator<ZigebeeDto> iterator = zigebeeDtoMap.values().iterator();
        while (iterator.hasNext()) {
            List<MasterDeviceDto> masters = iterator.next().getMasterDevices();
            for (int i = 0, size = masters.size(); i < size; i++) {
                onLoadDevice(masters.get(i));
            }
        }
        //更新自定义设备类型图标
        Map<String, String> iconParams = deviceJson.getIconParams();
        CustomDeviceIcon defaultIcon = DeviceBuilder.getCustomDeviceIcon("");
        if (iconParams != null) {
            List<DeviceType> modifiedType = new LinkedList<>();
            for (Map.Entry<String, String> entry : iconParams.entrySet()) {
                DeviceType customType = DeviceType.getCustomType(entry.getKey());
                CustomDeviceIcon customDeviceIcon = DeviceBuilder.getCustomDeviceIcon(entry.getValue());
                if (customType != null) {
                    customType.setOpenIcon(customDeviceIcon.getOpenIconResId());
                    customType.setCloseIcon(customDeviceIcon.getCloseIconResId());
                    customType.setMenuSelectedIconResId(customDeviceIcon.getMenuSelectedResId());
                    customType.setMenuUnselectedIconResId(customDeviceIcon.getMenuUnselectedResId());
                } else {
                    customType = new DeviceType(entry.getKey(), customDeviceIcon.getOpenIconResId(), customDeviceIcon.getCloseIconResId());
                    customType.setMenuSelectedIconResId(customDeviceIcon.getMenuSelectedResId());
                    customType.setMenuUnselectedIconResId(customDeviceIcon.getMenuUnselectedResId());
                    DeviceType.updateCustomType(customType);
                }
                modifiedType.add(customType);
            }
            List<DeviceType> resetTypeList = new ArrayList<>(DeviceType.getCustomTypeList());
            resetTypeList.removeAll(modifiedType);
            for (DeviceType deviceType : resetTypeList) {
                deviceType.setOpenIcon(defaultIcon.getOpenIconResId());
                deviceType.setCloseIcon(defaultIcon.getCloseIconResId());
                deviceType.setMenuSelectedIconResId(defaultIcon.getMenuSelectedResId());
                deviceType.setMenuUnselectedIconResId(defaultIcon.getMenuUnselectedResId());
            }
        } else {
            List<DeviceType> customTypeList = DeviceType.getCustomTypeList();
            for (DeviceType deviceType : customTypeList) {
                deviceType.setOpenIcon(defaultIcon.getOpenIconResId());
                deviceType.setCloseIcon(defaultIcon.getCloseIconResId());
                deviceType.setMenuSelectedIconResId(defaultIcon.getMenuSelectedResId());
                deviceType.setMenuUnselectedIconResId(defaultIcon.getMenuUnselectedResId());
            }
        }

        for (int i = 0, size = deviceJson.getDevices().size(); i < size; i++) {
            onLoadDevice(deviceJson.getDevices().get(i));
        }
        Map<String, List<ITemplate>> templates = deviceJson.getTemplates();
        for (List<ITemplate> templates1 : templates.values()) {
            for (int i = 0, size = templates1.size(); i < size; i++) {
                BaseDeviceDto deviceDto = (BaseDeviceDto) templates1.get(i);
                onLoadDevice(deviceDto);
            }
        }
        setSu(deviceJson.getSu());;
        /*DeviceType type = DeviceBuilder.getDeviceType(com.sunmnet.mediaroom.devicebean.enums.DeviceType.RECORDED_BROAD_CAST);
        if (mapDevices.containsKey(type)) {
            Map<String, IDevice> devices = mapDevices.get(type);
            List<IDevice> recordDevices = new ArrayList<>(devices.values());
            if (recordDevices!=null&&recordDevices.size()>0){
                IDevice dev=recordDevices.get(0);
                BaseDeviceDto recordedBroadCastDto= (BaseDeviceDto) dev.getProperty();
                EventBus.getDefault().post(recordedBroadCastDto);
            }
        }*/
        EventBus.getDefault().postSticky(new DeviceLoadedEvent());
    }

    private void onLoadDevice(BaseDeviceDto device) {
        IDevice device1 = DeviceBuilder.match(device, this.mapDevices);
        if (device1 != null) {
            List<IDevice> deviceList = null;
            if (devices.containsKey(device1.getDeviceType())) {
                deviceList = this.devices.get(device1.getDeviceType());
            } else {
                deviceList = new ArrayList<>();
                this.devices.put(device1.getDeviceType(), deviceList);
            }
            if (!deviceList.contains(device1)) {
                deviceList.add(device1);
            }
        }
    }

}
