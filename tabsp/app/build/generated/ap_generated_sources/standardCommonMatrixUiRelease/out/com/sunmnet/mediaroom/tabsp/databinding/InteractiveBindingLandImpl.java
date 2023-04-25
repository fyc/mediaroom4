package com.sunmnet.mediaroom.tabsp.databinding;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class InteractiveBindingLandImpl extends InteractiveBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.back_btn, 4);
        sViewsWithIds.put(R.id.interactive_options, 5);
        sViewsWithIds.put(R.id.switcherProgress, 6);
        sViewsWithIds.put(R.id.switcher, 7);
        sViewsWithIds.put(R.id.volumn_icon, 8);
        sViewsWithIds.put(R.id.volumn_plus_btn, 9);
        sViewsWithIds.put(R.id.volumn_minus_btn, 10);
    }
    // views
    @NonNull
    private final android.support.constraint.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public InteractiveBindingLandImpl(@Nullable android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds));
    }
    private InteractiveBindingLandImpl(android.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.LinearLayout) bindings[4]
            , (android.widget.TextView) bindings[1]
            , (android.widget.GridView) bindings[3]
            , (android.widget.LinearLayout) bindings[5]
            , null
            , (android.widget.ListView) bindings[2]
            , (android.widget.ImageView) bindings[7]
            , (android.widget.ProgressBar) bindings[6]
            , (android.view.View) bindings[8]
            , (android.view.View) bindings[10]
            , (android.view.View) bindings[9]
            );
        this.deviceSettingText.setTag(null);
        this.interactiveModes.setTag(null);
        this.interactives.setTag(null);
        this.mboundView0 = (android.support.constraint.ConstraintLayout) bindings[0];
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
        if (BR.titleName == variableId) {
            setTitleName((java.lang.String) variable);
        }
        else if (BR.settingAdapter == variableId) {
            setSettingAdapter((com.sunmnet.mediaroom.tabsp.ui.adapter.BindingAdapter) variable);
        }
        else if (BR.adapter == variableId) {
            setAdapter((com.sunmnet.mediaroom.tabsp.ui.adapter.BindingAdapter) variable);
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
    public void setSettingAdapter(@Nullable com.sunmnet.mediaroom.tabsp.ui.adapter.BindingAdapter SettingAdapter) {
        this.mSettingAdapter = SettingAdapter;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.settingAdapter);
        super.requestRebind();
    }
    public void setAdapter(@Nullable com.sunmnet.mediaroom.tabsp.ui.adapter.BindingAdapter Adapter) {
        this.mAdapter = Adapter;
        synchronized(this) {
            mDirtyFlags |= 0x4L;
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
        java.lang.String titleName = mTitleName;
        com.sunmnet.mediaroom.tabsp.ui.adapter.BindingAdapter<? extends android.databinding.ViewDataBinding,?> settingAdapter = mSettingAdapter;
        com.sunmnet.mediaroom.tabsp.ui.adapter.BindingAdapter<? extends android.databinding.ViewDataBinding,?> adapter = mAdapter;

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

            this.interactiveModes.setAdapter(settingAdapter);
        }
        if ((dirtyFlags & 0xcL) != 0) {
            // api target 1

            this.interactives.setAdapter(adapter);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): titleName
        flag 1 (0x2L): settingAdapter
        flag 2 (0x3L): adapter
        flag 3 (0x4L): null
    flag mapping end*/
    //end
}