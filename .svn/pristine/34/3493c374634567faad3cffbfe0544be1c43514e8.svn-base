package com.sunmnet.mediaroom.brand.fragment.template;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.sunmnet.mediaroom.brand.R;
import com.sunmnet.mediaroom.brand.api.ApiManager;
import com.sunmnet.mediaroom.brand.bean.response.TeachingInspectionTemplateDetailResponse;
import com.sunmnet.mediaroom.brand.bean.response.UserTeachingInspectionTemplateResponse;
import com.sunmnet.mediaroom.brand.bean.user.LoginUser;
import com.sunmnet.mediaroom.brand.rx.SingleTaskObserver;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ToastUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class TemplateTeachingInspectionFragment extends Fragment {

    LoginUser loginUser;
    Spinner spinner_template;
    ArrayAdapter<UserTeachingInspectionTemplateResponse.Result> adapter;
    TemplateTeachingInspectionQuestionFragment questionFragment;
    View spinnerLayout;
    View button;

    public static TemplateTeachingInspectionFragment newInstance(LoginUser loginUser) {
        Bundle args = new Bundle();
        args.putSerializable("user", loginUser);
        TemplateTeachingInspectionFragment fragment = new TemplateTeachingInspectionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginUser = (LoginUser) getArguments().getSerializable("user");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_template_teaching_inspection, null);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        spinner_template = view.findViewById(R.id.spinner_template);
        spinner_template.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (adapter != null && adapter.getItem(position) != null) {
                    UserTeachingInspectionTemplateResponse.Result item = adapter.getItem(position);
                    getTemplateDetail(item.getValue());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerLayout = view.findViewById(R.id.layout_spinner);
        button = view.findViewById(R.id.btn_submit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questionFragment != null && questionFragment.isVisible()) {
                    questionFragment.submit();
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(loginUser!=null){
            getTemplate();
        }
    }

    private void getTemplate() {
        ApiManager.getCourseApi().getTeachingInspectionTemplateList(loginUser.getToken())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleTaskObserver<UserTeachingInspectionTemplateResponse>() {
                    @Override
                    public void onNext(UserTeachingInspectionTemplateResponse response) {
                        if (response == null) {
                            RunningLog.run("教学巡查列表结果数据出错");
                        } else {
                            RunningLog.run("获取教学巡查列表完毕, success: " + response.isSuccess() + " msg:" + response.getMsg());
                        }
                        if (isDetached() || getContext() == null) {
                            return;
                        }
                        if (response != null && response.isSuccess()) {
                            if (response.getObj() == null || response.getObj().size() == 0) {
                                ToastUtil.show(getContext(), "无可用模板");
                            } else {
                                if (response.getObj().size() >= 2) {
                                    spinnerLayout.setVisibility(View.VISIBLE);
                                    adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, R.id.text, response.getObj());
                                    adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
                                    spinner_template.setAdapter(adapter);
                                } else {
                                    spinnerLayout.setVisibility(View.GONE);
                                    if (response.getObj().size() == 1) {
                                        getTemplateDetail(response.getObj().get(0).getValue());
                                    }
                                }
                            }
                        } else {
                            ToastUtil.show(getContext(), "获取模板列表失败");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        RunningLog.run("获取教学巡查列表失败: " + e.getMessage());
                        if (!isDetached() && getContext() != null) {
                            ToastUtil.show(getContext(), "获取模板列表失败：" + e.getMessage());
                        }
                    }
                });

    }

    private void getTemplateDetail(String templateId) {
        ApiManager.getCourseApi().getTeachingInspectionTemplateDetail(loginUser.getToken(), templateId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleTaskObserver<TeachingInspectionTemplateDetailResponse>() {
                    @Override
                    public void onNext(TeachingInspectionTemplateDetailResponse response) {
                        RunningLog.run("获取教学巡查模板完毕, success: " + (response != null ? response.isSuccess() + "" : "false"));
                        questionFragment = null;
                        if (isDetached() || getContext() == null) {
                            return;
                        }
                        if (response != null && response.isSuccess()) {
                            questionFragment = TemplateTeachingInspectionQuestionFragment.newInstance(loginUser.getToken(), response.getObj());
                            getChildFragmentManager().beginTransaction().replace(R.id.layout_detail, questionFragment).commit();
                        } else {
                            if (!isDetached() && getContext() != null) {
                                ToastUtil.show(getContext(), "获取模板失败");
                            }
                        }
                        if (questionFragment != null) {
                            button.setVisibility(View.VISIBLE);
                        } else {
                            button.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        RunningLog.run("获取模板失败: " + e.getMessage());
                        if (!isDetached() && getContext() != null) {
                            ToastUtil.show(getContext(), "获取模板失败：" + e.getMessage());
                        }
                    }
                });

    }
}
