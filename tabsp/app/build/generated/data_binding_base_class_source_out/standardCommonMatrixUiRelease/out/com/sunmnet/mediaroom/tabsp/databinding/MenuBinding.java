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
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.bean.MenuOperator;
import com.sunmnet.mediaroom.tabsp.ui.widget.RollingTextView;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class MenuBinding extends ViewDataBinding {
  @NonNull
  public final ImageView menuIcon;

  @NonNull
  public final RollingTextView menuView;

  @NonNull
  public final LinearLayout menuitem;

  @NonNull
  public final View rightLine;

  @Bindable
  protected MenuOperator mMenu;

  protected MenuBinding(Object _bindingComponent, View _root, int _localFieldCount,
      ImageView menuIcon, RollingTextView menuView, LinearLayout menuitem, View rightLine) {
    super(_bindingComponent, _root, _localFieldCount);
    this.menuIcon = menuIcon;
    this.menuView = menuView;
    this.menuitem = menuitem;
    this.rightLine = rightLine;
  }

  public abstract void setMenu(@Nullable MenuOperator menu);

  @Nullable
  public MenuOperator getMenu() {
    return mMenu;
  }

  @NonNull
  public static MenuBinding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup root,
      boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.tabsp_menu_item_layout, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static MenuBinding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup root,
      boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<MenuBinding>inflateInternal(inflater, R.layout.tabsp_menu_item_layout, root, attachToRoot, component);
  }

  @NonNull
  public static MenuBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.tabsp_menu_item_layout, null, false, component)
   */
  @NonNull
  @Deprecated
  public static MenuBinding inflate(@NonNull LayoutInflater inflater, @Nullable Object component) {
    return ViewDataBinding.<MenuBinding>inflateInternal(inflater, R.layout.tabsp_menu_item_layout, null, false, component);
  }

  public static MenuBinding bind(@NonNull View view) {
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
  public static MenuBinding bind(@NonNull View view, @Nullable Object component) {
    return (MenuBinding)bind(component, view, R.layout.tabsp_menu_item_layout);
  }
}
