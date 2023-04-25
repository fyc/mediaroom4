package com.sunmnet.mediaroom.brand.impl.template;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.sunmnet.mediaroom.brand.R;
import com.sunmnet.mediaroom.brand.bean.request.TeachingInspectionAnswerRequest;
import com.sunmnet.mediaroom.brand.bean.response.dto.TeachingInspectionQuestionDto;

public class TeachingInspectionDegreeQuestion extends AbsTeachingInspectionQuestion {

    View view;
    RadioGroup radioGroup;

    public TeachingInspectionDegreeQuestion(TeachingInspectionQuestionDto questionDto) {
        super(questionDto);
    }

    @Override
    public View initView(LayoutInflater layoutInflater, ViewGroup parentView) {
        if (view == null) {
            view = layoutInflater.inflate(R.layout.item_teaching_inspection_degree, parentView, false);
            ((TextView) view.findViewById(R.id.title)).setText(getTitle());
            radioGroup = view.findViewById(R.id.radioGroup);
            int starBeginNum = questionDto.getStarBeginNum();
            int starNum = questionDto.getStarNum();
            int baseId = (int) (System.currentTimeMillis() & 0x001FFFFF);
            for (int i = starBeginNum; i < starBeginNum + starNum; i++) {
                RadioButton degreeItem = (RadioButton) layoutInflater.inflate(R.layout.item_teaching_inspection_degree_item, radioGroup, false);
                degreeItem.setId(baseId);
                baseId++;
                String degree = "" + i;
                degreeItem.setText(degree);
                radioGroup.addView(degreeItem);
            }
        }
        return view;
    }

    @Override
    public View getView() {
        return view;
    }

    @Override
    public TeachingInspectionAnswerRequest.AnswerBean getAnswerData() {
        int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        if (checkedRadioButtonId < 0) {
            return null;
        }
        TeachingInspectionAnswerRequest.AnswerBean answerBean = new TeachingInspectionAnswerRequest.AnswerBean();
        answerBean.setId(questionDto.getId());
        answerBean.setType(questionDto.getType());
        String s = ((RadioButton) radioGroup.findViewById(checkedRadioButtonId)).getText().toString();
        answerBean.setText(s);
        return answerBean;
    }
}
