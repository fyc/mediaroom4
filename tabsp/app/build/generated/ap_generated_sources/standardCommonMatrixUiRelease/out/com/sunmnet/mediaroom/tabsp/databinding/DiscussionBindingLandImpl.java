package com.sunmnet.mediaroom.tabsp.databinding;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class DiscussionBindingLandImpl extends DiscussionBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.tagName, 5);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public DiscussionBindingLandImpl(@Nullable android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 6, sIncludes, sViewsWithIds));
    }
    private DiscussionBindingLandImpl(android.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.GridView) bindings[1]
            , (android.widget.GridView) bindings[2]
            , (android.widget.GridView) bindings[3]
            , (android.widget.GridView) bindings[4]
            , (android.widget.TextView) bindings[5]
            );
        this.inputGrid.setTag(null);
        this.inputGroup.setTag(null);
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.outputGrid.setTag(null);
        this.outputGroup.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x10L;
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
        if (BR.outputGroupAdapter == variableId) {
            setOutputGroupAdapter((com.sunmnet.mediaroom.tabsp.ui.adapter.BindingAdapter) variable);
        }
        else if (BR.inputAdapter == variableId) {
            setInputAdapter((com.sunmnet.mediaroom.tabsp.ui.adapter.BindingAdapter) variable);
        }
        else if (BR.inputGroupAdapter == variableId) {
            setInputGroupAdapter((com.sunmnet.mediaroom.tabsp.ui.adapter.BindingAdapter) variable);
        }
        else if (BR.outputAdapter == variableId) {
            setOutputAdapter((com.sunmnet.mediaroom.tabsp.ui.adapter.BindingAdapter) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setOutputGroupAdapter(@Nullable com.sunmnet.mediaroom.tabsp.ui.adapter.BindingAdapter OutputGroupAdapter) {
        this.mOutputGroupAdapter = OutputGroupAdapter;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.outputGroupAdapter);
        super.requestRebind();
    }
    public void setInputAdapter(@Nullable com.sunmnet.mediaroom.tabsp.ui.adapter.BindingAdapter InputAdapter) {
        this.mInputAdapter = InputAdapter;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.inputAdapter);
        super.requestRebind();
    }
    public void setInputGroupAdapter(@Nullable com.sunmnet.mediaroom.tabsp.ui.adapter.BindingAdapter InputGroupAdapter) {
        this.mInputGroupAdapter = InputGroupAdapter;
        synchronized(this) {
            mDirtyFlags |= 0x4L;
        }
        notifyPropertyChanged(BR.inputGroupAdapter);
        super.requestRebind();
    }
    public void setOutputAdapter(@Nullable com.sunmnet.mediaroom.tabsp.ui.adapter.BindingAdapter OutputAdapter) {
        this.mOutputAdapter = OutputAdapter;
        synchronized(this) {
            mDirtyFlags |= 0x8L;
        }
        notifyPropertyChanged(BR.outputAdapter);
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
        com.sunmnet.mediaroom.tabsp.ui.adapter.BindingAdapter<? extends android.databinding.ViewDataBinding,?> outputGroupAdapter = mOutputGroupAdapter;
        com.sunmnet.mediaroom.tabsp.ui.adapter.BindingAdapter<? extends android.databinding.ViewDataBinding,?> inputAdapter = mInputAdapter;
        com.sunmnet.mediaroom.tabsp.ui.adapter.BindingAdapter<? extends android.databinding.ViewDataBinding,?> inputGroupAdapter = mInputGroupAdapter;
        com.sunmnet.mediaroom.tabsp.ui.adapter.BindingAdapter<? extends android.databinding.ViewDataBinding,?> outputAdapter = mOutputAdapter;

        if ((dirtyFlags & 0x11L) != 0) {
        }
        if ((dirtyFlags & 0x12L) != 0) {
        }
        if ((dirtyFlags & 0x14L) != 0) {
        }
        if ((dirtyFlags & 0x18L) != 0) {
        }
        // batch finished
        if ((dirtyFlags & 0x12L) != 0) {
            // api target 1

            this.inputGrid.setAdapter(inputAdapter);
        }
        if ((dirtyFlags & 0x14L) != 0) {
            // api target 1

            this.inputGroup.setAdapter(inputGroupAdapter);
        }
        if ((dirtyFlags & 0x18L) != 0) {
            // api target 1

            this.outputGrid.setAdapter(outputAdapter);
        }
        if ((dirtyFlags & 0x11L) != 0) {
            // api target 1

            this.outputGroup.setAdapter(outputGroupAdapter);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): outputGroupAdapter
        flag 1 (0x2L): inputAdapter
        flag 2 (0x3L): inputGroupAdapter
        flag 3 (0x4L): outputAdapter
        flag 4 (0x5L): null
    flag mapping end*/
    //end
}