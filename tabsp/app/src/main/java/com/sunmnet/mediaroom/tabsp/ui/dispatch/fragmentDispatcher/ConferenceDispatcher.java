package com.sunmnet.mediaroom.tabsp.ui.dispatch.fragmentDispatcher;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.common.Constants;

@Route(path = Constants.ROUTERPATH_CONTROLL_CONFERENCE)
public class ConferenceDispatcher extends AbstractFragmentDispatcher {
    @Override
    public int getLayout() {
        return R.layout.tabsp_version2_confrence_layout;
    }
}
