package com.cn.encryption.utilsIn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.interfaces.RSAPrivateKey;
import java.util.Base64;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cn.SpringContextUtil;
import com.cn.encryption.utilsOut.MD5;


public class Util {
	private static final Logger LOG = LoggerFactory.getLogger(Util.class);

	
	//转换成十六进制字符串
    public static String byte2hex(byte[] b) {
        String hs="";
        String stmp="";
        for (int n=0;n<b.length;n++) {
            stmp=(java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length()==1) 
            	hs=hs+"0"+stmp;
            else 
            	hs=hs+stmp;
        }
        return hs.toUpperCase();
    }
    
    /**
	 * 获取接收到的数据
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public static String getMsg(HttpServletRequest request) throws IOException{
		BufferedReader br = request.getReader();
		char[] buffer = new char[1024];
		int len = 0;
		StringBuilder stringBuilder = new StringBuilder();
		while (-1 != (len = br.read(buffer))) {
			stringBuilder.append(buffer, 0, len);
		}
		br.close();
		LOG.info("接收到的数据：[{}]", stringBuilder);
		return stringBuilder.toString();
	}
	
	/**
	 * 获取3des密钥
	 * @param msg
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] getDesKey(String msg) throws UnsupportedEncodingException{
		String[] msgArr = msg.split("\\|");
		String trmTyp = new String(Base64.getDecoder().decode(msgArr[0]),PtsConstants.CHARSET);
		LOG.info("终端型号：[{}]", trmTyp);

		byte[] rsaBytes = Base64.getDecoder().decode(msgArr[1]);
		LOG.debug("密钥RSA密文：[{}]", ByteUtil.bytesToHex(rsaBytes));

		// RSA私钥
		RSAPrivateKey priKey = RSA.createOpensslRSAPrivateKey(PtsConstants.RSA_PRIVATE_KEY);
		byte[] desKeyBytes = RSA.decrypt(priKey, rsaBytes);
		LOG.debug("3DES密钥HEX：[{}]", ByteUtil.bytesToHex(desKeyBytes));
		String desKey = new String(desKeyBytes, PtsConstants.CHARSET);
		LOG.debug("3DES密钥：[{}]", desKey);
		return desKey.getBytes();
	}
	
	/**
	 * 解析明文json
	 * @param args
	 * @throws UnsupportedEncodingException
	 */
	public static void main(String[] args) throws UnsupportedEncodingException {
		String aa="MjE=|er8GU2TKcYgnOThqnj1QDSHHORuK9wNSlT4SB3cdiqPPyGqZCQKmuG5woymbwSKKzLB+RiuA0xDLSk+ojlN+NJOHyMNqjd5BIaNy3YHZpAVxczyXoJvC4Aax6W/HnFEFT6Ist9AZdR572wGBbXm/7iZclahpz9E20i0NLGR/N5Y=|kt11pM691qvE4lWzNOSxqFhlwFe1Rue5rO7vEegajR4dgs83x/sLRLBEFbepzKdb9AjwzSqiEoY+poRmpEavQNu0zRhISUlziikFq3K7R/sKyLiIhZ0IwAl+GJeZ4Cu/lK/kgZhG/vPBpUV1nCGn0mQymANsQiD5nf1EI4dUie4Zc/SBmw0iJDzLUFR69U1q5WqVEokci38BpzXlgMRf6tfYgaU6R+JPZPlwsuOQYuKhXKhxirTSiIQJvIEqYMcqtoYKcgjDJ9AoABTJ50+Nz4VRPtIhqTvPN5cw8/tlceZh+fuZO/4oBT98ABw50/MEtNVsfQsxyWCxCrdNRGF1ldnWQcGg8BWec8iH22WRDLQ0ePcvr+7mdYuYQwMRxjZQf2f5Dn7SYrXDdNKJAYJUbZ2if9eB/ejMhHMiTomq6LiMKbNlDBMTUOgPebnr2ZyO6x2HNtZsnronQs2Hds+wZ5txiDtDrQDBmQiRhBNa6c4bwMbuK+PiD+kd5zqpVTpgXvuEVFUAL55wbCgtoF/GVP8n1i2Lppp3KavOg2RMGN4ZWqOsIGNkn80C+dYvpVooZcNcdS+bZlazju5aZNiA2AXQhU0qHvsCv+BjGFXn2zvzT9+mzaciGRrCZ6dk41l3TbLOvXIzBkkqs7/cxX5iTrlBM9PA75q2EpGyDV0pxP/hLWtZ6vX1Rfra42o0Lplswip6WP8PMu7hqKnR7pqBrKMDwd2vIzxk+lfzoG99px7k61HMCQRxPmY5iUBrSxePD+PAFbImN9BdtL4DqKzlkC2gFPQBxG2TvU7ehfRECeBlNJmCOr6jwrTdst8EricA0dik7GYgswfaE5Xo54isOWGqkhB7CqX/lv8jGTf3TJ6766E3HfBfvtkrIYxNw3QlqSFweg985UDnB3pxI//yYJD8ZdFu4y/cyMhvoVzWeQ2vnXVb2CTLFCS9IBHxn25BJAxQG4YCBm2a61QzVoVOrHC5RgaViSXDOFQZV+LGSZXZaL2eFfKrB1Ld2SRXcfyHhQph2rzBTcmvwQVQFiKKmsJgnnYzVTZ2tzgI3yjkNQVRhQPejQICN3IW9UF2o7jO1AmIb7EYrTl0OWD4Ti2rUqiantQ5q7218ke2KmuF4JWehmPcRntxZLTBP6vKhOl9UjtN/7KUE0gUyQQIIizgpXEp6rElC3/+kETIrOycfuGjKx2Z5M9E7jEzOu/s4yCMq7XmNjrq+ovvR2xIxJp+vHdCpoEY7m2YbhAbb3UKk7KAoHbjvH5K+FfqIq9tXEYH420Zen8V9NfCqbQmK/fOTvRJM6v+RnzQ9QlFO0GcAwXXiHw6sKd6xA==";
		String[] msgArr = aa.split("\\|");
		String trmTyp = new String(Base64.getDecoder().decode(msgArr[0]),PtsConstants.CHARSET);
		LOG.info("终端型号：[{}]", trmTyp);

		byte[] rsaBytes = Base64.getDecoder().decode(msgArr[1]);
		LOG.info("密钥RSA密文：[{}]", ByteUtil.bytesToHex(rsaBytes));

		// RSA私钥
		RSAPrivateKey priKey = RSA.createOpensslRSAPrivateKey(PtsConstants.RSA_PRIVATE_KEY);
		byte[] desKeyBytes = RSA.decrypt(priKey, rsaBytes);
		LOG.debug("3DES密钥HEX：[{}]", ByteUtil.bytesToHex(desKeyBytes));
		String desKey = new String(desKeyBytes, PtsConstants.CHARSET);
		LOG.debug("3DES密钥：[{}]", desKey);
		byte[] bytes = Base64.getDecoder().decode(msgArr[2].getBytes(PtsConstants.CHARSET));
		LOG.info("报文密文长度：[{}]", bytes.length);
		LOG.info("报文密文：[{}]", ByteUtil.bytesToHex(bytes));
		byte[] jsonBytes = DES.decrypt(desKey.getBytes(), bytes, "desede", "desede/CBC/PKCS5Padding");
		LOG.debug("报文明文字节：[{}]", ByteUtil.bytesToHex(jsonBytes));
		String requestJson = new String(jsonBytes, PtsConstants.CHARSET);
		LOG.debug("报文明文字符：[{}]", requestJson);
	}
	
	/**
	 * 获取报文json
	 * @param msg
	 * @param desKey
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getJson(String msg,byte[] desKey) throws UnsupportedEncodingException{
		String[] msgArr = msg.split("\\|");
		byte[] bytes = Base64.getDecoder().decode(msgArr[2].getBytes(PtsConstants.CHARSET));
		LOG.debug("报文密文长度：[{}]", bytes.length);
		LOG.debug("报文密文：[{}]", ByteUtil.bytesToHex(bytes));
		byte[] jsonBytes = DES.decrypt(desKey, bytes, "desede", "desede/CBC/PKCS5Padding");
		LOG.debug("报文明文字节：[{}]", ByteUtil.bytesToHex(jsonBytes));
		String requestJson = new String(jsonBytes, PtsConstants.CHARSET);
		LOG.debug("报文明文字符：[{}]", requestJson);
		return requestJson;
	}
	
	/**
	 * 处理返回的字符
	 * @param errCode
	 * @param errMsg
	 * @param responseJson
	 * @param key
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String dealRetString(String errCode,String errMsg,String responseJson,byte[] key) throws UnsupportedEncodingException{
		StringBuilder resultBuilder = new StringBuilder();
		if (null != errCode) {
			resultBuilder
					.append("0|")
					.append(errCode)
					.append("|")
					.append(new String(Base64.getEncoder().encode(
							errMsg.getBytes(PtsConstants.CHARSET)), PtsConstants.CHARSET));
		} else {
			LOG.debug("返回JSON：[{}]", responseJson);
			String md5val = MD5.encode(responseJson);
			LOG.info("返回MD5值：[{}]", md5val);
			resultBuilder
					.append("1|")
					.append(new String(Base64.getEncoder()
							.encode(DES.encrypt(key,
									responseJson.getBytes(PtsConstants.CHARSET), "desede", "desede/CBC/PKCS5Padding")),
									PtsConstants.CHARSET))
					.append("|")
					.append(new String(Base64.getEncoder().encode(
							md5val.getBytes(PtsConstants.CHARSET)), PtsConstants.CHARSET));
		}
		return resultBuilder.toString();
	}

	/**
	 * 二维码被扫失败交易返回失败交易码，失败信息和uuid
	 * @param responseJson
	 * @param key
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String dealReverseRetString(String responseJson, byte[] key) throws UnsupportedEncodingException {
		LOG.debug("二维码被扫返回JSON:[{}]", responseJson);
		String md5val = MD5.encode(responseJson);
		LOG.info("二维码被扫返回MD5值:[{}]", md5val);
		StringBuilder resultBuilder = new StringBuilder();
		resultBuilder
		         .append("0|")
		         .append(new String(Base64.getEncoder()
		        		 .encode(DES.encrypt(key, responseJson.getBytes(PtsConstants.CHARSET), "desede", "desede/CBC/PKCS5Padding")),
		        		 PtsConstants.CHARSET))
		         .append("|")
		         .append(new String(Base64.getEncoder().encode(
		        		 md5val.getBytes(PtsConstants.CHARSET)), PtsConstants.CHARSET));
		return resultBuilder.toString();
	}
	
	/**
	 * 获取各合作机构3DES密钥
	 * @author wcy[wang_cy1@suixingpay.com]
	 * @param msg
	 * @return
	 * @throws UnsupportedEncodingException
	 * 
	 */
	public static byte[] getDecodeDesKey (String msg) throws UnsupportedEncodingException {
		String[] msgArry = msg.split("\\|");
		String orgNo = new String(Base64.getDecoder().decode(msgArry[0]), PtsConstants.CHARSET);
		LOG.debug("合作机构标识:[{}]", orgNo);	
		byte[] rsaBytes = Base64.getDecoder().decode(msgArry[1]);
		LOG.info("RSA密钥密文:[{}]", ByteUtil.bytesToHex(rsaBytes));
		
		// 根据合作机构标识获取私钥
		Properties traderSettings = SpringContextUtil.getBean("traderSettings");
		String privateKey = traderSettings.getProperty("privateKey." + orgNo);
		// 获取RSA私钥
		RSAPrivateKey rsaPrivateKey =  RSA.createOpensslRSAPrivateKey(privateKey);
		// 获取3DES密钥
		byte[] desKeyBytes = RSA.decryptByRsa(rsaPrivateKey, rsaBytes);
		LOG.debug("3DES密钥Hex:[{}]", ByteUtil.bytesToHex(desKeyBytes));
		String desKey = new String(desKeyBytes, PtsConstants.CHARSET);
		LOG.debug("3DES密钥:[{}]", desKey);
		return desKey.getBytes();
	}

	/**
	 * 获取解密后的报文
	 * @param msg
	 * @param desKey
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String getDecodeJson(String msg, byte[] desKey) throws Exception {
		String[] msgArry = msg.split("\\|");
		String orgNo = new String(Base64.getDecoder().decode(msgArry[0]), PtsConstants.CHARSET);
		Properties traderSettings = SpringContextUtil.getBean("traderSettings");
		String iv = traderSettings.getProperty("iv." + orgNo);
		LOG.info("合作机构偏移量[{}]", iv);
		byte[] bytes = Base64.getDecoder().decode(msgArry[2].getBytes(PtsConstants.CHARSET));
		LOG.debug("报文密文长度:[{}],报文密文:[{}]", bytes.length, ByteUtil.bytesToHex(bytes));
		byte[] jsonBytes = DES.decryptMsg(desKey, bytes, "desede", "desede/CBC/PKCS5Padding", iv);
		LOG.debug("报文明文字节:[{}]", ByteUtil.bytesToHex(jsonBytes));
		String requestJson = new String(jsonBytes, PtsConstants.CHARSET);
		LOG.debug("解密后的报文:[{}]", requestJson);
		return requestJson;
	}
	
	/**
	 * 加密返回报文
	 * @param responseJson
	 * @param key
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String dealResponseString(String errorCode, String responseJson, byte[] key, String iv) throws UnsupportedEncodingException {
		LOG.debug("二维码返回JSON:[{}]", responseJson);
		String md5val = MD5.encode(responseJson);
		LOG.info("二维码返回MD5值:[{}]", md5val);
		String sucessFlag = null;
		if (null != errorCode) {
			sucessFlag = "0";
		} else {
			sucessFlag = "1";
		}
		StringBuilder resultBuilder = new StringBuilder();
		resultBuilder.append(sucessFlag)
		         .append("|")
		         .append(new String(Base64.getEncoder()
		        		 .encode(DES.encryptMsg(key, responseJson.getBytes(PtsConstants.CHARSET), "desede", "desede/CBC/PKCS5Padding", iv)),
		        		 PtsConstants.CHARSET))
		         .append("|")
		         .append(new String(Base64.getEncoder().encode(
		        		 md5val.getBytes(PtsConstants.CHARSET)), PtsConstants.CHARSET));
		return resultBuilder.toString();
	}
	
}
