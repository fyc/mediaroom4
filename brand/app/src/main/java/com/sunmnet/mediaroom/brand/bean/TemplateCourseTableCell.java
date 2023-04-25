package com.sunmnet.mediaroom.brand.bean;


public class TemplateCourseTableCell {

    private String text;

    private int height;
    private int width;

    private String textColor;
    private boolean underline;
    private boolean italic;
    private boolean bold;
    private int textSize;

    public TemplateCourseTableCell() {
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public void setUnderline(boolean underline) {
        this.underline = underline;
    }

    public void setItalic(boolean italic) {
        this.italic = italic;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public String getTextColor() {
        return textColor;
    }

    public boolean isUnderline() {
        return underline;
    }

    public boolean isItalic() {
        return italic;
    }

    public boolean isBold() {
        return bold;
    }

    public int getTextSize() {
        return textSize;
    }
}
