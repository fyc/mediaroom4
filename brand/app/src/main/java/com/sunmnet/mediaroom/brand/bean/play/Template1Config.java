package com.sunmnet.mediaroom.brand.bean.play;

import java.util.List;

public class Template1Config {

    /**
     * logo : /picture/rBAYO19QmdyAKd_pAAAemjaMMl4027.png
     * text : 通知通知通知通知通知通知通知通知通知通知通知通知通知通知通知通知通知通知通知通知通知通知通知通知通知
     * signature : 教务处
     * picture : ["/picture/rBAYO19QmdyAJ2B2AAB20K1T1F8523.jpg","/picture/rBAYO19QmdyAaWVGAAhHWbkLLcQ467.png","/picture/rBAYO19QmdyAIxDhAAfay8GbalQ469.png"]
     */

    private String logo;
    private String text;
    private String signature;
    private List<String> picture;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public List<String> getPicture() {
        return picture;
    }

    public void setPicture(List<String> picture) {
        this.picture = picture;
    }
}
