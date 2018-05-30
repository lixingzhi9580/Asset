package com.cn.hnust.pojo;


/**
 * 
 * 
 * 
 **/
public class DhDesc {

	/****/
	private Integer uuid;

	/****/
	private String gameOrdersn;

	/****/
	private String gameKey;

	/****/
	private String gameValue;



	public void setUuid(Integer uuid){
		this.uuid = uuid;
	}

	public Integer getUuid(){
		return this.uuid;
	}

	public void setGameOrdersn(String gameOrdersn){
		this.gameOrdersn = gameOrdersn;
	}

	public String getGameOrdersn(){
		return this.gameOrdersn;
	}

	public void setGameKey(String gameKey){
		this.gameKey = gameKey;
	}

	public String getGameKey(){
		return this.gameKey;
	}

	public void setGameValue(String gameValue){
		this.gameValue = gameValue;
	}

	public String getGameValue(){
		return this.gameValue;
	}

}
