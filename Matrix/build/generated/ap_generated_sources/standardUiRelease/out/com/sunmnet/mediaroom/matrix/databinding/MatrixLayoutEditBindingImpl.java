package com.sunmnet.mediaroom.matrix.databinding;
import com.sunmnet.mediaroom.matrix.R;
import com.sunmnet.mediaroom.matrix.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class MatrixLayoutEditBindingImpl extends MatrixLayoutEditBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.back_fragment, 1);
        sViewsWithIds.put(R.id.device_back_image, 2);
        sViewsWithIds.put(R.id.deviceTypeName_title, 3);
        sViewsWithIds.put(R.id.clear, 4);
        sViewsWithIds.put(R.id.preview, 5);
        sViewsWithIds.put(R.id.save, 6);
        sViewsWithIds.put(R.id.cancel, 7);
        sViewsWithIds.put(R.id.matrix_edit_table, 8);
        sViewsWithIds.put(R.id.matrix_edit_gridview, 9);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public MatrixLayoutEditBindingImpl(@Nullable android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 10, sIncludes, sViewsWithIds));
    }
    private MatrixLayoutEditBindingImpl(android.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.LinearLayout) bindings[1]
            , (android.widget.Button) bindings[7]
            , (android.widget.Button) bindings[4]
            , (android.widget.ImageView) bindings[2]
            , (android.widget.TextView) bindings[3]
            , (android.widget.GridView) bindings[9]
            , (com.sunmnet.mediaroom.matrix.anotherUi.ui.MatrixTableLayout) bindings[8]
            , (android.widget.Button) bindings[5]
            , (android.widget.Button) bindings[6]
            );
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
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