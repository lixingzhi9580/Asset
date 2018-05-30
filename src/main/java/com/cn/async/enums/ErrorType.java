package com.cn.async.enums;

/**
 * 
 * @author Jacky Yu
 *
 */
public enum ErrorType {
	
	NET_ERROR(0,"通讯错误"),
	TIMEOUT_ERROR(1,"通讯超时")
	;
	
	//
	private final int code;
	private final String message;
	
	private ErrorType(int errorCode, String errorMessage){
		this.code = errorCode;
		this.message = errorMessage;
	}

	public Integer getErrorCode(){
		return code;
	}
	
	public String getErrorMessage(){
		return message;
	}
	
}
