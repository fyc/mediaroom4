package com.sunmnet.mediaroom.brand.api.service;

import com.sunmnet.mediaroom.brand.bean.request.FaceLoginRequest;
import com.sunmnet.mediaroom.brand.bean.request.LoginRequest;
import com.sunmnet.mediaroom.brand.bean.request.UpgradeResultRequest;
import com.sunmnet.mediaroom.brand.bean.response.CardholderInfoResponse;
import com.sunmnet.mediaroom.brand.bean.response.LoginResponse;
import com.sunmnet.mediaroom.brand.bean.response.LongResponse;
import com.sunmnet.mediaroom.brand.bean.response.ObjectResponse;
import com.sunmnet.mediaroom.brand.bean.response.StringResponse;
import com.sunmnet.mediaroom.brand.bean.response.WeatherResponse;
import com.sunmnet.mediaroom.common.bean.Result;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SysService {

    /**
     * 获取用户头像地址
     */
    @GET("sys-svr/api/getHeadPortrait")
    Observable<StringResponse> getHeadPortrait(@Query("accountId") String accountId);

    /**
     * 获取平台系统时间
     */
    @GET("sys-svr/api/getSystemTime")
    Observable<LongResponse> getSystemTime();

    /**
     * 获取天气
     */
    @GET("sys-svr/api/todayWeather")
    Observable<WeatherResponse> getTodayWeather(@Query("cityName") String cityName);

    /**
     * 账号登录
     */
    @POST("sys-svr/account/login/terminal/brand/uname")
    Observable<LoginResponse> login(@Body LoginRequest loginRequest);

    /**
     * 人脸登录
     */
    @POST("sys-svr/account/login/terminal/brand/face")
    Observable<LoginResponse> faceLogin(@Body FaceLoginRequest loginRequest);

    /**
     * 升级结果反馈
     */
    @POST("sys-svr/api/dealVerUpgrade")
    Observable<ObjectResponse> respondUpgradeResult(@Body UpgradeResultRequest resultRequest);


    /**
     * 人脸同步结果反馈
     */
    @FormUrlEncoded
    @POST("sys-svr/api/syncFaceResult")
    Observable<ObjectResponse> syncFaceResult(@Field("id") String id, @Field("status") Integer status);

    /**
     * 根据卡号获取持卡人信息
     * @param cardNumber 卡号
     * @return
     */
    @GET("sys-svr/api/getAccountByCardNum")
    Observable<Result<CardholderInfoResponse>> getCardholderInfo(@Query("cardNum") String cardNumber);
}
