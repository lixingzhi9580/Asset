package com.cn.hnust.pojo;


/**
 * 
 * 
 * 
 **/
public class Message {

	/****/
	private String uuid;

	/****/
	private String data;

	/****/
	private String request;

	/****/
	private String Response;

	/****/
	private String flg;



	public void setUuid(String uuid){
		this.uuid = uuid;
	}

	public String getUuid(){
		return this.uuid;
	}

	public void setData(String data){
		this.data = data;
	}

	public String getData(){
		return this.data;
	}

	public void setRequest(String request){
		this.request = request;
	}

	public String getRequest(){
		return this.request;
	}

	public void setResponse(String Response){
		this.Response = Response;
	}

	public String getResponse(){
		return this.Response;
	}

	public void setFlg(String flg){
		this.flg = flg;
	}

	public String getFlg(){
		return this.flg;
	}

}
