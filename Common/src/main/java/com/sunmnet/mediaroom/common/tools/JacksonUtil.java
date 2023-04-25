package com.sunmnet.mediaroom.common.tools;

import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JacksonUtil {

    private static ObjectMapper objectMapper;

    private static void initObjectMapper() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS);
        objectMapper.setDateFormat(DateUtil.getDateFormat(DateUtil.DEFAULT_FORMAT));
    }

    public static ObjectMapper getObjectMapper() {
        if (objectMapper == null) {
            initObjectMapper();
        }
        return objectMapper;
    }

    private JacksonUtil() {
    }

    public static String objToJsonStr(Object obj) {
        try {
            return getObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            RunningLog.error(e);
        }
        return null;
    }

    public static <T> T jsonStrToBean(String jsonStr, Class<T> clazz) {
        try {
            return getObjectMapper().readValue(jsonStr, clazz);
        } catch (IOException e) {
            e.printStackTrace();
            RunningLog.error(e);
        }
        return null;
    }

    public static <T> T jsonStrToObj(String jsonStr, TypeReference<T> typeReference) {
        try {
            return getObjectMapper().readValue(jsonStr, typeReference);
        } catch (IOException e) {
            e.printStackTrace();
            RunningLog.error(e);
        }
        return null;
    }

    public static Map<String, Object> jsonStrToMap_SO(String jsonStr) {
        try {
            return getObjectMapper().readValue(jsonStr, new TypeReference<Map<String, Object>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            RunningLog.error(e);
        }
        return null;
    }

    public static Map<String, String> jsonStrToMap_SS(String jsonStr) {
        try {
            return getObjectMapper().readValue(jsonStr, new TypeReference<Map<String, String>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            RunningLog.error(e);
        }
        return null;
    }

    public static <T> Map<String, T> jsonStrToMap(String jsonStr, Class<T> clazz) {
        Map<String, Map<String, Object>> map = null;
        try {
            map = getObjectMapper().readValue(jsonStr,
                    new TypeReference<Map<String, Map<String, Object>>>() {
                    });
        } catch (IOException e) {
            e.printStackTrace();
            RunningLog.error(e);
        }
        if (map == null)
            return null;
        Map<String, T> result = new HashMap<String, T>();
        for (Map.Entry<String, Map<String, Object>> entry : map.entrySet()) {
            result.put(entry.getKey(), mapToBean(entry.getValue(), clazz));
        }
        return result;
    }

    public static <T> List<T> jsonStrToList(String jsonArrayStr, Class<T> clazz) {
        List<Map<String, Object>> list = null;
        try {
            list = getObjectMapper().readValue(jsonArrayStr,
                    new TypeReference<List<Map<String, Object>>>() {
                    });
        } catch (IOException e) {
            e.printStackTrace();
            RunningLog.error(e);
        }
        if (list == null)
            return null;
        List<T> result = new ArrayList<T>();
        for (Map<String, Object> map : list) {
            result.add(mapToBean(map, clazz));
        }
        return result;
    }

    public static List<Map<String, Object>> jsonStrToListMap_SO(String jsonArrayStr) {
        List<Map<String, Object>> list = null;
        try {
            list = getObjectMapper().readValue(jsonArrayStr,
                    new TypeReference<List<Map<String, Object>>>() {
                    });
        } catch (IOException e) {
            e.printStackTrace();
            RunningLog.error(e);
        }
        return list;
    }

    public static <T> T mapToBean(Map map, Class<T> clazz) {
        return getObjectMapper().convertValue(map, clazz);
    }

    public static String mapToJsonStr(Map map) {
        try {
            return getObjectMapper().writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            RunningLog.error(e);
        }
        return null;
    }

    public static String getValueJsonStr(String jsonStr, String key) {
        JsonNode rootNode = null;
        try {
            rootNode = getObjectMapper().readTree(jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
            RunningLog.error(e);
        }
        if (rootNode == null)
            return null;
        return rootNode.path(key).textValue();
    }

    public static Object getValueObj(String jsonStr, String key) {
        Map<String, Object> map = jsonStrToMap_SO(jsonStr);
        return map == null ? null : map.get(key);
    }
}
