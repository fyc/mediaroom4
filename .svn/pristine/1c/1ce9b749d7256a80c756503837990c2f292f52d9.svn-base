package com.sunmnet.mediaroom.brand.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sunmnet.mediaroom.brand.R;
import com.sunmnet.mediaroom.brand.bean.item.DeviceItem;
import com.sunmnet.mediaroom.brand.common.base.DeviceApp;
import com.sunmnet.mediaroom.device.bean.DeviceType;

import java.util.List;

public class ControlCenterDeviceAdapter extends RecyclerView.Adapter<ControlCenterDeviceAdapter.ViewHolder> {

    private List<DeviceItem> data;
    private int selectedPosition = -1;
    private OnItemSelectedChangeListener listener;

    public void setOnItemSelectedChangeListener(OnItemSelectedChangeListener listener) {
        this.listener = listener;
    }

    public interface OnItemSelectedChangeListener {
        void onItemSelected(DeviceItem item, int position);
    }

    public ControlCenterDeviceAdapter(List<DeviceItem> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_template_control_center_device, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = viewHolder.getAdapterPosition();
                if (adapterPosition >= 0) {
                    if (selectedPosition >= 0) {
                        notifyItemChanged(selectedPosition);
                    }
                    selectedPosition = adapterPosition;
                    notifyItemChanged(selectedPosition);
                    if (listener != null) {
                        listener.onItemSelected(data.get(selectedPosition), selectedPosition);
                    }
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if (selectedPosition == i) {
            viewHolder.itemView.setSelected(true);
        } else {
            viewHolder.itemView.setSelected(false);
        }
        DeviceItem device = data.get(i);
        if (device.getDeviceType() == DeviceType.DOOR) {
            Glide.with(DeviceApp.getApp()).load(R.drawable.mediaroom4_door_locker_open).into(viewHolder.icon);
        } else if (device.getDeviceType() == DeviceType.INTERECEPTACLE) {
            Glide.with(DeviceApp.getApp()).load(R.drawable.icon_device_intereceptacle_open).into(viewHolder.icon);
        } else if (device.getDeviceType() == DeviceType.AIRCONDITIONER) {
            Glide.with(DeviceApp.getApp()).load(R.drawable.mediaroom4_menu_aircondtioner_selected).into(viewHolder.icon);
        } else {
            Glide.with(DeviceApp.getApp()).load(device.getDrawable()).into(viewHolder.icon);
        }
        viewHolder.name.setText(device.getDeviceName());
    }

    public void setSelectedPosition(int position) {
        if (position >= 0 && position < getItemCount()) {
            if (selectedPosition >= 0) {
                notifyItemChanged(selectedPosition);
            }
            this.selectedPosition = position;
            notifyItemChanged(selectedPosition);
            if (listener != null) {
                listener.onItemSelected(data.get(selectedPosition), selectedPosition);
            }
        }
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView icon;
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            name = itemView.findViewById(R.id.name);
        }
    }
}
