package com.sunmnet.mediaroom.brand.bean.play;

import java.io.Serializable;
import java.util.List;

public class NotificationRule extends PublishingRule implements Serializable {


    /**
     * contents : [{"text":"这是测试发送了一个通知","signature":""},{"text":"xxxxxxx","signature":""}]
     * background : #ffffff
     * color : #000000
     * underline : false
     * italic : false
     * bold : false
     * size : 12
     * alignment : 1
     * font : simsun
     * gravity : left
     * rollDirection : UTD
     */

    private String background;
    private String color;
    private boolean underline;
    private boolean italic;
    private boolean bold;
    private int size;
    private int alignment;
    private String font;
    private String gravity;
    private String rollDirection;
    private List<SignatureContent> contents;

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isUnderline() {
        return underline;
    }

    public void setUnderline(boolean underline) {
        this.underline = underline;
    }

    public boolean isItalic() {
        return italic;
    }

    public void setItalic(boolean italic) {
        this.italic = italic;
    }

    public boolean isBold() {
        return bold;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getAlignment() {
        return alignment;
    }

    public void setAlignment(int alignment) {
        this.alignment = alignment;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public String getGravity() {
        return gravity;
    }

    public void setGravity(String gravity) {
        this.gravity = gravity;
    }

    public String getRollDirection() {
        return rollDirection;
    }

    public void setRollDirection(String rollDirection) {
        this.rollDirection = rollDirection;
    }

    public List<SignatureContent> getContents() {
        return contents;
    }

    public void setContents(List<SignatureContent> contents) {
        this.contents = contents;
    }
}
