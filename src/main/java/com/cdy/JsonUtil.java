package com.cdy;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        List<List<String>> list = new ArrayList<>();
        list.add(ListUtil.of("123","456"));
        list.add(ListUtil.of("789"));
        String s = object2String(list);
        List strings = string2Object(s, List.class);
        System.out.println(strings);
        
    }
    
}
