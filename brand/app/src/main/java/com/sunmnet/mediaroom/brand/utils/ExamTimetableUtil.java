package com.sunmnet.mediaroom.brand.utils;

import android.text.TextUtils;

import com.sunmnet.mediaroom.common.tools.DateUtil;
import com.sunmnet.mediaroom.brand.bean.ExamTimetable;
import com.sunmnet.mediaroom.brand.bean.response.ExamDetailResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ExamTimetableUtil {

    public static List<ExamTimetable.TimeTableBean> getTodayTimetableList(ExamTimetable timetable) {
        List<ExamTimetable.TimeTableBean> tableBeanList = new ArrayList<>();
        String today = DateUtil.formatDate(new Date(), DateUtil.DEFAULT_FORMAT_DATE);
        for (ExamTimetable.TimeTableBean tableBean : timetable.getTimeTable()) {
            String examDate = tableBean.getExamDate();
            if (TextUtils.equals(examDate, today)) {
                tableBeanList.add(tableBean);
            }
        }
        return tableBeanList;
    }

    public static ExamTimetable.TimeTableBean getNearestTimetable(ExamTimetable timetable) {
        return getNearestTimetable(timetable.getTimeTable());
    }

    /**
     * 返回离当前时间最近的时间表
     */
    public static ExamTimetable.TimeTableBean getNearestTimetable(List<ExamTimetable.TimeTableBean> list) {
        String formatString = "yyyy-MM-dd HH:mm";
        long now = DateUtil.parseDateStr(DateUtil.getNowDateString(formatString), formatString).getTime();
        List<ExamTimetable.TimeTableBean> dBeanList = new ArrayList<>();
        List<Long> dList = new ArrayList<>();
        List<Long> absList = new ArrayList<>();//保存考试列表的绝对值
        List<ExamTimetable.TimeTableBean> absBeanList = new ArrayList<>();//保存绝对值最近的考试
        for (ExamTimetable.TimeTableBean tableBean : list) {
            String examDate = tableBean.getExamDate();
            String examTime = tableBean.getExamTime();
            String[] strings = examTime.split("-");
            String dateString = examDate + " " + strings[0];//开始时间
            String dateString2 = examDate + " " + strings[1];//结束时间
            Date startTime = DateUtil.parseDateStr(dateString, formatString);
            Date endTime = DateUtil.parseDateStr(dateString2, formatString);
            if (startTime == null || endTime == null) {
                absList.add(Long.MAX_VALUE);
                absBeanList.add(tableBean);
                break;
            }
            //恰好在考试的时间段里
            if (startTime.getTime() - now <= 0 && endTime.getTime() - now >= 0) {
                return tableBean;
            }
            absList.add(Math.abs(startTime.getTime() - now));
            absBeanList.add(tableBean);

            if (startTime.getTime() - now >= 0) {
                dList.add(startTime.getTime() - now);
                dBeanList.add(tableBean);
            }
        }
        if (dList.size() > 0) {//还没开始的
            ExamTimetable.TimeTableBean timeTableBean = dBeanList.get(dList.indexOf(Collections.min(dList)));
            return timeTableBean;
        }
        if (absList.size() > 0) {//包括过去的
            ExamTimetable.TimeTableBean timeTableBean = absBeanList.get(absList.indexOf(Collections.min(absList)));
            return timeTableBean;
        }
        return null;
    }


    /**
     * 过滤掉考试结束后一小时以上，或考试开始前两小时以上的考试时间
     */
    public static ExamTimetable.TimeTableBean filterTimeTable(ExamTimetable.TimeTableBean timeTableBean) {
        ExamTimetable.TimeTableBean filtered = timeTableBean;
        String formatString = "yyyy-MM-dd HH:mm";
        String examDate = timeTableBean.getExamDate();
        String examTime = timeTableBean.getExamTime();
        String[] strings = examTime.split("-");
        String startDateString = examDate + " " + strings[0];
        String endDateString = examDate + " " + strings[1];
        Date startTime = DateUtil.parseDateStr(startDateString, formatString);
        Date endTime = DateUtil.parseDateStr(endDateString, formatString);
        long nowMillis = System.currentTimeMillis();
        if (endTime != null && startTime != null) {
            if (nowMillis - endTime.getTime() >= TimeUnit.HOURS.toMillis(1)
                    || startTime.getTime() - nowMillis >= TimeUnit.HOURS.toMillis(2)) {
                filtered = null;
            }
        }
        return filtered;
    }

    public static ExamDetailResponse.ExamDetail getNearestExam(List<ExamDetailResponse.ExamDetail> list) {
        String formatString = "yyyy-MM-dd HH:mm";
        long now = DateUtil.parseDateStr(DateUtil.getNowDateString(formatString), formatString).getTime();
        List<ExamDetailResponse.ExamDetail> dBeanList = new ArrayList<>();
        List<Long> dList = new ArrayList<>();
        List<Long> absList = new ArrayList<>();//保存考试列表的绝对值
        List<ExamDetailResponse.ExamDetail> absBeanList = new ArrayList<>();//保存绝对值最近的考试
        for (ExamDetailResponse.ExamDetail tableBean : list) {
            String examDate = tableBean.getExamDate();
            String dateString = examDate + " " + tableBean.getExamTimeStart();//开始时间
            String dateString2 = examDate + " " + tableBean.getExamTimeEnd();//结束时间
            Date startTime = DateUtil.parseDateStr(dateString, formatString);
            Date endTime = DateUtil.parseDateStr(dateString2, formatString);
            if (startTime == null || endTime == null) {
                absList.add(Long.MAX_VALUE);
                absBeanList.add(tableBean);
                break;
            }
            //恰好在考试的时间段里
            if (startTime.getTime() - now <= 0 && endTime.getTime() - now >= 0) {
                return tableBean;
            }
            absList.add(Math.abs(startTime.getTime() - now));
            absBeanList.add(tableBean);

            if (startTime.getTime() - now >= 0) {
                dList.add(startTime.getTime() - now);
                dBeanList.add(tableBean);
            }
        }
        if (dList.size() > 0) {//还没开始的
            ExamDetailResponse.ExamDetail timeTableBean = dBeanList.get(dList.indexOf(Collections.min(dList)));
            return timeTableBean;
        }
        if (absList.size() > 0) {//包括过去的
            ExamDetailResponse.ExamDetail timeTableBean = absBeanList.get(absList.indexOf(Collections.min(absList)));
            return timeTableBean;
        }
        return null;
    }

    /**
     * 获取下一场考试
     *
     * @param list
     * @return
     */
    public static ExamDetailResponse.ExamDetail getNextExam(List<ExamDetailResponse.ExamDetail> list) {
        String formatString = "yyyy-MM-dd HH:mm";
        long now = DateUtil.parseDateStr(DateUtil.getNowDateString(formatString), formatString).getTime();
        List<ExamDetailResponse.ExamDetail> dBeanList = new ArrayList<>();
        List<Long> dList = new ArrayList<>();
        List<Long> absList = new ArrayList<>();//保存考试列表的绝对值
        List<ExamDetailResponse.ExamDetail> absBeanList = new ArrayList<>();//保存绝对值最近的考试
        for (ExamDetailResponse.ExamDetail tableBean : list) {
            String examDate = tableBean.getExamDate();
            String dateString = examDate + " " + tableBean.getExamTimeStart();//开始时间
            String dateString2 = examDate + " " + tableBean.getExamTimeEnd();//结束时间
            Date startTime = DateUtil.parseDateStr(dateString, formatString);
            Date endTime = DateUtil.parseDateStr(dateString2, formatString);
            if (startTime == null || endTime == null) {
                absList.add(Long.MAX_VALUE);
                absBeanList.add(tableBean);
                break;
            }
            absList.add(Math.abs(startTime.getTime() - now));
            absBeanList.add(tableBean);

            if (startTime.getTime() - now >= 0) {
                dList.add(startTime.getTime() - now);
                dBeanList.add(tableBean);
            }
        }
        if (dList.size() > 0) {//还没开始的
            ExamDetailResponse.ExamDetail timeTableBean = dBeanList.get(dList.indexOf(Collections.min(dList)));
            return timeTableBean;
        }
        return null;
    }

    public static ExamDetailResponse.ExamDetail filterTimeTable(ExamDetailResponse.ExamDetail timeTableBean, long before, long after) {
        ExamDetailResponse.ExamDetail filtered = timeTableBean;
        String formatString = "yyyy-MM-dd HH:mm";
        String examDate = timeTableBean.getExamDate();
        String startDateString = examDate + " " + timeTableBean.getExamTimeStart();//开始时间
        String endDateString = examDate + " " + timeTableBean.getExamTimeEnd();//结束时间
        Date startTime = DateUtil.parseDateStr(startDateString, formatString);
        Date endTime = DateUtil.parseDateStr(endDateString, formatString);
        long nowMillis = System.currentTimeMillis();
        if (endTime != null && startTime != null) {
            if (nowMillis - endTime.getTime() >= after
                    || startTime.getTime() - nowMillis >= before) {
                filtered = null;
            }
        }
        return filtered;
    }

    public static ExamDetailResponse.ExamDetail filterTimeTable(ExamDetailResponse.ExamDetail timeTableBean) {
        return filterTimeTable(timeTableBean, TimeUnit.HOURS.toMillis(2), TimeUnit.HOURS.toMillis(1));
    }

    public static boolean isNow(ExamDetailResponse.ExamDetail examDetail) {
        String formatString = "yyyy-MM-dd HH:mm";
        String examDate = examDetail.getExamDate();
        String startDateString = examDate + " " + examDetail.getExamTimeStart();//开始时间
        String endDateString = examDate + " " + examDetail.getExamTimeEnd();//结束时间
        Date startTime = DateUtil.parseDateStr(startDateString, formatString);
        Date endTime = DateUtil.parseDateStr(endDateString, formatString);
        long nowMillis = System.currentTimeMillis();
        if (endTime != null && startTime != null) {
            if (nowMillis >= startTime.getTime() && nowMillis <= endTime.getTime()) {
                return true;
            }
        }
        return false;
    }

    public static Date getStartTime(ExamDetailResponse.ExamDetail examDetail) {
        String formatString = "yyyy-MM-dd HH:mm";
        String examDate = examDetail.getExamDate();
        String startDateString = examDate + " " + examDetail.getExamTimeStart();
        return DateUtil.parseDateStr(startDateString, formatString);
    }

    public static Date getEndTime(ExamDetailResponse.ExamDetail examDetail) {
        String formatString = "yyyy-MM-dd HH:mm";
        String examDate = examDetail.getExamDate();
        String endDateString = examDate + " " + examDetail.getExamTimeEnd();
        return DateUtil.parseDateStr(endDateString, formatString);
    }

}
