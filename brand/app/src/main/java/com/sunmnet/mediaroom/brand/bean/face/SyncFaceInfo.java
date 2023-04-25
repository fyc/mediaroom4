package com.sunmnet.mediaroom.brand.bean.face;

import java.io.Serializable;
import java.util.List;

public class SyncFaceInfo implements Serializable {

    private Long modifyTime;

    private List<SyncFaceAccount> accountList;

    public Long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Long modifyTime) {
        this.modifyTime = modifyTime;
    }

    public List<SyncFaceAccount> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<SyncFaceAccount> accountList) {
        this.accountList = accountList;
    }
}
