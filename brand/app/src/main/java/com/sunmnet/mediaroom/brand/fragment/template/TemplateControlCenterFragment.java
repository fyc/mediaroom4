package com.sunmnet.mediaroom.brand.fragment.template;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beloo.widget.chipslayoutmanager.SpacingItemDecoration;
import com.sunmnet.mediaroom.brand.R;
import com.sunmnet.mediaroom.brand.bean.item.DeviceItem;
import com.sunmnet.mediaroom.brand.common.base.DeviceApp;
import com.sunmnet.mediaroom.brand.fragment.template.dialog.AbstractDeviceDialog;
import com.sunmnet.mediaroom.brand.ui.adapter.ControlCenterDeviceAdapter;
import com.sunmnet.mediaroom.common.tools.ToastUtil;
import com.sunmnet.mediaroom.device.DeviceBuilder;
import com.sunmnet.mediaroom.device.bean.DeviceState;
import com.sunmnet.mediaroom.device.bean.DeviceType;
import com.sunmnet.mediaroom.device.bean.IDevice;
import com.sunmnet.mediaroom.device.controll.Controller;

import java.util.ArrayList;
import java.util.List;

public class TemplateControlCenterFragment extends Fragment {

    private List<DeviceType> deviceTypes;

    private RecyclerView recyclerView;

    public TemplateControlCenterFragment() {
        deviceTypes = new ArrayList<>();
        //空调
        deviceTypes.add(DeviceType.AIRCONDITIONER);
        //灯光
        deviceTypes.add(DeviceType.LIGHT);
        //投影机
        deviceTypes.add(DeviceType.PROJECTOR);
        //功放
        deviceTypes.add(DeviceType.SOUND);
        //幕布
        deviceTypes.add(DeviceType.SCREEN);
        //交互屏
        deviceTypes.add(DeviceType.INTERACTIVE);
        //窗帘
        deviceTypes.add(DeviceType.CURTAIN);
        //门
        deviceTypes.add(DeviceType.DOOR);
        //新风机
        deviceTypes.add(DeviceType.FRESHAIR);
        //讲台电脑
        deviceTypes.add(DeviceType.PC);
        //学生机
        deviceTypes.add(DeviceType.STUPC);
        //备用
        deviceTypes.add(DeviceType.INTERECEPTACLE);
    }

    public static TemplateControlCenterFragment newInstance() {
        Bundle args = new Bundle();
        TemplateControlCenterFragment fragment = new TemplateControlCenterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_template_control_center, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        int dimensionPixelSize = recyclerView.getContext().getResources().getDimensionPixelSize(R.dimen.px_10);
        SpacingItemDecoration itemDecoration = new SpacingItemDecoration(dimensionPixelSize, dimensionPixelSize);
        recyclerView.addItemDecoration(itemDecoration);
    }

    @Override
    public void onResume() {
        super.onResume();
        initDeviceItem();
    }

    private void initDeviceItem() {
        //根据所需要设备类型生成一个显示用的device list，再分别按设备类型获取设备列表，根据设备状态，来确定显示的device属性
        List<DeviceItem> listItems = new ArrayList<>();
        for (DeviceType type : deviceTypes) {
            List<IDevice> devicesByDeviceType = Controller.getInstance().getDevicesByDeviceType(type);
            if (devicesByDeviceType != null && devicesByDeviceType.size() > 0) {
                DeviceItem item = new DeviceItem();
                DeviceType deviceType = devicesByDeviceType.get(0).getDeviceType();
                item.setDeviceType(deviceType);
                item.setDeviceName(DeviceApp.getApp().getResources().getString(DeviceBuilder.getDeviceTypeNameResource(deviceType.getDeviceType())));
                item.setDeviceState(DeviceState.OPENED);
                listItems.add(item);
            }
        }
        if (listItems.size() == 0) {
            ToastUtil.show(getContext(), "未发现可控制设备");
        } else {
            ControlCenterDeviceAdapter adapter = new ControlCenterDeviceAdapter(listItems);
            adapter.setOnItemSelectedChangeListener(new ControlCenterDeviceAdapter.OnItemSelectedChangeListener() {
                @Override
                public void onItemSelected(DeviceItem item, int position) {
                    AbstractDeviceFragment fragment;
                    if (DeviceType.AIRCONDITIONER.equals(item.getDeviceType())) {
                        fragment = new AirConditionerDeviceFragment();
                    } else {
                        fragment = new NormalDeviceFragment();
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString(AbstractDeviceDialog.DEVICETYPE_KEY, item.getDeviceType().getDeviceType());
                    fragment.setArguments(bundle);
                    getChildFragmentManager().beginTransaction().replace(R.id.contentLayout, fragment).commitAllowingStateLoss();
                }
            });
            recyclerView.setAdapter(adapter);
            adapter.setSelectedPosition(0);
        }
    }

}
