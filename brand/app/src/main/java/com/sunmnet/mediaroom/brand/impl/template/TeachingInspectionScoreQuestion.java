package com.sunmnet.mediaroom.brand.impl.template;

import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.sunmnet.mediaroom.brand.R;
import com.sunmnet.mediaroom.brand.bean.request.TeachingInspectionAnswerRequest;
import com.sunmnet.mediaroom.brand.bean.response.dto.TeachingInspectionQuestionDto;
import com.sunmnet.mediaroom.common.tools.TypeUtil;

public class TeachingInspectionScoreQuestion extends AbsTeachingInspectionQuestion {

    View view;
    EditText editTextScore;

    public TeachingInspectionScoreQuestion(TeachingInspectionQuestionDto questionDto) {
        super(questionDto);
    }

    @Override
    public View initView(LayoutInflater layoutInflater, ViewGroup parentView) {
        if (view == null) {
            view = layoutInflater.inflate(R.layout.item_teaching_inspection_score, parentView, false);
            ((TextView) view.findViewById(R.id.title)).setText(getTitle());
            ((TextView) view.findViewById(R.id.textScoreScale)).setText("评分（" + questionDto.getScore() + "分制）");
            editTextScore = view.findViewById(R.id.editText);
            editTextScore.setFilters(new InputFilter[]{new ScoreInputFilter(questionDto.getScore())});
        }
        return view;
    }

    @Override
    public View getView() {
        return view;
    }

    @Override
    public TeachingInspectionAnswerRequest.AnswerBean getAnswerData() {
        int intDef = TypeUtil.objToIntDef(editTextScore.getText().toString(), -1);
        if (intDef < 0) {
            return null;
        }
        TeachingInspectionAnswerRequest.AnswerBean answerBean = new TeachingInspectionAnswerRequest.AnswerBean();
        answerBean.setId(questionDto.getId());
        answerBean.setType(questionDto.getType());
        answerBean.setText(intDef + "");
        return answerBean;
    }

}
