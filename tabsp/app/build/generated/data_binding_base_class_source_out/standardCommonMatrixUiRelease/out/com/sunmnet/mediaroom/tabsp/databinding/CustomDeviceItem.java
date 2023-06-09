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
import com.sunmnet.mediaroom.device.bean.CustomDevice;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.ui.widget.RollingTextView;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class CustomDeviceItem extends ViewDataBinding {
  @NonNull
  public final RollingTextView rollingText;

  @Bindable
  protected CustomDevice.ComponentItem mComponent;

  protected CustomDeviceItem(Object _bindingComponent, View _root, int _localFieldCount,
      RollingTextView rollingText) {
    super(_bindingComponent, _root, _localFieldCount);
    this.rollingText = rollingText;
  }

  public abstract void setComponent(@Nullable CustomDevice.ComponentItem component);

  @Nullable
  public CustomDevice.ComponentItem getComponent() {
    return mComponent;
  }

  @NonNull
  public static CustomDeviceItem inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup root,
      boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.tabsp_custom_device_item_layout, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static CustomDeviceItem inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup root,
      boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<CustomDeviceItem>inflateInternal(inflater, R.layout.tabsp_custom_device_item_layout, root, attachToRoot, component);
  }

  @NonNull
  public static CustomDeviceItem inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.tabsp_custom_device_item_layout, null, false, component)
   */
  @NonNull
  @Deprecated
  public static CustomDeviceItem inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<CustomDeviceItem>inflateInternal(inflater, R.layout.tabsp_custom_device_item_layout, null, false, component);
  }

  public static CustomDeviceItem bind(@NonNull View view) {
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
  public static CustomDeviceItem bind(@NonNull View view, @Nullable Object component) {
    return (CustomDeviceItem)bind(component, view, R.layout.tabsp_custom_device_item_layout);
  }
}
