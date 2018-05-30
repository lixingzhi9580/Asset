package com.cn.hnust.pojo;

import java.util.List;

public class DHVo {
	private List<DHFirstMenu> msg;
//	private String login_info;
	private String status;
	private DHPaging paging;
	
	public List<DHFirstMenu> getMsg() {
		return msg;
	}

	public void setMsg(List<DHFirstMenu> msg) {
		this.msg = msg;
	}

//	public String getLogin_info() {
//		return login_info;
//	}
//
//	public void setLogin_info(String login_info) {
//		this.login_info = login_info;
//	}
//
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public DHPaging getPaging() {
		return paging;
	}

	public void setPaging(DHPaging paging) {
		this.paging = paging;
	}
}
