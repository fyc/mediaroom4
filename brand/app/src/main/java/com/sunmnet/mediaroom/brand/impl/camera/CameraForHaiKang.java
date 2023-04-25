package com.sunmnet.mediaroom.brand.impl.camera;

import android.text.TextUtils;

import com.sunmnet.mediaroom.brand.bean.response.CameraDeviceResponse;
import com.sunmnet.mediaroom.brand.interfaces.camera.ICamera;

/**
 * 海康摄像头
 */
public class CameraForHaiKang implements ICamera {

    @Override
    public String getPlayUrl(CameraDeviceResponse.Result camera) {
        if (camera == null)
            return "";
        if (!TextUtils.isEmpty(camera.getVideoCodeStream()) &&
                camera.getVideoCodeStream().startsWith("rtsp://")) {
            return camera.getVideoCodeStream();
        } else {
            String chanel = "1";
            String subType = "sub";
            StringBuffer buffer = new StringBuffer();
            buffer.append("rtsp://");
            buffer.append(camera.getUsername());
            buffer.append(":");
            buffer.append(camera.getPassword());
            buffer.append("@");
            buffer.append(camera.getIp());
            buffer.append(":");
            buffer.append(camera.getPort());
            buffer.append("/h264/ch");
            buffer.append(chanel);
            buffer.append("/");
            buffer.append(subType);
            buffer.append("/");
            buffer.append("av_stream");
            return buffer.toString();
        }
    }
}
