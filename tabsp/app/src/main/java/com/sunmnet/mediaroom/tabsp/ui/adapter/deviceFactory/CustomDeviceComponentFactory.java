package com.sunmnet.mediaroom.tabsp.ui.adapter.deviceFactory;

import com.sunmnet.mediaroom.device.bean.CustomDevice;
import com.sunmnet.mediaroom.tabsp.databinding.CustomDeviceItem;
import com.sunmnet.mediaroom.tabsp.ui.adapter.AbstractHolder;
import com.sunmnet.mediaroom.tabsp.ui.adapter.IHolder;

public class CustomDeviceComponentFactory implements IHolder.HolderFactory {

    @Override
    public IHolder newHolder() {
        IHolder holder = new AbstractHolder<CustomDeviceItem, CustomDevice.ComponentItem>() {
            @Override
            public void setProperty(CustomDeviceItem customDeviceItem, CustomDevice.ComponentItem componentItem) {
                super.setProperty(customDeviceItem, componentItem);
                customDeviceItem.setComponent(componentItem);
            }
        };
        return holder;
    }
}
