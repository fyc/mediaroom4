// Generated by data binding compiler. Do not edit!
package com.sunmnet.mediaroom.tabsp.databinding;

import android.databinding.Bindable;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.sunmnet.mediaroom.device.bean.AbstractDevice;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.ui.widget.MarqueTextView;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class DeviceInfoBinding extends ViewDataBinding {
  @NonNull
  public final ProgressBar controllProcessing;

  @NonNull
  public final FrameLayout deviceImageHolder;

  @NonNull
  public final ImageView deviceImg;

  @NonNull
  public final MarqueTextView deviceName;

  @Bindable
  protected AbstractDevice mDevice;

  protected DeviceInfoBinding(Object _bindingComponent, View _root, int _localFieldCount,
      ProgressBar controllProcessing, FrameLayout deviceImageHolder, ImageView deviceImg,
      MarqueTextView deviceName) {
    super(_bindingComponent, _root, _localFieldCount);
    this.controllProcessing = controllProcessing;
    this.deviceImageHolder = deviceImageHolder;
    this.deviceImg = deviceImg;
    this.deviceName = deviceName;
  }

  public abstract void setDevice(@Nullable AbstractDevice device);

  @Nullable
  public AbstractDevice getDevice() {
    return mDevice;
  }

  @NonNull
  public static DeviceInfoBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.tabsp_device_item_layout, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static DeviceInfoBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<DeviceInfoBinding>inflateInternal(inflater, R.layout.tabsp_device_item_layout, root, attachToRoot, component);
  }

  @NonNull
  public static DeviceInfoBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.tabsp_device_item_layout, null, false, component)
   */
  @NonNull
  @Deprecated
  public static DeviceInfoBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<DeviceInfoBinding>inflateInternal(inflater, R.layout.tabsp_device_item_layout, null, false, component);
  }

  public static DeviceInfoBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.bind(view, component)
   */
  @Deprecated
  public static DeviceInfoBinding bind(@NonNull View view, @Nullable Object component) {
    return (DeviceInfoBinding)bind(component, view, R.layout.tabsp_device_item_layout);
  }
}
