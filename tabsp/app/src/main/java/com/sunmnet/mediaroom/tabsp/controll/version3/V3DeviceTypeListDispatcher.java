package com.sunmnet.mediaroom.tabsp.controll.version3;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sunmnet.mediaroom.common.BaseActivity;
import com.sunmnet.mediaroom.common.tools.CommonUtil;
import com.sunmnet.mediaroom.device.bean.AbstractDevice;
import com.sunmnet.mediaroom.device.bean.DeviceType;
import com.sunmnet.mediaroom.device.controll.Controller;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.TabspApplication;
import com.sunmnet.mediaroom.tabsp.bean.MenuEntity;
import com.sunmnet.mediaroom.tabsp.common.Constants;
import com.sunmnet.mediaroom.tabsp.controll.version3.adapters.DeviceMenu;
import com.sunmnet.mediaroom.tabsp.controll.version3.bean.MenuType;
import com.sunmnet.mediaroom.tabsp.databinding.DeviceItem;
import com.sunmnet.mediaroom.tabsp.databinding.DeviceTypeList;
import com.sunmnet.mediaroom.tabsp.ui.adapter.AbstractHolder;
import com.sunmnet.mediaroom.tabsp.ui.adapter.BindingAdapter;
import com.sunmnet.mediaroom.tabsp.ui.adapter.HolderAdapter;
import com.sunmnet.mediaroom.tabsp.ui.adapter.IHolder;
import com.sunmnet.mediaroom.tabsp.ui.adapter.deviceFactory.ListViewDeviceFactory;
import com.sunmnet.mediaroom.tabsp.ui.dispatch.fragmentDispatcher.AbstractFragmentDispatcher;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

@Route(path = Constants.ROUTERPATH_CONTROLL_V3_TYPE_LIST)
public class V3DeviceTypeListDispatcher extends AbstractFragmentDispatcher {
    @BindView(R.id.menu)
    ListView types;
    //static Map<String, List<DeviceType>> deviceContainer;
    BindingAdapter adapter;
    int UNSELECT_TEXTCOLOR, SELECT_TEXTCOLOR;

    private static List<DeviceType> checkType(DeviceType... types) {
        List<DeviceType> typeList = new ArrayList<>();
        for (int i = 0; i < types.length; i++) {
            DeviceType type = types[i];
            if (Controller.getInstance().getDevicesByDeviceType(type) != null) typeList.add(type);
        }
        return typeList;
    }

    @Override
    public int getLayout() {
        return R.layout.tabsp_version3_listview_layout;
    }

    AbstractHolder<DeviceTypeList, DeviceMenu> prevHolder;

    @OnItemClick(R.id.menu)
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (view != null) {
            AbstractHolder<DeviceTypeList, DeviceMenu> holder = (AbstractHolder<DeviceTypeList, DeviceMenu>) view.getTag();
            if (holder != prevHolder) {
                if (prevHolder != null) prevHolder.setSelected(false);
                holder.setSelected(true);
                prevHolder = holder;
                EventBus.getDefault().postSticky(prevHolder.getProperty().deviceType);
            }
        }
    }

    @Override
    public void dispatch(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    public <E> void dispatch(View view, E e) {
        if (e != null && e instanceof BaseActivity) {
            UNSELECT_TEXTCOLOR = CommonUtil.getColorByAttribute((Context) e, R.attr.device_black_text_color);
            SELECT_TEXTCOLOR = CommonUtil.getColorByAttribute((Context) e, R.attr.colorPrimary);
        }
        this.dispatch(view);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMenuChange(MenuEntity entity) {
        EventBus.getDefault().removeStickyEvent(entity);
        List<DeviceMenu> deviceMenus = ((Version3Dispatcher) TabspApplication.getInstance()
                .getDispatcher()).getEffectiveMenu(entity.getType());
        if (adapter == null) {
            adapter = new BindingAdapter(R.layout.tabsp_version3_devicype_item_layout, new IHolder.HolderFactory() {
                @Override
                public IHolder newHolder() {
                    return new AbstractHolder<DeviceTypeList, DeviceMenu>() {
                        DeviceTypeList item;
                        View view;

                        @Override
                        public void setProperty(DeviceTypeList airconditionerItem, DeviceMenu type) {
                            this.property = type;
                            this.item = airconditionerItem;
                            airconditionerItem.setType(type);
                        }

                        @Override
                        public void bindView(View view) {
                            this.view = view;
                        }

                        @Override
                        public DeviceTypeList getViewDataBinding() {
                            return this.item;
                        }

                        @Override
                        public DeviceMenu getProperty() {
                            return this.property;
                        }

                        @Override
                        public void setSelected(boolean selected) {
                            if (selected) {
                                item.selectTag.setVisibility(View.VISIBLE);
                                item.modeName.setTextColor(SELECT_TEXTCOLOR);
                                item.modeContainer.setBackgroundResource(R.drawable.device_off_background);
                                view.setBackgroundResource(R.drawable.item_selected_background);
                            } else {
                                item.selectTag.setVisibility(View.GONE);
                                item.modeName.setTextColor(UNSELECT_TEXTCOLOR);
                                item.modeContainer.setBackgroundColor(Color.TRANSPARENT);
                                view.setBackgroundColor(Color.TRANSPARENT);
                            }
                        }
                    };
                }
            });
        }
        if (prevHolder != null) {
            prevHolder.setSelected(false);
            prevHolder = null;
        }
        types.setAdapter(adapter);
        adapter.setData(deviceMenus);
        adapter.notifyDataSetChanged();
        if (deviceMenus != null && deviceMenus.size() > 0) {
            types.postDelayed(new Runnable() {
                @Override
                public void run() {
                    types.performItemClick(types.getChildAt(0), 0, types.getItemIdAtPosition(0));
                }
            }, 50);
        }
    }

    @Override
    public void onReady() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void release() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
