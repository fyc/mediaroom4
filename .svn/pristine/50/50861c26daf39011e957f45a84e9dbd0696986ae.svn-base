package com.sunmnet.mediaroom.common.enums;

/**
 * Created by dengzl_pc on 2018/1/31.
 */

public enum CourseListType {
    FREE_LIST("normal", "显示空闲课程，并根据课节数逐一显示"),
    NO_FREE_LIST("no_free_list", "不显示空闲课程"),
    FREE_COMBINE("free_combine", "合并相邻并相同的课程，并且显示空闲课程"),
    NO_FREE_COMBINE("no_free_combine", "不显示空闲课程，并且合并"),
    FREE_COMBINE_BYCOUNT("free_combine_count","显示空闲课程，并根据指定合并数量显示"),
    NO_FREE_COMBINE_BYCOUNT("no_free_combine_count","不显示空闲课程，指定合并数量");
    private final String name;
    private final String desc;

    public String getName() {
        return name;
    }
    public String getDesc() {
        return desc;
    }

    private CourseListType(String name, String desc){
        this.name = name;
        this.desc = desc;
    }
}
