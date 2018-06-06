package com.cdy;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5工具类
 * Created by 陈东一
 * 2018/6/5 21:31
 */
public class MD5Util {
    
    private static String md5 = "MD5";
    private static String charsetName = "UTF-8";
    
    private static byte[] md5(String s) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MessageDigest algorithm;
        algorithm = MessageDigest.getInstance(md5);
            algorithm.reset();
        algorithm.update(s.getBytes(charsetName));
        return algorithm.digest();
    }
    
    private static String toHex(byte hash[]) {
        if (hash == null) {
            return null;
        }
        StringBuffer buf = new StringBuffer(hash.length * 2);
        int i;
        
        for (i = 0; i < hash.length; i++) {
            if ((hash[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString(hash[i] & 0xff, 16));
        }
        return buf.toString();
    }
    
    public static String hash(String s) throws UnsupportedEncodingException, NoSuchAlgorithmException {
            return new String(toHex(md5(s)).getBytes(charsetName), charsetName);
    }
    
    /**
     * 对密码按照用户名，密码，盐进行加密
     * @param username 用户名
     * @param password 密码
     * @param salt 盐
     * @return
     */
    public static String encryptPassword(String username, String password, String salt) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return MD5Util.hash(username + password + salt);
    }
    
    /**
     * 对密码按照密码，盐进行加密
     * @param password 密码
     * @param salt 盐
     * @return
     */
    public static String encryptPassword(String password, String salt) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return MD5Util.hash(password + salt);
    }
    
}
