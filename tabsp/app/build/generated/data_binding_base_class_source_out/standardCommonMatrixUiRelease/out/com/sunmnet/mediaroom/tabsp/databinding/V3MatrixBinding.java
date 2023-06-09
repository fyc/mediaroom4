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
import android.widget.GridView;
import com.sunmnet.mediaroom.matrix.MatrixView;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.ui.adapter.HolderAdapter;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class V3MatrixBinding extends ViewDataBinding {
  @NonNull
  public final GridView matrix3InputGrid;

  @NonNull
  public final MatrixView matrixview3;

  @Bindable
  protected HolderAdapter mInputAdapter;

  protected V3MatrixBinding(Object _bindingComponent, View _root, int _localFieldCount,
      GridView matrix3InputGrid, MatrixView matrixview3) {
    super(_bindingComponent, _root, _localFieldCount);
    this.matrix3InputGrid = matrix3InputGrid;
    this.matrixview3 = matrixview3;
  }

  public abstract void setInputAdapter(@Nullable HolderAdapter inputAdapter);

  @Nullable
  public HolderAdapter getInputAdapter() {
    return mInputAdapter;
  }

  @NonNull
  public static V3MatrixBinding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup root,
      boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.tabsp_version3_matrix_for_free, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static V3MatrixBinding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup root,
      boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<V3MatrixBinding>inflateInternal(inflater, R.layout.tabsp_version3_matrix_for_free, root, attachToRoot, component);
  }

  @NonNull
  public static V3MatrixBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.tabsp_version3_matrix_for_free, null, false, component)
   */
  @NonNull
  @Deprecated
  public static V3MatrixBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<V3MatrixBinding>inflateInternal(inflater, R.layout.tabsp_version3_matrix_for_free, null, false, component);
  }

  public static V3MatrixBinding bind(@NonNull View view) {
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
  public static V3MatrixBinding bind(@NonNull View view, @Nullable Object component) {
    return (V3MatrixBinding)bind(component, view, R.layout.tabsp_version3_matrix_for_free);
  }
}
