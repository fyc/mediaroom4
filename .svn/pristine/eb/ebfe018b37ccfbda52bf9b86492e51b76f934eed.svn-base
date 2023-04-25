package com.sunmnet.mediaroom.brand.impl.camera;

import com.sunmnet.mediaroom.brand.bean.response.CameraDeviceResponse;
import com.sunmnet.mediaroom.brand.interfaces.camera.ICamera;
import com.sunmnet.mediaroom.common.tools.ToastUtil;

/**
 * 监控摄像头整合类
 */
public class MonitorCamera {

    private ICamera camera = null;
    private CameraDeviceResponse.Result cameraDevice;

    private MonitorCamera(CameraDeviceResponse.Result cameraDeviceResult) {
        this.cameraDevice = cameraDeviceResult;
        if (cameraDeviceResult != null) {
            if (cameraDeviceResult.getBrandName() != null && !cameraDeviceResult.getBrandName().equals("")) {
                if ("大华".equals(cameraDeviceResult.getBrandName())) {
                    camera = new CameraForDahua();
                } else if ("海康".equals(cameraDeviceResult.getBrandName())
                        || "宇视".equals(cameraDeviceResult.getBrandName())
                        || "竞业达".equals(cameraDeviceResult.getBrandName())) {
                    camera = new CameraForHaiKang();
                } else if ("星网".equals(cameraDeviceResult.getBrandName())) {
                    camera = new CameraForXingWang();
                } else if (cameraDeviceResult.getVideoCodeStream() != null && !cameraDeviceResult.getVideoCodeStream().isEmpty()) {
                    // 未指定特定牌子的摄像头
                    camera = new CameraForUnspecified();
                }
            }
        }
        if (camera == null)
            camera = new CameraForNull();
    }

    public static MonitorCamera create(CameraDeviceResponse.Result result) {
        return new MonitorCamera(result);
    }

    public String getPlayUrl() {
        return camera.getPlayUrl(cameraDevice);
    }
}
