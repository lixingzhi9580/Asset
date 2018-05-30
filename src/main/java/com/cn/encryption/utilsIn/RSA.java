package com.cn.encryption.utilsIn;

import java.io.IOException;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;



/**
 * @description RSA工具
 * @author lds
 *
 */
public class RSA {
	private static final int KEY_SIZE = 1024;
	private static Logger logger = LoggerFactory.getLogger(RSA.class);
	static {
		if(Security.getProvider(BouncyCastleProvider.PROVIDER_NAME)==null){
			Security.addProvider(new BouncyCastleProvider());
		}
		
	}

	/**
	 * @description 生成密钥对
	 * @return
	 */
	public static KeyPair generateKeyPair() {
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(
					"RSA", "BC");
			keyPairGenerator.initialize(KEY_SIZE, new SecureRandom());
			return keyPairGenerator.genKeyPair();
		} catch (Exception e) {
			logger.error("不存在的算法",e);
		}
		return null;

	}

	/**
	 * @descripiton 生成公钥
	 * @param modulus
	 * @param publicExponent
	 * @return
	 */
	public static RSAPublicKey generateRSAPublicKey(byte[] modulus,
			byte[] publicExponent) {
		try {
			KeyFactory factory = KeyFactory.getInstance("RSA","BC");
			RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(new BigInteger(
					modulus), new BigInteger(publicExponent));
			return (RSAPublicKey) factory.generatePublic(pubKeySpec);
		} catch (Exception e) {
			logger.error("不存在的算法",e);
		}
		return null;
	}

	/**
	 * @description 生成私钥
	 * @param modulus
	 * @param privateExponent
	 * @return
	 */
	public static RSAPrivateKey generateRSAPrivateKey(byte[] modulus,
			byte[] privateExponent) {
		try {
			KeyFactory factory = KeyFactory.getInstance("RSA","BC");
			RSAPrivateKeySpec priKeySpec = new RSAPrivateKeySpec(
					new BigInteger(modulus), new BigInteger(privateExponent));
			return (RSAPrivateKey) factory.generatePrivate(priKeySpec);
		} catch (Exception e) {
			logger.error("不存在的算法",e);
		}
		return null;
	}

	/**
	 * @description 公钥加密
	 * @param key
	 * @param data
	 * @return
	 */
	public static byte[] encrypt(Key key, byte[] data) {
		try {
			Cipher cipher = Cipher.getInstance("RSA","BC");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			return cipher.doFinal(data);
		} catch (Exception e) {
			logger.error("无效公钥",e);
		}
		return null;
	}
	
	/**
	 * @description 私钥解密
	 * @param key
	 * @param raw
	 * @return
	 */
	public static byte[] decrypt(Key key, byte[] raw){
		try {
			Cipher cipher = Cipher.getInstance("RSA", "BC");
			cipher.init(Cipher.DECRYPT_MODE, key);
			return cipher.doFinal(raw);
		} catch (Exception e) {
			logger.error("无效私钥",e);
		}
		return null;
	}
	
	/**
	 * @description 从字符串中获取openssl rsa 生成的公钥
	 * @param publicKeyStr
	 * @return
	 * @throws RSAException
	 */
	public static RSAPublicKey createOpensslRSAPublicKey(String publicKeyStr) {
		try {
			BASE64Decoder base64Decoder = new BASE64Decoder();
			byte[] buffer = base64Decoder.decodeBuffer(publicKeyStr);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
			return (RSAPublicKey) keyFactory.generatePublic(keySpec);
		} catch (NoSuchAlgorithmException e) {
			logger.error("无此算法", e);
		} catch (InvalidKeySpecException e) {
			logger.error("公钥非法", e);
		} catch (IOException e) {
			logger.error("公钥数据内容读取错误", e);
		} catch (NullPointerException e) {
			logger.error("公钥数据为空", e);
		}
		return null;
	}

	/**
	 * @description 从字符串中获取openssl rsa 生成的私钥
	 * @param privateKeyStr
	 * @return
	 * @throws Exception
	 */
	public static RSAPrivateKey createOpensslRSAPrivateKey(String privateKeyStr) {
		try {
			BASE64Decoder base64Decoder = new BASE64Decoder();
			byte[] buffer = base64Decoder.decodeBuffer(privateKeyStr);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		} catch (NoSuchAlgorithmException e) {
			logger.error("无此算法");
		} catch (InvalidKeySpecException e) {
			logger.error("私钥非法");
		} catch (IOException e) {
			logger.error("私钥数据内容读取错误");
		} catch (NullPointerException e) {
			logger.error("私钥数据为空");
		}
		return null;
	}
	
	/**
	 * @description 使用RSA私钥解密加密数据
	 * @param key
	 * @param raw
	 * @return
	 */
	public static byte[] decryptByRsa(Key key, byte[] raw){
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, key);
			return cipher.doFinal(raw);
		} catch (Exception e) {
			logger.error("无效私钥",e);
		}
		return null;
	}
}
