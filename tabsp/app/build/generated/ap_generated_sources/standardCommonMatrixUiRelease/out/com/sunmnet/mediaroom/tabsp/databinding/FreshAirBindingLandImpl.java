package com.sunmnet.mediaroom.tabsp.databinding;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FreshAirBindingLandImpl extends FreshAirBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.switcher_layout, 3);
        sViewsWithIds.put(R.id.switcher_open, 4);
        sViewsWithIds.put(R.id.switcher_close, 5);
    }
    // views
    @NonNull
    private final android.support.constraint.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FreshAirBindingLandImpl(@Nullable android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 6, sIncludes, sViewsWithIds));
    }
    private FreshAirBindingLandImpl(android.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.ListView) bindings[1]
            , (android.widget.GridView) bindings[2]
            , (android.widget.Button) bindings[5]
            , (android.widget.LinearLayout) bindings[3]
            , (android.widget.Button) bindings[4]
            );
        this.devicelist.setTag(null);
        this.freshairMode.setTag(null);
        this.mboundView0 = (android.support.constraint.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
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
        if (BR.adapter == variableId) {
            setAdapter((com.sunmnet.mediaroom.tabsp.ui.adapter.HolderAdapter) variable);
        }
        else if (BR.modeAdapter == variableId) {
            setModeAdapter((com.sunmnet.mediaroom.tabsp.ui.adapter.HolderAdapter) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setAdapter(@Nullable com.sunmnet.mediaroom.tabsp.ui.adapter.HolderAdapter Adapter) {
        this.mAdapter = Adapter;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.adapter);
        super.requestRebind();
    }
    public void setModeAdapter(@Nullable com.sunmnet.mediaroom.tabsp.ui.adapter.HolderAdapter ModeAdapter) {
        this.mModeAdapter = ModeAdapter;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.modeAdapter);
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
        com.sunmnet.mediaroom.tabsp.ui.adapter.HolderAdapter<? extends android.databinding.ViewDataBinding,?> adapter = mAdapter;
        com.sunmnet.mediaroom.tabsp.ui.adapter.HolderAdapter<? extends android.databinding.ViewDataBinding,?> modeAdapter = mModeAdapter;

        if ((dirtyFlags & 0x5L) != 0) {
        }
        if ((dirtyFlags & 0x6L) != 0) {
        }
        // batch finished
        if ((dirtyFlags & 0x5L) != 0) {
            // api target 1

            this.devicelist.setAdapter(adapter);
        }
        if ((dirtyFlags & 0x6L) != 0) {
            // api target 1

            this.freshairMode.setAdapter(modeAdapter);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): adapter
        flag 1 (0x2L): modeAdapter
        flag 2 (0x3L): null
    flag mapping end*/
    //end
}