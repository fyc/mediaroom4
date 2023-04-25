package com.sunmnet.mediaroom.tabsp.ui.adapter;

import android.databinding.ViewDataBinding;
import android.view.View;

public interface IHolder<T extends ViewDataBinding,E> {
    public interface HolderFactory{
        public IHolder newHolder();
    }
    public void bindView(View view);
    public void setProperty(T t, E e);
    public E getProperty();
    public void setSelected(boolean selected);
}
