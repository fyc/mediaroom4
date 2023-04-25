package com.sunmnet.mediaroom.brand.impl.template;

import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.sunmnet.mediaroom.brand.bean.response.dto.TeachingInspectionQuestionDto;
import com.sunmnet.mediaroom.brand.interfaces.template.ITeachingInspectionQuestion;
import com.sunmnet.mediaroom.common.tools.TypeUtil;


public abstract class AbsTeachingInspectionQuestion implements ITeachingInspectionQuestion {

    protected TeachingInspectionQuestionDto questionDto;
    protected int questionNo;

    public AbsTeachingInspectionQuestion(TeachingInspectionQuestionDto questionDto) {
        this.questionDto = questionDto;
    }

    @Override
    public void setQuestionNo(int no) {
        questionNo = no;
    }

    @Override
    public boolean isRequired() {
        return questionDto != null && questionDto.isRequired();
    }


    protected CharSequence getTitle(){
        String title = questionDto.getTitle();
        if (questionNo > 0) {
            title = questionNo + ". " + title;
        }
        SpannableStringBuilder builder = new SpannableStringBuilder(title);
        if (questionDto.isRequired()) {
            builder.append(" *");
            builder.setSpan(new ForegroundColorSpan(0xFFFF0000), builder.length() - 1, builder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return builder;
    }


    public static class ScoreInputFilter implements InputFilter {
        int maxScore;

        public ScoreInputFilter(int maxScore) {
            this.maxScore = maxScore;
        }

        /**
         * @param source 新输入的字符串
         * @param start  新输入的字符串起始下标，一般为0
         * @param end    新输入的字符串终点下标，一般为source长度-1
         * @param dest   输入之前文本框内容
         * @param dstart 原内容前半段终止坐标
         * @param dend   原内容后半段起始坐标
         * @return 输入内容
         */
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            if (source.length() == 0 || end - start == 0 || dest.length() - dend + dstart == 0) {
                return null;
            }
            String sourceText = source.subSequence(start, end).toString();
            String destText = dest.toString();
            StringBuilder totalText = new StringBuilder();
            totalText.append(destText.substring(0, dstart))
                    .append(sourceText)
                    .append(destText.substring(dend));

            if (totalText.length() == 0)
                return null;
            int intDef = TypeUtil.objToIntDef(totalText.toString(), -1);
            if (intDef > maxScore || intDef < 0) {
                return "";
            }

            return sourceText;
        }
    }
}
