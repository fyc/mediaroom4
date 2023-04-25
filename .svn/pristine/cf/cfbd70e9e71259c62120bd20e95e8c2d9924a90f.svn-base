package com.sunmnet.mediaroom.tabsp.desktop;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.sunmnet.mediaroom.common.BaseActivity;
import com.sunmnet.mediaroom.common.BaseFragment;
import com.sunmnet.mediaroom.common.interfaces.IDataBindings;
import com.sunmnet.mediaroom.tabsp.desktop.bean.NetworkBean;
import com.sunmnet.mediaroom.tabsp.desktop.model.SettingPresenter;
import com.sunmnet.mediaroom.tabsp.desktop.view.EthernetFragment;
import com.sunmnet.mediaroom.tabsp.desktop.view.VersionFragment;
import com.sunmnet.mediaroom.tabsp.desktop.view.WlanFragment;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity  {
    @BindView(R.id.back)
    View back;
    @BindView(R.id.id_ethernet)
    TextView prevTagButton;
    SettingPresenter presenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_main);
        ButterKnife.bind(this);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (ethernet == null) {
            ethernet = new EthernetFragment();
            transaction.add(R.id.settings, ethernet);
        }
        if (wifi == null) {
            wifi = new WlanFragment();
            transaction.add(R.id.settings, wifi);
        }
        if (version==null){
            version=new VersionFragment();
            transaction.add(R.id.settings,version);
        }
        transaction.hide(version).hide(wifi).show(ethernet);
        prevFrag=ethernet;
        transaction.commit();
    }

    BaseFragment ethernet, wifi,version;
    BaseFragment prevFrag;
    private void enableFrargment(BaseFragment fragment){
        FragmentTransaction transaction=getFragmentManager().beginTransaction();
        /*if (fragment==wifi){
            transaction.hide(ethernet).show(fragment);
        }
        if (fragment==ethernet){
            transaction.hide(wifi).show(fragment);
        }*/
        transaction.hide(prevFrag).show(fragment);
        transaction.commit();
        prevFrag=fragment;
    }
    private void onButtonSelected(TextView selected,TextView unselected){
        selected.setTextColor(Color.WHITE);
        selected.setBackgroundColor(Color.BLACK);
        prevTagButton=selected;
        unselected.setTextColor(Color.BLACK);
        unselected.setBackgroundColor(Color.TRANSPARENT);
    }
    @OnClick({R.id.back,R.id.id_ethernet,R.id.id_wireless,R.id.id_version_fragment})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.id_ethernet:
                onButtonSelected((TextView) view,prevTagButton);
                enableFrargment(ethernet);
                break;
            case R.id.id_wireless:
                onButtonSelected((TextView) view,prevTagButton);
                enableFrargment(wifi);
                break;
            case R.id.id_version_fragment:
                onButtonSelected((TextView) view,prevTagButton);
                enableFrargment(version);
                break;

        }
    }
}
