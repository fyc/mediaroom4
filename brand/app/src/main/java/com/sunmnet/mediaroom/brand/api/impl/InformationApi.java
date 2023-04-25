package com.sunmnet.mediaroom.brand.api.impl;

import com.sunmnet.mediaroom.brand.api.service.InformationService;
import com.sunmnet.mediaroom.brand.bean.response.ExamDetailResponse;
import com.sunmnet.mediaroom.brand.bean.response.QueryExamRoomResponse;
import com.sunmnet.mediaroom.brand.utils.RetrofitManager;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class InformationApi implements InformationService {

    private InformationService service;

    private InformationService getApiService() {
        if (service == null) {
            service = RetrofitManager.getPLRetrofit() == null ? null : RetrofitManager.getPLRetrofit().create(InformationService.class);
        }
        return service;
    }

    @Override
    public Observable<ExamDetailResponse> getExamDetailList(String examId, String classroomCode) {
        return getApiService() == null ? null : getApiService().getExamDetailList(examId, classroomCode).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<QueryExamRoomResponse> findUserExamInfo(String registrationNumber, String idNumber) {
        return getApiService() == null ? null : getApiService().findUserExamInfo(registrationNumber, idNumber).subscribeOn(Schedulers.io());
    }
}
