package com.sunmnet.mediaroom.brand.bean.response;


import com.sunmnet.mediaroom.common.bean.Result;

import java.io.Serializable;
import java.util.List;

public class CampusResponse extends Result<List<CampusResponse.Result>> {

    public static class Result implements Serializable {

        private int id;
        private String campusCode;
        private String campusName;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCampusCode() {
            return campusCode;
        }

        public void setCampusCode(String campusCode) {
            this.campusCode = campusCode;
        }

        public String getCampusName() {
            return campusName;
        }

        public void setCampusName(String campusName) {
            this.campusName = campusName;
        }

        @Override
        public String toString() {
            return campusName;
        }
    }
}
