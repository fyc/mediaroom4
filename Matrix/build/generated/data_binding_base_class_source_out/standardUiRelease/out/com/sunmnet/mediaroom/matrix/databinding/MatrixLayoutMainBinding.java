// Generated by data binding compiler. Do not edit!
package com.sunmnet.mediaroom.matrix.databinding;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.sunmnet.mediaroom.matrix.R;
import com.sunmnet.mediaroom.matrix.anotherUi.ui.ColorfulMatrixLayout;
import com.sunmnet.mediaroom.matrix.ui.widget.MatrixSceneOneLineLayout;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class MatrixLayoutMainBinding extends ViewDataBinding {
  @NonNull
  public final Button btnClearall;

  @NonNull
  public final Button btnDefault;

  @NonNull
  public final Button btnSelectall;

  @NonNull
  public final ImageView ivArrow;

  @NonNull
  public final LinearLayout llMatrixPartOne;

  @NonNull
  public final ColorfulMatrixLayout matrixLayout;

  @NonNull
  public final GridView matrixMainGridview;

  @NonNull
  public final MatrixSceneOneLineLayout msolMatrixSceneList;

  @NonNull
  public final RelativeLayout rlMatrixPartThree;

  @NonNull
  public final RelativeLayout rlMatrixPartTwo;

  protected MatrixLayoutMainBinding(Object _bindingComponent, View _root, int _localFieldCount,
      Button btnClearall, Button btnDefault, Button btnSelectall, ImageView ivArrow,
      LinearLayout llMatrixPartOne, ColorfulMatrixLayout matrixLayout, GridView matrixMainGridview,
      MatrixSceneOneLineLayout msolMatrixSceneList, RelativeLayout rlMatrixPartThree,
      RelativeLayout rlMatrixPartTwo) {
    super(_bindingComponent, _root, _localFieldCount);
    this.btnClearall = btnClearall;
    this.btnDefault = btnDefault;
    this.btnSelectall = btnSelectall;
    this.ivArrow = ivArrow;
    this.llMatrixPartOne = llMatrixPartOne;
    this.matrixLayout = matrixLayout;
    this.matrixMainGridview = matrixMainGridview;
    this.msolMatrixSceneList = msolMatrixSceneList;
    this.rlMatrixPartThree = rlMatrixPartThree;
    this.rlMatrixPartTwo = rlMatrixPartTwo;
  }

  @NonNull
  public static MatrixLayoutMainBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.matrix_layout_main, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static MatrixLayoutMainBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<MatrixLayoutMainBinding>inflateInternal(inflater, R.layout.matrix_layout_main, root, attachToRoot, component);
  }

  @NonNull
  public static MatrixLayoutMainBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.matrix_layout_main, null, false, component)
   */
  @NonNull
  @Deprecated
  public static MatrixLayoutMainBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<MatrixLayoutMainBinding>inflateInternal(inflater, R.layout.matrix_layout_main, null, false, component);
  }

  public static MatrixLayoutMainBinding bind(@NonNull View view) {
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
  public static MatrixLayoutMainBinding bind(@NonNull View view, @Nullable Object component) {
    return (MatrixLayoutMainBinding)bind(component, view, R.layout.matrix_layout_main);
  }
}
