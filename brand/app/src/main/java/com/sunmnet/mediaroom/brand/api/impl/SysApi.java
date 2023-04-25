package com.sunmnet.mediaroom.brand.api.impl;

import com.sunmnet.mediaroom.brand.api.service.SysService;
import com.sunmnet.mediaroom.brand.bean.request.FaceLoginRequest;
import com.sunmnet.mediaroom.brand.bean.request.LoginRequest;
import com.sunmnet.mediaroom.brand.bean.request.UpgradeResultRequest;
import com.sunmnet.mediaroom.brand.bean.response.CardholderInfoResponse;
import com.sunmnet.mediaroom.brand.bean.response.LoginResponse;
import com.sunmnet.mediaroom.brand.bean.response.LongResponse;
import com.sunmnet.mediaroom.brand.bean.response.ObjectResponse;
import com.sunmnet.mediaroom.brand.bean.response.StringResponse;
import com.sunmnet.mediaroom.brand.bean.response.WeatherResponse;
import com.sunmnet.mediaroom.brand.utils.RetrofitManager;
import com.sunmnet.mediaroom.common.bean.Result;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class SysApi implements SysService {

    private SysService service;

    private SysService getApiService() {
        if (service == null) {
            service = RetrofitManager.getPLRetrofit() == null ? null : RetrofitManager.getPLRetrofit().create(SysService.class);
        }
        return service;
    }

    @Override
    public Observable<StringResponse> getHeadPortrait(String accountId) {
        return getApiService() == null ? null : getApiService().getHeadPortrait(accountId).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<LongResponse> getSystemTime() {
        return getApiService() == null ? null : getApiService().getSystemTime().subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<WeatherResponse> getTodayWeather(String cityName) {
        return getApiService() == null ? null : getApiService().getTodayWeather(cityName).subscribeOn(Schedulers.io());
    }


    @Override
    public Observable<LoginResponse> login(LoginRequest request) {
        return getApiService() == null ? null : getApiService().login(request).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<LoginResponse> faceLogin(FaceLoginRequest loginRequest) {
        return getApiService() == null ? null : getApiService().faceLogin(loginRequest).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<ObjectResponse> respondUpgradeResult(UpgradeResultRequest resultRequest) {
        return getApiService() == null ? null : getApiService().respondUpgradeResult(resultRequest).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<ObjectResponse> syncFaceResult(String id, Integer status) {
        return getApiService() == null ? null : getApiService().syncFaceResult(id, status).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<Result<CardholderInfoResponse>> getCardholderInfo(String cardNumber) {
        return getApiService() == null ? null : getApiService().getCardholderInfo(cardNumber).subscribeOn(Schedulers.io());
    }

}
