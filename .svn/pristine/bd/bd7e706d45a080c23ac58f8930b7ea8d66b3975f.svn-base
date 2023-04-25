package com.sunmnet.mediaroom.brand.data.database.play;

import com.sunmnet.mediaroom.brand.bean.play.TemplateProgramInfo;
import com.sunmnet.mediaroom.common.tools.JacksonUtil;

import org.greenrobot.greendao.converter.PropertyConverter;

public class TemplateProgramInfoPropertyConverter implements PropertyConverter<TemplateProgramInfo, String> {

    @Override
    public TemplateProgramInfo convertToEntityProperty(String databaseValue) {
        return JacksonUtil.jsonStrToBean(databaseValue, TemplateProgramInfo.class);
    }

    @Override
    public String convertToDatabaseValue(TemplateProgramInfo entityProperty) {
        return JacksonUtil.objToJsonStr(entityProperty);
    }
}
