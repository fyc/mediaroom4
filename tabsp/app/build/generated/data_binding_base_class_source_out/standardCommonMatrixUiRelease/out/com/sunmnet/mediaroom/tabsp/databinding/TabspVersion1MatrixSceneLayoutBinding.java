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
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.ui.adapter.HolderAdapter;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class TabspVersion1MatrixSceneLayoutBinding extends ViewDataBinding {
  @Bindable
  protected HolderAdapter mAdapter;

  protected TabspVersion1MatrixSceneLayoutBinding(Object _bindingComponent, View _root,
      int _localFieldCount) {
    super(_bindingComponent, _root, _localFieldCount);
  }

  public abstract void setAdapter(@Nullable HolderAdapter adapter);

  @Nullable
  public HolderAdapter getAdapter() {
    return mAdapter;
  }

  @NonNull
  public static TabspVersion1MatrixSceneLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.tabsp_version1_matrix_scene_layout, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static TabspVersion1MatrixSceneLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<TabspVersion1MatrixSceneLayoutBinding>inflateInternal(inflater, R.layout.tabsp_version1_matrix_scene_layout, root, attachToRoot, component);
  }

  @NonNull
  public static TabspVersion1MatrixSceneLayoutBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.tabsp_version1_matrix_scene_layout, null, false, component)
   */
  @NonNull
  @Deprecated
  public static TabspVersion1MatrixSceneLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<TabspVersion1MatrixSceneLayoutBinding>inflateInternal(inflater, R.layout.tabsp_version1_matrix_scene_layout, null, false, component);
  }

  public static TabspVersion1MatrixSceneLayoutBinding bind(@NonNull View view) {
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
  public static TabspVersion1MatrixSceneLayoutBinding bind(@NonNull View view,
      @Nullable Object component) {
    return (TabspVersion1MatrixSceneLayoutBinding)bind(component, view, R.layout.tabsp_version1_matrix_scene_layout);
  }
}
