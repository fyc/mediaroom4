package com.sunmnet.mediaroom.brand.impl.template;

import com.sunmnet.mediaroom.brand.bean.response.dto.TeachingInspectionQuestionDto;
import com.sunmnet.mediaroom.brand.interfaces.template.ITeachingInspectionQuestion;

public class TeachingInspectionQuestionFactory {

    public ITeachingInspectionQuestion getQuestion(TeachingInspectionQuestionDto questionDto) {
        switch (questionDto.getType()) {
            case 1:
                //文本题
                return new TeachingInspectionTextQuestion(questionDto);
            case 2:
                //评分题
                return new TeachingInspectionScoreQuestion(questionDto);
            case 3:
                //量表题
                return new TeachingInspectionDegreeQuestion(questionDto);
            case 4:
                //矩阵评分题
                return new TeachingInspectionScoreArrayQuestion(questionDto);
            case 5:
                //矩阵量表题
                return new TeachingInspectionDegreeArrayQuestion(questionDto);
        }
        return null;
    }

}
