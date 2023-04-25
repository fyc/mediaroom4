package com.sunmnet.mediaroom.brand.impl.template;

import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sunmnet.mediaroom.brand.R;
import com.sunmnet.mediaroom.brand.bean.request.TeachingInspectionAnswerRequest;
import com.sunmnet.mediaroom.brand.bean.response.dto.TeachingInspectionQuestionDto;
import com.sunmnet.mediaroom.brand.bean.response.dto.TeachingInspectionSubQuestionDto;
import com.sunmnet.mediaroom.common.tools.TypeUtil;

import java.util.ArrayList;
import java.util.List;

public class TeachingInspectionScoreArrayQuestion extends AbsTeachingInspectionQuestion {

    LinearLayout view;
    List<EditText> editTextList;

    public TeachingInspectionScoreArrayQuestion(TeachingInspectionQuestionDto questionDto) {
        super(questionDto);
    }

    @Override
    public View initView(LayoutInflater layoutInflater, ViewGroup parentView) {
        if (view == null) {
            view = (LinearLayout) layoutInflater.inflate(R.layout.item_teaching_inspection_score_array, parentView, false);
            ((TextView) view.findViewById(R.id.title)).setText(getTitle());
            ((TextView) view.findViewById(R.id.textScoreScale)).setText("评分（" + questionDto.getScore() + "分制）");
            editTextList = new ArrayList<>();
            for (TeachingInspectionSubQuestionDto subQuestionDto : questionDto.getSubQuestions()) {
                View itemView = layoutInflater.inflate(R.layout.item_teaching_inspection_score_array_item, view, false);
                ((TextView) itemView.findViewById(R.id.describe)).setText(subQuestionDto.getTitle());
                EditText editText = itemView.findViewById(R.id.editText);
                editText.setFilters(new InputFilter[]{new ScoreInputFilter(questionDto.getScore())});
                editText.setTag(subQuestionDto.getId());
                editTextList.add(editText);
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
        for (EditText editText : editTextList) {
            int intDef = TypeUtil.objToIntDef(editText.getText().toString(), -1);
            if (intDef < 0) {
                return null;
            }
            TeachingInspectionAnswerRequest.AnswerBean.GroupBean groupsBean = new TeachingInspectionAnswerRequest.AnswerBean.GroupBean();
            groupsBean.setId(editText.getTag().toString());
            groupsBean.setText(editText.getText().toString());
            groups.add(groupsBean);
        }
        return answerBean;
    }
}
