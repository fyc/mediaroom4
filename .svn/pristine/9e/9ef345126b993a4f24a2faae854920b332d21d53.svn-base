package com.sunmnet.mediaroom.tabsp.controll.version1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.sunmnet.mediaroom.common.BaseActivity;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.device.bean.DeviceType;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.controll.service.UIDispatcher;
import com.sunmnet.mediaroom.tabsp.customization.CustomPath;
import com.sunmnet.mediaroom.tabsp.ui.dispatch.fragmentDispatcher.AbstractFragmentDispatcher;

/**
 * 自定义设备界面
 */
@Route(path = CustomPath.CONTROLLER_DEVICE_CUSTOM_MENU_PROXY)
public class CustomDeviceMenuDispatchProxy extends AbstractFragmentDispatcher {
    private View contentView;
    private UIDispatcher audioMatrixDispatch;
    private UIDispatcher customDeviceMenuDispatch;
    // 窗帘自定义设备
    private UIDispatcher curtainDeviceMenuDispatch;
    private BaseActivity activity;
    private String type;
    private String prevType;
    private DeviceType deviceType;
    private String parentConnect;

    @Override
    public int getLayout() {
        return R.layout.tabsp_custom_device_proxy_layout;
    }

    @Override
    public <E> void dispatch(View view, E e) {
        super.dispatch(view, e);
        if (e instanceof BaseActivity) {
            this.activity = (BaseActivity) e;
        }
        if (this.contentView != view && view != null) {
            this.dispatch(this.type, view);
        }
    }

    @Override
    public void dispatch(String type, View view) {
        if (view == null)
            return;
        if (contentView != null && contentView == view) {
            ((ViewGroup) contentView).removeAllViews();
        }
        contentView = view;
        if (isAudioMatrix(deviceType)) {
            View inflateView = LayoutInflater.from(view.getContext()).inflate(audioMatrixDispatch.getLayout(), (ViewGroup) contentView, false);
            ((ViewGroup) contentView).addView(inflateView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            audioMatrixDispatch.dispatch(inflateView, activity);
            audioMatrixDispatch.dispatch(type, parentConnect);
        } else if (isCurtain(deviceType)) {
            // 窗帘自定义设备
            View inflateView = LayoutInflater.from(view.getContext()).inflate(curtainDeviceMenuDispatch.getLayout(), (ViewGroup) contentView, false);
            ((ViewGroup) contentView).addView(inflateView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            curtainDeviceMenuDispatch.dispatch(inflateView, activity);
            curtainDeviceMenuDispatch.dispatch(type, parentConnect);
        } else {
            View inflateView = LayoutInflater.from(view.getContext()).inflate(customDeviceMenuDispatch.getLayout(), (ViewGroup) contentView, false);
            ((ViewGroup) contentView).addView(inflateView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            customDeviceMenuDispatch.dispatch(inflateView);
            customDeviceMenuDispatch.dispatch(type, parentConnect);
        }
    }

    @Override
    public void dispatch(String type, String parent) {
        RunningLog.run(this.toString() + " dispatch(String type, String parent)");
        if (audioMatrixDispatch == null) {
            audioMatrixDispatch = (UIDispatcher) ARouter.getInstance().build(CustomPath.CONTROLLER_DEVICE_CUSTOM_MENU_AUDIO_MATRIX).navigation();
        }
        if (curtainDeviceMenuDispatch == null) {
            curtainDeviceMenuDispatch = (UIDispatcher) ARouter.getInstance().build(CustomPath.CONTROLLER_DEVICE_CUSTOM_MENU_CURTAIN).navigation();
        }
        if (customDeviceMenuDispatch == null) {
            customDeviceMenuDispatch = (UIDispatcher) ARouter.getInstance().build(CustomPath.CONTROLLER_DEVICE_CUSTOM_MENU_NORMAL).navigation();
        }
        parentConnect = parent;
        this.dispatch(type);
    }

    @Override
    public void dispatch(String type) {
        this.prevType = this.type;
        this.type = type;
        deviceType = DeviceType.getCustomType(type);
        if (deviceType == null) {
            return;
        }
        if (this.contentView != null) {
            this.dispatch(type, contentView);
        }
    }

    @Override
    public void onReady() {
        super.onReady();
        if (isAudioMatrix(type)) {
            if (audioMatrixDispatch != null) {
                audioMatrixDispatch.onReady();
            }
        } else if (isCurtain(type)) {
            if (curtainDeviceMenuDispatch != null) {
                curtainDeviceMenuDispatch.onReady();
            }
        } else {
            if (customDeviceMenuDispatch != null) {
                customDeviceMenuDispatch.onReady();
            }
        }
    }

    @Override
    public void release() {
        super.release();
        if (audioMatrixDispatch != null) {
            audioMatrixDispatch.release();
        }
        if (curtainDeviceMenuDispatch != null) {
            curtainDeviceMenuDispatch.release();
        }
        if (customDeviceMenuDispatch != null) {
            customDeviceMenuDispatch.release();
        }
    }

    @Override
    public void invisible() {
        super.invisible();
        if (isAudioMatrix(prevType)) {
            if (audioMatrixDispatch != null) {
                audioMatrixDispatch.invisible();
            }
        } else if (isCurtain(prevType)) {
            if (curtainDeviceMenuDispatch != null) {
                curtainDeviceMenuDispatch.invisible();
            }
        } else {
            if (customDeviceMenuDispatch != null) {
                customDeviceMenuDispatch.invisible();
            }
        }
    }

    private boolean isAudioMatrix(String typeCode) {
        if (typeCode == null)
            return false;
        DeviceType customType = DeviceType.getCustomType(typeCode);
        return isAudioMatrix(customType);
    }

    private boolean isAudioMatrix(DeviceType deviceType) {
        if (deviceType == null)
            return false;
        //判断是否音频处理器
        return deviceType.getDeviceTypeName().contains("音频处理器");
    }

    /**
     * 判断是不是窗帘自定义设备
     * @param typeCode 类型码
     * @return
     */
    private boolean isCurtain(String typeCode) {
        if (typeCode == null)
            return false;
        DeviceType customType = DeviceType.getCustomType(typeCode);
        return isCurtain(customType);
    }

    /**
     * 判断是不是窗帘自定义设备
     * @param deviceType 设备类型
     * @return
     */
    private boolean isCurtain(DeviceType deviceType) {
        if (deviceType == null) return false;
        //判断是否是自定义窗帘
        return deviceType.getDeviceTypeName().contains("窗帘");
    }
}
