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
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.bean.NetworkBean;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class EthernetDataBinding extends ViewDataBinding {
  @NonNull
  public final Button disable;

  @NonNull
  public final Button enable;

  @Bindable
  protected NetworkBean mNetwork;

  protected EthernetDataBinding(Object _bindingComponent, View _root, int _localFieldCount,
      Button disable, Button enable) {
    super(_bindingComponent, _root, _localFieldCount);
    this.disable = disable;
    this.enable = enable;
  }

  public abstract void setNetwork(@Nullable NetworkBean network);

  @Nullable
  public NetworkBean getNetwork() {
    return mNetwork;
  }

  @NonNull
  public static EthernetDataBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.tabsp_setting_ethernet_layout, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static EthernetDataBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<EthernetDataBinding>inflateInternal(inflater, R.layout.tabsp_setting_ethernet_layout, root, attachToRoot, component);
  }

  @NonNull
  public static EthernetDataBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.tabsp_setting_ethernet_layout, null, false, component)
   */
  @NonNull
  @Deprecated
  public static EthernetDataBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<EthernetDataBinding>inflateInternal(inflater, R.layout.tabsp_setting_ethernet_layout, null, false, component);
  }

  public static EthernetDataBinding bind(@NonNull View view) {
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
  public static EthernetDataBinding bind(@NonNull View view, @Nullable Object component) {
    return (EthernetDataBinding)bind(component, view, R.layout.tabsp_setting_ethernet_layout);
  }
}
