package com.sunmnet.mediaroom.tabsp.ui.adapter.deviceFactory;

import android.view.View;

import com.sunmnet.mediaroom.common.tools.CommonUtil;
import com.sunmnet.mediaroom.device.bean.AbstractDevice;
import com.sunmnet.mediaroom.device.bean.Device;
import com.sunmnet.mediaroom.device.bean.DeviceState;
import com.sunmnet.mediaroom.device.bean.DeviceType;
import com.sunmnet.mediaroom.device.bean.Dimmer;
import com.sunmnet.mediaroom.device.controll.Controller;
import com.sunmnet.mediaroom.device.listeners.DeviceStateNotifier;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.TabspApplication;
import com.sunmnet.mediaroom.tabsp.databinding.DimmerBinding;
import com.sunmnet.mediaroom.tabsp.ui.adapter.DeviceHolder;
import com.sunmnet.mediaroom.tabsp.ui.adapter.IHolder;
import com.sunmnet.mediaroom.tabsp.ui.widget.DimmerPowerView;


public class DimmerFactory implements IHolder.HolderFactory {
    public static class DimmerHolder extends DeviceHolder<DimmerBinding> implements DeviceStateNotifier, View.OnClickListener {
        public void onClick(View view) {
            int power = -1;
            switch (view.getId()) {
                case R.id.dimmer_minus_frame:
                    binding.dimmerPower.plus(-1);
                    power = binding.dimmerPower.getPower();
                    break;
                case R.id.dimmer_plus_frame:
                    binding.dimmerPower.plus(1);
                    power = binding.dimmerPower.getPower();
                    break;
            }
            if (power > -1) {
                Controller.getInstance().setState(property, power + "");
            }
        }

        @Override
        public void bindView(View view) {
            super.bindView(view);
        }

        @Override
        public void setProperty(DimmerBinding deviceInfoBinding, AbstractDevice device) {
            register(device);
            this.binding = deviceInfoBinding;
            this.property = device;
            binding.setDevice(device);
            setProgressing(device.isProcessing());
            setProperty((Device) device);
        }

        private void setProperty(Device device) {
            if (device.getDeviceType() == DeviceType.DIMMER) {
                binding.dimmer.setVisibility(View.VISIBLE);
                binding.dimmerPlusFrame.setOnClickListener(this);
                binding.dimmerMinusFrame.setOnClickListener(this);
                CommonUtil.loadResourceIntoImage(TabspApplication.getInstance(), R.drawable.mediaroom4_tempeture_plus, binding.dimmerPlus);
                CommonUtil.loadResourceIntoImage(TabspApplication.getInstance(), R.drawable.mediaroom4_tempeture_minus, binding.dimmerMinus);
                DimmerPowerView dimmerPowerView = this.contentView.findViewById(R.id.dimmer_power);
                if (dimmerPowerView != null) {
                    setSelected(device.isOpened());
                    dimmerPowerView.setPower(((Dimmer) property).getStateValue());
                }
            } else setSelected(device.isOpened());
        }

        @Override
        public void setSelected(boolean selected) {
            int background = selected ? R.drawable.device_on_corner_background : R.drawable.device_off_corner_background;
            int drawable = 0;
            if (selected) {
                drawable = this.property.getDeviceType().getDeviceDrawableByState(DeviceState.OPENED);
            } else
                drawable = this.property.getDeviceType().getDeviceDrawableByState(DeviceState.CLOSED);
            CommonUtil.loadResourceIntoImage(TabspApplication.getInstance(), drawable, icon);
            holder.setBackgroundResource(background);
        }

        @Override
        public void run() {
            setProgressing(this.property.isProcessing());
            setProperty((Device) this.property);
        }
    }

    @Override
    public IHolder newHolder() {
        return new DimmerHolder();
    }

}