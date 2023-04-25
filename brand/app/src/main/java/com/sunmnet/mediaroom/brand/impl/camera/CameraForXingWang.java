package com.sunmnet.mediaroom.brand.impl.camera;

import android.text.TextUtils;

import com.sunmnet.mediaroom.brand.bean.response.CameraDeviceResponse;
import com.sunmnet.mediaroom.brand.interfaces.camera.ICamera;

/**
 * 星网摄像头
 */
public class CameraForXingWang implements ICamera {

    @Override
    public String getPlayUrl(CameraDeviceResponse.Result camera) {
        //"rtsp://" +deviceIp  +":" + devicePort + "/id=1&type=0");
        if (camera == null)
            return "";
        if (!TextUtils.isEmpty(camera.getVideoCodeStream()) &&
                camera.getVideoCodeStream().startsWith("rtsp://")) {
            return camera.getVideoCodeStream();
        } else {
            String subType = "1";
            StringBuffer buffer = new StringBuffer();
            buffer.append("rtsp://");
            buffer.append(camera.getIp());
            buffer.append(":");
            buffer.append(camera.getPort());
            buffer.append("/id=1&type=");
            buffer.append(subType);
            return buffer.toString();
        }
    }
}
