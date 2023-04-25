package com.sunmnet.mediaroom.tabsp.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

public class TabspWeather extends BaseObservable {
    @Bindable
    public String tempPath;
    @Bindable
    public String temp;
    @Bindable
    public String lowTemp;
    @Bindable
    public String highTemp;
    @Bindable
    public String allDayTemp;
}
