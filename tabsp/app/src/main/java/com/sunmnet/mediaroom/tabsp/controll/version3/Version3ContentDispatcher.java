package com.sunmnet.mediaroom.tabsp.controll.version3;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sunmnet.mediaroom.common.BaseActivity;
import com.sunmnet.mediaroom.common.events.Event;
import com.sunmnet.mediaroom.common.events.EventType;
import com.sunmnet.mediaroom.common.tools.CommonUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.device.DeviceBuilder;
import com.sunmnet.mediaroom.device.bean.DeviceScene;
import com.sunmnet.mediaroom.device.bean.DeviceType;
import com.sunmnet.mediaroom.device.bean.EnvirmentDevice;
import com.sunmnet.mediaroom.device.bean.MatrixScene;
import com.sunmnet.mediaroom.device.bean.OtherSetting;
import com.sunmnet.mediaroom.device.controll.Controller;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.TabspApplication;
import com.sunmnet.mediaroom.tabsp.bean.DialogInfo;
import com.sunmnet.mediaroom.tabsp.common.Constants;
import com.sunmnet.mediaroom.tabsp.controll.version1.MainPageSceneHolder;
import com.sunmnet.mediaroom.tabsp.controll.version3.adapters.DeviceMenu;
import com.sunmnet.mediaroom.tabsp.databinding.SceneItem;
import com.sunmnet.mediaroom.tabsp.databinding.V3Content;
import com.sunmnet.mediaroom.tabsp.interfaces.DialogListener;
import com.sunmnet.mediaroom.tabsp.ui.adapter.AbstractHolder;
import com.sunmnet.mediaroom.tabsp.ui.adapter.HolderAdapter;
import com.sunmnet.mediaroom.tabsp.ui.adapter.IHolder;
import com.sunmnet.mediaroom.tabsp.ui.dispatch.fragmentDispatcher.AbstractFragmentDispatcher;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

@Route(path = Constants.ROUTERPATH_CONTROLL_V3_CONTENT)
public class Version3ContentDispatcher extends AbstractFragmentDispatcher implements View.OnClickListener {
    BaseActivity activity;
    V3Content binding;
    @BindView(R.id.volumn_value)
    TextView volumnTextView;

    @BindView(R.id.v3quick_container)
    LinearLayout quickContainer;

    @BindView(R.id.controll_scene_grid)
    GridView scene;

    @Override
    public int getLayout() {
        return R.layout.tabsp_version3_main_content_layout;
    }

    @OnClick({R.id.volumn_plus_btn, R.id.volumn_minus_btn, R.id.classon_onekey, R.id.classover_onekey})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.volumn_plus_btn:
                optVolumn(5);
                break;
            case R.id.volumn_minus_btn:
                optVolumn(-5);
                break;
            case R.id.classon_onekey:
                Controller.getInstance().classOn();
                break;
            case R.id.classover_onekey:
                //如果开启了防误触提示
                if (TabspApplication.getInstance().getConfig().isAntiTouchMode()) {
                    DialogInfo dialogInfo = new DialogInfo(DialogInfo.DialogType.CONFIRM_CLASSOVER, new DialogListener() {
                        @Override
                        public <T> void onConfirmed(T t) {
                            Controller.getInstance().classOver();
                        }

                        @Override
                        public void onCancel() {

                        }

                        @Override
                        public void onTick(int count) {

                        }
                    });
                    Event<DialogInfo, EventType> classOver = new Event<>();
                    classOver.setMessage(dialogInfo);
                    classOver.setEventType(EventType.DIALOG_EVENT);
                    EventBus.getDefault().post(classOver);
                } else {
                    Controller.getInstance().classOver();
                }
                break;
            case R.id.batch_close:
                controll(false, view);
                break;
            case R.id.batch_open:
                controll(true, view);
                break;
        }
    }

    private void controll(boolean switcher, View view) {
        if (view.getParent() != null) {
            Object object = ((View) view.getParent()).getTag();
            if (object != null && object instanceof DeviceType) {
                if (switcher) {
                    Controller.getInstance().open((DeviceType) object);
                } else {
                    Controller.getInstance().close((DeviceType) object);
                }
            }
        }
    }

    private void optVolumn(int value) {
        int cacheValue = 0;
        try {
            cacheValue = Integer.parseInt(volumnTextView.getText().toString());
        } catch (Exception e) {
            RunningLog.error(e);
        }
        cacheValue += value;
        if (cacheValue > 100) cacheValue = 100;
        if (cacheValue < 0) cacheValue = 0;
        String volumn = cacheValue + "";
        Controller.getInstance().setVolumn(volumn);
        if (volumnTextView != null) volumnTextView.setText(volumn);
    }

    int selected = Color.BLUE, unselected = Color.WHITE;
    AbstractHolder<SceneItem, DeviceScene> prevSelected;

    @OnItemClick(R.id.controll_scene_grid)
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        AbstractHolder<SceneItem, DeviceScene> holder = (AbstractHolder) view.getTag();
        if (holder != prevSelected) {
            if (prevSelected != null) {
                prevSelected.setSelected(false);
            }
            holder.setSelected(true);
            prevSelected = holder;
            Controller.getInstance().setScene(holder.getProperty());
        }
    }

    @Override
    public <E> void dispatch(View view, E e) {
        if (e instanceof BaseActivity) {
            this.activity = (BaseActivity) e;
            selected = CommonUtil.getColorByAttribute(this.activity, R.attr.common_text_color);
            unselected = CommonUtil.getColorByAttribute(this.activity, R.attr.device_black_text_color);
        }
        this.dispatch(view);
    }

    @Override
    public void dispatch(View view) {
        if (view != null) {
            ButterKnife.bind(this, view);
            binding = DataBindingUtil.bind(view);
            List<MatrixScene> scenes = Controller.getInstance().getMatrixConfig().getScenes();
            if (scenes != null && scenes.size() > 0) {
                List<DeviceScene> caches = Controller.getInstance().getScenes();
                scene.setNumColumns(3);
                HolderAdapter adapter = new HolderAdapter(R.layout.tabsp_version1_scene_item_layout, new IHolder.HolderFactory() {
                    @Override
                    public IHolder newHolder() {
                        return new MainPageSceneHolder(selected, unselected);
                    }
                });
                adapter.setData(caches);
                binding.setHolderAdapter(adapter);
            }
            initDeviceControll(quickContainer);
        }
    }

    private void initDeviceControll(LinearLayout v3quick_container) {
        if (v3quick_container != null) {
            LayoutInflater inflater = LayoutInflater.from(this.activity);
            List<DeviceMenu> menus = ((Version3Dispatcher) TabspApplication.getInstance().getDispatcher()).getEffectiveMenu(null);
            DeviceType[] deviceType = new DeviceType[menus.size()];
            for (int i = 0, size = menus.size(); i < size; i++) {
                deviceType[i] = menus.get(i).deviceType;
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            for (int i = 0; i < deviceType.length; i++) {
                if (Controller.getInstance().getDevicesByDeviceType(deviceType[i]) != null) {
                    View view = inflater.inflate(R.layout.tabsp_version3_quick_item_layout, null, false);
                    TextView textView = view.findViewById(R.id.device_name);
                    if (textView != null)
                        textView.setText(DeviceBuilder.getDeviceTypeName(deviceType[i].getDeviceType()));
                    bindEvent(view.findViewById(R.id.batch_open), deviceType[i], this, "升");
                    bindEvent(view.findViewById(R.id.batch_close), deviceType[i], this, "降");
                    view.setTag(deviceType[i]);
                    v3quick_container.addView(view, params);
                }
            }
            v3quick_container.setGravity(Gravity.CENTER);
        }
    }

    private void bindEvent(View view, DeviceType type, View.OnClickListener onClickListener, String text) {
        if (view != null) {
            if (type == DeviceType.SCREEN && view instanceof View) {
                ((Button) view).setText(text);
            }
            view.setOnClickListener(onClickListener);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loadOtherSetting(OtherSetting setting) {
        if (this.volumnTextView != null && setting.getVolumn() != null) {
            this.volumnTextView.setText(setting.getVolumn());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEnvirmentChanged(EnvirmentDevice envirmentDevice) {
        binding.setEnvirment(envirmentDevice);
    }

    @Override
    public void release() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onReady() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }
}
