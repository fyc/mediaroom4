package com.sunmnet.mediaroom.device.bean;

import java.util.List;

public class SceneControlBean {

    /**
     * operateMethod : 1
     * operativesId : xxxxxxxx
     * controlCodes : ["controlCode1","controlCode2","controlCode3"]
     * matrixSceneCodes : ["xxxxx"]
     */

    private int operateMethod;
    private String operativesId;
    private List<String> controlCodes;
    private List<String> matrixSceneCodes;

    public int getOperateMethod() {
        return operateMethod;
    }

    public void setOperateMethod(int operateMethod) {
        this.operateMethod = operateMethod;
    }

    public String getOperativesId() {
        return operativesId;
    }

    public void setOperativesId(String operativesId) {
        this.operativesId = operativesId;
    }

    public List<String> getControlCodes() {
        return controlCodes;
    }

    public void setControlCodes(List<String> controlCodes) {
        this.controlCodes = controlCodes;
    }

    public List<String> getMatrixSceneCodes() {
        return matrixSceneCodes;
    }

    public void setMatrixSceneCodes(List<String> matrixSceneCodes) {
        this.matrixSceneCodes = matrixSceneCodes;
    }
}
