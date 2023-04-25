package com.sunmnet.mediaroom.brand.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import com.sunmnet.mediaroom.brand.R;
import com.sunmnet.mediaroom.brand.bean.item.DeviceItem;
import com.sunmnet.mediaroom.device.bean.DeviceState;
import com.sunmnet.mediaroom.device.bean.IDevice;

import java.util.List;

public class NormalDeviceItemAdapter extends RecyclerView.Adapter<NormalDeviceItemAdapter.ViewHolder> {

    List<DeviceItem> data;
    private OnItemCheckedChangeListener listener;


    public void setOnItemSelectedChangeListener(OnItemCheckedChangeListener listener) {
        this.listener = listener;
    }

    public interface OnItemCheckedChangeListener {
        void onItemCheckedChanged(DeviceItem item, boolean isChecked);
    }

    public NormalDeviceItemAdapter(List<DeviceItem> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_template_device_normal_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.btnSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!buttonView.isPressed())
                    return;
                int adapterPosition = viewHolder.getAdapterPosition();
                if (adapterPosition >= 0) {
                    if (listener != null) {
                        listener.onItemCheckedChanged(data.get(adapterPosition), isChecked);
                    }
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        IDevice device = data.get(i).getDevice();
        DeviceState state = device.getDeviceState();
        viewHolder.name.setText(device.getDeviceName());
        if (DeviceState.CLOSING == state || DeviceState.OPENNING == state) {
            viewHolder.progressBar.setVisibility(View.VISIBLE);
            viewHolder.btnSwitch.setEnabled(false);
        } else {
            viewHolder.progressBar.setVisibility(View.GONE);
            viewHolder.btnSwitch.setEnabled(true);
        }
        if (DeviceState.OPENNING == state || DeviceState.OPENED == state || DeviceState.OPENNONET == state) {
            viewHolder.btnSwitch.setChecked(true);
        } else {
            viewHolder.btnSwitch.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;
        TextView name;
        Switch btnSwitch;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progress);
            name = itemView.findViewById(R.id.name);
            btnSwitch = itemView.findViewById(R.id.btn_switch);
        }
    }
}
