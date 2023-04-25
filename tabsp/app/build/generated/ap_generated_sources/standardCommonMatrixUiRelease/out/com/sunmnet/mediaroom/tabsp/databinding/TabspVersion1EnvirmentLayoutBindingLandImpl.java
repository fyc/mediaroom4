package com.sunmnet.mediaroom.tabsp.databinding;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class TabspVersion1EnvirmentLayoutBindingLandImpl extends TabspVersion1EnvirmentLayoutBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = null;
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    @NonNull
    private final android.widget.TextView mboundView1;
    @NonNull
    private final android.widget.TextView mboundView2;
    @NonNull
    private final android.widget.TextView mboundView3;
    @NonNull
    private final android.widget.TextView mboundView4;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public TabspVersion1EnvirmentLayoutBindingLandImpl(@Nullable android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds));
    }
    private TabspVersion1EnvirmentLayoutBindingLandImpl(android.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            );
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView1 = (android.widget.TextView) bindings[1];
        this.mboundView1.setTag(null);
        this.mboundView2 = (android.widget.TextView) bindings[2];
        this.mboundView2.setTag(null);
        this.mboundView3 = (android.widget.TextView) bindings[3];
        this.mboundView3.setTag(null);
        this.mboundView4 = (android.widget.TextView) bindings[4];
        this.mboundView4.setTag(null);
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
        if (BR.envirment == variableId) {
            setEnvirment((com.sunmnet.mediaroom.device.bean.EnvirmentDevice) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setEnvirment(@Nullable com.sunmnet.mediaroom.device.bean.EnvirmentDevice Envirment) {
        updateRegistration(0, Envirment);
        this.mEnvirment = Envirment;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.envirment);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeEnvirment((com.sunmnet.mediaroom.device.bean.EnvirmentDevice) object, fieldId);
        }
        return false;
    }
    private boolean onChangeEnvirment(com.sunmnet.mediaroom.device.bean.EnvirmentDevice Envirment, int fieldId) {
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
        com.sunmnet.mediaroom.device.bean.EnvirmentDevice envirment = mEnvirment;
        java.lang.String envirmentPm = null;
        java.lang.String envirmentTempture = null;
        java.lang.String envirmentHourAqi = null;
        java.lang.String envirmentHumidity = null;

        if ((dirtyFlags & 0x3L) != 0) {



                if (envirment != null) {
                    // read envirment.pm
                    envirmentPm = envirment.getPm();
                    // read envirment.tempture
                    envirmentTempture = envirment.getTempture();
                    // read envirment.hourAqi
                    envirmentHourAqi = envirment.getHourAqi();
                    // read envirment.humidity
                    envirmentHumidity = envirment.getHumidity();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            android.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView1, envirmentHourAqi);
            android.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView2, envirmentTempture);
            android.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView3, envirmentHumidity);
            android.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView4, envirmentPm);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): envirment
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}