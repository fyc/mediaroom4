package com.sunmnet.mediaroom.device.bean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MatrixScene {
    public String sceneName;

    public Map<String, List<String>> getSceneMap() {
        return sceneMap;
    }

    public Map<String, List<String>> sceneMap;
    public String sceneCode;

    public Map<MatrixInterface, List<MatrixInterface>> getInterfaceMap() {
        return interfaceMap;
    }

    public void setInterfaceMap(Map<MatrixInterface, List<MatrixInterface>> interfaceMap) {
        this.interfaceMap = interfaceMap;
    }

    public Map<MatrixInterface,List<MatrixInterface>> interfaceMap;
    public void setSceneMap(Map<String, List<Integer>> maps) {
        sceneMap = new LinkedHashMap<>();
        Iterator<Map.Entry<String, List<Integer>>> entryIterator = maps.entrySet().iterator();
        while (entryIterator.hasNext()) {
            Map.Entry<String, List<Integer>> values = entryIterator.next();
            List<String> strings = new ArrayList<>();
            for (int i = 0, size = values.getValue().size(); i < size; i++) {
                strings.add(values.getValue().get(i) + "");
            }
            sceneMap.put(values.getKey(), strings);
        }
    }
}
