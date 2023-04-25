package com.sunmnet.mediaroom.tabsp.ui.adapter.deviceFactory;

import android.view.View;
import android.widget.CompoundButton;

import com.sunmnet.mediaroom.common.tools.CommonUtil;
import com.sunmnet.mediaroom.device.bean.AbstractDevice;
import com.sunmnet.mediaroom.device.bean.Device;
import com.sunmnet.mediaroom.device.bean.DeviceState;
import com.sunmnet.mediaroom.device.controll.Controller;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.TabspApplication;
import com.sunmnet.mediaroom.tabsp.databinding.DoorBinding;
import com.sunmnet.mediaroom.tabsp.ui.adapter.DeviceHolder;
import com.sunmnet.mediaroom.tabsp.ui.adapter.IHolder;

import butterknife.OnCheckedChanged;


public class DoorFactory implements IHolder.HolderFactory {
    public static class DoorHolder extends DeviceHolder<DoorBinding> implements CompoundButton.OnCheckedChangeListener {
        int normal, open;

        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b) {
                Controller.getInstance().open(this.property);
            } else Controller.getInstance().close(this.property);
        }

        @Override
        public void setProperty(DoorBinding doorInfoBinding, AbstractDevice device) {
            register(device);
            this.binding = doorInfoBinding;
            this.property = device;
            binding.setDevice(device);
            CommonUtil.loadResourceIntoImage(TabspApplication.getInstance(), R.drawable.mediaroom4_door_magnetic_close, binding.doorMagImg);
            CommonUtil.loadResourceIntoImage(TabspApplication.getInstance(), R.drawable.door_lock, binding.doorLockImg);
            run();
        }

        @Override
        public void bindView(View view) {
            super.bindView(view);
            normal = CommonUtil.getColorByAttribute(view.getContext(), R.attr.device_black_text_color);
            open = CommonUtil.getColorByAttribute(view.getContext(), R.attr.colorPrimary);
        }

        private void enable(int visibleProcessing, int textColor, boolean check, String text) {
            binding.doorProcessing.setVisibility(visibleProcessing);
            CommonUtil.loadResourceIntoImage(TabspApplication.getInstance(), R.drawable.mediaroom4_door_open, icon);
            CommonUtil.loadResourceIntoImage(TabspApplication.getInstance(), R.drawable.mediaroom4_door_locker_close, binding.doorLockImg);
            CommonUtil.loadResourceIntoImage(TabspApplication.getInstance(), R.drawable.mediaroom4_door_magnetic_close, binding.doorMagImg);
            binding.magneticState.setTextColor(textColor);
            binding.magneticState.setText(text);
            binding.switcherDoorlock.setChecked(check);
        }

        private void disable(int visibleProcessing, int textColor, boolean check, String text) {
            binding.doorProcessing.setVisibility(visibleProcessing);
            CommonUtil.loadResourceIntoImage(TabspApplication.getInstance(), R.drawable.mediaroom4_door_close, icon);
            CommonUtil.loadResourceIntoImage(TabspApplication.getInstance(), R.drawable.mediaroom4_door_locker_open, binding.doorLockImg);
            CommonUtil.loadResourceIntoImage(TabspApplication.getInstance(), R.drawable.mdiaroom_door_magnetic_close, binding.doorMagImg);
            binding.magneticState.setTextColor(textColor);
            binding.magneticState.setText(text);
            binding.switcherDoorlock.setChecked(check);
        }

        private void setDevice(AbstractDevice device) {
            DeviceState state = device.getDeviceState();
            switch (state) {
                case OPENED:
                case OPENNONET:
                    enable(View.GONE, open, true, binding.magneticState.getContext().getString(R.string.text_opened));
                    break;
                case CLOSED:
                case ERROR:
                    disable(View.GONE, normal, false, binding.magneticState.getContext().getString(R.string.text_closed));
                    break;
                case DOORLOCKER_LOCKED:
                    enable(View.GONE, normal, true, binding.magneticState.getContext().getString(R.string.text_closed));
                    break;
                case DOORLOCKER_RELEASE:
                    disable(View.GONE, open, false, binding.magneticState.getContext().getString(R.string.text_opened));
                    break;
                case CLOSING:
                    enable(View.VISIBLE, normal, true, binding.magneticState.getContext().getString(R.string.text_closed));
                    break;
                case OPENNING:
                    disable(View.VISIBLE, normal, false, binding.magneticState.getContext().getString(R.string.text_opened));
                    break;
            }
        }

        @Override
        public void run() {
            binding.switcherDoorlock.setOnCheckedChangeListener(null);
            setDevice(property);
            binding.switcherDoorlock.setOnCheckedChangeListener(this);
        }
    }

    @Override
    public IHolder newHolder() {
        return new DoorHolder();
    }


}