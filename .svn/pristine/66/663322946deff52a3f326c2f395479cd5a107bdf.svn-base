package com.sunmnet.mediaroom.brand.impl.camera;

import android.text.TextUtils;

import com.sunmnet.mediaroom.brand.bean.response.CameraDeviceResponse;
import com.sunmnet.mediaroom.brand.interfaces.camera.ICamera;

/**
 * 大华摄像头
 */
public class CameraForDahua implements ICamera {

    @Override
    public String getPlayUrl(CameraDeviceResponse.Result camera) {
        if (camera == null)
            return "";
        if (!TextUtils.isEmpty(camera.getVideoCodeStream()) &&
                camera.getVideoCodeStream().startsWith("rtsp://")) {
            return camera.getVideoCodeStream();
        } else {
            String subType = "1";
            String chanel = "1";
            StringBuffer buffer = new StringBuffer();
            buffer.append("rtsp://");
            buffer.append(camera.getUsername());
            buffer.append(":");
            buffer.append(camera.getPassword());
            buffer.append("@");
            buffer.append(camera.getIp());
            buffer.append(":");
            buffer.append(camera.getPort());
            buffer.append("/cam/realmonitor?channel=");
            buffer.append(chanel);
            buffer.append("&subtype=");
            buffer.append(subType);
            //rtsp://admin:admin@192.168.18.42:554/cam/realmonitor?channel=1&subtype=0
            return buffer.toString();
        }
    }
}
