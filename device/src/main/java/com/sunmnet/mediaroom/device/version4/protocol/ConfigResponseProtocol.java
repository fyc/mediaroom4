package com.sunmnet.mediaroom.device.version4.protocol;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.sunmnet.mediaroom.common.bean.SocketMessage;
import com.sunmnet.mediaroom.common.bean.SocketResult;
import com.sunmnet.mediaroom.common.interfaces.ProtocolHandler;
import com.sunmnet.mediaroom.common.tools.JacksonUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.device.bean.MatrixConfig;
import com.sunmnet.mediaroom.device.bean.MatrixInterface;
import com.sunmnet.mediaroom.device.bean.MatrixScene;
import com.sunmnet.mediaroom.devicebean.new2.DeviceJson;
import com.sunmnet.mediaroom.devicebean.new2.SpJson;
import com.sunmnet.mediaroom.devicebean.new2.utils.JsonUtils;
import com.sunmnet.mediaroom.devicebean.sp.matrix.MatrixDto;
import com.sunmnet.mediaroom.devicebean.sp.matrix.MatrixSceneDto;
import com.sunmnet.mediaroom.devicebean.tag.CommuTag;
import com.sunmnet.mediaroom.util.bean.course.CourseDto;

import org.apache.mina.core.session.IoSession;
import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 向核心请求配置信息返回结果
 */
public class ConfigResponseProtocol implements ProtocolHandler<SocketMessage, String> {

    @Override
    public synchronized SocketResult<String> handle(IoSession session, SocketMessage message) {
        RunningLog.run("收到设备配置信息");
        Map<String, String> map = JacksonUtil.jsonStrToMap_SS(message.getMsg());
        if (map == null) {
            RunningLog.run("设备配置信息解析有误: " + message.getMsg());
            return SocketResult.fail();
        }
        String jsonType = map.get("type");
        String json = map.get("json");
        RunningLog.run("设备配置信息类型：" + jsonType);
        if (!TextUtils.isEmpty(jsonType) && !TextUtils.isEmpty(json)) {
            if (jsonType.toLowerCase().equals("device")) {
                DeviceJson deviceJson = JsonUtils.fromJson(json, DeviceJson.class);
                EventBus.getDefault().post(deviceJson);
            } else if (jsonType.toLowerCase().equals("course")) {
                CourseDto deviceJson = JSON.parseObject(json, CourseDto.class);
                EventBus.getDefault().post(deviceJson);
            } else if (jsonType.toLowerCase().equals("sp")) {
                //String cache="{\"key\":\"81\",\"msg\":\"{\\\"type\\\":\\\"SP\\\",\\\"json\\\":\\\"{\\\\\\\"su\\\\\\\":{\\\\\\\"components\\\\\\\":{}},\\\\\\\"spRule\\\\\\\":{\\\\\\\"minInterval\\\\\\\":\\\\\\\"1\\\\\\\",\\\\\\\"model\\\\\\\":\\\\\\\"0\\\\\\\",\\\\\\\"doorModel\\\\\\\":\\\\\\\"0\\\\\\\",\\\\\\\"rule\\\\\\\":\\\\\\\"2\\\\\\\",\\\\\\\"preEmpower\\\\\\\":\\\\\\\"5\\\\\\\",\\\\\\\"delayEmpower\\\\\\\":\\\\\\\"12\\\\\\\",\\\\\\\"doorPreEmpower\\\\\\\":\\\\\\\"15\\\\\\\",\\\\\\\"doorDelayEmpower\\\\\\\":\\\\\\\"15\\\\\\\"},\\\\\\\"spAutos\\\\\\\":[],\\\\\\\"sceneControls\\\\\\\":[],\\\\\\\"shortcutControlScenes\\\\\\\":[\\\\\\\"6d82609c6fb549d984a44efd8bba49af\\\\\\\",\\\\\\\"49edb8f2f63a4323968e9db9ceb6530a\\\\\\\"],\\\\\\\"matrix\\\\\\\":{\\\\\\\"sound\\\\\\\":\\\\\\\"null\\\\\\\",\\\\\\\"scenes\\\\\\\":[{\\\\\\\"matrixSceneCode\\\\\\\":\\\\\\\"609fcc86c12d4515bbe2529821918e7e\\\\\\\",\\\\\\\"sceneName\\\\\\\":\\\\\\\"电脑输出到所有\\\\\\\",\\\\\\\"interfaces\\\\\\\":{\\\\\\\"1\\\\\\\":[1,2,3,4,5,6,7,8],\\\\\\\"2\\\\\\\":[],\\\\\\\"3\\\\\\\":[],\\\\\\\"4\\\\\\\":[],\\\\\\\"5\\\\\\\":[],\\\\\\\"6\\\\\\\":[],\\\\\\\"7\\\\\\\":[],\\\\\\\"8\\\\\\\":[]}},{\\\\\\\"matrixSceneCode\\\\\\\":\\\\\\\"7d032c1fdf4d41df8f6edb34d810b67c\\\\\\\",\\\\\\\"sceneName\\\\\\\":\\\\\\\"无线投屏输入到所有\\\\\\\",\\\\\\\"interfaces\\\\\\\":{\\\\\\\"1\\\\\\\":[3],\\\\\\\"2\\\\\\\":[1,2,4,5,6,7,8],\\\\\\\"3\\\\\\\":[],\\\\\\\"4\\\\\\\":[],\\\\\\\"5\\\\\\\":[],\\\\\\\"6\\\\\\\":[],\\\\\\\"7\\\\\\\":[],\\\\\\\"8\\\\\\\":[]}},{\\\\\\\"matrixSceneCode\\\\\\\":\\\\\\\"c4ee2a71ba694d8fa0da076e8a269380\\\\\\\",\\\\\\\"sceneName\\\\\\\":\\\\\\\"705\\\\\\\",\\\\\\\"interfaces\\\\\\\":{\\\\\\\"1\\\\\\\":[],\\\\\\\"2\\\\\\\":[],\\\\\\\"3\\\\\\\":[],\\\\\\\"4\\\\\\\":[],\\\\\\\"5\\\\\\\":[4],\\\\\\\"6\\\\\\\":[],\\\\\\\"7\\\\\\\":[],\\\\\\\"8\\\\\\\":[]}}],\\\\\\\"inputMap\\\\\\\":{\\\\\\\"1\\\\\\\":\\\\\\\"输入口1\\\\\\\",\\\\\\\"2\\\\\\\":\\\\\\\"输入口2\\\\\\\",\\\\\\\"3\\\\\\\":\\\\\\\"输入口3\\\\\\\",\\\\\\\"4\\\\\\\":\\\\\\\"输入口4\\\\\\\",\\\\\\\"5\\\\\\\":\\\\\\\"输入口5\\\\\\\",\\\\\\\"6\\\\\\\":\\\\\\\"输入口6\\\\\\\",\\\\\\\"7\\\\\\\":\\\\\\\"输入口7\\\\\\\",\\\\\\\"8\\\\\\\":\\\\\\\"输入口8\\\\\\\"},\\\\\\\"outputMap\\\\\\\":{\\\\\\\"1\\\\\\\":\\\\\\\"输出口1\\\\\\\",\\\\\\\"2\\\\\\\":\\\\\\\"输出口2\\\\\\\",\\\\\\\"3\\\\\\\":\\\\\\\"输出口3\\\\\\\",\\\\\\\"4\\\\\\\":\\\\\\\"输出口4\\\\\\\",\\\\\\\"5\\\\\\\":\\\\\\\"输出口5\\\\\\\",\\\\\\\"6\\\\\\\":\\\\\\\"输出口6\\\\\\\",\\\\\\\"7\\\\\\\":\\\\\\\"输出口7\\\\\\\",\\\\\\\"8\\\\\\\":\\\\\\\"输出口8\\\\\\\"}},\\\\\\\"conditions\\\\\\\":[{\\\\\\\"conditionCode\\\\\\\":\\\\\\\"48ff8f925eef4221aa631228fdf5c804\\\\\\\",\\\\\\\"conditionName\\\\\\\":\\\\\\\"刷卡开机\\\\\\\",\\\\\\\"conditionType\\\\\\\":\\\\\\\"DEVICE_CONTROL\\\\\\\",\\\\\\\"relative\\\\\\\":\\\\\\\"2\\\\\\\",\\\\\\\"courseCondition\\\\\\\":{},\\\\\\\"deviceControlCodes\\\\\\\":[{\\\\\\\"deviceType\\\\\\\":\\\\\\\"PROJECTOR\\\\\\\",\\\\\\\"code\\\\\\\":\\\\\\\"1\\\\\\\",\\\\\\\"delaySeconds\\\\\\\":\\\\\\\"0\\\\\\\"},{\\\\\\\"deviceType\\\\\\\":\\\\\\\"SCREEN\\\\\\\",\\\\\\\"code\\\\\\\":\\\\\\\"1\\\\\\\",\\\\\\\"delaySeconds\\\\\\\":\\\\\\\"0\\\\\\\"},{\\\\\\\"deviceType\\\\\\\":\\\\\\\"INTERACTIVE\\\\\\\",\\\\\\\"code\\\\\\\":\\\\\\\"1\\\\\\\",\\\\\\\"delaySeconds\\\\\\\":\\\\\\\"0\\\\\\\",\\\\\\\"videoSourceParam\\\\\\\":\\\\\\\"\\\\\\\"},{\\\\\\\"deviceType\\\\\\\":\\\\\\\"PC\\\\\\\",\\\\\\\"code\\\\\\\":\\\\\\\"1\\\\\\\",\\\\\\\"delaySeconds\\\\\\\":\\\\\\\"0\\\\\\\"}]},{\\\\\\\"conditionCode\\\\\\\":\\\\\\\"6b7c4467363449b5bc584dd8238f9d4b\\\\\\\",\\\\\\\"conditionName\\\\\\\":\\\\\\\"刷卡关机\\\\\\\",\\\\\\\"conditionType\\\\\\\":\\\\\\\"DEVICE_CONTROL\\\\\\\",\\\\\\\"relative\\\\\\\":\\\\\\\"2\\\\\\\",\\\\\\\"courseCondition\\\\\\\":{},\\\\\\\"deviceControlCodes\\\\\\\":[{\\\\\\\"deviceType\\\\\\\":\\\\\\\"PROJECTOR\\\\\\\",\\\\\\\"code\\\\\\\":\\\\\\\"0\\\\\\\",\\\\\\\"delaySeconds\\\\\\\":\\\\\\\"0\\\\\\\"},{\\\\\\\"deviceType\\\\\\\":\\\\\\\"SCREEN\\\\\\\",\\\\\\\"code\\\\\\\":\\\\\\\"0\\\\\\\",\\\\\\\"delaySeconds\\\\\\\":\\\\\\\"0\\\\\\\"},{\\\\\\\"deviceType\\\\\\\":\\\\\\\"INTERACTIVE\\\\\\\",\\\\\\\"code\\\\\\\":\\\\\\\"0\\\\\\\",\\\\\\\"delaySeconds\\\\\\\":\\\\\\\"0\\\\\\\",\\\\\\\"videoSourceParam\\\\\\\":\\\\\\\"\\\\\\\"},{\\\\\\\"deviceType\\\\\\\":\\\\\\\"PC\\\\\\\",\\\\\\\"code\\\\\\\":\\\\\\\"0\\\\\\\",\\\\\\\"delaySeconds\\\\\\\":\\\\\\\"0\\\\\\\"}]},{\\\\\\\"conditionCode\\\\\\\":\\\\\\\"92c9b4221d5a484cb25e767abd9f991f\\\\\\\",\\\\\\\"conditionName\\\\\\\":\\\\\\\"刷卡\\\\\\\",\\\\\\\"conditionType\\\\\\\":\\\\\\\"OTHER_TYPE\\\\\\\",\\\\\\\"relative\\\\\\\":\\\\\\\"2\\\\\\\",\\\\\\\"courseCondition\\\\\\\":{},\\\\\\\"deviceControlCodes\\\\\\\":[],\\\\\\\"otherType\\\\\\\":{\\\\\\\"type\\\\\\\":0,\\\\\\\"role\\\\\\\":\\\\\\\"01\\\\\\\",\\\\\\\"thisClass\\\\\\\":\\\\\\\"0\\\\\\\"}},{\\\\\\\"conditionCode\\\\\\\":\\\\\\\"cbd307940542415fa914cff7a28d541f\\\\\\\",\\\\\\\"conditionName\\\\\\\":\\\\\\\"面板登录\\\\\\\",\\\\\\\"conditionType\\\\\\\":\\\\\\\"OTHER_TYPE\\\\\\\",\\\\\\\"relative\\\\\\\":\\\\\\\"2\\\\\\\",\\\\\\\"courseCondition\\\\\\\":{},\\\\\\\"deviceControlCodes\\\\\\\":[],\\\\\\\"otherType\\\\\\\":{\\\\\\\"type\\\\\\\":1,\\\\\\\"thisClass\\\\\\\":\\\\\\\"0\\\\\\\"}}],\\\\\\\"controls\\\\\\\":[{\\\\\\\"controlCode\\\\\\\":\\\\\\\"6d82609c6fb549d984a44efd8bba49af\\\\\\\",\\\\\\\"controlName\\\\\\\":\\\\\\\"上课\\\\\\\",\\\\\\\"classLocation\\\\\\\":\\\\\\\"\\\\\\\",\\\\\\\"controlType\\\\\\\":\\\\\\\"DEVICECONTROL\\\\\\\",\\\\\\\"deviceControls\\\\\\\":[{\\\\\\\"deviceType\\\\\\\":\\\\\\\"PROJECTOR\\\\\\\",\\\\\\\"code\\\\\\\":\\\\\\\"1\\\\\\\",\\\\\\\"delaySeconds\\\\\\\":\\\\\\\"0\\\\\\\"},{\\\\\\\"deviceType\\\\\\\":\\\\\\\"SCREEN\\\\\\\",\\\\\\\"code\\\\\\\":\\\\\\\"1\\\\\\\",\\\\\\\"delaySeconds\\\\\\\":\\\\\\\"0\\\\\\\"},{\\\\\\\"deviceType\\\\\\\":\\\\\\\"INTERACTIVE\\\\\\\",\\\\\\\"code\\\\\\\":\\\\\\\"1\\\\\\\",\\\\\\\"delaySeconds\\\\\\\":\\\\\\\"0\\\\\\\",\\\\\\\"videoSourceParam\\\\\\\":\\\\\\\"\\\\\\\"},{\\\\\\\"deviceType\\\\\\\":\\\\\\\"PC\\\\\\\",\\\\\\\"code\\\\\\\":\\\\\\\"1\\\\\\\",\\\\\\\"delaySeconds\\\\\\\":\\\\\\\"0\\\\\\\"},{\\\\\\\"deviceType\\\\\\\":\\\\\\\"SOUND\\\\\\\",\\\\\\\"code\\\\\\\":\\\\\\\"1\\\\\\\",\\\\\\\"delaySeconds\\\\\\\":\\\\\\\"0\\\\\\\"},{\\\\\\\"deviceType\\\\\\\":\\\\\\\"LIGHT\\\\\\\",\\\\\\\"code\\\\\\\":\\\\\\\"1\\\\\\\",\\\\\\\"delaySeconds\\\\\\\":\\\\\\\"0\\\\\\\"}]},{\\\\\\\"controlCode\\\\\\\":\\\\\\\"49edb8f2f63a4323968e9db9ceb6530a\\\\\\\",\\\\\\\"controlName\\\\\\\":\\\\\\\"下课\\\\\\\",\\\\\\\"classLocation\\\\\\\":\\\\\\\"\\\\\\\",\\\\\\\"controlType\\\\\\\":\\\\\\\"DEVICECONTROL\\\\\\\",\\\\\\\"deviceControls\\\\\\\":[{\\\\\\\"deviceType\\\\\\\":\\\\\\\"PROJECTOR\\\\\\\",\\\\\\\"code\\\\\\\":\\\\\\\"0\\\\\\\",\\\\\\\"delaySeconds\\\\\\\":\\\\\\\"0\\\\\\\"},{\\\\\\\"deviceType\\\\\\\":\\\\\\\"SCREEN\\\\\\\",\\\\\\\"code\\\\\\\":\\\\\\\"0\\\\\\\",\\\\\\\"delaySeconds\\\\\\\":\\\\\\\"0\\\\\\\"},{\\\\\\\"deviceType\\\\\\\":\\\\\\\"INTERACTIVE\\\\\\\",\\\\\\\"code\\\\\\\":\\\\\\\"0\\\\\\\",\\\\\\\"delaySeconds\\\\\\\":\\\\\\\"0\\\\\\\",\\\\\\\"videoSourceParam\\\\\\\":\\\\\\\"\\\\\\\"},{\\\\\\\"deviceType\\\\\\\":\\\\\\\"PC\\\\\\\",\\\\\\\"code\\\\\\\":\\\\\\\"0\\\\\\\",\\\\\\\"delaySeconds\\\\\\\":\\\\\\\"0\\\\\\\"},{\\\\\\\"deviceType\\\\\\\":\\\\\\\"SOUND\\\\\\\",\\\\\\\"code\\\\\\\":\\\\\\\"0\\\\\\\",\\\\\\\"delaySeconds\\\\\\\":\\\\\\\"0\\\\\\\"},{\\\\\\\"deviceType\\\\\\\":\\\\\\\"LIGHT\\\\\\\",\\\\\\\"code\\\\\\\":\\\\\\\"0\\\\\\\",\\\\\\\"delaySeconds\\\\\\\":\\\\\\\"0\\\\\\\"}]},{\\\\\\\"controlCode\\\\\\\":\\\\\\\"76fcb9818e9e4e2e8a0a9b92ec022847\\\\\\\",\\\\\\\"controlName\\\\\\\":\\\\\\\"开功放\\\\\\\",\\\\\\\"classLocation\\\\\\\":\\\\\\\"\\\\\\\",\\\\\\\"controlType\\\\\\\":\\\\\\\"DEVICECONTROL\\\\\\\",\\\\\\\"deviceControls\\\\\\\":[{\\\\\\\"deviceType\\\\\\\":\\\\\\\"SOUND\\\\\\\",\\\\\\\"code\\\\\\\":\\\\\\\"1\\\\\\\",\\\\\\\"delaySeconds\\\\\\\":\\\\\\\"0\\\\\\\"}]},{\\\\\\\"controlCode\\\\\\\":\\\\\\\"629f97f14fe1443bb5aabfab618af21f\\\\\\\",\\\\\\\"controlName\\\\\\\":\\\\\\\"1\\\\\\\",\\\\\\\"classLocation\\\\\\\":\\\\\\\"\\\\\\\",\\\\\\\"controlType\\\\\\\":\\\\\\\"DEVICECONTROL\\\\\\\",\\\\\\\"deviceControls\\\\\\\":[{\\\\\\\"deviceType\\\\\\\":\\\\\\\"LIGHT\\\\\\\",\\\\\\\"code\\\\\\\":\\\\\\\"1\\\\\\\",\\\\\\\"delaySeconds\\\\\\\":\\\\\\\"0\\\\\\\"}]},{\\\\\\\"controlCode\\\\\\\":\\\\\\\"e0c403d4e3f14fccac1a9826a9815892\\\\\\\",\\\\\\\"controlName\\\\\\\":\\\\\\\"2\\\\\\\",\\\\\\\"classLocation\\\\\\\":\\\\\\\"\\\\\\\",\\\\\\\"controlType\\\\\\\":\\\\\\\"DEVICECONTROL\\\\\\\",\\\\\\\"deviceControls\\\\\\\":[{\\\\\\\"deviceType\\\\\\\":\\\\\\\"CURTAIN\\\\\\\",\\\\\\\"code\\\\\\\":\\\\\\\"1\\\\\\\",\\\\\\\"delaySeconds\\\\\\\":\\\\\\\"0\\\\\\\"}]},{\\\\\\\"controlCode\\\\\\\":\\\\\\\"d1669ce88d3447af8b2c3fbba5d2780b\\\\\\\",\\\\\\\"controlName\\\\\\\":\\\\\\\"3\\\\\\\",\\\\\\\"classLocation\\\\\\\":\\\\\\\"\\\\\\\",\\\\\\\"controlType\\\\\\\":\\\\\\\"DEVICECONTROL\\\\\\\",\\\\\\\"deviceControls\\\\\\\":[{\\\\\\\"deviceType\\\\\\\":\\\\\\\"PROJECTOR\\\\\\\",\\\\\\\"code\\\\\\\":\\\\\\\"1\\\\\\\",\\\\\\\"delaySeconds\\\\\\\":\\\\\\\"0\\\\\\\"}]},{\\\\\\\"controlCode\\\\\\\":\\\\\\\"548e600357f24123b44d9ea574383ddc\\\\\\\",\\\\\\\"controlName\\\\\\\":\\\\\\\"4\\\\\\\",\\\\\\\"classLocation\\\\\\\":\\\\\\\"\\\\\\\",\\\\\\\"controlType\\\\\\\":\\\\\\\"DEVICECONTROL\\\\\\\",\\\\\\\"deviceControls\\\\\\\":[{\\\\\\\"deviceType\\\\\\\":\\\\\\\"INTERACTIVE\\\\\\\",\\\\\\\"code\\\\\\\":\\\\\\\"1\\\\\\\",\\\\\\\"delaySeconds\\\\\\\":\\\\\\\"0\\\\\\\",\\\\\\\"videoSourceParam\\\\\\\":\\\\\\\"\\\\\\\"}]},{\\\\\\\"controlCode\\\\\\\":\\\\\\\"0f38a3e9b9864338b56feffc4467c449\\\\\\\",\\\\\\\"controlName\\\\\\\":\\\\\\\"6\\\\\\\",\\\\\\\"classLocation\\\\\\\":\\\\\\\"\\\\\\\",\\\\\\\"controlType\\\\\\\":\\\\\\\"DEVICECONTROL\\\\\\\",\\\\\\\"deviceControls\\\\\\\":[{\\\\\\\"deviceType\\\\\\\":\\\\\\\"PC\\\\\\\",\\\\\\\"code\\\\\\\":\\\\\\\"1\\\\\\\",\\\\\\\"delaySeconds\\\\\\\":\\\\\\\"0\\\\\\\"}]},{\\\\\\\"controlCode\\\\\\\":\\\\\\\"d26423c7dee44767a1c53777090ae89d\\\\\\\",\\\\\\\"controlName\\\\\\\":\\\\\\\"8\\\\\\\",\\\\\\\"classLocation\\\\\\\":\\\\\\\"\\\\\\\",\\\\\\\"controlType\\\\\\\":\\\\\\\"DEVICECONTROL\\\\\\\",\\\\\\\"deviceControls\\\\\\\":[{\\\\\\\"deviceType\\\\\\\":\\\\\\\"SOUND\\\\\\\",\\\\\\\"code\\\\\\\":\\\\\\\"1\\\\\\\",\\\\\\\"delaySeconds\\\\\\\":\\\\\\\"0\\\\\\\"}]},{\\\\\\\"controlCode\\\\\\\":\\\\\\\"d47cf0e253ea4237b13e2dbeae42de4d\\\\\\\",\\\\\\\"controlName\\\\\\\":\\\\\\\"434\\\\\\\",\\\\\\\"classLocation\\\\\\\":\\\\\\\"\\\\\\\",\\\\\\\"controlType\\\\\\\":\\\\\\\"DEVICECONTROL\\\\\\\",\\\\\\\"deviceControls\\\\\\\":[{\\\\\\\"deviceType\\\\\\\":\\\\\\\"CLASSBRAND\\\\\\\",\\\\\\\"code\\\\\\\":\\\\\\\"1\\\\\\\",\\\\\\\"delaySeconds\\\\\\\":\\\\\\\"0\\\\\\\"}]},{\\\\\\\"controlCode\\\\\\\":\\\\\\\"665a4d14d9fe44b0a85b445e7abb7422\\\\\\\",\\\\\\\"controlName\\\\\\\":\\\\\\\"44\\\\\\\",\\\\\\\"classLocation\\\\\\\":\\\\\\\"\\\\\\\",\\\\\\\"controlType\\\\\\\":\\\\\\\"DEVICECONTROL\\\\\\\",\\\\\\\"deviceControls\\\\\\\":[{\\\\\\\"deviceType\\\\\\\":\\\\\\\"LIGHT\\\\\\\",\\\\\\\"code\\\\\\\":\\\\\\\"1\\\\\\\",\\\\\\\"delaySeconds\\\\\\\":\\\\\\\"0\\\\\\\"}]},{\\\\\\\"controlCode\\\\\\\":\\\\\\\"cf519bbd0a6f4cd79be7484f2db98757\\\\\\\",\\\\\\\"controlName\\\\\\\":\\\\\\\"3543253253253\\\\\\\",\\\\\\\"classLocation\\\\\\\":\\\\\\\"\\\\\\\",\\\\\\\"controlType\\\\\\\":\\\\\\\"DEVICECONTROL\\\\\\\",\\\\\\\"deviceControls\\\\\\\":[{\\\\\\\"deviceType\\\\\\\":\\\\\\\"CURTAIN\\\\\\\",\\\\\\\"code\\\\\\\":\\\\\\\"1\\\\\\\",\\\\\\\"delaySeconds\\\\\\\":\\\\\\\"0\\\\\\\"}]}],\\\\\\\"oneClickClass\\\\\\\":[\\\\\\\"6d82609c6fb549d984a44efd8bba49af\\\\\\\"],\\\\\\\"oneClickGetOutOfClass\\\\\\\":[\\\\\\\"49edb8f2f63a4323968e9db9ceb6530a\\\\\\\"],\\\\\\\"cardAttendClass\\\\\\\":[],\\\\\\\"cardGetOutOfClass\\\\\\\":[]}\\\"}\"}\n";
                SpJson spJson = JacksonUtil.jsonStrToBean(json, SpJson.class);
                if (spJson.getMatrix() != null) {
                    handleMatrix(spJson.getMatrix());
                }
                EventBus.getDefault().post(spJson);
            }
        }
        return SocketResult.success();
    }

    @Override
    public String getTagName() {
        return CommuTag.TABSP_CONFIGURATION;
    }

    private void handleMatrix(MatrixDto matrixDto) {
        MatrixConfig matrixConfig = new MatrixConfig();
        Map<String, MatrixInterface> inputMatrix = new LinkedHashMap<>(), outputMatrix = new LinkedHashMap<>();
        matrixConfig.setOutputInterface(toInterface(matrixDto.getOutputMap(), inputMatrix));
        matrixConfig.setInputInterface(toInterface(matrixDto.getInputMap(), outputMatrix));
        matrixConfig.setScenes(getScenes(matrixDto.getScenes(), inputMatrix, outputMatrix));
        EventBus.getDefault().post(matrixConfig);
    }

    private List<MatrixInterface> toInterface(Map<String, String> inputs, Map<String, MatrixInterface> inputMatrix) {
        ArrayList list = sort(inputs);
        List<MatrixInterface> interfaces = new ArrayList<>();
        for (int i = 0, size = list.size(); i < size; i++) {
            Map.Entry<String, String> values = (Map.Entry<String, String>) list.get(i);
            MatrixInterface matrixInterface = new MatrixInterface();
            matrixInterface.input = values.getKey();
            matrixInterface.inputName = values.getValue();
            inputMatrix.put(matrixInterface.input, matrixInterface);
            interfaces.add(matrixInterface);
        }
        return interfaces;
    }

    private List<MatrixScene> getScenes(List<MatrixSceneDto> sceneDtos, Map<String, MatrixInterface> input, Map<String, MatrixInterface> output) {
        List<MatrixScene> scenes = new ArrayList<>();
        for (int i = 0, size = sceneDtos.size(); i < size; i++) {
            MatrixSceneDto sceneDto = sceneDtos.get(i);
            MatrixScene matrixScene = new MatrixScene();
            matrixScene.sceneName = sceneDto.getSceneName();
            matrixScene.sceneCode = sceneDto.getMatrixSceneCode();
            Map<String, List<Integer>> maps = sceneDto.getInterfaces();
            Map<MatrixInterface, List<MatrixInterface>> cache = new LinkedHashMap<>();
            if (maps != null) {
                Iterator<Map.Entry<String, List<Integer>>> entryIterator = maps.entrySet().iterator();
                while (entryIterator.hasNext()) {
                    Map.Entry<String, List<Integer>> val = entryIterator.next();
                    if (input.containsKey(val.getKey())) {
                        MatrixInterface in = input.get(val.getKey());
                        if (!cache.containsKey(in)) {
                            cache.put(in, new ArrayList<MatrixInterface>());
                        }
                        List<MatrixInterface> interfaces = cache.get(in);
                        for (int j = 0, siz = val.getValue().size(); j < siz; j++) {
                            String out = val.getValue().get(j) + "";
                            if (output.containsKey(out)) {
                                interfaces.add(output.get(out));
                            }
                        }
                    }
                }
            }
            matrixScene.setSceneMap(maps);
            matrixScene.setInterfaceMap(cache);
            scenes.add(matrixScene);
        }
        return scenes;
    }

    private ArrayList sort(Map inputs) {
        ArrayList<Map.Entry<String, String>> l = new ArrayList<Map.Entry<String, String>>(inputs.entrySet());
        Collections.sort(l, new Comparator<Map.Entry<String, String>>() {
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                int a = Integer.parseInt(o1.getKey());
                int b = Integer.parseInt(o2.getKey());
                return a - b;
            }
        });
        return l;
    }

}
