package com.cn.encryption.utilsIn;

import java.security.Key;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @desctipion DES/3DES加解密
 * @author luodesheng
 *
 */
public class DES {
	private static Logger logger = LoggerFactory.getLogger(RSA.class);
	/**
	 * @description DES加密
	 * @param key
	 * @param data
	 * @return
	 */
	public static byte[] encrypt(byte[] key, byte[] data, String type, String padding) {
		try {
			KeySpec spec = "DES".equalsIgnoreCase(type) ? new DESKeySpec(key) : new DESedeKeySpec(key);
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(type);
			Key deskey = keyfactory.generateSecret(spec);
			Cipher cipher = Cipher.getInstance(padding);
			if (padding.indexOf("/CBC/") > -1) {
				IvParameterSpec ips = new IvParameterSpec("87654321".getBytes());
				cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
			} else
				cipher.init(Cipher.ENCRYPT_MODE, deskey);
			return cipher.doFinal(data);
		} catch (Exception e) {
			logger.error("DES加密出错", e);
		}
		return null;
	}

	/**
	 * @description DES解密
	 * @param key
	 * @param data
	 * @return
	 */
	public static byte[] decrypt(byte[] key, byte[] data, String type, String padding) {
		try {
			KeySpec spec = "DES".equalsIgnoreCase(type) ? new DESKeySpec(key) : new DESedeKeySpec(key);
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(type);
			Key deskey = keyfactory.generateSecret(spec);
			Cipher cipher = Cipher.getInstance(padding);
			if (padding.indexOf("/CBC/") > -1) {
				IvParameterSpec ips = new IvParameterSpec("87654321".getBytes());
				cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
			} else
				cipher.init(Cipher.DECRYPT_MODE, deskey);
			return cipher.doFinal(data);

		} catch (Exception e) {
			logger.error("DES解密出错", e);
		}
		return null;
	}

	/**
	 * @description 初始化密钥
	 * @param key
	 * @return
	 */
	public static byte[] initKey(String key) {
		byte keyBytes[] = ByteUtil.hexToBytes(key);
		if (16 == keyBytes.length) {
			byte bytes[] = new byte[24];
			System.arraycopy(keyBytes, 0, bytes, 0, 16);
			System.arraycopy(keyBytes, 0, bytes, 16, 8);
			return bytes;
		} else if (24 == keyBytes.length || 8 == keyBytes.length) {
			return keyBytes;
		} else {
			logger.error("key length is not 16 or 24 or 8");
		}
		return null;
	}

	/**
	 * 用3DES密钥解密报文
	 * @param desKey
	 * @param data
	 * @param type
	 * @param padding
	 * @param iv
	 * @return
	 */
	public static byte[] decryptMsg(byte[] desKey, byte[] data, String type, String padding, String iv) {
		try {
			KeySpec spec = "DES".equalsIgnoreCase(type) ? new DESKeySpec(desKey) : new DESedeKeySpec(desKey);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(type);
			Key deskey = keyFactory.generateSecret(spec);
			Cipher cipher = Cipher.getInstance(padding);
			if (-1 < padding.indexOf("/CBC/")) {
				IvParameterSpec ivParam = new IvParameterSpec(iv.getBytes());
				cipher.init(Cipher.DECRYPT_MODE, deskey, ivParam);
			} else {
				cipher.init(Cipher.DECRYPT_MODE, deskey);
			}
			return cipher.doFinal(data);
		} catch (Exception exception) {
			logger.error("DES解密出错", exception);
		}
		return null;
	}
	
	/**
	 * 加密返回报文
	 * @param key
	 * @param data
	 * @param type
	 * @param padding
	 * @return
	 */
	public static byte[] encryptMsg(byte[] key, byte[] data, String type, String padding, String iv) {
		try {
			KeySpec spec = "DES".equalsIgnoreCase(type) ? new DESKeySpec(key) : new DESedeKeySpec(key);
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(type);
			Key deskey = keyfactory.generateSecret(spec);
			Cipher cipher = Cipher.getInstance(padding);
			if (-1 < padding.indexOf("/CBC/")) {
				IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
				cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
			} else
				cipher.init(Cipher.ENCRYPT_MODE, deskey);
			return cipher.doFinal(data);
		} catch (Exception e) {
			logger.error("DES加密出错", e);
		}
		return null;
	}

}
