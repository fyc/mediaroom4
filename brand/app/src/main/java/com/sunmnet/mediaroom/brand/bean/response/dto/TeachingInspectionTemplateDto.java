package com.sunmnet.mediaroom.brand.bean.response.dto;

import java.io.Serializable;
import java.util.List;

public class TeachingInspectionTemplateDto implements Serializable {
    /**
     * id : 7ab77e27ccb44b06a77d2151a1354dc6
     * templateName : 巡查模板1
     * prefix : 为了更好地提高教学质量，希望你抽出几分钟，填写您对此节课程的感受与建议！
     * questions : [{"id":"3890d781f9ed4e08917acd90bd9bee8d","type":1,"title":"文本题","description":"Balala","required":true},{"id":"5095f9375f894b4783c21f599763260a","type":2,"title":"评分题","description":"评分评分呀","required":true,"score":10},{"id":"f3bb0bd512644256b0efe158878fc42b","type":3,"title":"量表题","description":"满意度","required":true,"starBeginNum":5,"startNum":1},{"id":"5a21853464814764b6dd0554b6b7e933","type":4,"title":"矩阵评分题","description":"评分评分评评评","required":true,"subQuestions":[{"id":"fb40de2498354e6b9e289cf1f0d6854b","title":"问题1"},{"id":"69080247db62468f9169cbfe4d8b7a85","title":"问题2"},{"id":"c506d85b316848938da7d16e014b79fd","title":"问题3"}],"score":100},{"id":"46db1adef5c045218e8904dd01664c1d","type":5,"title":"矩阵量表题","description":"快来回答","required":true,"subQuestions":[{"id":"01b22e3a5cc34ee198cf7f934d9dc170","title":"问题1"},{"id":"b96eb2f8416f41fd8a9120d5661f5ea7","title":"问题2"},{"id":"dbb244222af64e02b51b58ad0cea5ab9","title":"问题3"}],"starBeginNum":5,"startNum":1}]
     * roleIdList : ["00"]
     */

    private String id;
    private String templateName;
    private String prefix;
    private List<TeachingInspectionQuestionDto> questions;
    private List<String> roleIdList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public List<TeachingInspectionQuestionDto> getQuestions() {
        return questions;
    }

    public void setQuestions(List<TeachingInspectionQuestionDto> questions) {
        this.questions = questions;
    }

    public List<String> getRoleIdList() {
        return roleIdList;
    }

    public void setRoleIdList(List<String> roleIdList) {
        this.roleIdList = roleIdList;
    }
}
