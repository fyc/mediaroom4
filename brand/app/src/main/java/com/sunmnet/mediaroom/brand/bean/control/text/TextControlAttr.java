package com.sunmnet.mediaroom.brand.bean.control.text;

import java.util.List;

public class TextControlAttr {

    /**
     * 文本状态。静态：0；动态：1
     */
    private int textState;
    /**
     * 滚动方向 UTD从上到下 DTU从下到上 LTR从左到右 RTL从右到左
     */
    private String rollDirection;

    /**
     * 滚动速度
     */
    private int rollSpeed;

    /**
     * 内容列表
     */
    private List<Content> contents;

    public int getTextState() {
        return textState;
    }

    public void setTextState(int textState) {
        this.textState = textState;
    }

    public String getRollDirection() {
        return rollDirection;
    }

    public void setRollDirection(String rollDirection) {
        this.rollDirection = rollDirection;
    }

    public int getRollSpeed() {
        return rollSpeed;
    }

    public void setRollSpeed(int rollSpeed) {
        this.rollSpeed = rollSpeed;
    }

    public List<Content> getContents() {
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }

    public static class Content {

        /**
         * 文本
         */
        private String text;
        /**
         * 署名
         */
        private String signature;

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
    }
}
