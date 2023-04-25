package com.sunmnet.mediaroom.brand.bean.request;

import java.io.Serializable;
import java.util.List;

public class TeachingInspectionAnswerRequest implements Serializable {

    /**
     * templateId : 7ab77e27ccb44b06a77d2151a1354dc6
     * classroomCode : A1-0222
     * answerList : [{"id":"3890d781f9ed4e08917acd90bd9bee8d","type":1,"text":"<p>文本题答答答答<\/p>"},{"id":"5095f9375f894b4783c21f599763260a","type":2,"text":"8"},{"id":"f3bb0bd512644256b0efe158878fc42b","type":3,"text":"8"},{"id":"5a21853464814764b6dd0554b6b7e933","type":4,"groups":[{"id":"fb40de2498354e6b9e289cf1f0d6854b","text":"98"},{"id":"69080247db62468f9169cbfe4d8b7a85","text":"99"},{"id":"c506d85b316848938da7d16e014b79fd","text":"100"}]},{"id":"46db1adef5c045218e8904dd01664c1d","type":5,"groups":[{"id":"01b22e3a5cc34ee198cf7f934d9dc170","text":"5"},{"id":"b96eb2f8416f41fd8a9120d5661f5ea7","text":"4"},{"id":"dbb244222af64e02b51b58ad0cea5ab9","text":"3"}]}]
     */

    private String templateId;
    private String classroomCode;
    private List<AnswerBean> answerList;

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getClassroomCode() {
        return classroomCode;
    }

    public void setClassroomCode(String classroomCode) {
        this.classroomCode = classroomCode;
    }

    public List<AnswerBean> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<AnswerBean> answerList) {
        this.answerList = answerList;
    }

    public static class AnswerBean {
        /**
         * id : 3890d781f9ed4e08917acd90bd9bee8d
         * type : 1
         * text : <p>文本题答答答答</p>
         * groups : [{"id":"fb40de2498354e6b9e289cf1f0d6854b","text":"98"},{"id":"69080247db62468f9169cbfe4d8b7a85","text":"99"},{"id":"c506d85b316848938da7d16e014b79fd","text":"100"}]
         */

        private String id;
        private int type;
        private String text;
        private List<GroupBean> groups;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public List<GroupBean> getGroups() {
            return groups;
        }

        public void setGroups(List<GroupBean> groups) {
            this.groups = groups;
        }

        public static class GroupBean {
            /**
             * id : fb40de2498354e6b9e289cf1f0d6854b
             * text : 98
             */

            private String id;
            private String text;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }
        }
    }
}
