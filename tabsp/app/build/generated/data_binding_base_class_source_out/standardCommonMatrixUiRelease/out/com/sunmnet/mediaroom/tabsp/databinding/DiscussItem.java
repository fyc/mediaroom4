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
import android.widget.TextView;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.interfaces.ICommonItem;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class DiscussItem extends ViewDataBinding {
  @NonNull
  public final FrameLayout deviceImageHolder;

  @NonNull
  public final ImageView deviceImg;

  @NonNull
  public final TextView deviceName;

  @Bindable
  protected ICommonItem mItem;

  protected DiscussItem(Object _bindingComponent, View _root, int _localFieldCount,
      FrameLayout deviceImageHolder, ImageView deviceImg, TextView deviceName) {
    super(_bindingComponent, _root, _localFieldCount);
    this.deviceImageHolder = deviceImageHolder;
    this.deviceImg = deviceImg;
    this.deviceName = deviceName;
  }

  public abstract void setItem(@Nullable ICommonItem item);

  @Nullable
  public ICommonItem getItem() {
    return mItem;
  }

  @NonNull
  public static DiscussItem inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup root,
      boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.tabsp_version2_custom_discuss_item, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static DiscussItem inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup root,
      boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<DiscussItem>inflateInternal(inflater, R.layout.tabsp_version2_custom_discuss_item, root, attachToRoot, component);
  }

  @NonNull
  public static DiscussItem inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.tabsp_version2_custom_discuss_item, null, false, component)
   */
  @NonNull
  @Deprecated
  public static DiscussItem inflate(@NonNull LayoutInflater inflater, @Nullable Object component) {
    return ViewDataBinding.<DiscussItem>inflateInternal(inflater, R.layout.tabsp_version2_custom_discuss_item, null, false, component);
  }

  public static DiscussItem bind(@NonNull View view) {
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
  public static DiscussItem bind(@NonNull View view, @Nullable Object component) {
    return (DiscussItem)bind(component, view, R.layout.tabsp_version2_custom_discuss_item);
  }
}
