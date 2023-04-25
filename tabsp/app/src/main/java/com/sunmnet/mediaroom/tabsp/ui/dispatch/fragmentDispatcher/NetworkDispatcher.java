package com.sunmnet.mediaroom.tabsp.ui.dispatch.fragmentDispatcher;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sunmnet.mediaroom.common.BaseActivity;
import com.sunmnet.mediaroom.common.tools.AndroidNetworkUtil;
import com.sunmnet.mediaroom.common.tools.EthernetUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ThreadUtils;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.TabspApplication;
import com.sunmnet.mediaroom.tabsp.TabspNetworkUtil;
import com.sunmnet.mediaroom.tabsp.bean.NetworkBean;
import com.sunmnet.mediaroom.tabsp.bean.NetworkInterface;
import com.sunmnet.mediaroom.tabsp.common.Constants;
import com.sunmnet.mediaroom.tabsp.databinding.EthernetDataBinding;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 网络设置界面处理
 * */
@Route(path = Constants.ROUTERPATH_SETTING_NETWORK)
public class NetworkDispatcher extends AbstractFragmentDispatcher {
    BaseActivity baseActivity;
    EthernetDataBinding binding;

    private void toast(String message) {
        if (baseActivity != null) {
            RunningLog.run(message);
            baseActivity.toastMessage(message);
        }
    }

    @OnClick({R.id.enable, R.id.disable})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.enable:
                ThreadUtils.execute(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void run() {
                        NetworkBean bean = binding.getNetwork();
                        if (check(bean)) {
                            boolean set = EthernetUtil.setEthernetStaticIp(TabspApplication.getInstance(),
                                    bean.getLocalIP(),
                                    bean.getNetmask(),
                                    bean.getGateway(),
                                    bean.getDns1());
                            if (set) {
                                toast("网络设置完成!!");
                                EventBus.getDefault().post(bean);
                            } else {
                                toast("网络设置失败！！");
                            }
                        }
                    }

                    private boolean check(NetworkInterface bean) {
                        if (!AndroidNetworkUtil.isLegalIP(bean.getLocalIP())) {
                            toast("IP设置有误！！");
                            return false;
                        }
                        if (!AndroidNetworkUtil.isLegalIP(bean.getGateway())) {
                            toast("网关设置有误！！");
                            return false;
                        }
                        if (!AndroidNetworkUtil.isLegalIP(bean.getNetmask())) {
                            toast("广播地址设置有误！！");
                            return false;
                        }
                        if (!AndroidNetworkUtil.isLegalIP(bean.getDns1())) {
                            toast("dns设置有误！！");
                            return false;
                        }
                        return true;
                    }
                });
                break;
            case R.id.disable:
                ThreadUtils.execute(new Runnable() {
                    @Override
                    public void run() {
                        AndroidNetworkUtil.disableEthernet();
                    }
                });
                break;
        }
    }

    @Override
    public <E> void dispatch(View view, E e) {
        if (e instanceof BaseActivity) {
            this.baseActivity = (BaseActivity) e;
        }
        binding = DataBindingUtil.bind(view);
        ButterKnife.bind(this, view);
        ThreadUtils.execute(new Runnable() {
            @Override
            public void run() {
                NetworkBean networkBean = new NetworkBean();
                TabspNetworkUtil.loadNetwork(AndroidNetworkUtil.NetworkType.ETHERNET, networkBean);
                binding.setNetwork(networkBean);
            }
        });

    }


    @Override
    public int getLayout() {
        return R.layout.tabsp_setting_ethernet_layout;
    }
}
