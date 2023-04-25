package com.sunmnet.mediaroom.brand.data.database.play;

import com.sunmnet.mediaroom.brand.bean.play.SignatureContent;
import com.sunmnet.mediaroom.common.tools.JacksonUtil;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.List;

public class NotificationContentPropertyConverter implements PropertyConverter<List<SignatureContent>, String> {


    @Override
    public List<SignatureContent> convertToEntityProperty(String databaseValue) {
        return JacksonUtil.jsonStrToList(databaseValue, SignatureContent.class);
    }

    @Override
    public String convertToDatabaseValue(List<SignatureContent> entityProperty) {
        return JacksonUtil.objToJsonStr(entityProperty);
    }
}
