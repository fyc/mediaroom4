package com.sunmnet.mediaroom.tabsp.ui.adapter.deviceFactory;

import android.view.View;
import android.widget.ImageView;

import com.sunmnet.mediaroom.common.tools.CommonUtil;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.TabspApplication;
import com.sunmnet.mediaroom.tabsp.bean.SettingMode;
import com.sunmnet.mediaroom.tabsp.databinding.ModeItem;
import com.sunmnet.mediaroom.tabsp.ui.adapter.AbstractHolder;
import com.sunmnet.mediaroom.tabsp.ui.adapter.IHolder;

public class ModeSelectFactory implements IHolder.HolderFactory {
    @Override
    public IHolder newHolder() {
        return new AbstractHolder<ModeItem, SettingMode>() {
            ModeItem binding;
            ImageView icon;
            View holder;

            @Override
            public void bindView(View view) {
                icon = view.findViewById(R.id.device_img);
                holder = view.findViewById(R.id.device_image_holder);
            }

            @Override
            public void setProperty(ModeItem aircondtionerModeItem, SettingMode airconditionerMode) {
                this.property = airconditionerMode;
                this.binding = aircondtionerModeItem;
                binding.setMode(airconditionerMode);
            }

            @Override
            public ModeItem getViewDataBinding() {
                return this.binding;
            }

            @Override
            public SettingMode getProperty() {
                return this.property;
            }

            @Override
            public void setSelected(boolean selected) {
                int background = selected ? R.drawable.device_on_corner_background : R.drawable.item_selected_background;
                int drawable = selected ? this.property.getSelected() : this.property.getUnselected();
                CommonUtil.loadResourceIntoImage(TabspApplication.getInstance(), drawable, icon);
                holder.setBackgroundResource(background);
            }
        };
    }
}
