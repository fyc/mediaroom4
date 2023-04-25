package com.sunmnet.mediaroom.brand.api.impl;

import com.sunmnet.mediaroom.brand.api.service.CourseService;
import com.sunmnet.mediaroom.brand.bean.request.AttendSignInRequest2;
import com.sunmnet.mediaroom.brand.bean.request.TeachingInspectionAnswerRequest;
import com.sunmnet.mediaroom.brand.bean.response.AttendResultResponse;
import com.sunmnet.mediaroom.brand.bean.response.BorrowInfoResponse;
import com.sunmnet.mediaroom.brand.bean.response.BuildingClassroomStatusResponse;
import com.sunmnet.mediaroom.brand.bean.response.BuildingResponse;
import com.sunmnet.mediaroom.brand.bean.response.ClassroomResponse;
import com.sunmnet.mediaroom.brand.bean.response.ClassroomStatusResponse;
import com.sunmnet.mediaroom.brand.bean.response.DepartmentGradeTreeResponse;
import com.sunmnet.mediaroom.brand.bean.response.FloorResponse;
import com.sunmnet.mediaroom.brand.bean.response.IntegerResponse;
import com.sunmnet.mediaroom.brand.bean.response.QueryCourseInfoResponse;
import com.sunmnet.mediaroom.brand.bean.response.ReserveInfoResponse;
import com.sunmnet.mediaroom.brand.bean.response.StringResponse;
import com.sunmnet.mediaroom.brand.bean.response.TeachingInspectionTemplateDetailResponse;
import com.sunmnet.mediaroom.brand.bean.response.UserCourseScheduleResponse;
import com.sunmnet.mediaroom.brand.bean.response.UserTeachingInspectionTemplateResponse;
import com.sunmnet.mediaroom.brand.bean.response.dto.AttendanceQrCodeDto;
import com.sunmnet.mediaroom.brand.utils.RetrofitManager;
import com.sunmnet.mediaroom.common.bean.Result;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class CourseApi implements CourseService {

    private CourseService service;

    private CourseService getApiService() {
        if (service == null) {
            service = RetrofitManager.getPLRetrofit() == null ? null : RetrofitManager.getPLRetrofit().create(CourseService.class);
        }
        return service;
    }

    @Override
    public Observable<StringResponse> getCampusList() {
        return getApiService().getCampusList();
    }

    @Override
    public Observable<BuildingResponse> getBuildingList(String campusCode) {
        return getApiService() == null ? null : getApiService().getBuildingList(campusCode).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<FloorResponse> getFloorList(String campusCode, String buildCode) {
        return getApiService() == null ? null : getApiService().getFloorList(campusCode, buildCode).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<ClassroomResponse> getClassroomList(String campusCode, String buildCode, String floorCode) {
        return getApiService() == null ? null : getApiService().getClassroomList(campusCode, buildCode, floorCode).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<QueryCourseInfoResponse> getCourseList(String date, String studentName, String teacherName, String className, String classroomName) {
        return getApiService() == null ? null : getApiService().getCourseList(date, studentName, teacherName, className, classroomName).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<QueryCourseInfoResponse> getCourseList(String date, Map<String, String> map) {
        return getApiService() == null ? null : getApiService().getCourseList(date, map).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<UserCourseScheduleResponse> getCourseScheduleByUser(String token, String accountId, Integer weekNum) {
        return getApiService() == null ? null : getApiService().getCourseScheduleByUser(token, accountId, weekNum).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<DepartmentGradeTreeResponse> getDepartmentGradeTree() {
        return getApiService() == null ? null : getApiService().getDepartmentGradeTree().subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<ClassroomStatusResponse> getClassroomStatus(String classroomCode) {
        return getApiService() == null ? null : getApiService().getClassroomStatus(classroomCode).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BuildingClassroomStatusResponse> getBuildingClassroomStatus(String buildCode) {
        return getApiService() == null ? null : getApiService().getBuildingClassroomStatus(buildCode).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<UserTeachingInspectionTemplateResponse> getTeachingInspectionTemplateList(String token) {
        return getApiService() == null ? null : getApiService().getTeachingInspectionTemplateList(token).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<TeachingInspectionTemplateDetailResponse> getTeachingInspectionTemplateDetail(String token, String templateId) {
        return getApiService() == null ? null : getApiService().getTeachingInspectionTemplateDetail(token, templateId).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<StringResponse> submitTeachingInspectionAnswer(String token, TeachingInspectionAnswerRequest request) {
        return getApiService() == null ? null : getApiService().submitTeachingInspectionAnswer(token, request).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<IntegerResponse> getAttendanceArriveNo(String classroomCode, String classNo) {
        return getApiService() == null ? null : getApiService().getAttendanceArriveNo(classroomCode, classNo).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<IntegerResponse> getAttendanceLeaveNo(String classroomCode, String classNo) {
        return getApiService() == null ? null : getApiService().getAttendanceLeaveNo(classroomCode, classNo).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<IntegerResponse> getAttendanceOnTimeNo(String classroomCode, String classNo) {
        return getApiService() == null ? null : getApiService().getAttendanceOnTimeNo(classroomCode, classNo).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<IntegerResponse> getAttendanceLateNo(String classroomCode, String classNo) {
        return getApiService() == null ? null : getApiService().getAttendanceLateNo(classroomCode, classNo).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<IntegerResponse> getAttendanceShouldArriveNo(String classroomCode, String classNo) {
        return getApiService() == null ? null : getApiService().getAttendanceShouldArriveNo(classroomCode, classNo).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<AttendResultResponse> getStudentAttendanceResult(String classroomCode, String gradeCode, String courseDate, String classNo) {
        return getApiService() == null ? null : getApiService().getStudentAttendanceResult(classroomCode, gradeCode, courseDate, classNo).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<Result<AttendanceQrCodeDto>> getAttendQrCode(String classroomCode) {
        return getApiService() == null ? null : getApiService().getAttendQrCode(classroomCode).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BorrowInfoResponse> getClassroomBorrowInfo(String classroomCode) {
        return getApiService() == null ? null : getApiService().getClassroomBorrowInfo(classroomCode).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<ReserveInfoResponse> getClassroomReserveInfo(String classroomCode) {
        return getApiService() == null ? null : getApiService().getClassroomReserveInfo(classroomCode).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<ReserveInfoResponse> attendSignIn(AttendSignInRequest2 request) {
        return getApiService() == null ? null : getApiService().attendSignIn(request).subscribeOn(Schedulers.io());
    }

}
