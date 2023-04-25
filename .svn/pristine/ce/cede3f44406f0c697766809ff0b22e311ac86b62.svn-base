package com.sunmnet.mediaroom.tabsp.ui.adapter.deviceFactory;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.sunmnet.mediaroom.common.tools.CommonUtil;
import com.sunmnet.mediaroom.device.bean.CategoryDevice;
import com.sunmnet.mediaroom.device.listeners.DeviceStateNotifier;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.TabspApplication;
import com.sunmnet.mediaroom.tabsp.databinding.MediaInfoBinding;
import com.sunmnet.mediaroom.tabsp.ui.adapter.AbstractHolder;
import com.sunmnet.mediaroom.tabsp.ui.adapter.IHolder;


public class MediaFactory implements IHolder.HolderFactory {
    public static class MediaHolder extends AbstractHolder<MediaInfoBinding, CategoryDevice> implements DeviceStateNotifier, Runnable {
        @Override
        public String getListenerKey() {
            return property.getDeviceTypeCode();
        }

        @Override
        public void callUpdate() {
        }

        @Override
        public void callUpdate(String code) {
            this.temp.post(this);
        }

        ImageView icon;
        View temp;
        ProgressBar progressBar;

        @Override
        public void bindView(View view) {
            icon = view.findViewById(R.id.device_img);
            temp = view.findViewById(R.id.device_image_holder);
            progressBar = view.findViewById(R.id.controll_processing);
        }

        @Override
        public void setProperty(MediaInfoBinding deviceInfoBinding, CategoryDevice device) {
            super.setProperty(deviceInfoBinding, device);
            device.setNotifier(this);
            run();
        }

        protected void setProgressing(boolean isProgress) {
            if (progressBar != null) {
                progressBar.setVisibility(isProgress ? View.VISIBLE : View.GONE);
            }
        }

        @Override
        public void setSelected(boolean selected) {
            int background = selected ? R.drawable.device_on_corner_background : R.drawable.device_off_corner_background;
            int drawable = this.property.getDrawable();
            CommonUtil.loadResourceIntoImage(TabspApplication.getInstance(), drawable, icon);
            temp.setBackgroundResource(background);
        }

        @Override
        public void run() {
            binding.setDevice(property);
            setSelected(property.isOpened());
            setProgressing(property.isProcessing());
        }
    }

    @Override
    public IHolder newHolder() {
        return new MediaHolder();
    }
}
