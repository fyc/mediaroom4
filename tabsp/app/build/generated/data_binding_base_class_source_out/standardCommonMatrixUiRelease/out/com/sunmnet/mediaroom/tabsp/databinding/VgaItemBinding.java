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
import android.widget.TextView;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.bean.VgaItem;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class VgaItemBinding extends ViewDataBinding {
  @NonNull
  public final ImageView vgaitemImage;

  @NonNull
  public final TextView vgaitemName;

  @Bindable
  protected VgaItem mVgaItem;

  protected VgaItemBinding(Object _bindingComponent, View _root, int _localFieldCount,
      ImageView vgaitemImage, TextView vgaitemName) {
    super(_bindingComponent, _root, _localFieldCount);
    this.vgaitemImage = vgaitemImage;
    this.vgaitemName = vgaitemName;
  }

  public abstract void setVgaItem(@Nullable VgaItem vgaItem);

  @Nullable
  public VgaItem getVgaItem() {
    return mVgaItem;
  }

  @NonNull
  public static VgaItemBinding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup root,
      boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.tabsp_version1_mediar_vga_item, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static VgaItemBinding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup root,
      boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<VgaItemBinding>inflateInternal(inflater, R.layout.tabsp_version1_mediar_vga_item, root, attachToRoot, component);
  }

  @NonNull
  public static VgaItemBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.tabsp_version1_mediar_vga_item, null, false, component)
   */
  @NonNull
  @Deprecated
  public static VgaItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<VgaItemBinding>inflateInternal(inflater, R.layout.tabsp_version1_mediar_vga_item, null, false, component);
  }

  public static VgaItemBinding bind(@NonNull View view) {
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
  public static VgaItemBinding bind(@NonNull View view, @Nullable Object component) {
    return (VgaItemBinding)bind(component, view, R.layout.tabsp_version1_mediar_vga_item);
  }
}
