package com.sunmnet.mediaroom.brand.bean.item;

import android.support.annotation.NonNull;

import java.util.List;

public class DepartmentGradeItem {

    String id;
    String parentId;
    String name;
    List<GradeItem> gradeList;

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

    public List<GradeItem> getGradeList() {
        return gradeList;
    }

    public void setGradeList(List<GradeItem> gradeList) {
        this.gradeList = gradeList;
    }

    @NonNull
    @Override
    public String toString() {
        return name == null ? "" : name;
    }
}
