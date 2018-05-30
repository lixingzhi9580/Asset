package com.cn.async.demo;

import com.cn.async.dto.AsyncResponse;


public class DemoAsyncResponse implements AsyncResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String key;

	private byte[] textBody;

	
	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public String getKey() {
		return this.key;
	}

	public byte[] getTextBody() {
		return textBody;
	}

	public void setTextBody(byte[] textBody) {
		this.textBody = textBody;
	}


}
