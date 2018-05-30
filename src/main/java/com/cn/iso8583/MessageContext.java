package com.cn.iso8583;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageContext {
	private Message message;
	private Logger log;
	private Connection conn;
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message currentMsg) {
		this.message = currentMsg;
	}
	public Logger getLogger(){
		if(log == null){
		   log = LoggerFactory.getLogger(message.requestId);
		}
		return log;
	}
	public Connection getConnection(){
		return conn;
	}
}
