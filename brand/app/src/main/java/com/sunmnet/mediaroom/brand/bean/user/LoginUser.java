package com.sunmnet.mediaroom.brand.bean.user;

import com.sunmnet.mediaroom.brand.bean.response.LoginResponse;
import com.sunmnet.mediaroom.brand.enums.TemplateRight;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LoginUser implements Serializable {
    private String id;
    private String username;
    private String nameCn;
    private String nameEn;
    private List<UserRole> roleList;
    private String code;
    private String mobilePhoneNum;
    private String token;
    private Set<TemplateRight> rights;

    public LoginUser() {
    }

    public LoginUser(LoginResponse.Result loginResponseResult) {
        id = loginResponseResult.getAccountDto().getId();
        username = loginResponseResult.getAccountDto().getUsername();
        nameCn = loginResponseResult.getAccountDto().getNameCn();
        nameEn = loginResponseResult.getAccountDto().getNameEn();
        code = loginResponseResult.getAccountDto().getCode();
        mobilePhoneNum = loginResponseResult.getAccountDto().getCellPhone();
        token = loginResponseResult.getToken();
        roleList = new ArrayList<>();
        for (AccountRole role : loginResponseResult.getRoleListDto()) {
            UserRole userRole = new UserRole(role);
            roleList.add(userRole);
        }
        rights = new HashSet<>();
        for (RoleBrandRight roleBrandRight : loginResponseResult.getRightList()) {
            for (String code : roleBrandRight.getRightCodeList()) {
                rights.add(TemplateRight.getRightByCode(code));
            }
        }
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public void setRoleList(List<UserRole> roleList) {
        this.roleList = roleList;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getNameCn() {
        return nameCn;
    }

    public String getNameEn() {
        return nameEn;
    }

    public List<UserRole> getRoleList() {
        return roleList;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMobilePhoneNum() {
        return mobilePhoneNum;
    }

    public void setMobilePhoneNum(String mobilePhoneNum) {
        this.mobilePhoneNum = mobilePhoneNum;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Set<TemplateRight> getRights() {
        return rights;
    }

    public void setRights(Set<TemplateRight> rights) {
        this.rights = rights;
    }

    /**
     * 是否老师
     *
     * @return
     */
    public boolean isTeacher() {
        if (roleList != null && roleList.size() > 0) {
            for (UserRole userRole : roleList) {
                if (userRole.getId().equals(UserRole.TeacherRole)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    /**
     * 是否管理员
     *
     * @return
     */
    public boolean isAdmin() {
        if (roleList != null && roleList.size() > 0) {
            for (UserRole userRole : roleList) {
                if (userRole.getId().equals(UserRole.AdminRole)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    /**
     * 是否超级管理员
     *
     * @return
     */
    public boolean isSuperAdmin() {
        if (roleList != null && roleList.size() > 0) {
            for (UserRole userRole : roleList) {
                if (userRole.getId().equals(UserRole.SuperAdminRole)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    /**
     * 是否学生
     *
     * @return
     */
    public boolean isStudent() {
        if (roleList != null && roleList.size() > 0) {
            for (UserRole userRole : roleList) {
                if (userRole.getId().equals(UserRole.StudentRole)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    /**
     * 是否辅导员
     *
     * @return
     */
    public boolean isInstructor() {
        if (roleList != null && roleList.size() > 0) {
            for (UserRole userRole : roleList) {
                if (userRole.getId().equals(UserRole.InstructorRole)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }
}
