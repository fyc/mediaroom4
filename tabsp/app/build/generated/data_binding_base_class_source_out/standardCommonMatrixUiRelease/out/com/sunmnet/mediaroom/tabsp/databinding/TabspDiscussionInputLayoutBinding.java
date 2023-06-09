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
import android.widget.GridView;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.ui.adapter.BindingAdapter;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class TabspDiscussionInputLayoutBinding extends ViewDataBinding {
  @NonNull
  public final GridView inputGrid;

  @NonNull
  public final GridView outputGroup;

  @NonNull
  public final FrameLayout tipseInput;

  @Bindable
  protected BindingAdapter mInputAdapter;

  @Bindable
  protected BindingAdapter mInputGroupAdapter;

  protected TabspDiscussionInputLayoutBinding(Object _bindingComponent, View _root,
      int _localFieldCount, GridView inputGrid, GridView outputGroup, FrameLayout tipseInput) {
    super(_bindingComponent, _root, _localFieldCount);
    this.inputGrid = inputGrid;
    this.outputGroup = outputGroup;
    this.tipseInput = tipseInput;
  }

  public abstract void setInputAdapter(@Nullable BindingAdapter inputAdapter);

  @Nullable
  public BindingAdapter getInputAdapter() {
    return mInputAdapter;
  }

  public abstract void setInputGroupAdapter(@Nullable BindingAdapter inputGroupAdapter);

  @Nullable
  public BindingAdapter getInputGroupAdapter() {
    return mInputGroupAdapter;
  }

  @NonNull
  public static TabspDiscussionInputLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.tabsp_discussion_input_layout, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static TabspDiscussionInputLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<TabspDiscussionInputLayoutBinding>inflateInternal(inflater, R.layout.tabsp_discussion_input_layout, root, attachToRoot, component);
  }

  @NonNull
  public static TabspDiscussionInputLayoutBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.tabsp_discussion_input_layout, null, false, component)
   */
  @NonNull
  @Deprecated
  public static TabspDiscussionInputLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<TabspDiscussionInputLayoutBinding>inflateInternal(inflater, R.layout.tabsp_discussion_input_layout, null, false, component);
  }

  public static TabspDiscussionInputLayoutBinding bind(@NonNull View view) {
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
  public static TabspDiscussionInputLayoutBinding bind(@NonNull View view,
      @Nullable Object component) {
    return (TabspDiscussionInputLayoutBinding)bind(component, view, R.layout.tabsp_discussion_input_layout);
  }
}
