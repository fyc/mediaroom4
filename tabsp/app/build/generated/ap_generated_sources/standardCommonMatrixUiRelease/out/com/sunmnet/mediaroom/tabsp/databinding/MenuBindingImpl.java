package com.sunmnet.mediaroom.tabsp.databinding;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class MenuBindingImpl extends MenuBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.menuitem, 2);
        sViewsWithIds.put(R.id.menuIcon, 3);
        sViewsWithIds.put(R.id.right_line, 4);
    }
    // views
    @NonNull
    private final android.widget.FrameLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public MenuBindingImpl(@Nullable android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds));
    }
    private MenuBindingImpl(android.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.ImageView) bindings[3]
            , (com.sunmnet.mediaroom.tabsp.ui.widget.RollingTextView) bindings[1]
            , (android.widget.LinearLayout) bindings[2]
            , (android.view.View) bindings[4]
            );
        this.mboundView0 = (android.widget.FrameLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.menuView.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x2L;
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
        if (BR.menu == variableId) {
            setMenu((com.sunmnet.mediaroom.tabsp.bean.MenuOperator) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setMenu(@Nullable com.sunmnet.mediaroom.tabsp.bean.MenuOperator Menu) {
        this.mMenu = Menu;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.menu);
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
        com.sunmnet.mediaroom.tabsp.bean.MenuOperator menu = mMenu;
        java.lang.String menuGetEntityMenuName = null;
        com.sunmnet.mediaroom.tabsp.bean.MenuEntity menuGetEntity = null;

        if ((dirtyFlags & 0x3L) != 0) {



                if (menu != null) {
                    // read menu.getEntity()
                    menuGetEntity = menu.getEntity();
                }


                if (menuGetEntity != null) {
                    // read menu.getEntity().menuName
                    menuGetEntityMenuName = menuGetEntity.getMenuName();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            android.databinding.adapters.TextViewBindingAdapter.setText(this.menuView, menuGetEntityMenuName);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): menu
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}