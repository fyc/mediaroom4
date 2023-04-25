package com.sunmnet.mediaroom.tabsp.ui.adapter.deviceFactory;

import android.graphics.Color;
import android.view.View;

import com.sunmnet.mediaroom.common.BaseActivity;
import com.sunmnet.mediaroom.common.tools.CommonUtil;
import com.sunmnet.mediaroom.device.bean.AbstractDevice;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.databinding.DeviceItem;
import com.sunmnet.mediaroom.tabsp.ui.adapter.AbstractHolder;
import com.sunmnet.mediaroom.tabsp.ui.adapter.IHolder;

public class ListViewDeviceFactory implements IHolder.HolderFactory {
    public static int SELECT_TEXTCOLOR = -1;
    public static int UNSELECT_TEXTCOLOR = -1;

    public static void init(BaseActivity activity, int selectAttr, int unselectAttr) {
        if (SELECT_TEXTCOLOR == -1 || UNSELECT_TEXTCOLOR == -1) {
            UNSELECT_TEXTCOLOR = CommonUtil.getColorByAttribute(activity, unselectAttr);
            SELECT_TEXTCOLOR = CommonUtil.getColorByAttribute(activity, selectAttr);
        }
    }

    @Override
    public IHolder newHolder() {
        return new AbstractHolder<DeviceItem, AbstractDevice>() {
            DeviceItem item;
            View view;

            @Override
            public void setProperty(DeviceItem airconditionerItem, AbstractDevice airconditioner) {
                this.property = airconditioner;
                this.item = airconditionerItem;
                airconditionerItem.setDevice(airconditioner);
            }

            @Override
            public void bindView(View view) {
                this.view = view;
            }

            @Override
            public DeviceItem getViewDataBinding() {
                return this.item;
            }

            @Override
            public AbstractDevice getProperty() {
                return this.property;
            }

            @Override
            public void setSelected(boolean selected) {
                if (selected) {
                    item.selectTag.setVisibility(View.VISIBLE);
                    item.modeName.setTextColor(SELECT_TEXTCOLOR);
                    item.modeContainer.setBackgroundResource(R.drawable.device_off_background);
                    view.setBackgroundResource(R.drawable.item_selected_background);
                } else {
                    item.selectTag.setVisibility(View.GONE);
                    item.modeName.setTextColor(UNSELECT_TEXTCOLOR);
                    item.modeContainer.setBackgroundColor(Color.TRANSPARENT);
                    view.setBackgroundColor(Color.TRANSPARENT);
                }
            }
        };
    }
}
