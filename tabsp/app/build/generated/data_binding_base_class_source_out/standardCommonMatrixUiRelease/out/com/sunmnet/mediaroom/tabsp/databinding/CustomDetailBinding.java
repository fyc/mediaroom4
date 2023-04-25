// Generated by data binding compiler. Do not edit!
package com.sunmnet.mediaroom.tabsp.databinding;

import android.databinding.Bindable;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.ui.adapter.BindingAdapter;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class CustomDetailBinding extends ViewDataBinding {
  @NonNull
  public final LinearLayout backBtn;

  @NonNull
  public final ConstraintLayout controlLayout;

  @NonNull
  public final ConstraintLayout customControlLayout;

  @NonNull
  public final TextView deviceSettingText;

  @NonNull
  public final GridView function;

  @NonNull
  public final LinearLayout functionLayout;

  @NonNull
  public final ProgressBar progress;

  @NonNull
  public final GridView source;

  @NonNull
  public final LinearLayout sourceLayout;

  @NonNull
  public final Switch switcher;

  @NonNull
  public final TextView titleFunction;

  @NonNull
  public final TextView titleSource;

  @Bindable
  protected String mTitleName;

  @Bindable
  protected BindingAdapter mFunctionAdapter;

  @Bindable
  protected BindingAdapter mSourceAdapter;

  protected CustomDetailBinding(Object _bindingComponent, View _root, int _localFieldCount,
      LinearLayout backBtn, ConstraintLayout controlLayout, ConstraintLayout customControlLayout,
      TextView deviceSettingText, GridView function, LinearLayout functionLayout,
      ProgressBar progress, GridView source, LinearLayout sourceLayout, Switch switcher,
      TextView titleFunction, TextView titleSource) {
    super(_bindingComponent, _root, _localFieldCount);
    this.backBtn = backBtn;
    this.controlLayout = controlLayout;
    this.customControlLayout = customControlLayout;
    this.deviceSettingText = deviceSettingText;
    this.function = function;
    this.functionLayout = functionLayout;
    this.progress = progress;
    this.source = source;
    this.sourceLayout = sourceLayout;
    this.switcher = switcher;
    this.titleFunction = titleFunction;
    this.titleSource = titleSource;
  }

  public abstract void setTitleName(@Nullable String titleName);

  @Nullable
  public String getTitleName() {
    return mTitleName;
  }

  public abstract void setFunctionAdapter(@Nullable BindingAdapter functionAdapter);

  @Nullable
  public BindingAdapter getFunctionAdapter() {
    return mFunctionAdapter;
  }

  public abstract void setSourceAdapter(@Nullable BindingAdapter sourceAdapter);

  @Nullable
  public BindingAdapter getSourceAdapter() {
    return mSourceAdapter;
  }

  @NonNull
  public static CustomDetailBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.tabsp_custom_device_detail_layout, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static CustomDetailBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<CustomDetailBinding>inflateInternal(inflater, R.layout.tabsp_custom_device_detail_layout, root, attachToRoot, component);
  }

  @NonNull
  public static CustomDetailBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.tabsp_custom_device_detail_layout, null, false, component)
   */
  @NonNull
  @Deprecated
  public static CustomDetailBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<CustomDetailBinding>inflateInternal(inflater, R.layout.tabsp_custom_device_detail_layout, null, false, component);
  }

  public static CustomDetailBinding bind(@NonNull View view) {
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
  public static CustomDetailBinding bind(@NonNull View view, @Nullable Object component) {
    return (CustomDetailBinding)bind(component, view, R.layout.tabsp_custom_device_detail_layout);
  }
}
