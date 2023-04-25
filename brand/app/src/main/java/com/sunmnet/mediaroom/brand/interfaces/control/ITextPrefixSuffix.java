package com.sunmnet.mediaroom.brand.interfaces.control;

/**
 * 前后缀文本
 */

public interface ITextPrefixSuffix {

    /**
     * 设置前缀文本
     *
     * @param prefixText 前缀文本
     */
    void setPrefixText(String prefixText);

    /**
     * 设置后缀文本
     *
     * @param suffixText 后缀文本
     */
    void setSuffixText(String suffixText);

    /**
     * 返回前缀文本
     *
     * @return 前缀文本
     */
    String getPrefixText();

    /**
     * 返回后缀文本
     *
     * @return 后缀文本
     */
    String getSuffixText();
}
