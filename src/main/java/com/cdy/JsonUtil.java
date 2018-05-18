package com.cdy;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Arrays;

/**
 * todo
 * Created by 陈东一
 * 2018/5/15 22:30
 */
public class JsonUtil {
    
    public static String object2String(Object o) throws JsonProcessingException {
        return object2StringByJackson(o);
    }
    public static <T> T string2Object(String json, Class<T> clazz) throws IOException {
        return string2ObjectByJackson(json, clazz);
    }
    
    private static String object2StringByJackson(Object o) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(o);
    }
    
    private static String object2StringByFastJson(Object o) {
        return JSONObject.toJSONString(o);
    }
    
    private static <T> T string2ObjectByJackson(String json, Class<T> clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, clazz);
    }
    
    private static <T> T string2ObjectByFastJson(String json, Class<T> clazz) throws IOException {
        return JSONObject.parseObject(json, clazz);
    }
    
    public static void main(String[] args) throws IOException {
        String[] test = new String[2];
        test[0] = "123";
        test[1] = "456";
        String s = object2String(test);
        String[] strings = string2Object(s, String[].class);
        System.out.println(Arrays.toString(strings));
        
    }
    
}
