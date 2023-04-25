package com.sunmnet.mediaroom.tabsp.ui.adapter;

import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunmnet.mediaroom.common.tools.CommonUtil;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.TabspApplication;
import com.sunmnet.mediaroom.tabsp.bean.AbstractItem;

public class SmallItemHolder<T extends ViewDataBinding, E extends AbstractItem> extends AbstractHolder<T, E> {
    protected T binding;
    protected ImageView icon;
    protected View holder;
    protected TextView textView;

    @Override
    public void bindView(View view) {
        icon = view.findViewById(R.id.device_img);
        holder = view.findViewById(R.id.device_image_holder);
        textView = view.findViewById(R.id.device_name);
    }

    @Override
    public T getViewDataBinding() {
        return binding;
    }

    @Override
    public E getProperty() {
        return this.property;
    }

    protected void loadIcon(boolean selected) {
        Object res = null;
        if (selected) res = property.getSelectIcon();
        else res = property.getUnselectIcon();
        if (res instanceof String) {
            CommonUtil.loadResourceIntoImage(TabspApplication.getInstance(), res.toString(), icon);
        } else if (res instanceof Integer) {
            CommonUtil.loadResourceIntoImage(TabspApplication.getInstance(), Integer.parseInt(res.toString()), icon);
        }
    }

    protected void loadBackground(boolean selected) {
        Object res = null;
        if (selected) res = property.getSelectBackgound();
        else res = property.getUnselectBackground();
        if (res instanceof String) {
            CommonUtil.loadBackgroundImage(TabspApplication.getInstance(), res.toString(), holder);
        } else if (res instanceof Integer) {
            holder.setBackgroundResource((int) res);
            //CommonUtil.loadBackgroundImage(TabspApplication.getInstance(), Integer.parseInt(res.toString()), view);
        } else holder.setBackgroundColor(Color.TRANSPARENT);
    }

    protected void loadTextColor(boolean selected) {
        Object res = null;
        if (selected) res = property.getSelectTextColor();
        else res = property.getUnselectTextColor();
        if (res != null)
            this.textView.setTextColor(Integer.parseInt(res.toString()));
        else this.textView.setTextColor(Color.WHITE);
    }

    @Override
    public void setSelected(boolean selected) {
        loadIcon(selected);
        loadBackground(selected);
        loadTextColor(selected);
    }

}
