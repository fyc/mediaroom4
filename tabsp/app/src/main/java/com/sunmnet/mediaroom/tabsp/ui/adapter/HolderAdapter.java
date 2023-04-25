package com.sunmnet.mediaroom.tabsp.ui.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 适配器缓存对象
 * */
public class HolderAdapter<T extends ViewDataBinding, E> extends BaseAdapter<E> {
    IHolder.HolderFactory factory;
    int layoutId;

    public HolderAdapter(int layoutId, IHolder.HolderFactory factory) {
        this.factory = factory;
        this.layoutId = layoutId;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        T t = null;
        IHolder holder = null;
        if (convertView == null) {
            //创建一个databinding
            t = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), layoutId, parent, false);
            //获取convertView
            convertView = t.getRoot();
            holder = factory.newHolder();
            holder.bindView(convertView);
            convertView.setTag(holder);
        } else {
            holder = (IHolder) convertView.getTag();
            t=DataBindingUtil.getBinding(convertView);
        }
        holder.setProperty(t, this.data.get(i));
        return convertView;
    }
}
