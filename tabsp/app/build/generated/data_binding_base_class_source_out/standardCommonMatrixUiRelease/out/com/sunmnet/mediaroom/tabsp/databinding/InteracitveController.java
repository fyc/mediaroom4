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
import android.widget.ListView;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.ui.adapter.HolderAdapter;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class InteracitveController extends ViewDataBinding {
  @NonNull
  public final ListView displayListview;

  @Bindable
  protected HolderAdapter mAdapter;

  protected InteracitveController(Object _bindingComponent, View _root, int _localFieldCount,
      ListView displayListview) {
    super(_bindingComponent, _root, _localFieldCount);
    this.displayListview = displayListview;
  }

  public abstract void setAdapter(@Nullable HolderAdapter adapter);

  @Nullable
  public HolderAdapter getAdapter() {
    return mAdapter;
  }

  @NonNull
  public static InteracitveController inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.tabsp_version2_display_layout, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static InteracitveController inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<InteracitveController>inflateInternal(inflater, R.layout.tabsp_version2_display_layout, root, attachToRoot, component);
  }

  @NonNull
  public static InteracitveController inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.tabsp_version2_display_layout, null, false, component)
   */
  @NonNull
  @Deprecated
  public static InteracitveController inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<InteracitveController>inflateInternal(inflater, R.layout.tabsp_version2_display_layout, null, false, component);
  }

  public static InteracitveController bind(@NonNull View view) {
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
  public static InteracitveController bind(@NonNull View view, @Nullable Object component) {
    return (InteracitveController)bind(component, view, R.layout.tabsp_version2_display_layout);
  }
}
