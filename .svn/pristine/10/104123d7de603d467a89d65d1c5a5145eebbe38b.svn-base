package com.sunmnet.mediaroom.brand.impl.template;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.sunmnet.mediaroom.brand.R;
import com.sunmnet.mediaroom.brand.bean.request.TeachingInspectionAnswerRequest;
import com.sunmnet.mediaroom.brand.bean.response.dto.TeachingInspectionQuestionDto;
import com.sunmnet.mediaroom.brand.bean.response.dto.TeachingInspectionSubQuestionDto;

import java.util.ArrayList;
import java.util.List;

public class TeachingInspectionDegreeArrayQuestion extends AbsTeachingInspectionQuestion {

    LinearLayout view;
    List<RadioGroup> radioGroupList;

    public TeachingInspectionDegreeArrayQuestion(TeachingInspectionQuestionDto questionDto) {
        super(questionDto);
    }

    @Override
    public View initView(LayoutInflater layoutInflater, ViewGroup parentView) {
        if (view == null) {
            view = (LinearLayout) layoutInflater.inflate(R.layout.item_teaching_inspection_degree_array, parentView, false);
            ((TextView) view.findViewById(R.id.title)).setText(getTitle());

            int starBeginNum = questionDto.getStarBeginNum();
            int starNum = questionDto.getStarNum();
            int baseId = (int) (System.currentTimeMillis() & 0x001FFFFF);
            radioGroupList = new ArrayList<>();
            for (TeachingInspectionSubQuestionDto subQuestionDto : questionDto.getSubQuestions()) {
                View itemView = layoutInflater.inflate(R.layout.item_teaching_inspection_degree_array_item, view, false);
                ((TextView) itemView.findViewById(R.id.describe)).setText(subQuestionDto.getTitle());
                RadioGroup radioGroup = itemView.findViewById(R.id.radioGroup);
                radioGroup.setTag(subQuestionDto.getId());
                for (int i = starBeginNum; i < starBeginNum + starNum; i++) {
                    RadioButton degreeItem = (RadioButton) layoutInflater.inflate(R.layout.item_teaching_inspection_degree_item, radioGroup, false);
                    degreeItem.setId(baseId);
                    baseId++;
                    String degree = "" + i;
                    degreeItem.setText(degree);
                    degreeItem.setTag(degree);
                    radioGroup.addView(degreeItem);
                }
                radioGroupList.add(radioGroup);
                view.addView(itemView);
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
        TeachingInspectionAnswerRequest.AnswerBean answerBean = new TeachingInspectionAnswerRequest.AnswerBean();
        answerBean.setId(questionDto.getId());
        answerBean.setType(questionDto.getType());
        List<TeachingInspectionAnswerRequest.AnswerBean.GroupBean> groups = new ArrayList<>();
        answerBean.setGroups(groups);
        for (RadioGroup radioGroup : radioGroupList) {
            int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
            if (checkedRadioButtonId < 0) {
                return null;
            }
            TeachingInspectionAnswerRequest.AnswerBean.GroupBean groupsBean = new TeachingInspectionAnswerRequest.AnswerBean.GroupBean();
            groupsBean.setId(radioGroup.getTag().toString());
            groupsBean.setText(((RadioButton) radioGroup.findViewById(checkedRadioButtonId)).getText().toString());
            groups.add(groupsBean);
        }
        return answerBean;
    }
}
