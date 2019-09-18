package com.zljx.vo;

/**
 * @Author: sunzhihan
 * @Date：2019/09/02 11:31
 * @Version 1.0
 **/
public class HuaDou {
    /**{
     "addHuadou":7.62132,
     "calories":17.2,
     "ctime":"2019-06-06 21:22:17",
     "huaDou":22.868789,
     "id":1835952,
     "num":4300,
     "userId":"1136611290454822912",
     "utime":"2019-09-02 10:30:00"
     }*/

    private Integer addHuadou;
    private Integer calories;
    private Integer huaDou;
    private String ctime;
    private String userId;
    private String utime;
    private Integer id;
    private Integer num;

    public Integer getAddHuadou() {
        return addHuadou;
    }

    public void setAddHuadou(Integer addHuadou) {
        this.addHuadou = addHuadou;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public Integer getHuaDou() {
        return huaDou;
    }

    public void setHuaDou(Integer huaDou) {
        this.huaDou = huaDou;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUtime() {
        return utime;
    }

    public void setUtime(String utime) {
        this.utime = utime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "HuaDou{" +
                "addHuadou=" + addHuadou +
                ", calories=" + calories +
                ", huaDou=" + huaDou +
                ", ctime='" + ctime + '\'' +
                ", userId='" + userId + '\'' +
                ", utime='" + utime + '\'' +
                ", id=" + id +
                ", num=" + num +
                '}';
    }
}
