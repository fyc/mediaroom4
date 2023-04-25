package com.sunmnet.mediaroom.brand.bean.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.sunmnet.mediaroom.brand.bean.control.base.ControlProperty;
import com.sunmnet.mediaroom.brand.bean.control.base.UnknownControlProperty;
import com.sunmnet.mediaroom.brand.interfaces.control.IBaseControl;
import com.sunmnet.mediaroom.brand.utils.ControlBuilder;
import com.sunmnet.mediaroom.common.tools.JacksonUtil;

import java.io.IOException;
import java.util.List;

public class ControlPropertyDeserializer extends JsonDeserializer<ControlProperty> {

    @Override
    public ControlProperty deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        String type = node.get("type").asText();
        Class<? extends IBaseControl> cls = ControlBuilder.getControlClass(type);
        if (cls != null) {
            return JacksonUtil.jsonStrToBean(node.toString(), ControlBuilder.getPropertyClass(cls));
        } else {
            return JacksonUtil.jsonStrToBean(node.toString(), UnknownControlProperty.class);
        }
    }

    public static void main(String[] args) {
        String js = "[{\"type\":\"text\",\"id\":\"text-1\",\"name\":\"文本-1\",\"style\":{\"zIndex\":1,\"left\":252,\"top\":285,\"width\":124,\"height\":50,\"backgroundColor\":\"rgba(255, 255, 255, 0)\",\"color\":\"#ffffff\",\"underline\":false,\"italic\":false,\"bold\":false,\"fontSize\":\"30\",\"textAlign\":\"center\",\"fontFamily\":\"simsun\"},\"attr\":{\"contents\":[{\"text\":\"任课老师\",\"signature\":\"\"}],\"textState\":0,\"rollDirection\":\"none\",\"rollSpeed\":10},\"isTemplate\":true}]";
        List<ControlProperty> list = JacksonUtil.jsonStrToList(js, ControlProperty.class);
        System.out.println(list.toString());
    }
}
