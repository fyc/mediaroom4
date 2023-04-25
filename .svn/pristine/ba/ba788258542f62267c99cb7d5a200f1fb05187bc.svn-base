package com.sunmnet.mediaroom.tabsp.controll.version3;

import android.databinding.DataBindingUtil;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sunmnet.mediaroom.common.BaseActivity;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.TabspApplication;
import com.sunmnet.mediaroom.tabsp.bean.MenuOperator;
import com.sunmnet.mediaroom.tabsp.common.Constants;
import com.sunmnet.mediaroom.tabsp.databinding.MenuBinding;
import com.sunmnet.mediaroom.tabsp.ui.adapter.AbstractHolder;
import com.sunmnet.mediaroom.tabsp.ui.adapter.MenuHolderAdapter;
import com.sunmnet.mediaroom.tabsp.ui.dispatch.fragmentDispatcher.AbstractFragmentDispatcher;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

@Route(path = Constants.ROUTERPATH_CONTROLL_V3_MENU)
public class V3DetailMenuDispatcher extends AbstractFragmentDispatcher {
    BaseActivity activity;
    @BindView(R.id.menu)
    ListView menuView;

    AbstractHolder<MenuBinding, MenuOperator> prevSelected;

    @Override
    public void dispatch(View view) {
        ButterKnife.bind(this, view);
        List<MenuOperator> operators = TabspApplication.getInstance().getDispatcher().getMenus();
        MenuHolderAdapter adapter = new MenuHolderAdapter(R.layout.tabsp_menu_item_layout);
        adapter.setData(operators);
        menuView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        menuView.postDelayed(new Runnable() {
            @Override
            public void run() {
                menuView.performItemClick(menuView.getChildAt(0), 0, menuView.getItemIdAtPosition(0));

            }
        }, 100);
    }

    @OnItemClick(R.id.menu)
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        AbstractHolder<MenuBinding, MenuOperator> holder = (AbstractHolder) view.getTag();
        if (holder != prevSelected) {
            if (prevSelected != null) {
                prevSelected.setSelected(false);
            }
            holder.setSelected(true);
            prevSelected = holder;
            EventBus.getDefault().postSticky(holder.getProperty().getEntity());
        }
    }

    @Override
    public int getLayout() {
        return R.layout.tabsp_version3_menu_layout;
    }

    @Override
    public <E> void dispatch(View view, E e) {
        if (e instanceof BaseActivity) {
            this.activity = (BaseActivity) e;
        }
        this.dispatch(view);
    }
}
