package com.cn.encryption.utilsOut;

import java.security.MessageDigest;

/**
 * MD5加密工具类
 * @author lixz 
 * @date 2016-03-07
 */
public class MD5 {

	public static String encode(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return Util.byte2hex(md.digest(str.getBytes()));
		} catch (Exception e) {
			new RuntimeException(e.getMessage(), e);
		}
		return null;
	}
}
