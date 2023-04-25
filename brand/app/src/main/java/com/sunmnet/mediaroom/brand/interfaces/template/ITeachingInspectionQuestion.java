package com.sunmnet.mediaroom.brand.interfaces.template;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sunmnet.mediaroom.brand.bean.request.TeachingInspectionAnswerRequest;

public interface ITeachingInspectionQuestion {

    View initView(LayoutInflater layoutInflater, ViewGroup parentView);

    View getView();

    TeachingInspectionAnswerRequest.AnswerBean getAnswerData();

    void setQuestionNo(int no);

    boolean isRequired();
}
