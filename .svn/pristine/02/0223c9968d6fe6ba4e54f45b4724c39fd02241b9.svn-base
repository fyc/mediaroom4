package com.sunmnet.mediaroom.brand.fragment.template;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.sunmnet.mediaroom.brand.R;
import com.sunmnet.mediaroom.brand.bean.user.LoginUser;
import com.sunmnet.mediaroom.common.tools.CourseHelper;
import com.sunmnet.mediaroom.common.tools.DateUtil;
import com.sunmnet.mediaroom.common.tools.ToastUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

//TODO 请假接口
public class TemplateAskForLeaveFragment extends Fragment {

    Spinner type, start_class, end_class;

    EditText editText;

    TextView tv_start_date, tv_end_date;
    String[] typeArray = new String[]{"事假", "病假", "其他"};

    public static TemplateAskForLeaveFragment newInstance(LoginUser loginUser) {
        Bundle args = new Bundle();
        TemplateAskForLeaveFragment fragment = new TemplateAskForLeaveFragment();
        args.putSerializable("user", loginUser);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_template_ask_for_leave, null);
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

        start_class = (Spinner) view.findViewById(R.id.spinner_start_class);
        end_class = (Spinner) view.findViewById(R.id.spinner_end_class);
        type = (Spinner) view.findViewById(R.id.spinner_type);
        editText = (EditText) view.findViewById(R.id.et_reason);

        tv_start_date = (TextView) view.findViewById(R.id.tv_start_date);
        tv_end_date = (TextView) view.findViewById(R.id.tv_end_date);


        tv_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate(tv_start_date);
            }
        });

        tv_end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate(tv_end_date);
            }
        });

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, R.id.text, typeArray);
        typeAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        type.setAdapter(typeAdapter);

        List<String> str = new ArrayList<>();
        for (int x = 1; x <= CourseHelper.getDefault().getCourseTimeArray().size(); x++) {
            str.add("第" + x + "节");
        }

        ArrayAdapter<String> classNoAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, R.id.text, str);
        classNoAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        start_class.setAdapter(classNoAdapter);
        end_class.setAdapter(classNoAdapter);

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

    private void submit(){
        if (!checkInputData()) {
            return;
        }
//        showProgressDialog();
         /*  cause:事由
             startTime：开始日期
             endTime:结束日期
             type:类型（1，事假 2，病假 3， 其它）
             startClassNo:开始节数
             endClassNo:开始节数 */
        ToastUtil.show(getContext(), "请假成功");
//        Map<String, String> param = new LinkedHashMap<>();
//        param.put("cause", editText.getText().toString().trim());
//        param.put("startTime", tv_start_date.getText().toString().trim());
//        param.put("endTime", tv_end_date.getText().toString().trim());
//        param.put("type", (type.getSelectedItemPosition() + 1) + "");
//        param.put("startClassNo", (start_class.getSelectedItemPosition() + 1) + "");
//        param.put("endClassNo", (end_class.getSelectedItemPosition() + 1) + "");
//        EasyOkHttp.create(getContext())
//                .url(DeviceApp.getApp().getPlServerAddr() + "/action/mediaroom/appmgr/student/student/leave")
//                .client(OkHttpClientManager.getHttpClient(getContext(), DeviceApp.getApp().getPlServerAddr()))
//                .params(param)
//                .callback(new EasyCallback() {
//                    @Override
//                    protected void onSuccess(String result) {
//                        RunningLog.debug("提交结果：" + result);
//                        ResultWrapper resultWrapper = JacksonUtil.jsonStrToBean(result, ResultWrapper.class);
//                        if (!isDetached()) {
//                            if (resultWrapper != null && resultWrapper.isSuccess()) {
//                                ToastUtil.show(getContext(), "请假成功");
//                            } else {
//                                ToastUtil.show(getContext(), "请假失败");
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

    private void showProgressDialog() {
        if (progressDialog == null){
            progressDialog = new ProgressDialog(getContext());
        }
        progressDialog.setTitle("提交");
        progressDialog.setMessage("正在提交...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    private void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }


    private boolean checkInputData() {
        if (TextUtils.isEmpty(tv_start_date.getText())) {
            ToastUtil.show(getContext(), "请选择开始日期");
            return false;
        }
        if (DateUtil.isBefore(tv_start_date.getText().toString(),
                DateUtil.formatDate(new Date(), DateUtil.DEFAULT_FORMAT_DATE),
                DateUtil.DEFAULT_FORMAT_DATE)) {
            ToastUtil.show(getContext(), "开始日期不可早于今天");
            return false;
        }
        if (TextUtils.isEmpty(tv_end_date.getText())) {
            ToastUtil.show(getContext(), "请选择结束日期");
            return false;
        }
        if (DateUtil.isBefore(tv_start_date.getText().toString(),
                tv_end_date.getText().toString(),
                DateUtil.DEFAULT_FORMAT_DATE)) {
            ToastUtil.show(getContext(), "结束日期不可早于开始日期");
            return false;
        }
        if (TextUtils.isEmpty(editText.getText())) {
            ToastUtil.show(getContext(), "请填写请假理由");
        }
        if (end_class.getSelectedItemPosition() < start_class.getSelectedItemPosition()) {
            ToastUtil.show(getContext(), "结束课节不能小于开始课节");
            return false;
        }
        return true;
    }
}
