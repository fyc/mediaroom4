package com.sunmnet.mediaroom.tabsp.ui.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class BindingAdapter<T extends ViewDataBinding, E> extends BaseAdapter<E> {
    protected IHolder.HolderFactory factory;
    protected int layoutId;

    public BindingAdapter(int layoutId, IHolder.HolderFactory factory) {
        this.factory = factory;
        this.layoutId = layoutId;
    }

    public BindingAdapter() {
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        T t = null;
        AbstractHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
            t = DataBindingUtil.bind(convertView);
            holder = (AbstractHolder) factory.newHolder();
            holder.bindView(convertView);
            convertView.setTag(holder);
        } else {
            holder = (AbstractHolder) convertView.getTag();
            t = (T) holder.getViewDataBinding();
        }
        holder.setProperty(t, this.data.get(i));
        return convertView;
    }
}