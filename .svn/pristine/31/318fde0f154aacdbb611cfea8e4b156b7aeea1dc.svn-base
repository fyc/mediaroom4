package com.sunmnet.mediaroom.brand.data.database.play;

import com.sunmnet.mediaroom.brand.bean.play.ProgramInfo;
import com.sunmnet.mediaroom.common.tools.JacksonUtil;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.List;

public class ProgramInfoPropertyConverter implements PropertyConverter<List<ProgramInfo>, String> {

    @Override
    public List<ProgramInfo> convertToEntityProperty(String databaseValue) {
        return JacksonUtil.jsonStrToList(databaseValue, ProgramInfo.class);
    }

    @Override
    public String convertToDatabaseValue(List<ProgramInfo> entityProperty) {
        return JacksonUtil.objToJsonStr(entityProperty);
    }
}
