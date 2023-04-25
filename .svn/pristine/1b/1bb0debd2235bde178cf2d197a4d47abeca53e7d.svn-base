package com.sunmnet.mediaroom.brand.interfaces.control;

/**
 * 文本风格
 */

public interface ITextStyle {

    String ALIGN_LEFT = "left";
    String ALIGN_RIGHT = "right";
    String ALIGN_CENTER = "center";

    String ALIGN_TOP = "top";
    String ALIGN_BOTTOM = "bottom";

    /**
     * 设置字体
     *
     * @param font 字体文件名，如：simsun.ttf
     */
    void setFont(String font);

    /**
     * 设置文字大小，使用时需转化为Android的像素单位
     *
     * @param textSize json里的数值
     */
    void setControlTextSize(int textSize);

    /**
     * 设置文字颜色
     *
     * @param textColor RGB颜色值，如#203012
     */
    void setControlTextColor(String textColor);

    /**
     * 设置文本组件背景颜色
     *
     * @param textBackgroundColor RGB颜色值，如#203012
     */
    void setTextBackgroundColor(String textBackgroundColor);

    /**
     * 下划线
     */
    void setUnderline(boolean underline);

    /**
     * 倾斜
     */
    void setItalic(boolean italic);

    /**
     * 加粗
     */
    void setBold(boolean bold);

    /**
     * 对齐方式
     */
    void setAlignment(String alignment);

    /**
     * @return 字体文件名，如：simsun.ttf
     */
    String getFont();

    /**
     * 使用时需转换为Android的像素单位
     *
     * @return 文字大小
     */
    int getControlTextSize();

    /**
     * 返回文字RGB颜色值，如#120302
     *
     * @return RGB颜色值
     */
    String getControlTextColor();

    /**
     * 返回文本组件背景RGB颜色值，如#120302
     *
     * @return RGB颜色值
     */
    String getTextBackgroundColor();

    /**
     * 下划线
     */
    boolean isUnderline();

    /**
     * 倾斜
     */
    boolean isItalic();

    /**
     * 加粗
     */
    boolean isBold();

    /**
     * 对齐方式。左对齐：ALIGN_LEFT； 右对齐：ALIGN_RIGHT； 居中对齐：ALIGN_CENTER
     */
    String getAlignment();


}
