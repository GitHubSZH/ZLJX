package com.zljx.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * VO:封装控制层要返回到客户端的数据
 * @author tarena
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonLaiUIResult{
	/**我们自己设置的服务端向客户端输出的状态码*/
    private int code=0;//"1,ok;0,false"
    /**状态码state对应的具体信息*/
    private String msg="ok";
    /**业务层返回给控制层的具体数据*/
    public JsonLaiUIResult(Throwable e) {
    	
    	this.code=1;
    	this.msg=e.getMessage();
    	
	}
	public JsonLaiUIResult(){}
    public JsonLaiUIResult(String msg) {
    	this.msg=msg;
	}
    public JsonLaiUIResult(Object data) {
    	this.data=data;
	}
    
    private int count ;
    public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	private Object data;
    
 
      
}