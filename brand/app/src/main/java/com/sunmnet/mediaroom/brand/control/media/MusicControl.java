package com.sunmnet.mediaroom.brand.control.media;


import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.sunmnet.mediaroom.brand.R;
import com.sunmnet.mediaroom.brand.control.base.BaseVideoControl;
import com.sunmnet.mediaroom.brand.bean.control.media.MusicControlAttr;
import com.sunmnet.mediaroom.brand.bean.control.media.MusicControlProperty;

import java.util.ArrayList;
import java.util.List;

public class MusicControl extends BaseVideoControl<MusicControlProperty> {


    public MusicControl(Context context) {
        super(context);
    }

    public MusicControl(Context context, MarginLayoutParams layoutParams) {
        super(context, layoutParams);
    }

    public MusicControl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

//    @Override
//    public void setControlDataMap(Map<String, Object> dataMap) {
//        super.setControlDataMap(dataMap);
//        List<Map<String, Object>> list = TypeUtil.castToMapList_SO(attrDataMap.get("files"));
//        videoList = new ArrayList<>();
//        for (Map<String, Object> map : list) {
//            //音乐文件在节目资源文件夹的路径
//            String suffix = TypeUtil.objToStr(map.get("filePath"));
//            //获取节目资源解压路径，拼出完整的文件路径
//            videoList.add(getResourceFilePath(suffix));
//        }
//    }

    @Override
    public void setProperty(MusicControlProperty property) {
        super.setProperty(property);
        List<MusicControlAttr.FilesBean> list = property.getAttr().getFiles();
        videoList = new ArrayList<>();
        for (MusicControlAttr.FilesBean filesBean : list) {
            //音乐文件在节目资源文件夹的路径
            String suffix = filesBean.getFilePath();
            //获取节目资源解压路径，拼出完整的文件路径
            videoList.add(getResourceFilePath(suffix));
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        //音乐文件无封面图时显示的图片
        ImageView imageView = new ImageView(getContext());
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setImageResource(R.drawable.icon_music_control);
//        imageView.setAlpha(50);
        addView(imageView, 0);
    }
}
