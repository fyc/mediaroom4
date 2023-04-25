package com.sunmnet.mediaroom.tabsp.databinding;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class MediaDeviceAdapterImpl extends MediaDeviceAdapter  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.sourceLayout, 2);
        sViewsWithIds.put(R.id.media_vga_list, 3);
    }
    // views
    @NonNull
    private final android.support.constraint.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public MediaDeviceAdapterImpl(@Nullable android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 4, sIncludes, sViewsWithIds));
    }
    private MediaDeviceAdapterImpl(android.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.LinearLayout) bindings[3]
            , (android.widget.GridView) bindings[1]
            , (android.widget.LinearLayout) bindings[2]
            );
        this.mboundView0 = (android.support.constraint.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.medialist.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x4L;
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
        if (BR.vgaAdapter == variableId) {
            setVgaAdapter((com.sunmnet.mediaroom.tabsp.ui.adapter.BindingAdapter) variable);
        }
        else if (BR.adapter == variableId) {
            setAdapter((com.sunmnet.mediaroom.tabsp.ui.adapter.MediaBindingAdapter) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setVgaAdapter(@Nullable com.sunmnet.mediaroom.tabsp.ui.adapter.BindingAdapter VgaAdapter) {
        this.mVgaAdapter = VgaAdapter;
    }
    public void setAdapter(@Nullable com.sunmnet.mediaroom.tabsp.ui.adapter.MediaBindingAdapter Adapter) {
        this.mAdapter = Adapter;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.adapter);
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
        com.sunmnet.mediaroom.tabsp.ui.adapter.MediaBindingAdapter<? extends android.databinding.ViewDataBinding,?> adapter = mAdapter;

        if ((dirtyFlags & 0x6L) != 0) {
        }
        // batch finished
        if ((dirtyFlags & 0x6L) != 0) {
            // api target 1

            this.medialist.setAdapter(adapter);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): vgaAdapter
        flag 1 (0x2L): adapter
        flag 2 (0x3L): null
    flag mapping end*/
    //end
}