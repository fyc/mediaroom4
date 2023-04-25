package com.sunmnet.mediaroom.tabsp.databinding;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class TabspVersion1CourseLayoutBindingLandImpl extends TabspVersion1CourseLayoutBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.courseholder, 6);
        sViewsWithIds.put(R.id.ll_one_key, 7);
        sViewsWithIds.put(R.id.classon_onekey, 8);
        sViewsWithIds.put(R.id.iv_class_on_logo, 9);
        sViewsWithIds.put(R.id.tv_class_on_text, 10);
        sViewsWithIds.put(R.id.classover_onekey, 11);
        sViewsWithIds.put(R.id.iv_class_over_logo, 12);
        sViewsWithIds.put(R.id.tv_class_over_text, 13);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    @NonNull
    private final android.widget.TextView mboundView1;
    @NonNull
    private final android.widget.TextView mboundView2;
    @NonNull
    private final android.widget.TextView mboundView3;
    @NonNull
    private final android.widget.TextView mboundView4;
    @NonNull
    private final android.widget.TextView mboundView5;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public TabspVersion1CourseLayoutBindingLandImpl(@Nullable android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 14, sIncludes, sViewsWithIds));
    }
    private TabspVersion1CourseLayoutBindingLandImpl(android.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.view.View) bindings[8]
            , (android.view.View) bindings[11]
            , (android.widget.LinearLayout) bindings[6]
            , (android.widget.ImageView) bindings[9]
            , (android.widget.ImageView) bindings[12]
            , (android.widget.LinearLayout) bindings[7]
            , (android.widget.TextView) bindings[10]
            , (android.widget.TextView) bindings[13]
            );
        this.mboundView0 = (android.widget.RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView1 = (android.widget.TextView) bindings[1];
        this.mboundView1.setTag(null);
        this.mboundView2 = (android.widget.TextView) bindings[2];
        this.mboundView2.setTag(null);
        this.mboundView3 = (android.widget.TextView) bindings[3];
        this.mboundView3.setTag(null);
        this.mboundView4 = (android.widget.TextView) bindings[4];
        this.mboundView4.setTag(null);
        this.mboundView5 = (android.widget.TextView) bindings[5];
        this.mboundView5.setTag(null);
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
        if (BR.course == variableId) {
            setCourse((com.sunmnet.mediaroom.common.bean.course.CourseSchedule) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setCourse(@Nullable com.sunmnet.mediaroom.common.bean.course.CourseSchedule Course) {
        this.mCourse = Course;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.course);
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
        java.lang.String courseCourseTime = null;
        java.lang.String courseTeacherName = null;
        com.sunmnet.mediaroom.common.bean.course.CourseSchedule course = mCourse;
        java.lang.String courseCourseName = null;
        java.lang.String courseClassNo = null;
        java.lang.String courseGradeName = null;

        if ((dirtyFlags & 0x3L) != 0) {



                if (course != null) {
                    // read course.courseTime
                    courseCourseTime = course.getCourseTime();
                    // read course.teacherName
                    courseTeacherName = course.getTeacherName();
                    // read course.courseName
                    courseCourseName = course.getCourseName();
                    // read course.classNo
                    courseClassNo = course.getClassNo();
                    // read course.gradeName
                    courseGradeName = course.getGradeName();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            android.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView1, courseCourseName);
            android.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView2, courseClassNo);
            android.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView3, courseCourseTime);
            android.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView4, courseTeacherName);
            android.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView5, courseGradeName);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): course
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}