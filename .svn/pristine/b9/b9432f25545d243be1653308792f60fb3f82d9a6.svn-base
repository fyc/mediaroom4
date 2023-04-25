package com.sunmnet.mediaroom.brand.api.service;

import com.sunmnet.mediaroom.brand.bean.response.CameraDeviceResponse;
import com.sunmnet.mediaroom.brand.bean.response.StringResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface DeviceService {

    /**
     * 获取教室监控摄像头
     */
    @FormUrlEncoded
    @POST("device-svr/api/getCameraByClassroom")
    Observable<CameraDeviceResponse> getCameraByClassroom(@Field("classroomCode") String classroomCode);

    /**
     * 上传班牌人脸识别信息文件host.info 的base64码
     */
    @POST("device-svr/api/uploadClassBrandSerialCode")
    Observable<StringResponse> uploadSerialCode(@Body HashMap<String, String> body);

    /**
     * 上传静默考勤记录
     * @param request
     * @return
     */
    @POST("device-svr/api/saveSignLog")
    Observable<StringResponse> insertAttendanceLog(@Body HashMap<String, Object> request);
}
