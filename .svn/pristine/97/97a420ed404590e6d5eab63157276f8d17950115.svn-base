package com.sunmnet.mediaroom.tabsp.ui.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * create by WangJincheng on 2021-11-26
 * 多媒体界面里的设备列表专用
 * 解决首个item状态不会改变的问题，这里沿用之前的解决方法，不是最佳，后续要找到原因并优化
 */

public class MediaBindingAdapter<T extends ViewDataBinding, E> extends BaseAdapter<E> {
    protected IHolder.HolderFactory factory;
    protected int layoutId;
    private Reference<View> firstItemView;

    public MediaBindingAdapter(int layoutId, IHolder.HolderFactory factory) {
        this.factory = factory;
        this.layoutId = layoutId;
    }

    public MediaBindingAdapter() {

    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        T t = null;
        AbstractHolder holder = null;
        if (i == 0 && convertView == null && firstItemView != null && firstItemView.get() != null) {
            //处理多次调用getView(0, null, parent);
            if (firstItemView != null && firstItemView.get() != null) {
                convertView = firstItemView.get();
            }
        }
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
            t = DataBindingUtil.bind(convertView);
            holder = (AbstractHolder) factory.newHolder();
            holder.bindView(convertView);
            convertView.setTag(holder);
            if (i == 0) {
                firstItemView = new WeakReference<>(convertView);
            }
        } else {
            holder = (AbstractHolder) convertView.getTag();
            t = (T) holder.getViewDataBinding();
        }
        holder.setProperty(t, this.data.get(i));
        return convertView;
    }
}