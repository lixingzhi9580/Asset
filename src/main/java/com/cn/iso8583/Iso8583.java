package com.cn.iso8583;

import java.util.ArrayList;

public class Iso8583 {
	private ArrayList<Item8583> head;
	private ArrayList<Item8583> body;
	
	public ArrayList<Item8583> getHead() {
		return head;
	}
	public void setHead(ArrayList<Item8583> head) {
		this.head = head;
	}
	public ArrayList<Item8583> getBody() {
		return body;
	}
	public void setBody(ArrayList<Item8583> body) {
		this.body = body;
	}
	@Override
	public String toString() {
		StringBuffer msgbuf = new StringBuffer();
		msgbuf.append("Iso8583 [head=");
		msgbuf.append(head);
		msgbuf.append("; body=");
		msgbuf.append(body);
		msgbuf.append("]");
		return msgbuf.toString();
	}

}
