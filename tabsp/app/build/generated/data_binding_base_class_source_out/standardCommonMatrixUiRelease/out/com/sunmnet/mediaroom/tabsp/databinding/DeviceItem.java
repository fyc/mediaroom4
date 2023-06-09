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
import android.widget.TextView;
import com.sunmnet.mediaroom.device.bean.AbstractDevice;
import com.sunmnet.mediaroom.tabsp.R;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class DeviceItem extends ViewDataBinding {
  @NonNull
  public final FrameLayout modeContainer;

  @NonNull
  public final TextView modeName;

  @NonNull
  public final View selectTag;

  @Bindable
  protected AbstractDevice mDevice;

  protected DeviceItem(Object _bindingComponent, View _root, int _localFieldCount,
      FrameLayout modeContainer, TextView modeName, View selectTag) {
    super(_bindingComponent, _root, _localFieldCount);
    this.modeContainer = modeContainer;
    this.modeName = modeName;
    this.selectTag = selectTag;
  }

  public abstract void setDevice(@Nullable AbstractDevice device);

  @Nullable
  public AbstractDevice getDevice() {
    return mDevice;
  }

  @NonNull
  public static DeviceItem inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup root,
      boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.tabsp_device_listviewitem_layout, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static DeviceItem inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup root,
      boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<DeviceItem>inflateInternal(inflater, R.layout.tabsp_device_listviewitem_layout, root, attachToRoot, component);
  }

  @NonNull
  public static DeviceItem inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.tabsp_device_listviewitem_layout, null, false, component)
   */
  @NonNull
  @Deprecated
  public static DeviceItem inflate(@NonNull LayoutInflater inflater, @Nullable Object component) {
    return ViewDataBinding.<DeviceItem>inflateInternal(inflater, R.layout.tabsp_device_listviewitem_layout, null, false, component);
  }

  public static DeviceItem bind(@NonNull View view) {
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
  public static DeviceItem bind(@NonNull View view, @Nullable Object component) {
    return (DeviceItem)bind(component, view, R.layout.tabsp_device_listviewitem_layout);
  }
}
