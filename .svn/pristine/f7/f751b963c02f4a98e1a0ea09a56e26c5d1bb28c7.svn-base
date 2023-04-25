package com.sunmnet.mediaroom.brand.fragment.template.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sunmnet.mediaroom.brand.MainApplication;
import com.sunmnet.mediaroom.brand.R;
import com.sunmnet.mediaroom.brand.bean.item.DeviceItem;
import com.sunmnet.mediaroom.brand.common.base.DeviceApp;
import com.sunmnet.mediaroom.device.DeviceBuilder;
import com.sunmnet.mediaroom.device.bean.DeviceState;
import com.sunmnet.mediaroom.device.bean.DeviceType;
import com.sunmnet.mediaroom.device.bean.IDevice;
import com.sunmnet.mediaroom.device.controll.Controller;

import java.util.ArrayList;
import java.util.List;

public class DeviceFragmentDialog extends AbstractDeviceDialog implements AdapterView.OnItemClickListener, View.OnClickListener {
    GridView gridView;
    DeviceAdapter adapter;

    @Override
    protected int getContentLayout() {
        return R.layout.dialog_template_normal_device;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView = (TextView) view.findViewById(R.id.device_type_name);
        textView.setText(DeviceBuilder.getDeviceTypeNameResource(deviceTypeCode));

        List<IDevice> devicesByDeviceType = Controller.getInstance().getDevicesByDeviceType(DeviceBuilder.getDeviceType(deviceTypeCode));
        List<DeviceItem> items = new ArrayList<>();
        for (IDevice d : devicesByDeviceType) {
            DeviceItem<IDevice> item = new DeviceItem<>();
            item.setDevice(d);
            items.add(item);
        }
        adapter = new DeviceAdapter(items);
        this.gridView = (GridView) view.findViewById(R.id.device_gridview);
        if (this.gridView != null) {
            this.gridView.setAdapter(adapter);
            this.gridView.setOnItemClickListener(this);
            adapter.notifyDataSetChanged();
        }
        bindClick(view.findViewById(R.id.all_open), this);
        bindClick(view.findViewById(R.id.all_close), this);
    }

    @Override
    protected void updateData() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        DeviceViewHolder deviceInfoIHolder = (DeviceViewHolder) view.getTag();
        if (deviceInfoIHolder != null) {
            IDevice itemData = deviceInfoIHolder.getDevice();
            if (itemData != null) {
                if (itemData.isOpened()) {
                    Controller.getInstance().close(itemData);
                } else {
                    Controller.getInstance().open(itemData);
                }
            }
            if (this.adapter != null)
                this.adapter.notifyDataSetChanged();
        }
    }

    private void changeState(DeviceState state) {
        switch (state) {
            case OPENNING:
                Controller.getInstance().open(DeviceBuilder.getDeviceType(deviceTypeCode));
                break;
            case CLOSING:
                Controller.getInstance().close(DeviceBuilder.getDeviceType(deviceTypeCode));
                break;
        }
        if (this.adapter != null)
            this.adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.all_close:
                this.changeState(DeviceState.CLOSING);
                break;
            case R.id.all_open:
                this.changeState(DeviceState.OPENNING);
                break;
        }
    }


    static class DeviceAdapter extends BaseAdapter {
        public DeviceAdapter() {
        }

        public DeviceAdapter(List<DeviceItem> data) {
            setData(data);
        }

        public void setData(List<DeviceItem> data) {
            this.data = data;
        }

        List<DeviceItem> data = new ArrayList<>();

        @Override
        public int getCount() {
            return this.data.size();
        }

        @Override
        public Object getItem(int position) {
            return this.data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            DeviceViewHolder holder = null;
            if (convertView == null) {
                holder = new DeviceViewHolder();
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_operating_gridview_item, null, false);
                convertView.setTag(holder);
                holder.setView(convertView);
            } else
                holder = (DeviceViewHolder) convertView.getTag();
            holder.setItemData(this.data.get(position).getDevice());
            return convertView;
        }
    }

    static class DeviceViewHolder {
        ImageView image;
        TextView dName;
        View view;
        ProgressBar progressBar;
        IDevice info;
        final int openColor = DeviceApp.getApp().getResources().getColor(R.color.control_device_open);
        final int closeColor = DeviceApp.getApp().getResources().getColor(R.color.control_device_close);

        public void setView(View view) {
            image = (ImageView) view.findViewById(R.id.device_image);
            dName = (TextView) view.findViewById(R.id.device_name);
            progressBar = (ProgressBar) view.findViewById(R.id.progressing);
            this.view = view;
        }

        public void setItemData(IDevice device) {
            this.info = device;
            dName.setText(device.getDeviceName());
            if (device.getDeviceType() == DeviceType.DOOR) {
                if (device.isOpened()) {
                    Glide.with(MainApplication.getApp()).load(R.drawable.mediaroom4_door_locker_open).into(this.image);
                } else {
                    Glide.with(MainApplication.getApp()).load(R.drawable.mediaroom4_door_locker_close).into(this.image);
                }
            } else if (device.getDeviceType() == DeviceType.INTERECEPTACLE) {
                if (device.isOpened()) {
                    Glide.with(MainApplication.getApp()).load(R.drawable.icon_device_intereceptacle_open).into(this.image);
                } else {
                    Glide.with(MainApplication.getApp()).load(R.drawable.icon_device_intereceptacle_close).into(this.image);
                }
            } else {
                Glide.with(MainApplication.getApp()).load(device.getDrawable()).into(this.image);
            }
            DeviceState state = device.getDeviceState();
            if (DeviceState.CLOSING == state ||
                    DeviceState.OPENNING == state) {
                this.progressBar.setVisibility(View.VISIBLE);
            } else this.progressBar.setVisibility(View.GONE);
            setTextColor(state);
        }

        private void setTextColor(DeviceState state) {
            if (DeviceState.OPENED == state || DeviceState.OPENNONET == state || DeviceState.CLOSING == state) {
                dName.setTextColor(openColor);
                this.view.setBackgroundResource(R.drawable.device_controll_open_bg);
            } else if (DeviceState.CLOSED == state || DeviceState.ERROR == state || DeviceState.OPENNING == state) {
                dName.setTextColor(closeColor);
                this.view.setBackgroundResource(R.drawable.device_controll_close_bg);
            }
        }

        public IDevice getDevice() {
            return this.info;
        }
    }
}
