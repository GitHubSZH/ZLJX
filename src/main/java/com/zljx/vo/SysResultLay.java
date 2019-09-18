package com.zljx.vo;

public class SysResultLay {
    private Integer code;
    private String msg;
    private Integer count;
    private Object data;

    public SysResultLay(){

    }
    public static SysResultLay oK(Object data) {
        return new SysResultLay(data);
    }
    public static SysResultLay msg(String msg) {
        return new SysResultLay(msg);
    }

    public static SysResultLay build(Integer code, String msg) {
        return new SysResultLay(code, msg, null);
    }

    public SysResultLay(Object data) {
        this.code = 0;
        this.msg = "OK";
        this.data = data;
    }
    public SysResultLay(String msg) {
        this.code = 0;
        this.msg = msg;
        this.data = null;
    }

    public SysResultLay(Integer code, String msg, Integer count, Object data) {
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }

    public SysResultLay(Integer code, String msg,  Object data) {
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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
