package com.sunmnet.mediaroom.brand.bean.response;


import com.sunmnet.mediaroom.common.bean.Result;

import java.io.Serializable;
import java.util.List;

public class DepartmentGradeTreeResponse extends Result<List<DepartmentGradeTreeResponse.Result>> {

    public static class Result implements Serializable {

        /**
         * id : RLZYX
         * parentId : GLXY
         * name : 人力资源系
         * label : 人力资源系
         * key : RLZYX
         * children : [{"id":"RLZY","parentId":"RLZYX","name":"人力资源1班","label":"人力资源1班","key":"RLZY","children":[]},{"id":"RLGL","parentId":"RLZYX","name":"人力管理1班","label":"人力管理1班","key":"RLGL","children":[]}]
         */

        private String id;
        private String parentId;
        private String name;
        private String label;
        private String key;
        private List<Child> children;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public List<Child> getChildren() {
            return children;
        }

        public void setChildren(List<Child> children) {
            this.children = children;
        }

        public static class Child {
            /**
             * id : RLZY
             * parentId : RLZYX
             * name : 人力资源1班
             * label : 人力资源1班
             * key : RLZY
             * children : []
             */

            private String id;
            private String parentId;
            private String name;
            private String label;
            private String key;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getParentId() {
                return parentId;
            }

            public void setParentId(String parentId) {
                this.parentId = parentId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }
        }
    }
}
