package com.sunmnet.mediaroom.tabsp.databinding;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class DoorBindingLandImpl extends DoorBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.door_title, 2);
        sViewsWithIds.put(R.id.device_img, 3);
        sViewsWithIds.put(R.id.door_mag, 4);
        sViewsWithIds.put(R.id.door_mag_img, 5);
        sViewsWithIds.put(R.id.magnetic_state, 6);
        sViewsWithIds.put(R.id.door_lock, 7);
        sViewsWithIds.put(R.id.door_lock_img, 8);
        sViewsWithIds.put(R.id.switcher_doorlock, 9);
        sViewsWithIds.put(R.id.doorProcessing, 10);
    }
    // views
    @NonNull
    private final android.support.constraint.ConstraintLayout mboundView0;
    @NonNull
    private final android.widget.TextView mboundView1;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public DoorBindingLandImpl(@Nullable android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds));
    }
    private DoorBindingLandImpl(android.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (android.widget.ImageView) bindings[3]
            , (android.widget.LinearLayout) bindings[7]
            , (android.widget.ImageView) bindings[8]
            , (android.widget.LinearLayout) bindings[4]
            , (android.widget.ImageView) bindings[5]
            , (android.widget.ProgressBar) bindings[10]
            , (android.widget.LinearLayout) bindings[2]
            , (android.widget.TextView) bindings[6]
            , (android.widget.Switch) bindings[9]
            );
        this.mboundView0 = (android.support.constraint.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView1 = (android.widget.TextView) bindings[1];
        this.mboundView1.setTag(null);
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
        if (BR.device == variableId) {
            setDevice((com.sunmnet.mediaroom.device.bean.AbstractDevice) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setDevice(@Nullable com.sunmnet.mediaroom.device.bean.AbstractDevice Device) {
        updateRegistration(0, Device);
        this.mDevice = Device;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.device);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeDevice((com.sunmnet.mediaroom.device.bean.AbstractDevice) object, fieldId);
        }
        return false;
    }
    private boolean onChangeDevice(com.sunmnet.mediaroom.device.bean.AbstractDevice Device, int fieldId) {
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
        java.lang.String deviceDeviceName = null;
        com.sunmnet.mediaroom.device.bean.AbstractDevice<?> device = mDevice;

        if ((dirtyFlags & 0x3L) != 0) {



                if (device != null) {
                    // read device.deviceName
                    deviceDeviceName = device.getDeviceName();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            android.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView1, deviceDeviceName);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): device
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}