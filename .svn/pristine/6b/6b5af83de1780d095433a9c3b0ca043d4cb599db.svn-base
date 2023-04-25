package com.sunmnet.mediaroom.tabsp.controll.version1;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sunmnet.mediaroom.common.BaseActivity;
import com.sunmnet.mediaroom.common.tools.CommonUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.device.DeviceTag;
import com.sunmnet.mediaroom.device.bean.CategoryDevice;
import com.sunmnet.mediaroom.device.bean.DeviceType;
import com.sunmnet.mediaroom.device.bean.IDevice;
import com.sunmnet.mediaroom.device.bean.VagChangeEvent;
import com.sunmnet.mediaroom.device.controll.Controller;
import com.sunmnet.mediaroom.device.events.DeviceLoadedEvent;
import com.sunmnet.mediaroom.devicebean.tag.CommuTag;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.TabspApplication;
import com.sunmnet.mediaroom.tabsp.bean.MenuEntity;
import com.sunmnet.mediaroom.tabsp.bean.VgaItem;
import com.sunmnet.mediaroom.tabsp.common.Constants;
import com.sunmnet.mediaroom.tabsp.databinding.MediaDeviceAdapter;
import com.sunmnet.mediaroom.tabsp.databinding.MediaInfoBinding;
import com.sunmnet.mediaroom.tabsp.databinding.VgaItemBinding;
import com.sunmnet.mediaroom.tabsp.eventbus.events.MediaVgeSourceSetEvent;
import com.sunmnet.mediaroom.tabsp.ui.adapter.AbstractHolder;
import com.sunmnet.mediaroom.tabsp.ui.adapter.MediaBindingAdapter;
import com.sunmnet.mediaroom.tabsp.ui.adapter.deviceFactory.MediaFactory;
import com.sunmnet.mediaroom.tabsp.ui.dispatch.fragmentDispatcher.AbstractFragmentDispatcher;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnItemClick;

import static com.sunmnet.mediaroom.tabsp.controll.version1.Version1Dispatcher.CONTROLLER_VERSION1_MEDIA;

/**
 * 多媒体界面
 */
@Route(path = CONTROLLER_VERSION1_MEDIA)
public class MediaDispatcher extends AbstractFragmentDispatcher {
    MediaDeviceAdapter deviceAdapterBinding;
    MediaBindingAdapter adapter;
    //BindingAdapter vgaAdapter;
    List<VgaItem> vgaItems;
    List<CategoryDevice> categoryDeviceList;
    Map<String, AbstractHolder> holders = new HashMap<>();

    int DEFAULT_SELECTED_TEXT_COLOR, DEFAULT_UNSELECT_TEXT_COLOR;

    AbstractHolder prevHolder;

    @OnItemClick(R.id.medialist)
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        AbstractHolder<MediaInfoBinding, CategoryDevice> holder = (AbstractHolder) view.getTag();
        CategoryDevice categoryDevice = holder.getProperty();
        if (categoryDevice.getDeviceCount() > 1
                || categoryDevice.getDeviceType() == DeviceType.INTERACTIVE
                || categoryDevice.getDeviceType().getDeviceType().startsWith(DeviceTag.DEVICE_CUSTOM)) {
            MenuEntity entity = createMenuEntity(holder.getProperty().getDeviceTypeCode());
            EventBus.getDefault().post(entity);
        } else {
            Controller.getInstance().reverse(categoryDevice.getDevice(0));
        }
    }

    private MenuEntity createMenuEntity(String deviceTypeCode) {
        String link = null;
        if (deviceTypeCode.equals(DeviceTag.DEVICE_INTERACTIVE)) {
            link = Constants.ROUTERPATH_CONTROLL_DEVICE_INTERACTIVE;
        } else if (deviceTypeCode.startsWith(DeviceTag.DEVICE_CUSTOM)) {
            link = Constants.ROUTERPATH_CONTROLL_DEVICE_CUSTOM;
        } else {
            link = Constants.ROUTERPATH_CONTROLL_DEVICE;
        }
        return new MenuEntity(link, deviceTypeCode, CONTROLLER_VERSION1_MEDIA);
    }

    @Override
    public <E> void dispatch(View view, E e) {
        super.dispatch(view, e);
        if (e != null && e instanceof Activity) {

            DEFAULT_SELECTED_TEXT_COLOR = CommonUtil.getColorByAttribute(((BaseActivity) e), R.attr.device_black_text_color);
//            DEFAULT_UNSELECT_TEXT_COLOR = CommonUtil.getColorByAttribute(((BaseActivity) e), R.attr.common_text_color);
            DEFAULT_UNSELECT_TEXT_COLOR = 0xFF8A8A8A;
            this.dispatch(view);
        }
    }

    @Override
    public void dispatch(View view) {
        super.dispatch(view);
        if (view == null)
            return;
        deviceAdapterBinding = DataBindingUtil.bind(view);
        ButterKnife.bind(this, view);
        initCategoryDeviceList();
        adapter = new MediaBindingAdapter(R.layout.tabsp_version1_media_item, new MediaFactory());
        adapter.setData(categoryDeviceList);
        deviceAdapterBinding.setAdapter(adapter);

        createVga();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    private void createVga() {
        Integer suVersion = Controller.getInstance().getSu().getSuVersion();
        //1:wm1000 2:wm2000
        if (suVersion != 1) {
            //只要wm1000有固定信号源
            deviceAdapterBinding.sourceLayout.setVisibility(View.GONE);
            return;
        }
        deviceAdapterBinding.sourceLayout.setVisibility(View.VISIBLE);
        deviceAdapterBinding.mediaVgaList.removeAllViews();
        if (this.vgaItems == null) {
            this.vgaItems = getVgaList();
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        int marginPx = TabspApplication.getInstance().getResources().getDimensionPixelSize(R.dimen.px_20);
        params.rightMargin = marginPx;
        params.topMargin = marginPx;
        for (int i = 0; i < vgaItems.size(); i++) {
            VgaItem item = vgaItems.get(i);
            View view = LayoutInflater.from(TabspApplication.getInstance()).inflate(R.layout.tabsp_version1_mediar_vga_item, null, false);
            deviceAdapterBinding.mediaVgaList.addView(view, params);
            VgaItemBinding binding = DataBindingUtil.bind(view);
            AbstractHolder holder = new AbstractHolder<VgaItemBinding, VgaItem>() {
                View view;

                @Override
                public void bindView(View view) {
                    this.view = view;
                }

                @Override
                public void setProperty(VgaItemBinding vgaItemBinding, VgaItem vgaItem) {
                    super.setProperty(vgaItemBinding, vgaItem);
                    setSelected(vgaItem.isSelect());
                }

                @Override
                public void setSelected(boolean selected) {
                    this.binding.vgaitemName.setText(this.property.getName());
                    int drawable = selected ? this.property.getSelectedDrawable() : this.property.getUnselectDrawable();
                    binding.vgaitemImage.setImageResource(drawable);
                    this.binding.vgaitemName.setTextColor(selected ? DEFAULT_SELECTED_TEXT_COLOR : DEFAULT_UNSELECT_TEXT_COLOR);
                    drawable = selected ? R.drawable.device_on_corner_background : R.drawable.device_off_corner_background;
                    view.setBackgroundResource(drawable);
                }
            };
            holders.put(item.getTag(), holder);
            holder.bindView(view);
            holder.setProperty(binding, item);
            view.setTag(holder);
            view.setOnClickListener(new OnVgaClick());
        }
    }


    private class OnVgaClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            AbstractHolder holder = (AbstractHolder) v.getTag();
            onHolderSelected(holder);
        }
    }

    private void setHolder(AbstractHolder holder) {
        if (holder != null && holder != prevHolder) {
            if (prevHolder != null) prevHolder.setSelected(false);
            holder.setSelected(true);
            prevHolder = holder;
        }
    }

    private void onHolderSelected(AbstractHolder holder) {
        //setHolder(holder);
        Controller.getInstance().sendMsg(CommuTag.DEVICE_VGA_CHANGE, ((VgaItem) holder.getProperty()).getTag());
    }

    private List getVgaList() {
        List<VgaItem> modes = new ArrayList<>();
        modes.add(new VgaItem(R.drawable.mediaroom4_vga_pc_selected, R.drawable.mediaroom4_vga_pc_unselected, "本地电脑", "2"));
        modes.add(new VgaItem(R.drawable.mediaroom4_vga_notebook_selected, R.drawable.mediaroom4_vga_notebook_unselect, "笔记本", "3"));
        modes.add(new VgaItem(R.drawable.mediaroom4_vga_showcase_selected, R.drawable.mediaroom4_vga_showcase_unselect, "展台", "4"));
        modes.add(new VgaItem(R.drawable.mediaroom4_vga_other_selected, R.drawable.mediaroom4_vga_other_unselect, "其他", "1"));
        return modes;
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onVgaChanged(VagChangeEvent event) {
        if (event != null && event.getVgaMode() != null && this.holders.containsKey(event.getVgaMode())) {
            setHolder(holders.get(event.getVgaMode()));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onSetVgaSource(MediaVgeSourceSetEvent event) {
        switch (event.sourceTag) {
            case 2:
                deviceAdapterBinding.mediaVgaList.getChildAt(0).performClick();
                break;
        }
    }

    /**
     * 设备重新加载，界面数据也重新加载, 事件触发源在DeviceControllerImpl.java文件
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDeviceLoaded(DeviceLoadedEvent event) {
        initCategoryDeviceList();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onReady() {
        RunningLog.run("mediardispatcher.onReady");
        int state = lifeCycleState.get();
        if (state == RELEASE) {
            initCategoryDeviceList();
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        }
        super.onReady();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    private void initCategoryDeviceList() {
        if (categoryDeviceList == null) {
            categoryDeviceList = getCategories();
        } else {
            releaseCategoryDeviceList();
            synchronized (categoryDeviceList) {
                categoryDeviceList.addAll(getCategories());
            }
        }
    }

    @Override
    public void invisible() {
        super.invisible();
        RunningLog.run("mediardispatcher.invisible");
    }

    @Override
    public void release() {
        super.release();
        RunningLog.run("mediardispatcher.release");
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        releaseCategoryDeviceList();
    }

    private void releaseCategoryDeviceList() {
        if (categoryDeviceList != null) {
            synchronized (categoryDeviceList) {
                if (categoryDeviceList.size() > 0) {
                    for (CategoryDevice categoryDevice : categoryDeviceList) {
                        categoryDevice.release();
                    }
                    categoryDeviceList.clear();
                }
            }
        }
    }

    @Override
    public int getLayout() {
        return R.layout.tabsp_version1_media_layout;
    }

    /**
     * 获取多媒体设备的集合
     */
    private List<CategoryDevice> getCategories() {
        DeviceType[] queries = new DeviceType[]{
                DeviceType.PC, DeviceType.PROJECTOR, DeviceType.SCREEN,
                DeviceType.SOUND, DeviceType.INTERACTIVE, DeviceType.STUPC,
                DeviceType.SMARTTV
        };
        List<CategoryDevice> categoryDevices = new ArrayList<>();
        for (int i = 0; i < queries.length; i++) {
            CategoryDevice categoryDevice = getCategory(queries[i]);
            if (categoryDevice != null) {
                categoryDevices.add(categoryDevice);
            }
        }
//        categoryDevices.addAll(getCustomDeviceCategory());
        return categoryDevices;
    }

    /**
     * 合并设备到多媒体设备
     */
    private CategoryDevice getCategory(DeviceType deviceType) {
        List<IDevice> devices = Controller.getInstance().getDevicesByDeviceType(deviceType);
        if (devices != null && devices.size() > 0) {
            CategoryDevice categoryDevice = new CategoryDevice(devices);
            return categoryDevice;
        }
        return null;
    }

    private List<CategoryDevice> getCustomDeviceCategory() {
        List<CategoryDevice> categoryDevices = new ArrayList<>();
        List<DeviceType> customTypeList = DeviceType.getCustomTypeList();
        for (DeviceType type : customTypeList) {
            CategoryDevice category = getCategory(type);
            if (category != null) {
                categoryDevices.add(category);
            }
        }
        return categoryDevices;
    }
}
