package com.sunmnet.mediaroom.tabsp.controll.version3.adapters;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.device.bean.Airconditioner;
import com.sunmnet.mediaroom.device.bean.Device;
import com.sunmnet.mediaroom.device.bean.DeviceState;
import com.sunmnet.mediaroom.device.bean.DeviceType;
import com.sunmnet.mediaroom.device.bean.FreshAir;
import com.sunmnet.mediaroom.device.bean.IDevice;
import com.sunmnet.mediaroom.device.bean.Interactive;
import com.sunmnet.mediaroom.device.controll.Controller;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.TabspApplication;
import com.sunmnet.mediaroom.tabsp.bean.SettingMode;
import com.sunmnet.mediaroom.tabsp.databinding.AirconditionerControllItem;
import com.sunmnet.mediaroom.tabsp.databinding.ControllItem;
import com.sunmnet.mediaroom.tabsp.databinding.FreshAirControllItem;
import com.sunmnet.mediaroom.tabsp.databinding.InteractiveControllItem;
import com.sunmnet.mediaroom.tabsp.databinding.RollingItem;
import com.sunmnet.mediaroom.tabsp.ui.adapter.AbstractHolder;
import com.sunmnet.mediaroom.tabsp.ui.adapter.BaseAdapter;
import com.sunmnet.mediaroom.tabsp.ui.adapter.IHolder;
import com.sunmnet.mediaroom.tabsp.ui.adapter.deviceFactory.ModeSelectFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class TypeAdapter<T extends ViewDataBinding, E> extends BaseAdapter<E> {
    DeviceType type;
    static int selected, unselect;

    public TypeAdapter(DeviceType type, int selected, int unselected) {
        this.selected = selected;
        this.unselect = unselected;
        this.type = type;
        loadData();
    }

    private void loadData() {
        this.data = (List<E>) Controller.getInstance().getDevicesByDeviceType(type);
    }

    public DeviceType getType() {
        return this.type;
    }

    public void changeDeviceType(DeviceType type) {
        this.type = type;
        loadData();
    }

    IHolder.HolderFactory factory = new IHolder.HolderFactory() {
        @Override
        public IHolder newHolder() {
            if (type.getDeviceType().equalsIgnoreCase(DeviceType.AIRCONDITIONER.getDeviceType())) {
                return new AirconditionerHolder();
            } else if (type.getDeviceType().equalsIgnoreCase(DeviceType.INTERACTIVE.getDeviceType())) {
                return new InteractiveHolder();
            } else if (type.getDeviceType().equalsIgnoreCase(DeviceType.FRESHAIR.getDeviceType())) {
                return new FreshAirHolder();
            } else {
                return new NormalDeviceHolder();
            }
        }
    };

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        T t = null;
        IHolder holder = null;
        if (convertView == null) {
            //创建一个databinding
            t = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), getLayoutId(), parent, false);
            //获取convertView
            convertView = t.getRoot();
            holder = factory.newHolder();
            holder.bindView(convertView);
            convertView.setTag(holder);
        } else {
            holder = (IHolder) convertView.getTag();
            t = DataBindingUtil.getBinding(convertView);
        }
        holder.setProperty(t, this.data.get(i));
        return convertView;
    }

    private int getLayoutId() {
        if (this.type.getDeviceType().equalsIgnoreCase(DeviceType.AIRCONDITIONER.getDeviceType())) {
            return R.layout.tabsp_version3_airconditioner_item_layout;
        } else if (this.type.getDeviceType().equalsIgnoreCase(DeviceType.FRESHAIR.getDeviceType())) {
            return R.layout.tabsp_version3_freshair_item_layout;
        } else if (this.type.getDeviceType().equalsIgnoreCase(DeviceType.INTERACTIVE.getDeviceType())) {
            return R.layout.tabsp_version3_interactive_item_layout;
        } else {
            return R.layout.tabsp_version3_device_controll_item_layout;
        }
    }

    public static class NormalDeviceHolder extends AbstractHolder<ControllItem, Device> {
        Device device;

        @OnClick({R.id.single_open, R.id.single_close})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.single_open:
                    Controller.getInstance().open(device);
                    break;
                case R.id.single_close:
                    Controller.getInstance().close(device);
                    break;
            }
        }

        @Override
        public void bindView(View view) {
            ButterKnife.bind(this, view);
        }

        @Override
        public void setProperty(ControllItem controllItem, Device device) {
            controllItem.setDev(device);
            this.device = device;
        }
    }

    public static class AirconditionerHolder extends SpecialHolder<AirconditionerControllItem, Airconditioner, SettingMode> {
        static List<SettingMode> modes;
        static Map<String, Integer> modeIndex = new HashMap<>();

        static {
            modes = new ArrayList<>();
            modes.add(createAirconditionerMode(R.string.name_airconditioner_heat));
            modes.add(createAirconditionerMode(R.string.name_airconditioner_cold));
            modes.add(createAirconditionerMode(R.string.name_airconditioner_fan));
            modes.add(createAirconditionerMode(R.string.name_airconditioner_xeransis));
            modes.add(createAirconditionerMode(R.string.name_airconditioner_auto));
        }

        private static SettingMode createAirconditionerMode(int string) {
            SettingMode mode = null;
            String name = TabspApplication.getInstance().getString(string);
            switch (string) {
                case R.string.name_airconditioner_heat:
                    mode = new SettingMode(name, R.drawable.aircondtioner_mode_heat_on, R.drawable.aircondtioner_mode_heat, "5");
                    break;
                case R.string.name_airconditioner_cold:
                    mode = new SettingMode(name, R.drawable.aircondtioner_mode_cold_on, R.drawable.aircondtioner_mode_cold, "2");
                    break;
                case R.string.name_airconditioner_fan:
                    mode = new SettingMode(name, R.drawable.aircondtioner_mode_fan_on, R.drawable.aircondtioner_mode_fan, "4");
                    break;
                case R.string.name_airconditioner_xeransis:
                    mode = new SettingMode(name, R.drawable.aircondtioner_mode_xeransis_on, R.drawable.aircondtioner_mode_xeransis, "3");
                    break;
                case R.string.name_airconditioner_auto:
                    mode = new SettingMode(name, R.drawable.aircondtioner_mode_auto_on, R.drawable.aircondtioner_mode_auto, "1");
                    break;
            }
            modeIndex.put(mode.getMode(), modes.size());
            return mode;
        }

        @Override
        protected void onModeSelected(SettingMode holder) {
            getProperty().getSetting().setMode(holder.getMode());
            String state = getProperty().getControllString();
            Controller.getInstance().setState(getProperty(), state);
        }

        @Override
        protected List<SettingMode> getModes() {
            return modes;
        }

        @Override
        public void setProperty(AirconditionerControllItem airconditionerControllItem, Airconditioner airconditioner) {
            airconditionerControllItem.setDev(airconditioner);
            this.property = airconditioner;
        }

        @Override
        protected void onSwitchState(View view) {
            switch (view.getId()) {
                case R.id.single_close:
                    getProperty().getSetting().setDeviceState(DeviceState.CLOSED);
                    break;
                case R.id.single_open:
                    getProperty().getSetting().setDeviceState(DeviceState.OPENED);
                    break;
            }
            Controller.getInstance().setState(getProperty(),getProperty().getControllString());
        }
    }
    public static class FreshAirHolder extends SpecialHolder<FreshAirControllItem, FreshAir, SettingMode> {
        static Map<String, Integer> modeIndex = new HashMap<>();
        static List<SettingMode> modes;

        static {
            List<SettingMode> modes = new ArrayList<>();
            modes.add(createAirconditionerMode(R.string.name_freshair_auto, modes));
            modes.add(createAirconditionerMode(R.string.name_freshair_low, modes));
            modes.add(createAirconditionerMode(R.string.name_freshair_middle, modes));
            modes.add(createAirconditionerMode(R.string.name_freshair_high, modes));
        }

        private static SettingMode createAirconditionerMode(int string, List<SettingMode> modes) {
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
        protected void onModeSelected(SettingMode holder) {
            RunningLog.run("选中了模式：" + holder.getName());
        }

        @Override
        protected List<SettingMode> getModes() {
            return modes;
        }

        @Override
        public void setProperty(FreshAirControllItem freshAirControllItem, FreshAir freshAir) {
            freshAirControllItem.setDev(freshAir);
        }
    }

    public static class InteractiveHolder extends SpecialHolder<InteractiveControllItem, Interactive, SettingMode> {
        static Map<String, Integer> modeIndex = new HashMap<>();
        static List<SettingMode> modes;

        static {
            List<IDevice> devices = Controller.getInstance().getDevicesByDeviceType(DeviceType.INTERACTIVE);
            Interactive interactive = (Interactive) devices.get(0);
            List<SettingMode> settingModes = new ArrayList<>();
            Map<String, String> videoInputs = interactive.getVideoParams().getVideoSourceNameMap();
            if (videoInputs == null || videoInputs.size() <= 0) {
                videoInputs = interactive.getVideoParams().getVideoSourceMap();
            }
            Iterator<String> keys = videoInputs.keySet().iterator();
            String name = "测试";
            int index = 0;
            while (keys.hasNext()) {
                String key = keys.next();
                SettingMode settingMode = new SettingMode(name + (index + 1), Color.TRANSPARENT, Color.TRANSPARENT, key);
                settingModes.add(settingMode);
                modeIndex.put(key, index);
                index++;
            }
            modes = settingModes;
        }

        @Override
        protected void onSwitchState(View view) {
            switch (view.getId()) {
                case R.id.single_close:
                    Controller.getInstance().close(getProperty());
                    break;
                case R.id.single_open:
                    Controller.getInstance().open(getProperty());
                    break;
            }
        }

        @Override
        protected int getItemLayout() {
            return R.layout.tabsp_rollingtext_item;
        }

        private static final String OPT_NAME = "interactives";

        @Override
        protected void onModeSelected(SettingMode holder) {
            Map<String, String> params = new HashMap<>();
            params.put("deviceType", getProperty().getDeviceTypeCode());
            params.put("deviceCode", getProperty().getDeviceCode());
            params.put("videoSource", holder.getMode());
            Controller.getInstance().setDeviceSettings(OPT_NAME, params);
        }

        @Override
        protected IHolder getHolder() {
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
                    int color = selected ? TypeAdapter.selected : TypeAdapter.unselect;
                    if (color != 0) this.textView.setTextColor(color);
                    this.rollingItem.rollingText.setEllipsize(selected ? TextUtils.TruncateAt.valueOf("MARQUEE") : TextUtils.TruncateAt.valueOf("END"));
                }
            };
        }

        @Override
        protected List<SettingMode> getModes() {
            return modes;
        }

        @Override
        public void setProperty(InteractiveControllItem interactiveControllItem, Interactive interactive) {
            interactiveControllItem.setDev(interactive);
            this.property = interactive;
        }
    }

    public static abstract class SpecialHolder<T extends ViewDataBinding, E, F> extends AbstractHolder<T, E> implements View.OnClickListener {
        AbstractHolder<T, E> prevHolder;

        @Override
        public void onClick(View v) {
            AbstractHolder holder = (AbstractHolder) v.getTag();
            if (holder != prevHolder) {
                if (prevHolder != null) prevHolder.setSelected(false);
                holder.setSelected(true);
                prevHolder = holder;
                onModeSelected((F) holder.getProperty());
            }
        }

        @OnClick({R.id.single_close, R.id.single_open})
        public void onSwicherClick(View view) {
            onSwitchState(view);
        }

        protected void onSwitchState(View view) {

        }

        protected abstract void onModeSelected(F holder);

        protected LinearLayout modeLayout;

        private void addMode(F mode, LinearLayout layout, LinearLayout.LayoutParams params, LayoutInflater inflater) {
            View modeItemLayout = inflater.inflate(getItemLayout(), null, false);
            IHolder holder = getHolder();
            holder.bindView(modeItemLayout);
            ViewDataBinding binding = DataBindingUtil.bind(modeItemLayout);
            holder.setProperty(binding, mode);
            modeItemLayout.setTag(holder);
            modeItemLayout.setOnClickListener(this);
            layout.addView(modeItemLayout, params);
        }

        private LinearLayout createModeLayout(View view) {
            LinearLayout layout = new LinearLayout(view.getContext());
            layout.setOrientation(LinearLayout.HORIZONTAL);
            return layout;
        }

        private LinearLayout addMode(int start, int stop, LinearLayout container, LayoutInflater inflater, List<F> modes) {
            LinearLayout linearLayout = createModeLayout(container);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = linearLayout.getContext().getResources().getDimensionPixelSize(R.dimen.px_20);
            for (int i = start; i < stop; i++) {
                addMode(modes.get(i), linearLayout, params, inflater);
            }
            return linearLayout;
        }

        @Override
        public void bindView(View view) {
            ButterKnife.bind(this, view);
            modeLayout = view.findViewById(R.id.modes);
            modeLayout.setOrientation(LinearLayout.VERTICAL);
            LayoutInflater inflater = LayoutInflater.from(view.getContext());
            LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int size = view.getContext().getResources().getDimensionPixelSize(R.dimen.px_20);
            rowParams.topMargin = size;
            rowParams.bottomMargin = size;
            List<F> modes = getModes();
            List<LinearLayout> layouts = new ArrayList<>();
            if (modes.size() > 5) {
                int count = modes.size() / 5;
                if (count * 5 < modes.size()) count += 1;//分行后的总数量小于原容器的数量，就添加一行
                int stop = modes.size() % 5;
                int start = 0;
                for (int i = 0; i < count; i++) {
                    if (i == count - 1) {//最后一行
                        if (stop != 0) {
                            layouts.add(addMode((i) * 5, (i) * 5 + stop, modeLayout, inflater, modes));
                        }
                    } else {
                        layouts.add(addMode(start, (i + 1) * 5, modeLayout, inflater, modes));
                    }
                }
            } else {
                layouts.add(addMode(0, modes.size(), modeLayout, inflater, modes));
            }
            for (int i = 0; i < layouts.size(); i++) {
                modeLayout.addView(layouts.get(i), rowParams);
            }
        }

        protected IHolder getHolder() {
            return new ModeSelectFactory().newHolder();
        }

        protected abstract List<F> getModes();

        @Override
        public abstract void setProperty(T t, E e);

        protected int getItemLayout() {
            return R.layout.tabsp_mode_item_layout;
        }
    }
}
