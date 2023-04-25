package com.sunmnet.mediaroom.brand.impl.attendance;

import android.annotation.SuppressLint;

import com.sunmnet.mediaroom.brand.api.ApiManager;
import com.sunmnet.mediaroom.brand.bean.response.StringResponse;
import com.sunmnet.mediaroom.brand.common.base.DeviceApp;
import com.sunmnet.mediaroom.brand.impl.swipeCard.SwipeCardContext;
import com.sunmnet.mediaroom.brand.rx.SingleTaskObserver;
import com.sunmnet.mediaroom.common.bean.course.CourseSchedule;
import com.sunmnet.mediaroom.common.tools.CourseHelper;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;

/**
 * Create by WangJincheng on 2021-11-12
 * 静默刷卡
 */

public class SilentSwipeCardAttendance extends AbstractAttendance {

    @Override
    @SuppressLint("SimpleDateFormat")
    public <T> void attendance(T cardNumber) {
        if (cardNumber instanceof String) {
            HashMap<String, Object> requestBody = new HashMap<>();
            requestBody.put("cardNum", cardNumber);
            Date nowDate = new Date();
            CourseSchedule courseSchedule = CourseHelper.getDefault().getCourseSchedule(nowDate, 0, "0", 0);
            if (courseSchedule == null) {
                RunningLog.run("当前不在考勤时间内");
            } else {
                if (courseSchedule.getClassNo() != null) {
                    requestBody.put("classNo", Integer.parseInt(courseSchedule.getClassNo()));
                }
                if (courseSchedule.getStartTime() != null) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    requestBody.put("courseDate", simpleDateFormat.format(courseSchedule.getStartTime()));
                }
                if (courseSchedule.getCourseName() != null) {
                    requestBody.put("courseName", courseSchedule.getCourseName());
                }
                if (courseSchedule.getTeacherName() != null) {
                    requestBody.put("courseTeacher", courseSchedule.getTeacherName());
                }
            }
            if (DeviceApp.getApp().getDevice().getClassroomCode() != null) {
                requestBody.put("classroomCode", DeviceApp.getApp().getDevice().getClassroomCode());
            }
            requestBody.put("signMethod", 1); // 1-刷卡，2-人脸识别
            ApiManager.getDeviceApi().insertAttendanceLog(requestBody).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleTaskObserver<StringResponse>() {
                @Override
                public void onNext(@NonNull StringResponse stringResponse) {
                    RunningLog.debug("上传静默刷卡结果:" + (stringResponse != null && stringResponse.isSuccess()));
                    ToastUtil.show(DeviceApp.getApp(), stringResponse.getMsg());
                    recoverSwipeCard();
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    ToastUtil.show(DeviceApp.getApp(), "提交记录失败");
                    recoverSwipeCard();
                }
            });
        }
    }

    /**
     * 恢复刷卡功能为普通功能
     */
    private void recoverSwipeCard() {
        SwipeCardContext.getInstance().recovery();
    }

}
