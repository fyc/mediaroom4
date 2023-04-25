package com.sunmnet.mediaroom.brand.fragment.template;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.sunmnet.mediaroom.brand.R;
import com.sunmnet.mediaroom.brand.api.ApiManager;
import com.sunmnet.mediaroom.brand.api.impl.CourseApi;
import com.sunmnet.mediaroom.brand.bean.item.BuildingItem;
import com.sunmnet.mediaroom.brand.bean.item.ClassroomItem;
import com.sunmnet.mediaroom.brand.bean.item.ClassroomItemTree;
import com.sunmnet.mediaroom.brand.bean.item.FloorItem;
import com.sunmnet.mediaroom.brand.bean.response.BuildingResponse;
import com.sunmnet.mediaroom.brand.bean.response.ClassroomResponse;
import com.sunmnet.mediaroom.brand.bean.response.FloorResponse;
import com.sunmnet.mediaroom.brand.bean.user.LoginUser;
import com.sunmnet.mediaroom.brand.rx.ClassroomItemTreeZipFunction;
import com.sunmnet.mediaroom.brand.rx.SingleTaskObserver;
import com.sunmnet.mediaroom.common.tools.DateUtil;
import com.sunmnet.mediaroom.common.tools.ToastUtil;
import com.sunmnet.mediaroom.common.tools.TypeUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

//TODO 等借用教室接口
public class TemplateBorrowClassroomFragment extends Fragment {


    EditText et_reason, et_use_num;

    TextView tv_date, tv_start_time, tv_end_time;

    Spinner spinner_building, spinner_floor, spinner_classroom;
    Spinner spinner_borrow_type;

    String[] typeArray = new String[]{"班级", "团体", "其他"};

    ArrayAdapter<BuildingItem> buildingAdapter;
    ArrayAdapter<FloorItem> floorAdapter;
    ArrayAdapter<ClassroomItem> classroomAdapter;
    ClassroomItemTree classroomTree;

    int waitCount = 0;
//    List<ClassroomResponse.Result> classroomList;

    public static TemplateBorrowClassroomFragment newInstance(LoginUser loginUser) {
        Bundle args = new Bundle();
        TemplateBorrowClassroomFragment fragment = new TemplateBorrowClassroomFragment();
        args.putSerializable("user", loginUser);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_template_borrow_classroom, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View view = getView();
        if (view == null)
            return;

        view.findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });

        et_reason = (EditText) view.findViewById(R.id.et_reason);
        et_use_num = (EditText) view.findViewById(R.id.et_use_num);

        tv_date = (TextView) view.findViewById(R.id.tv_date);
        tv_start_time = (TextView) view.findViewById(R.id.tv_start_time);
        tv_end_time = (TextView) view.findViewById(R.id.tv_end_time);


        spinner_building = (Spinner) view.findViewById(R.id.spinner_building);
        spinner_floor = (Spinner) view.findViewById(R.id.spinner_floor);
        spinner_classroom = (Spinner) view.findViewById(R.id.spinner_classroom);
//        spinner_campus = (Spinner) view.findViewById(R.id.spinner_campus);
        spinner_borrow_type = (Spinner) view.findViewById(R.id.spinner_borrow_type);


        tv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate(tv_date);
            }
        });

        tv_start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTime(tv_start_time);
            }
        });

        tv_end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTime(tv_end_time);
            }
        });

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, R.id.text, typeArray);
        typeAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner_borrow_type.setAdapter(typeAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        initClassroomData();
    }

    private void selectTime(final TextView textView) {
        Calendar ca = Calendar.getInstance();
        int hours = ca.get(Calendar.HOUR_OF_DAY);
        int minutes = ca.get(Calendar.MINUTE);
        TimePickerDialog datePickerDialog = new TimePickerDialog(getContext(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar ca = Calendar.getInstance();
                        ca.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        ca.set(Calendar.MINUTE, minute);
                        ca.set(Calendar.SECOND, 0);
                        textView.setText(DateUtil.formatDate(ca.getTime(), DateUtil.DEFAULT_FORMAT_TIME));
                    }
                },
                hours, minutes, true);
        datePickerDialog.show();
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


    private void submit() {
        if (!checkInputData())
            return;
//        showProgressDialog("提交", "正在提交申请...");
         /*     cause:事由
                borrowDate：日期
                startTime：开始时间
                endTime:结束时间
                appointmentType 类型（1，班级 2，团队 3， 其它）
                classroomCode:教室编号
                usePeopleNum：使用人数  */
        ToastUtil.show(getContext(), "提交成功");
//        Map<String, String> param = new LinkedHashMap<>();
//        param.put("cause", et_reason.getText().toString().trim());
//        param.put("borrowDate", tv_date.getText().toString().trim());
//        param.put("startTime", tv_start_time.getText().toString().trim());
//        param.put("endTime", tv_end_time.getText().toString().trim());
//        param.put("appointmentType", (spinner_borrow_type.getSelectedItemPosition() + 1) + "");
//        param.put("classroomCode", classroomAdapter.getItem(spinner_classroom.getSelectedItemPosition()).getCode());
//        param.put("usePeopleNum", et_use_num.getText().toString().trim());
//        EasyOkHttp.create(getContext())
//                .url(DeviceApp.getApp().getPlServerAddr() + "/action/mediaroom/appmgr/student/student/borrowClassroom")
//                .client(OkHttpClientManager.getHttpClient(getContext(), DeviceApp.getApp().getPlServerAddr()))
//                .params(param)
//                .callback(new EasyCallback() {
//                    @Override
//                    protected void onSuccess(String result) {
//                        RunningLog.debug("提交结果：" + result);
//                        ResultWrapper resultWrapper = JacksonUtil.jsonStrToBean(result, ResultWrapper.class);
//                        if (!isDetached()) {
//                            if (resultWrapper != null && resultWrapper.isSuccess()) {
//                                ToastUtil.show(getContext(), "提交成功");
//                            } else {
//                                ToastUtil.show(getContext(), "提交失败");
//                            }
//                        }
//                        hideProgressDialog();
//                    }
//
//                    @Override
//                    protected void onFail(String errorMsg) {
//                        RunningLog.debug("提交失败： " + errorMsg);
//                        if (!isDetached()) {
//                            ToastUtil.show(getContext(), "提交失败");
//                        }
//                        hideProgressDialog();
//                    }
//                }).post();
    }

    private ProgressDialog progressDialog;

    private void showProgressDialog(String title, String msg) {
        synchronized (TemplateBorrowClassroomFragment.this) {
            if (progressDialog == null || !progressDialog.isShowing()) {
                if (progressDialog == null){
                    progressDialog = new ProgressDialog(getContext());
                }
                progressDialog.setTitle(title);
                progressDialog.setMessage(msg);
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
            }
            waitCount += 1;
        }
    }

    private void hideProgressDialog() {
        synchronized (TemplateBorrowClassroomFragment.this) {
            waitCount -= 1;
            if (waitCount == 0 && progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }


    private void initClassroomData() {
        showProgressDialog("加载中", "正在加载数据...");
        CourseApi courseApi = ApiManager.getCourseApi();
        Observable<BuildingResponse> buildingList = courseApi.getBuildingList(null);
        Observable<FloorResponse> floorList = courseApi.getFloorList(null, null);
        Observable<ClassroomResponse> classroomList = courseApi.getClassroomList(null, null, null);
        Observable.zip(buildingList, floorList, classroomList, new ClassroomItemTreeZipFunction())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleTaskObserver<ClassroomItemTree>() {
                    @Override
                    public void onNext(ClassroomItemTree classroomItemTree) {
                        classroomTree = classroomItemTree;
                        if (!isDetached() && getContext() != null) {
                            initSpinner();
                        }
                        hideProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideProgressDialog();
                    }
                });
    }

    private void initSpinner() {
        buildingAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, R.id.text, classroomTree.getAllBuilding());
        buildingAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner_building.setAdapter(buildingAdapter);
        spinner_building.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                if (buildingAdapter != null && buildingAdapter.getCount() > 0 && buildingAdapter.getItem(position) != null) {
                    onBuildingSelected(buildingAdapter.getItem(position).getCode());
                } else {
                    onBuildingSelected(null);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        List<FloorItem> floorItemList = new ArrayList<>();
        floorAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, R.id.text, floorItemList);
        floorAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner_floor.setAdapter(floorAdapter);
        spinner_floor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                if (floorAdapter != null && floorAdapter.getCount() > 0 && floorAdapter.getItem(position) != null) {
                    onFloorSelected(floorAdapter.getItem(position).getCode());
                } else {
                    onFloorSelected(null);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        List<ClassroomItem> classroomItemList = new ArrayList<>();
        classroomAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, R.id.text, classroomItemList);
        classroomAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner_classroom.setAdapter(classroomAdapter);
    }

    private void onBuildingSelected(String buildCode) {
        if (floorAdapter != null) {
            floorAdapter.clear();
            if (buildCode != null && classroomTree.getFloorList(buildCode) != null) {
                floorAdapter.addAll(classroomTree.getFloorList(buildCode));
            }
            floorAdapter.notifyDataSetChanged();
        }
        if (floorAdapter != null && floorAdapter.getCount() > 0 && floorAdapter.getItem(0) != null) {
            onFloorSelected(floorAdapter.getItem(0).getCode());
        } else {
            onFloorSelected(null);
        }
    }

    private void onFloorSelected(String floorCode) {
        if (classroomAdapter != null) {
            classroomAdapter.clear();
            if (floorCode != null && classroomTree.getClassroomList(floorCode) != null) {
                classroomAdapter.addAll(classroomTree.getClassroomList(floorCode));
            }
            classroomAdapter.notifyDataSetChanged();
        }

    }

    private boolean checkInputData() {
        if (TextUtils.isEmpty(tv_date.getText())) {
            ToastUtil.show(getContext(), "请选择日期");
            return false;
        }
        Date nowDate = DateUtil.parseDateStr(DateUtil.formatDate(new Date(), DateUtil.DEFAULT_FORMAT_DATE), DateUtil.DEFAULT_FORMAT_DATE);
        Date date = DateUtil.parseDateStr(tv_date.getText().toString(), DateUtil.DEFAULT_FORMAT_DATE);
        if (date.before(nowDate)) {
            ToastUtil.show(getContext(), "日期不可早于今天");
            return false;
        }
        if (TextUtils.isEmpty(tv_start_time.getText())) {
            ToastUtil.show(getContext(), "请选择开始时间");
            return false;
        }
        if (TextUtils.isEmpty(tv_end_time.getText())) {
            ToastUtil.show(getContext(), "请选择结束时间");
            return false;
        }
        if (spinner_classroom.getSelectedItemPosition() < 0) {
            ToastUtil.show(getContext(), "请选择教室");
            return false;
        }
        if (TextUtils.isEmpty(et_use_num.getText()) || TypeUtil.objToInt(et_use_num.getText().toString()) == 0) {
            ToastUtil.show(getContext(), "请输入使用人数，使用人数不可为0");
            return false;
        }
        if (TextUtils.isEmpty(et_reason.getText())) {
            ToastUtil.show(getContext(), "请输入借用事由");
            return false;
        }
        return true;
    }
}
