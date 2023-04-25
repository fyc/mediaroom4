package com.sunmnet.mediaroom.brand.bean.item;

import com.bin.david.form.annotation.SmartColumn;
import com.bin.david.form.annotation.SmartTable;
import com.sunmnet.mediaroom.brand.bean.response.QueryCourseInfoResponse;

@SmartTable(name = "考场查询结果")
public class QueryExamRoomResultItem {

    @SmartColumn(id = 1, name = "考试日期",autoMerge = true)
    private String date;

    @SmartColumn(id = 2, name = "考试时间")
    private String time;

    @SmartColumn(id = 3, name = "科目")
    private String subject;

    @SmartColumn(id = 4, name = "考场")
    private String room;

    @SmartColumn(id = 5, name = "座位号")
    private String seat;

    public QueryExamRoomResultItem() {
    }

    public QueryExamRoomResultItem(String date, String time, String subject, String room, String seat) {
        this.date = date;
        this.time = time;
        this.subject = subject;
        this.room = room;
        this.seat = seat;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }
}
