package com.loujie.www.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;

/**
 * @name loujie
 * @date 2019/1/30
 */
public class JsonGsonUtils {

    static Gson jsonUtils = new GsonBuilder()
            .setLenient()// json宽松
            .enableComplexMapKeySerialization()//支持Map的key为复杂对象的形式
            // .serializeNulls() //智能null
            // .setPrettyPrinting()// 调教格式
            .disableHtmlEscaping() //默认是GSON把HTML 转义的
            .create();

    public static String toJson(Object obj) {
        try {
            return jsonUtils.toJson(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T parse(String jsonString, Class<T> cla) {
        try {
            return jsonUtils.fromJson(jsonString, cla);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, Object> parse(String jsonString) {
        return parse(jsonString, Map.class);
    }

}
