package com.sunmnet.mediaroom.brand.fragment.template;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.sunmnet.mediaroom.brand.R;
import com.sunmnet.mediaroom.brand.api.ApiManager;
import com.sunmnet.mediaroom.brand.api.impl.CourseApi;
import com.sunmnet.mediaroom.brand.bean.item.BuildingItem;
import com.sunmnet.mediaroom.brand.bean.item.ClassroomItem;
import com.sunmnet.mediaroom.brand.bean.item.ClassroomItemTree;
import com.sunmnet.mediaroom.brand.bean.item.FloorItem;
import com.sunmnet.mediaroom.brand.bean.response.BuildingResponse;
import com.sunmnet.mediaroom.brand.bean.response.ClassroomResponse;
import com.sunmnet.mediaroom.brand.bean.response.DeviceTypeResponse;
import com.sunmnet.mediaroom.brand.bean.response.FailureTypeResponse;
import com.sunmnet.mediaroom.brand.bean.response.FloorResponse;
import com.sunmnet.mediaroom.brand.bean.user.LoginUser;
import com.sunmnet.mediaroom.brand.common.base.DeviceApp;
import com.sunmnet.mediaroom.brand.common.network.EasyCallback;
import com.sunmnet.mediaroom.brand.common.network.EasyOkHttp;
import com.sunmnet.mediaroom.brand.rx.ClassroomItemTreeZipFunction;
import com.sunmnet.mediaroom.brand.rx.SingleTaskObserver;
import com.sunmnet.mediaroom.brand.utils.OkHttpClientManager;
import com.sunmnet.mediaroom.common.tools.JacksonUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ToastUtil;
import com.sunmnet.mediaroom.common.tools.TypeUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

//TODO 故障申报接口
public class TemplateReportFaultFragment extends Fragment {


    EditText et_describe, et_code;

    Spinner spinner_building, spinner_floor, spinner_classroom, spinner_type, spinner_device;
//    Spinner spinner_campus;

    List<DeviceTypeResponse.Datas> deviceTypeList;
    List<FailureTypeResponse.Datas> failureTypeList;

    ArrayAdapter<BuildingItem> buildingAdapter;
    ArrayAdapter<FloorItem> floorAdapter;
    ArrayAdapter<ClassroomItem> classroomAdapter;
    ClassroomItemTree classroomTree;

    private int waitCount = 0;

    public static TemplateReportFaultFragment newInstance(LoginUser loginUser) {
        Bundle args = new Bundle();
        TemplateReportFaultFragment fragment = new TemplateReportFaultFragment();
        args.putSerializable("user", loginUser);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_template_report_fault, null);
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

        et_code = (EditText) view.findViewById(R.id.et_code);
        et_describe = (EditText) view.findViewById(R.id.et_describe);

        spinner_building = (Spinner) view.findViewById(R.id.spinner_building);
        spinner_floor = (Spinner) view.findViewById(R.id.spinner_floor);
        spinner_classroom = (Spinner) view.findViewById(R.id.spinner_classroom);
        spinner_type = (Spinner) view.findViewById(R.id.spinner_type);
        spinner_device = (Spinner) view.findViewById(R.id.spinner_device);
//        spinner_campus = (Spinner) view.findViewById(R.id.spinner_campus);

    }

    @Override
    public void onResume() {
        super.onResume();
        getDeviceType();
        getFailureType();
        initClassroomData();
    }

    private ProgressDialog progressDialog;

    private void showProgressDialog(String title, String msg) {
        synchronized (TemplateReportFaultFragment.this) {
            if (progressDialog == null || !progressDialog.isShowing()) {
                if (progressDialog == null) {
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
        synchronized (TemplateReportFaultFragment.this) {
            waitCount -= 1;
            if (waitCount == 0 && progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }

    private void submit() {
        if (!checkInputData())
            return;
        showProgressDialog("提交", "正在提交...");
                /*  problemCode：资产编号
                    deviceClassification：设备类型
                    faultClassification：故障类型
                    problemDescribe：故障描述
                    classroomCodes:教室编号  */
        Map<String, String> param = new LinkedHashMap<>();
        param.put("problemType", "1");
        param.put("problemCode", et_code.getText().toString().trim());
        param.put("deviceClassification", deviceTypeList.get(spinner_device.getSelectedItemPosition()).getDeviceTypeCode());
        param.put("faultClassification", failureTypeList.get(spinner_type.getSelectedItemPosition()).getFaultCode());
        param.put("problemDescribe", et_describe.getText().toString().trim());
        param.put("classroomCodes", classroomAdapter.getItem(spinner_classroom.getSelectedItemPosition()).getCode());
        EasyOkHttp.create(getContext())
                .url(DeviceApp.getApp().getPlServerAddr() + "/action/mediaroom/configuremgr/problemTrack/addTrack")
                .client(OkHttpClientManager.getHttpClient(getContext(), DeviceApp.getApp().getPlServerAddr()))
                .params(param)
                .callback(new EasyCallback() {
                    @Override
                    protected void onSuccess(String result) {
                        RunningLog.debug("提交结果：" + result);
                        //{"trackId":34,"state":true}
                        Map<String, Object> map = JacksonUtil.jsonStrToMap_SO(result);
                        if (!isDetached() && getContext() != null && map != null) {
                            if (TypeUtil.objToBoolean(map.get("state"))) {
                                ToastUtil.show(getContext(), "提交成功");
                            } else {
                                ToastUtil.show(getContext(), "提交失败");
                            }
                        }
                        hideProgressDialog();
                    }

                    @Override
                    protected void onFail(String errorMsg) {
                        RunningLog.debug("提交失败： " + errorMsg);
                        if (!isDetached() && getContext() != null) {
                            ToastUtil.show(getContext(), "提交失败");
                        }
                        hideProgressDialog();
                    }
                }).post();
    }

    private void getDeviceType() {
        showProgressDialog("加载中", "正在加载数据...");
        Map<String, String> param = new LinkedHashMap<>();
        param.put("queryJsonStr", "{\"deviceTypeName\":\"%\"}");
        EasyOkHttp.create(getContext())
                .url(DeviceApp.getApp().getPlServerAddr() + "/action/mediaroom/configuremgr/problemTrack/findAllDeviceType")
                .client(OkHttpClientManager.getHttpClient(getContext(), DeviceApp.getApp().getPlServerAddr()))
                .cacheTime(120)
                .params(param)
                .callback(new EasyCallback() {
                    @Override
                    protected void onSuccess(String result) {
                        DeviceTypeResponse resultBean = JacksonUtil.jsonStrToBean(result, DeviceTypeResponse.class);
                        if (resultBean != null && resultBean.isState()) {
                            deviceTypeList = resultBean.getDatas();
                            ArrayAdapter deviceAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, R.id.text, deviceTypeList);
                            deviceAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
                            spinner_device.setAdapter(deviceAdapter);
                        }
                        hideProgressDialog();
                    }

                    @Override
                    protected void onFail(String errorMsg) {
                        hideProgressDialog();
                    }
                }).post();
    }

    private void getFailureType() {
        showProgressDialog("加载中", "正在加载数据...");
        EasyOkHttp.create(getContext())
                .url(DeviceApp.getApp().getPlServerAddr() + "/action/mediaroom/configuremgr/problemTrack/findAllFaultType")
                .cacheTime(120)
                .client(OkHttpClientManager.getHttpClient(getContext(), DeviceApp.getApp().getPlServerAddr()))
                .callback(new EasyCallback() {
                    @Override
                    protected void onSuccess(String result) {
                        FailureTypeResponse resultBean = JacksonUtil.jsonStrToBean(result, FailureTypeResponse.class);
                        if (resultBean != null && resultBean.isState()) {
                            failureTypeList = resultBean.getDatas();
                            ArrayAdapter typeAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, R.id.text, failureTypeList);
                            typeAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
                            spinner_type.setAdapter(typeAdapter);
                        }
                        hideProgressDialog();
                    }

                    @Override
                    protected void onFail(String errorMsg) {
                        hideProgressDialog();
                    }
                }).post();
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
                if (buildingAdapter != null && buildingAdapter.getCount() > 0 && buildingAdapter.getItem(0) != null) {
                    onBuildingSelected(buildingAdapter.getItem(0).getCode());
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
                if (floorAdapter != null && floorAdapter.getCount() > 0 && floorAdapter.getItem(0) != null) {
                    onFloorSelected(floorAdapter.getItem(0).getCode());
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
        if (TextUtils.isEmpty(et_code.getText())) {
            ToastUtil.show(getContext(), "请输入资产编号");
            return false;
        }
        if (spinner_device.getSelectedItemPosition() < 0) {
            ToastUtil.show(getContext(), "请选择故障设备");
            return false;
        }
        if (spinner_type.getSelectedItemPosition() < 0) {
            ToastUtil.show(getContext(), "请选择故障类型");
            return false;
        }
        if (spinner_classroom.getSelectedItemPosition() < 0) {
            ToastUtil.show(getContext(), "请选择教室");
            return false;
        }
        if (TextUtils.isEmpty(et_describe.getText())) {
            ToastUtil.show(getContext(), "请输入故障描述");
            return false;
        }
        return true;
    }
}
