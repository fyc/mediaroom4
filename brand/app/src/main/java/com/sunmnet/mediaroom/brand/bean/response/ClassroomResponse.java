package com.sunmnet.mediaroom.brand.bean.response;

import com.sunmnet.mediaroom.common.bean.Result;

import java.io.Serializable;
import java.util.List;

public class ClassroomResponse extends Result<List<ClassroomResponse.Result>> {

    public static class Result implements Serializable {

        /**
         * id : d1765bc38eed4b31ac8c5c90651278f8
         * classroomCode : H1-0605
         * campusCode : H
         * buildCode : H1
         * floorCode : H1-06
         * campusName : 海珠校区
         * buildName : 琶洲新村
         * floorName : 第H1-06层
         * classroomName : 琶洲新村-06楼05
         * classroomSeatNum : 1
         * classroomLength : 1
         * classroomWidth : 1
         * classroomHeight : 1
         */

        private String id;
        private String classroomCode;
        private String campusCode;
        private String buildCode;
        private String floorCode;
        private String campusName;
        private String buildName;
        private String floorName;
        private String classroomName;
        private int classroomSeatNum;
        private int classroomLength;
        private int classroomWidth;
        private int classroomHeight;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getClassroomCode() {
            return classroomCode;
        }

        public void setClassroomCode(String classroomCode) {
            this.classroomCode = classroomCode;
        }

        public String getCampusCode() {
            return campusCode;
        }

        public void setCampusCode(String campusCode) {
            this.campusCode = campusCode;
        }

        public String getBuildCode() {
            return buildCode;
        }

        public void setBuildCode(String buildCode) {
            this.buildCode = buildCode;
        }

        public String getFloorCode() {
            return floorCode;
        }

        public void setFloorCode(String floorCode) {
            this.floorCode = floorCode;
        }

        public String getCampusName() {
            return campusName;
        }

        public void setCampusName(String campusName) {
            this.campusName = campusName;
        }

        public String getBuildName() {
            return buildName;
        }

        public void setBuildName(String buildName) {
            this.buildName = buildName;
        }

        public String getFloorName() {
            return floorName;
        }

        public void setFloorName(String floorName) {
            this.floorName = floorName;
        }

        public String getClassroomName() {
            return classroomName;
        }

        public void setClassroomName(String classroomName) {
            this.classroomName = classroomName;
        }

        public int getClassroomSeatNum() {
            return classroomSeatNum;
        }

        public void setClassroomSeatNum(int classroomSeatNum) {
            this.classroomSeatNum = classroomSeatNum;
        }

        public int getClassroomLength() {
            return classroomLength;
        }

        public void setClassroomLength(int classroomLength) {
            this.classroomLength = classroomLength;
        }

        public int getClassroomWidth() {
            return classroomWidth;
        }

        public void setClassroomWidth(int classroomWidth) {
            this.classroomWidth = classroomWidth;
        }

        public int getClassroomHeight() {
            return classroomHeight;
        }

        public void setClassroomHeight(int classroomHeight) {
            this.classroomHeight = classroomHeight;
        }
    }
}
