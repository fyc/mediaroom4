package com.sunmnet.mediaroom.device.controll;

import com.sunmnet.mediaroom.common.BaseApplication;
import com.sunmnet.mediaroom.common.tools.JacksonUtil;
import com.sunmnet.mediaroom.common.tools.SharePrefUtil;
import com.sunmnet.mediaroom.device.bean.DeviceScene;
import com.sunmnet.mediaroom.device.bean.DeviceState;
import com.sunmnet.mediaroom.device.bean.SceneControlBean;
import com.sunmnet.mediaroom.device.controll.service.ITransformer;
import com.sunmnet.mediaroom.device.controll.service.SceneService;
import com.sunmnet.mediaroom.devicebean.new2.SpJson;
import com.sunmnet.mediaroom.devicebean.new2.utils.JsonUtils;
import com.sunmnet.mediaroom.devicebean.sp.SceneControlDto;
import com.sunmnet.mediaroom.devicebean.sp.control.ControlDto;
import com.sunmnet.mediaroom.devicebean.tag.CommuTag;
import com.sunmnet.mediaroom.util.bean.cmd.tabsp_class_scene.TabspControlSceneDto;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeviceSceneImpl implements SceneService<DeviceScene> {

    public static final String SP_DB_NAME = "sp_config_json";
    public static final String SP_CONFIG_INFO = "sp_config_info"; // 平台sp配置信息

    ITransformer sender;
    SpJson spJson;

    public DeviceSceneImpl(ITransformer sender) {
        this.sender = sender;
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public synchronized void loadScene(SpJson spJson) {
        String spJsonStr = JsonUtils.toJson(spJson);
        SharePrefUtil.saveValue(BaseApplication.getInstance().getApplicationContext(), SP_DB_NAME, SP_CONFIG_INFO, spJsonStr);
        this.scenes.clear();
        this.spJson = spJson;
        this.scenes.clear();
        Map<String, ControlDto> controlls = new HashMap<>();
        for (ControlDto controllDto : spJson.getControls()
        ) {
            controlls.put(controllDto.getControlCode(), controllDto);
        }
        for (String controllCode : spJson.getShortcutControlScenes()
        ) {
            if (controlls.containsKey(controllCode)) {
                ControlDto controllDto = controlls.get(controllCode);
                DeviceScene deviceScene = new DeviceScene(controllDto, controllDto.getControlName());
                scenes.add(deviceScene);
            }
        }
        /*List<String> controlls = spJson.getShortcutControlScenes();
        for (String controllCode : controlls
        ) {
            for (ControlDto controllDto : spJson.getControls()
            ) {
                if ()
            }
        }*/
       /* for (int i = 0, size = spJson.getSceneControls().size(); i < size; i++) {
            SceneControlDto sceneControlDto = spJson.getSceneControls().get(i);
            DeviceScene deviceScene = new DeviceScene(sceneControlDto, sceneControlDto.getName());
            scenes.add(deviceScene);
        }*/
        /*for (int i = 0, size = spJson.getControls().size(); i < size; i++) {
            ControlDto controlDto = spJson.getControls().get(i);
            DeviceScene deviceScene = new DeviceScene(controlDto, controlDto.getControlName());
            scenes.add(deviceScene);
        }*/
    }

    @Subscribe
    public void onClassroomSceneLoad3(List<TabspControlSceneDto> sceneDtos) {
        this.scenes.clear();
        for (int i = 0, size = sceneDtos.size(); i < size; i++) {
            TabspControlSceneDto sceneDto = sceneDtos.get(i);
            DeviceScene<TabspControlSceneDto> safer = new DeviceScene<>(sceneDto, sceneDto.getSceneName());
            this.scenes.add(safer);
        }
    }

    List<DeviceScene> scenes = new ArrayList<>();

    @Override
    public List<DeviceScene> getScenes() {
        if (scenes.isEmpty()) {
            loadSpFromSP();
        }
        return scenes;
    }

    /**
     * 从sharePreference加载SP到scenes
     */
    private void loadSpFromSP() {
        String spJsonStr = SharePrefUtil.getString(BaseApplication.getInstance().getApplicationContext(), SP_DB_NAME, SP_CONFIG_INFO);
        if (spJsonStr == null) return;
        this.spJson = JsonUtils.fromJson(spJsonStr, SpJson.class);
        Map<String, ControlDto> controlls = new HashMap<>();
        for (ControlDto controllDto : spJson.getControls()) {
            controlls.put(controllDto.getControlCode(), controllDto);
        }
        for (String controllCode : spJson.getShortcutControlScenes()) {
            if (controlls.containsKey(controllCode)) {
                ControlDto controllDto = controlls.get(controllCode);
                DeviceScene deviceScene = new DeviceScene(controllDto, controllDto.getControlName());
                scenes.add(deviceScene);
            }
        }
        /*List<String> controlls = spJson.getShortcutControlScenes();
        for (String controllCode : controlls
        ) {
            for (ControlDto controllDto : spJson.getControls()
            ) {
                if ()
            }
        }*/
       /* for (int i = 0, size = spJson.getSceneControls().size(); i < size; i++) {
            SceneControlDto sceneControlDto = spJson.getSceneControls().get(i);
            DeviceScene deviceScene = new DeviceScene(sceneControlDto, sceneControlDto.getName());
            scenes.add(deviceScene);
        }*/
        /*for (int i = 0, size = spJson.getControls().size(); i < size; i++) {
            ControlDto controlDto = spJson.getControls().get(i);
            DeviceScene deviceScene = new DeviceScene(controlDto, controlDto.getControlName());
            scenes.add(deviceScene);
        }*/
    }

    @Override
    public void setScene(DeviceScene deviceScene) {
        if (deviceScene.getScene() instanceof SceneControlDto) {
            SceneControlDto controlDto = (SceneControlDto) deviceScene.getScene();
            SceneControlBean controlBean = new SceneControlBean();
            //controlBean.setOperativesId(3);
            controlBean.setOperateMethod(3);
            controlBean.setControlCodes(controlDto.getControlSceneCodes());
            this.sender.sendMsg(CommuTag.RAPID_DEVICE_OPERATE, JsonUtils.toJson(controlBean));
        } else if (deviceScene.getScene() instanceof List) {

        } else if (deviceScene.getScene() instanceof ControlDto) {
            ControlDto controlDto = (ControlDto) deviceScene.getScene();
            SceneControlBean controlBean = new SceneControlBean();
            controlBean.setOperativesId("2");
            controlBean.setControlCodes(Arrays.asList(new String[]{controlDto.getControlCode()}));
            this.sender.sendMsg(CommuTag.RAPID_DEVICE_OPERATE, JsonUtils.toJson(controlBean));
        }
    }

    private void setCodes(List<String> sceneCodes) {
        List<String> strings = sceneCodes;
        SceneControlBean controlBean = new SceneControlBean();
        controlBean.setOperateMethod(3);
        controlBean.setOperativesId("ONEKEY");
        List<String> scenes = new ArrayList<>();
        for (int i = 0, size = strings.size(); i < size; i++) {
            scenes.add(strings.get(i));
        }
        controlBean.setControlCodes(scenes);
        this.sender.sendMsg(CommuTag.RAPID_DEVICE_OPERATE, JsonUtils.toJson(controlBean));
    }

    @Override
    public void setScene(String state, DeviceScene scene) {
        if (DeviceState.OPENED.getStateValue().equals(state)) {
            setCodes(this.spJson.getOneClickClass());
        } else {
            setCodes(this.spJson.getOneClickGetOutOfClass());
        }
    }

    @Override
    public void release() {
        EventBus.getDefault().unregister(this);
    }
}
