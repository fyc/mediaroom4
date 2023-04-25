package com.sunmnet.mediaroom.tabsp.customization;

import android.content.Context;
import android.net.Uri;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.PathReplaceService;
import com.sunmnet.mediaroom.tabsp.common.Constants;

@Route(path = CustomPath.PATH_REPLACE)
public class PathReplaceServiceImpl implements PathReplaceService {
    @Override
    public String forString(String path) {
        if (path.equals(Constants.ROUTERPATH_CONTROLL_DEVICE_CUSTOM_MENU)) {
            return CustomPath.CONTROLLER_DEVICE_CUSTOM_MENU_PROXY;
        }
        return path;
    }

    @Override
    public Uri forUri(Uri uri) {
        return uri;
    }

    @Override
    public void init(Context context) {

    }
}
