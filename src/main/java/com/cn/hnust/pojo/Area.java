package com.cn.hnust.pojo;


/**
 * 
 * 
 * 
 **/
public class Area {

	/****/
	private String code;

	/****/
	private String areaNm;

	/****/
	private String prov;

	/****/
	private String city;

	/****/
	private String dist;

	/****/
	private String areaTyp;

	/****/
	private String parentId;

	/****/
	private String searchCode;

	/****/
	private String searchName;

	/****/
	private String errMsg;



	public void setCode(String code){
		this.code = code;
	}

	public String getCode(){
		return this.code;
	}

	public void setAreaNm(String areaNm){
		this.areaNm = areaNm;
	}

	public String getAreaNm(){
		return this.areaNm;
	}

	public void setProv(String prov){
		this.prov = prov;
	}

	public String getProv(){
		return this.prov;
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

	public void setAreaTyp(String areaTyp){
		this.areaTyp = areaTyp;
	}

	public String getAreaTyp(){
		return this.areaTyp;
	}

	public void setParentId(String parentId){
		this.parentId = parentId;
	}

	public String getParentId(){
		return this.parentId;
	}

	public void setSearchCode(String searchCode){
		this.searchCode = searchCode;
	}

	public String getSearchCode(){
		return this.searchCode;
	}

	public void setSearchName(String searchName){
		this.searchName = searchName;
	}

	public String getSearchName(){
		return this.searchName;
	}

	public void setErrMsg(String errMsg){
		this.errMsg = errMsg;
	}

	public String getErrMsg(){
		return this.errMsg;
	}

	@Override
	public String toString() {
		return "Area [code=" + code + ", areaNm=" + areaNm + ", prov=" + prov + ", city=" + city + ", dist=" + dist
				+ ", areaTyp=" + areaTyp + ", parentId=" + parentId + ", searchCode=" + searchCode + ", searchName="
				+ searchName + ", errMsg=" + errMsg + "]";
	}

}
