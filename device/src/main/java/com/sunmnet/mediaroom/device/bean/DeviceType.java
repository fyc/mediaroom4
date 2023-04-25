package com.sunmnet.mediaroom.device.bean;

import android.support.v4.util.ObjectsCompat;

import com.sunmnet.mediaroom.device.DeviceBuilder;
import com.sunmnet.mediaroom.device.DeviceTag;
import com.sunmnet.mediaroom.device.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeviceType {
    public static List<DeviceType> vals = new ArrayList<>();
    public static final DeviceType PC, LIGHT, CURTAIN, STUPC, MATRIX,
            AIRCONDITIONER, FAN, DIMMER, PROJECTOR,
            SCREEN, INTERACTIVE, SOUND, DOOR, AIRQUALITY,
            FRESHAIR, HYGROTHERMOGRAPH, INTERECEPTACLE,
            RECORDED, ILLUMINANCE, ROSTRUMLOCKER, REMOTEINTERACTION, WIRELESS,
            SMARTTV;

    static List<DeviceType> customTypeList = new ArrayList<>();
    static Map<String, DeviceType> customTypeMap = new HashMap<>();

    static {
        PC = new DeviceType(DeviceTag.DEVICE_PC, R.drawable.mediaroom4_pc_open, R.drawable.mediaroom4_pc_off);
        vals.add(PC);
        LIGHT = new DeviceType(DeviceTag.DEVICE_LIGHT, R.drawable.mediaroom4_light_open, R.drawable.mediaroom4_light_close);
        vals.add(LIGHT);
        CURTAIN = new DeviceType(DeviceTag.DEVICE_CURTAIN, R.drawable.mediaroom4_curtain_open, R.drawable.mediaroom4_curtain_close);
        vals.add(CURTAIN);
        STUPC = new DeviceType(DeviceTag.DEVICE_STUPC, R.drawable.mediaroom4_pc_open, R.drawable.mediaroom4_pc_off);
        vals.add(STUPC);
        AIRCONDITIONER = new DeviceType(DeviceTag.DEVICE_AIRCONDITIONER);
        vals.add(AIRCONDITIONER);
        /*AIRCONDITIONER = new DeviceType("AIRCONDITIONER", R.drawable.aircondtioner_selected, R.drawable.airconditioner_unselect);
        vals.add(AIRCONDITIONER);*/
        FAN = new DeviceType(DeviceTag.DEVICE_FAN, R.drawable.mediaroom4_fan_open, R.drawable.mediaroom4_fan_open);
        vals.add(FAN);
        DIMMER = new DeviceType(DeviceTag.DEVICE_DIMMER, R.drawable.mediaroom4_light_open, R.drawable.mediaroom4_light_close);
        vals.add(DIMMER);
        PROJECTOR = new DeviceType(DeviceTag.DEVICE_PROJECTOR, R.drawable.mediaroom4_projector_open, R.drawable.mediaroom4_projector_off);
        vals.add(PROJECTOR);
        SCREEN = new DeviceType(DeviceTag.DEVICE_SCREEN, R.drawable.mediaroom4_screen_on, R.drawable.mediaroom4_screen_close);
        vals.add(SCREEN);
        INTERACTIVE = new DeviceType(DeviceTag.DEVICE_INTERACTIVE, R.drawable.mediaroom4_interactive_open, R.drawable.mediaroom4_interactive_off);
        vals.add(INTERACTIVE);
        SOUND = new DeviceType(DeviceTag.DEVICE_SOUND, R.drawable.mediaroom4_sound_open, R.drawable.mediaroom4_sound_off);
        vals.add(SOUND);
        DOOR = new DeviceType(DeviceTag.DEVICE_DOORLOCKER);
        vals.add(DOOR);
        FRESHAIR = new DeviceType(DeviceTag.DEVICE_FRESHAIR);
        vals.add(FRESHAIR);
        INTERECEPTACLE = new DeviceType(DeviceTag.DEVICE_INTERECEPTACLE);
        vals.add(INTERECEPTACLE);
        REMOTEINTERACTION = new DeviceType(com.sunmnet.mediaroom.devicebean.new2.enums.DeviceType.REMOTEINTERACTION.getCode());
        WIRELESS = new DeviceType(com.sunmnet.mediaroom.devicebean.new2.enums.DeviceType.WIRELESSDISPLAY.getCode());
        vals.add(WIRELESS);
        /*PC = new DeviceType("PC", R.drawable.pc_opened, R.drawable.pc_close);
        vals.add(PC);
        LIGHT = new DeviceType("LIGHT", R.drawable.light_on, R.drawable.light_off);
        vals.add(LIGHT);
        CURTAIN = new DeviceType("CURTAIN", R.drawable.curtain_open, R.drawable.curtain_close);
        vals.add(CURTAIN);
        STUPC = new DeviceType("STUPC", R.drawable.pc_opened, R.drawable.pc_close, R.drawable.stupc_exception, R.drawable.pc_close, R.drawable.pc_opened, R.drawable.pc_opened);
        vals.add(STUPC);
        AIRCONDITIONER = new DeviceType("AIRCONDITIONER", R.drawable.aircondtioner_selected, R.drawable.airconditioner_unselect);
        vals.add(AIRCONDITIONER);
        FAN = new DeviceType("FAN", R.drawable.fan_fan_open, R.drawable.fan_fan_close);
        vals.add(FAN);
        DIMMER = new DeviceType("DIMMER", R.drawable.light_on, R.drawable.light_off);
        vals.add(DIMMER);
        PROJECTOR = new DeviceType("PROJECTOR", R.drawable.projector_open, R.drawable.project_close);
        vals.add(PROJECTOR);
        SCREEN = new DeviceType("SCREEN", R.drawable.screen_open, R.drawable.screen_close);
        vals.add(SCREEN);
        INTERACTIVE = new DeviceType("INTERACTIVE", R.drawable.interactive_open, R.drawable.interactice_close);
        vals.add(INTERACTIVE);
        SOUND = new DeviceType("SOUND", R.drawable.sound_open, R.drawable.sound_close);
        vals.add(SOUND);
        DOOR = new DeviceType("DOOR", R.drawable.door_open, R.drawable.door_close);
        vals.add(DOOR);
        FRESHAIR = new DeviceType("FRESHAIR", R.drawable.opened, R.drawable.closed);
        vals.add(FRESHAIR);
        INTERECEPTACLE = new DeviceType("INTERECEPTACLE", R.drawable.bak_device_selected, R.drawable.bakdevice_normal);
        vals.add(INTERECEPTACLE);*/
        RECORDED = new DeviceType(com.sunmnet.mediaroom.devicebean.enums.DeviceType.RECORDED_BROAD_CAST);
        vals.add(RECORDED);
        ILLUMINANCE = new DeviceType("ILLUMINANCE");
        vals.add(ILLUMINANCE);
        ROSTRUMLOCKER = new DeviceType("ROSTRUMLOCKER");
        vals.add(ROSTRUMLOCKER);
        MATRIX = new DeviceType("VIDEOMATRIX");
        vals.add(MATRIX);
        AIRQUALITY = new DeviceType("AIRQUALITY");
        vals.add(AIRQUALITY);
        HYGROTHERMOGRAPH = new DeviceType("HYGROTHERMOGRAPH");
        vals.add(HYGROTHERMOGRAPH);


        SMARTTV = new DeviceType(com.sunmnet.mediaroom.devicebean.new2.enums.DeviceType.SMARTTV.getCode(), R.drawable.mediaroom4_smarttv_open, R.drawable.mediaroom4_smarttv_close);
        vals.add(SMARTTV);
    }


    private String deviceType;
    private String deviceTypeName;
    private Map<DeviceState, Integer> images;

    private int menuSelectedIconResId;
    private int menuUnselectedIconResId;

    public DeviceType(String deviceTypeCode) {
        this.deviceType = deviceTypeCode;
        this.deviceTypeName = DeviceBuilder.getDeviceTypeName(deviceTypeCode);
    }

    public DeviceType(String deviceType, int open, int close) {
        this(deviceType, open, close, close, close, open, open);
    }

    public DeviceType(String deviceTypeCode, int open, int close, int error, int openning, int closing, int openNonet) {
        this(deviceTypeCode.toUpperCase());
        this.images = new HashMap<>();
        images.put(DeviceState.OPENED, open);
        images.put(DeviceState.CLOSED, close);
        images.put(DeviceState.ERROR, error);
        images.put(DeviceState.OPENNING, openning);
        images.put(DeviceState.CLOSING, closing);
        images.put(DeviceState.OPENNONET, openNonet);
    }

    public DeviceType(String deviceType, String deviceTypeName) {
        this.deviceType = deviceType;
        this.deviceTypeName = deviceTypeName;
    }

    public DeviceType(String deviceType, String deviceTypeName, int open, int close, int error, int openning, int closing, int openNonet) {
        this(deviceType.toUpperCase(), deviceTypeName);
        this.deviceTypeName = deviceTypeName;
        this.images = new HashMap<>();
        images.put(DeviceState.OPENED, open);
        images.put(DeviceState.CLOSED, close);
        images.put(DeviceState.ERROR, error);
        images.put(DeviceState.OPENNING, openning);
        images.put(DeviceState.CLOSING, closing);
        images.put(DeviceState.OPENNONET, openNonet);
    }

    public static List<DeviceType> values() {
        return vals;
    }

    public static void updateCustomType(DeviceType deviceType) {
        DeviceType localType = customTypeMap.get(deviceType.getDeviceType());
        if (localType == null) {
            customTypeList.add(deviceType);
            customTypeMap.put(deviceType.getDeviceType(), deviceType);
        } else if (!ObjectsCompat.equals(localType.getDeviceTypeName(), deviceType.getDeviceTypeName())) {
            localType.setDeviceTypeName(deviceType.getDeviceTypeName());
        }
    }

    public static DeviceType getCustomType(String deviceTypeCode) {
        return customTypeMap.get(deviceTypeCode);
    }

    public static List<DeviceType> getCustomTypeList() {
        return customTypeList;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public String getDeviceTypeName() {
        return deviceTypeName;
    }

    public void setDeviceTypeName(String deviceTypeName) {
        this.deviceTypeName = deviceTypeName;
    }

    public void setOpenIcon(int resId) {
        if (this.images == null) {
            this.images = new HashMap<>();
        }
        images.put(DeviceState.OPENED, resId);
        images.put(DeviceState.OPENNING, resId);
        images.put(DeviceState.OPENNONET, resId);
    }

    public void setCloseIcon(int resId) {
        if (this.images == null) {
            this.images = new HashMap<>();
        }
        images.put(DeviceState.CLOSED, resId);
        images.put(DeviceState.ERROR, resId);
        images.put(DeviceState.CLOSING, resId);
    }

    public int getDeviceDrawableByState(DeviceState state) {
        if (this.images != null && images.containsKey(state)) {
            return images.get(state);
        } else if (images.containsKey(DeviceState.ERROR)) {
            return images.get(DeviceState.ERROR);
        }
        return 0;
    }

    public int getMenuSelectedIconResId() {
        return menuSelectedIconResId;
    }

    public void setMenuSelectedIconResId(int menuSelectedIconResId) {
        this.menuSelectedIconResId = menuSelectedIconResId;
    }

    public int getMenuUnselectedIconResId() {
        return menuUnselectedIconResId;
    }

    public void setMenuUnselectedIconResId(int menuUnselectedIconResId) {
        this.menuUnselectedIconResId = menuUnselectedIconResId;
    }
}
