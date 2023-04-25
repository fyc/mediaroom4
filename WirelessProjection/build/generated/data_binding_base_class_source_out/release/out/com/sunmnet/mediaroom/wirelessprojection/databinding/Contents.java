// Generated by data binding compiler. Do not edit!
package com.sunmnet.mediaroom.wirelessprojection.databinding;

import android.databinding.Bindable;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.bozee.managerapp.WirelessActivity;
import com.sunmnet.mediaroom.wirelessprojection.R;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class Contents extends ViewDataBinding {
  @NonNull
  public final Button cancelAuthorize;

  @NonNull
  public final Button connectServerFinder;

  @NonNull
  public final Button disConnectServerFinder;

  @NonNull
  public final EditText hostIp;

  @NonNull
  public final Button requestAllClientsLock;

  @NonNull
  public final Button requestAllClientsUnLock;

  @NonNull
  public final Button requestAnDisplayInfo;

  @NonNull
  public final Button requestAnDisplaySettingInfo;

  @NonNull
  public final Button requestAnDisplaySettingInfoChange;

  @NonNull
  public final Button requestAppFiles;

  @NonNull
  public final Button requestAppMark;

  @NonNull
  public final Button requestAppWriter;

  @NonNull
  public final Button requestClassRoomAllClientsWorkOn;

  @NonNull
  public final Button requestClassRoomAllClientsWorkOut;

  @NonNull
  public final Button requestClassroomAllClientsOffScreen;

  @NonNull
  public final Button requestClassroomAllClientsOnScreen;

  @NonNull
  public final Button requestClassroomKickAllClients;

  @NonNull
  public final Button requestClassroomKickClient;

  @NonNull
  public final Button requestDeviceVolume;

  @NonNull
  public final Button requestLockRootDevice;

  @NonNull
  public final Button requestServerAuthorize;

  @NonNull
  public final Button requestSwitchDeviceStateReady;

  @NonNull
  public final Button requestSwitchDeviceStateSharing;

  @NonNull
  public final Button requestSystemInfo;

  @NonNull
  public final Button requestTeacherRecovery;

  @NonNull
  public final Button requestTeacherStop;

  @NonNull
  public final Button requestUnlockRootDevice;

  @NonNull
  public final Button requestUpdateClassRoomWorkOn;

  @NonNull
  public final Button requestUpdateClassRoomWorkOut;

  @NonNull
  public final Button requestUpdateDeviceVolume;

  @NonNull
  public final Button requestUpdateLockStatusLocked;

  @NonNull
  public final Button requestUpdateLockStatusUnLocked;

  @NonNull
  public final Button requestUpdatePermitStatusAlwaysAccepted;

  @NonNull
  public final Button requestUpdatePermitStatusAlwaysRejected;

  @NonNull
  public final Button requestUpdatePermitStatusAsk;

  @NonNull
  public final Button requestWorkOnModeMulti;

  @NonNull
  public final Button requestWorkOnModeSingle;

  @NonNull
  public final Button startServerFinder;

  @NonNull
  public final Button stopServerFinder;

  @Bindable
  protected WirelessActivity mValue;

  protected Contents(Object _bindingComponent, View _root, int _localFieldCount,
      Button cancelAuthorize, Button connectServerFinder, Button disConnectServerFinder,
      EditText hostIp, Button requestAllClientsLock, Button requestAllClientsUnLock,
      Button requestAnDisplayInfo, Button requestAnDisplaySettingInfo,
      Button requestAnDisplaySettingInfoChange, Button requestAppFiles, Button requestAppMark,
      Button requestAppWriter, Button requestClassRoomAllClientsWorkOn,
      Button requestClassRoomAllClientsWorkOut, Button requestClassroomAllClientsOffScreen,
      Button requestClassroomAllClientsOnScreen, Button requestClassroomKickAllClients,
      Button requestClassroomKickClient, Button requestDeviceVolume, Button requestLockRootDevice,
      Button requestServerAuthorize, Button requestSwitchDeviceStateReady,
      Button requestSwitchDeviceStateSharing, Button requestSystemInfo,
      Button requestTeacherRecovery, Button requestTeacherStop, Button requestUnlockRootDevice,
      Button requestUpdateClassRoomWorkOn, Button requestUpdateClassRoomWorkOut,
      Button requestUpdateDeviceVolume, Button requestUpdateLockStatusLocked,
      Button requestUpdateLockStatusUnLocked, Button requestUpdatePermitStatusAlwaysAccepted,
      Button requestUpdatePermitStatusAlwaysRejected, Button requestUpdatePermitStatusAsk,
      Button requestWorkOnModeMulti, Button requestWorkOnModeSingle, Button startServerFinder,
      Button stopServerFinder) {
    super(_bindingComponent, _root, _localFieldCount);
    this.cancelAuthorize = cancelAuthorize;
    this.connectServerFinder = connectServerFinder;
    this.disConnectServerFinder = disConnectServerFinder;
    this.hostIp = hostIp;
    this.requestAllClientsLock = requestAllClientsLock;
    this.requestAllClientsUnLock = requestAllClientsUnLock;
    this.requestAnDisplayInfo = requestAnDisplayInfo;
    this.requestAnDisplaySettingInfo = requestAnDisplaySettingInfo;
    this.requestAnDisplaySettingInfoChange = requestAnDisplaySettingInfoChange;
    this.requestAppFiles = requestAppFiles;
    this.requestAppMark = requestAppMark;
    this.requestAppWriter = requestAppWriter;
    this.requestClassRoomAllClientsWorkOn = requestClassRoomAllClientsWorkOn;
    this.requestClassRoomAllClientsWorkOut = requestClassRoomAllClientsWorkOut;
    this.requestClassroomAllClientsOffScreen = requestClassroomAllClientsOffScreen;
    this.requestClassroomAllClientsOnScreen = requestClassroomAllClientsOnScreen;
    this.requestClassroomKickAllClients = requestClassroomKickAllClients;
    this.requestClassroomKickClient = requestClassroomKickClient;
    this.requestDeviceVolume = requestDeviceVolume;
    this.requestLockRootDevice = requestLockRootDevice;
    this.requestServerAuthorize = requestServerAuthorize;
    this.requestSwitchDeviceStateReady = requestSwitchDeviceStateReady;
    this.requestSwitchDeviceStateSharing = requestSwitchDeviceStateSharing;
    this.requestSystemInfo = requestSystemInfo;
    this.requestTeacherRecovery = requestTeacherRecovery;
    this.requestTeacherStop = requestTeacherStop;
    this.requestUnlockRootDevice = requestUnlockRootDevice;
    this.requestUpdateClassRoomWorkOn = requestUpdateClassRoomWorkOn;
    this.requestUpdateClassRoomWorkOut = requestUpdateClassRoomWorkOut;
    this.requestUpdateDeviceVolume = requestUpdateDeviceVolume;
    this.requestUpdateLockStatusLocked = requestUpdateLockStatusLocked;
    this.requestUpdateLockStatusUnLocked = requestUpdateLockStatusUnLocked;
    this.requestUpdatePermitStatusAlwaysAccepted = requestUpdatePermitStatusAlwaysAccepted;
    this.requestUpdatePermitStatusAlwaysRejected = requestUpdatePermitStatusAlwaysRejected;
    this.requestUpdatePermitStatusAsk = requestUpdatePermitStatusAsk;
    this.requestWorkOnModeMulti = requestWorkOnModeMulti;
    this.requestWorkOnModeSingle = requestWorkOnModeSingle;
    this.startServerFinder = startServerFinder;
    this.stopServerFinder = stopServerFinder;
  }

  public abstract void setValue(@Nullable WirelessActivity value);

  @Nullable
  public WirelessActivity getValue() {
    return mValue;
  }

  @NonNull
  public static Contents inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup root,
      boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_main, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static Contents inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup root,
      boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<Contents>inflateInternal(inflater, R.layout.activity_main, root, attachToRoot, component);
  }

  @NonNull
  public static Contents inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_main, null, false, component)
   */
  @NonNull
  @Deprecated
  public static Contents inflate(@NonNull LayoutInflater inflater, @Nullable Object component) {
    return ViewDataBinding.<Contents>inflateInternal(inflater, R.layout.activity_main, null, false, component);
  }

  public static Contents bind(@NonNull View view) {
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
  public static Contents bind(@NonNull View view, @Nullable Object component) {
    return (Contents)bind(component, view, R.layout.activity_main);
  }
}