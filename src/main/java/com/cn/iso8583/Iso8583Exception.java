package com.cn.iso8583;

public class Iso8583Exception extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public Iso8583Exception(String message) {
		super(message);
	}

	public Iso8583Exception(String message, Throwable e) {
		super(message, e);
	}
}
