package com.sunmnet.mediaroom.brand.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.sunmnet.mediaroom.brand.R;
import com.sunmnet.mediaroom.brand.bean.ProgramLayoutBean;
import com.sunmnet.mediaroom.brand.interfaces.control.IBaseControl;
import com.sunmnet.mediaroom.brand.interfaces.control.IExamControl;
import com.sunmnet.mediaroom.brand.bean.control.base.ControlProperty;
import com.sunmnet.mediaroom.brand.bean.event.RefreshControlEvent;
import com.sunmnet.mediaroom.brand.utils.ColorUtil;
import com.sunmnet.mediaroom.brand.utils.ControlBuilder;
import com.sunmnet.mediaroom.common.tools.JacksonUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Map;


public class ProgramFragment extends Fragment {

    protected boolean isInit = false;

    public static ProgramFragment newInstance(ProgramLayoutBean programLayoutBean, String resourcePath, String examId) {
        Bundle args = new Bundle();
        ProgramFragment fragment = new ProgramFragment();
        args.putSerializable("bean", programLayoutBean);
        args.putString("resPath", resourcePath);
        args.putString("examId", examId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_program, null);
        isInit = true;
        initControl(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroyView() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroyView();
        isInit = false;
    }

    private void initControl(final View view) {
        Bundle args = getArguments();
        if (args != null) {
            ProgramLayoutBean datas = (ProgramLayoutBean) args.getSerializable("bean");
            if (datas == null) {
                return;
            }
            String resourcePath = args.getString("resPath");
            String examId = args.getString("examId");
            List<ControlProperty> propertyList = JacksonUtil.jsonStrToList(datas.getControls(), ControlProperty.class);
            List<Map<String, Object>> list = datas.getControlList();
            if (list == null) {
                return;
            }
            if (propertyList == null) {
                return;
            }
            ((ViewGroup) view).removeAllViews();
//            for (Map<String, Object> data : list) {
            for (ControlProperty property : propertyList) {
                if (property == null)
                    continue;
                IBaseControl control = ControlBuilder.createControl(getActivity(), property.getType());
                if (control == null)
                    continue;
//                ControlProperty controlProperty = ControlBuilder.mapToProperty(data, control.getClass());
                ((View) control).setLayoutParams(new FrameLayout.LayoutParams(0, 0));
                control.setResourcePath(resourcePath);
//                control.setControlDataMap(data);
                control.setProperty(property);
                if (control instanceof IExamControl) {
                    ((IExamControl) control).setExamTimetableId(examId);
                }
                ((ViewGroup) view).addView((View) control);
                control.refreshControlData();
                control.onControlHide();
            }

            String bg = datas.getBackground();
            if (!TextUtils.isEmpty(bg)) {
                if (bg.startsWith("#") || bg.toLowerCase().startsWith("rgb")) {
                    view.setBackgroundColor(ColorUtil.parseColor(bg));
                } else {
                    String url;
                    if (bg.startsWith("http://") || bg.startsWith("https://")) {
                        url = bg;
                    } else {
                        url = resourcePath + bg;
                    }
                    Glide.with(view.getContext()).load(url).diskCacheStrategy(DiskCacheStrategy.NONE).into(new CustomTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            view.setBackgroundDrawable(resource);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }
                    });
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        checkControlState();
    }

    @Override
    public void onPause() {
        super.onPause();
        checkControlState();
    }

    private void checkControlState() {
        if (!isInit)
            return;
        if (getUserVisibleHint()) {
            if (getView() != null) {
                int count = ((ViewGroup) getView()).getChildCount();
                for (int i = 0; i < count; i++) {
                    View child = ((ViewGroup) getView()).getChildAt(i);
                    if (child != null && child instanceof IBaseControl) {
                        ((IBaseControl) child).onControlShow();
                    }
                }
            }
        } else {
            if (getView() != null) {
                int count = ((ViewGroup) getView()).getChildCount();
                for (int i = 0; i < count; i++) {
                    View child = ((ViewGroup) getView()).getChildAt(i);
                    if (child != null && child instanceof IBaseControl) {
                        ((IBaseControl) child).onControlHide();
                    }
                }
            }
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        checkControlState();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshControl(RefreshControlEvent event) {
        if (getView() != null) {
            int count = ((ViewGroup) getView()).getChildCount();
            for (int i = 0; i < count; i++) {
                View child = ((ViewGroup) getView()).getChildAt(i);
                if (child != null && child instanceof IBaseControl && event.contains((IBaseControl) child)) {
                    ((IBaseControl) child).refreshControlData();
                }
            }
        }
    }

    public void refreshControlData() {
        if (getView() != null) {
            int count = ((ViewGroup) getView()).getChildCount();
            for (int i = 0; i < count; i++) {
                View child = ((ViewGroup) getView()).getChildAt(i);
                if (child != null && child instanceof IBaseControl) {
                    ((IBaseControl) child).refreshControlData();
                }
            }
        }
    }
}
