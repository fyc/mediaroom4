package com.sunmnet.mediaroom.brand.bean.response;

import com.sunmnet.mediaroom.brand.bean.user.Account;
import com.sunmnet.mediaroom.brand.bean.user.RoleBrandRight;
import com.sunmnet.mediaroom.brand.bean.user.AccountRole;
import com.sunmnet.mediaroom.common.bean.Result;

import java.io.Serializable;
import java.util.List;

public class LoginResponse extends Result<LoginResponse.Result> {

    public static class Result implements Serializable {

        /**
         * academyCode : string
         * accountDto : {"academyCode":"string","activeEndTime":"2020-09-11T07:47:46.322Z","activeStartTime":"2020-09-11T07:47:46.322Z","age":0,"birthday":"2020-09-11T07:47:46.322Z","cellPhone":"string","clientId":"string","code":"string","createTime":"2020-09-11T07:47:46.322Z","delStatus":0,"departmentCode":"string","email":"string","faceUrl":"string","gradeCode":"string","groupId":"string","iconUrl":"string","id":"string","imei":"string","imeiTime":"2020-09-11T07:47:46.322Z","lastLoginTime":"2020-09-11T07:47:46.322Z","majorCode":"string","modifyId":"string","modifyTime":"2020-09-11T07:47:46.322Z","multiLogin":0,"nameCn":"string","nameEn":"string","password":"string","passwordLevel":0,"phone":"string","sex":0,"status":0,"type":0,"username":"string"}
         * roleListDto : [{"code":"string","createTime":"2020-09-11T07:47:46.322Z","id":"string","modifyTime":"2020-09-11T07:47:46.322Z","name":"string","remark":"string"}]
         * token : string
         * rightList: [{"rightCodeList": [ "string"], "roleId": "string"}]
         */

        /**
         * 学院编号
         */
        private String academyCode;

        /**
         * 账号
         */
        private Account accountDto;

        /**
         * token
         */
        private String token;

        /**
         * 角色信息列表
         */
        private List<AccountRole> roleListDto;

        private List<RoleBrandRight> rightList;

        public String getAcademyCode() {
            return academyCode;
        }

        public void setAcademyCode(String academyCode) {
            this.academyCode = academyCode;
        }

        public Account getAccountDto() {
            return accountDto;
        }

        public void setAccountDto(Account accountDto) {
            this.accountDto = accountDto;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public List<AccountRole> getRoleListDto() {
            return roleListDto;
        }

        public void setRoleListDto(List<AccountRole> roleListDto) {
            this.roleListDto = roleListDto;
        }

        public List<RoleBrandRight> getRightList() {
            return rightList;
        }

        public void setRightList(List<RoleBrandRight> rightList) {
            this.rightList = rightList;
        }

    }
}
