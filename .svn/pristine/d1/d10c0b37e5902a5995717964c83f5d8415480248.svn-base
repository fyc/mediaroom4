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
import com.sunmnet.mediaroom.brand.ui.adapter.NormalDeviceItemAdapter;
import com.sunmnet.mediaroom.device.DeviceBuilder;
import com.sunmnet.mediaroom.device.bean.DeviceState;
import com.sunmnet.mediaroom.device.bean.IDevice;
import com.sunmnet.mediaroom.device.controll.Controller;

import java.util.ArrayList;
import java.util.List;

public class NormalDeviceFragment extends AbstractDeviceFragment implements View.OnClickListener {
    private RecyclerView recyclerView;
    private NormalDeviceItemAdapter deviceAdapter;

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_template_device_content;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView = view.findViewById(R.id.device_type_name);
        textView.setText(DeviceBuilder.getDeviceTypeNameResource(deviceTypeCode));
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        int dimensionPixelSize = recyclerView.getContext().getResources().getDimensionPixelSize(R.dimen.px_10);
        SpacingItemDecoration itemDecoration = new SpacingItemDecoration(0, dimensionPixelSize);
        recyclerView.addItemDecoration(itemDecoration);

        List<IDevice> devicesByDeviceType = Controller.getInstance().getDevicesByDeviceType(DeviceBuilder.getDeviceType(deviceTypeCode));
        List<DeviceItem> items = new ArrayList<>();
        for (IDevice d : devicesByDeviceType) {
            DeviceItem<IDevice> item = new DeviceItem<>();
            item.setDevice(d);
            items.add(item);
        }
        deviceAdapter = new NormalDeviceItemAdapter(items);
        deviceAdapter.setOnItemSelectedChangeListener(new NormalDeviceItemAdapter.OnItemCheckedChangeListener() {
            @Override
            public void onItemCheckedChanged(DeviceItem item, boolean isChecked) {
                IDevice itemData = item.getDevice();
                if (isChecked) {
                    Controller.getInstance().open(itemData);
                } else {
                    Controller.getInstance().close(itemData);
                }
            }
        });
        recyclerView.setAdapter(deviceAdapter);
        bindClick(view.findViewById(R.id.all_open), this);
        bindClick(view.findViewById(R.id.all_close), this);
    }

    @Override
    protected void updateData() {
        if (deviceAdapter != null) {
            deviceAdapter.notifyDataSetChanged();
        }
    }


    private void changeState(DeviceState state) {
        switch (state) {
            case OPENED:
                Controller.getInstance().open(DeviceBuilder.getDeviceType(deviceTypeCode));
                break;
            case CLOSED:
                Controller.getInstance().close(DeviceBuilder.getDeviceType(deviceTypeCode));
                break;
        }
        if (deviceAdapter != null) {
            deviceAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.all_close:
                this.changeState(DeviceState.CLOSED);
                break;
            case R.id.all_open:
                this.changeState(DeviceState.OPENED);
                break;
        }
    }

}
