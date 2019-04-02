package com.zljx.vo;

public class PicUploadResult {
    /**
     *   "code": 0
     *   ,"msg": ""
     *   ,"data": {
     *     "src": "http://cdn.layui.com/123.jpg"
     *   }
     *
     * */
    private Integer code = 0;
    private String msg;
    private Object data;

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

    public void setData(Object data) {
        this.data = data;
    }
}
