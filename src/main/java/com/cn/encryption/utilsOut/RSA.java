package com.cn.encryption.utilsOut;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

/**
 * RSA加密工具类
 * @author lixz
 * 
 */
public class RSA {
	private static final String CHARSET = "utf-8";
	
	/**
	 * 随机生成密钥对
	 */
	public static KeyPair genKeyPair() {
		KeyPairGenerator keyPairGen = null;
		try {
			keyPairGen = KeyPairGenerator.getInstance("RSA");
			keyPairGen.initialize(1024, new SecureRandom());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		KeyPair keyPair = keyPairGen.generateKeyPair();
		return keyPair;
	}

	/**
	 * 加载公钥
	 * @param publicKeyStr 公钥数据字符串
	 * @return 公钥
	 * @throws Exception 加载公钥时产生的异常
	 */
	public static RSAPublicKey loadPublicKey(String publicKey) throws Exception {
		try {
			byte[] buffer = Base64.getDecoder().decode(publicKey);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
			RSAPublicKey rsaPublicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
			return rsaPublicKey;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法",e);
		} catch (InvalidKeySpecException e) {
			throw new Exception("公钥非法",e);
		} catch (NullPointerException e) {
			throw new Exception("公钥数据为空",e);
		}
	}

	/**
	 * 加载私钥
	 * @param privateKeyStr 私钥数据字符串
	 * @return 私钥
	 * @throws Exception 加载私钥时产生的异常
	 */
	public static RSAPrivateKey loadPrivateKey(byte[] privateKey) throws Exception {
		try {
			byte[] buffer = Base64.getDecoder().decode(privateKey);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
			return rsaPrivateKey;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法",e);
		} catch (InvalidKeySpecException e) {
			throw new Exception("私钥非法",e);
		} catch (NullPointerException e) {
			throw new Exception("私钥数据为空",e);
		}
	}

	/**
	 * 公钥加密
	 * @param publicKey 公钥
	 * @param plainTextData 明文数据
	 * @return 密文数据
	 * @throws Exception 加密产生的异常信息
	 */
	public static byte[] encrypt(Key publicKey, String plainTextData)
			throws Exception {
		if (publicKey == null) {
			throw new Exception("加密公钥为空, 请设置");
		}
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] output = cipher.doFinal(plainTextData.getBytes(CHARSET));
			return output;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此加密算法",e);
		} catch (InvalidKeyException e) {
			throw new Exception("加密公钥非法,请检查",e);
		} catch (IllegalBlockSizeException e) {
			throw new Exception("明文长度非法",e);
		} catch (BadPaddingException e) {
			throw new Exception("明文数据已损坏",e);
		}
	}

	/**
	 * 私钥解密
	 * @param privateKey 私钥
	 * @param cipherData 密文数据
	 * @return 明文数据
	 * @throws Exception 解密产生的异常信息
	 */
	public static byte[] decrypt(Key privateKey, byte[] cipherData)
			throws Exception {
		if (privateKey == null) {
			throw new Exception("解密私钥为空, 请设置");
		}
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] output = cipher.doFinal(cipherData);
			return output;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此解密算法",e);
		} catch (InvalidKeyException e) {
			throw new Exception("解密私钥非法,请检查",e);
		} catch (IllegalBlockSizeException e) {
			throw new Exception("密文长度非法",e);
		} catch (BadPaddingException e) {
			throw new Exception("密文数据已损坏",e);
		}
	}

	public static void main(String[] args) throws Exception {
		KeyPair rsaEncrypt = RSA.genKeyPair();
		byte[] puk=Base64.getEncoder().encode(rsaEncrypt.getPublic().getEncoded());
		System.out.println("新生成公钥为："+new String(puk,CHARSET));
		byte[] prk=Base64.getEncoder().encode(rsaEncrypt.getPrivate().getEncoded());
		System.out.println("新生成私钥为："+new String(prk,CHARSET));
		
		RSAPrivateKey rSAPrivateKey=RSA.loadPrivateKey(prk);
		RSAPublicKey rSAPublicKey=RSA.loadPublicKey(new String(puk));
		// 测试字符串
		String encryptStr = "Test String chaijunkun";
		System.out.println("原字符串为："+encryptStr);
		try {
			// 加密
			byte[] cipher = RSA.encrypt(rSAPublicKey,encryptStr);
			// 解密
			byte[] plainText = RSA.decrypt(rSAPrivateKey,cipher);
			System.out.println("解密字符串为："+new String(plainText));
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		String PARTNER_ID="12548652";
		System.out.println("privateKey."+PARTNER_ID+"="+new String(prk,CHARSET));
		System.out.println("PARTNER_ID:"+new String(Base64.getEncoder().encode(PARTNER_ID.getBytes(CHARSET))));;
		System.out.println("DEFAULT_PUBLIC_KEY:"+new String(puk,CHARSET));
	}
}

