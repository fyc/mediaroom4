package com.sunmnet.mediaroom.brand.fragment.template;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.beloo.widget.chipslayoutmanager.SpacingItemDecoration;
import com.sunmnet.mediaroom.brand.R;
import com.sunmnet.mediaroom.brand.bean.item.DeviceItem;
import com.sunmnet.mediaroom.brand.ui.adapter.AirConditionerDeviceItemAdapter;
import com.sunmnet.mediaroom.device.DeviceBuilder;
import com.sunmnet.mediaroom.device.bean.Airconditioner;
import com.sunmnet.mediaroom.device.bean.DeviceState;
import com.sunmnet.mediaroom.device.bean.DeviceType;
import com.sunmnet.mediaroom.device.bean.IDevice;
import com.sunmnet.mediaroom.device.controll.Controller;

import java.util.ArrayList;
import java.util.List;

public class AirConditionerDeviceFragment extends AbstractDeviceFragment implements View.OnClickListener {
    private List<Airconditioner> airConditionerList;

    private AirConditionerDeviceItemAdapter adapter;

    private RecyclerView recyclerView;

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_template_device_content;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TextView textView = view.findViewById(R.id.device_type_name);
        textView.setText(DeviceBuilder.getDeviceTypeNameResource(deviceTypeCode));

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        int dimensionPixelSize = recyclerView.getContext().getResources().getDimensionPixelSize(R.dimen.px_10);
        SpacingItemDecoration itemDecoration = new SpacingItemDecoration(0, dimensionPixelSize);
        recyclerView.addItemDecoration(itemDecoration);

        airConditionerList = new ArrayList<>();
        List<IDevice> devices = Controller.getInstance().getDevicesByDeviceType(DeviceType.AIRCONDITIONER);
        for (IDevice device : devices) {
            if (device instanceof Airconditioner) {
                airConditionerList.add((Airconditioner) device);
            }
        }
        List<DeviceItem<Airconditioner>> deviceItems = new ArrayList<>();
        for (Airconditioner airconditioner : airConditionerList) {
            DeviceItem<Airconditioner> item = new DeviceItem<>();
            item.setDevice(airconditioner);
            item.setDeviceName(airconditioner.getDeviceName());
            item.setDeviceType(airconditioner.getDeviceType());
            item.setDeviceState(airconditioner.getDeviceState());
            deviceItems.add(item);
        }
        adapter = new AirConditionerDeviceItemAdapter(deviceItems);
        adapter.setOnItemSelectedChangeListener(new AirConditionerDeviceItemAdapter.OnAirConditionerStateChangeListener() {
            @Override
            public void onModeChange(DeviceItem<Airconditioner> item, String mode) {
                item.getDevice().getSetting().setMode(mode);
                control(item.getDevice());
            }

            @Override
            public void onTempChange(DeviceItem<Airconditioner> item, String temp) {
                item.getDevice().getSetting().setTempeture(temp);
                control(item.getDevice());
            }

            @Override
            public void onDeviceOpen(DeviceItem<Airconditioner> item) {
                item.getDevice().getSetting().setDeviceState(DeviceState.OPENNING);
                control(item.getDevice());
            }

            @Override
            public void onDeviceClose(DeviceItem<Airconditioner> item) {
                item.getDevice().getSetting().setDeviceState(DeviceState.CLOSING);
                control(item.getDevice());
            }
        });

        recyclerView.setAdapter(adapter);

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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.all_open:
                for (Airconditioner airconditioner : airConditionerList) {
                    airconditioner.getSetting().setDeviceState(DeviceState.OPENED);
                    control(airconditioner);
                }
                break;
            case R.id.all_close:
                for (Airconditioner airconditioner : airConditionerList) {
                    airconditioner.getSetting().setDeviceState(DeviceState.CLOSED);
                    control(airconditioner);
                }
                break;
        }
    }

    protected void control(Airconditioner airconditioner) {
        Controller.getInstance().setState(airconditioner, airconditioner.getControllString());
    }

}
