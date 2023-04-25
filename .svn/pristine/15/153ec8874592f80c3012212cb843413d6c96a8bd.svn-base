package com.sunmnet.mediaroom.device;

import android.content.Context;
import android.support.v4.util.ObjectsCompat;

import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.device.bean.Airconditioner;
import com.sunmnet.mediaroom.device.bean.CustomDevice;
import com.sunmnet.mediaroom.device.bean.Device;
import com.sunmnet.mediaroom.device.bean.DeviceState;
import com.sunmnet.mediaroom.device.bean.DeviceType;
import com.sunmnet.mediaroom.device.bean.FreshAir;
import com.sunmnet.mediaroom.device.bean.IDevice;
import com.sunmnet.mediaroom.device.bean.Interactive;
import com.sunmnet.mediaroom.device.bean.Dimmer;
import com.sunmnet.mediaroom.devicebean.new2.components.param.VideoSourceParam;
import com.sunmnet.mediaroom.util.JsonUtils;
import com.sunmnet.mediaroom.util.bean.BaseDeviceDto;
import com.sunmnet.mediaroom.util.bean.device.DeviceDto;
import com.sunmnet.mediaroom.util.bean.device.MasterDeviceDto;
import com.sunmnet.mediaroom.util.bean.device.PaDeviceDto;
import com.sunmnet.mediaroom.util.bean.device.PaDto;
import com.sunmnet.mediaroom.util.bean.device.ZigebeeDto;

import java.text.Collator;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DeviceBuilder {
    static Map<String, DeviceType> deviceTypeMap;
    static Map<String, DeviceState> stats;
    static Map<String, String> deviceTypeNames;
    static Map<String, Integer> deviceTypeResource;
    static Map<String, CustomDeviceIcon> customDeviceTypeIcon;

    public static void init(Context ctx) {
        deviceTypeMap = new HashMap<>();
        stats = new HashMap<>();
        deviceTypeNames = new HashMap<>();
        initTypeName(ctx);
        for (DeviceType type : DeviceType.values()
        ) {
            deviceTypeMap.put(type.getDeviceType(), type);
        }
        for (DeviceState stat : DeviceState.values()
        ) {
            stats.put(stat.getStateValue(), stat);
        }
        deviceTypeResource = getDeviceTypes();
        customDeviceTypeIcon = getCustomDeviceIconMap();
    }

    public static void initTypeName(Context context) {
        Map<String, Integer> types = getDeviceTypes();
        Iterator<Map.Entry<String, Integer>> iterator = types.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> values = iterator.next();
            String deviceTypeName = context.getString(values.getValue());
            if (deviceTypeName == null)
                throw new RuntimeException("DeviceBuilder.initTypeName 无法加载设备名称!!");
            deviceTypeNames.put(values.getKey(), deviceTypeName);
        }
    }

    public static String getDeviceTypeName(String deviceTypeCode) {
        if (deviceTypeNames.containsKey(deviceTypeCode)) return deviceTypeNames.get(deviceTypeCode);
        return null;
    }

    public static Integer getDeviceTypeNameResource(String deviceTypeCode) {
        if (deviceTypeResource.containsKey(deviceTypeCode))
            return deviceTypeResource.get(deviceTypeCode);
        else return 0;
    }

    private static Map<String, CustomDeviceIcon> getCustomDeviceIconMap() {
        Map<String, CustomDeviceIcon> iconMap = new HashMap<>();
        iconMap.put("iconjiaohuping", new CustomDeviceIcon(R.drawable.mediaroom4_custom_open_interactive, R.drawable.mediaroom4_custom_close_interactive, R.drawable.mediaroom4_custom_open_interactive, R.drawable.mediaroom4_custom_menu_unselected_interactive));
        iconMap.put("icongongfang", new CustomDeviceIcon(R.drawable.mediaroom4_custom_open_sound, R.drawable.mediaroom4_custom_close_sound, R.drawable.mediaroom4_custom_open_sound, R.drawable.mediaroom4_custom_menu_unselected_sound));
        iconMap.put("icondengguang1", new CustomDeviceIcon(R.drawable.mediaroom4_custom_open_light, R.drawable.mediaroom4_custom_close_light, R.drawable.mediaroom4_custom_open_light, R.drawable.mediaroom4_custom_menu_unselected_light));
        iconMap.put("iconfengshan", new CustomDeviceIcon(R.drawable.mediaroom4_custom_open_fan, R.drawable.mediaroom4_custom_close_fan, R.drawable.mediaroom4_custom_open_fan, R.drawable.mediaroom4_custom_menu_unselected_fan));
        iconMap.put("iconchuanglian", new CustomDeviceIcon(R.drawable.mediaroom4_custom_open_curtain, R.drawable.mediaroom4_custom_close_curtain, R.drawable.mediaroom4_custom_open_curtain, R.drawable.mediaroom4_custom_menu_unselected_curtain));
        iconMap.put("iconjiangtaidiannao", new CustomDeviceIcon(R.drawable.mediaroom4_custom_open_pc, R.drawable.mediaroom4_custom_close_pc, R.drawable.mediaroom4_custom_open_pc, R.drawable.mediaroom4_custom_menu_unselected_pc));
        iconMap.put("iconmubu1", new CustomDeviceIcon(R.drawable.mediaroom4_custom_open_screen, R.drawable.mediaroom4_custom_close_screen, R.drawable.mediaroom4_custom_open_screen, R.drawable.mediaroom4_custom_menu_unselected_screen));
        iconMap.put("icontouyingji", new CustomDeviceIcon(R.drawable.mediaroom4_custom_open_projector, R.drawable.mediaroom4_custom_close_projector, R.drawable.mediaroom4_custom_open_projector, R.drawable.mediaroom4_custom_menu_unselected_projector));
        iconMap.put("icontongyongicon", new CustomDeviceIcon(R.drawable.mediaroom4_custom_open_default, R.drawable.mediaroom4_custom_close_default, R.drawable.mediaroom4_custom_open_default, R.drawable.mediaroom4_custom_menu_unselected_default));
        iconMap.put("iconxinfengji", new CustomDeviceIcon(R.drawable.mediaroom4_custom_open_freshair, R.drawable.mediaroom4_custom_close_freshair, R.drawable.mediaroom4_custom_open_freshair, R.drawable.mediaroom4_custom_menu_unselected_freshair));
        iconMap.put("iconbanpai1", new CustomDeviceIcon(R.drawable.mediaroom4_custom_open_classbrand, R.drawable.mediaroom4_custom_close_classbrand, R.drawable.mediaroom4_custom_open_classbrand, R.drawable.mediaroom4_custom_menu_unselected_classbrand));
        iconMap.put("iconmensuo", new CustomDeviceIcon(R.drawable.mediaroom4_custom_open_doorlock, R.drawable.mediaroom4_custom_close_doorlock, R.drawable.mediaroom4_custom_open_doorlock, R.drawable.mediaroom4_custom_menu_unselected_doorlock));
        iconMap.put("iconchuankoufuwuqi1", new CustomDeviceIcon(R.drawable.mediaroom4_custom_open_serial_server, R.drawable.mediaroom4_custom_close_serial_server, R.drawable.mediaroom4_custom_open_serial_server, R.drawable.mediaroom4_custom_menu_unselected_serial_server));
        iconMap.put("iconzhongyangkongtiao", new CustomDeviceIcon(R.drawable.mediaroom4_custom_open_airconditioner, R.drawable.mediaroom4_custom_close_airconditioner, R.drawable.mediaroom4_custom_open_airconditioner, R.drawable.mediaroom4_custom_menu_unselected_airconditioner));
        iconMap.put("iconziyuan25", new CustomDeviceIcon(R.drawable.mediaroom4_custom_open_jack, R.drawable.mediaroom4_custom_close_jack, R.drawable.mediaroom4_custom_open_jack, R.drawable.mediaroom4_custom_menu_unselected_jack));
        iconMap.put("iconicon_kongkai", new CustomDeviceIcon(R.drawable.mediaroom4_custom_open_circuit_breaker, R.drawable.mediaroom4_custom_close_circuit_breaker, R.drawable.mediaroom4_custom_open_circuit_breaker, R.drawable.mediaroom4_custom_menu_unselected_circuit_breaker));
        iconMap.put("iconziyuan36", new CustomDeviceIcon(R.drawable.mediaroom4_custom_open_airconditioner2, R.drawable.mediaroom4_custom_close_airconditioner2, R.drawable.mediaroom4_custom_open_airconditioner2, R.drawable.mediaroom4_custom_menu_unselected_airconditioner2));
        return iconMap;
    }

    public static CustomDeviceIcon getCustomDeviceIcon(String iconId) {
        if (customDeviceTypeIcon.containsKey(iconId)) {
            return customDeviceTypeIcon.get(iconId);
        } else {
            return customDeviceTypeIcon.get("icontongyongicon");
        }
    }

    private static Map<String, Integer> getDeviceTypes() {
        Map<String, Integer> deviceTypeResource = new HashMap<>();
        deviceTypeResource.put("PC", R.string.name_pc);
        deviceTypeResource.put("LIGHT", R.string.name_light);
        deviceTypeResource.put("CURTAIN", R.string.name_curtain);
        deviceTypeResource.put("STUPC", R.string.name_stupc);
        deviceTypeResource.put("VIDEOMATRIX", R.string.name_matrix);
        deviceTypeResource.put("MATRIX", R.string.name_matrix);
        deviceTypeResource.put(DeviceTag.DEVICE_MATRIX, R.string.name_matrix);
        deviceTypeResource.put(com.sunmnet.mediaroom.devicebean.enums.DeviceType.RECORDED_BROAD_CAST, R.string.name_recorded);
        deviceTypeResource.put("RECORD", R.string.name_recorded);
        deviceTypeResource.put("FAN", R.string.name_fan);
        deviceTypeResource.put(com.sunmnet.mediaroom.devicebean.enums.DeviceType.AIR_CONDITIONER, R.string.name_airconditioner);
        deviceTypeResource.put("AIRCONDITIONER", R.string.name_airconditioner);
        deviceTypeResource.put("DIMMER", R.string.name_dimmer);
        deviceTypeResource.put("PROJECTOR", R.string.name_projector);
        deviceTypeResource.put("SCREEN", R.string.name_screen);
        deviceTypeResource.put("INTERACTIVE", R.string.name_interactive);
        deviceTypeResource.put("FRESHAIR", R.string.name_freshair);
        deviceTypeResource.put("SOUND", R.string.name_sound);
        deviceTypeResource.put("DOOR", R.string.name_door);
        deviceTypeResource.put("AIRQUALITY", R.string.name_airquality);
        deviceTypeResource.put("HYGROTHERMOGRAPH", R.string.name_hygrothernograph);
        deviceTypeResource.put("INTERECEPTACLE", R.string.name_interceptacle);
        deviceTypeResource.put("ILLUMINANCE", R.string.name_ILLUMINANCE);
        deviceTypeResource.put("ROSTRUMLOCKER", R.string.name_ROSTRUMLOCKER);
        deviceTypeResource.put("WIRELESSDISPLAY", R.string.name_wireless);
        deviceTypeResource.put("SMARTTV", R.string.name_smarttv);
        return deviceTypeResource;
    }

    public static DeviceState getDeviceStateByState(String state) {
        if (stats.containsKey(state)) return stats.get(state);
        else if (state.equals("6")) {
            return DeviceState.CLOSED;
        } else return DeviceState.CUSTOM;
    }

    public static DeviceType getDeviceType(String deviceTypeCode) {
        if ("MATRIX".equals(deviceTypeCode)) {
            deviceTypeCode = "VIDEOMATRIX";
        } else if ("RECORD".equals(deviceTypeCode)) {
            deviceTypeCode = com.sunmnet.mediaroom.devicebean.enums.DeviceType.RECORDED_BROAD_CAST;
        } else if ("AIR_CONDITIONER".equals(deviceTypeCode)) {
            deviceTypeCode = com.sunmnet.mediaroom.devicebean.enums.DeviceType.AIR_CONDITIONER;
        }
        if (deviceTypeMap.containsKey(deviceTypeCode)) {
            return deviceTypeMap.get(deviceTypeCode);
        } else {
            return DeviceType.getCustomType(deviceTypeCode);
        }
    }

    public static IDevice buildDevice(BaseDeviceDto deviceDto, DeviceType type) {
        IDevice device = null;
        if (deviceDto != null) {
            device = new Device(type, deviceDto.getDeviceName(), deviceDto.getDeviceCode());
            device.setProperty(deviceDto);
            if (stats.containsKey(deviceDto.getDeviceState())) {
                device.setDeviceState(stats.get(deviceDto.getDeviceState()));
            } else {//默认状态
                device.setDeviceState(DeviceState.ERROR);
            }
        } else
            RunningLog.warn("com.sunmnet.mediaroom.device.version3.DeviceBuilder 无法处理对象-->" + JsonUtils.objectToJson(deviceDto));
        return device;
    }

    public static Map<DeviceType, Map<String, IDevice>> buildDevices(DeviceDto deviceDto) {
        Map<DeviceType, Map<String, IDevice>> devices = new HashMap<>();
        match(deviceDto.getStupcs(), devices);
        for (int i = 0; i < deviceDto.getPas().size(); i++) {
            PaDto pa = deviceDto.getPas().get(i);
            match(pa.getPaDevice(), devices);
        }
        match(devices, deviceDto.getZigebees());
        match(deviceDto.getBakDevices(), devices);
        match(deviceDto.getRostrumLocker(), devices);
        if (deviceDto.getOther() != null) {
            match(deviceDto.getOther().getFreshairs(), devices);
            match(deviceDto.getOther().getMatrixDevices(), devices);
            match(deviceDto.getOther().getRecordedDevices(), devices);
        }
        return devices;
    }

    public static void sort(List<IDevice> sorts) {
        Collections.sort(sorts, new Comparator<IDevice>() {
            Collator cmp = Collator.getInstance(java.util.Locale.CHINA);

            @Override
            public int compare(IDevice o1, IDevice o2) {
                BaseDeviceDto device1 = (BaseDeviceDto) o1.getProperty();
                BaseDeviceDto device2 = (BaseDeviceDto) o2.getProperty();
                if (device1.getDeviceSign() != null && device2.getDeviceSign() != null) {
                    if (cmp.compare(device1.getDeviceSign(), device2.getDeviceSign()) > 0) {
                        return 1;
                    } else if (cmp.compare(device1.getDeviceSign(), device2.getDeviceSign()) < 0) {
                        return -1;
                    }
                }
                return 0;
            }
        });
    }


    public static void match(List<? extends BaseDeviceDto> devices, Map<DeviceType, Map<String, IDevice>> deviceContainer) {
        if (devices == null || devices.size() <= 0) return;
        Map<String, IDevice> deviceList = null;
        for (int i = 0; i < devices.size(); i++) {
            BaseDeviceDto deviceDto = devices.get(i);
            String deviceTypeCode = deviceDto.getDeviceTypeCode();
            if (deviceTypeMap.containsKey(deviceTypeCode)) {
                DeviceType deviceType = deviceTypeMap.get(deviceTypeCode);
                if (deviceContainer.containsKey(deviceType)) {
                    deviceList = deviceContainer.get(deviceType);
                } else {
                    deviceList = new LinkedHashMap<>();
                    deviceContainer.put(deviceType, deviceList);
                }
                IDevice device = null;
                if (DeviceTag.DEVICE_AIRCONDITIONER.equals(deviceTypeCode)) {
                    device = new Airconditioner(deviceType, deviceDto.getDeviceName(), deviceDto.getDeviceCode());
                    device.setProperty(deviceDto);
                } else if (DeviceTag.DEVICE_INTERACTIVE.equals(deviceTypeCode)) {
                    device = new Interactive(deviceType, deviceDto.getDeviceName(), deviceDto.getDeviceCode());
                    device.setProperty(deviceDto);
                } else if (DeviceTag.DEVICE_FRESHAIR.equals(deviceTypeCode)) {
                    device = new FreshAir(deviceType, deviceDto.getDeviceName(), deviceDto.getDeviceCode());
                    device.setProperty(deviceDto);
                } else if (DeviceTag.DEVICE_DIMMER.equals(deviceTypeCode)) {
                    device = new Dimmer(deviceType, deviceDto.getDeviceName(), deviceDto.getDeviceCode());
                    device.setProperty(deviceDto);
                } else {
                    device = buildDevice(deviceDto, deviceType);
                }
                if (device != null) deviceList.put(deviceDto.getDeviceCode(), device);
            } else {
                RunningLog.warn("未识别的设备类型:" + deviceTypeCode);
            }
        }
    }

    public static void match(BaseDeviceDto baseDeviceDto, Map<DeviceType, Map<String, IDevice>> deviceContainer) {
        if (baseDeviceDto == null) return;
        match(Arrays.asList(new BaseDeviceDto[]{baseDeviceDto}), deviceContainer);
    }

    public static IDevice buildDevice(com.sunmnet.mediaroom.devicebean.new2.BaseDeviceDto deviceDto, DeviceType type) {
        IDevice device = null;
        if (deviceDto != null) {
            device = new Device(type, deviceDto.getDeviceName(), deviceDto.getDeviceCode());
            device.setProperty(deviceDto);
            device.setDeviceState(DeviceState.ERROR);
        } else
            RunningLog.warn("com.sunmnet.mediaroom.device.version3.DeviceBuilder 无法处理对象-->" + JsonUtils.objectToJson(deviceDto));
        return device;
    }

    public static IDevice match(com.sunmnet.mediaroom.devicebean.new2.BaseDeviceDto deviceDto, Map<DeviceType, Map<String, IDevice>> deviceContainer) {
        Map<String, IDevice> deviceList = null;
        String deviceTypeCode = deviceDto.getDeviceType();
        if (deviceTypeMap.containsKey(deviceTypeCode)) {
            DeviceType deviceType = deviceTypeMap.get(deviceTypeCode);
            if (deviceContainer.containsKey(deviceType)) {
                deviceList = deviceContainer.get(deviceType);
            } else {
                deviceList = new LinkedHashMap<>();
                deviceContainer.put(deviceType, deviceList);
            }
            IDevice device = null;
            if (DeviceTag.DEVICE_AIRCONDITIONER.equals(deviceTypeCode)) {
                device = new Airconditioner(deviceType, deviceDto.getDeviceName(), deviceDto.getDeviceCode());
                device.setProperty(deviceDto);
            } else if (DeviceTag.DEVICE_INTERACTIVE.equals(deviceTypeCode)) {
                if (deviceDto.getComponents() != null && deviceDto.getComponents().containsKey("VIDEO_SOURCE_PARAM")) {
                    VideoSourceParam param = (VideoSourceParam) deviceDto.getComponents().get("VIDEO_SOURCE_PARAM");
                    device = new Interactive(deviceType, deviceDto.getDeviceName(), deviceDto.getDeviceCode(), param);
                } else {
                    device = new Interactive(deviceType, deviceDto.getDeviceName(), deviceDto.getDeviceCode());
                }
                device.setProperty(deviceDto);
            } else if (DeviceTag.DEVICE_FRESHAIR.equals(deviceTypeCode)) {
                device = new FreshAir(deviceType, deviceDto.getDeviceName(), deviceDto.getDeviceCode());
                device.setProperty(deviceDto);
            } else if (DeviceTag.DEVICE_DIMMER.equals(deviceTypeCode)) {
                device = new Dimmer(deviceType, deviceDto.getDeviceName(), deviceDto.getDeviceCode());
                device.setProperty(deviceDto);
            } else {
                device = buildDevice(deviceDto, deviceType);
            }
            if (device != null) deviceList.put(deviceDto.getDeviceCode(), device);
            return device;
        } else if (deviceTypeCode.startsWith(DeviceTag.DEVICE_CUSTOM)) {
            RunningLog.run("自定义设备类型: deviceTypeCode=" + deviceTypeCode + ", deviceTypeName=" + deviceDto.getDeviceTypeName());
            //初始化自定义设备类型
            if (!ObjectsCompat.equals(deviceTypeNames.get(deviceTypeCode), deviceDto.getDeviceTypeName())) {
                //更新设备类型名字
                deviceTypeNames.put(deviceTypeCode, deviceDto.getDeviceTypeName());
            }
            DeviceType deviceType = DeviceType.getCustomType(deviceTypeCode);
            if (deviceType == null) {
                //创建自定义设备类型对象
                CustomDeviceIcon customDeviceIcon = getCustomDeviceIcon("");
                deviceType = new DeviceType(deviceTypeCode, customDeviceIcon.getOpenIconResId(), customDeviceIcon.getCloseIconResId());
                DeviceType.updateCustomType(deviceType);
            } else {
                //更新设备类型名字
                deviceType.setDeviceTypeName(deviceDto.getDeviceTypeName());
            }
            if (deviceContainer.containsKey(deviceType)) {
                deviceList = deviceContainer.get(deviceType);
            } else {
                deviceList = new LinkedHashMap<>();
                deviceContainer.put(deviceType, deviceList);
            }
            IDevice device = new CustomDevice(deviceType, deviceDto.getDeviceName(), deviceDto.getDeviceCode());
            device.setProperty(deviceDto);
            device.setDeviceState(DeviceState.ERROR);
            if (device != null) {
                deviceList.put(deviceDto.getDeviceCode(), device);
            }
            return device;
        } else {
            RunningLog.warn("未识别的设备类型:" + deviceTypeCode);
        }
        return null;
    }

    public static void match(PaDeviceDto paDeviceDto, Map<DeviceType, Map<String, IDevice>> deviceContainer) {
        if (paDeviceDto != null) {
            match(paDeviceDto.getPc(), deviceContainer);
            match(paDeviceDto.getProjector(), deviceContainer);
            match(paDeviceDto.getScreen(), deviceContainer);
            match(paDeviceDto.getSound(), deviceContainer);
            match(paDeviceDto.getInteractive(), deviceContainer);
            match(paDeviceDto.getBaks(), deviceContainer);
            match(paDeviceDto.getInteractives(), deviceContainer);
            match(paDeviceDto.getProjectors(), deviceContainer);
        }
    }

    public static void match(Map<DeviceType, Map<String, IDevice>> deviceContainer, List<ZigebeeDto> zees) {
        for (int i = 0; i < zees.size(); i++) {
            ZigebeeDto zigbee = zees.get(i);
            for (int j = 0; j < zigbee.getMasterDevices().size(); j++) {
                MasterDeviceDto device = zigbee.getMasterDevices().get(j);
                match(device, deviceContainer);
            }
        }
    }
}
