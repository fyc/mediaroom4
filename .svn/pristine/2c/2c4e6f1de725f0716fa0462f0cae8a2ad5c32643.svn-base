package com.sunmnet.mediaroom.matrix.utils;

import android.text.TextUtils;

import com.sunmnet.mediaroom.util.bean.cmd.tabsp_matrix.TabspMatrixDto;
import com.sunmnet.mediaroom.util.bean.cmd.tabsp_matrix.TabspMatrixSceneDto;
import com.sunmnet.mediaroom.util.bean.matrix.MatrixDto;
import com.sunmnet.mediaroom.util.bean.matrix.MatrixInterfacesDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dengzl_pc on 2018/2/6.
 */

public class MatrixUtils {
    public static int MIN_INTERFACE=8;
    public static void copyMatrixScenes(TabspMatrixDto source, TabspMatrixDto target) {
        target.setAccountId(source.getAccountId());
        target.setClassroomCode(source.getClassroomCode());
        target.setScenes(source.getScenes());
    }
    public static void copyScenes(TabspMatrixDto source, TabspMatrixDto target){
        if(source!=null){
            target.setScenes(source.getScenes());
        }
    }

    public static TabspMatrixSceneDto makeEmptyScene() {
        TabspMatrixSceneDto empty = new TabspMatrixSceneDto();
        Map<String, List<String>> maps = new LinkedHashMap<>();
        for (int i = 1; i <= MIN_INTERFACE; i++) {
            String input = i + "";
            List<String> list = new ArrayList<>();
            maps.put(input, list);
        }
        empty.setInterfaces(maps);
        return empty;
    }

    public static void copyMatrixScenes(TabspMatrixSceneDto source, TabspMatrixSceneDto target) {
    }

    public static String generateNotExistName(TabspMatrixDto tabsps) {
        /*String name = "场景1";
        Map<String, TabspMatrixSceneDto> maps = toMap(tabsps);

        return name;*/
        return generateNotExistName(tabsps.getScenes());
    }
    public static String generateNotExistName(List<TabspMatrixSceneDto> sceneDtos){
        Map<String, TabspMatrixSceneDto> existing=toMap(sceneDtos);
        String name="";
        String defaultName = "场景";
        for (int i = 1; i <= 10; i++) {
            name = defaultName + i;
            if (!existing.containsKey(name)) {
                break;
            }
        }
        return name;
    }

    public static TabspMatrixSceneDto createDefaultScene(TabspMatrixDto tabsps) {
        TabspMatrixSceneDto create = new TabspMatrixSceneDto();
        Map<String, TabspMatrixSceneDto> maps = toMap(tabsps);
        String defaultName = "场景";
        for (int i = 1; i < 10; i++) {
            String name = defaultName + i;
            if (!maps.containsKey(name)) {
                create.setSceneName(name);
                break;
            }
        }
        return create;
    }

    public static boolean isSameIO(TabspMatrixDto m1, TabspMatrixDto m2) {
        boolean isSame = false;
        if (m1 != null && m2 != null && m1.getAccountId().equals(m2.getAccountId())) {
            isSame = isSameIO(m1.getInputMap(), m1.getInputMap());
            isSame = isSameIO(m1.getOutputMap(), m1.getOutputMap());
        }
        return isSame;
    }

    private static boolean isSameIO(Map<String, String> m1, Map<String, String> m2) {
        boolean isSame = true;
        Iterator<String> keys = m1.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            if (!m2.containsKey(key) || !m2.get(key).equals(m1.get(key))) {
                isSame = false;
                break;
            }
        }
        return isSame;
    }

    public static boolean isSameScene(TabspMatrixSceneDto t1, TabspMatrixSceneDto t2) {
        boolean isSame = true;
        if (!t1.getSceneName().equals(t2.getSceneName())) return false;
        Map<String, List<String>> m1 = t1.getInterfaces();
        Map<String, List<String>> m2 = t2.getInterfaces();
        Iterator<String> keys = m1.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            if (!m2.containsKey(key)) {
                isSame = false;
                break;
            } else {
                List<String> l1 = m1.get(key);
                List<String> l2 = m2.get(key);
                if (l1.size() != l2.size()) {
                    isSame = false;
                    break;
                } else {
                    for (int i = 0; i < l1.size(); i++) {
                        String value = l1.get(i);
                        if (!l2.contains(value)) {
                            isSame = false;
                            break;
                        }
                    }
                }
            }
        }
        return isSame;
    }

    public static boolean isSameScene(Map<String, TabspMatrixSceneDto> s1, Map<String, TabspMatrixSceneDto> s2) {
        boolean isSame = true;
        if (s1 != null && s2 != null) {
            Iterator<String> keys = s1.keySet().iterator();
            while (keys.hasNext()) {
                String key = keys.next();
                if (s2.containsKey(key)) {
                    TabspMatrixSceneDto t1 = s1.get(key);
                    TabspMatrixSceneDto t2 = s2.get(key);
                    if (t1.getSceneName().equals(t2.getSceneName())) {
                        if (!isSameScene(t1, t2)) {
                            isSame = false;
                            break;
                        }
                    } else {
                        isSame = false;
                        break;
                    }
                } else {
                    isSame = false;
                    break;
                }
            }
        }
        return isSame;
    }

    public static boolean isSameScene(TabspMatrixDto m1, TabspMatrixDto m2) {
        boolean isEqual = false;
        if (m1 != null && m2 != null && !TextUtils.isEmpty(m1.getAccountId())
                && !TextUtils.isEmpty(m2.getAccountId())
                && (m1.getAccountId().equals(m2.getAccountId()))) {
            isEqual = true;
        }
        /*if(m1!=null&&m2!=null){
            if(m1.getAccountId().equals(m2.getAccountId()))
            {
                if(m1.getScenes().size()==m2.getScenes().size()){
                    if(isSameScene(toMap(m1),toMap(m2)))
                    {
                        return true;
                    }else RunningLog.warn("当前保存的场景和回调的场景不一致:"+ JsonUtils.objectToJson(m1.getScenes())+"\n"+JsonUtils.objectToJson(m2.getScenes()));
                }else
                    RunningLog.warn("保存的场景数量不一致！！");
            }else
                RunningLog.warn("矩阵场景设置：当前设置的accountId和回调的accountId不一致！");
        }*/
        return isEqual;
    }

    public static void copyMatrixSettingsWithoutAccount(TabspMatrixDto source, TabspMatrixDto target) {
        if (source != null) {
            if (source.getInputMap() != null && source.getInputMap().size() > 0) {
                target.setInputMap(source.getInputMap());
            }
            if (source.getOutputMap() != null && source.getOutputMap().size() > 0) {
                target.setOutputMap(source.getOutputMap());
            }
            target.setSound(source.getSound());
        }
    }

    public static void copyMatrixSettings(TabspMatrixDto source, TabspMatrixDto target) {
        copyMatrixSettingsWithoutAccount(source, target);
        if (source != null) {
            target.setAccountId(source.getAccountId());
            target.setClassroomCode(source.getClassroomCode());
        }
    }

    public static int getConnectingCount(MatrixInterfacesDto dto) {
        int count = 0;
        if (dto == null) return 0;
        Map<String, List<String>> connects = dto.getInterfaces();
        if (connects.size() > 0) {
            Iterator<String> keys = connects.keySet().iterator();
            while (keys.hasNext()) {
                String key = keys.next();
                List<String> strs = connects.get(key);
                count += strs.size();
            }
        }
        return count;
    }

    public static void remove(TabspMatrixSceneDto scene, int input, int output, TabspMatrixDto dto) {

    }

    public static Map<String, String> generateMap(String input) {
        Map<String, String> maps = new LinkedHashMap();
        for (int i = 1; i < 9; i++) {
            maps.put(i + "", input + i);
        }
        return maps;
    }

    public static void remove(TabspMatrixDto dto, TabspMatrixSceneDto scene) {
        Map<String, TabspMatrixSceneDto> map = toMap(dto);
        if (map.containsKey(scene.getSceneName())) {
            TabspMatrixSceneDto cp = map.get(scene.getSceneName());
            dto.getScenes().remove(cp);
        }
    }

    public static TabspMatrixSceneDto copy(TabspMatrixSceneDto scene) {
        TabspMatrixSceneDto cp = new TabspMatrixSceneDto(new LinkedHashMap<String, List<String>>(), "");
        cp.setSceneName(scene.getSceneName());
        Map<String, List<String>> scenes = scene.getInterfaces();
        Iterator<String> keys = scenes.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            List<String> values = scenes.get(key);
            List<String> cvalues = new ArrayList<>();
            cp.getInterfaces().put(key, cvalues);
            for (int i = 0; i < values.size(); i++) {
                String str = values.get(i);
                cvalues.add(str);
            }
        }
        return cp;
    }

    public static void clearMatrix(MatrixInterfacesDto scene) {
        scene.getInterfaces().clear();
    }

    public static void save(TabspMatrixDto dto, TabspMatrixSceneDto scene) {
        Map<String, TabspMatrixSceneDto> toMap = toMap(dto);
        if (toMap.containsKey(scene.getSceneName())) {
            //存在则修改
            TabspMatrixSceneDto localScene = toMap.get(scene.getSceneName());
            localScene.getInterfaces().clear();
            Map<String, List<String>> values = new LinkedHashMap<>();
            localScene.setInterfaces(values);
            Map<String, List<String>> exists = scene.getInterfaces();
            Iterator<String> keys = exists.keySet().iterator();
            while (keys.hasNext()) {
                String key = keys.next();
                List<String> strs = exists.get(key);
                List<String> newVal = new ArrayList<>();
                newVal.addAll(strs);
                values.put(key, newVal);
            }
        } else {
            dto.getScenes().add(copy(scene));
        }


    }

    public static TabspMatrixSceneDto makeDefaultScene() {
        TabspMatrixSceneDto scene = new TabspMatrixSceneDto();
        makeDefaultScene(null, scene);
        return scene;
    }

    public static void makeDefaultScene(TabspMatrixDto dto, TabspMatrixSceneDto scene) {
        makeDefaultScene(MIN_INTERFACE,scene);
       /* for (int i = 1; i <= MIN_INTERFACE; i++) {
            String keyName = i + "";
            String value = i + "";
            List<String> values = new ArrayList<>();
            values.add(value);
            defaults.put(keyName, values);
        }*/
    }
    public static void makeDefaultScene(int interfaceCount, TabspMatrixSceneDto scene){
        Map<String, List<String>> defaults = scene.getInterfaces();
        for (int i = 1; i <= interfaceCount; i++) {
            String keyName = i + "";
            String value = i + "";
            List<String> values = new ArrayList<>();
            values.add(value);
            defaults.put(keyName, values);
        }
    }

    public static void updateScene(int input, int output, boolean isSelect, TabspMatrixSceneDto scene, TabspMatrixDto dto) {
        Map<String, String> inMap = inMap(scene);
        String sinput = input + "", sout = output + "";
        List<String> values = null;
        if (inMap.containsKey(sout) && isSelect) {
            String key = inMap.get(sout);
            values = scene.getInterfaces().get(key);
            values.remove(sout);
        }
        values = scene.getInterfaces().get(sinput);
        if (values == null) {
            values = new ArrayList<>();
            scene.getInterfaces().put(sinput, values);
        }
        if (!values.contains(sout) && isSelect) {
            values.add(sout);
        } else
            values.remove(sout);
    }

    public static Map<String, String> inMap(TabspMatrixSceneDto dto) {
        Map<String, String> valueInKey = new HashMap<>();
        Map<String, List<String>> values = dto.getInterfaces();
        Iterator<String> keys = values.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            List<String> outputs = values.get(key);
            for (int i = 0; i < outputs.size(); i++) {
                valueInKey.put(outputs.get(i), key);
            }
        }
        return valueInKey;
    }

    public static Map<String, String> inMap(MatrixDto dto) {
        Map<String, String> valueInKey = new HashMap<>();
        int size = dto.getScenes().size();
        List<MatrixInterfacesDto> dtos = dto.getScenes();
        for (int i = 0; i < size; i++) {
            MatrixInterfacesDto matrix = dtos.get(i);
            Map<String, List<String>> values = matrix.getInterfaces();
            Iterator<String> keys = values.keySet().iterator();
            while (keys.hasNext()) {
                String key = keys.next();
                List<String> value = values.get(key);
                for (int j = 0; j < value.size(); j++) {
                    valueInKey.put(value.get(j), key);
                }
            }
        }
        return valueInKey;
    }

    public static Map<String, Integer> toList(TabspMatrixDto dto) {
        Map<String, Integer> interfacesDtoMap = new LinkedHashMap<>();
        int size = dto.getScenes().size();
        for (int i = 0; i < size; i++) {
            TabspMatrixSceneDto mif = dto.getScenes().get(i);
            interfacesDtoMap.put(mif.getSceneName(), i);
        }
        return interfacesDtoMap;
    }

    public static Map<String, TabspMatrixSceneDto> toMap(TabspMatrixDto dto) {
        return toMap(dto.getScenes());
    }

    public static Map<String, TabspMatrixSceneDto> toMap(List<TabspMatrixSceneDto> scenes) {
        Map<String, TabspMatrixSceneDto> interfacesDtoMap = new LinkedHashMap<>();
        int size = scenes.size();
        for (int i = 0; i < size; i++) {
            TabspMatrixSceneDto mif = scenes.get(i);
            interfacesDtoMap.put(mif.getSceneName(), mif);
        }
        return interfacesDtoMap;
    }

    public static TabspMatrixSceneDto newMatrix(TabspMatrixDto bean, boolean isDefault) {
        Map<String, TabspMatrixSceneDto> scenes = toMap(bean);
        String tagName = "场景";
        TabspMatrixSceneDto scene = null;
        for (int i = 1; i <= 10; i++) {
            String name = tagName + i;
            if (!scenes.containsKey(name)) {
                tagName = name;
                break;
            }
        }
        scene = new TabspMatrixSceneDto(new LinkedHashMap<String, List<String>>(), tagName);
        //scene.setSceneName(tagName);
        if (isDefault) {
            makeDefaultScene(null, scene);
        }
        return scene;
    }


    public static void remove(TabspMatrixSceneDto selectedScene, int intput, Integer output) {
        String in = intput + "";
        String out = output + "";
        if (selectedScene.getInterfaces().containsKey(in)) {
            List<String> str = selectedScene.getInterfaces().get(in);
            if (str.contains(out)) str.remove(out);
        }
    }
    public static TabspMatrixSceneDto findSceneInMatrixDto(TabspMatrixSceneDto sceneDto, TabspMatrixDto matrixDto){
        return findSceneInMatrixDto(sceneDto.getSceneName(),matrixDto);
    }
    public static TabspMatrixSceneDto findSceneInMatrixDto(String name, TabspMatrixDto matrixDto){
        Map<String, TabspMatrixSceneDto> sceneDtoMap=toMap(matrixDto);
        return sceneDtoMap.containsKey(name)?sceneDtoMap.get(name):null;
    }
    public static TabspMatrixSceneDto findSceneInScenes(String name, List<TabspMatrixSceneDto> sceneDtos){
        Map<String, TabspMatrixSceneDto> sceneDtoMap=toMap(sceneDtos);
        return sceneDtoMap.containsKey(name)?sceneDtoMap.get(name):null;
    }
}
