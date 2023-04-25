package com.sunmnet.mediaroom.brand.bean.response;


import com.sunmnet.mediaroom.common.bean.Result;

import java.io.Serializable;
import java.util.List;

public class BuildingResponse extends Result<List<BuildingResponse.Result>> {

    public static class Result implements Serializable {

        /**
         * id : e8f3f070b124490ba427fe994c241d0f
         * buildCode : HE
         * buildName : 琶洲新村2
         * campusCode : H
         * createTime : 2020-09-07 14:57:46
         */

        private String id;
        private String buildCode;
        private String buildName;
        private String campusCode;
        private String createTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBuildCode() {
            return buildCode;
        }

        public void setBuildCode(String buildCode) {
            this.buildCode = buildCode;
        }

        public String getBuildName() {
            return buildName;
        }

        public void setBuildName(String buildName) {
            this.buildName = buildName;
        }

        public String getCampusCode() {
            return campusCode;
        }

        public void setCampusCode(String campusCode) {
            this.campusCode = campusCode;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
