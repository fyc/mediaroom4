package com.sunmnet.mediaroom.matrix.anotherUi.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.sunmnet.mediaroom.matrix.anotherUi.interfaces.IHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dengzl_pc on 2018/3/7.
 */

public class CommonAdapter<T> extends BaseAdapter {
    public CommonAdapter(){}
    public CommonAdapter(Class clazz, int layoutId) {
        this.clazz = clazz;
        this.layoutId = layoutId;
    }

    View.OnClickListener listener;
    public CommonAdapter(Class clazz, int layoutId, View.OnClickListener listener){
        this(clazz,layoutId);
        this.listener=listener;
    }
    protected Class clazz;
    protected int layoutId;
    protected int icon = 0;
    public List<T> getData(){
        return this.datas;
    }
    public void setData(List<T> datas) {
        this.datas = datas;
    }
    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setIcons(Map<String, Integer> icons) {
        this.icons = icons;
    }

    protected Map<String, Integer> icons;
    protected List<T> datas=new ArrayList<>();

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int i) {
        return this.datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        IHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(this.layoutId, viewGroup, false);
            holder = createHolder();
            if (this.icon != 0) holder.setIcon(this.icon);
            if (this.icons != null) holder.setIcon(this.icons);
            if(this.listener!=null);
            holder.setView(view);
            view.setTag(holder);
        } else holder = (IHolder) view.getTag();
        holder.setProperty(this.datas.get(i));
        return view;
    }
    protected IHolder createHolder() {
        IHolder holder = null;
        try {
            holder = (IHolder) this.clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
            holder = new EmptyHolder();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            holder = new EmptyHolder();
        }
        return holder;
    }
}
