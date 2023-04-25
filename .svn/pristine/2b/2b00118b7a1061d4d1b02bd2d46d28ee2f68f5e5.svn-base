package com.sunmnet.mediaroom.brand.bean.item;

import java.util.ArrayList;
import java.util.List;

public class ClassroomStatusCell {

    private List<DrawStatus> drawStatusList;
    private List<ClassroomStatus> statusList;

    private int width;
    private int height;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<DrawStatus> getDrawStatusList() {
        return drawStatusList;
    }

    public void setDrawStatusList(List<DrawStatus> drawStatusList) {
        this.drawStatusList = drawStatusList;
    }

    public void addDrawStatus(DrawStatus drawStatus) {
        if (drawStatusList == null) {
            drawStatusList = new ArrayList<>();
        }
        drawStatusList.add(drawStatus);
    }

    public void clearDrawStatus() {
        if (drawStatusList != null) {
            drawStatusList.clear();
        }
    }

    public List<ClassroomStatus> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<ClassroomStatus> statusList) {
        this.statusList = statusList;
    }

    public void addClassroomStatus(ClassroomStatus drawStatus) {
        if (statusList == null) {
            statusList = new ArrayList<>();
        }
        statusList.add(drawStatus);
    }

    public void clearClassroomStatus() {
        if (statusList != null) {
            statusList.clear();
        }
    }

    public static class ClassroomStatus {
        private String startTime;
        private String endTime;
        private int type;
    }

    public static class DrawStatus {
        private float startPercent;
        private float endPercent;
        private int color;

        public float getStartPercent() {
            return startPercent;
        }

        public void setStartPercent(float startPercent) {
            this.startPercent = startPercent;
        }

        public float getEndPercent() {
            return endPercent;
        }

        public void setEndPercent(float endPercent) {
            this.endPercent = endPercent;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }
    }

}
