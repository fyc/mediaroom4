package com.sunmnet.mediaroom.brand.utils;

import android.content.Context;

import com.sunmnet.mediaroom.brand.bean.control.button.CourseQueryControlProperty;
import com.sunmnet.mediaroom.brand.bean.control.button.ExamRoomQueryControlProperty;
import com.sunmnet.mediaroom.brand.bean.control.button.FaceAttendControlProperty;
import com.sunmnet.mediaroom.brand.bean.control.button.CardAttendControlProperty;
import com.sunmnet.mediaroom.brand.control.button.CourseQueryControl;
import com.sunmnet.mediaroom.brand.control.button.ExamRoomQueryControl;
import com.sunmnet.mediaroom.brand.control.button.FaceAttendControl;
import com.sunmnet.mediaroom.brand.control.button.CardAttendControl;
import com.sunmnet.mediaroom.brand.control.media.*;
import com.sunmnet.mediaroom.brand.control.other.*;
import com.sunmnet.mediaroom.brand.control.table.*;
import com.sunmnet.mediaroom.brand.control.text.*;
import com.sunmnet.mediaroom.brand.control.weather.WeatherControl;
import com.sunmnet.mediaroom.brand.interfaces.control.IBaseControl;
import com.sunmnet.mediaroom.brand.bean.control.base.ControlProperty;
import com.sunmnet.mediaroom.brand.bean.control.media.*;
import com.sunmnet.mediaroom.brand.bean.control.other.*;
import com.sunmnet.mediaroom.brand.bean.control.table.*;
import com.sunmnet.mediaroom.brand.bean.control.text.*;
import com.sunmnet.mediaroom.common.tools.JacksonUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ControlBuilder {

    private static HashMap<String, Class<? extends IBaseControl>> controlClass = new HashMap<>();
    private static HashMap<Class<? extends IBaseControl>, Class<? extends ControlProperty>> controlProperty = new HashMap<>();

    static {

        controlClass.put("courseTeacher", CourseTeacherControl.class);//任课老师
        controlClass.put("courseName", CourseNameControl.class);//课程名
        controlClass.put("classroom", ClassroomControl.class);//教室
        controlClass.put("week", WeekControl.class);//星期
        controlClass.put("classNo", ClassNoControl.class);//课节
        controlClass.put("courseTime", CourseTimeControl.class);//课程时间
        controlClass.put("gradeName", GradeNameControl.class);//班级
        controlClass.put("arriveNo", ArriveNoControl.class);//实到人数
        controlClass.put("shouldArriveNo", ShouldArriveNoControl.class);//应到人数
        controlClass.put("lateNo", LateNoControl.class);//迟到人数
        controlClass.put("leaveNo", LeaveNoControl.class);//请假人数
        controlClass.put("capacityNo", CapacityNoControl.class);//容纳人数
        controlClass.put("onTimeNo", OnTimeNoControl.class);//正常签到人数
        controlClass.put("time", TimeControl.class);//时间
        controlClass.put("date", DateControl.class);//日期
        controlClass.put("text", TextControl.class);//文本
        controlClass.put("semesterWeeks", SemesterWeeksControl.class);//学周

        controlClass.put("examRound", ExamRoundControl.class);//考试场次
        controlClass.put("examLocation", ExamLocationControl.class);//考试地点
        controlClass.put("examTime", ExamTimeControl.class);//考试时间
        controlClass.put("examDate", ExamDateControl.class);//考试日期
        controlClass.put("examCourse", ExamCourseControl.class);//考试课程
        controlClass.put("examCourseTeacher", ExamCourseTeacherControl.class);//考试-任课老师
        controlClass.put("examClass", ExamClassControl.class);//考试班级
        controlClass.put("examPeople", ExamPeopleControl.class);//考试人数
        controlClass.put("examInvigilator", ExamInvigilatorControl.class);//监考老师
        controlClass.put("examTimeToStart", ExamTimeToStartControl.class);//离考试开始时间
        controlClass.put("examTimeToEnd", ExamTimeToEndControl.class);//离考试结束时间
        controlClass.put("examTimeToStartToEnd", ExamTimeToStartToEndControl.class);//离考试开始时间、结束时间轮播
        controlClass.put("examRoom", ExamRoomControl.class);//考场名称
        controlClass.put("examStudents", ExamStudentsControl.class);//考生姓名


        controlClass.put("browser", BrowserControl.class);//网页
        controlClass.put("stream", StreamControl.class);//流媒体
        controlClass.put("videoMonitor", VideoMonitorControl.class);//视频监控

        controlClass.put("weekCourseTable1", WeekCourseTableControl1.class);//周课程表1
        controlClass.put("weekCourseTable2", WeekCourseTableControl2.class);//周课程表2
        controlClass.put("weekCourseTable", WeekCourseTableControl.class);//周课程表
        controlClass.put("dayCourseTable", DayCourseTableControl.class);//日课程表
        controlClass.put("table", TableControl.class);//表格

        controlClass.put("weather", WeatherControl.class);//天气
        controlClass.put("shape", ShapeControl.class);//形状（矩形框）
        controlClass.put("attendanceQRCode", AttendanceQRCodeControl.class);//考勤二维码

        controlClass.put("picture", PictureControl.class);//图片
        controlClass.put("music", MusicControl.class);//音乐
        controlClass.put("video", VideoControl.class);//视频

        controlClass.put("teacherPortrait", TeacherPortraitControl.class);//老师头像

        controlClass.put("courseQuery", CourseQueryControl.class);//课表查询
        controlClass.put("examRoomQuery", ExamRoomQueryControl.class);//考场查询
        controlClass.put("faceAttend", FaceAttendControl.class);//人脸签到
        controlClass.put("borrowInfo", BorrowInfoControl.class);//教室借用信息
        controlClass.put("reserveInfo", ReserveInfoControl.class);//教室预约信息
        controlClass.put("cardAttend", CardAttendControl.class); // 刷卡签到

        controlProperty.put(CourseTeacherControl.class, CourseTeacherControlProperty.class);
        controlProperty.put(CourseNameControl.class, CourseNameControlProperty.class);
        controlProperty.put(ClassroomControl.class, ClassroomControlProperty.class);
        controlProperty.put(WeekControl.class, WeekControlProperty.class);
        controlProperty.put(ClassNoControl.class, ClassNoControlProperty.class);
        controlProperty.put(CourseTimeControl.class, CourseTimeControlProperty.class);
        controlProperty.put(GradeNameControl.class, GradeNameControlProperty.class);
        controlProperty.put(ArriveNoControl.class, ArriveNoControlProperty.class);
        controlProperty.put(ShouldArriveNoControl.class, ShouldArriveNoControlProperty.class);
        controlProperty.put(LateNoControl.class, LateNoControlProperty.class);
        controlProperty.put(LeaveNoControl.class, LeaveNoControlProperty.class);
        controlProperty.put(CapacityNoControl.class, CapacityNoControlProperty.class);
        controlProperty.put(OnTimeNoControl.class, OnTimeNoControlProperty.class);
        controlProperty.put(TimeControl.class, TimeControlProperty.class);
        controlProperty.put(DateControl.class, DateControlProperty.class);
        controlProperty.put(TextControl.class, TextControlProperty.class);

        controlProperty.put(ExamRoundControl.class, ExamRoundControlProperty.class);
        controlProperty.put(ExamLocationControl.class, ExamLocationControlProperty.class);
        controlProperty.put(ExamTimeControl.class, ExamTimeControlProperty.class);
        controlProperty.put(ExamDateControl.class, ExamDateControlProperty.class);
        controlProperty.put(ExamCourseControl.class, ExamCourseControlProperty.class);
        controlProperty.put(ExamCourseTeacherControl.class, ExamCourseTeacherControlProperty.class);
        controlProperty.put(ExamClassControl.class, ExamClassControlProperty.class);
        controlProperty.put(ExamPeopleControl.class, ExamPeopleControlProperty.class);
        controlProperty.put(ExamInvigilatorControl.class, ExamInvigilatorControlProperty.class);
        controlProperty.put(ExamTimeToStartControl.class, ExamTimeToStartControlProperty.class);
        controlProperty.put(ExamTimeToEndControl.class, ExamTimeToEndControlProperty.class);
        controlProperty.put(ExamTimeToStartToEndControl.class, ExamTimeToStartToEndControlProperty.class);
        controlProperty.put(ExamRoomControl.class, ExamRoomControlProperty.class);

        controlProperty.put(BrowserControl.class, BrowserControlProperty.class);
        controlProperty.put(StreamControl.class, StreamControlProperty.class);
        controlProperty.put(VideoMonitorControl.class, VideoMonitorControlProperty.class);

        controlProperty.put(WeekCourseTableControl1.class, WeekCourseTableControlProperty.class);
        controlProperty.put(WeekCourseTableControl2.class, WeekCourseTableControlProperty.class);
        controlProperty.put(WeekCourseTableControl.class, WeekCourseTableControlProperty.class);
        controlProperty.put(DayCourseTableControl.class, CourseTableControlProperty.class);
        controlProperty.put(TableControl.class, TableControlProperty.class);


        controlProperty.put(WeatherControl.class, WeatherControlProperty.class);
        controlProperty.put(ShapeControl.class, ShapeControlProperty.class);
        controlProperty.put(AttendanceQRCodeControl.class, AttendanceQRCodeControlProperty.class);

        controlProperty.put(PictureControl.class, PictureControlProperty.class);
        controlProperty.put(MusicControl.class, MusicControlProperty.class);
        controlProperty.put(VideoControl.class, VideoControlProperty.class);

        controlProperty.put(TeacherPortraitControl.class, TeacherPortraitControlProperty.class);

        controlProperty.put(CourseQueryControl.class, CourseQueryControlProperty.class);
        controlProperty.put(ExamRoomQueryControl.class, ExamRoomQueryControlProperty.class);
        controlProperty.put(FaceAttendControl.class, FaceAttendControlProperty.class);
        controlProperty.put(BorrowInfoControl.class, BorrowInfoControlProperty.class);
        controlProperty.put(ReserveInfoControl.class, ReserveInfoControlProperty.class);
        controlProperty.put(SemesterWeeksControl.class, SemesterWeeksControlProperty.class);
        controlProperty.put(ExamStudentsControl.class, ExamStudentsControlProperty.class);
        controlProperty.put(CardAttendControl.class, CardAttendControlProperty.class);
    }

    public static IBaseControl createControl(Context context, String type) {
        try {
            Class<? extends IBaseControl> c = controlClass.get(type);
            if (c != null)
                return c.getConstructor(Context.class).newInstance(context);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ControlProperty mapToProperty(Map map, Class<? extends IBaseControl> controlClass) {
        Class<? extends ControlProperty> css = controlProperty.get(controlClass);
        if (css != null) {
            return JacksonUtil.mapToBean(map, css);
        }
        return null;
    }

    public static void addControl(String type, Class<? extends IBaseControl> theClass) {
        controlClass.put(type, theClass);
    }

    public static void removeControl(String type) {
        controlClass.remove(type);
    }

    public static Class<? extends IBaseControl> getControlClass(String type) {
        return controlClass.get(type);
    }

    public static Class<? extends ControlProperty> getPropertyClass(Class<? extends IBaseControl> cz) {
        return controlProperty.get(cz);
    }
}
