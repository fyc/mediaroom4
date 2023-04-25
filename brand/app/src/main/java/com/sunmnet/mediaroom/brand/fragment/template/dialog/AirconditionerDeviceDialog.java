package com.sunmnet.mediaroom.brand.fragment.template.dialog;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;

import com.sunmnet.mediaroom.brand.R;
import com.sunmnet.mediaroom.brand.bean.item.DeviceItem;
import com.sunmnet.mediaroom.brand.view.ImageTextRadioGroup;
import com.sunmnet.mediaroom.common.tools.TypeUtil;
import com.sunmnet.mediaroom.device.DeviceBuilder;
import com.sunmnet.mediaroom.device.bean.Airconditioner;
import com.sunmnet.mediaroom.device.bean.DeviceState;
import com.sunmnet.mediaroom.device.bean.DeviceType;
import com.sunmnet.mediaroom.device.bean.IDevice;
import com.sunmnet.mediaroom.device.controll.Controller;

import java.util.ArrayList;
import java.util.List;

public class AirconditionerDeviceDialog extends AbstractDeviceDialog implements View.OnClickListener, ImageTextRadioGroup.OnSelectListener {
    ImageTextRadioGroup mode;
    Spinner spinner;
    GridView gridView;
    List<Airconditioner> airconditioners;
    View prevSelectedView;
    AirConditionerAdapter adapter;
    private static final String TAG_POWER = "POWER",
            TAG_MODE = "MODE",
            TAG_TEMPERATURE = "TEMPERATION";

    String[] temppture = new String[]{
            "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32"
    };

    @Override
    protected int getContentLayout() {
        return R.layout.airconditioner_controll_layout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TextView textView = (TextView) view.findViewById(R.id.device_type_name);
        textView.setText(DeviceBuilder.getDeviceTypeNameResource(deviceTypeCode));
        spinner = (Spinner) view.findViewById(R.id.temperatureRange);
        ArrayAdapter<String> tempAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_dialog_item, R.id.text, temppture);
        tempAdapter.setDropDownViewResource(R.layout.spinner_dialog_dropdown_item);
        spinner.setAdapter(tempAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (view != null && view instanceof TextView) {
                    TextView textView = (TextView) view;
                    String temp = textView.getText().toString();
                    if (prevSelectedView != null) {
                        DeviceItem<Airconditioner> deviceInfo = (DeviceItem<Airconditioner>) prevSelectedView.getTag();
                        Airconditioner device = deviceInfo.getDevice();
                        if (device != null) {
                            device.getSetting().setTempeture(temp);
                            control(device);
                        } else {
                            for (Airconditioner airconditioner : airconditioners) {
                                airconditioner.getSetting().setTempeture(temp);
                                control(airconditioner);
                            }
                        }

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        airconditioners = new ArrayList<>();
        List<IDevice> devices = Controller.getInstance().getDevicesByDeviceType(DeviceType.AIRCONDITIONER);
        for (IDevice device : devices) {
            if (device instanceof Airconditioner) {
                airconditioners.add((Airconditioner) device);
            }
        }
        List<DeviceItem<Airconditioner>> deviceItems = new ArrayList<>();
        DeviceItem<Airconditioner> total = new DeviceItem<>();
        total.setDeviceName("统一设置");
        total.setDeviceState(DeviceState.ERROR);
        total.setDeviceType(DeviceType.AIRCONDITIONER);
        for (Airconditioner airconditioner : airconditioners) {
            DeviceItem<Airconditioner> item = new DeviceItem<>();
            item.setDevice(airconditioner);
            item.setDeviceName(airconditioner.getDeviceName());
            item.setDeviceType(airconditioner.getDeviceType());
            item.setDeviceState(airconditioner.getDeviceState());
            deviceItems.add(item);
        }
        adapter = new AirConditionerAdapter(getContext(), deviceItems);

        gridView = (GridView) view.findViewById(R.id.device_gridview);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (prevSelectedView != null)
                    prevSelectedView.setBackgroundColor(Color.TRANSPARENT);
                Airconditioner info = adapter.getDeviceItem(i).getDevice();
                if (info != null) {
                    updateUI(info);
                }
                prevSelectedView = view;
                prevSelectedView.setBackgroundColor(0xFF5C948C);
            }
        });
        gridView.setAdapter(this.adapter);
        gridView.postDelayed(new Runnable() {
            @Override
            public void run() {
                gridView.performItemClick(gridView.getChildAt(0), 0, gridView.getItemIdAtPosition(0));
            }
        }, 50);
        this.mode = (ImageTextRadioGroup) view.findViewById(R.id.mode);
        mode.setOnSelectListener(this);
        bindClick(view.findViewById(R.id.open), this);
        bindClick(view.findViewById(R.id.close), this);
    }

    @Override
    protected void updateData() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
        if (prevSelectedView != null) {
            DeviceItem<Airconditioner> deviceInfo = (DeviceItem<Airconditioner>) prevSelectedView.getTag();
            Airconditioner device = deviceInfo.getDevice();
            if (device != null) {
                updateUI(device);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.open:
                if (prevSelectedView != null) {
                    DeviceItem<Airconditioner> deviceInfo = (DeviceItem<Airconditioner>) prevSelectedView.getTag();
                    Airconditioner device = deviceInfo.getDevice();
                    if (device != null) {
                        device.getSetting().setDeviceState(DeviceState.OPENNING);
                        control(device);
                    } else {
                        for (Airconditioner airconditioner : airconditioners) {
                            airconditioner.getSetting().setDeviceState(DeviceState.OPENNING);
                            control(airconditioner);
                        }
                    }
                }
                break;
            case R.id.close:
                if (prevSelectedView != null) {
                    DeviceItem<Airconditioner> deviceInfo = (DeviceItem<Airconditioner>) prevSelectedView.getTag();
                    Airconditioner device = deviceInfo.getDevice();
                    if (device != null) {
                        device.getSetting().setDeviceState(DeviceState.CLOSING);
                        control(device);
                    } else {
                        for (Airconditioner airconditioner : airconditioners) {
                            airconditioner.getSetting().setDeviceState(DeviceState.CLOSING);
                            control(airconditioner);
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void onSelected(int id, View view) {
        if (prevSelectedView != null) {
            DeviceItem<Airconditioner> deviceInfo = (DeviceItem<Airconditioner>) prevSelectedView.getTag();
            switch (id) {
                case R.id.mode:
                    Airconditioner device = deviceInfo.getDevice();
                    if (device != null) {
                        device.getSetting().setMode(view.getTag().toString());
                        control(device);
                    } else {
                        for (Airconditioner airconditioner : airconditioners) {
                            airconditioner.getSetting().setMode(view.getTag().toString());
                            control(airconditioner);
                        }
                    }
                    break;
            }
        }
    }

    protected void control(Airconditioner airconditioner) {
        airconditioner.getControllString();
        Controller.getInstance().setState(airconditioner, airconditioner.getControllString());
    }

    protected void updateUI(Airconditioner airconditioner) {
        int temp = TypeUtil.objToIntDef(airconditioner.getSetting().getTempeture(), 26);
        int pos = temp - 16;
        pos = Math.max(pos, 0);
        pos = Math.min(0, pos);
        spinner.setSelection(pos);
        mode.setCheckedByTag(airconditioner.getSetting().getMode());
    }

    static class AirConditionerAdapter extends BaseAdapter {

        Context context;
        List<DeviceItem<Airconditioner>> data;

        public AirConditionerAdapter(Context context ,List<DeviceItem<Airconditioner>> data) {
            this.data = data;
            this.context = context;
        }


        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        public DeviceItem<Airconditioner> getDeviceItem(int position) {
            return data == null ? null : data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = null;
            if (convertView == null) {
                textView = new TextView(context);
                textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                textView.setMinWidth(context.getResources().getDimensionPixelSize(R.dimen.dp_40));
                textView.setPadding(10, 10, 10, 10);
                textView.setTextColor(Color.WHITE);
            } else
                textView = (TextView) convertView;
            DeviceItem deviceInfo = data.get(position);
            textView.setText(deviceInfo.getDeviceName());
            textView.setTag(deviceInfo);
            convertView = textView;
            return convertView;
        }
    }
}
