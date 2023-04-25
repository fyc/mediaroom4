package com.sunmnet.mediaroom.device.controll.service;

import com.sunmnet.mediaroom.device.bean.MatrixConfig;
import com.sunmnet.mediaroom.device.bean.MatrixScene;

import java.util.List;
import java.util.Map;

/**
 * 矩阵操作接口
 * */
public interface MatrixService extends IRelease {
    /**
     * 矩阵控制
     */
    public void controll(String input, String[] output);

    public void controll(String input, String output);

    public void controll(String sceneName);

    public MatrixConfig getMatrixConfig();

    public void controll(MatrixScene scene);

    public void control(Map<String, List<String>> interfaces);

    /**
     * 矩阵Edid控制
     * @param interfaces
     */
    void edidControl(Map<String, List<String>> interfaces);
}
