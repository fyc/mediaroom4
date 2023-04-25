package com.sunmnet.mediaroom.brand.fragment.template;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.sunmnet.mediaroom.brand.R;
import com.sunmnet.mediaroom.brand.api.ApiManager;
import com.sunmnet.mediaroom.brand.bean.item.DepartmentGradeItem;
import com.sunmnet.mediaroom.brand.bean.item.GradeItem;
import com.sunmnet.mediaroom.brand.bean.response.AttendResultResponse;
import com.sunmnet.mediaroom.brand.bean.response.DepartmentGradeTreeResponse;
import com.sunmnet.mediaroom.brand.common.base.DeviceApp;
import com.sunmnet.mediaroom.brand.rx.SingleTaskObserver;
import com.sunmnet.mediaroom.common.tools.CourseHelper;
import com.sunmnet.mediaroom.common.tools.DateUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class TemplateAttendanceFragment extends Fragment {


    GridView gv_arrive, gv_leave, gv_late;

    Spinner spinner_department, spinner_grade, spinner_class_no;

    TextView text_date;

    ArrayAdapter<DepartmentGradeItem> departmentAdapter;
    ArrayAdapter<GradeItem> gradeAdapter;

    SingleTaskObserver<AttendResultResponse> observer = new SingleTaskObserver<AttendResultResponse>() {
        @Override
        public void onNext(AttendResultResponse response) {
            if (response != null && response.isSuccess() && response.getObj() != null) {
                AttendanceAdapter arriveAdapter = new AttendanceAdapter(getContext(), response.getObj().getCheckInList());
                gv_arrive.setAdapter(arriveAdapter);


                AttendanceAdapter leaveAdapter = new AttendanceAdapter(getContext(), response.getObj().getLeaveList());
                gv_leave.setAdapter(leaveAdapter);

                AttendanceAdapter lateAdapter = new AttendanceAdapter(getContext(), response.getObj().getLateList());
                gv_late.setAdapter(lateAdapter);
            }
        }

        @Override
        public void onError(Throwable e) {

        }
    };

    public static TemplateAttendanceFragment newInstance() {
        Bundle args = new Bundle();
        TemplateAttendanceFragment fragment = new TemplateAttendanceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_template_attendance, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gv_arrive = view.findViewById(R.id.gv_arrive);
        gv_leave = view.findViewById(R.id.gv_leave);
        gv_late = view.findViewById(R.id.gv_late);

        spinner_department = view.findViewById(R.id.spinner_department);
        spinner_grade = view.findViewById(R.id.spinner_grade);
        spinner_class_no = view.findViewById(R.id.spinner_class_no);
        text_date = view.findViewById(R.id.text_date);

        text_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate(text_date);
            }
        });
        text_date.setText(DateUtil.getNowDateString(DateUtil.DEFAULT_FORMAT_DATE));

        List<String> str = new ArrayList<>();
        str.add("当前课节");
        for (int x = 1; x <= CourseHelper.getDefault().getCourseTimeArray().size(); x++) {
            str.add("第" + x + "节");
        }
        ArrayAdapter<String> classNoAdapter = new ArrayAdapter<>(spinner_class_no.getContext(), R.layout.spinner_item, R.id.text, str);
        classNoAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner_class_no.setAdapter(classNoAdapter);
        getDepartmentAndGrade();
    }

    private void getDepartmentAndGrade() {
        showProgressDialog();
        ApiManager.getCourseApi().getDepartmentGradeTree()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleTaskObserver<DepartmentGradeTreeResponse>() {
                    @Override
                    public void onNext(DepartmentGradeTreeResponse departmentGradeTreeResponse) {
                        if (departmentGradeTreeResponse == null || !departmentGradeTreeResponse.isSuccess()) {
                            RunningLog.run("获取系别信息失败");
                            return;
                        }
                        List<DepartmentGradeTreeResponse.Result> obj = departmentGradeTreeResponse.getObj();
                        if (obj == null) {
                            RunningLog.run("系别信息数据为空");
                            return;
                        }
                        if (!isDetached() && getContext() != null) {
                            initDepartmentAndGradeSpinner(obj);
                        }
                        hideProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideProgressDialog();
                    }
                });
    }

    private void initDepartmentAndGradeSpinner(List<DepartmentGradeTreeResponse.Result> resultList) {
        List<DepartmentGradeItem> itemList = new ArrayList<>();
        if (resultList != null && resultList.size() > 0) {
            DepartmentGradeItem nullItem = new DepartmentGradeItem();
            List<GradeItem> nullGradeItemList = new ArrayList<>();
            nullGradeItemList.add(new GradeItem());
            nullItem.setGradeList(nullGradeItemList);
            itemList.add(nullItem);
        } else {
            spinner_department.setAdapter(null);
            spinner_grade.setAdapter(null);
            return;
        }
        for (DepartmentGradeTreeResponse.Result result : resultList) {
            DepartmentGradeItem department = new DepartmentGradeItem();
            department.setId(result.getId());
            department.setName(result.getName());
            department.setParentId(result.getParentId());
            List<GradeItem> gradeList = new ArrayList<>();
            department.setGradeList(gradeList);
            if (result.getChildren() != null) {
                for (DepartmentGradeTreeResponse.Result.Child child : result.getChildren()) {
                    GradeItem grade = new GradeItem();
                    grade.setId(child.getId());
                    grade.setName(child.getName());
                    grade.setParentId(child.getParentId());
                    gradeList.add(grade);
                }
            }
            itemList.add(department);
        }

        departmentAdapter = new ArrayAdapter<>(spinner_department.getContext(), R.layout.spinner_item, R.id.text, itemList);
        departmentAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner_department.setAdapter(departmentAdapter);
//        spinner_department.setSelection(0);

        List<GradeItem> gradeItemList = new ArrayList<>(itemList.get(0).getGradeList());
        gradeAdapter = new ArrayAdapter<>(spinner_grade.getContext(), R.layout.spinner_item, R.id.text, gradeItemList);
        gradeAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner_grade.setAdapter(gradeAdapter);
//        spinner_grade.setSelection(0);


        spinner_department.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (gradeAdapter != null) {
                    gradeAdapter.clear();
                    if (departmentAdapter.getItem(position) != null && departmentAdapter.getItem(position).getGradeList() != null) {
                        gradeAdapter.addAll(departmentAdapter.getItem(position).getGradeList());
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_grade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    getAttendData();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getAttendData() {
        if ((spinner_grade.getSelectedItemPosition() == 0 && spinner_department.getSelectedItemPosition() == 0) || spinner_grade.getSelectedItem() == null)
            return;
        int selectedItemPosition = spinner_class_no.getSelectedItemPosition();
        String classNo = selectedItemPosition == 0 ? null : selectedItemPosition + "";
        String courseDate = text_date.getText().toString();
        String gradeCode = ((GradeItem) spinner_grade.getSelectedItem()).getId();
        ApiManager.getCourseApi().getStudentAttendanceResult(DeviceApp.getApp().getClassroomCode(), gradeCode, courseDate, classNo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private void testData() {
        List<AttendResultResponse.Result.AttendInfo> arriveList = new ArrayList<>();
        int i = 0;
        for (; i < 30; i++) {
            AttendResultResponse.Result.AttendInfo attendanceInfo = new AttendResultResponse.Result.AttendInfo();
            attendanceInfo.setName("学生" + i);
            arriveList.add(attendanceInfo);
        }
        AttendanceAdapter arriveAdapter = new AttendanceAdapter(getContext(), arriveList);
        gv_arrive.setAdapter(arriveAdapter);


        List<AttendResultResponse.Result.AttendInfo> leaveList = new ArrayList<>();
        for (; i < 35; i++) {
            AttendResultResponse.Result.AttendInfo attendanceInfo = new AttendResultResponse.Result.AttendInfo();
            attendanceInfo.setName("学生" + i);
            leaveList.add(attendanceInfo);
        }
        AttendanceAdapter leaveAdapter = new AttendanceAdapter(getContext(), leaveList);
        gv_leave.setAdapter(leaveAdapter);

        List<AttendResultResponse.Result.AttendInfo> lateList = new ArrayList<>();
        for (; i < 40; i++) {
            AttendResultResponse.Result.AttendInfo attendanceInfo = new AttendResultResponse.Result.AttendInfo();
            attendanceInfo.setName("学生" + i);
            lateList.add(attendanceInfo);
        }
        AttendanceAdapter lateAdapter = new AttendanceAdapter(getContext(), lateList);
        gv_late.setAdapter(lateAdapter);
    }

    private void selectDate(final TextView textView) {
        Calendar ca = Calendar.getInstance();
        int mYear = ca.get(Calendar.YEAR);
        int mMonth = ca.get(Calendar.MONTH);
        int mDay = ca.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar ca = Calendar.getInstance();
                        ca.set(Calendar.YEAR, year);
                        ca.set(Calendar.MONTH, month);
                        ca.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        textView.setText(DateUtil.formatDate(ca.getTime(), DateUtil.DEFAULT_FORMAT_DATE));
                    }
                },
                mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    class AttendanceAdapter extends BaseAdapter {


        List<AttendResultResponse.Result.AttendInfo> attendanceItemList;
        Context mContext;

        public AttendanceAdapter(Context context, List<AttendResultResponse.Result.AttendInfo> attendanceItemList) {
            mContext = context;
            this.attendanceItemList = attendanceItemList;
        }

        @Override
        public int getCount() {
            return attendanceItemList == null ? 0 : attendanceItemList.size();
        }

        @Override
        public Object getItem(int position) {
            return attendanceItemList == null ? null : attendanceItemList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.template_attendance_item, null);
            }
            TextView textView = (TextView) convertView.findViewById(R.id.text);

            final ImageView imageView = (ImageView) convertView.findViewById(R.id.image);

            textView.setText(attendanceItemList.get(position).getName());
            Glide.with(mContext)
//                    .load(R.drawable.template_default_portrait)
                    .load(attendanceItemList.get(position).getImgUrl())
                    .error(R.drawable.template_default_portrait)
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(new ImageViewTarget<Drawable>(imageView) {
                        @Override
                        protected void setResource(@Nullable Drawable resource) {
                            view.setImageDrawable(resource);
                        }
                    });
            return convertView;
        }
    }

    private ProgressDialog progressDialog;

    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getContext());
        }
        progressDialog.setTitle("查询");
        progressDialog.setMessage("正在查询课表...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    private void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

}
