package com.cn.hnust.pojo;


/**
 * 
 * 
 * 
 **/
public class MinshenArea {

	/****/
	private String code;

	/****/
	private String prov;

	/****/
	private String parentId;

	/****/
	private String areaTyp;

	/****/
	private String city;

	/****/
	private String dist;



	public void setCode(String code){
		this.code = code;
	}

	public String getCode(){
		return this.code;
	}

	public void setProv(String prov){
		this.prov = prov;
	}

	public String getProv(){
		return this.prov;
	}

	public void setParentId(String parentId){
		this.parentId = parentId;
	}

	public String getParentId(){
		return this.parentId;
	}

	public void setAreaTyp(String areaTyp){
		this.areaTyp = areaTyp;
	}

	public String getAreaTyp(){
		return this.areaTyp;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return this.city;
	}

	public void setDist(String dist){
		this.dist = dist;
	}

	public String getDist(){
		return this.dist;
	}

}
