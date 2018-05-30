package com.cn.async.bean;

import com.cn.async.enums.ErrorType;


/**
 * 
 * @author Jacky Yu
 *
 * @param <I> input
 * @param <O> output
 */
public class SyncBean<I,O> {

	private ErrorType errorType;
	
	private I request;
	private O response;
	//
	public I getRequest() {
		return request;
	}
	public void setRequest(I request) {
		this.request = request;
	}
	public O getResponse() {
		return response;
	}
	public void setResponse(O response) {
		this.response = response;
	}
	//
	public void setErrorType(ErrorType errorType) {
		this.errorType = errorType;
	}
	public Integer getErrorCode(){
		return errorType==null?null:errorType.getErrorCode();
	}
	
	public String getErrorMessage(){
		return errorType==null?null:errorType.getErrorMessage();
	}
	
}
