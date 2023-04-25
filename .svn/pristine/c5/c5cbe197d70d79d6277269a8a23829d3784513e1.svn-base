package com.sunmnet.mediaroom.common.tools;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;


public class GsonUtil {

    private static Gson gson = new Gson();

    public static String objToJsonStr(Object obj) {
        return gson.toJson(obj);
    }

    public static String mapToJsonStr(Map<String, ?> jsonMap) {
        return gson.toJson(jsonMap);
    }

    public static Map<?, ?> jsonStrToMap(String jsonStr) {
        Type type = new TypeToken<Map<?, ?>>() {
        }.getType();
        Map<String, Object> map = null;
        try {
            map = gson.fromJson(jsonStr, type);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static Map<String, Object> jsonStrToMap_SO(String jsonStr) {
        Type type = new TypeToken<Map<String, Object>>() {
        }.getType();
        Map<String, Object> map = null;
        try {
            map = gson.fromJson(jsonStr, type);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static Map<String, String> jsonStrToMap_SS(String jsonStr) {
        Type type = new TypeToken<Map<String, String>>() {
        }.getType();
        Map<String, String> map = null;
        try {
            map = gson.fromJson(jsonStr, type);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static List<Map<String, Object>> jsonStrToMapList_SO(String jsonStr) {
        Type type = new TypeToken<List<Map<String, Object>>>() {
        }.getType();
        List<Map<String, Object>> list = null;
        try {
            list = gson.fromJson(jsonStr, type);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<Map<String, String>> jsonStrToMapList_SS(String jsonStr) {
        Type type = new TypeToken<List<Map<String, String>>>() {
        }.getType();
        List<Map<String, String>> list = null;
        try {
            list = gson.fromJson(jsonStr, type);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static <T> T jsonStrToBean(String jsonStr, Class<T> bean) {
        T obj = null;
        try {
            obj = gson.fromJson(jsonStr, bean);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static <T> T jsonStrToObj(String jsonStr, Type typeOfT) {
        T obj = null;
        try {
            obj = gson.fromJson(jsonStr, typeOfT);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static <T> List<T> jsonStrToList(String jsonStr, Class<T> bean) {
        Type type = new TypeToken<List<T>>() {
        }.getType();
        List<T> obj = null;
        try {
            obj = gson.fromJson(jsonStr, type);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static String getValueJsonStr(String jsonStr, String key) {
        JsonObject obj = null;
        try {
            obj = gson.fromJson(jsonStr, JsonObject.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return (obj == null || obj.get(key) == null) ? null : obj.get(key).toString();
    }

    public static Object getValueObj(String jsonStr, String key) {
        Map<String, Object> map = null;
        try {
            map = jsonStrToMap_SO(jsonStr);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return map == null ? null : map.get(key);
    }

    public static <T> T mapToBean(Map map, Class<T> clazz) {
        String json = gson.toJson(map);
        if (json == null)
            return null;
        T obj = null;
        try {
            obj = gson.fromJson(json, clazz);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return obj;
    }


}
