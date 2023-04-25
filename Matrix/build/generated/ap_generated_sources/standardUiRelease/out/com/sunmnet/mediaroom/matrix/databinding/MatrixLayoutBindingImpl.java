package com.sunmnet.mediaroom.matrix.databinding;
import com.sunmnet.mediaroom.matrix.R;
import com.sunmnet.mediaroom.matrix.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class MatrixLayoutBindingImpl extends MatrixLayoutBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new android.databinding.ViewDataBinding.IncludedLayouts(3);
        sIncludes.setIncludes(0, 
            new String[] {"matrix_layout_main", "matrix_layout_edit"},
            new int[] {1, 2},
            new int[] {com.sunmnet.mediaroom.matrix.R.layout.matrix_layout_main,
                com.sunmnet.mediaroom.matrix.R.layout.matrix_layout_edit});
        sViewsWithIds = null;
    }
    // views
    @NonNull
    private final android.widget.FrameLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public MatrixLayoutBindingImpl(@Nullable android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 3, sIncludes, sViewsWithIds));
    }
    private MatrixLayoutBindingImpl(android.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 2
            , (com.sunmnet.mediaroom.matrix.databinding.MatrixLayoutEditBinding) bindings[2]
            , (com.sunmnet.mediaroom.matrix.databinding.MatrixLayoutMainBinding) bindings[1]
            );
        setContainedBinding(this.matrixEdit);
        setContainedBinding(this.matrixMain);
        this.mboundView0 = (android.widget.FrameLayout) bindings[0];
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
        matrixMain.invalidateAll();
        matrixEdit.invalidateAll();
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        if (matrixMain.hasPendingBindings()) {
            return true;
        }
        if (matrixEdit.hasPendingBindings()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
            return variableSet;
    }

    @Override
    public void setLifecycleOwner(@Nullable android.arch.lifecycle.LifecycleOwner lifecycleOwner) {
        super.setLifecycleOwner(lifecycleOwner);
        matrixMain.setLifecycleOwner(lifecycleOwner);
        matrixEdit.setLifecycleOwner(lifecycleOwner);
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeMatrixEdit((com.sunmnet.mediaroom.matrix.databinding.MatrixLayoutEditBinding) object, fieldId);
            case 1 :
                return onChangeMatrixMain((com.sunmnet.mediaroom.matrix.databinding.MatrixLayoutMainBinding) object, fieldId);
        }
        return false;
    }
    private boolean onChangeMatrixEdit(com.sunmnet.mediaroom.matrix.databinding.MatrixLayoutEditBinding MatrixEdit, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeMatrixMain(com.sunmnet.mediaroom.matrix.databinding.MatrixLayoutMainBinding MatrixMain, int fieldId) {
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
        // batch finished
        executeBindingsOn(matrixMain);
        executeBindingsOn(matrixEdit);
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): matrixEdit
        flag 1 (0x2L): matrixMain
        flag 2 (0x3L): null
    flag mapping end*/
    //end
}