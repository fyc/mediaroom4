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
import android.widget.TextView;
import com.sunmnet.mediaroom.matrix.R;
import com.sunmnet.mediaroom.matrix.anotherUi.ui.MatrixTableLayout;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class MatrixLayoutEditBinding extends ViewDataBinding {
  @NonNull
  public final LinearLayout backFragment;

  @NonNull
  public final Button cancel;

  @NonNull
  public final Button clear;

  @NonNull
  public final ImageView deviceBackImage;

  @NonNull
  public final TextView deviceTypeNameTitle;

  @NonNull
  public final GridView matrixEditGridview;

  @NonNull
  public final MatrixTableLayout matrixEditTable;

  @NonNull
  public final Button preview;

  @NonNull
  public final Button save;

  protected MatrixLayoutEditBinding(Object _bindingComponent, View _root, int _localFieldCount,
      LinearLayout backFragment, Button cancel, Button clear, ImageView deviceBackImage,
      TextView deviceTypeNameTitle, GridView matrixEditGridview, MatrixTableLayout matrixEditTable,
      Button preview, Button save) {
    super(_bindingComponent, _root, _localFieldCount);
    this.backFragment = backFragment;
    this.cancel = cancel;
    this.clear = clear;
    this.deviceBackImage = deviceBackImage;
    this.deviceTypeNameTitle = deviceTypeNameTitle;
    this.matrixEditGridview = matrixEditGridview;
    this.matrixEditTable = matrixEditTable;
    this.preview = preview;
    this.save = save;
  }

  @NonNull
  public static MatrixLayoutEditBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.matrix_layout_edit, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static MatrixLayoutEditBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<MatrixLayoutEditBinding>inflateInternal(inflater, R.layout.matrix_layout_edit, root, attachToRoot, component);
  }

  @NonNull
  public static MatrixLayoutEditBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.matrix_layout_edit, null, false, component)
   */
  @NonNull
  @Deprecated
  public static MatrixLayoutEditBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<MatrixLayoutEditBinding>inflateInternal(inflater, R.layout.matrix_layout_edit, null, false, component);
  }

  public static MatrixLayoutEditBinding bind(@NonNull View view) {
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
  public static MatrixLayoutEditBinding bind(@NonNull View view, @Nullable Object component) {
    return (MatrixLayoutEditBinding)bind(component, view, R.layout.matrix_layout_edit);
  }
}