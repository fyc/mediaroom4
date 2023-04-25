package com.sunmnet.mediaroom.brand.bean.user;


import java.io.Serializable;

public class UserRole implements Serializable {
    private String id;
    private String name;

    public final static String SuperAdminRole = "01";
    public final static String AdminRole = "02";
    public final static String TeacherRole = "03";
    public final static String InstructorRole = "04";
    public final static String StudentRole = "05";

    public UserRole(AccountRole role) {
        id = role.getId();
        name = role.getName();
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
