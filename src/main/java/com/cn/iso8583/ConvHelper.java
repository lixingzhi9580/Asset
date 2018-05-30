package com.cn.iso8583;

/**
 * Copyright (C),1998 -- 2006 Hisuntech Co..Ld
 * 
 * HiConvHelper.java 2006-10-20
 * 
 */

public class ConvHelper {
	public static byte[] hexToByteArray(String hexa) {
		if (hexa == null) {
			return null;
		}
		if ((hexa.length() % 2) != 0) {
			return null;
		}
		int tamArray = hexa.length() / 2;
		byte[] retorno = new byte[tamArray];
		for (int i = 0; i < tamArray; i++) {
			retorno[i] = hexToByte(hexa.substring(i * 2, i * 2 + 2));
		}
		return retorno;
	}
	
	public static byte hexToByte(String hexa) {
		if (hexa == null) {
			return 0;
		}
		if (hexa.length() != 2) {
			return 0;
		}
		byte[] b = hexa.getBytes();
		byte valor = (byte) (hexDigitValue((char) b[0]) * 16 + hexDigitValue((char) b[1]));
		return valor;
	}
	
	private static int hexDigitValue(char c) {
		int retorno = 0;
		if (c >= '0' && c <= '9') {
			retorno = (int) (((byte) c) - 48);
		} else if (c >= 'A' && c <= 'F') {
			retorno = (int) (((byte) c) - 55);
		} else if (c >= 'a' && c <= 'f') {
			retorno = (int) (((byte) c) - 87);
		} else {
			return 0;
		}
		return retorno;
	}
}
