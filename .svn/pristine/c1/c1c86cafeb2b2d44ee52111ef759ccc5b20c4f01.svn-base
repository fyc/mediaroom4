package com.sunmnet.mediaroom.common.bean.course;



import com.sunmnet.mediaroom.common.tools.DateUtil;

import java.util.Date;

public class CourseTime {

    public static final String COURSE_TIME_FORMAT = "HH:mm";

    public CourseTime(String classNo, String start, String end) {
        this.classNo = classNo;
        this.start = start;
        this.end = end;
        this.key = Integer.parseInt(classNo);
    }

    private String classNo;
    private String start;
    private String end;
    private int key;

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getClassNo() {
        return classNo;
    }

    public void setClassNo(String classNo) {
        this.classNo = classNo;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public Date getStartTime(String dateStr) {
        String startTime = this.start;
        if (this.start.length() <= 5) {
            startTime += ":00";
        }
        startTime = dateStr + " " + startTime;
        return DateUtil.parseDateStr(startTime, "yyyy-MM-dd HH:mm:ss");
    }

    public Date getEndTime(String dateStr) {
        String startTime = this.end;
        if (this.end.length() <= 5) {
            startTime += ":00";
        }
        startTime = dateStr + " " + startTime;
        return DateUtil.parseDateStr(startTime, "yyyy-MM-dd HH:mm:ss");
    }
}
