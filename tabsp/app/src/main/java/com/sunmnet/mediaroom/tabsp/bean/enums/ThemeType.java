package com.sunmnet.mediaroom.tabsp.bean.enums;

import com.sunmnet.mediaroom.tabsp.R;

public enum ThemeType {
    NORMAL(R.style.BaseAppTheme),
    LARGE(R.style.large_style),
    XLARGE(R.style.xlarge_style),
    XXLARGE(R.style.xxlarge_style),
    LARGE_MENU(R.style.large_menu_style),
    XLARGE_MENU(R.style.xlarge_menu_style),
    XXLARGE_MENU(R.style.xxlarge_menu_style);
    ThemeType(int styleValue){
        this.styleValue=styleValue;
    }
    int styleValue;
    public int getStyleValue(){
        return styleValue;
    }
}
