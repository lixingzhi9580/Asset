package com.cn.hnust.pojo;


/**
 * 
 * 
 * 
 **/
public class UserTt {

	/**UUID**/
	private String uuid;

	/**编号**/
	private String id;

	/**姓名**/
	private String userName;

	/**密码**/
	private String password;

	/**年龄**/
	private Integer age;



	public void setUuid(String uuid){
		this.uuid = uuid;
	}

	public String getUuid(){
		return this.uuid;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}

	public void setUserName(String userName){
		this.userName = userName;
	}

	public String getUserName(){
		return this.userName;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return this.password;
	}

	public void setAge(Integer age){
		this.age = age;
	}

	public Integer getAge(){
		return this.age;
	}

}
