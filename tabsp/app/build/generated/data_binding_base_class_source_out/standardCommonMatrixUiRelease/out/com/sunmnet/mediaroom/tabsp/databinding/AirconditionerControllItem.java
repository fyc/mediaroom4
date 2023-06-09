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
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.sunmnet.mediaroom.device.bean.Airconditioner;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.ui.adapter.BindingAdapter;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class AirconditionerControllItem extends ViewDataBinding {
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

  @NonNull
  public final TextView temptureValue;

  @NonNull
  public final FrameLayout volumnMinusBtn;

  @NonNull
  public final FrameLayout volumnPlusBtn;

  @Bindable
  protected Airconditioner mDev;

  @Bindable
  protected Airconditioner.Setting mSetting;

  @Bindable
  protected BindingAdapter mSettingAdapter;

  protected AirconditionerControllItem(Object _bindingComponent, View _root, int _localFieldCount,
      LinearLayout airconditionerOpt, TextView deviceName, LinearLayout modes, Button singleClose,
      Button singleOpen, TextView temptureValue, FrameLayout volumnMinusBtn,
      FrameLayout volumnPlusBtn) {
    super(_bindingComponent, _root, _localFieldCount);
    this.airconditionerOpt = airconditionerOpt;
    this.deviceName = deviceName;
    this.modes = modes;
    this.singleClose = singleClose;
    this.singleOpen = singleOpen;
    this.temptureValue = temptureValue;
    this.volumnMinusBtn = volumnMinusBtn;
    this.volumnPlusBtn = volumnPlusBtn;
  }

  public abstract void setDev(@Nullable Airconditioner dev);

  @Nullable
  public Airconditioner getDev() {
    return mDev;
  }

  public abstract void setSetting(@Nullable Airconditioner.Setting setting);

  @Nullable
  public Airconditioner.Setting getSetting() {
    return mSetting;
  }

  public abstract void setSettingAdapter(@Nullable BindingAdapter settingAdapter);

  @Nullable
  public BindingAdapter getSettingAdapter() {
    return mSettingAdapter;
  }

  @NonNull
  public static AirconditionerControllItem inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.tabsp_version3_airconditioner_item_layout, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static AirconditionerControllItem inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<AirconditionerControllItem>inflateInternal(inflater, R.layout.tabsp_version3_airconditioner_item_layout, root, attachToRoot, component);
  }

  @NonNull
  public static AirconditionerControllItem inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.tabsp_version3_airconditioner_item_layout, null, false, component)
   */
  @NonNull
  @Deprecated
  public static AirconditionerControllItem inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<AirconditionerControllItem>inflateInternal(inflater, R.layout.tabsp_version3_airconditioner_item_layout, null, false, component);
  }

  public static AirconditionerControllItem bind(@NonNull View view) {
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
  public static AirconditionerControllItem bind(@NonNull View view, @Nullable Object component) {
    return (AirconditionerControllItem)bind(component, view, R.layout.tabsp_version3_airconditioner_item_layout);
  }
}
