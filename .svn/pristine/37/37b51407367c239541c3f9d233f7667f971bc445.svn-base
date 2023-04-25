package com.sunmnet.mediaroom.tabsp.ui.dispatch.fragmentDispatcher;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.TabspApplication;
import com.sunmnet.mediaroom.tabsp.bean.MenuEntity;
import com.sunmnet.mediaroom.tabsp.bean.MenuOperator;
import com.sunmnet.mediaroom.tabsp.common.Constants;
import com.sunmnet.mediaroom.tabsp.controll.service.Dispatcher;
import com.sunmnet.mediaroom.tabsp.controll.service.UIDispatcher;
import com.sunmnet.mediaroom.tabsp.databinding.MenuAdapterBinding;
import com.sunmnet.mediaroom.tabsp.databinding.MenuBinding;
import com.sunmnet.mediaroom.tabsp.ui.DispatchFragment;
import com.sunmnet.mediaroom.tabsp.ui.adapter.AbstractHolder;
import com.sunmnet.mediaroom.tabsp.ui.adapter.MenuHolderAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

/**
 * 菜单
 */
@Route(path = Constants.ROUTERPATH_CONTROLLER_MENU)
public class MenuDispatcher extends AbstractFragmentDispatcher {
    MenuAdapterBinding menuAdapterBinding;
    MenuHolderAdapter adapter;
    AbstractHolder<MenuBinding, MenuOperator> prevSelected;
    FragmentActivity container;
    MenuEntity prevSelectEntity;
    @BindView(R.id.menu)
    GridView menuGrid;

    @OnItemClick(R.id.menu)
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        AbstractHolder<MenuBinding, MenuOperator> holder = (AbstractHolder) view.getTag();
        if (holder != prevSelected) {
            if (prevSelected != null) {
                prevSelected.setSelected(false);
            }
            holder.setSelected(true);
            onMenuChanged(holder.getProperty().getEntity());
            prevSelected = holder;
        }
    }

    @Override
    public void release() {
        RunningLog.run(this+"MenuDispatcher.release");
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onReady() {
        RunningLog.run(this+"MenuDispatcher.onReady");
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMenuChanged(MenuEntity menuEntity) {
        FragmentTransaction transaction = this.container.getSupportFragmentManager().beginTransaction();
        Fragment visibleFragment = null;
        if (this.prevSelectEntity != null) {
            visibleFragment = this.container.getSupportFragmentManager().findFragmentByTag(prevSelectEntity.getLink());
            if (visibleFragment != null)
                transaction.hide(visibleFragment);
        }
        Fragment exitFragment = this.container.getSupportFragmentManager().findFragmentByTag(menuEntity.getLink());
        if (exitFragment == null) {
            exitFragment = new DispatchFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Dispatcher.PAGE_KEY, menuEntity.getLink());
            exitFragment.setArguments(bundle);
            transaction.add(R.id.content_fragment, exitFragment, menuEntity.getLink());
        }
        UIDispatcher dispatcher = (UIDispatcher) ARouter.getInstance().build(menuEntity.getLink()).navigation();
        if (dispatcher != null){
            dispatcher.setDataMap(menuEntity.getData());
            dispatcher.dispatch(menuEntity.getType(), menuEntity.getParent());
        }
        transaction.show(exitFragment);
        transaction.commitAllowingStateLoss();
        this.prevSelectEntity = menuEntity;
    }

    @Override
    public int getLayout() {
        return R.layout.tabsp_menu_layout;
    }

    @Override
    public <E> void dispatch(View view, E e) {
        super.dispatch(view, e);
        if (e instanceof FragmentActivity) {
            this.container = (FragmentActivity) e;
        }
        TabspApplication.getInstance().getDispatcher().apply(Constants.ROUTERPATH_CONTROLLER_MENU, view);
        ButterKnife.bind(this, view);
        List<MenuOperator> operators = TabspApplication.getInstance().getDispatcher().getMenus();
        menuAdapterBinding = DataBindingUtil.bind(view);
        adapter=new MenuHolderAdapter(R.layout.tabsp_menu_item_layout);
        for (MenuOperator operator : operators) {
            RunningLog.debug("左侧菜单栏:" + operator.getEntity().getMenuName());
        }
        adapter.setData(operators);
        menuAdapterBinding.setAdapter(adapter);
        view.postDelayed(() ->
                menuGrid.performItemClick(menuGrid.getChildAt(0), 0, menuGrid.getItemIdAtPosition(0)),
                50);
    }
}
