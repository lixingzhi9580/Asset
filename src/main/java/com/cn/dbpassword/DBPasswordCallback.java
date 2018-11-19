/** <a href="http://www.cpupk.com/decompiler">Eclipse Class Decompiler</a> plugin, Copyright (c) 2017 Chen Chao. **/
package com.cn.dbpassword;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.alibaba.druid.util.DruidPasswordCallback;



public class DBPasswordCallback extends DruidPasswordCallback {
	private static final Log LOG = LogFactory.getLog(DBPasswordCallback.class);
	private static final long serialVersionUID = 8352871450818066381L;


	public static void main(String[] args) {
		try {
//			String pwd = "BE7499D74B35701B";//dev
			String pwd = "EC4A6D12BC6858754AF5D1AF7EFA083C";//test
//			String pwd = "5364FB6D0FE4362065975B736249D3C60985958D719A06E3";//RC

//			String pwd = "736D57553B3927F53E62F0FB8DBFEF9B0985958D719A06E3";
			
			
			String e = decryptHex(getKey(DBPasswordCallback.class.getResourceAsStream("/key.properties")), pwd);
			System.out.println(e);
		} catch (Exception arg3) {
			LOG.error("decrypt password error:", arg3);
		}
	}
	
	private static String getKey(InputStream in) {
		String str = null;
		try {
			ObjectInputStream e = new ObjectInputStream(in);
			str = new String(Base64.getDecoder().decode((byte[]) ((byte[]) e.readObject())));
		} catch (FileNotFoundException arg2) {
			LOG.error("decrypt password getKey FileNotFoundException:", arg2);
		} catch (IOException arg3) {
			LOG.error("decrypt password getKey IOException:", arg3);
		} catch (ClassNotFoundException arg4) {
			LOG.error("decrypt password getKey ClassNotFoundException:", arg4);
		}

		return str;
	}
	
	private static final String ALGORITHM = "DES";

	public static String decryptHex(String key, String textHex) throws Exception {
		return new String(decrypt(Hex.decodeHex(textHex.toCharArray()), key));
	}

	public static String encryptHex(String key, String textHex) throws Exception {
		return new String(encrypt(textHex, key));
	}

	public static byte[] encrypt(String text, String key) throws Exception {
		SecretKeySpec securekey = new SecretKeySpec(key.getBytes(), ALGORITHM);
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(1, securekey);
		return cipher.doFinal(text.getBytes());
	}

	public static byte[] decrypt(byte[] data, String key) throws Exception {
		return decrypt(data, key.getBytes());
	}

	public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		SecretKeySpec securekey = new SecretKeySpec(key, ALGORITHM);
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(2, securekey);
		return cipher.doFinal(data);
	}
}