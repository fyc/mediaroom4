package com.sunmnet.mediaroom.brand.fragment.template;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sunmnet.mediaroom.brand.R;
import com.sunmnet.mediaroom.brand.api.ApiManager;
import com.sunmnet.mediaroom.brand.bean.request.TeachingInspectionAnswerRequest;
import com.sunmnet.mediaroom.brand.bean.response.StringResponse;
import com.sunmnet.mediaroom.brand.bean.response.dto.TeachingInspectionQuestionDto;
import com.sunmnet.mediaroom.brand.bean.response.dto.TeachingInspectionTemplateDto;
import com.sunmnet.mediaroom.brand.common.base.DeviceApp;
import com.sunmnet.mediaroom.brand.impl.template.TeachingInspectionQuestionFactory;
import com.sunmnet.mediaroom.brand.interfaces.template.ITeachingInspectionQuestion;
import com.sunmnet.mediaroom.brand.rx.SingleTaskObserver;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class TemplateTeachingInspectionQuestionFragment extends Fragment {

    TeachingInspectionTemplateDto teachingTemplateDto;
    List<ITeachingInspectionQuestion> questionList;
    String token;

    public static TemplateTeachingInspectionQuestionFragment newInstance(String token, TeachingInspectionTemplateDto teachingTemplateDto) {
        Bundle args = new Bundle();
        args.putSerializable("data", teachingTemplateDto);
        args.putString("token", token);
        TemplateTeachingInspectionQuestionFragment fragment = new TemplateTeachingInspectionQuestionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        teachingTemplateDto = (TeachingInspectionTemplateDto) getArguments().get("data");
        token = getArguments().getString("token");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_template_teaching_inspection_question, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        questionList = new ArrayList<>();
        LinearLayout linearLayout = view.findViewById(R.id.questionLayout);
        TextView name = view.findViewById(R.id.name);
        TextView describe = view.findViewById(R.id.describe);

        name.setText(teachingTemplateDto.getTemplateName());
        describe.setText(teachingTemplateDto.getPrefix());
        LayoutInflater layoutInflater = LayoutInflater.from(view.getContext());
        TeachingInspectionQuestionFactory factory = new TeachingInspectionQuestionFactory();
        for (int i = 0; i < teachingTemplateDto.getQuestions().size(); i++) {
            TeachingInspectionQuestionDto questionDto = teachingTemplateDto.getQuestions().get(i);
            ITeachingInspectionQuestion question = factory.getQuestion(questionDto);
            question.setQuestionNo(i + 1);
            questionList.add(question);
            View questionView = question.initView(layoutInflater, linearLayout);
            linearLayout.addView(questionView);
        }

    }


    public void submit() {
        if (questionList == null)
            return;
        List<TeachingInspectionAnswerRequest.AnswerBean> answerList = new ArrayList<>();
        for (ITeachingInspectionQuestion question : questionList) {
            TeachingInspectionAnswerRequest.AnswerBean answerData = question.getAnswerData();
            if (question.isRequired() && answerData == null) {
                if (!isDetached() && getContext() != null) {
                    ToastUtil.show(getContext(), "有必填项尚未填写");
                }
                return;
            }
            answerList.add(answerData);
        }
        TeachingInspectionAnswerRequest request = new TeachingInspectionAnswerRequest();
        request.setTemplateId(teachingTemplateDto.getId());
        request.setClassroomCode(DeviceApp.getApp().getClassroomCode());
        request.setAnswerList(answerList);
        ApiManager.getCourseApi().submitTeachingInspectionAnswer(token, request)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleTaskObserver<StringResponse>() {
                    @Override
                    public void onNext(StringResponse response) {
                        RunningLog.run("教学巡查提交结果: success:" + (response != null && response.isSuccess() ? "true" : "false"));
                        if (!isDetached() && getContext() != null) {
                            if (response != null && response.isSuccess()) {
                                ToastUtil.show(getContext(), "提交成功");
                            } else {
                                ToastUtil.show(getContext(), "提交失败");
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        RunningLog.run("教学巡查提交失败:" + e.getMessage());
                        if (!isDetached() && getContext() != null) {
                            ToastUtil.show(getContext(), "提交失败:" + e.getMessage());
                        }
                    }
                });
    }

}
