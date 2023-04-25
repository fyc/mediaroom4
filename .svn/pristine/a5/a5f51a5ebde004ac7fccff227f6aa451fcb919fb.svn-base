package com.sunmnet.mediaroom.tabsp.interfaces;

import android.databinding.ViewDataBinding;
import android.view.View;

public interface IHolder<T extends ViewDataBinding,E> {
    public interface HolderFactory{
        public IHolder newHolder();
    }
    public void bindView(View view);
    public void setProperty(T t,E e);
    public E getProperty();
    public void setSelected(boolean selected);
    public void update();
    /**
     * 移交处理
     * @return true 移交成功，不再执行其他任务  false 不移交
     * */
    public boolean devolved();
}
