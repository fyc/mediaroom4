package com.sunmnet.mediaroom.brand.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.sunmnet.mediaroom.brand.R;
import com.sunmnet.mediaroom.brand.bean.item.DeviceItem;
import com.sunmnet.mediaroom.brand.view.ImageTextRadioGroup;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.TypeUtil;
import com.sunmnet.mediaroom.device.bean.Airconditioner;

import java.util.List;

public class AirConditionerDeviceItemAdapter extends RecyclerView.Adapter<AirConditionerDeviceItemAdapter.ViewHolder> {

    private List<DeviceItem<Airconditioner>> data;
    private OnAirConditionerStateChangeListener listener;
    private String[] tempArray = new String[]{
            "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32"
    };

    public void setOnItemSelectedChangeListener(OnAirConditionerStateChangeListener listener) {
        this.listener = listener;
    }

    public interface OnAirConditionerStateChangeListener {
        void onModeChange(DeviceItem<Airconditioner> item, String mode);

        void onTempChange(DeviceItem<Airconditioner> item, String temp);

        void onDeviceOpen(DeviceItem<Airconditioner> item);

        void onDeviceClose(DeviceItem<Airconditioner> item);
    }

    public AirConditionerDeviceItemAdapter(List<DeviceItem<Airconditioner>> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_template_device_airconditioner_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.mode.setOnSelectListener(new ImageTextRadioGroup.OnSelectListener() {
            @Override
            public void onSelected(int id, View view) {
                RunningLog.run("空调模式变化");
                int adapterPosition = viewHolder.getAdapterPosition();
                if (adapterPosition >= 0) {
                    DeviceItem<Airconditioner> item = data.get(adapterPosition);
                    if (listener != null) {
                        listener.onModeChange(item, view.getTag().toString());
                    }
                }
            }
        });
        ArrayAdapter<String> tempAdapter = new ArrayAdapter<>(viewGroup.getContext(), R.layout.spinner_dialog_item, R.id.text, tempArray);
        tempAdapter.setDropDownViewResource(R.layout.spinner_dialog_dropdown_item);
        viewHolder.temp.setAdapter(tempAdapter);
        viewHolder.temp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                RunningLog.run("空调温度值变化");
                int adapterPosition = viewHolder.getAdapterPosition();
                if (adapterPosition >= 0) {
                    DeviceItem<Airconditioner> item = data.get(adapterPosition);
                    if (listener != null && i >= 0 && i < tempArray.length) {
                        String temp = tempArray[i];
                        if (listener != null) {
                            listener.onTempChange(item, temp);
                        }
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        viewHolder.open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = viewHolder.getAdapterPosition();
                if (adapterPosition >= 0) {
                    DeviceItem<Airconditioner> item = data.get(adapterPosition);
                    if (listener != null) {
                        listener.onDeviceOpen(item);
                    }
                }
            }
        });

        viewHolder.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = viewHolder.getAdapterPosition();
                if (adapterPosition >= 0) {
                    DeviceItem<Airconditioner> item = data.get(adapterPosition);
                    if (listener != null) {
                        listener.onDeviceClose(item);
                    }
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Airconditioner airconditioner = data.get(i).getDevice();
        int temp = TypeUtil.objToIntDef(airconditioner.getSetting().getTempeture(), 26);
        int pos = temp - 16;
        pos = Math.min(pos, tempArray.length - 1);
        pos = Math.max(pos, 0);
        //取消监听，设置完后，再重新设置监听
        AdapterView.OnItemSelectedListener onItemSelectedListener = viewHolder.temp.getOnItemSelectedListener();
        viewHolder.temp.setOnItemSelectedListener(null);
        viewHolder.temp.setSelection(pos, false);
        viewHolder.temp.setOnItemSelectedListener(onItemSelectedListener);

        viewHolder.mode.setCheckedByTag(airconditioner.getSetting().getMode());
        viewHolder.name.setText(airconditioner.getDeviceName());
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        Button open;
        Button close;
        ImageTextRadioGroup mode;
        Spinner temp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            open = itemView.findViewById(R.id.open);
            close = itemView.findViewById(R.id.close);
            mode = itemView.findViewById(R.id.mode);
            temp = itemView.findViewById(R.id.temperatureRange);
        }
    }
}
