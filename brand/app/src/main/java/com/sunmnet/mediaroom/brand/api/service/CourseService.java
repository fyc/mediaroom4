package com.sunmnet.mediaroom.brand.api.service;

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
import com.sunmnet.mediaroom.common.bean.Result;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface CourseService {

    /**
     * 获取校区列表
     */
    @GET("course-svr/api/campusList")
    Observable<StringResponse> getCampusList();

    /**
     * 获取楼栋列表
     */
    @GET("course-svr/api/buildList")
    Observable<BuildingResponse> getBuildingList(@Query("campusCode") String campusCode);

    /**
     * 获取楼层列表
     */
    @GET("course-svr/api/floorList")
    Observable<FloorResponse> getFloorList(@Query("campusCode") String campusCode, @Query("buildCode") String buildCode);

    /**
     * 获取教室列表
     */
    @GET("course-svr/api/classroomList")
    Observable<ClassroomResponse> getClassroomList(@Query("campusCode") String campusCode, @Query("buildCode") String buildCode, @Query("floorCode") String floorCode);

    /**
     * 按条件查询课程
     */
    @GET("course-svr/api/courseList")
    Observable<QueryCourseInfoResponse> getCourseList(@Query("date") String date, @Query("studentName") String studentName,
                                                      @Query("teacherName") String teacherName, @Query("className") String className,
                                                      @Query("classroomName") String classroomName);

    /**
     * 按条件查询课程
     */
    @GET("course-svr/api/courseList")
    Observable<QueryCourseInfoResponse> getCourseList(@Query("date") String date, @QueryMap Map<String, String> map);

    /**
     * 获取系别班级信息
     */
    @GET("course-svr/api/getDepartmentGradeTree")
    Observable<DepartmentGradeTreeResponse> getDepartmentGradeTree();

    /**
     * 获取用户的课程表
     */
    @GET("course-svr/courseInfo/findWeekModeCourseTableByUser")
    Observable<UserCourseScheduleResponse> getCourseScheduleByUser(@Header("token") String token, @Query("accountId") String accountId, @Query("weekNum") Integer weekNum);

    /**
     * 查询教室状态（当周）
     */
    @GET("course-svr/api/findClassroomStatusByWeekMode")
    Observable<ClassroomStatusResponse> getClassroomStatus(@Query("classroomCode") String classroomCode);

    /**
     * 查询楼栋教室状态（实时）
     */
    @GET("course-svr/api/findClassroomStatusByBuild")
    Observable<BuildingClassroomStatusResponse> getBuildingClassroomStatus(@Query("buildCode") String buildCode);

    /**
     * 获取登录用户可使用教学巡查模板列表
     */
    @GET("course-svr/teachInspectionTemplate/getUserTemplate")
    Observable<UserTeachingInspectionTemplateResponse> getTeachingInspectionTemplateList(@Header("token") String token);

    /**
     * 获取教学巡查模板详情
     */
    @GET("course-svr/teachInspectionTemplate/detail")
    Observable<TeachingInspectionTemplateDetailResponse> getTeachingInspectionTemplateDetail(@Header("token") String token, @Query("id") String templateId);

    /**
     * 提交保存教学巡查记录
     */
    @POST("course-svr/teachInspectionList/save")
    Observable<StringResponse> submitTeachingInspectionAnswer(@Header("token") String token, @Body TeachingInspectionAnswerRequest request);

    /**
     * 实到人数
     */
    @GET("course-svr/api/attendanceArriveNo")
    Observable<IntegerResponse> getAttendanceArriveNo(@Query("classroomCode") String classroomCode, @Query("classNo") String classNo);

    /**
     * 请假人数
     */
    @GET("course-svr/api/attendanceLeaveNo")
    Observable<IntegerResponse> getAttendanceLeaveNo(@Query("classroomCode") String classroomCode, @Query("classNo") String classNo);

    /**
     * 正常签到人数
     */
    @GET("course-svr/api/attendanceOnTimeNo")
    Observable<IntegerResponse> getAttendanceOnTimeNo(@Query("classroomCode") String classroomCode, @Query("classNo") String classNo);

    /**
     * 迟到人数
     */
    @GET("course-svr/api/attendanceLateNo")
    Observable<IntegerResponse> getAttendanceLateNo(@Query("classroomCode") String classroomCode, @Query("classNo") String classNo);

    /**
     * 应到人数
     */
    @GET("course-svr/api/attendanceShouldArriveNo")
    Observable<IntegerResponse> getAttendanceShouldArriveNo(@Query("classroomCode") String classroomCode, @Query("classNo") String classNo);

    /**
     * 考勤结果
     *
     * @param classroomCode 必须
     * @param gradeCode     必须
     * @param courseDate
     * @param classNo
     * @return
     */
    @GET("course-svr/api/getStudentAttendanceResult")
    Observable<AttendResultResponse> getStudentAttendanceResult(@Query("classroomCode") String classroomCode, @Query("gradeCode") String gradeCode,
                                                          @Query("courseDate") String courseDate, @Query("classNo") String classNo);

    /**
     * 考勤二维码
     */
    @GET("course-svr/api/attendanceQrCode")
    Observable<Result<AttendanceQrCodeDto>> getAttendQrCode(@Query("classroomCode") String classroomCode);


    /**
     * 教室借用信息
     */
    @GET("course-svr/api/classroomBorrowList")
    Observable<BorrowInfoResponse> getClassroomBorrowInfo(@Query("classroomCode") String classroomCode);


    /**
     * 预约信息
     */
    @GET("course-svr/api/classroomAppointmentList")
    Observable<ReserveInfoResponse> getClassroomReserveInfo(@Query("classroomCode") String classroomCode);


    /**
     * 考勤签到
     */
    @POST("course-svr/api/clockIn")
    Observable<ReserveInfoResponse> attendSignIn(@Body AttendSignInRequest2 request);
}
