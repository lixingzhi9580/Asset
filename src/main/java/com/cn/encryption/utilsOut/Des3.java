package com.cn.encryption.utilsOut;

import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * 3DES加密工具类
 * @author lixz 
 * @date 2016-03-07
 */
public class Des3 {
	private static final String CHARSET = "utf-8";

	/**
	 * 3DES加密
	 * @param plainText 普通文本
	 * @param secretKey
	 * @return
	 * @throws Exception 
	 */
	public static String encode(String plainText,String secretKey,String iv){
		try{
			Key deskey = null;
			DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
			deskey = keyfactory.generateSecret(spec);
			Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
			IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
			cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
			byte[] encryptData = cipher.doFinal(plainText.getBytes(CHARSET));
			byte[] bb = Base64.getEncoder().encode(encryptData);
			return new String(bb,CHARSET);
		}catch(Exception e){
			e.printStackTrace();
		}
		return plainText;
	}
	

	/**
	 * 3DES解密
	 * 
	 * @param encryptText 解密文本
	 * @return
	 * @throws Exception
	 */
	public static String decode(String encryptText,String secretKey,String iv){
		try{
			Key deskey = null;
			DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
			deskey = keyfactory.generateSecret(spec);
			Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
			IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
	
			byte[] decryptData = cipher.doFinal(Base64.getDecoder().decode(encryptText.getBytes(CHARSET)));
			return new String(decryptData, CHARSET);
		}catch(Exception e){
			e.printStackTrace();
		}
		return encryptText;
	}
	
	
	/**
	 * 生成48位3DES密钥
	 * @return
	 */
	public static String generateKey(){
		KeyGenerator kg;
		try {
			kg = KeyGenerator.getInstance("desede");
			kg.init(168);
			SecretKey secretKey = kg.generateKey();
			byte[] key = secretKey.getEncoded();
			return Util.byte2hex(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		String iv = "我唉12";
		String key=generateKey();
		
		String aaa="wodadadad";
		String bbb=Base64.getEncoder().encodeToString(aaa.getBytes());
		System.out.println(bbb);
		byte[] ccc=Base64.getDecoder().decode(bbb);
		System.out.println(new String(ccc));
		System.out.println(key);
		String data = Des3.encode(new String(ccc), key,iv);
		System.out.println(data);
		String data1 = Des3.decode(data, key,iv);
		System.out.println(data1);	
	}
}

