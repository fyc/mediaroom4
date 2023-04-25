package com.sunmnet.mediaroom.tabsp.controll.version1;

import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sunmnet.mediaroom.common.BaseActivity;
import com.sunmnet.mediaroom.common.tools.CommonUtil;
import com.sunmnet.mediaroom.device.bean.DeviceState;
import com.sunmnet.mediaroom.device.bean.DeviceType;
import com.sunmnet.mediaroom.device.bean.IDevice;
import com.sunmnet.mediaroom.device.bean.Interactive;
import com.sunmnet.mediaroom.device.bean.StateEventType;
import com.sunmnet.mediaroom.device.controll.Controller;
import com.sunmnet.mediaroom.device.events.DeviceLoadedEvent;
import com.sunmnet.mediaroom.device.events.DeviceNotifyEvent;
import com.sunmnet.mediaroom.device.listeners.DeviceStateNotifier;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.TabspApplication;
import com.sunmnet.mediaroom.tabsp.bean.MenuEntity;
import com.sunmnet.mediaroom.tabsp.bean.SettingMode;
import com.sunmnet.mediaroom.tabsp.common.Constants;
import com.sunmnet.mediaroom.tabsp.databinding.DeviceItem;
import com.sunmnet.mediaroom.tabsp.databinding.InteractiveBinding;
import com.sunmnet.mediaroom.tabsp.databinding.ModeItem;
import com.sunmnet.mediaroom.tabsp.databinding.RollingItem;
import com.sunmnet.mediaroom.tabsp.ui.adapter.AbstractHolder;
import com.sunmnet.mediaroom.tabsp.ui.adapter.BindingAdapter;
import com.sunmnet.mediaroom.tabsp.ui.adapter.IHolder;
import com.sunmnet.mediaroom.tabsp.ui.adapter.deviceFactory.ListViewDeviceFactory;
import com.sunmnet.mediaroom.tabsp.ui.dispatch.fragmentDispatcher.AbstractFragmentDispatcher;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
/**
 * 交互屏界面
 * */
@Route(path = Constants.ROUTERPATH_CONTROLL_DEVICE_INTERACTIVE)
public class InteracitveDispatch extends AbstractFragmentDispatcher implements DeviceStateNotifier {
    String parent;
    BaseActivity activity;
    InteractiveBinding binding;
    Map<String, Integer> modeIndex = new HashMap<>();
    AbstractHolder<ModeItem, SettingMode> prevMode;
    AbstractHolder<DeviceItem, Interactive> prevDevice;
    int selected, unselected;
    static Interactive commonDevice;

    static {
        //初始化时给一个全局控制的设备,保证已经操作过的设备依然可以保存当前状态
        commonDevice = new Interactive(DeviceType.INTERACTIVE, TabspApplication.getInstance().getString(R.string.name_globle_controll), null);
        commonDevice.setDeviceState(DeviceState.CLOSED);
    }

    @Override
    public int getLayout() {
        return R.layout.tabsp_interactive_layout;
    }

    @OnItemClick(R.id.interactives)
    public void onListviewItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //选中设备时的逻辑
        AbstractHolder<DeviceItem, Interactive> holder = (AbstractHolder<DeviceItem, Interactive>) view.getTag();
        if (prevDevice != holder) {
            if (prevDevice != null) prevDevice.setSelected(false);
            holder.setSelected(true);
            prevDevice = holder;
            onSelect(prevDevice.getProperty());
        }
    }

    @OnItemClick(R.id.interactive_modes)
    public void onModeClick(AdapterView<?> adapterView, View view, int i, long l) {
        //选中模式时的切换
        AbstractHolder<ModeItem, SettingMode> holder = (AbstractHolder<ModeItem, SettingMode>) view.getTag();
        if (prevDevice != null && prevMode != holder) {
            if (prevMode != null) {
                prevMode.setSelected(false);
            }
            holder.setSelected(true);
            prevMode = holder;
            changeMode(prevMode.getProperty().getMode());
        }
    }

    private void setSwitcher(Interactive interactive) {
        int drawable = 0;
        switch (interactive.getDeviceState()) {
            case OPENNONET:
            case OPENED:
                drawable = R.drawable.opened;
                binding.switcherProgress.setVisibility(View.GONE);
                break;
            case CLOSED:
            case ERROR:
                drawable = R.drawable.closed;
                binding.switcherProgress.setVisibility(View.GONE);
                break;
            case OPENNING:
                drawable = R.drawable.closed;
                binding.switcherProgress.setVisibility(View.VISIBLE);
                break;
            case CLOSING:
                drawable = R.drawable.opened;
                binding.switcherProgress.setVisibility(View.VISIBLE);
                break;
        }
        CommonUtil.loadResourceIntoImage(activity, drawable, binding.switcher);
    }

    /**
     * 设备选中时更新界面的状态以及模式
     */
    private void onSelect(Interactive airconditioner) {
        setSwitcher(airconditioner);
        Interactive.InteractiveMode setting = airconditioner.getMode();
        if (prevMode != null) {
            prevMode.setSelected(false);
            if (setting.getMode() != null && !setting.getMode().equals(prevMode.getProperty())) {
                int index = modeIndex.get(setting.getMode());
                View view = binding.interactiveModes.getChildAt(index);
                if (view != null && view.getTag() != null) {
                    AbstractHolder holder = (AbstractHolder) view.getTag();
                    holder.setSelected(true);
                    prevMode = holder;
                }
            }
        }
    }

    private static final String OPT_NAME = "interactives";

    /**
     * 模式切换
     */
    private void changeMode(String state) {
        if (prevDevice != null) {
            Map<String, String> params = new HashMap<>();
            params.put("deviceType", prevDevice.getProperty().getDeviceTypeCode());
            params.put("deviceCode", prevDevice.getProperty().getDeviceCode());
            //操作音量加减用的状态值 为1与2 其他为设置开关状态
            if (!state.equals("1") && !state.equals("2")) {
                //只改变状态
                prevDevice.getProperty().getMode().setMode(state);
                params.put("videoSource", state);
                if (prevDevice.getProperty().getDeviceCode() == null) {
                    //全局设置，循环设备列表，所有的都设置成相同的状态
                    List<Interactive> devices = ((BindingAdapter) binding.interactives.getAdapter()).getData();
                    for (int i = 0, size = devices.size(); i < size; i++) {
                        Interactive dev = devices.get(i);
                        dev.getMode().setMode(state);
                    }
                }
            } else {
                params.put("control", state);
            }
            //设置交互屏状态
            Controller.getInstance().setDeviceSettings(OPT_NAME, params);
        }
    }

    @OnClick({R.id.switcher, R.id.volumn_plus_btn, R.id.volumn_minus_btn, R.id.back_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.switcher:
                if (prevDevice != null) {
                    Interactive interactive = prevDevice.getProperty();
                    if (interactive.getDeviceCode() == null) {
                        DeviceState state;
                        if (interactive.getDeviceState() == DeviceState.CLOSED) {
                            state = DeviceState.OPENED;
                        } else {
                            state = DeviceState.CLOSED;
                        }
                        Controller.getInstance().reverse(interactive);
                        interactive.setDeviceState(state);
                    } else {
                        Controller.getInstance().reverse(interactive);
                    }
                    setSwitcher(interactive);
                }
                break;
            case R.id.volumn_plus_btn:
                changeMode("1");//声音加
                break;
            case R.id.volumn_minus_btn:
                changeMode("2");//声音减
                break;
            case R.id.back_btn://返回按钮
                MenuEntity menuEntity = new MenuEntity(parent, null, null);
                EventBus.getDefault().post(menuEntity);
                break;
        }
    }

    private List<SettingMode> initModes(Interactive interactive) {
        List<SettingMode> settingModes = new ArrayList<>();
        Map<String, String> videoInputs = interactive.getVideoParams().getVideoSourceMap();
        Map<String, String> videoNames = interactive.getVideoParams().getVideoSourceNameMap();
        if (videoInputs == null || videoNames == null) return settingModes;
        int index = 0;
        Iterator<String> keys = videoInputs.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            if (videoNames.containsKey(key)) {
                SettingMode settingMode = new SettingMode(videoNames.get(key), Color.TRANSPARENT, Color.TRANSPARENT, key);
                settingModes.add(settingMode);
                modeIndex.put(key, index);
                index++;
            }
        }
        return settingModes;
    }

    @Override
    public void dispatch(View view) {
        ButterKnife.bind(this, view);
        binding = DataBindingUtil.bind(view);
        binding.switcher.setImageResource(R.drawable.switcher_closed_effect);
        CommonUtil.loadBackgroundImage(this.activity, R.drawable.mediaoom4_interactive_volumn, binding.volumnIcon);
        binding.volumnMinusBtn.setBackgroundResource(R.drawable.interactive_volumn_minus);
        binding.volumnPlusBtn.setBackgroundResource(R.drawable.interactive_volumn_plus);
        binding.setTitleName(TabspApplication.getInstance().getString(R.string.name_interactive) + "设置");
        List<IDevice> devices = Controller.getInstance().getDevicesByDeviceType(DeviceType.INTERACTIVE);
        if (devices != null && devices.size() > 0) {
            listenToStateChange(1);
            ListViewDeviceFactory.init(this.activity, R.attr.colorPrimary, R.attr.device_black_text_color);
            BindingAdapter adapter = new BindingAdapter(R.layout.tabsp_device_listviewitem_layout, new ListViewDeviceFactory());
            List<SettingMode> modes = initModes((Interactive) devices.get(0));
            BindingAdapter modeAdapter = new BindingAdapter(R.layout.tabsp_rollingtext_item, new IHolder.HolderFactory() {
                @Override
                public IHolder newHolder() {
                    return new AbstractHolder<RollingItem, SettingMode>() {
                        RollingItem rollingItem;
                        TextView textView;

                        @Override
                        public void bindView(View view) {
                            this.textView = (TextView) view;
                        }

                        @Override
                        public void setProperty(RollingItem rollingItem, SettingMode settingMode) {
                            this.property = settingMode;
                            this.rollingItem = rollingItem;
                            rollingItem.setItem(settingMode);
                            setSelected(false);
                        }

                        @Override
                        public RollingItem getViewDataBinding() {
                            return rollingItem;
                        }

                        @Override
                        public SettingMode getProperty() {
                            return property;
                        }

                        @Override
                        public void setSelected(boolean selected) {
                            textView.setBackgroundResource(selected ? R.drawable.scene_item_select_background : R.drawable.scene_item_background);
                            int color = selected ? InteracitveDispatch.this.selected : InteracitveDispatch.this.unselected;
                            if (color != 0) this.textView.setTextColor(color);
                            this.rollingItem.rollingText.setEllipsize(selected ? TextUtils.TruncateAt.valueOf("MARQUEE") : TextUtils.TruncateAt.valueOf("END"));
                        }
                    };
                }
            });
            modeAdapter.setData(modes);
            if (activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                binding.interactiveModes.setNumColumns(5);
            } else {
                binding.interactiveModes.setNumColumns(3);
            }
            binding.setSettingAdapter(modeAdapter);
            List<IDevice> newDevices = new ArrayList<>();
            newDevices.add(commonDevice);
            newDevices.addAll(devices);
            adapter.setData(newDevices);
            binding.setAdapter(adapter);
            binding.interactives.postDelayed(new Runnable() {
                @Override
                public void run() {
                    binding.interactives.performItemClick(binding.interactives.getChildAt(0), 0, binding.interactives.getItemIdAtPosition(0));
                }
            }, 50);

        }

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    private void listenToStateChange(int type) {
        DeviceNotifyEvent event = new DeviceNotifyEvent();
        event.setMessage(this);
        StateEventType eventType = new StateEventType();
        eventType.type = type;
        eventType.key = DeviceType.INTERACTIVE.getDeviceType();
        event.setEventType(eventType);
        EventBus.getDefault().post(event);
    }

    @Override
    public <E> void dispatch(View view, E e) {
        if (e instanceof BaseActivity) {
            this.activity = (BaseActivity) e;
            selected = CommonUtil.getColorByAttribute(activity, R.attr.common_text_color);
            unselected = CommonUtil.getColorByAttribute(activity, R.attr.device_black_text_color);
        }
        this.dispatch(view);
    }

    @Override
    public void dispatch(String type, String parent) {
        this.parent = parent;
    }


    @Override
    public void release() {
        super.release();
        listenToStateChange(0);
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public String getListenerKey() {
        return DeviceType.INTERACTIVE.getDeviceType();
    }

    @Override
    public void callUpdate() {
    }

    @Override
    public void callUpdate(String code) {
        if (code != null && code.equals(DeviceType.INTERACTIVE.getDeviceType())) {
            //选中的设备的状态有更新
            if (prevDevice != null) {
                binding.switcher.post(new Runnable() {
                    @Override
                    public void run() {
                        setSwitcher(prevDevice.getProperty());
                    }
                });
            }
        }
    }

    /**
     * 设备重新加载，界面数据也重新加载, 事件触发源在DeviceControllerImpl.java文件
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDeviceLoaded(DeviceLoadedEvent event) {
        List<IDevice> devices = Controller.getInstance().getDevicesByDeviceType(DeviceType.INTERACTIVE);
        if (devices != null && devices.size() > 0) {
            List<SettingMode> modes = initModes((Interactive) devices.get(0));
            binding.getSettingAdapter().setData(modes);
            binding.getSettingAdapter().notifyDataSetChanged();
            List<IDevice> newDevices = new ArrayList<>();
            newDevices.add(commonDevice);
            newDevices.addAll(devices);
            binding.getAdapter().setData(newDevices);
            binding.getAdapter().notifyDataSetChanged();
        }
    }
}
