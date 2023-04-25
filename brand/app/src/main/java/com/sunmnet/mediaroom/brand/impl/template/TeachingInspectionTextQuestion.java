package com.sunmnet.mediaroom.brand.impl.template;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.sunmnet.mediaroom.brand.R;
import com.sunmnet.mediaroom.brand.bean.request.TeachingInspectionAnswerRequest;
import com.sunmnet.mediaroom.brand.bean.response.dto.TeachingInspectionQuestionDto;

public class TeachingInspectionTextQuestion extends AbsTeachingInspectionQuestion {

    View view;
    EditText editText;

    public TeachingInspectionTextQuestion(TeachingInspectionQuestionDto questionDto) {
        super(questionDto);
    }

    @Override
    public View initView(LayoutInflater layoutInflater, ViewGroup parentView) {
        if (view == null) {
            view = layoutInflater.inflate(R.layout.item_teaching_inspection_text, parentView, false);
            ((TextView) view.findViewById(R.id.title)).setText(getTitle());
            editText = view.findViewById(R.id.editText);
        }
        return view;
    }

    @Override
    public View getView() {
        return view;
    }

    @Override
    public TeachingInspectionAnswerRequest.AnswerBean getAnswerData() {
        String string = editText.getText().toString();
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        TeachingInspectionAnswerRequest.AnswerBean answerBean = new TeachingInspectionAnswerRequest.AnswerBean();
        answerBean.setId(questionDto.getId());
        answerBean.setType(questionDto.getType());
        answerBean.setText(editText.getText().toString());
        return answerBean;
    }

}
