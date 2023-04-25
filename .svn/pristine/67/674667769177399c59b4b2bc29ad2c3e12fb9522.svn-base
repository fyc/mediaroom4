package com.sunmnet.mediaroom.tabsp.ui.dispatch;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sunmnet.mediaroom.common.BaseActivity;
import com.sunmnet.mediaroom.common.tools.CommonUtil;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.TabspApplication;
import com.sunmnet.mediaroom.tabsp.bean.MenuEntity;
import com.sunmnet.mediaroom.tabsp.bean.TabspConfig;
import com.sunmnet.mediaroom.tabsp.common.Constants;
import com.sunmnet.mediaroom.tabsp.controll.AbstractActivityUiDispatcher;
import com.sunmnet.mediaroom.tabsp.controll.service.Dispatcher;
import com.sunmnet.mediaroom.tabsp.databinding.SettingAdapterMatcher;
import com.sunmnet.mediaroom.tabsp.databinding.SettingItemMenu;
import com.sunmnet.mediaroom.tabsp.ui.DispatchFragment;
import com.sunmnet.mediaroom.tabsp.ui.adapter.AbstractHolder;
import com.sunmnet.mediaroom.tabsp.ui.adapter.HolderAdapter;
import com.sunmnet.mediaroom.tabsp.ui.adapter.IHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnItemClick;

@Route(path = Constants.ROUTERPATH_SETTING)
public class SettingDispatcher extends AbstractActivityUiDispatcher implements AdapterView.OnItemClickListener {
    @Override
    public int getLayout() {
        return R.layout.tabsp_setting_layout;
    }

    @Override
    public void dispatch(BaseActivity baseActivity) {
        super.dispatch(baseActivity);
        SettingAdapterMatcher binding = DataBindingUtil.setContentView(baseActivity, getLayout());
        HolderAdapter<SettingAdapterMatcher, MenuEntity> adapter = new HolderAdapter<>(R.layout.tabsp_settingmenu_item, new IHolder.HolderFactory() {
            @Override
            public IHolder newHolder() {
                return new AbstractHolder<SettingItemMenu, MenuEntity>() {
                    SettingItemMenu t;

                    @Override
                    public void setProperty(SettingItemMenu settingItemMenu, MenuEntity menuEntity) {
                        this.property = menuEntity;
                        this.t = settingItemMenu;
                        settingItemMenu.setMenu(menuEntity);
                        setSelected(false);
                    }

                    @Override
                    public void setSelected(boolean selected) {
                        t.settingTitle.setBackgroundResource(selected ? R.drawable.btn_onclick_effect_blue : R.drawable.btn_onclick_effect_grey);
                    }
                };
            }
        });
        binding.setAdapter(adapter);
        adapter.setData(getMenu());
        binding.settingMenu.setOnItemClickListener(this);
        TabspConfig config = TabspApplication.getInstance().getConfig();
        CommonUtil.loadBackgroundImage(activity, config.getBaseLoginBackground(),
                R.drawable.activity_login_enter_background, binding.settingContainer);
        binding.settingContainer.postDelayed(() -> binding.settingMenu.performItemClick(binding.settingMenu.getChildAt(0), 0, binding.settingMenu.getItemIdAtPosition(0)), 50);
        binding.settingContainer.postDelayed(() -> {
            View view = binding.settingContainer.findViewById(R.id.back_btn);
            if (view != null)
                view.setOnClickListener(v -> {
                    if (baseActivity != null) baseActivity.finish();
                });
        }, 3000);
    }


    private List<MenuEntity> getMenu() {
        List<MenuEntity> entities = new ArrayList<>();
        entities.add(new MenuEntity("网络配置", Constants.ROUTERPATH_SETTING_NETWORK, null, null));
        entities.add(new MenuEntity("退出系统", Constants.ROUTERPATH_SETTING_QUIT_APP, null, null));
        return entities;
    }

    AbstractHolder<SettingItemMenu, MenuEntity> prev;

    @OnItemClick(R.id.setting_menu)
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        AbstractHolder<SettingItemMenu, MenuEntity> holder = (AbstractHolder<SettingItemMenu, MenuEntity>) view.getTag();
        if (holder != prev) {
            if (prev != null) prev.setSelected(false);
            holder.setSelected(true);
            onMenuSelected(holder.getProperty());
            prev = holder;
        }
    }

    private void onMenuSelected(MenuEntity entity) {
        FragmentTransaction transaction = this.activity.getSupportFragmentManager().beginTransaction();
        DispatchFragment fragment = new DispatchFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Dispatcher.PAGE_KEY, entity.getLink());
        fragment.setArguments(bundle);
        transaction.replace(R.id.setting_content, fragment);
        transaction.commit();
    }
}
