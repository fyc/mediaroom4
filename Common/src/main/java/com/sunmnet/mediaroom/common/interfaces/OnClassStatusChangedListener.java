package com.sunmnet.mediaroom.common.interfaces;


import com.sunmnet.mediaroom.common.bean.course.CourseSchedule;

/**
 * Created by dengzl_pc on 2018/3/30.
 */

public interface OnClassStatusChangedListener {
    public void onClassOver(CourseSchedule rest);

    public void onClassOn(String classNo);

    public void reload();
}
