package com.sunmnet.mediaroom.brand.impl.attendance;

import com.sunmnet.mediaroom.brand.api.ApiManager;
import com.sunmnet.mediaroom.brand.bean.event.AttendanceEvent;
import com.sunmnet.mediaroom.brand.bean.event.RefreshAttendanceEvent;
import com.sunmnet.mediaroom.brand.bean.request.AttendSignInRequest2;
import com.sunmnet.mediaroom.brand.bean.response.CardholderInfoResponse;
import com.sunmnet.mediaroom.brand.bean.response.ReserveInfoResponse;
import com.sunmnet.mediaroom.brand.common.base.DeviceApp;
import com.sunmnet.mediaroom.brand.rx.SingleTaskObserver;
import com.sunmnet.mediaroom.brand.utils.VerifyUtil;
import com.sunmnet.mediaroom.common.bean.Result;
import com.sunmnet.mediaroom.common.bean.course.CourseSchedule;
import com.sunmnet.mediaroom.common.config.CourseConfig;
import com.sunmnet.mediaroom.common.tools.CourseHelper;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ThreadUtils;
import com.sunmnet.mediaroom.common.tools.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.Date;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Create by WangJincheng on 2021-07-16
 * 刷卡考勤
 */

public class SwipeCardAttendance extends AbstractAttendance {

    @Override
    public <T> void attendance(T cardNumber) {
        if (cardNumber instanceof String) {
            Date nowDate = new Date();
            CourseSchedule courseSchedule = CourseHelper.getDefault().getCourseSchedule(nowDate, 0, "0", CourseConfig.PRE_START_TIME);
            if (courseSchedule == null) {
                ToastUtil.show(DeviceApp.getApp(), "当前不在考勤时间内");
                RunningLog.run("当前不在考勤时间内，中止上传刷卡签到结果");
                return;
            }

            // 获取持卡人信息
            ApiManager.getSysApi().getCardholderInfo((String) cardNumber).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleTaskObserver<Result<CardholderInfoResponse>>() {
                @Override
                public void onNext(Result<CardholderInfoResponse> response) {
                    if (response != null && response.isSuccess()) {
                        CardholderInfoResponse userInfo = response.getObj();
                        if (userInfo != null) {
                            // 提交考勤记录
                            submitAttendanceInfo(userInfo, courseSchedule);
                        } else {
                            RunningLog.run("持卡用户信息DTO失败");

                        }
                    } else {
                        ToastUtil.show(DeviceApp.getApp(), " 签到失败");
                    }
                }

                @Override
                public void onError(Throwable e) {
                    RunningLog.debug("上传签到结果失败:" + (e == null ? "null" : e.getMessage()));
                    ToastUtil.show(DeviceApp.getApp(), " 签到失败");
                }
            });
        }
    }

    /**
     * 提交考勤记录
     * @param userInfo 用户信息
     * @param courseSchedule 课程表
     */
    private void submitAttendanceInfo(CardholderInfoResponse userInfo, CourseSchedule courseSchedule) {
        AttendSignInRequest2 request = new AttendSignInRequest2();
        request.setAccountId(userInfo.getId());
        request.setDeviceCode(DeviceApp.getApp().getDevice().getDeviceCode());
        request.setClassNo(Integer.parseInt(courseSchedule.getClassNo()));
        request.setClassroomCode(DeviceApp.getApp().getClassroomCode());
        request.setAttenceMethod(AttendSignInRequest2.SWIPE_CARD_ATTEND_METHOD);
        request.setTimestamp(System.currentTimeMillis() + "");
        String verifyCode = VerifyUtil.getVerifyCode(request.getClassroomCode(), request.getTimestamp(), request.getAccountId());
        request.setVerifyCode(verifyCode);

        ApiManager.getCourseApi().attendSignIn(request).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleTaskObserver<ReserveInfoResponse>() {
                    @Override
                    public void onNext(ReserveInfoResponse response) {
                        RunningLog.debug("上传签到结果:" + (response != null && response.isSuccess()));
                        String attendanceMsg;
                        if (response != null && response.isSuccess()) {
                            if (Locale.getDefault().getCountry().equals(Locale.CHINA.getCountry())) {
                                attendanceMsg = userInfo.getNameCn() + " 签到成功";
                            } else {
                                attendanceMsg = userInfo.getNameEn() + " 签到成功";
                            }
                        } else {
                            if (Locale.getDefault().getCountry().equals(Locale.CHINA.getCountry())) {
                                attendanceMsg = userInfo.getNameCn() + ", " + response.getMsg();
                            } else {
                                attendanceMsg = userInfo.getNameEn() + ", " + response.getMsg();
                            }
                        }
                        // 发送考勤事件
                        EventBus.getDefault().post(new AttendanceEvent(attendanceMsg));
                        ThreadUtils.execute(() -> {
                            try {
                                // 延时，等待平台添加数据
                                Thread.sleep(800);
                                // 发送刷新考勤控件的考勤数据事件
                                EventBus.getDefault().post(new RefreshAttendanceEvent());
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable e) {
                        RunningLog.debug("上传签到结果失败:" + (e == null ? "null" : e.getMessage()));
                        if (Locale.getDefault().getCountry().equals(Locale.CHINA.getCountry())) {
                            ToastUtil.show(DeviceApp.getApp(), userInfo.getNameCn() + " 签到失败");
                        } else {
                            ToastUtil.show(DeviceApp.getApp(), userInfo.getNameEn() + " 签到失败");
                        }
                    }
                });
    }
}
