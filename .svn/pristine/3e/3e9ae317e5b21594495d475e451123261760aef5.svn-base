package com.sunmnet.mediaroom.brand.control.media;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.sunmnet.mediaroom.brand.common.base.DeviceApp;
import com.sunmnet.mediaroom.common.config.CourseConfig;
import com.sunmnet.mediaroom.common.bean.course.CourseSchedule;
import com.sunmnet.mediaroom.common.tools.CourseHelper;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.TypeUtil;
import com.sunmnet.mediaroom.brand.R;
import com.sunmnet.mediaroom.brand.api.ApiManager;
import com.sunmnet.mediaroom.brand.bean.response.StringResponse;
import com.sunmnet.mediaroom.brand.control.base.BaseFrameControl;
import com.sunmnet.mediaroom.brand.bean.control.media.TeacherPortraitControlProperty;
import com.sunmnet.mediaroom.brand.rx.SingleTaskObserver;

import java.util.Date;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * 老师头像
 */
public class TeacherPortraitControl extends BaseFrameControl<TeacherPortraitControlProperty> {

    protected ImageView imageView;
    protected int defaultImgResId;

    protected String classNo;//匹配课节
    protected String week;//匹配星期
    protected String portrait;//头像。默认default，本人self

    public TeacherPortraitControl(Context context) {
        super(context);
    }

    public TeacherPortraitControl(Context context, MarginLayoutParams layoutParams) {
        super(context, layoutParams);
    }

    public TeacherPortraitControl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init() {
        imageView = new ImageView(getContext());
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        if (defaultImgResId == 0) {
            defaultImgResId = R.drawable.icon_default_portrait;
        }
        if (classNo == null) {
            classNo = "0";
        }
        if (week == null) {
            week = "0";
        }
        if (portrait == null) {
            portrait = "default";
        }
        imageView.setImageResource(defaultImgResId);
        removeAllViews();
        addView(imageView);
    }

    @Override
    public void refreshControlData() {
        //根据匹配星期和课节从课程表获取数据
        String id = "";
        if (CourseHelper.getDefault().isLoaded()) {
            CourseSchedule courseSchedule = CourseHelper.getDefault().getCourseSchedule(new Date(), TypeUtil.objToInt(week), classNo, CourseConfig.PRE_START_TIME);
            if (courseSchedule != null) {
                id = courseSchedule.getAccountId();
            }
        }
        if (portrait.equals("default") || TextUtils.isEmpty(id) || !portrait.equals("self")) {
            //设置默认头像
            imageView.setImageResource(defaultImgResId);
            return;
        }
        if (!DeviceApp.getApp().isRegistered()) {
            return;
        }
        RunningLog.run("获取老师头像图片");
        ApiManager.getSysApi().getHeadPortrait(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleTaskObserver<StringResponse>() {
                    @Override
                    public void onNext(StringResponse stringResponse) {
                        if (stringResponse == null || !stringResponse.isSuccess() || TextUtils.isEmpty(stringResponse.getObj()))
                            return;
                        String str = stringResponse.getObj();
                        Glide.with(getContext()).load(str).into(new ImageViewTarget<Drawable>(imageView) {
                            @Override
                            protected void setResource(@Nullable Drawable resource) {
                                imageView.setImageDrawable(resource);
                            }

                            @Override
                            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                                imageView.setImageResource(defaultImgResId);
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable e) {
                        RunningLog.error("获取老师头像图片失败：" + e.getMessage());
                    }
                });
    }

//    @Override
//    public void setControlDataMap(Map<String, Object> dataMap) {
//        super.setControlDataMap(dataMap);
//        classNo = TypeUtil.objToStr(attrDataMap.get("classNo"));
//        week = TypeUtil.objToStr(attrDataMap.get("week"));
//        portrait = TypeUtil.objToStrDef(attrDataMap.get("portrait"), "default");
//    }

    @Override
    public void setProperty(TeacherPortraitControlProperty property) {
        super.setProperty(property);
        classNo = TypeUtil.objToStr(property.getAttr().getClassNo());
        week = TypeUtil.objToStr(property.getAttr().getWeek());
        portrait = TypeUtil.objToStrDef(property.getAttr().getPortrait(), "default");
    }
}
