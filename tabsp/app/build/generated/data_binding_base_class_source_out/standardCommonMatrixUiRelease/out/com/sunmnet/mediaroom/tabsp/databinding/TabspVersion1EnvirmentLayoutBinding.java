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
import com.sunmnet.mediaroom.device.bean.EnvirmentDevice;
import com.sunmnet.mediaroom.tabsp.R;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class TabspVersion1EnvirmentLayoutBinding extends ViewDataBinding {
  @Bindable
  protected EnvirmentDevice mEnvirment;

  protected TabspVersion1EnvirmentLayoutBinding(Object _bindingComponent, View _root,
      int _localFieldCount) {
    super(_bindingComponent, _root, _localFieldCount);
  }

  public abstract void setEnvirment(@Nullable EnvirmentDevice envirment);

  @Nullable
  public EnvirmentDevice getEnvirment() {
    return mEnvirment;
  }

  @NonNull
  public static TabspVersion1EnvirmentLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.tabsp_version1_envirment_layout, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static TabspVersion1EnvirmentLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<TabspVersion1EnvirmentLayoutBinding>inflateInternal(inflater, R.layout.tabsp_version1_envirment_layout, root, attachToRoot, component);
  }

  @NonNull
  public static TabspVersion1EnvirmentLayoutBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.tabsp_version1_envirment_layout, null, false, component)
   */
  @NonNull
  @Deprecated
  public static TabspVersion1EnvirmentLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<TabspVersion1EnvirmentLayoutBinding>inflateInternal(inflater, R.layout.tabsp_version1_envirment_layout, null, false, component);
  }

  public static TabspVersion1EnvirmentLayoutBinding bind(@NonNull View view) {
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
  public static TabspVersion1EnvirmentLayoutBinding bind(@NonNull View view,
      @Nullable Object component) {
    return (TabspVersion1EnvirmentLayoutBinding)bind(component, view, R.layout.tabsp_version1_envirment_layout);
  }
}
