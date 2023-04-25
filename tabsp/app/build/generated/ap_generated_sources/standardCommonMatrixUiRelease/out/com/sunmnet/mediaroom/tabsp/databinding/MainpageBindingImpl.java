package com.sunmnet.mediaroom.tabsp.databinding;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class MainpageBindingImpl extends MainpageBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new android.databinding.ViewDataBinding.IncludedLayouts(5);
        sIncludes.setIncludes(0, 
            new String[] {"tabsp_version1_course_layout", "tabsp_version1_envirment_layout", "tabsp_version1_scene_layout"},
            new int[] {2, 3, 4},
            new int[] {com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version1_course_layout,
                com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version1_envirment_layout,
                com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version1_scene_layout});
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.tabsp_volumn_layout, 1);
    }
    // views
    @NonNull
    private final android.support.constraint.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public MainpageBindingImpl(@Nullable android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds));
    }
    private MainpageBindingImpl(android.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 4
            , (com.sunmnet.mediaroom.tabsp.databinding.TabspVersion1CourseLayoutBinding) bindings[2]
            , (com.sunmnet.mediaroom.tabsp.databinding.TabspVersion1SceneLayoutBinding) bindings[4]
            , (com.sunmnet.mediaroom.tabsp.databinding.TabspVersion1EnvirmentLayoutBinding) bindings[3]
            , (android.view.View) bindings[1]
            );
        setContainedBinding(this.courseLayout);
        setContainedBinding(this.deviceSceneLayout);
        setContainedBinding(this.envirmentLayout);
        this.mboundView0 = (android.support.constraint.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x40L;
        }
        courseLayout.invalidateAll();
        envirmentLayout.invalidateAll();
        deviceSceneLayout.invalidateAll();
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        if (courseLayout.hasPendingBindings()) {
            return true;
        }
        if (envirmentLayout.hasPendingBindings()) {
            return true;
        }
        if (deviceSceneLayout.hasPendingBindings()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.envirment == variableId) {
            setEnvirment((com.sunmnet.mediaroom.device.bean.EnvirmentDevice) variable);
        }
        else if (BR.course == variableId) {
            setCourse((com.sunmnet.mediaroom.common.bean.course.CourseSchedule) variable);
        }
        else if (BR.constrollSceneAdapter == variableId) {
            setConstrollSceneAdapter((com.sunmnet.mediaroom.tabsp.ui.adapter.HolderAdapter) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setEnvirment(@Nullable com.sunmnet.mediaroom.device.bean.EnvirmentDevice Envirment) {
        updateRegistration(0, Envirment);
        this.mEnvirment = Envirment;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.envirment);
        super.requestRebind();
    }
    public void setCourse(@Nullable com.sunmnet.mediaroom.common.bean.course.CourseSchedule Course) {
        this.mCourse = Course;
        synchronized(this) {
            mDirtyFlags |= 0x10L;
        }
        notifyPropertyChanged(BR.course);
        super.requestRebind();
    }
    public void setConstrollSceneAdapter(@Nullable com.sunmnet.mediaroom.tabsp.ui.adapter.HolderAdapter ConstrollSceneAdapter) {
        this.mConstrollSceneAdapter = ConstrollSceneAdapter;
        synchronized(this) {
            mDirtyFlags |= 0x20L;
        }
        notifyPropertyChanged(BR.constrollSceneAdapter);
        super.requestRebind();
    }

    @Override
    public void setLifecycleOwner(@Nullable android.arch.lifecycle.LifecycleOwner lifecycleOwner) {
        super.setLifecycleOwner(lifecycleOwner);
        courseLayout.setLifecycleOwner(lifecycleOwner);
        envirmentLayout.setLifecycleOwner(lifecycleOwner);
        deviceSceneLayout.setLifecycleOwner(lifecycleOwner);
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeEnvirment((com.sunmnet.mediaroom.device.bean.EnvirmentDevice) object, fieldId);
            case 1 :
                return onChangeEnvirmentLayout((com.sunmnet.mediaroom.tabsp.databinding.TabspVersion1EnvirmentLayoutBinding) object, fieldId);
            case 2 :
                return onChangeDeviceSceneLayout((com.sunmnet.mediaroom.tabsp.databinding.TabspVersion1SceneLayoutBinding) object, fieldId);
            case 3 :
                return onChangeCourseLayout((com.sunmnet.mediaroom.tabsp.databinding.TabspVersion1CourseLayoutBinding) object, fieldId);
        }
        return false;
    }
    private boolean onChangeEnvirment(com.sunmnet.mediaroom.device.bean.EnvirmentDevice Envirment, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeEnvirmentLayout(com.sunmnet.mediaroom.tabsp.databinding.TabspVersion1EnvirmentLayoutBinding EnvirmentLayout, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeDeviceSceneLayout(com.sunmnet.mediaroom.tabsp.databinding.TabspVersion1SceneLayoutBinding DeviceSceneLayout, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x4L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeCourseLayout(com.sunmnet.mediaroom.tabsp.databinding.TabspVersion1CourseLayoutBinding CourseLayout, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x8L;
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
        com.sunmnet.mediaroom.device.bean.EnvirmentDevice envirment = mEnvirment;
        com.sunmnet.mediaroom.common.bean.course.CourseSchedule course = mCourse;
        com.sunmnet.mediaroom.tabsp.ui.adapter.HolderAdapter<? extends android.databinding.ViewDataBinding,?> constrollSceneAdapter = mConstrollSceneAdapter;

        if ((dirtyFlags & 0x41L) != 0) {
        }
        if ((dirtyFlags & 0x50L) != 0) {
        }
        if ((dirtyFlags & 0x60L) != 0) {
        }
        // batch finished
        if ((dirtyFlags & 0x50L) != 0) {
            // api target 1

            this.courseLayout.setCourse(course);
        }
        if ((dirtyFlags & 0x60L) != 0) {
            // api target 1

            this.deviceSceneLayout.setAdapter(constrollSceneAdapter);
        }
        if ((dirtyFlags & 0x41L) != 0) {
            // api target 1

            this.envirmentLayout.setEnvirment(envirment);
        }
        executeBindingsOn(courseLayout);
        executeBindingsOn(envirmentLayout);
        executeBindingsOn(deviceSceneLayout);
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): envirment
        flag 1 (0x2L): envirmentLayout
        flag 2 (0x3L): deviceSceneLayout
        flag 3 (0x4L): courseLayout
        flag 4 (0x5L): course
        flag 5 (0x6L): constrollSceneAdapter
        flag 6 (0x7L): null
    flag mapping end*/
    //end
}