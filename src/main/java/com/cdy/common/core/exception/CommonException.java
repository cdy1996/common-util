package com.cdy.common.core.exception;

/**
 * 通用异常类
 * Created by 陈东一
 * 2018/6/23 23:02
 */
public class CommonException extends RuntimeException {
    
    
    public CommonException(String message) {
        super(message);
    }
    
    public CommonException() {
    }
    
}
