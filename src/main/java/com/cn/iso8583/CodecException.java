package com.cn.iso8583;

public class CodecException extends Exception {

	private static final long serialVersionUID = 8151763420299943644L;

	public CodecException(String message) {
		super(message);
	}

	public CodecException(String message, Throwable th) {
		super(message, th);
	}
}
