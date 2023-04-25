package com.sunmnet.mediaroom.tabsp.ui.adapter.deviceFactory;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunmnet.mediaroom.common.tools.CommonUtil;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.TabspApplication;
import com.sunmnet.mediaroom.tabsp.bean.AbstractItem;
import com.sunmnet.mediaroom.tabsp.bean.LectureItem;
import com.sunmnet.mediaroom.tabsp.databinding.LectureItemBinding;
import com.sunmnet.mediaroom.tabsp.databinding.ModeItem;
import com.sunmnet.mediaroom.tabsp.ui.adapter.AbstractHolder;
import com.sunmnet.mediaroom.tabsp.ui.adapter.IHolder;

public class LectureFactory implements IHolder.HolderFactory {
    @Override
    public IHolder newHolder() {
        return new AbstractHolder<LectureItemBinding, AbstractItem<?>>() {
            LectureItemBinding binding;
            ImageView icon;
            View view;
            View holder;
            TextView textView;

            @Override
            public void bindView(View view) {
                this.view = view;
                icon = view.findViewById(R.id.device_img);
                holder = view.findViewById(R.id.device_image_holder);
                textView = view.findViewById(R.id.device_name);
            }

            @Override
            public void setProperty(LectureItemBinding aircondtionerModeItem,  AbstractItem<?> airconditionerMode) {
                this.property = airconditionerMode;
                this.binding = aircondtionerModeItem;
                binding.setItem(airconditionerMode);
                setSelected(false);
            }

            @Override
            public LectureItemBinding getViewDataBinding() {
                return this.binding;
            }

            @Override
            public  AbstractItem<?> getProperty() {
                return this.property;
            }

            private void loadIcon(boolean selected) {
                Object res = null;
                if (selected) res = property.getSelectIcon();
                else res = property.getUnselectIcon();

                if (res instanceof String) {
                    CommonUtil.loadResourceIntoImage(TabspApplication.getInstance(), res.toString(), icon);
                } else if (res instanceof Integer) {
                    CommonUtil.loadResourceIntoImage(TabspApplication.getInstance(), Integer.parseInt(res.toString()), icon);
                }
            }

            private void loadBackground(boolean selected) {
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

            private void loadTextColor(boolean selected) {
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

        };
    }
}
