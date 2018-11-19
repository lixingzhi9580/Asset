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
		String iv = "12345678";
		System.out.println("偏移量"+iv);
		String key = "u8sZtwDNMeNxUkxNsYSUbBxxWyj56K63TycQvppKVFI33ISu";
		System.out.println("48位des密钥"+key);
		String value="wodadadad";
		System.out.println("json值"+value);
//		String bbb=Base64.getEncoder().encodeToString(value.getBytes());
//		System.out.println("base64处理后的json值"+bbb);
//		byte[] ccc=Base64.getDecoder().decode(bbb);
		String data = Des3.encode(value, key,iv);
		System.out.println("Des3加密后的值"+data);
		String data1 = Des3.decode("+gmme6VD/bnvtoT1fTPbsje+TYe87I1sPM9haYw1FBPaXaI0puedq1Zl6IqYP/XNEyU/sHHNWOKRTkLVoIoFJXQETMEAJL0pc/nMsB9KMBEgeI/8+AA+vTHkRgI73k2IhzjOFqbEyoT1fKrypyA+IpLV/H2td+dKGY1BA63xRaUuqHF67bsGNAyqclp7Jbh7wSxEhkWliJ0e+ENXUz4bLesqzZ/ObsKWgUyawPplk9DyNS3ubKrP8ThixQrQepbU9T67DU5WMv4=", key,iv);
		System.out.println("Des3解密后的值"+data1);
	}
}

