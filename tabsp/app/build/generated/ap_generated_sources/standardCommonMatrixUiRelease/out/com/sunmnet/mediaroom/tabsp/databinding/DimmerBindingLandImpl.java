package com.sunmnet.mediaroom.tabsp.databinding;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class DimmerBindingLandImpl extends DimmerBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.device_image_holder, 2);
        sViewsWithIds.put(R.id.device_img, 3);
        sViewsWithIds.put(R.id.controll_processing, 4);
        sViewsWithIds.put(R.id.dimmer, 5);
        sViewsWithIds.put(R.id.dimmer_minus_frame, 6);
        sViewsWithIds.put(R.id.dimmer_minus, 7);
        sViewsWithIds.put(R.id.dimmer_power, 8);
        sViewsWithIds.put(R.id.dimmer_plus_frame, 9);
        sViewsWithIds.put(R.id.dimmer_plus, 10);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public DimmerBindingLandImpl(@Nullable android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds));
    }
    private DimmerBindingLandImpl(android.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (android.widget.ProgressBar) bindings[4]
            , (android.widget.FrameLayout) bindings[2]
            , (android.widget.ImageView) bindings[3]
            , (android.widget.TextView) bindings[1]
            , (android.widget.LinearLayout) bindings[5]
            , (android.widget.ImageView) bindings[7]
            , (android.widget.LinearLayout) bindings[6]
            , (android.widget.ImageView) bindings[10]
            , (android.widget.LinearLayout) bindings[9]
            , (com.sunmnet.mediaroom.tabsp.ui.widget.DimmerPowerView) bindings[8]
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

            android.databinding.adapters.TextViewBindingAdapter.setText(this.deviceName, deviceDeviceName);
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