package com.sunmnet.mediaroom.tabsp.bean;


import com.sunmnet.mediaroom.tabsp.interfaces.DialogListener;

public class DialogInfo {
    public static enum DialogType {
        DEVICE_CLOSING, DEVICE_OPENNING, CONFIRM_CLASSOVER,
    }

    public DialogInfo( DialogType type, DialogListener listener) {
        this.listener = listener;
        this.type = type;
    }
    public DialogInfo( DialogType type, DialogListener listener,int countDownTime) {
        this.listener = listener;
        this.type = type;
        this.countDownTime=countDownTime;
    }
    DialogListener listener;
    DialogType type;

    public String getDialogName() {
        return dialogName;
    }

    public void setDialogName(String dialogName) {
        this.dialogName = dialogName;
    }

    private String dialogName;

    public String getDialogContent() {
        return dialogContent;
    }

    public void setDialogContent(String dialogContent) {
        this.dialogContent = dialogContent;
    }

    private String dialogContent;

    public int getCountDownTime() {
        return countDownTime;
    }

    public void setCountDownTime(int countDownTime) {
        this.countDownTime = countDownTime;
    }

    int countDownTime;
    public DialogListener getListener() {
        return listener;
    }

    public void setListener(DialogListener listener) {
        this.listener = listener;
    }

    public DialogType getType() {
        return type;
    }

    public void setType(DialogType type) {
        this.type = type;
    }
}
