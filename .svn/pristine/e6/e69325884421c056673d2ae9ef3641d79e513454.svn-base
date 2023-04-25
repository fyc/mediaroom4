package com.sunmnet.mediaroom.device.bean;


import android.support.annotation.IntDef;
import android.text.TextUtils;

import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.devicebean.new2.BaseDeviceDto;
import com.sunmnet.mediaroom.devicebean.new2.components.Component;
import com.sunmnet.mediaroom.devicebean.new2.components.IComponent;
import com.sunmnet.mediaroom.devicebean.new2.components.ctrl.FunctionCodeParam;
import com.sunmnet.mediaroom.devicebean.new2.components.param.VideoSourceParam;
import com.sunmnet.mediaroom.devicebean.new2.enums.ParamType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CustomDevice extends Device {

    /**
     * 功能码
     */
    private List<ComponentItem> functionList = new ArrayList<>();

    /**
     * 视频源
     */
    private List<ComponentItem> signalSourceList = new ArrayList<>();

    public CustomDevice(DeviceType deviceType, String deviceName, String deviceCode) {
        super(deviceType, deviceName, deviceCode);
    }

    @Override
    public void setProperty(BaseDeviceDto baseDeviceDto) {
        super.setProperty(baseDeviceDto);
        Map<String, IComponent> components = baseDeviceDto.getComponents();
        functionList.clear();
        signalSourceList.clear();
        for (Map.Entry<String, IComponent> entry : components.entrySet()) {
            IComponent value = entry.getValue();
            String key = entry.getKey();
            RunningLog.debug("CustomDevice Component(key=" + key + " ,value=" + value + ")");
            if (TextUtils.isEmpty(key) || value == null) {
                RunningLog.run("Component数据有误 (key=" + key + " ,value=" + value + ")");
                continue;
            }
            if (ParamType.VIDEO_SOURCE_PARAM.getCode().equals(key) && value instanceof VideoSourceParam) {
                Set<Map.Entry<String, String>> entries = ((VideoSourceParam) value).getVideoSourceMap().entrySet();
                for (Map.Entry<String, String> sourceEntry : entries) {
                    String sourceKey = sourceEntry.getKey();
                    String code = sourceEntry.getValue();
                    String name = ((VideoSourceParam) value).getVideoSourceNameMap().get(sourceKey);
                    ComponentItem componentItem = new ComponentItem();
                    componentItem.setCode(code);
                    componentItem.setName(name);
                    componentItem.setId(sourceKey);
                    componentItem.setType(ComponentItem.TYPE_VIDEO_SOURCE);
                    signalSourceList.add(componentItem);
                }
            } else if (ParamType.FUNCTION_CODE_PARAM.getCode().equals(key) && value instanceof FunctionCodeParam) {
//                String startCode = ((FunctionCodeParam) value).getStartCode();
//                if (!TextUtils.isEmpty(startCode)) {
//                    ComponentItem componentItem = new ComponentItem();
//                    componentItem.setCode(startCode);
//                    componentItem.setName("开机");
//                    componentItem.setId("START");
//                    componentItem.setType(ComponentItem.TYPE_FUNCTION);
//                    functionList.add(componentItem);
//                }
//                String stopCode = ((FunctionCodeParam) value).getStopCode();
//                if (!TextUtils.isEmpty(stopCode)) {
//                    ComponentItem componentItem = new ComponentItem();
//                    componentItem.setCode(stopCode);
//                    componentItem.setName("关机");
//                    componentItem.setId("STOP");
//                    componentItem.setType(ComponentItem.TYPE_FUNCTION);
//                    functionList.add(componentItem);
//                }
                String soundAddCode = ((FunctionCodeParam) value).getSoundAddCode();
                if (!TextUtils.isEmpty(soundAddCode)) {
                    ComponentItem componentItem = new ComponentItem();
                    componentItem.setCode(soundAddCode);
                    componentItem.setName("音量+");
                    componentItem.setId("VOL_UP");
                    componentItem.setType(ComponentItem.TYPE_FUNCTION);
                    functionList.add(componentItem);
                }
                String soundSubCode = ((FunctionCodeParam) value).getSoundSubCode();
                if (!TextUtils.isEmpty(soundSubCode)) {
                    ComponentItem componentItem = new ComponentItem();
                    componentItem.setCode(soundSubCode);
                    componentItem.setName("音量-");
                    componentItem.setId("VOL_DOWN");
                    componentItem.setType(ComponentItem.TYPE_FUNCTION);
                    functionList.add(componentItem);
                }
                String searchCode = ((FunctionCodeParam) value).getSearchCode();
                if (!TextUtils.isEmpty(searchCode)) {
                    ComponentItem componentItem = new ComponentItem();
                    componentItem.setCode(searchCode);
                    componentItem.setName("搜索");
                    componentItem.setId("SEARCH");
                    componentItem.setType(ComponentItem.TYPE_FUNCTION);
                    functionList.add(componentItem);
                }
            } else if (key.startsWith(ParamType.CUSTOMIZE_PARAM.getCode()) && value instanceof Component) {
                Integer componentType = ((Component) value).getComponentType();
                ComponentItem componentItem = new ComponentItem();
                componentItem.setId(value.getComponentCode());
                componentItem.setName(((Component) value).getComponentName());
                Object componentValue = ((Component) value).getValue();
                if (componentValue != null) {
                    componentItem.setCode(componentValue.toString());
                }
                if (componentType == ComponentItem.TYPE_FUNCTION) {
                    componentItem.setType(ComponentItem.TYPE_FUNCTION);
                    functionList.add(componentItem);
                }
                // 自定义配置的视频源，在VideoSourceParam里也能取到，这边有重复数据，先暂时注释掉
//                else if (componentType == ComponentItem.TYPE_VIDEO_SOURCE) {
//                    componentItem.setType(ComponentItem.TYPE_VIDEO_SOURCE);
//                    signalSourceList.add(componentItem);
//                }
            }
        }
    }

    public List<ComponentItem> getFunctionList() {
        return functionList;
    }

    public List<ComponentItem> getSignalSourceList() {
        return signalSourceList;
    }

    /**
     * 功能按钮
     */
    public static class ComponentItem {

        @IntDef({TYPE_FUNCTION, TYPE_VIDEO_SOURCE})
        @Retention(RetentionPolicy.SOURCE)
        @interface Type {
        }

        public static final int TYPE_FUNCTION = 1;
        public static final int TYPE_VIDEO_SOURCE = 2;
        private String id;
        private String name;
        private String code;
        @Type
        private int type;//类型 1：功能按钮， 2：视频源

        public ComponentItem() {
        }

        public ComponentItem(String id, String name, String code, @Type int type) {
            this.id = id;
            this.name = name;
            this.code = code;
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public int getType() {
            return type;
        }

        public void setType(@Type int type) {
            this.type = type;
        }
    }
}
