package com.cdy;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * todo
 * Created by 陈东一
 * 2018/5/20 13:51
 */
public class CookieUtil {
    
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
    
    public static void addCookie(String key, String value, int time, HttpServletResponse response){
        Cookie cookie = new Cookie(key, value);//创建新cookie
        cookie.setMaxAge(time);// 设置存在时间为5分钟
        cookie.setPath("/");//设置作用域
        response.addCookie(cookie);//将cookie添加到response的cookie数组中返回给客户端
    }
    
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
