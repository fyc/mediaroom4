package com.sunmnet.mediaroom.tabsp.databinding;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class DialogFaultDescriptionBindingImpl extends DialogFaultDescriptionBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.tv_title, 1);
        sViewsWithIds.put(R.id.ll_fault_description, 2);
        sViewsWithIds.put(R.id.rg_fault_description, 3);
        sViewsWithIds.put(R.id.environmental_equipment_failure, 4);
        sViewsWithIds.put(R.id.multimedia_device_failure, 5);
        sViewsWithIds.put(R.id.audio_device_failure, 6);
        sViewsWithIds.put(R.id.equipment_circuit_failure, 7);
        sViewsWithIds.put(R.id.other_equipment_issues, 8);
        sViewsWithIds.put(R.id.gl_one, 9);
        sViewsWithIds.put(R.id.btn_submit, 10);
        sViewsWithIds.put(R.id.btn_cancel, 11);
    }
    // views
    @NonNull
    private final android.support.v7.widget.CardView mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public DialogFaultDescriptionBindingImpl(@Nullable android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 12, sIncludes, sViewsWithIds));
    }
    private DialogFaultDescriptionBindingImpl(android.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.RadioButton) bindings[6]
            , (android.widget.Button) bindings[11]
            , (android.widget.Button) bindings[10]
            , (android.widget.RadioButton) bindings[4]
            , (android.widget.RadioButton) bindings[7]
            , (android.support.constraint.Guideline) bindings[9]
            , (android.widget.LinearLayout) bindings[2]
            , (android.widget.RadioButton) bindings[5]
            , (android.widget.RadioButton) bindings[8]
            , (android.widget.RadioGroup) bindings[3]
            , (android.widget.TextView) bindings[1]
            );
        this.mboundView0 = (android.support.v7.widget.CardView) bindings[0];
        this.mboundView0.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x1L;
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
            return variableSet;
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
        // batch finished
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}