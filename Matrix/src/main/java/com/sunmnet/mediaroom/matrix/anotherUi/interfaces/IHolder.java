package com.sunmnet.mediaroom.matrix.anotherUi.interfaces;

import android.view.View;

import java.util.Map;

/**
 * Created by dengzl_pc on 2018/3/5.
 */

public interface IHolder<T> {
    public void setView(View view);
    public void setProperty(T property);
    public T getProperty();
    public void setIcon(int icon);
    public void setIcon(Map<String, Integer> icons);
}
