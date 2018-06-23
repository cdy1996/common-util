package com.cdy.util.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * cookie工具类
 * Created by 陈东一
 * 2018/5/20 13:51
 */
public class CookieUtil {
    
    /**
     * 从cookie中获取对应key的值
     * @param key 对应value的key
     * @param request HttpServletRequest
     * @return String
     */
    public static String getCookie(String key , HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for (Cookie cookie : cookies) {
                if (key.equalsIgnoreCase(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
    
    /**
     * 添加key，value到cookie中，作用域为顶级域
     * @param key String
     * @param value String
     * @param time 保存的时长
     * @param response HttpServletResponse
     */
    public static void addCookie(String key, String value, int time, HttpServletResponse response){
        Cookie cookie = new Cookie(key, value);//创建新cookie
        cookie.setMaxAge(time);// 设置存在时间为5分钟
        cookie.setPath("/");//设置作用域
        response.addCookie(cookie);//将cookie添加到response的cookie数组中返回给客户端
    }
    
    /**
     * 删除cookie中的key
     * @param key String
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    public static void delCookie(String key, HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (key.equalsIgnoreCase(cookie.getName())) {
                cookie.setValue(null);
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }
    }
}
