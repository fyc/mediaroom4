package com.sunmnet.mediaroom.tabsp.databinding;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class V3ContentImpl extends V3Content  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new android.databinding.ViewDataBinding.IncludedLayouts(6);
        sIncludes.setIncludes(0, 
            new String[] {"tabsp_version3_main_matrix_layout"},
            new int[] {5},
            new int[] {com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version3_main_matrix_layout});
        sIncludes.setIncludes(1, 
            new String[] {"tabsp_version1_envirment_layout"},
            new int[] {4},
            new int[] {com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version1_envirment_layout});
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.onekey_layout, 2);
        sViewsWithIds.put(R.id.tabsp_volumn_layout, 3);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    @Nullable
    private final com.sunmnet.mediaroom.tabsp.databinding.TabspVersion3MainMatrixLayoutBinding mboundView01;
    @NonNull
    private final android.support.constraint.ConstraintLayout mboundView1;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public V3ContentImpl(@Nullable android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 6, sIncludes, sViewsWithIds));
    }
    private V3ContentImpl(android.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 2
            , (com.sunmnet.mediaroom.tabsp.databinding.TabspVersion1EnvirmentLayoutBinding) bindings[4]
            , (android.view.View) bindings[2]
            , (android.view.View) bindings[3]
            );
        setContainedBinding(this.envirmentLayout);
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView01 = (com.sunmnet.mediaroom.tabsp.databinding.TabspVersion3MainMatrixLayoutBinding) bindings[5];
        setContainedBinding(this.mboundView01);
        this.mboundView1 = (android.support.constraint.ConstraintLayout) bindings[1];
        this.mboundView1.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x8L;
        }
        envirmentLayout.invalidateAll();
        mboundView01.invalidateAll();
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        if (envirmentLayout.hasPendingBindings()) {
            return true;
        }
        if (mboundView01.hasPendingBindings()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.envirment == variableId) {
            setEnvirment((com.sunmnet.mediaroom.device.bean.EnvirmentDevice) variable);
        }
        else if (BR.holderAdapter == variableId) {
            setHolderAdapter((com.sunmnet.mediaroom.tabsp.ui.adapter.HolderAdapter) variable);
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
    public void setHolderAdapter(@Nullable com.sunmnet.mediaroom.tabsp.ui.adapter.HolderAdapter HolderAdapter) {
        this.mHolderAdapter = HolderAdapter;
        synchronized(this) {
            mDirtyFlags |= 0x4L;
        }
        notifyPropertyChanged(BR.holderAdapter);
        super.requestRebind();
    }

    @Override
    public void setLifecycleOwner(@Nullable android.arch.lifecycle.LifecycleOwner lifecycleOwner) {
        super.setLifecycleOwner(lifecycleOwner);
        envirmentLayout.setLifecycleOwner(lifecycleOwner);
        mboundView01.setLifecycleOwner(lifecycleOwner);
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeEnvirment((com.sunmnet.mediaroom.device.bean.EnvirmentDevice) object, fieldId);
            case 1 :
                return onChangeEnvirmentLayout((com.sunmnet.mediaroom.tabsp.databinding.TabspVersion1EnvirmentLayoutBinding) object, fieldId);
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
    private boolean onChangeEnvirmentLayout(com.sunmnet.mediaroom.tabsp.databinding.TabspVersion1EnvirmentLayoutBinding EnvirmentLayout, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
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
        com.sunmnet.mediaroom.tabsp.ui.adapter.HolderAdapter<? extends android.databinding.ViewDataBinding,?> holderAdapter = mHolderAdapter;

        if ((dirtyFlags & 0x9L) != 0) {
        }
        if ((dirtyFlags & 0xcL) != 0) {
        }
        // batch finished
        if ((dirtyFlags & 0x9L) != 0) {
            // api target 1

            this.envirmentLayout.setEnvirment(envirment);
        }
        if ((dirtyFlags & 0xcL) != 0) {
            // api target 1

            this.mboundView01.setAdapter(holderAdapter);
        }
        executeBindingsOn(envirmentLayout);
        executeBindingsOn(mboundView01);
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): envirment
        flag 1 (0x2L): envirmentLayout
        flag 2 (0x3L): holderAdapter
        flag 3 (0x4L): null
    flag mapping end*/
    //end
}