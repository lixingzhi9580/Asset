package com.cn.async.dto;

import java.io.Serializable;

public interface Message extends Serializable{

	public byte[] getTextBody();
	
	public void setTextBody(byte[] textBody);
}
