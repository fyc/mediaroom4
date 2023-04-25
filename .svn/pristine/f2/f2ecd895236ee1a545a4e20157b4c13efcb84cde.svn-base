package com.sunmnet.mediaroom.tabsp.ui.dispatch.fragmentDispatcher;

import android.databinding.DataBindingUtil;
import android.view.View;
import android.widget.AdapterView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sunmnet.mediaroom.common.BaseActivity;
import com.sunmnet.mediaroom.device.bean.DeviceType;
import com.sunmnet.mediaroom.device.bean.FreshAir;
import com.sunmnet.mediaroom.device.bean.IDevice;
import com.sunmnet.mediaroom.device.controll.Controller;
import com.sunmnet.mediaroom.device.events.DeviceLoadedEvent;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.TabspApplication;
import com.sunmnet.mediaroom.tabsp.bean.SettingMode;
import com.sunmnet.mediaroom.tabsp.common.Constants;
import com.sunmnet.mediaroom.tabsp.databinding.DeviceItem;
import com.sunmnet.mediaroom.tabsp.databinding.FreshAirBinding;
import com.sunmnet.mediaroom.tabsp.databinding.ModeItem;
import com.sunmnet.mediaroom.tabsp.ui.adapter.AbstractHolder;
import com.sunmnet.mediaroom.tabsp.ui.adapter.HolderAdapter;
import com.sunmnet.mediaroom.tabsp.ui.adapter.deviceFactory.ListViewDeviceFactory;
import com.sunmnet.mediaroom.tabsp.ui.adapter.deviceFactory.ModeSelectFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
/**
 * 风机控制
 * */
@Route(path = Constants.ROUTERPATH_CONTROLL_DEVICE_FRESHAIR)
public class FreshAirDispatcher extends AbstractFragmentDispatcher {
    BaseActivity activity;
    FreshAirBinding binding;
    Map<String, Integer> modeIndex = new HashMap<>();
    AbstractHolder<ModeItem, SettingMode> prevMode;
    AbstractHolder<DeviceItem, FreshAir> prevDevice;

    @Override
    public int getLayout() {
        return R.layout.tabsp_freshair_layout;
    }

    @OnItemClick(R.id.devicelist)
    public void onDeviceSelect(AdapterView<?> adapterView, View view, int i, long l) {
        AbstractHolder<DeviceItem, FreshAir> holder = (AbstractHolder<DeviceItem, FreshAir>) view.getTag();
        if (holder != prevDevice) {
            if (prevDevice != null) prevDevice.setSelected(false);
            holder.setSelected(true);
            prevDevice = holder;
            onSelectFreshair(prevDevice.getProperty());
        }
    }

    private void onSelectFreshair(FreshAir freshAir) {
        FreshAir.Mode mode = freshAir.getMode();
        if (prevMode != null && !mode.getMode().equals(prevMode.getProperty().getMode())) {
            prevMode.setSelected(false);
            if (modeIndex.containsKey(mode.getMode())) {
                int index = modeIndex.get(mode.getMode());
                View view = binding.freshairMode.getChildAt(index);
                if (view != null && view.getTag() != null) {
                    AbstractHolder holder = (AbstractHolder) view.getTag();
                    holder.setSelected(true);
                    prevMode = holder;
                }
            } else
                prevMode = null;

        }
    }

    @OnItemClick(R.id.freshair_mode)
    public void onModeSelect(AdapterView<?> adapterView, View view, int i, long l) {
        AbstractHolder<ModeItem, SettingMode> holder = (AbstractHolder<ModeItem, SettingMode>) view.getTag();
        if (prevDevice != null && prevMode != holder) {
            if (prevMode != null) prevMode.setSelected(false);
            holder.setSelected(true);
            prevMode = holder;

        }
    }

    @OnClick({R.id.switcher_open, R.id.switcher_close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.switcher_open:
                if (prevDevice != null) {
                    Controller.getInstance().close(prevDevice.getProperty());
                }
                break;
            case R.id.switcher_close:
                if (prevDevice != null) {
                    Controller.getInstance().open(prevDevice.getProperty());
                }
                break;
        }
    }

    @Override
    public void dispatch(View view) {
        ButterKnife.bind(this, view);
        binding = DataBindingUtil.bind(view);
        List<SettingMode> modes = new ArrayList<>();
        modes.add(createAirconditionerMode(R.string.name_freshair_auto, modes));
        modes.add(createAirconditionerMode(R.string.name_freshair_low, modes));
        modes.add(createAirconditionerMode(R.string.name_freshair_middle, modes));
        modes.add(createAirconditionerMode(R.string.name_freshair_high, modes));
        HolderAdapter adapter = new HolderAdapter(R.layout.tabsp_mode_item_layout, new ModeSelectFactory());
        adapter.setData(modes);
        binding.freshairMode.setNumColumns(modes.size());
        binding.setModeAdapter(adapter);
        binding.freshairMode.postDelayed(() -> {
            int childCount = binding.freshairMode.getChildCount();
            for (int i = 0; i <childCount; i++) {
                ((AbstractHolder<ModeItem, SettingMode>) binding.freshairMode.getChildAt(i).getTag()).setSelected(false);
            }
        }, 50);
        List<IDevice> devices = Controller.getInstance().getDevicesByDeviceType(DeviceType.FRESHAIR);
        if (devices != null && devices.size() > 0) {
            adapter = new HolderAdapter(R.layout.tabsp_device_listviewitem_layout, new ListViewDeviceFactory());
            adapter.setData(devices);
            binding.setAdapter(adapter);
        }

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    private SettingMode createAirconditionerMode(int string, List<SettingMode> modes) {
        SettingMode mode = null;
        String name = TabspApplication.getInstance().getString(string);
        switch (string) {
            case R.string.name_freshair_auto:
                mode = new SettingMode(name, R.drawable.mediaroom4_freshair_auto_select, R.drawable.mediaroom4_freshair_auto_unselect, "1");
                break;
            case R.string.name_freshair_low:
                mode = new SettingMode(name, R.drawable.mediaroom4_freshair_low_select, R.drawable.mediaroom4_freshair_low_unselect, "2");
                break;
            case R.string.name_freshair_middle:
                mode = new SettingMode(name, R.drawable.mediaroom4_freshair_middle_select, R.drawable.mediaroom4_freshair_middle_unselect, "3");
                break;
            case R.string.name_freshair_high:
                mode = new SettingMode(name, R.drawable.mediaroom4_freshair_high_select, R.drawable.mediaroom4_freshair_high_select, "4");
                break;
        }
        modeIndex.put(mode.getMode(), modes.size());
        return mode;
    }

    @Override
    public <E> void dispatch(View view, E e) {
        if (e instanceof BaseActivity) this.activity = (BaseActivity) e;
        this.dispatch(view);
    }

    /**
     * 设备重新加载，界面数据也重新加载, 事件触发源在DeviceControllerImpl.java文件
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDeviceLoaded(DeviceLoadedEvent event) {
        List<IDevice> devices = Controller.getInstance().getDevicesByDeviceType(DeviceType.FRESHAIR);
        if (devices != null && devices.size() > 0) {
            binding.getAdapter().setData(devices);
            binding.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public void release() {
        super.release();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
