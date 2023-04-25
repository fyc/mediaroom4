// Generated by data binding compiler. Do not edit!
package com.sunmnet.mediaroom.tabsp.databinding;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Guideline;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.sunmnet.mediaroom.tabsp.R;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class DialogFaultDescriptionBinding extends ViewDataBinding {
  @NonNull
  public final RadioButton audioDeviceFailure;

  @NonNull
  public final Button btnCancel;

  @NonNull
  public final Button btnSubmit;

  @NonNull
  public final RadioButton environmentalEquipmentFailure;

  @NonNull
  public final RadioButton equipmentCircuitFailure;

  @NonNull
  public final Guideline glOne;

  @NonNull
  public final LinearLayout llFaultDescription;

  @NonNull
  public final RadioButton multimediaDeviceFailure;

  @NonNull
  public final RadioButton otherEquipmentIssues;

  @NonNull
  public final RadioGroup rgFaultDescription;

  @NonNull
  public final TextView tvTitle;

  protected DialogFaultDescriptionBinding(Object _bindingComponent, View _root,
      int _localFieldCount, RadioButton audioDeviceFailure, Button btnCancel, Button btnSubmit,
      RadioButton environmentalEquipmentFailure, RadioButton equipmentCircuitFailure,
      Guideline glOne, LinearLayout llFaultDescription, RadioButton multimediaDeviceFailure,
      RadioButton otherEquipmentIssues, RadioGroup rgFaultDescription, TextView tvTitle) {
    super(_bindingComponent, _root, _localFieldCount);
    this.audioDeviceFailure = audioDeviceFailure;
    this.btnCancel = btnCancel;
    this.btnSubmit = btnSubmit;
    this.environmentalEquipmentFailure = environmentalEquipmentFailure;
    this.equipmentCircuitFailure = equipmentCircuitFailure;
    this.glOne = glOne;
    this.llFaultDescription = llFaultDescription;
    this.multimediaDeviceFailure = multimediaDeviceFailure;
    this.otherEquipmentIssues = otherEquipmentIssues;
    this.rgFaultDescription = rgFaultDescription;
    this.tvTitle = tvTitle;
  }

  @NonNull
  public static DialogFaultDescriptionBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.dialog_fault_description, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static DialogFaultDescriptionBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<DialogFaultDescriptionBinding>inflateInternal(inflater, R.layout.dialog_fault_description, root, attachToRoot, component);
  }

  @NonNull
  public static DialogFaultDescriptionBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.dialog_fault_description, null, false, component)
   */
  @NonNull
  @Deprecated
  public static DialogFaultDescriptionBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<DialogFaultDescriptionBinding>inflateInternal(inflater, R.layout.dialog_fault_description, null, false, component);
  }

  public static DialogFaultDescriptionBinding bind(@NonNull View view) {
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
  public static DialogFaultDescriptionBinding bind(@NonNull View view, @Nullable Object component) {
    return (DialogFaultDescriptionBinding)bind(component, view, R.layout.dialog_fault_description);
  }
}