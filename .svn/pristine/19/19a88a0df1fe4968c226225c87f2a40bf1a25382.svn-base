package com.sunmnet.mediaroom.tabsp.ui.adapter;

import android.databinding.ViewDataBinding;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.sunmnet.mediaroom.common.tools.CommonUtil;
import com.sunmnet.mediaroom.device.bean.AbstractDevice;
import com.sunmnet.mediaroom.device.bean.StateEventType;
import com.sunmnet.mediaroom.device.events.DeviceNotifyEvent;
import com.sunmnet.mediaroom.device.listeners.DeviceStateNotifier;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.TabspApplication;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

public abstract class DeviceHolder<T extends ViewDataBinding> extends AbstractHolder<T, AbstractDevice> implements DeviceStateNotifier, Runnable {
    @BindView(R.id.device_image)
    protected ImageView icon;
    @BindView(R.id.device_image_holder)
    protected View holder;
    protected View contentView;
    protected ProgressBar progressBar;
    protected T binding;

    @Override
    public void bindView(View view) {
        this.contentView = view;
        icon = view.findViewById(R.id.device_img);
        holder = view.findViewById(R.id.device_image_holder);
        progressBar = view.findViewById(R.id.controll_processing);
    }

    protected void setProgressing(boolean isProgress) {
        if (progressBar != null) {
            progressBar.setVisibility(isProgress ? View.VISIBLE : View.GONE);
        }
    }

    protected void register(AbstractDevice device) {
        DeviceNotifyEvent event = new DeviceNotifyEvent();
        event.setMessage(this);
        StateEventType eventType = new StateEventType();
        eventType.type = 3;
        eventType.key = device.getDeviceCode();
        event.setEventType(eventType);
        EventBus.getDefault().post(event);
    }

    @Override
    public T getViewDataBinding() {
        return binding;
    }

    @Override
    public AbstractDevice getProperty() {
        return this.property;
    }

    @Override
    public void setSelected(boolean selected) {
        int background = selected ? R.drawable.device_on_corner_background : R.drawable.device_off_corner_background;
        int drawable = this.property.getDrawable();
        CommonUtil.loadResourceIntoImage(TabspApplication.getInstance(), drawable, icon);
        holder.setBackgroundResource(background);
    }

    @Override
    public String getListenerKey() {
        return null;
    }

    @Override
    public void callUpdate() {
        icon.post(this);
    }

    @Override
    public void callUpdate(String code) {
        icon.post(this);
    }

    @Override
    public void run() {
        setProgressing(property.isProcessing());
        setSelected(property.isOpened());
    }
}
