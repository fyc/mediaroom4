package com.sunmnet.mediaroom.tabsp.ui.adapter.deviceFactory;

import android.view.View;

import com.sunmnet.mediaroom.device.bean.AbstractDevice;
import com.sunmnet.mediaroom.device.bean.Device;
import com.sunmnet.mediaroom.tabsp.databinding.DeviceInfoBinding;
import com.sunmnet.mediaroom.tabsp.ui.adapter.DeviceHolder;
import com.sunmnet.mediaroom.tabsp.ui.adapter.IHolder;

public class DeviceFactory implements IHolder.HolderFactory {
    public DeviceFactory() {

    }

    @Override
    public IHolder newHolder() {
        IHolder holder = new DeviceHolder<DeviceInfoBinding>() {
            @Override
            public void setProperty(DeviceInfoBinding deviceInfoBinding, AbstractDevice device) {
                register(device);
                this.binding = deviceInfoBinding;
                deviceInfoBinding.setDevice(device);
                this.property = device;
                setProgressing(property.isProcessing());
                setSelected(property.isOpened());
            }

        };
        return holder;
    }
}
