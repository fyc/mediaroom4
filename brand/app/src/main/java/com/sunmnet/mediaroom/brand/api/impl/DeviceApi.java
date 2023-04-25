package com.sunmnet.mediaroom.brand.api.impl;

import com.sunmnet.mediaroom.brand.api.service.DeviceService;
import com.sunmnet.mediaroom.brand.bean.response.CameraDeviceResponse;
import com.sunmnet.mediaroom.brand.bean.response.StringResponse;
import com.sunmnet.mediaroom.brand.utils.RetrofitManager;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.Body;

public class DeviceApi implements DeviceService {

    private DeviceService service;

    private DeviceService getApiService() {
        if (service == null) {
            service = RetrofitManager.getPLRetrofit() == null ? null : RetrofitManager.getPLRetrofit().create(DeviceService.class);
        }
        return service;
    }


    @Override
    public Observable<CameraDeviceResponse> getCameraByClassroom(String classroomCode) {
        return getApiService() == null ? null : getApiService().getCameraByClassroom(classroomCode).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<StringResponse> uploadSerialCode(@Body HashMap<String, String> body) {
        return getApiService() == null ? null : getApiService().uploadSerialCode(body).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<StringResponse> insertAttendanceLog(HashMap<String, Object> request) {
        return getApiService() == null ? null : getApiService().insertAttendanceLog(request).subscribeOn(Schedulers.io());
    }
}
