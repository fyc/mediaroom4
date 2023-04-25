package com.sunmnet.mediaroom.device.version3;

import android.content.Intent;
import android.content.pm.PackageInfo;

import com.sunmnet.mediaroom.device.DeviceBuilder;
import com.sunmnet.mediaroom.device.bean.DeviceType;
import com.sunmnet.mediaroom.device.bean.IDevice;
import com.sunmnet.mediaroom.device.bean.LoginEvent;
import com.sunmnet.mediaroom.device.bean.MatrixConfig;
import com.sunmnet.mediaroom.device.bean.MatrixInterface;
import com.sunmnet.mediaroom.device.bean.MatrixScene;
import com.sunmnet.mediaroom.device.bean.OtherSetting;
import com.sunmnet.mediaroom.device.controll.Controller;
import com.sunmnet.mediaroom.device.events.DeviceChangeEvent;
import com.sunmnet.mediaroom.device.events.OtherEventType;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Connector /*extends AbstractSocketClient implements ITransformer, IConnector*/ {
    //Map<DeviceType, Map<String, IDevice>> devices = new HashMap<>();
//    public Connector(String ip, Integer port) {
//        this(ip, port, true, "", null, 100000, true);
//
//    }
//
//    public Connector(String ip, Integer port, Boolean reconnect, String name, Integer reconnectTime, Boolean splitFlag) {
//        super(ip, port, reconnect, name, null, reconnectTime, splitFlag);
//
//    }
//
//    public Connector(String ip, Integer port, Boolean reconnect, String name, SocketOutputable sopt, Integer reconnectTime, Boolean splitFlag) {
//        super(ip, port, reconnect, name, sopt, reconnectTime, splitFlag);
//    }
//
//    @Override
//    protected void afterConnect() {
//        EventBus.getDefault().post(this);
//        this.send("ca");
//        ThreadUtils.execute(new Runnable() {
//            @Override
//            public void run() {
//                String[] command = new String[]{
//                        "68", "6j"
//                };
//                for (int i = 0; i < command.length; i++) {
//                    try {
//                        Thread.sleep(300);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    send(command[i]);
//                }
//            }
//        });
//    }
//
//
//    @Override
//    public void release() {
//        this.close();
//    }
//
//    @Override
//    public boolean sendMsg(String message) {
//        return sendMsg("", message);
//    }
//
//    @Override
//    public boolean sendMsg(String topic, String message) {
//        return this.send(topic + message);
//    }
//
//    DeviceChangeEvent changeEvent = new DeviceChangeEvent();
//
//    private void onDeviceStateUpdate(String update) {
//        synchronized (changeEvent) {
//            changeEvent.clear();
//            String[] values = update.split(";");
//            changeEvent.getUpdates().addAll(Arrays.asList(values));
//            EventBus.getDefault().post(changeEvent);
//        }
//    }
//
//    @Override
//    protected void receive(String str) {
//        RunningLog.run("接收到数据:" + str);
//        if (str.length() >= 2) {
//            String protocol = str.substring(0, 2);
//            int index = -1;
//            String data = null;
//            switch (protocol) {
//                case "c6":
//                    String valeu = str.substring(2);
//                    if ("1".equals(valeu)) {
//                        User user = new User();
//                        user.setLoginType(LoginType.ACCOUNT);
//                        user.setUserName("admin");
//                        user.setLoginType(LoginType.CARD);
//                        LoginEvent event = new LoginEvent();
//                        event.setEventType(EventType.ON_CLASS);
//                        event.setMessage(user);
//                        EventBus.getDefault().postSticky(event);
//                    } else {
//                        Event<User, EventType> event = new Event<>();
//                        event.setEventType(EventType.EVENT_LOCK);
//                        EventBus.getDefault().post(event);
//                    }
//                    break;
//                case "c1":
//                    index = str.indexOf(',');
//                    data = str.substring(index + 1);
//                    matchDevice(data);
//                    break;
//                case "c2":
//                    index = str.indexOf(',');
//                    data = str.substring(index + 1);
//                    matcheCourse(data);
//
//                    break;
//                case "63":
//                    data = str.substring(2, str.length());
//                    onDeviceStateUpdate(data);
//                    break;
//                case "68":
//                    String value = str.replace("68", "");
//                    OtherSetting otherSetting = new OtherSetting();
//                    otherSetting.setVgaMode(value);
//                    EventBus.getDefault().post(otherSetting);
//                    /*Event<Integer, OtherEventType> event = new Event<>();
//                    event.setEventType(OtherEventType.VGA);
//                    event.setMessage(Integer.parseInt(value));
//                    EventBus.getDefault().postSticky(event);*/
//                    break;
//                case "64":
//                    data = str.substring(2, str.length());
//                    onDeviceStateUpdate(data);
//                    break;
//                case "h1":
//                    data = str.substring(2, str.length());
//                    MatrixConfig matrixDto = formateMatrix(data);
//                    EventBus.getDefault().postSticky(matrixDto);
//                    break;
//                case "h2":
//                    data = str.substring(2, str.length());
//                    MatrixConfig matrix = formateMatrix(data);
//                    EventBus.getDefault().postSticky(matrix);
//                    break;
//                case "h3":
//                    break;
//                case "h4":
//                    data = str.substring(2, str.length());
//                    MatrixConfig ma = formateMatrix(data);
//                    EventBus.getDefault().postSticky(ma);
//                    break;
//                case "h6":
//                    data = str.substring(2, str.length());
//                    LoginEvent loginEvent = handleLoginStatus(data);
//                    if (loginEvent != null) EventBus.getDefault().post(loginEvent);
//                    break;
//
//                case "h7":
//                    data = str.substring(2, str.length());
//                    Gson gson = new Gson();
//                    List<TabspControlSceneDto> ps = gson.fromJson(data, new TypeToken<List<TabspControlSceneDto>>() {
//                    }.getType());
//                    EventBus.getDefault().post(ps);
//                    break;
//                case "c9":
//                    AppUtil appUtil = new AppUtil();
//                    PackageInfo info = appUtil.getPackageInfo(BaseApplication.getInstance());
//                    send("c9" + info.versionName);
//                    break;
//                case "6n":
//                    List<IDevice> devices = Controller.getInstance().getDevicesByDeviceType(DeviceType.AIRQUALITY);
//                    index = str.indexOf(',');
//                    data = str.substring(index + 1);
//                    if (devices != null && devices.size() >= 1) {
//                        BaseDeviceDto deviceDto = (BaseDeviceDto) devices.get(0).getProperty();
//                        deviceDto.setDeviceState(data);
//                    }
//                    break;
//                case "c4":
//                    value = str.replace("c4", "");
//                    SystemEvent event1 = new SystemEvent(SystemEventType.TIME_RESET);
//                    event1.setMessage(value);
//                    EventBus.getDefault().post(event1);
//                    break;
//                case "6j":
//                    value = str.replace("6j", "");
//                    OtherSetting s = new OtherSetting();
//                    s.setVolumn(value);
//                    EventBus.getDefault().post(s);
//                    /*Event<Integer, OtherEventType> event2 = new Event<>();
//                    event2.setEventType(OtherEventType.SOUND_VOLUMN);
//                    event2.setMessage(Integer.parseInt(value));
//                    EventBus.getDefault().postSticky(event2);*/
//                    break;
//                default:
//                    RunningLog.warn("未识别的数据：" + str);
//                    break;
//            }
//        } else {
//            RunningLog.warn("无法从  " + str + "  中获取协议头，长度不足!!");
//        }
//    }
//
//    private LoginEvent handleLoginStatus(String data) {
//        TabspLoginCheckDto checkDto = (TabspLoginCheckDto) JsonUtils.jsonToBean(data, TabspLoginCheckDto.class);
//        EventBus.getDefault().post(checkDto);
//        //LoginEvent loginEvent = new LoginEvent(false, "");
//        return null;
//    }
//
//    private List<MatrixInterface> makeInterface(Map<String, String> maps) {
//        Iterator<Map.Entry<String, String>> iterator = maps.entrySet().iterator();
//        List<MatrixInterface> interfaces = new ArrayList<>();
//        while (iterator.hasNext()) {
//            Map.Entry<String, String> values = iterator.next();
//            MatrixInterface inter = new MatrixInterface();
//            inter.input = values.getKey();
//            inter.inputName = values.getValue();
//            interfaces.add(inter);
//        }
//        return interfaces;
//    }
//
//    private MatrixConfig formateMatrix(String jsonStr) {
//        TabspMatrixDto tabspMatrixDto = (TabspMatrixDto) JsonUtils.jsonToBean(jsonStr, TabspMatrixDto.class);
//        MatrixConfig config = new MatrixConfig();
//        if (tabspMatrixDto.getInputMap() != null) {
//            config.setInputInterface(makeInterface(tabspMatrixDto.getInputMap()));
//        }
//        if (tabspMatrixDto.getOutputMap() != null) {
//            config.setOutputInterface(makeInterface(tabspMatrixDto.getOutputMap()));
//        }
//        if (tabspMatrixDto.getScenes() != null) {
//            int size = tabspMatrixDto.getScenes().size();
//            List<MatrixScene> scenes = new ArrayList<>();
//            config.setScenes(scenes);
//            for (int i = 0; i < size; i++) {
//                TabspMatrixSceneDto sceneDto = tabspMatrixDto.getScenes().get(i);
//                MatrixScene scene = new MatrixScene();
//                scene.sceneName = sceneDto.getSceneName();
//                scene.sceneMap = sceneDto.getInterfaces();
//                scenes.add(scene);
//            }
//        }
//        return config;
//    }
//
//    private void matchDevice(String deviceJson) {
//        try {
//            synchronized (changeEvent) {
//                changeEvent.clear();
//                DeviceDto deviceDto = (DeviceDto) JsonUtils.jsonToBean(deviceJson, DeviceDto.class);
//                Map<DeviceType, Map<String, IDevice>> devices = DeviceBuilder.buildDevices(deviceDto);
//                if (devices != null && devices.size() > 0) {
//                    changeEvent.setDevices(devices);
//                    EventBus.getDefault().postSticky(changeEvent);
//                } else {
//                    EventBus.getDefault().post(new TipesEvent("无法处理设备列表!!!"));
//                }
//            }
//        } catch (Exception e) {
//            RunningLog.error(e);
//        }
//    }
//
//    private void matcheCourse(String deviceJson) {
//        try {
//            CourseDto courseDto = (CourseDto) JsonUtils.jsonToBean(deviceJson, CourseDto.class);
//            CourseHelper.getDefault().setCourse(courseDto);
//            Intent intent = new Intent(BaseApplication.getInstance(), BellService.class);
//            BaseApplication.getInstance().startService(intent);
//            // EventBus.getDefault().post(courseDto);
//        } catch (Exception e) {
//            RunningLog.error(e);
//        }
//    }
}
