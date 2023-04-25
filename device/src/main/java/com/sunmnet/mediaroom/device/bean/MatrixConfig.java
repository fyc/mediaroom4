package com.sunmnet.mediaroom.device.bean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MatrixConfig {
    List<MatrixInterface> inputInterface;
    List<MatrixInterface> outputInterface;
    List<MatrixScene> scenes;

    public Map<String, MatrixInterface> toMap(List<MatrixInterface> interfaces) {
        Map<String, MatrixInterface> map = new LinkedHashMap<>();
        for (int i = 0,size=interfaces.size(); i <size ; i++) {
            map.put(interfaces.get(i).input,interfaces.get(i));
        }
        return map;
    }

    public List<MatrixInterface> getInputInterface() {
        return inputInterface;
    }

    public void setInputInterface(List<MatrixInterface> inputInterface) {
        this.inputInterface = inputInterface;
    }

    public List<MatrixInterface> getOutputInterface() {
        return outputInterface;
    }

    public void setOutputInterface(List<MatrixInterface> outputInterface) {
        this.outputInterface = outputInterface;
    }

    public List<MatrixScene> getScenes() {
        return scenes;
    }

    public void setScenes(List<MatrixScene> scenes) {
        this.scenes = scenes;
    }

    public List<MatrixInterface> getInterfacesByList(List<MatrixInterface> interfaces, List<String> outputs) {
        List<MatrixInterface> values = new ArrayList<>();
        for (int i = 0; i < outputs.size(); i++) {
            MatrixInterface matrixInterface = findInterface(outputs.get(i), interfaces);
            if (matrixInterface != null) {
                values.add(matrixInterface);
            }
        }
        return values;
    }

    public MatrixInterface findInterface(String input, List<MatrixInterface> interfaces) {
        MatrixInterface matrixInterface = null;
        if (interfaces != null) {
            for (int i = 0, size = interfaces.size(); i < size; i++) {
                if (interfaces.get(i).input.equals(input)) {
                    matrixInterface = interfaces.get(i);
                    break;
                }
            }
        }
        return matrixInterface;
    }
    public Map<MatrixInterface,List<MatrixInterface>> toSceneMap(MatrixScene scene){
        Map<MatrixInterface,List<MatrixInterface>> map=new LinkedHashMap<>();
        Map<String, List<String>> scenes = scene.getSceneMap();
        if (scenes != null && scenes.size() > 0) {
            Iterator<String> keys = scenes.keySet().iterator();
            while (keys.hasNext()) {
                String key = keys.next();
                MatrixInterface matrixInterface=this.findInterface(key,this.getInputInterface());
                if (matrixInterface!=null){
                    map.put(matrixInterface,this.getInterfacesByList(getOutputInterface(),scenes.get(key)));
                }
            }
        }
        return map;
    }
}
