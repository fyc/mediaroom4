package com.sunmnet.mediaroom.tabsp.databinding;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class DoorInfoBindingLandImpl extends DoorInfoBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.door_image, 2);
        sViewsWithIds.put(R.id.door_magnetic_status, 3);
        sViewsWithIds.put(R.id.device_door_megnet_name, 4);
        sViewsWithIds.put(R.id.device_state, 5);
        sViewsWithIds.put(R.id.door_lock_status, 6);
        sViewsWithIds.put(R.id.device_door_lockName, 7);
        sViewsWithIds.put(R.id.door_operating, 8);
        sViewsWithIds.put(R.id.device_doorlock_state, 9);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public DoorInfoBindingLandImpl(@Nullable android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 10, sIncludes, sViewsWithIds));
    }
    private DoorInfoBindingLandImpl(android.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (android.widget.TextView) bindings[7]
            , (android.widget.TextView) bindings[4]
            , (android.widget.ImageView) bindings[9]
            , (android.widget.TextView) bindings[1]
            , (android.widget.TextView) bindings[5]
            , (android.widget.ImageView) bindings[2]
            , (android.widget.ImageView) bindings[6]
            , (android.widget.ImageView) bindings[3]
            , (android.widget.ProgressBar) bindings[8]
            );
        this.deviceName.setTag(null);
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
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
        if (BR.doorInfo == variableId) {
            setDoorInfo((com.sunmnet.mediaroom.device.bean.AbstractDevice) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setDoorInfo(@Nullable com.sunmnet.mediaroom.device.bean.AbstractDevice DoorInfo) {
        updateRegistration(0, DoorInfo);
        this.mDoorInfo = DoorInfo;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.doorInfo);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeDoorInfo((com.sunmnet.mediaroom.device.bean.AbstractDevice) object, fieldId);
        }
        return false;
    }
    private boolean onChangeDoorInfo(com.sunmnet.mediaroom.device.bean.AbstractDevice DoorInfo, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
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
        java.lang.String doorInfoGetDeviceName = null;
        com.sunmnet.mediaroom.device.bean.AbstractDevice<?> doorInfo = mDoorInfo;

        if ((dirtyFlags & 0x3L) != 0) {



                if (doorInfo != null) {
                    // read doorInfo.getDeviceName()
                    doorInfoGetDeviceName = doorInfo.getDeviceName();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            android.databinding.adapters.TextViewBindingAdapter.setText(this.deviceName, doorInfoGetDeviceName);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): doorInfo
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}