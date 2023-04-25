package com.sunmnet.mediaroom.tabsp.ui.dispatch.fragmentDispatcher;

import android.view.View;

import android.databinding.DataBindingUtil;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sunmnet.mediaroom.common.BaseActivity;
import com.sunmnet.mediaroom.common.tools.ShellUtils;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.common.Constants;
import com.sunmnet.mediaroom.tabsp.databinding.QuitAppDataBinding;
import com.sunmnet.mediaroom.tabsp.utils.PackageTool;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 退出APP界面处理
 * */

@Route(path = Constants.ROUTERPATH_SETTING_QUIT_APP)
public class QuitAppDispatcher extends AbstractFragmentDispatcher {
    BaseActivity baseActivity;
    QuitAppDataBinding binding;

    @OnClick({R.id.quit_app})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.quit_app:
                PackageTool.getInstance(baseActivity).startExistsApp(PackageTool.LAUNCHER_PACKAGE_NAME);
                ShellUtils.execCommand("am force-stop com.sunmnet.mediaroom.tabsp");
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
    }

    @Override
    public int getLayout() {
        return R.layout.tabsp_setting_quit_app_layout;
    }
}
