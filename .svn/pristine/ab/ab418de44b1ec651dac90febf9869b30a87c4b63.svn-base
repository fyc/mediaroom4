package com.sunmnet.mediaroom.tabsp.ui.adapter;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sunmnet.mediaroom.common.tools.CommonUtil;
import com.sunmnet.mediaroom.tabsp.TabspApplication;
import com.sunmnet.mediaroom.tabsp.bean.MenuOperator;
import com.sunmnet.mediaroom.tabsp.databinding.MenuBinding;

public class MenuHolderAdapter<E> extends BaseAdapter<E> {
    int layoutId;

    public MenuHolderAdapter(int layoutId) {
        this.layoutId = layoutId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MenuBinding t = null;
        MenuHolder holder = null;
        if (convertView == null) {
            //创建一个databinding
            t = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), layoutId, parent, false);
            //获取convertView
            convertView = t.getRoot();
            holder = new MenuHolder();
            holder.bindView(convertView);
            convertView.setTag(holder);
        } else {
            holder = (MenuHolder) convertView.getTag();
            t = DataBindingUtil.getBinding(convertView);
        }
        holder.setProperty(t, (MenuOperator) this.data.get(position), position);
        return convertView;
    }

    private class MenuHolder extends AbstractHolder<MenuBinding, MenuOperator> {
        View view;
        MenuBinding holder;
        MenuOperator property;
        int position;

        @Override
        public void bindView(View view) {
            this.view = view;
        }

        @Override
        public void setProperty(MenuBinding holder, MenuOperator entity) {
            holder.setMenu(entity);
            this.holder = holder;
            this.property = entity;
            setSelected(false);
        }

        public void setProperty(MenuBinding holder, MenuOperator entity, int posistion) {
            this.position = posistion;
            this.setProperty(holder, entity);
            if (posistion % 2 == 0) {
                holder.rightLine.setVisibility(View.VISIBLE);
            } else holder.rightLine.setVisibility(View.GONE);
        }

        private void loadIcon(boolean selected) {
            Object res = null;
            if (selected) res = property.getSelectIcon();
            else res = property.getUnselectIcon();

            if (res instanceof String) {
                CommonUtil.loadResourceIntoImage(TabspApplication.getInstance(), res.toString(), holder.menuIcon);
            } else if (res instanceof Integer) {
                CommonUtil.loadResourceIntoImage(TabspApplication.getInstance(), Integer.parseInt(res.toString()), holder.menuIcon);
            }
        }

        private void loadBackground(boolean selected) {
            Object res = null;
            if (selected) res = property.getSelectBackgound();
            else res = property.getUnselectBackground();
            if (res instanceof String) {
                CommonUtil.loadBackgroundImage(TabspApplication.getInstance(), res.toString(), holder.menuitem);
            } else if (res instanceof Integer) {
                CommonUtil.loadBackgroundImage(TabspApplication.getInstance(), Integer.parseInt(res.toString()), holder.menuitem);
            } else holder.menuitem.setBackgroundColor(Color.TRANSPARENT);

        }

        private void loadTextColor(boolean selected) {
            Object res = null;
            if (selected) res = property.getSelectTextColor();
            else res = property.getUnselectTextColor();
            if (res != null)
                this.holder.menuView.setTextColor(Integer.parseInt(res.toString()));
            else this.holder.menuView.setTextColor(Color.WHITE);
        }

        @Override
        public void setSelected(boolean selected) {
            loadIcon(selected);
            loadBackground(selected);
            loadTextColor(selected);
            holder.menuView.setEllipsize(selected ? TextUtils.TruncateAt.valueOf("MARQUEE") : TextUtils.TruncateAt.valueOf("END"));

        }

        @Override
        public MenuOperator getProperty() {
            return this.property;
        }
    }
}
