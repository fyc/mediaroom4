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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import com.sunmnet.mediaroom.device.bean.AbstractDevice;
import com.sunmnet.mediaroom.tabsp.R;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class DoorBinding extends ViewDataBinding {
  @NonNull
  public final ImageView deviceImg;

  @NonNull
  public final LinearLayout doorLock;

  @NonNull
  public final ImageView doorLockImg;

  @NonNull
  public final LinearLayout doorMag;

  @NonNull
  public final ImageView doorMagImg;

  @NonNull
  public final ProgressBar doorProcessing;

  @NonNull
  public final LinearLayout doorTitle;

  @NonNull
  public final TextView magneticState;

  @NonNull
  public final Switch switcherDoorlock;

  @Bindable
  protected AbstractDevice mDevice;

  protected DoorBinding(Object _bindingComponent, View _root, int _localFieldCount,
      ImageView deviceImg, LinearLayout doorLock, ImageView doorLockImg, LinearLayout doorMag,
      ImageView doorMagImg, ProgressBar doorProcessing, LinearLayout doorTitle,
      TextView magneticState, Switch switcherDoorlock) {
    super(_bindingComponent, _root, _localFieldCount);
    this.deviceImg = deviceImg;
    this.doorLock = doorLock;
    this.doorLockImg = doorLockImg;
    this.doorMag = doorMag;
    this.doorMagImg = doorMagImg;
    this.doorProcessing = doorProcessing;
    this.doorTitle = doorTitle;
    this.magneticState = magneticState;
    this.switcherDoorlock = switcherDoorlock;
  }

  public abstract void setDevice(@Nullable AbstractDevice device);

  @Nullable
  public AbstractDevice getDevice() {
    return mDevice;
  }

  @NonNull
  public static DoorBinding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup root,
      boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.tabsp_door_item_layout, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static DoorBinding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup root,
      boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<DoorBinding>inflateInternal(inflater, R.layout.tabsp_door_item_layout, root, attachToRoot, component);
  }

  @NonNull
  public static DoorBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.tabsp_door_item_layout, null, false, component)
   */
  @NonNull
  @Deprecated
  public static DoorBinding inflate(@NonNull LayoutInflater inflater, @Nullable Object component) {
    return ViewDataBinding.<DoorBinding>inflateInternal(inflater, R.layout.tabsp_door_item_layout, null, false, component);
  }

  public static DoorBinding bind(@NonNull View view) {
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
  public static DoorBinding bind(@NonNull View view, @Nullable Object component) {
    return (DoorBinding)bind(component, view, R.layout.tabsp_door_item_layout);
  }
}