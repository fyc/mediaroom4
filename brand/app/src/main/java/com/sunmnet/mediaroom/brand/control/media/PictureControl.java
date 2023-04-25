package com.sunmnet.mediaroom.brand.control.media;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.sunmnet.mediaroom.common.tools.TypeUtil;
import com.sunmnet.mediaroom.brand.R;
import com.sunmnet.mediaroom.brand.control.base.BaseFrameControl;
import com.sunmnet.mediaroom.brand.control.base.HandlerTimerTask;
import com.sunmnet.mediaroom.brand.bean.control.media.PictureControlAttr;
import com.sunmnet.mediaroom.brand.bean.control.media.PictureControlProperty;

import java.util.List;
import java.util.Timer;

/**
 * 图片控件
 */
public class PictureControl extends BaseFrameControl<PictureControlProperty> {

    protected ImageSwitcher imageSwitcher;

    private int carousel = 10;//轮播时间 单位：秒

//    private List<Map<String, Object>> files;
    private List<PictureControlAttr.FilesBean> files;

    private int position = 0;//当前播放

    private Timer timer;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            position++;
            refreshControlData();
        }
    };

    public PictureControl(Context context) {
        super(context);
    }

    public PictureControl(Context context, MarginLayoutParams layoutParams) {
        super(context, layoutParams);
    }

    public PictureControl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public List<PictureControlAttr.FilesBean> getFiles() {
        return files;
    }

    public void setFiles(List<PictureControlAttr.FilesBean> files) {
        this.files = files;
    }

    @Override
    protected void init() {
        imageSwitcher = new ImageSwitcher(getContext());
        imageSwitcher.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(getContext());
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                return imageView;
            }
        });

        imageSwitcher.setInAnimation(getContext(), R.anim.slide_in_right);
        imageSwitcher.setOutAnimation(getContext(), R.anim.slide_out_left);
        removeAllViews();
        addView(imageSwitcher);
    }

    @Override
    public void refreshControlData() {
        if (files == null || files.size() == 0)
            return;
        int temp = position % files.size();
        if (position != temp && files.size() < 2) {
            position = temp;
            return;
        }
        position = temp;
        String path = files.get(position).getFilePath();
        //节目解压目录
        path = getResourceFilePath(path);
        try {
            Glide.with(getContext()).load(path).diskCacheStrategy(DiskCacheStrategy.NONE).into(new CustomTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    imageSwitcher.setImageDrawable(resource);
                }

                @Override
                public void onLoadCleared(@Nullable Drawable placeholder) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Override
//    public void setControlDataMap(Map<String, Object> dataMap) {
//        super.setControlDataMap(dataMap);
//        files = TypeUtil.castToMapList_SO(attrDataMap.get("files"));
//        carousel = TypeUtil.objToIntDef(attrDataMap.get("carousel"), 10);
//    }

    @Override
    public void setProperty(PictureControlProperty property) {
        super.setProperty(property);
//        List<PictureControlAttr.FilesBean> filesBeanList = property.getAttr().getFiles();
//        files = new ArrayList<>();
//        for (PictureControlAttr.FilesBean filesBean : filesBeanList) {
//            Map<String, Object> m = new HashMap<>();
//            m.put("filePath", filesBean.getFilePath());
//            files.add(m);
//        }
        files = property.getAttr().getFiles();
        carousel = TypeUtil.objToIntDef(property.getAttr().getCarousel(), 10);
        if (carousel <= 0) {
            carousel = 10;
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        onControlShow();
    }

    @Override
    public void onControlShow() {
        if (timer != null)
            onControlHide();
        try {
            timer = new Timer();
            timer.schedule(new HandlerTimerTask(runnable), carousel * 1000, carousel * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        onControlHide();
    }

    @Override
    public void onControlHide() {
        if (timer == null)
            return;
        try {
            timer.cancel();
            timer = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
