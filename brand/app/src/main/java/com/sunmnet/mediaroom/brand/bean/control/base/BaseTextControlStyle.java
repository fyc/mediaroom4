package com.sunmnet.mediaroom.brand.bean.control.base;

/**
 * 文本类组件属性
 */
public class BaseTextControlStyle extends BaseControlStyle {

    /**
     * 文字的背景颜色，不是组件的背景色
     */
    protected String backgroundColor;
    /**
     * 文字颜色
     */
    protected String color;

    /**
     * 下划线
     */
    protected boolean underline;
    /**
     * 倾斜
     */
    protected boolean italic;
    /**
     * 加粗
     */
    protected boolean bold;

    /**
     * 字体大小
     */
    protected int fontSize;

    /**
     * 对齐方式。左对齐：left； 右对齐：right； 居中对齐：center
     */
    protected String textAlign;

    /**
     * 文字字体。APP将预置一部分字体。预留使用自定义字体功能，从节目资源文件里加载字体文件
     */
    protected String fontFamily;

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
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

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public String getTextAlign() {
        return textAlign;
    }

    public void setTextAlign(String textAlign) {
        this.textAlign = textAlign;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }
}
