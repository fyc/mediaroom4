package com.sunmnet.mediaroom.brand.control.media;


import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.sunmnet.mediaroom.brand.control.base.BaseVideoControl;
import com.sunmnet.mediaroom.brand.bean.control.media.VideoControlAttr;
import com.sunmnet.mediaroom.brand.bean.control.media.VideoControlProperty;

import java.util.ArrayList;

public class VideoControl extends BaseVideoControl<VideoControlProperty> {


    public VideoControl(Context context) {
        super(context);
    }

    public VideoControl(Context context, MarginLayoutParams layoutParams) {
        super(context, layoutParams);
    }

    public VideoControl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init() {
        super.init();
        setBackgroundColor(0xff000000);
    }

//    @Override
//    public void setControlDataMap(Map<String, Object> dataMap) {
//        super.setControlDataMap(dataMap);
//        List<Map<String, Object>> list = TypeUtil.castToMapList_SO(attrDataMap.get("files"));
//        videoList = new ArrayList<>();
//        for (Map<String, Object> map : list) {
//            String path = TypeUtil.objToStr(map.get("filePath"));
//            //获取节目资源解压路径，拼出完整的文件路径
////            if (path.endsWith("raw/sample.wmv")) {
////                //String str = "android.resource://"+ getContext().getPackageName()+"/"+ R.raw.sample;
////                //map.put("path", FileResourceUtil.getAppRootFolderPath()+"/"+"sample.wmv");
////                path = FileResourceUtil.getAppRootFolderPath()+"/"+"sample.wmv";
////            }
//            videoList.add(getResourceFilePath(path));
//        }
//    }

    @Override
    public void setProperty(VideoControlProperty property) {
        super.setProperty(property);
        videoList = new ArrayList<>();
        for (VideoControlAttr.FilesBean filesBean : property.getAttr().getFiles()) {
            String path = filesBean.getFilePath();
            //获取节目资源解压路径，拼出完整的文件路径
            videoList.add(getResourceFilePath(path));
        }
    }
}
