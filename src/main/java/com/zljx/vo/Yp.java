package com.zljx.vo;

/**
 * @Author: sunzhihan
 * @Date：2019/09/02 11:54
 * @Version 1.0
 **/
public class Yp {
    /*{"code":"9999","msg":"已达每日计算上限4300步","sign":null,"success":true,"resp":null}*/
    private String code;
    private String msg;
    private String sign;
    private HuaDou resp;
    private boolean success;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public HuaDou getResp() {
        return resp;
    }

    public void setResp(HuaDou resp) {
        this.resp = resp;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "Yp{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", sign='" + sign + '\'' +
                ", resp=" + resp +
                ", success=" + success +
                '}';
    }
}
