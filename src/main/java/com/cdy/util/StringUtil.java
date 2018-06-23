package com.cdy.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * Created by 陈东一
 * 2018/5/15 22:31
 */
public class StringUtil {
    
    //邮箱，手机，姓名，昵称，身份证号，银行卡号等；
    public static final String MAIL_PATTERN =     "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
    public static final String MOBILE_PATTERN =   "^1[3|4|5|7|8][0-9]\\d{8}$";
    public static final String NAME_PATTERN =     "^[\\u4E00-\\u9FBF][\\u4E00-\\u9FBF(.|·)]{0,13}[\\u4E00-\\u9FBF]$";
    public static final String NICKNAME_PATTERN = "^((?!\\d{5})[\\u4E00-\\u9FBF(.|·)|0-9A-Za-z_]){2,11}$";
    public static final String PASSWORD_PATTERN = "^[\\s\\S]{6,30}$";
    public static final String CODE_PATTERN =     "^0\\d{2,4}$";
    public static final String POSTCODE_PATTERN = "^\\d{6}$";
    public static final String ID_PATTERN =       "^\\d{6}(\\d{8}|\\d{11})[0-9a-zA-Z]$";
    public static final String BANK_CARD_PATTERN ="^\\d{16,30}$";
    
    
    /**
     * 判断是否为空
     * @param string String
     * @return Boolean
     */
    public static boolean isEmpty(String string){
        return string == null || string.length() == 0;
    }
    
    /**
     * 判断是否不为空
     * @param string String
     * @return Boolean
     */
    public static boolean isNotEmpty(String string){
        return !isEmpty(string);
    }
    
    /**
     * 判断是否为空白
     * @param string String
     * @return boolean
     */
    public static boolean isBlank(String string){
        int strLen;
        if (string == null || (strLen = string.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(string.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * 判断是否为不空白
     * @param string String
     * @return boolean
     */
    public static boolean isNotBlank(String string){
        return !isBlank(string);
    }
    
    /**
     * 正则检查是否匹配
     * @param string 需要验证的字符串
     * @param regex 正则表达式
     * @param ignoreCase 是否忽略大小写
     * @return boolean
     */
    public static boolean match(String string, String regex, boolean ignoreCase){
        Pattern pattern;
        // 忽略大小写的写法
        if (ignoreCase) {
            pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        } else {
            pattern = Pattern.compile(regex);
        }
        Matcher matcher = pattern.matcher(string);
        // 字符串是否与正则表达式相匹配
        return matcher.matches();
    }
    
    public static boolean match(String string, String regex){
        return match(string, regex, true);
    }
    
    
    /**
     * 手机号中间四位自定义替换
     *
     * @param mobile
     * @param transCode 中间四位目标值 如GXJF 将136GXJF1111
     * @return
     */
    public static String maskMobile(String mobile, String transCode) {
        if (match(mobile, MOBILE_PATTERN)) {
            transCode = StringUtil.isEmpty(transCode) ? "****" : transCode;
            return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", String.format("$1%s$2", transCode));
        }
        return mobile;
    }
    
    /**
     * 邮箱地址加星号
     *
     * @param email
     * @return
     */
    public static String maskEmail(String email) {
        if (match(email, MAIL_PATTERN)) {
            String userName = email.substring(0, email.indexOf("@"));
            int len = userName.length();
            if (len >= 5) {
                int total = len - 3;
                int half = total / 2;
                int start = half;
                int end = len - half;
                if (total % 2 != 0) {
                    end = end - 1;
                }
                StringBuilder sb = new StringBuilder(email);
                for (int i = start; i < end; i++) {
                    sb.setCharAt(i, '*');
                }
                return sb.toString();
            }
        }
        return email;
    }
    
    /**
     * 账号中间四位自定义替换
     *
     * @param account
     * @return
     */
    public static String maskTradeAccount(String account) {
        return account.replaceAll("(\\d{7})\\d*(\\d{4})", "$1****$2");
    }
    
}
