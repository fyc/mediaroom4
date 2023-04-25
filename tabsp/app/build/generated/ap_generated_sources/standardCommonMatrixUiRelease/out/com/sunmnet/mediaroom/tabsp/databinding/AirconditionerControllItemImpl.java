package com.sunmnet.mediaroom.tabsp.databinding;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class AirconditionerControllItemImpl extends AirconditionerControllItem  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.airconditioner_opt, 2);
        sViewsWithIds.put(R.id.single_open, 3);
        sViewsWithIds.put(R.id.single_close, 4);
        sViewsWithIds.put(R.id.volumn_minus_btn, 5);
        sViewsWithIds.put(R.id.tempture_value, 6);
        sViewsWithIds.put(R.id.volumn_plus_btn, 7);
        sViewsWithIds.put(R.id.modes, 8);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public AirconditionerControllItemImpl(@Nullable android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 9, sIncludes, sViewsWithIds));
    }
    private AirconditionerControllItemImpl(android.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (android.widget.LinearLayout) bindings[2]
            , (android.widget.TextView) bindings[1]
            , (android.widget.LinearLayout) bindings[8]
            , (android.widget.Button) bindings[4]
            , (android.widget.Button) bindings[3]
            , (android.widget.TextView) bindings[6]
            , (android.widget.FrameLayout) bindings[5]
            , (android.widget.FrameLayout) bindings[7]
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
                mDirtyFlags = 0x8L;
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
        if (BR.settingAdapter == variableId) {
            setSettingAdapter((com.sunmnet.mediaroom.tabsp.ui.adapter.BindingAdapter) variable);
        }
        else if (BR.setting == variableId) {
            setSetting((com.sunmnet.mediaroom.device.bean.Airconditioner.Setting) variable);
        }
        else if (BR.dev == variableId) {
            setDev((com.sunmnet.mediaroom.device.bean.Airconditioner) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setSettingAdapter(@Nullable com.sunmnet.mediaroom.tabsp.ui.adapter.BindingAdapter SettingAdapter) {
        this.mSettingAdapter = SettingAdapter;
    }
    public void setSetting(@Nullable com.sunmnet.mediaroom.device.bean.Airconditioner.Setting Setting) {
        this.mSetting = Setting;
    }
    public void setDev(@Nullable com.sunmnet.mediaroom.device.bean.Airconditioner Dev) {
        updateRegistration(0, Dev);
        this.mDev = Dev;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.dev);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeDev((com.sunmnet.mediaroom.device.bean.Airconditioner) object, fieldId);
        }
        return false;
    }
    private boolean onChangeDev(com.sunmnet.mediaroom.device.bean.Airconditioner Dev, int fieldId) {
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
        java.lang.String devGetDeviceName = null;
        com.sunmnet.mediaroom.device.bean.Airconditioner dev = mDev;

        if ((dirtyFlags & 0x9L) != 0) {



                if (dev != null) {
                    // read dev.getDeviceName()
                    devGetDeviceName = dev.getDeviceName();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x9L) != 0) {
            // api target 1

            android.databinding.adapters.TextViewBindingAdapter.setText(this.deviceName, devGetDeviceName);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): dev
        flag 1 (0x2L): settingAdapter
        flag 2 (0x3L): setting
        flag 3 (0x4L): null
    flag mapping end*/
    //end
}