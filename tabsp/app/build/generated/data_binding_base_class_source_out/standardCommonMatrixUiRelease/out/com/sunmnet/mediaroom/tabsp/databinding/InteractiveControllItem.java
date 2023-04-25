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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.sunmnet.mediaroom.device.bean.Interactive;
import com.sunmnet.mediaroom.tabsp.R;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class InteractiveControllItem extends ViewDataBinding {
  @NonNull
  public final LinearLayout airconditionerOpt;

  @NonNull
  public final TextView deviceName;

  @NonNull
  public final LinearLayout modes;

  @NonNull
  public final Button singleClose;

  @NonNull
  public final Button singleOpen;

  @Bindable
  protected Interactive mDev;

  protected InteractiveControllItem(Object _bindingComponent, View _root, int _localFieldCount,
      LinearLayout airconditionerOpt, TextView deviceName, LinearLayout modes, Button singleClose,
      Button singleOpen) {
    super(_bindingComponent, _root, _localFieldCount);
    this.airconditionerOpt = airconditionerOpt;
    this.deviceName = deviceName;
    this.modes = modes;
    this.singleClose = singleClose;
    this.singleOpen = singleOpen;
  }

  public abstract void setDev(@Nullable Interactive dev);

  @Nullable
  public Interactive getDev() {
    return mDev;
  }

  @NonNull
  public static InteractiveControllItem inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.tabsp_version3_interactive_item_layout, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static InteractiveControllItem inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<InteractiveControllItem>inflateInternal(inflater, R.layout.tabsp_version3_interactive_item_layout, root, attachToRoot, component);
  }

  @NonNull
  public static InteractiveControllItem inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.tabsp_version3_interactive_item_layout, null, false, component)
   */
  @NonNull
  @Deprecated
  public static InteractiveControllItem inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<InteractiveControllItem>inflateInternal(inflater, R.layout.tabsp_version3_interactive_item_layout, null, false, component);
  }

  public static InteractiveControllItem bind(@NonNull View view) {
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
  public static InteractiveControllItem bind(@NonNull View view, @Nullable Object component) {
    return (InteractiveControllItem)bind(component, view, R.layout.tabsp_version3_interactive_item_layout);
  }
}
