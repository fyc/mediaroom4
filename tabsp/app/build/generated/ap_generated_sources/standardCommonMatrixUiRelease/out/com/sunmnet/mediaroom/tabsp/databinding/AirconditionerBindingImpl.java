package com.sunmnet.mediaroom.tabsp.databinding;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class AirconditionerBindingImpl extends AirconditionerBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.airconditioner_switcher, 4);
        sViewsWithIds.put(R.id.switcher_open, 5);
        sViewsWithIds.put(R.id.switcher_close, 6);
        sViewsWithIds.put(R.id.airconditioner_operate, 7);
        sViewsWithIds.put(R.id.airconditioner_minus, 8);
        sViewsWithIds.put(R.id.airconditioner_plus, 9);
    }
    // views
    @NonNull
    private final android.support.constraint.ConstraintLayout mboundView0;
    @NonNull
    private final android.widget.TextView mboundView2;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public AirconditionerBindingImpl(@Nullable android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 10, sIncludes, sViewsWithIds));
    }
    private AirconditionerBindingImpl(android.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.view.View) bindings[8]
            , (android.widget.GridView) bindings[3]
            , (android.widget.LinearLayout) bindings[7]
            , (android.view.View) bindings[9]
            , (android.widget.LinearLayout) bindings[4]
            , (android.widget.ListView) bindings[1]
            , (android.widget.Button) bindings[6]
            , (android.widget.Button) bindings[5]
            );
        this.airconditionerMode.setTag(null);
        this.devicelist.setTag(null);
        this.mboundView0 = (android.support.constraint.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView2 = (android.widget.TextView) bindings[2];
        this.mboundView2.setTag(null);
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
        else if (BR.adapter == variableId) {
            setAdapter((com.sunmnet.mediaroom.tabsp.ui.adapter.BindingAdapter) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setSettingAdapter(@Nullable com.sunmnet.mediaroom.tabsp.ui.adapter.BindingAdapter SettingAdapter) {
        this.mSettingAdapter = SettingAdapter;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.settingAdapter);
        super.requestRebind();
    }
    public void setSetting(@Nullable com.sunmnet.mediaroom.device.bean.Airconditioner.Setting Setting) {
        this.mSetting = Setting;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.setting);
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
        com.sunmnet.mediaroom.tabsp.ui.adapter.BindingAdapter<? extends android.databinding.ViewDataBinding,?> settingAdapter = mSettingAdapter;
        com.sunmnet.mediaroom.device.bean.Airconditioner.Setting setting = mSetting;
        com.sunmnet.mediaroom.tabsp.ui.adapter.BindingAdapter<? extends android.databinding.ViewDataBinding,?> adapter = mAdapter;
        java.lang.String settingTempeture = null;

        if ((dirtyFlags & 0x9L) != 0) {
        }
        if ((dirtyFlags & 0xaL) != 0) {



                if (setting != null) {
                    // read setting.tempeture
                    settingTempeture = setting.getTempeture();
                }
        }
        if ((dirtyFlags & 0xcL) != 0) {
        }
        // batch finished
        if ((dirtyFlags & 0x9L) != 0) {
            // api target 1

            this.airconditionerMode.setAdapter(settingAdapter);
        }
        if ((dirtyFlags & 0xcL) != 0) {
            // api target 1

            this.devicelist.setAdapter(adapter);
        }
        if ((dirtyFlags & 0xaL) != 0) {
            // api target 1

            android.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView2, settingTempeture);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): settingAdapter
        flag 1 (0x2L): setting
        flag 2 (0x3L): adapter
        flag 3 (0x4L): null
    flag mapping end*/
    //end
}