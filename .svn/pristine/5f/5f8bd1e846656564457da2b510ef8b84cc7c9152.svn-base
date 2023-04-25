package com.sunmnet.mediaroom.tabsp.ui.adapter;

import android.databinding.ViewDataBinding;
import android.support.annotation.CallSuper;
import android.view.View;

public abstract class AbstractHolder<T extends ViewDataBinding, E> implements IHolder<T, E> {
    protected E property;
    protected T binding;

    @Override
    public void bindView(View view) {

    }

    @Override
    public void setProperty(T t, E e) {
        this.property = e;
        this.binding = t;
    }

    public T getViewDataBinding() {
        return this.binding;
    }

    @Override
    public E getProperty() {
        return this.property;
    }

    @Override
    public void setSelected(boolean selected) {

    }
}
