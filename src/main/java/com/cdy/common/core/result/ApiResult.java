package com.cdy.common.core.result;

import org.apache.poi.ss.formula.functions.T;

/**
 * 通用 restful 返回
 * Created by 陈东一
 * 2018/6/23 23:02
 */
public class ApiResult {
    
    /** 错误码. */
    private Integer code;
    
    /** 提示信息. */
    private String msg;
    
    /** 具体内容. */
    private Object data;
    
    
    
    
    public static ApiResult success(Object o){
        return new ApiResult(200, "success", o);
    }
    
    public static ApiResult success(){
        return new ApiResult(200, "success", null);
    }
    
    public static ApiResult fail(){
        return new ApiResult(500, "failed", null);
    }
    
    public static ApiResult fail(String o){
        return new ApiResult(500, o, null);
    }
    
    
    public ApiResult() {
    }
    
    public ApiResult(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    
    public Integer getCode() {
        return code;
    }
    
    public void setCode(Integer code) {
        this.code = code;
    }
    
    public String getMsg() {
        return msg;
    }
    
    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    public Object getData() {
        return data;
    }
    
    public void setData(T data) {
        this.data = data;
    }
}
