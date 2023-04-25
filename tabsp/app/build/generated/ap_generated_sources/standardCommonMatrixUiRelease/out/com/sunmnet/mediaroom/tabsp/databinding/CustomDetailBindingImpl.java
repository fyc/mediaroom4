package com.sunmnet.mediaroom.tabsp.databinding;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class CustomDetailBindingImpl extends CustomDetailBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.back_btn, 4);
        sViewsWithIds.put(R.id.control_layout, 5);
        sViewsWithIds.put(R.id.switcher, 6);
        sViewsWithIds.put(R.id.progress, 7);
        sViewsWithIds.put(R.id.customControlLayout, 8);
        sViewsWithIds.put(R.id.functionLayout, 9);
        sViewsWithIds.put(R.id.title_function, 10);
        sViewsWithIds.put(R.id.sourceLayout, 11);
        sViewsWithIds.put(R.id.title_source, 12);
    }
    // views
    @NonNull
    private final android.support.constraint.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public CustomDetailBindingImpl(@Nullable android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 13, sIncludes, sViewsWithIds));
    }
    private CustomDetailBindingImpl(android.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.LinearLayout) bindings[4]
            , (android.support.constraint.ConstraintLayout) bindings[5]
            , (android.support.constraint.ConstraintLayout) bindings[8]
            , (android.widget.TextView) bindings[1]
            , (android.widget.GridView) bindings[2]
            , (android.widget.LinearLayout) bindings[9]
            , (android.widget.ProgressBar) bindings[7]
            , (android.widget.GridView) bindings[3]
            , (android.widget.LinearLayout) bindings[11]
            , (android.widget.Switch) bindings[6]
            , (android.widget.TextView) bindings[10]
            , (android.widget.TextView) bindings[12]
            );
        this.deviceSettingText.setTag(null);
        this.function.setTag(null);
        this.mboundView0 = (android.support.constraint.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.source.setTag(null);
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
        if (BR.titleName == variableId) {
            setTitleName((java.lang.String) variable);
        }
        else if (BR.functionAdapter == variableId) {
            setFunctionAdapter((com.sunmnet.mediaroom.tabsp.ui.adapter.BindingAdapter) variable);
        }
        else if (BR.sourceAdapter == variableId) {
            setSourceAdapter((com.sunmnet.mediaroom.tabsp.ui.adapter.BindingAdapter) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setTitleName(@Nullable java.lang.String TitleName) {
        this.mTitleName = TitleName;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.titleName);
        super.requestRebind();
    }
    public void setFunctionAdapter(@Nullable com.sunmnet.mediaroom.tabsp.ui.adapter.BindingAdapter FunctionAdapter) {
        this.mFunctionAdapter = FunctionAdapter;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.functionAdapter);
        super.requestRebind();
    }
    public void setSourceAdapter(@Nullable com.sunmnet.mediaroom.tabsp.ui.adapter.BindingAdapter SourceAdapter) {
        this.mSourceAdapter = SourceAdapter;
        synchronized(this) {
            mDirtyFlags |= 0x4L;
        }
        notifyPropertyChanged(BR.sourceAdapter);
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
        java.lang.String titleName = mTitleName;
        com.sunmnet.mediaroom.tabsp.ui.adapter.BindingAdapter<? extends android.databinding.ViewDataBinding,?> functionAdapter = mFunctionAdapter;
        com.sunmnet.mediaroom.tabsp.ui.adapter.BindingAdapter<? extends android.databinding.ViewDataBinding,?> sourceAdapter = mSourceAdapter;

        if ((dirtyFlags & 0x9L) != 0) {
        }
        if ((dirtyFlags & 0xaL) != 0) {
        }
        if ((dirtyFlags & 0xcL) != 0) {
        }
        // batch finished
        if ((dirtyFlags & 0x9L) != 0) {
            // api target 1

            android.databinding.adapters.TextViewBindingAdapter.setText(this.deviceSettingText, titleName);
        }
        if ((dirtyFlags & 0xaL) != 0) {
            // api target 1

            this.function.setAdapter(functionAdapter);
        }
        if ((dirtyFlags & 0xcL) != 0) {
            // api target 1

            this.source.setAdapter(sourceAdapter);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): titleName
        flag 1 (0x2L): functionAdapter
        flag 2 (0x3L): sourceAdapter
        flag 3 (0x4L): null
    flag mapping end*/
    //end
}