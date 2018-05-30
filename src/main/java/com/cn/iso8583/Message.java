package com.cn.iso8583;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Message implements Serializable {
	private static final long serialVersionUID = -974555158310560717L;
	protected String requestId;
	protected ConcurrentHashMap<String, String> head;
	protected ConcurrentHashMap<String, Object> body;
    protected int timeout;
	public Message() {
		this.head = new ConcurrentHashMap<String, String>();
		this.head.put("STM", String.valueOf(System.currentTimeMillis()));
		this.requestId=createRequestId();
	}

	public String getObjectHeadItem(String fldName) {
		return this.head.get(fldName);
	}

	public void setHeadItem(String fldName, String fldValue) {
		this.head.put(fldName, fldValue);
	}

	public void delHeadItem(String fldName) {
		this.head.remove(fldName);
	}

	public Object getProperty(String propertyName) {

		return this.body.get(propertyName);
	}

	public void setProperty(String propertyName, Object value) {
		this.body.put(propertyName, value);
	}

	public ConcurrentHashMap<String, Object> getBody() {
		return body;
	}

	public void setBody(ConcurrentHashMap<String, Object> body) {
		this.body = body;
	}
	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	@Override
	public String toString() {
		StringBuffer msgbuf = new StringBuffer();
		msgbuf.append("REQ_ID:");
		msgbuf.append(this.requestId);
		msgbuf.append("\t");
		if (head != null) {
            Iterator<Map.Entry<String,String>> iter = head.entrySet().iterator();
            while(iter.hasNext()){
            	Map.Entry<String, String> entry = iter.next();
            	msgbuf.append(entry.getKey()+":");
            	msgbuf.append(entry.getValue()+"\t");
            }
		}
		if (body != null) {
            Iterator<Map.Entry<String,Object>> iter = body.entrySet().iterator();
            while(iter.hasNext()){
            	Map.Entry<String, Object> entry = iter.next();
            	msgbuf.append(entry.getKey()+":");
            	msgbuf.append(entry.getValue()+"\t");
            }
		}			
		return msgbuf.toString();
	}
	public Boolean isMessageTimeout(){
		if(timeout ==0){
			return false;
		}
		long nowTime = System.currentTimeMillis();
		long startTime = Long.parseLong(head.get("STM"));
		if(nowTime -startTime >timeout*1000L){
			return true;
		}
		return false;
	}
	
	private String createRequestId() {
		return "CHPSPOS1POSP000000000001";
	}
}
