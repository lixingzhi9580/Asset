package com.cn.hnust.utils;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

public class Utils {
	
	
	public static final String Success = "1";
	public static final String Fail = "0";
	public static final String RETURNURL="returnUrl";
	
	public static String getUUid(){
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	/**
	 * 取得上一页的路径。
	 * 
	 * @param request
	 * @return
	 */
	public static String getPrePage(HttpServletRequest request) {
		if(StringUtils.isEmpty(request.getParameter(RETURNURL))){
			return request.getHeader("Referer");
		}
		return request.getParameter(RETURNURL);
	}
}
