package com.sunmnet.mediaroom.tabsp.bean;

import com.sunmnet.mediaroom.common.bean.SocketMessage;
import com.sunmnet.mediaroom.common.tools.GsonUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;

/**
 * Create by WangJincheng on 2021/4/25
 * 用户扫描登录二维码后的操作，平台返回的结果
 */

public class LoginQrCodeOperateResult{

    // socket 消息
    private SocketMessage socketMessage;

    public LoginQrCodeOperateResult(SocketMessage _socketMessage) {
        this.socketMessage = _socketMessage;
    }

    public SocketMessage getSocketMessage() {
        return socketMessage;
    }

    /**
     * 获取APP端扫码后的操作结果
     * @return operateResult
     */
    public OperateResult getOperateResult() {
        OperateResult operateResult = null;
        if (this.socketMessage.getMsg() != null && !this.socketMessage.getMsg().isEmpty()) {
            RunningLog.run("登录结果消息:" + this.socketMessage.getMsg());
            operateResult = GsonUtil.jsonStrToBean(this.socketMessage.getMsg(), OperateResult.class);
        }
        return operateResult;
    }

    public class OperateResult {
        private int node;
        private AccountDtoDTO accountDto;
        private String token;

        public int getNode() {
            return node;
        }

        public void setNode(int node) {
            this.node = node;
        }

        public AccountDtoDTO getAccountDto() {
            return accountDto;
        }

        public void setAccountDto(AccountDtoDTO accountDto) {
            this.accountDto = accountDto;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public class AccountDtoDTO {
            private String id;
            private int age;
            private String birthday;
            private String cellPhone;
            private String nameCn;
            private String nameEn;
            private int sex;
            private int status;
            private String username;
            private String lastLoginTime;
            private int type;
            private int multiLogin;
            private String departmentCode;
            private String iconUrl;
            private String email;
            private String code;
            private String gradeCode;
            private String academyCode;
            private String majorCode;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getAge() {
                return age;
            }

            public void setAge(int age) {
                this.age = age;
            }

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public String getCellPhone() {
                return cellPhone;
            }

            public void setCellPhone(String cellPhone) {
                this.cellPhone = cellPhone;
            }

            public String getNameCn() {
                return nameCn;
            }

            public void setNameCn(String nameCn) {
                this.nameCn = nameCn;
            }

            public String getNameEn() {
                return nameEn;
            }

            public void setNameEn(String nameEn) {
                this.nameEn = nameEn;
            }

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getLastLoginTime() {
                return lastLoginTime;
            }

            public void setLastLoginTime(String lastLoginTime) {
                this.lastLoginTime = lastLoginTime;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getMultiLogin() {
                return multiLogin;
            }

            public void setMultiLogin(int multiLogin) {
                this.multiLogin = multiLogin;
            }

            public String getDepartmentCode() {
                return departmentCode;
            }

            public void setDepartmentCode(String departmentCode) {
                this.departmentCode = departmentCode;
            }

            public String getIconUrl() {
                return iconUrl;
            }

            public void setIconUrl(String iconUrl) {
                this.iconUrl = iconUrl;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getGradeCode() {
                return gradeCode;
            }

            public void setGradeCode(String gradeCode) {
                this.gradeCode = gradeCode;
            }

            public String getAcademyCode() {
                return academyCode;
            }

            public void setAcademyCode(String academyCode) {
                this.academyCode = academyCode;
            }

            public String getMajorCode() {
                return majorCode;
            }

            public void setMajorCode(String majorCode) {
                this.majorCode = majorCode;
            }

            @Override
            public String toString() {
                return "AccountDtoDTO{" +
                        "id='" + id + '\'' +
                        ", age=" + age +
                        ", birthday='" + birthday + '\'' +
                        ", cellPhone='" + cellPhone + '\'' +
                        ", nameCn='" + nameCn + '\'' +
                        ", nameEn='" + nameEn + '\'' +
                        ", sex=" + sex +
                        ", status=" + status +
                        ", username='" + username + '\'' +
                        ", lastLoginTime='" + lastLoginTime + '\'' +
                        ", type=" + type +
                        ", multiLogin=" + multiLogin +
                        ", departmentCode='" + departmentCode + '\'' +
                        ", iconUrl='" + iconUrl + '\'' +
                        ", email='" + email + '\'' +
                        ", code='" + code + '\'' +
                        ", gradeCode='" + gradeCode + '\'' +
                        ", academyCode='" + academyCode + '\'' +
                        ", majorCode='" + majorCode + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "OperateResult{" +
                    "node=" + node +
                    ", accountDto=" + accountDto.toString() +
                    ", token='" + token + '\'' +
                    '}';
        }
    }
}
