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
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.sunmnet.mediaroom.device.bean.Airconditioner;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.ui.adapter.BindingAdapter;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class AirconditionerBinding extends ViewDataBinding {
  @NonNull
  public final View airconditionerMinus;

  @NonNull
  public final GridView airconditionerMode;

  /**
   * This binding is not available in all configurations.
   * <p>
   * Present:
   * <ul>
   *   <li>layout/</li>
   * </ul>
   *
   * Absent:
   * <ul>
   *   <li>layout-land/</li>
   * </ul>
   */
  @Nullable
  public final LinearLayout airconditionerOperate;

  @NonNull
  public final View airconditionerPlus;

  @NonNull
  public final LinearLayout airconditionerSwitcher;

  @NonNull
  public final ListView devicelist;

  @NonNull
  public final Button switcherClose;

  @NonNull
  public final Button switcherOpen;

  @Bindable
  protected BindingAdapter mAdapter;

  @Bindable
  protected Airconditioner.Setting mSetting;

  @Bindable
  protected BindingAdapter mSettingAdapter;

  protected AirconditionerBinding(Object _bindingComponent, View _root, int _localFieldCount,
      View airconditionerMinus, GridView airconditionerMode, LinearLayout airconditionerOperate,
      View airconditionerPlus, LinearLayout airconditionerSwitcher, ListView devicelist,
      Button switcherClose, Button switcherOpen) {
    super(_bindingComponent, _root, _localFieldCount);
    this.airconditionerMinus = airconditionerMinus;
    this.airconditionerMode = airconditionerMode;
    this.airconditionerOperate = airconditionerOperate;
    this.airconditionerPlus = airconditionerPlus;
    this.airconditionerSwitcher = airconditionerSwitcher;
    this.devicelist = devicelist;
    this.switcherClose = switcherClose;
    this.switcherOpen = switcherOpen;
  }

  public abstract void setAdapter(@Nullable BindingAdapter adapter);

  @Nullable
  public BindingAdapter getAdapter() {
    return mAdapter;
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
  public static AirconditionerBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.tabsp_airconditioner_layout, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static AirconditionerBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<AirconditionerBinding>inflateInternal(inflater, R.layout.tabsp_airconditioner_layout, root, attachToRoot, component);
  }

  @NonNull
  public static AirconditionerBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.tabsp_airconditioner_layout, null, false, component)
   */
  @NonNull
  @Deprecated
  public static AirconditionerBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<AirconditionerBinding>inflateInternal(inflater, R.layout.tabsp_airconditioner_layout, null, false, component);
  }

  public static AirconditionerBinding bind(@NonNull View view) {
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
  public static AirconditionerBinding bind(@NonNull View view, @Nullable Object component) {
    return (AirconditionerBinding)bind(component, view, R.layout.tabsp_airconditioner_layout);
  }
}