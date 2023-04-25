package com.sunmnet.mediaroom.wirelessprojection.databinding;
import com.sunmnet.mediaroom.wirelessprojection.R;
import com.sunmnet.mediaroom.wirelessprojection.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ContentsImpl extends Contents  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.host_ip, 39);
    }
    // views
    @NonNull
    private final android.widget.ScrollView mboundView0;
    // variables
    // values
    // listeners
    private OnClickListenerImpl mValueRequestClassroomAllClientsOnScreenAndroidViewViewOnClickListener;
    private OnClickListenerImpl1 mValueRequestSystemInfoAndroidViewViewOnClickListener;
    private OnClickListenerImpl2 mValueStartServerFinderAndroidViewViewOnClickListener;
    private OnClickListenerImpl3 mValueConnectServerFinderAndroidViewViewOnClickListener;
    private OnClickListenerImpl4 mValueDisConnectServerFinderAndroidViewViewOnClickListener;
    private OnClickListenerImpl5 mValueRequestAnDisplaySettingInfoAndroidViewViewOnClickListener;
    private OnClickListenerImpl6 mValueRequestAllClientsLockAndroidViewViewOnClickListener;
    private OnClickListenerImpl7 mValueRequestAppWriterAndroidViewViewOnClickListener;
    private OnClickListenerImpl8 mValueRequestAppMarkAndroidViewViewOnClickListener;
    private OnClickListenerImpl9 mValueRequestUpdateDeviceVolumeAndroidViewViewOnClickListener;
    private OnClickListenerImpl10 mValueRequestDeviceVolumeAndroidViewViewOnClickListener;
    private OnClickListenerImpl11 mValueRequestClassroomKickClientAndroidViewViewOnClickListener;
    private OnClickListenerImpl12 mValueRequestSwitchDeviceStateReadyAndroidViewViewOnClickListener;
    private OnClickListenerImpl13 mValueRequestClassRoomAllClientsWorkOutAndroidViewViewOnClickListener;
    private OnClickListenerImpl14 mValueStopServerFinderAndroidViewViewOnClickListener;
    private OnClickListenerImpl15 mValueRequestTeacherRecoveryAndroidViewViewOnClickListener;
    private OnClickListenerImpl16 mValueRequestAppFilesAndroidViewViewOnClickListener;
    private OnClickListenerImpl17 mValueRequestAnDisplayInfoAndroidViewViewOnClickListener;
    private OnClickListenerImpl18 mValueRequestUpdateClassRoomWorkOutAndroidViewViewOnClickListener;
    private OnClickListenerImpl19 mValueRequestAllClientsUnLockAndroidViewViewOnClickListener;
    private OnClickListenerImpl20 mValueRequestUpdatePermitStatusAlwaysAcceptedAndroidViewViewOnClickListener;
    private OnClickListenerImpl21 mValueRequestUpdateClassRoomWorkOnAndroidViewViewOnClickListener;
    private OnClickListenerImpl22 mValueRequestSwitchDeviceStateSharingAndroidViewViewOnClickListener;
    private OnClickListenerImpl23 mValueRequestLockRootDeviceAndroidViewViewOnClickListener;
    private OnClickListenerImpl24 mValueRequestUpdateLockStatusLockedAndroidViewViewOnClickListener;
    private OnClickListenerImpl25 mValueCancelAuthorizeAndroidViewViewOnClickListener;
    private OnClickListenerImpl26 mValueRequestUpdateLockStatusUnLockedAndroidViewViewOnClickListener;
    private OnClickListenerImpl27 mValueRequestUnlockRootDeviceAndroidViewViewOnClickListener;
    private OnClickListenerImpl28 mValueRequestClassroomKickAllClientsAndroidViewViewOnClickListener;
    private OnClickListenerImpl29 mValueRequestUpdatePermitStatusAskAndroidViewViewOnClickListener;
    private OnClickListenerImpl30 mValueRequestTeacherStopAndroidViewViewOnClickListener;
    private OnClickListenerImpl31 mValueRequestClassRoomAllClientsWorkOnAndroidViewViewOnClickListener;
    private OnClickListenerImpl32 mValueRequestUpdatePermitStatusAlwaysRejectedAndroidViewViewOnClickListener;
    private OnClickListenerImpl33 mValueRequestAnDisplaySettingInfoChangeAndroidViewViewOnClickListener;
    private OnClickListenerImpl34 mValueRequestWorkOnModeMultiAndroidViewViewOnClickListener;
    private OnClickListenerImpl35 mValueRequestWorkOnModeSingleAndroidViewViewOnClickListener;
    private OnClickListenerImpl36 mValueRequestClassroomAllClientsOffScreenAndroidViewViewOnClickListener;
    private OnClickListenerImpl37 mValueRequestServerAuthorizeAndroidViewViewOnClickListener;
    // Inverse Binding Event Handlers

    public ContentsImpl(@Nullable android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 40, sIncludes, sViewsWithIds));
    }
    private ContentsImpl(android.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.Button) bindings[6]
            , (android.widget.Button) bindings[3]
            , (android.widget.Button) bindings[4]
            , (android.widget.EditText) bindings[39]
            , (android.widget.Button) bindings[22]
            , (android.widget.Button) bindings[23]
            , (android.widget.Button) bindings[7]
            , (android.widget.Button) bindings[24]
            , (android.widget.Button) bindings[25]
            , (android.widget.Button) bindings[32]
            , (android.widget.Button) bindings[31]
            , (android.widget.Button) bindings[30]
            , (android.widget.Button) bindings[20]
            , (android.widget.Button) bindings[21]
            , (android.widget.Button) bindings[18]
            , (android.widget.Button) bindings[17]
            , (android.widget.Button) bindings[19]
            , (android.widget.Button) bindings[27]
            , (android.widget.Button) bindings[35]
            , (android.widget.Button) bindings[37]
            , (android.widget.Button) bindings[5]
            , (android.widget.Button) bindings[16]
            , (android.widget.Button) bindings[15]
            , (android.widget.Button) bindings[26]
            , (android.widget.Button) bindings[29]
            , (android.widget.Button) bindings[28]
            , (android.widget.Button) bindings[38]
            , (android.widget.Button) bindings[13]
            , (android.widget.Button) bindings[14]
            , (android.widget.Button) bindings[36]
            , (android.widget.Button) bindings[11]
            , (android.widget.Button) bindings[12]
            , (android.widget.Button) bindings[9]
            , (android.widget.Button) bindings[10]
            , (android.widget.Button) bindings[8]
            , (android.widget.Button) bindings[34]
            , (android.widget.Button) bindings[33]
            , (android.widget.Button) bindings[1]
            , (android.widget.Button) bindings[2]
            );
        this.cancelAuthorize.setTag(null);
        this.connectServerFinder.setTag(null);
        this.disConnectServerFinder.setTag(null);
        this.mboundView0 = (android.widget.ScrollView) bindings[0];
        this.mboundView0.setTag(null);
        this.requestAllClientsLock.setTag(null);
        this.requestAllClientsUnLock.setTag(null);
        this.requestAnDisplayInfo.setTag(null);
        this.requestAnDisplaySettingInfo.setTag(null);
        this.requestAnDisplaySettingInfoChange.setTag(null);
        this.requestAppFiles.setTag(null);
        this.requestAppMark.setTag(null);
        this.requestAppWriter.setTag(null);
        this.requestClassRoomAllClientsWorkOn.setTag(null);
        this.requestClassRoomAllClientsWorkOut.setTag(null);
        this.requestClassroomAllClientsOffScreen.setTag(null);
        this.requestClassroomAllClientsOnScreen.setTag(null);
        this.requestClassroomKickAllClients.setTag(null);
        this.requestClassroomKickClient.setTag(null);
        this.requestDeviceVolume.setTag(null);
        this.requestLockRootDevice.setTag(null);
        this.requestServerAuthorize.setTag(null);
        this.requestSwitchDeviceStateReady.setTag(null);
        this.requestSwitchDeviceStateSharing.setTag(null);
        this.requestSystemInfo.setTag(null);
        this.requestTeacherRecovery.setTag(null);
        this.requestTeacherStop.setTag(null);
        this.requestUnlockRootDevice.setTag(null);
        this.requestUpdateClassRoomWorkOn.setTag(null);
        this.requestUpdateClassRoomWorkOut.setTag(null);
        this.requestUpdateDeviceVolume.setTag(null);
        this.requestUpdateLockStatusLocked.setTag(null);
        this.requestUpdateLockStatusUnLocked.setTag(null);
        this.requestUpdatePermitStatusAlwaysAccepted.setTag(null);
        this.requestUpdatePermitStatusAlwaysRejected.setTag(null);
        this.requestUpdatePermitStatusAsk.setTag(null);
        this.requestWorkOnModeMulti.setTag(null);
        this.requestWorkOnModeSingle.setTag(null);
        this.startServerFinder.setTag(null);
        this.stopServerFinder.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x2L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.value == variableId) {
            setValue((com.bozee.managerapp.WirelessActivity) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setValue(@Nullable com.bozee.managerapp.WirelessActivity Value) {
        this.mValue = Value;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.value);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        android.view.View.OnClickListener valueRequestClassroomAllClientsOnScreenAndroidViewViewOnClickListener = null;
        android.view.View.OnClickListener valueRequestSystemInfoAndroidViewViewOnClickListener = null;
        android.view.View.OnClickListener valueStartServerFinderAndroidViewViewOnClickListener = null;
        android.view.View.OnClickListener valueConnectServerFinderAndroidViewViewOnClickListener = null;
        android.view.View.OnClickListener valueDisConnectServerFinderAndroidViewViewOnClickListener = null;
        android.view.View.OnClickListener valueRequestAnDisplaySettingInfoAndroidViewViewOnClickListener = null;
        android.view.View.OnClickListener valueRequestAllClientsLockAndroidViewViewOnClickListener = null;
        android.view.View.OnClickListener valueRequestAppWriterAndroidViewViewOnClickListener = null;
        android.view.View.OnClickListener valueRequestAppMarkAndroidViewViewOnClickListener = null;
        android.view.View.OnClickListener valueRequestUpdateDeviceVolumeAndroidViewViewOnClickListener = null;
        android.view.View.OnClickListener valueRequestDeviceVolumeAndroidViewViewOnClickListener = null;
        android.view.View.OnClickListener valueRequestClassroomKickClientAndroidViewViewOnClickListener = null;
        android.view.View.OnClickListener valueRequestSwitchDeviceStateReadyAndroidViewViewOnClickListener = null;
        android.view.View.OnClickListener valueRequestClassRoomAllClientsWorkOutAndroidViewViewOnClickListener = null;
        android.view.View.OnClickListener valueStopServerFinderAndroidViewViewOnClickListener = null;
        android.view.View.OnClickListener valueRequestTeacherRecoveryAndroidViewViewOnClickListener = null;
        android.view.View.OnClickListener valueRequestAppFilesAndroidViewViewOnClickListener = null;
        android.view.View.OnClickListener valueRequestAnDisplayInfoAndroidViewViewOnClickListener = null;
        android.view.View.OnClickListener valueRequestUpdateClassRoomWorkOutAndroidViewViewOnClickListener = null;
        android.view.View.OnClickListener valueRequestAllClientsUnLockAndroidViewViewOnClickListener = null;
        android.view.View.OnClickListener valueRequestUpdatePermitStatusAlwaysAcceptedAndroidViewViewOnClickListener = null;
        android.view.View.OnClickListener valueRequestUpdateClassRoomWorkOnAndroidViewViewOnClickListener = null;
        android.view.View.OnClickListener valueRequestSwitchDeviceStateSharingAndroidViewViewOnClickListener = null;
        android.view.View.OnClickListener valueRequestLockRootDeviceAndroidViewViewOnClickListener = null;
        android.view.View.OnClickListener valueRequestUpdateLockStatusLockedAndroidViewViewOnClickListener = null;
        android.view.View.OnClickListener valueCancelAuthorizeAndroidViewViewOnClickListener = null;
        android.view.View.OnClickListener valueRequestUpdateLockStatusUnLockedAndroidViewViewOnClickListener = null;
        android.view.View.OnClickListener valueRequestUnlockRootDeviceAndroidViewViewOnClickListener = null;
        android.view.View.OnClickListener valueRequestClassroomKickAllClientsAndroidViewViewOnClickListener = null;
        android.view.View.OnClickListener valueRequestUpdatePermitStatusAskAndroidViewViewOnClickListener = null;
        android.view.View.OnClickListener valueRequestTeacherStopAndroidViewViewOnClickListener = null;
        com.bozee.managerapp.WirelessActivity value = mValue;
        android.view.View.OnClickListener valueRequestClassRoomAllClientsWorkOnAndroidViewViewOnClickListener = null;
        android.view.View.OnClickListener valueRequestUpdatePermitStatusAlwaysRejectedAndroidViewViewOnClickListener = null;
        android.view.View.OnClickListener valueRequestAnDisplaySettingInfoChangeAndroidViewViewOnClickListener = null;
        android.view.View.OnClickListener valueRequestWorkOnModeMultiAndroidViewViewOnClickListener = null;
        android.view.View.OnClickListener valueRequestWorkOnModeSingleAndroidViewViewOnClickListener = null;
        android.view.View.OnClickListener valueRequestClassroomAllClientsOffScreenAndroidViewViewOnClickListener = null;
        android.view.View.OnClickListener valueRequestServerAuthorizeAndroidViewViewOnClickListener = null;

        if ((dirtyFlags & 0x3L) != 0) {



                if (value != null) {
                    // read value::requestClassroomAllClientsOnScreen
                    valueRequestClassroomAllClientsOnScreenAndroidViewViewOnClickListener = (((mValueRequestClassroomAllClientsOnScreenAndroidViewViewOnClickListener == null) ? (mValueRequestClassroomAllClientsOnScreenAndroidViewViewOnClickListener = new OnClickListenerImpl()) : mValueRequestClassroomAllClientsOnScreenAndroidViewViewOnClickListener).setValue(value));
                    // read value::requestSystemInfo
                    valueRequestSystemInfoAndroidViewViewOnClickListener = (((mValueRequestSystemInfoAndroidViewViewOnClickListener == null) ? (mValueRequestSystemInfoAndroidViewViewOnClickListener = new OnClickListenerImpl1()) : mValueRequestSystemInfoAndroidViewViewOnClickListener).setValue(value));
                    // read value::startServerFinder
                    valueStartServerFinderAndroidViewViewOnClickListener = (((mValueStartServerFinderAndroidViewViewOnClickListener == null) ? (mValueStartServerFinderAndroidViewViewOnClickListener = new OnClickListenerImpl2()) : mValueStartServerFinderAndroidViewViewOnClickListener).setValue(value));
                    // read value::connectServerFinder
                    valueConnectServerFinderAndroidViewViewOnClickListener = (((mValueConnectServerFinderAndroidViewViewOnClickListener == null) ? (mValueConnectServerFinderAndroidViewViewOnClickListener = new OnClickListenerImpl3()) : mValueConnectServerFinderAndroidViewViewOnClickListener).setValue(value));
                    // read value::disConnectServerFinder
                    valueDisConnectServerFinderAndroidViewViewOnClickListener = (((mValueDisConnectServerFinderAndroidViewViewOnClickListener == null) ? (mValueDisConnectServerFinderAndroidViewViewOnClickListener = new OnClickListenerImpl4()) : mValueDisConnectServerFinderAndroidViewViewOnClickListener).setValue(value));
                    // read value::requestAnDisplaySettingInfo
                    valueRequestAnDisplaySettingInfoAndroidViewViewOnClickListener = (((mValueRequestAnDisplaySettingInfoAndroidViewViewOnClickListener == null) ? (mValueRequestAnDisplaySettingInfoAndroidViewViewOnClickListener = new OnClickListenerImpl5()) : mValueRequestAnDisplaySettingInfoAndroidViewViewOnClickListener).setValue(value));
                    // read value::requestAllClientsLock
                    valueRequestAllClientsLockAndroidViewViewOnClickListener = (((mValueRequestAllClientsLockAndroidViewViewOnClickListener == null) ? (mValueRequestAllClientsLockAndroidViewViewOnClickListener = new OnClickListenerImpl6()) : mValueRequestAllClientsLockAndroidViewViewOnClickListener).setValue(value));
                    // read value::requestAppWriter
                    valueRequestAppWriterAndroidViewViewOnClickListener = (((mValueRequestAppWriterAndroidViewViewOnClickListener == null) ? (mValueRequestAppWriterAndroidViewViewOnClickListener = new OnClickListenerImpl7()) : mValueRequestAppWriterAndroidViewViewOnClickListener).setValue(value));
                    // read value::requestAppMark
                    valueRequestAppMarkAndroidViewViewOnClickListener = (((mValueRequestAppMarkAndroidViewViewOnClickListener == null) ? (mValueRequestAppMarkAndroidViewViewOnClickListener = new OnClickListenerImpl8()) : mValueRequestAppMarkAndroidViewViewOnClickListener).setValue(value));
                    // read value::requestUpdateDeviceVolume
                    valueRequestUpdateDeviceVolumeAndroidViewViewOnClickListener = (((mValueRequestUpdateDeviceVolumeAndroidViewViewOnClickListener == null) ? (mValueRequestUpdateDeviceVolumeAndroidViewViewOnClickListener = new OnClickListenerImpl9()) : mValueRequestUpdateDeviceVolumeAndroidViewViewOnClickListener).setValue(value));
                    // read value::requestDeviceVolume
                    valueRequestDeviceVolumeAndroidViewViewOnClickListener = (((mValueRequestDeviceVolumeAndroidViewViewOnClickListener == null) ? (mValueRequestDeviceVolumeAndroidViewViewOnClickListener = new OnClickListenerImpl10()) : mValueRequestDeviceVolumeAndroidViewViewOnClickListener).setValue(value));
                    // read value::requestClassroomKickClient
                    valueRequestClassroomKickClientAndroidViewViewOnClickListener = (((mValueRequestClassroomKickClientAndroidViewViewOnClickListener == null) ? (mValueRequestClassroomKickClientAndroidViewViewOnClickListener = new OnClickListenerImpl11()) : mValueRequestClassroomKickClientAndroidViewViewOnClickListener).setValue(value));
                    // read value::requestSwitchDeviceStateReady
                    valueRequestSwitchDeviceStateReadyAndroidViewViewOnClickListener = (((mValueRequestSwitchDeviceStateReadyAndroidViewViewOnClickListener == null) ? (mValueRequestSwitchDeviceStateReadyAndroidViewViewOnClickListener = new OnClickListenerImpl12()) : mValueRequestSwitchDeviceStateReadyAndroidViewViewOnClickListener).setValue(value));
                    // read value::requestClassRoomAllClientsWorkOut
                    valueRequestClassRoomAllClientsWorkOutAndroidViewViewOnClickListener = (((mValueRequestClassRoomAllClientsWorkOutAndroidViewViewOnClickListener == null) ? (mValueRequestClassRoomAllClientsWorkOutAndroidViewViewOnClickListener = new OnClickListenerImpl13()) : mValueRequestClassRoomAllClientsWorkOutAndroidViewViewOnClickListener).setValue(value));
                    // read value::stopServerFinder
                    valueStopServerFinderAndroidViewViewOnClickListener = (((mValueStopServerFinderAndroidViewViewOnClickListener == null) ? (mValueStopServerFinderAndroidViewViewOnClickListener = new OnClickListenerImpl14()) : mValueStopServerFinderAndroidViewViewOnClickListener).setValue(value));
                    // read value::requestTeacherRecovery
                    valueRequestTeacherRecoveryAndroidViewViewOnClickListener = (((mValueRequestTeacherRecoveryAndroidViewViewOnClickListener == null) ? (mValueRequestTeacherRecoveryAndroidViewViewOnClickListener = new OnClickListenerImpl15()) : mValueRequestTeacherRecoveryAndroidViewViewOnClickListener).setValue(value));
                    // read value::requestAppFiles
                    valueRequestAppFilesAndroidViewViewOnClickListener = (((mValueRequestAppFilesAndroidViewViewOnClickListener == null) ? (mValueRequestAppFilesAndroidViewViewOnClickListener = new OnClickListenerImpl16()) : mValueRequestAppFilesAndroidViewViewOnClickListener).setValue(value));
                    // read value::requestAnDisplayInfo
                    valueRequestAnDisplayInfoAndroidViewViewOnClickListener = (((mValueRequestAnDisplayInfoAndroidViewViewOnClickListener == null) ? (mValueRequestAnDisplayInfoAndroidViewViewOnClickListener = new OnClickListenerImpl17()) : mValueRequestAnDisplayInfoAndroidViewViewOnClickListener).setValue(value));
                    // read value::requestUpdateClassRoomWorkOut
                    valueRequestUpdateClassRoomWorkOutAndroidViewViewOnClickListener = (((mValueRequestUpdateClassRoomWorkOutAndroidViewViewOnClickListener == null) ? (mValueRequestUpdateClassRoomWorkOutAndroidViewViewOnClickListener = new OnClickListenerImpl18()) : mValueRequestUpdateClassRoomWorkOutAndroidViewViewOnClickListener).setValue(value));
                    // read value::requestAllClientsUnLock
                    valueRequestAllClientsUnLockAndroidViewViewOnClickListener = (((mValueRequestAllClientsUnLockAndroidViewViewOnClickListener == null) ? (mValueRequestAllClientsUnLockAndroidViewViewOnClickListener = new OnClickListenerImpl19()) : mValueRequestAllClientsUnLockAndroidViewViewOnClickListener).setValue(value));
                    // read value::requestUpdatePermitStatusAlwaysAccepted
                    valueRequestUpdatePermitStatusAlwaysAcceptedAndroidViewViewOnClickListener = (((mValueRequestUpdatePermitStatusAlwaysAcceptedAndroidViewViewOnClickListener == null) ? (mValueRequestUpdatePermitStatusAlwaysAcceptedAndroidViewViewOnClickListener = new OnClickListenerImpl20()) : mValueRequestUpdatePermitStatusAlwaysAcceptedAndroidViewViewOnClickListener).setValue(value));
                    // read value::requestUpdateClassRoomWorkOn
                    valueRequestUpdateClassRoomWorkOnAndroidViewViewOnClickListener = (((mValueRequestUpdateClassRoomWorkOnAndroidViewViewOnClickListener == null) ? (mValueRequestUpdateClassRoomWorkOnAndroidViewViewOnClickListener = new OnClickListenerImpl21()) : mValueRequestUpdateClassRoomWorkOnAndroidViewViewOnClickListener).setValue(value));
                    // read value::requestSwitchDeviceStateSharing
                    valueRequestSwitchDeviceStateSharingAndroidViewViewOnClickListener = (((mValueRequestSwitchDeviceStateSharingAndroidViewViewOnClickListener == null) ? (mValueRequestSwitchDeviceStateSharingAndroidViewViewOnClickListener = new OnClickListenerImpl22()) : mValueRequestSwitchDeviceStateSharingAndroidViewViewOnClickListener).setValue(value));
                    // read value::requestLockRootDevice
                    valueRequestLockRootDeviceAndroidViewViewOnClickListener = (((mValueRequestLockRootDeviceAndroidViewViewOnClickListener == null) ? (mValueRequestLockRootDeviceAndroidViewViewOnClickListener = new OnClickListenerImpl23()) : mValueRequestLockRootDeviceAndroidViewViewOnClickListener).setValue(value));
                    // read value::requestUpdateLockStatusLocked
                    valueRequestUpdateLockStatusLockedAndroidViewViewOnClickListener = (((mValueRequestUpdateLockStatusLockedAndroidViewViewOnClickListener == null) ? (mValueRequestUpdateLockStatusLockedAndroidViewViewOnClickListener = new OnClickListenerImpl24()) : mValueRequestUpdateLockStatusLockedAndroidViewViewOnClickListener).setValue(value));
                    // read value::cancelAuthorize
                    valueCancelAuthorizeAndroidViewViewOnClickListener = (((mValueCancelAuthorizeAndroidViewViewOnClickListener == null) ? (mValueCancelAuthorizeAndroidViewViewOnClickListener = new OnClickListenerImpl25()) : mValueCancelAuthorizeAndroidViewViewOnClickListener).setValue(value));
                    // read value::requestUpdateLockStatusUnLocked
                    valueRequestUpdateLockStatusUnLockedAndroidViewViewOnClickListener = (((mValueRequestUpdateLockStatusUnLockedAndroidViewViewOnClickListener == null) ? (mValueRequestUpdateLockStatusUnLockedAndroidViewViewOnClickListener = new OnClickListenerImpl26()) : mValueRequestUpdateLockStatusUnLockedAndroidViewViewOnClickListener).setValue(value));
                    // read value::requestUnlockRootDevice
                    valueRequestUnlockRootDeviceAndroidViewViewOnClickListener = (((mValueRequestUnlockRootDeviceAndroidViewViewOnClickListener == null) ? (mValueRequestUnlockRootDeviceAndroidViewViewOnClickListener = new OnClickListenerImpl27()) : mValueRequestUnlockRootDeviceAndroidViewViewOnClickListener).setValue(value));
                    // read value::requestClassroomKickAllClients
                    valueRequestClassroomKickAllClientsAndroidViewViewOnClickListener = (((mValueRequestClassroomKickAllClientsAndroidViewViewOnClickListener == null) ? (mValueRequestClassroomKickAllClientsAndroidViewViewOnClickListener = new OnClickListenerImpl28()) : mValueRequestClassroomKickAllClientsAndroidViewViewOnClickListener).setValue(value));
                    // read value::requestUpdatePermitStatusAsk
                    valueRequestUpdatePermitStatusAskAndroidViewViewOnClickListener = (((mValueRequestUpdatePermitStatusAskAndroidViewViewOnClickListener == null) ? (mValueRequestUpdatePermitStatusAskAndroidViewViewOnClickListener = new OnClickListenerImpl29()) : mValueRequestUpdatePermitStatusAskAndroidViewViewOnClickListener).setValue(value));
                    // read value::requestTeacherStop
                    valueRequestTeacherStopAndroidViewViewOnClickListener = (((mValueRequestTeacherStopAndroidViewViewOnClickListener == null) ? (mValueRequestTeacherStopAndroidViewViewOnClickListener = new OnClickListenerImpl30()) : mValueRequestTeacherStopAndroidViewViewOnClickListener).setValue(value));
                    // read value::requestClassRoomAllClientsWorkOn
                    valueRequestClassRoomAllClientsWorkOnAndroidViewViewOnClickListener = (((mValueRequestClassRoomAllClientsWorkOnAndroidViewViewOnClickListener == null) ? (mValueRequestClassRoomAllClientsWorkOnAndroidViewViewOnClickListener = new OnClickListenerImpl31()) : mValueRequestClassRoomAllClientsWorkOnAndroidViewViewOnClickListener).setValue(value));
                    // read value::requestUpdatePermitStatusAlwaysRejected
                    valueRequestUpdatePermitStatusAlwaysRejectedAndroidViewViewOnClickListener = (((mValueRequestUpdatePermitStatusAlwaysRejectedAndroidViewViewOnClickListener == null) ? (mValueRequestUpdatePermitStatusAlwaysRejectedAndroidViewViewOnClickListener = new OnClickListenerImpl32()) : mValueRequestUpdatePermitStatusAlwaysRejectedAndroidViewViewOnClickListener).setValue(value));
                    // read value::requestAnDisplaySettingInfoChange
                    valueRequestAnDisplaySettingInfoChangeAndroidViewViewOnClickListener = (((mValueRequestAnDisplaySettingInfoChangeAndroidViewViewOnClickListener == null) ? (mValueRequestAnDisplaySettingInfoChangeAndroidViewViewOnClickListener = new OnClickListenerImpl33()) : mValueRequestAnDisplaySettingInfoChangeAndroidViewViewOnClickListener).setValue(value));
                    // read value::requestWorkOnModeMulti
                    valueRequestWorkOnModeMultiAndroidViewViewOnClickListener = (((mValueRequestWorkOnModeMultiAndroidViewViewOnClickListener == null) ? (mValueRequestWorkOnModeMultiAndroidViewViewOnClickListener = new OnClickListenerImpl34()) : mValueRequestWorkOnModeMultiAndroidViewViewOnClickListener).setValue(value));
                    // read value::requestWorkOnModeSingle
                    valueRequestWorkOnModeSingleAndroidViewViewOnClickListener = (((mValueRequestWorkOnModeSingleAndroidViewViewOnClickListener == null) ? (mValueRequestWorkOnModeSingleAndroidViewViewOnClickListener = new OnClickListenerImpl35()) : mValueRequestWorkOnModeSingleAndroidViewViewOnClickListener).setValue(value));
                    // read value::requestClassroomAllClientsOffScreen
                    valueRequestClassroomAllClientsOffScreenAndroidViewViewOnClickListener = (((mValueRequestClassroomAllClientsOffScreenAndroidViewViewOnClickListener == null) ? (mValueRequestClassroomAllClientsOffScreenAndroidViewViewOnClickListener = new OnClickListenerImpl36()) : mValueRequestClassroomAllClientsOffScreenAndroidViewViewOnClickListener).setValue(value));
                    // read value::requestServerAuthorize
                    valueRequestServerAuthorizeAndroidViewViewOnClickListener = (((mValueRequestServerAuthorizeAndroidViewViewOnClickListener == null) ? (mValueRequestServerAuthorizeAndroidViewViewOnClickListener = new OnClickListenerImpl37()) : mValueRequestServerAuthorizeAndroidViewViewOnClickListener).setValue(value));
                }
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            this.cancelAuthorize.setOnClickListener(valueCancelAuthorizeAndroidViewViewOnClickListener);
            this.connectServerFinder.setOnClickListener(valueConnectServerFinderAndroidViewViewOnClickListener);
            this.disConnectServerFinder.setOnClickListener(valueDisConnectServerFinderAndroidViewViewOnClickListener);
            this.requestAllClientsLock.setOnClickListener(valueRequestAllClientsLockAndroidViewViewOnClickListener);
            this.requestAllClientsUnLock.setOnClickListener(valueRequestAllClientsUnLockAndroidViewViewOnClickListener);
            this.requestAnDisplayInfo.setOnClickListener(valueRequestAnDisplayInfoAndroidViewViewOnClickListener);
            this.requestAnDisplaySettingInfo.setOnClickListener(valueRequestAnDisplaySettingInfoAndroidViewViewOnClickListener);
            this.requestAnDisplaySettingInfoChange.setOnClickListener(valueRequestAnDisplaySettingInfoChangeAndroidViewViewOnClickListener);
            this.requestAppFiles.setOnClickListener(valueRequestAppFilesAndroidViewViewOnClickListener);
            this.requestAppMark.setOnClickListener(valueRequestAppMarkAndroidViewViewOnClickListener);
            this.requestAppWriter.setOnClickListener(valueRequestAppWriterAndroidViewViewOnClickListener);
            this.requestClassRoomAllClientsWorkOn.setOnClickListener(valueRequestClassRoomAllClientsWorkOnAndroidViewViewOnClickListener);
            this.requestClassRoomAllClientsWorkOut.setOnClickListener(valueRequestClassRoomAllClientsWorkOutAndroidViewViewOnClickListener);
            this.requestClassroomAllClientsOffScreen.setOnClickListener(valueRequestClassroomAllClientsOffScreenAndroidViewViewOnClickListener);
            this.requestClassroomAllClientsOnScreen.setOnClickListener(valueRequestClassroomAllClientsOnScreenAndroidViewViewOnClickListener);
            this.requestClassroomKickAllClients.setOnClickListener(valueRequestClassroomKickAllClientsAndroidViewViewOnClickListener);
            this.requestClassroomKickClient.setOnClickListener(valueRequestClassroomKickClientAndroidViewViewOnClickListener);
            this.requestDeviceVolume.setOnClickListener(valueRequestDeviceVolumeAndroidViewViewOnClickListener);
            this.requestLockRootDevice.setOnClickListener(valueRequestLockRootDeviceAndroidViewViewOnClickListener);
            this.requestServerAuthorize.setOnClickListener(valueRequestServerAuthorizeAndroidViewViewOnClickListener);
            this.requestSwitchDeviceStateReady.setOnClickListener(valueRequestSwitchDeviceStateReadyAndroidViewViewOnClickListener);
            this.requestSwitchDeviceStateSharing.setOnClickListener(valueRequestSwitchDeviceStateSharingAndroidViewViewOnClickListener);
            this.requestSystemInfo.setOnClickListener(valueRequestSystemInfoAndroidViewViewOnClickListener);
            this.requestTeacherRecovery.setOnClickListener(valueRequestTeacherRecoveryAndroidViewViewOnClickListener);
            this.requestTeacherStop.setOnClickListener(valueRequestTeacherStopAndroidViewViewOnClickListener);
            this.requestUnlockRootDevice.setOnClickListener(valueRequestUnlockRootDeviceAndroidViewViewOnClickListener);
            this.requestUpdateClassRoomWorkOn.setOnClickListener(valueRequestUpdateClassRoomWorkOnAndroidViewViewOnClickListener);
            this.requestUpdateClassRoomWorkOut.setOnClickListener(valueRequestUpdateClassRoomWorkOutAndroidViewViewOnClickListener);
            this.requestUpdateDeviceVolume.setOnClickListener(valueRequestUpdateDeviceVolumeAndroidViewViewOnClickListener);
            this.requestUpdateLockStatusLocked.setOnClickListener(valueRequestUpdateLockStatusLockedAndroidViewViewOnClickListener);
            this.requestUpdateLockStatusUnLocked.setOnClickListener(valueRequestUpdateLockStatusUnLockedAndroidViewViewOnClickListener);
            this.requestUpdatePermitStatusAlwaysAccepted.setOnClickListener(valueRequestUpdatePermitStatusAlwaysAcceptedAndroidViewViewOnClickListener);
            this.requestUpdatePermitStatusAlwaysRejected.setOnClickListener(valueRequestUpdatePermitStatusAlwaysRejectedAndroidViewViewOnClickListener);
            this.requestUpdatePermitStatusAsk.setOnClickListener(valueRequestUpdatePermitStatusAskAndroidViewViewOnClickListener);
            this.requestWorkOnModeMulti.setOnClickListener(valueRequestWorkOnModeMultiAndroidViewViewOnClickListener);
            this.requestWorkOnModeSingle.setOnClickListener(valueRequestWorkOnModeSingleAndroidViewViewOnClickListener);
            this.startServerFinder.setOnClickListener(valueStartServerFinderAndroidViewViewOnClickListener);
            this.stopServerFinder.setOnClickListener(valueStopServerFinderAndroidViewViewOnClickListener);
        }
    }
    // Listener Stub Implementations
    public static class OnClickListenerImpl implements android.view.View.OnClickListener{
        private com.bozee.managerapp.WirelessActivity value;
        public OnClickListenerImpl setValue(com.bozee.managerapp.WirelessActivity value) {
            this.value = value;
            return value == null ? null : this;
        }
        @Override
        public void onClick(android.view.View arg0) {
            this.value.requestClassroomAllClientsOnScreen(arg0); 
        }
    }
    public static class OnClickListenerImpl1 implements android.view.View.OnClickListener{
        private com.bozee.managerapp.WirelessActivity value;
        public OnClickListenerImpl1 setValue(com.bozee.managerapp.WirelessActivity value) {
            this.value = value;
            return value == null ? null : this;
        }
        @Override
        public void onClick(android.view.View arg0) {
            this.value.requestSystemInfo(arg0); 
        }
    }
    public static class OnClickListenerImpl2 implements android.view.View.OnClickListener{
        private com.bozee.managerapp.WirelessActivity value;
        public OnClickListenerImpl2 setValue(com.bozee.managerapp.WirelessActivity value) {
            this.value = value;
            return value == null ? null : this;
        }
        @Override
        public void onClick(android.view.View arg0) {
            this.value.startServerFinder(arg0); 
        }
    }
    public static class OnClickListenerImpl3 implements android.view.View.OnClickListener{
        private com.bozee.managerapp.WirelessActivity value;
        public OnClickListenerImpl3 setValue(com.bozee.managerapp.WirelessActivity value) {
            this.value = value;
            return value == null ? null : this;
        }
        @Override
        public void onClick(android.view.View arg0) {
            this.value.connectServerFinder(arg0); 
        }
    }
    public static class OnClickListenerImpl4 implements android.view.View.OnClickListener{
        private com.bozee.managerapp.WirelessActivity value;
        public OnClickListenerImpl4 setValue(com.bozee.managerapp.WirelessActivity value) {
            this.value = value;
            return value == null ? null : this;
        }
        @Override
        public void onClick(android.view.View arg0) {
            this.value.disConnectServerFinder(arg0); 
        }
    }
    public static class OnClickListenerImpl5 implements android.view.View.OnClickListener{
        private com.bozee.managerapp.WirelessActivity value;
        public OnClickListenerImpl5 setValue(com.bozee.managerapp.WirelessActivity value) {
            this.value = value;
            return value == null ? null : this;
        }
        @Override
        public void onClick(android.view.View arg0) {
            this.value.requestAnDisplaySettingInfo(arg0); 
        }
    }
    public static class OnClickListenerImpl6 implements android.view.View.OnClickListener{
        private com.bozee.managerapp.WirelessActivity value;
        public OnClickListenerImpl6 setValue(com.bozee.managerapp.WirelessActivity value) {
            this.value = value;
            return value == null ? null : this;
        }
        @Override
        public void onClick(android.view.View arg0) {
            this.value.requestAllClientsLock(arg0); 
        }
    }
    public static class OnClickListenerImpl7 implements android.view.View.OnClickListener{
        private com.bozee.managerapp.WirelessActivity value;
        public OnClickListenerImpl7 setValue(com.bozee.managerapp.WirelessActivity value) {
            this.value = value;
            return value == null ? null : this;
        }
        @Override
        public void onClick(android.view.View arg0) {
            this.value.requestAppWriter(arg0); 
        }
    }
    public static class OnClickListenerImpl8 implements android.view.View.OnClickListener{
        private com.bozee.managerapp.WirelessActivity value;
        public OnClickListenerImpl8 setValue(com.bozee.managerapp.WirelessActivity value) {
            this.value = value;
            return value == null ? null : this;
        }
        @Override
        public void onClick(android.view.View arg0) {
            this.value.requestAppMark(arg0); 
        }
    }
    public static class OnClickListenerImpl9 implements android.view.View.OnClickListener{
        private com.bozee.managerapp.WirelessActivity value;
        public OnClickListenerImpl9 setValue(com.bozee.managerapp.WirelessActivity value) {
            this.value = value;
            return value == null ? null : this;
        }
        @Override
        public void onClick(android.view.View arg0) {
            this.value.requestUpdateDeviceVolume(arg0); 
        }
    }
    public static class OnClickListenerImpl10 implements android.view.View.OnClickListener{
        private com.bozee.managerapp.WirelessActivity value;
        public OnClickListenerImpl10 setValue(com.bozee.managerapp.WirelessActivity value) {
            this.value = value;
            return value == null ? null : this;
        }
        @Override
        public void onClick(android.view.View arg0) {
            this.value.requestDeviceVolume(arg0); 
        }
    }
    public static class OnClickListenerImpl11 implements android.view.View.OnClickListener{
        private com.bozee.managerapp.WirelessActivity value;
        public OnClickListenerImpl11 setValue(com.bozee.managerapp.WirelessActivity value) {
            this.value = value;
            return value == null ? null : this;
        }
        @Override
        public void onClick(android.view.View arg0) {
            this.value.requestClassroomKickClient(arg0); 
        }
    }
    public static class OnClickListenerImpl12 implements android.view.View.OnClickListener{
        private com.bozee.managerapp.WirelessActivity value;
        public OnClickListenerImpl12 setValue(com.bozee.managerapp.WirelessActivity value) {
            this.value = value;
            return value == null ? null : this;
        }
        @Override
        public void onClick(android.view.View arg0) {
            this.value.requestSwitchDeviceStateReady(arg0); 
        }
    }
    public static class OnClickListenerImpl13 implements android.view.View.OnClickListener{
        private com.bozee.managerapp.WirelessActivity value;
        public OnClickListenerImpl13 setValue(com.bozee.managerapp.WirelessActivity value) {
            this.value = value;
            return value == null ? null : this;
        }
        @Override
        public void onClick(android.view.View arg0) {
            this.value.requestClassRoomAllClientsWorkOut(arg0); 
        }
    }
    public static class OnClickListenerImpl14 implements android.view.View.OnClickListener{
        private com.bozee.managerapp.WirelessActivity value;
        public OnClickListenerImpl14 setValue(com.bozee.managerapp.WirelessActivity value) {
            this.value = value;
            return value == null ? null : this;
        }
        @Override
        public void onClick(android.view.View arg0) {
            this.value.stopServerFinder(arg0); 
        }
    }
    public static class OnClickListenerImpl15 implements android.view.View.OnClickListener{
        private com.bozee.managerapp.WirelessActivity value;
        public OnClickListenerImpl15 setValue(com.bozee.managerapp.WirelessActivity value) {
            this.value = value;
            return value == null ? null : this;
        }
        @Override
        public void onClick(android.view.View arg0) {
            this.value.requestTeacherRecovery(arg0); 
        }
    }
    public static class OnClickListenerImpl16 implements android.view.View.OnClickListener{
        private com.bozee.managerapp.WirelessActivity value;
        public OnClickListenerImpl16 setValue(com.bozee.managerapp.WirelessActivity value) {
            this.value = value;
            return value == null ? null : this;
        }
        @Override
        public void onClick(android.view.View arg0) {
            this.value.requestAppFiles(arg0); 
        }
    }
    public static class OnClickListenerImpl17 implements android.view.View.OnClickListener{
        private com.bozee.managerapp.WirelessActivity value;
        public OnClickListenerImpl17 setValue(com.bozee.managerapp.WirelessActivity value) {
            this.value = value;
            return value == null ? null : this;
        }
        @Override
        public void onClick(android.view.View arg0) {
            this.value.requestAnDisplayInfo(arg0); 
        }
    }
    public static class OnClickListenerImpl18 implements android.view.View.OnClickListener{
        private com.bozee.managerapp.WirelessActivity value;
        public OnClickListenerImpl18 setValue(com.bozee.managerapp.WirelessActivity value) {
            this.value = value;
            return value == null ? null : this;
        }
        @Override
        public void onClick(android.view.View arg0) {
            this.value.requestUpdateClassRoomWorkOut(arg0); 
        }
    }
    public static class OnClickListenerImpl19 implements android.view.View.OnClickListener{
        private com.bozee.managerapp.WirelessActivity value;
        public OnClickListenerImpl19 setValue(com.bozee.managerapp.WirelessActivity value) {
            this.value = value;
            return value == null ? null : this;
        }
        @Override
        public void onClick(android.view.View arg0) {
            this.value.requestAllClientsUnLock(arg0); 
        }
    }
    public static class OnClickListenerImpl20 implements android.view.View.OnClickListener{
        private com.bozee.managerapp.WirelessActivity value;
        public OnClickListenerImpl20 setValue(com.bozee.managerapp.WirelessActivity value) {
            this.value = value;
            return value == null ? null : this;
        }
        @Override
        public void onClick(android.view.View arg0) {
            this.value.requestUpdatePermitStatusAlwaysAccepted(arg0); 
        }
    }
    public static class OnClickListenerImpl21 implements android.view.View.OnClickListener{
        private com.bozee.managerapp.WirelessActivity value;
        public OnClickListenerImpl21 setValue(com.bozee.managerapp.WirelessActivity value) {
            this.value = value;
            return value == null ? null : this;
        }
        @Override
        public void onClick(android.view.View arg0) {
            this.value.requestUpdateClassRoomWorkOn(arg0); 
        }
    }
    public static class OnClickListenerImpl22 implements android.view.View.OnClickListener{
        private com.bozee.managerapp.WirelessActivity value;
        public OnClickListenerImpl22 setValue(com.bozee.managerapp.WirelessActivity value) {
            this.value = value;
            return value == null ? null : this;
        }
        @Override
        public void onClick(android.view.View arg0) {
            this.value.requestSwitchDeviceStateSharing(arg0); 
        }
    }
    public static class OnClickListenerImpl23 implements android.view.View.OnClickListener{
        private com.bozee.managerapp.WirelessActivity value;
        public OnClickListenerImpl23 setValue(com.bozee.managerapp.WirelessActivity value) {
            this.value = value;
            return value == null ? null : this;
        }
        @Override
        public void onClick(android.view.View arg0) {
            this.value.requestLockRootDevice(arg0); 
        }
    }
    public static class OnClickListenerImpl24 implements android.view.View.OnClickListener{
        private com.bozee.managerapp.WirelessActivity value;
        public OnClickListenerImpl24 setValue(com.bozee.managerapp.WirelessActivity value) {
            this.value = value;
            return value == null ? null : this;
        }
        @Override
        public void onClick(android.view.View arg0) {
            this.value.requestUpdateLockStatusLocked(arg0); 
        }
    }
    public static class OnClickListenerImpl25 implements android.view.View.OnClickListener{
        private com.bozee.managerapp.WirelessActivity value;
        public OnClickListenerImpl25 setValue(com.bozee.managerapp.WirelessActivity value) {
            this.value = value;
            return value == null ? null : this;
        }
        @Override
        public void onClick(android.view.View arg0) {
            this.value.cancelAuthorize(arg0); 
        }
    }
    public static class OnClickListenerImpl26 implements android.view.View.OnClickListener{
        private com.bozee.managerapp.WirelessActivity value;
        public OnClickListenerImpl26 setValue(com.bozee.managerapp.WirelessActivity value) {
            this.value = value;
            return value == null ? null : this;
        }
        @Override
        public void onClick(android.view.View arg0) {
            this.value.requestUpdateLockStatusUnLocked(arg0); 
        }
    }
    public static class OnClickListenerImpl27 implements android.view.View.OnClickListener{
        private com.bozee.managerapp.WirelessActivity value;
        public OnClickListenerImpl27 setValue(com.bozee.managerapp.WirelessActivity value) {
            this.value = value;
            return value == null ? null : this;
        }
        @Override
        public void onClick(android.view.View arg0) {
            this.value.requestUnlockRootDevice(arg0); 
        }
    }
    public static class OnClickListenerImpl28 implements android.view.View.OnClickListener{
        private com.bozee.managerapp.WirelessActivity value;
        public OnClickListenerImpl28 setValue(com.bozee.managerapp.WirelessActivity value) {
            this.value = value;
            return value == null ? null : this;
        }
        @Override
        public void onClick(android.view.View arg0) {
            this.value.requestClassroomKickAllClients(arg0); 
        }
    }
    public static class OnClickListenerImpl29 implements android.view.View.OnClickListener{
        private com.bozee.managerapp.WirelessActivity value;
        public OnClickListenerImpl29 setValue(com.bozee.managerapp.WirelessActivity value) {
            this.value = value;
            return value == null ? null : this;
        }
        @Override
        public void onClick(android.view.View arg0) {
            this.value.requestUpdatePermitStatusAsk(arg0); 
        }
    }
    public static class OnClickListenerImpl30 implements android.view.View.OnClickListener{
        private com.bozee.managerapp.WirelessActivity value;
        public OnClickListenerImpl30 setValue(com.bozee.managerapp.WirelessActivity value) {
            this.value = value;
            return value == null ? null : this;
        }
        @Override
        public void onClick(android.view.View arg0) {
            this.value.requestTeacherStop(arg0); 
        }
    }
    public static class OnClickListenerImpl31 implements android.view.View.OnClickListener{
        private com.bozee.managerapp.WirelessActivity value;
        public OnClickListenerImpl31 setValue(com.bozee.managerapp.WirelessActivity value) {
            this.value = value;
            return value == null ? null : this;
        }
        @Override
        public void onClick(android.view.View arg0) {
            this.value.requestClassRoomAllClientsWorkOn(arg0); 
        }
    }
    public static class OnClickListenerImpl32 implements android.view.View.OnClickListener{
        private com.bozee.managerapp.WirelessActivity value;
        public OnClickListenerImpl32 setValue(com.bozee.managerapp.WirelessActivity value) {
            this.value = value;
            return value == null ? null : this;
        }
        @Override
        public void onClick(android.view.View arg0) {
            this.value.requestUpdatePermitStatusAlwaysRejected(arg0); 
        }
    }
    public static class OnClickListenerImpl33 implements android.view.View.OnClickListener{
        private com.bozee.managerapp.WirelessActivity value;
        public OnClickListenerImpl33 setValue(com.bozee.managerapp.WirelessActivity value) {
            this.value = value;
            return value == null ? null : this;
        }
        @Override
        public void onClick(android.view.View arg0) {
            this.value.requestAnDisplaySettingInfoChange(arg0); 
        }
    }
    public static class OnClickListenerImpl34 implements android.view.View.OnClickListener{
        private com.bozee.managerapp.WirelessActivity value;
        public OnClickListenerImpl34 setValue(com.bozee.managerapp.WirelessActivity value) {
            this.value = value;
            return value == null ? null : this;
        }
        @Override
        public void onClick(android.view.View arg0) {
            this.value.requestWorkOnModeMulti(arg0); 
        }
    }
    public static class OnClickListenerImpl35 implements android.view.View.OnClickListener{
        private com.bozee.managerapp.WirelessActivity value;
        public OnClickListenerImpl35 setValue(com.bozee.managerapp.WirelessActivity value) {
            this.value = value;
            return value == null ? null : this;
        }
        @Override
        public void onClick(android.view.View arg0) {
            this.value.requestWorkOnModeSingle(arg0); 
        }
    }
    public static class OnClickListenerImpl36 implements android.view.View.OnClickListener{
        private com.bozee.managerapp.WirelessActivity value;
        public OnClickListenerImpl36 setValue(com.bozee.managerapp.WirelessActivity value) {
            this.value = value;
            return value == null ? null : this;
        }
        @Override
        public void onClick(android.view.View arg0) {
            this.value.requestClassroomAllClientsOffScreen(arg0); 
        }
    }
    public static class OnClickListenerImpl37 implements android.view.View.OnClickListener{
        private com.bozee.managerapp.WirelessActivity value;
        public OnClickListenerImpl37 setValue(com.bozee.managerapp.WirelessActivity value) {
            this.value = value;
            return value == null ? null : this;
        }
        @Override
        public void onClick(android.view.View arg0) {
            this.value.requestServerAuthorize(arg0); 
        }
    }
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): value
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}