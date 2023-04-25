package com.sunmnet.mediaroom.device.controll;

import android.content.Context;

import com.sunmnet.mediaroom.common.bean.TerminalMessage;
import com.sunmnet.mediaroom.common.enums.TabspVersion;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.device.DeviceBuilder;
import com.sunmnet.mediaroom.device.bean.DeviceScene;
import com.sunmnet.mediaroom.device.bean.DeviceState;
import com.sunmnet.mediaroom.device.bean.DeviceType;
import com.sunmnet.mediaroom.device.bean.IDevice;
import com.sunmnet.mediaroom.device.bean.MatrixConfig;
import com.sunmnet.mediaroom.device.bean.MatrixScene;
import com.sunmnet.mediaroom.device.bean.OtherSetting;
import com.sunmnet.mediaroom.device.controll.service.DeviceService;
import com.sunmnet.mediaroom.device.controll.service.IClassOpt;
import com.sunmnet.mediaroom.device.controll.service.ITransformer;
import com.sunmnet.mediaroom.device.controll.service.SceneService;
import com.sunmnet.mediaroom.device.version4.DeviceControllerImpl;
import com.sunmnet.mediaroom.device.version4.ProxyTransformer;
import com.sunmnet.mediaroom.devicebean.new2.device.ai.SuDto;
import com.sunmnet.mediaroom.devicebean.tag.CommuTag;
import com.sunmnet.mediaroom.device.controll.service.LoginService;
import com.sunmnet.mediaroom.device.controll.service.MatrixService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;
import java.util.Map;

/**
 * 控制器
 */
public class Controller implements IController {
    MatrixService matrixService;
    LoginService loginService;
    DeviceService deviceService;
    SceneService sceneService;
    private static Controller self;
    Context context;
    ITransformer sender;
    IClassOpt opt;
    OtherSetting otherSetting = new OtherSetting();

    private Controller(Context context, TabspVersion tabspVersion, String serverHost, int port) {
        this.context = context.getApplicationContext();
        DeviceBuilder.init(context);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    private Controller(Context context) {
        this.context = context.getApplicationContext();
        DeviceBuilder.init(context);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    private void recycleInfo() {
        if (this.sender != null) this.sender.release();
        if (this.deviceService != null) this.deviceService.release();
        if (this.loginService != null) this.loginService.release();
        if (this.matrixService != null) this.matrixService.release();
        if (this.sceneService != null) this.sceneService.release();
    }

    /**
     * 初始化中控设置
     *
     * @param tabspVersion 面板软件版本
     * @param serverHost   中控IP
     * @param port         中控开放端口
     * @param source       连接到中控的设备编号
     */
    public void setControllCenterInfo(TabspVersion tabspVersion, String serverHost, int port, @TerminalMessage.Source int source) {
        if (sender != null) {
            recycleInfo();
        }
        switch (tabspVersion) {
            /*case VERSION_3:
                sender = new Version3Transformer(context, serverHost, port);
                this.deviceService = new Version3ControllImpl(sender);
                this.matrixService = new MatrixServiceImpl(sender);
                this.loginService = new Version3LoginServiceImpl(sender);
                break;*/
            case VERSION_4:
                sender = new ProxyTransformer(serverHost, port, source);
                this.matrixService = new MatrixServiceImpl(sender);
                this.loginService = new LoginServiceImpl(sender);
                this.deviceService = new DeviceControllerImpl(sender);
                break;
            case VERSION_3_4:

                break;
        }
        this.sceneService = new DeviceSceneImpl(sender);
    }

    public void setControllCenterInfo(TabspVersion tabspVersion, String serverHost, int port) {
        if (sender != null) {
            recycleInfo();
        }
        switch (tabspVersion) {
            /*case VERSION_3:
                sender = new Version3Transformer(context, serverHost, port);
                this.deviceService = new Version3ControllImpl(sender);
                this.matrixService = new MatrixServiceImpl(sender);
                this.loginService = new Version3LoginServiceImpl(sender);
                break;*/
            case VERSION_4:
                sender = new ProxyTransformer(serverHost, port);
                this.matrixService = new MatrixServiceImpl(sender);
                this.loginService = new LoginServiceImpl(sender);
                this.deviceService = new DeviceControllerImpl(sender);
                break;
            case VERSION_3_4:

                break;
        }
        this.sceneService = new DeviceSceneImpl(sender);

    }

    @Subscribe
    public void onSettingUpdate(OtherSetting setting) {
        if (setting.getVolumn() != null) {
            this.otherSetting.setVolumn(setting.getVolumn());
        }
    }

    @Override
    public List<IDevice> getDevices() {
        return this.deviceService.getDevices();
    }

    @Override
    public List<IDevice> getDevicesByDeviceType(DeviceType tag) {
        return this.deviceService.getDevicesByDeviceType(tag);
    }

    @Override
    public void setDeviceSettings(String name, Map<String, String> params) {
        this.deviceService.setDeviceSettings(name, params);
    }

    @Override
    public void open(IDevice device) {
        this.deviceService.open(device);
    }

    @Override
    public void open(DeviceType deviceTypeCode) {
        this.deviceService.open(deviceTypeCode);
    }

    @Override
    public void close(DeviceType deviceTypeCode) {
        this.deviceService.close(deviceTypeCode);
    }

    @Override
    public void close(IDevice device) {
        this.deviceService.close(device);
    }

    @Override
    public void reverse(IDevice device) {
        this.deviceService.reverse(device);
    }

    @Override
    public void setState(IDevice device, String state) {
        this.deviceService.setState(device, state);
    }

    @Override
    public void setState(IDevice device, DeviceState state) {
        this.deviceService.setState(device, state);
    }

    @Override
    public void setSu(SuDto su) {
        this.deviceService.setSu(su);
    }

    @Override
    public SuDto getSu() {
        return this.deviceService.getSu();
    }

    @Override
    public void doLogin(String userName, String pwd) {
        this.loginService.doLogin(userName, pwd);
    }

    @Override
    public void doLogin(String url, String userName, String pwd, String classroomCode) {
        this.loginService.doLogin(url, userName, pwd, classroomCode);
    }

    @Override
    public void controll(String input, String[] output) {
        this.matrixService.controll(input, output);
    }

    @Override
    public void controll(String input, String output) {
        this.matrixService.controll(input, output);
    }

    @Override
    public void controll(String sceneName) {
        this.matrixService.controll(sceneName);
    }

    @Override
    public MatrixConfig getMatrixConfig() {
        return this.matrixService.getMatrixConfig();
    }

    @Override
    public void controll(MatrixScene scene) {
        this.matrixService.controll(scene);
    }

    @Override
    public void control(Map<String, List<String>> interfaces) {
        this.matrixService.control(interfaces);
    }

    @Override
    public void edidControl(Map<String, List<String>> interfaces) {
        this.matrixService.edidControl(interfaces);
    }

    public static Controller init(Context context) {
        if (self == null) {
            self = new Controller(context);
        }
        return self;
    }

    public synchronized static Controller getInstance() {
        if (self == null) throw new RuntimeException("you must be call Controller.init() first");
        return self;
    }

    @Override
    public boolean isReady() {
        return this.sender != null && this.sender.isReady();
    }

    @Override
    public void release() {
        if (this.deviceService != null) this.deviceService.release();
        if (this.loginService != null) this.loginService.release();
        if (this.matrixService != null) this.matrixService.release();
        if (this.sender != null) this.sender.release();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean sendMsg(String message) {
        return sender.sendMsg(message);
    }

    @Override
    public boolean sendMsg(String topic, String message) {
        return sender.sendMsg(topic, message);
    }

    @Override
    public List<DeviceScene> getScenes() {
        return sceneService.getScenes();
    }

    @Override
    public void setScene(DeviceScene deviceScene) {
        sceneService.setScene(deviceScene);
    }

    @Override
    public void setScene(String name, DeviceScene scene) {
        sceneService.setScene(name, scene);
    }

    @Override
    public void classOn() {
        this.sceneService.setScene(DeviceState.OPENED.getStateValue(), null);
    }

    @Override
    public void classOver() {
        this.sceneService.setScene(DeviceState.CLOSED.getStateValue(), null);
    }

    @Override
    public void setVolumn(String volumn) {
        String string = "{\"mode\": \"1\",\"aas\":\"\",\"value\": \"" + volumn + "\"}";
        sender.sendMsg(CommuTag.DEVICE_VOLUME, string);
    }

    @Override
    public void query(String topic, String params) {
        query(new String[]{topic}, new String[]{params});
    }

    @Override
    public void query(String[] topic, String[] params) {
        if (topic.length != params.length) {
            RunningLog.error("query 参数长度不一致！！");
            return;
        }
        if (sender == null) {
            RunningLog.error("发送器尚未初始化！");
            return;
        }
        for (int i = 0; i < topic.length; i++) {
            String top = topic[i];
            String param = params[i];
            sender.sendMsg(top, param);
        }
    }
}
