package com.sunmnet.mediaroom.device.controll;

import com.sunmnet.mediaroom.device.bean.MatrixConfig;
import com.sunmnet.mediaroom.device.bean.MatrixInterface;
import com.sunmnet.mediaroom.device.bean.MatrixScene;
import com.sunmnet.mediaroom.device.bean.SceneControlBean;
import com.sunmnet.mediaroom.device.controll.service.ITransformer;
import com.sunmnet.mediaroom.devicebean.new2.utils.JsonUtils;
import com.sunmnet.mediaroom.devicebean.tag.CommuTag;
import com.sunmnet.mediaroom.device.controll.service.MatrixService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MatrixServiceImpl implements MatrixService {

    ITransformer sender;
    MatrixConfig config = null;

    static class LocalScene {
        Map<String, List<String>> interfaces = new LinkedHashMap<>();
    }

    /**
     * 选定主Edid指令格式
     */
    static class LocalEdid {
        Map<String, List<String>> edid = new LinkedHashMap<>();
    }

    public MatrixServiceImpl(ITransformer sender) {
        this.sender = sender;
        EventBus.getDefault().register(this);
    }

    @Override
    public void controll(String input, String[] output) {
        LocalScene localScene = new LocalScene();
        List<String> outpus = Arrays.asList(output);
        localScene.interfaces.put(input, outpus);
        this.sender.sendMsg(CommuTag.MATRIX_CONTROL, JsonUtils.toJson(localScene));
    }

    @Override
    public void control(Map<String, List<String>> interfaces) {
        LocalScene localScene = new LocalScene();
        for (Map.Entry<String, List<String>> entry : interfaces.entrySet()) {
            List<String> outpus = new ArrayList<>(entry.getValue());
            localScene.interfaces.put(entry.getKey(), outpus);
        }
        String msg = JsonUtils.toJson(localScene);
        this.sender.sendMsg(CommuTag.MATRIX_CONTROL, msg);
    }

    @Override
    public void edidControl(Map<String, List<String>> interfaces) {
        LocalEdid localEdid = new LocalEdid();
        for (Map.Entry<String, List<String>> entry : interfaces.entrySet()) {
            List<String> inputs = new ArrayList<>(entry.getValue());
            localEdid.edid.put(entry.getKey(), inputs);
        }
        String msg = JsonUtils.toJson(localEdid);
        this.sender.sendMsg("d4", msg);
    }

    @Override
    public void controll(String input, String output) {
        controll(input, new String[]{output});
    }

    @Override
    public void controll(String sceneName) {

    }

    @Subscribe
    public void onMatrixReceiv(MatrixConfig config) {
        this.config = config;
    }

    @Override
    public MatrixConfig getMatrixConfig() {
        /*MatrixConfig matrixConfig = new MatrixConfig();
        matrixConfig.setInputInterface(getInputInterface());
        matrixConfig.setOutputInterface(getOnputInterface());
        matrixConfig.setScenes(getScenes());
        return matrixConfig;*/
        if (this.config==null) return new MatrixConfig();
        return this.config;
    }

    @Override
    public void controll(MatrixScene scene) {
        SceneControlBean controlBean = new SceneControlBean();
        controlBean.setOperativesId("2");
        List<String> scenes = new ArrayList<>();
        scenes.add(scene.sceneCode);
        controlBean.setMatrixSceneCodes(scenes);
        this.sender.sendMsg(CommuTag.RAPID_DEVICE_OPERATE, JsonUtils.toJson(controlBean));
    }


    private List<MatrixInterface> getInputInterface() {
        List<MatrixInterface> interfaces = new ArrayList<>();
        String name = "input";
        MatrixInterface matrixInterface = null;
        for (int i = 1; i <= 16; i++) {
            matrixInterface = new MatrixInterface();
            matrixInterface.inputName = name + i;
            matrixInterface.input = i + "";
            interfaces.add(matrixInterface);
        }
        return interfaces;
    }

    private List<MatrixInterface> getOnputInterface() {
        List<MatrixInterface> interfaces = new ArrayList<>();
        String name = "output";
        MatrixInterface matrixInterface = null;
        for (int i = 1; i <= 16; i++) {
            matrixInterface = new MatrixInterface();
            matrixInterface.inputName = name + i;
            matrixInterface.input = i + "";
            interfaces.add(matrixInterface);
        }
        return interfaces;
    }

    @Override
    public void release() {
        EventBus.getDefault().unregister(this);
    }
}
