package com.sunmnet.mediaroom.brand.bean.response;

import com.sunmnet.mediaroom.common.bean.Result;

import java.io.Serializable;
import java.util.List;

public class FloorResponse extends Result<List<FloorResponse.Result>> {

    public static class Result implements Serializable {

        /**
         * id : 009efd5b04a2485ba11962c0fcb7ee84
         * campusCode : H
         * buildCode : HF
         * floorCode : HF-01
         * campusName : 海珠校区
         * buildName : F
         * floorName : 第HF-01层
         */

        private String id;
        private String campusCode;
        private String buildCode;
        private String floorCode;
        private String campusName;
        private String buildName;
        private String floorName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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
    }
}
