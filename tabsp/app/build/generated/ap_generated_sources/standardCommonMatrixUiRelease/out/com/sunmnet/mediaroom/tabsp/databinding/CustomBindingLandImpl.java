package com.sunmnet.mediaroom.tabsp.databinding;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class CustomBindingLandImpl extends CustomBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.back_btn, 5);
        sViewsWithIds.put(R.id.control_layout, 6);
        sViewsWithIds.put(R.id.switcher_open, 7);
        sViewsWithIds.put(R.id.switcher_close, 8);
        sViewsWithIds.put(R.id.customControlLayout, 9);
        sViewsWithIds.put(R.id.functionLayout, 10);
        sViewsWithIds.put(R.id.title_function, 11);
        sViewsWithIds.put(R.id.sourceLayout, 12);
        sViewsWithIds.put(R.id.title_source, 13);
    }
    // views
    @NonNull
    private final android.support.constraint.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public CustomBindingLandImpl(@Nullable android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 14, sIncludes, sViewsWithIds));
    }
    private CustomBindingLandImpl(android.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.LinearLayout) bindings[5]
            , (android.support.constraint.ConstraintLayout) bindings[6]
            , (android.support.constraint.ConstraintLayout) bindings[9]
            , (android.widget.ListView) bindings[2]
            , (android.widget.TextView) bindings[1]
            , (android.widget.GridView) bindings[3]
            , (android.widget.LinearLayout) bindings[10]
            , (android.widget.GridView) bindings[4]
            , (android.widget.LinearLayout) bindings[12]
            , (android.widget.Button) bindings[8]
            , (android.widget.Button) bindings[7]
            , (android.widget.TextView) bindings[11]
            , (android.widget.TextView) bindings[13]
            );
        this.deviceListView.setTag(null);
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
        if (BR.titleName == variableId) {
            setTitleName((java.lang.String) variable);
        }
        else if (BR.deviceAdapter == variableId) {
            setDeviceAdapter((com.sunmnet.mediaroom.tabsp.ui.adapter.BindingAdapter) variable);
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
    public void setDeviceAdapter(@Nullable com.sunmnet.mediaroom.tabsp.ui.adapter.BindingAdapter DeviceAdapter) {
        this.mDeviceAdapter = DeviceAdapter;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.deviceAdapter);
        super.requestRebind();
    }
    public void setFunctionAdapter(@Nullable com.sunmnet.mediaroom.tabsp.ui.adapter.BindingAdapter FunctionAdapter) {
        this.mFunctionAdapter = FunctionAdapter;
        synchronized(this) {
            mDirtyFlags |= 0x4L;
        }
        notifyPropertyChanged(BR.functionAdapter);
        super.requestRebind();
    }
    public void setSourceAdapter(@Nullable com.sunmnet.mediaroom.tabsp.ui.adapter.BindingAdapter SourceAdapter) {
        this.mSourceAdapter = SourceAdapter;
        synchronized(this) {
            mDirtyFlags |= 0x8L;
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
        com.sunmnet.mediaroom.tabsp.ui.adapter.BindingAdapter<? extends android.databinding.ViewDataBinding,?> deviceAdapter = mDeviceAdapter;
        com.sunmnet.mediaroom.tabsp.ui.adapter.BindingAdapter<? extends android.databinding.ViewDataBinding,?> functionAdapter = mFunctionAdapter;
        com.sunmnet.mediaroom.tabsp.ui.adapter.BindingAdapter<? extends android.databinding.ViewDataBinding,?> sourceAdapter = mSourceAdapter;

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

            this.deviceListView.setAdapter(deviceAdapter);
        }
        if ((dirtyFlags & 0x11L) != 0) {
            // api target 1

            android.databinding.adapters.TextViewBindingAdapter.setText(this.deviceSettingText, titleName);
        }
        if ((dirtyFlags & 0x14L) != 0) {
            // api target 1

            this.function.setAdapter(functionAdapter);
        }
        if ((dirtyFlags & 0x18L) != 0) {
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
        flag 1 (0x2L): deviceAdapter
        flag 2 (0x3L): functionAdapter
        flag 3 (0x4L): sourceAdapter
        flag 4 (0x5L): null
    flag mapping end*/
    //end
}