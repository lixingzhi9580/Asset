package com.cn.encryption.demoIm;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cn.encryption.utilsIn.ByteUtil;
import com.cn.encryption.utilsIn.DES;
import com.cn.encryption.utilsIn.PtsConstants;
import com.cn.encryption.utilsIn.Util;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class HposServiceTest {
	private final static Logger LOG = LoggerFactory.getLogger(HposServiceTest.class);

	
	public static void main(String[] args) throws Exception {
		String msg="MjE=|UhdPZmxN4VKDe0o/kHSePwARYpzDPkwmRZ2BaYg2TjK90jU4lPlDi9pflNc+IgJUqe+Xu7wO2bTT66hjvtNqHuIvQFGt7BbTjjq68H7JLlI6IiAQDOnZSgoQpkQDOraXo3VDYWVDTPJ78R2j7wU7F4mleKtlrrdX0oDSfQN5BVU=|yXnTDujDSnq0Hko0eQHbud6vp2Y/6VmR6vClkjaAbwy6/zqpCDa0QwbPNybODxckVRYigAB6zOIt6IGcbYsRVvH0smZikeKzJPPqvEFTkyybe+sjT5tD0brewWFpWgkIgDw78e4N7fM41vXcxVtBz//fre0lU1pcNSoE3CS/Uv3j5A1GUbAh+VpnHtJEz76EQyH1D8mQ+4q7xS6ztDI7cBrrEH6m8pbHq02eqbUJ0GjAZZkavnorkdDZu8F0wHiLys0s3J6d3WkhG8sGmKv1FcG2M8hHeR2BXrZsu0/VhKHtECB6F0gdEOQwq2GmtZntGrQw9n1DjDtsXRjBV3lkrLi+gMT+xpnXbN8amH2FSd87mqG98rAgMEv84/us72/J8x/D6i8fWa5eRCRsrlT/BwgqkiAtGl0hVFKXRn4Pk1YVvdWA0soMT05piOsJDiLCcsnmrwD92SYAwBn9PDJq2BLJmCEPqweSH6Bzx6T23D5B0pIQkE6WYJ/1j6K2h0hCM/4IlCfWWr/5D9EqLW5mw8DanWKbIfFocTWu8t7YyHK7y9HZWtsDJh2piYm1uthT8dIF8xhdoiHIyke/rQ/ptip658icfKzRd0EGTvfVmPa4YLyNaMfrliiOvygowTGuhHi2swCKIbxUrLB0WTFMKhCrSgjdd9UxJTCatVGUaus/zEQtfu0TnM8SERPITchXqmIJ2jotFdQca2QWnEL9Tfnm8L+6QKNq38RhWwxTaO20VSEJjhqd7pHeBhmXm8VswuCqkOwVxfkywLa7kF1k06gxu1MV23aUWYpc3WD+rUn8vkw1e8bfCu/29yhVFIclD7bwpb7RCF7A5N8o+vSwEotlqFjvvR1fELQo2X8bLSxPvBqoymvBbq6a9/9heHM7YpNc3mUg8BUBFG9nKdyFs/QfitwKaBZ9fvnqDlZ1eBYrPXFOGi3wxe9UCn77Vw02WjmomFrhGOPpji4eNr2x1hJyXCyhItPoogrGAm+8Mr8xjsn6rm35DZf81cJ+h3ElM1VgM8t2r0R9weuPI4yfPv3X20oDuUuoHFd53yrj2GloslZg3qWlUa5LZOlUKEffJt24aR6dkEZAjqyGRyCYQ8Hinx7NGBmED8pnfnWrwoYd4imzj8s083v7owspesCsmzDRl5PyguQ=";
		byte[] key=Util.getDesKey(msg);
		String json = Util.getJson(msg, key);
		LOG.info("json：[{}]", json);
		JSONObject jsonObject = JSON.parseObject(json);
		Map<String, String> map=encryptJson(jsonObject);
		LOG.info("map：[{}]", map);
	}
	
	
	/**
	 * 解密磁道和密码
	 * 
	 * @param jsonObject
	 * @return
	 */
	private static Map<String, String> encryptJson(JSONObject jsonObject) {
		byte[] trackDesKey = null;
		if (!"002".equals(jsonObject.getString("terminalId").substring(1, 4))) {
			trackDesKey = PtsConstants.TRACK_DESKEY;
		} else {
			trackDesKey = PtsConstants.TRACK_DESKEY_NEWLAND;
		}
		// 解密磁道信息
		byte[] randomLeftBytes = ByteUtil.hexToBytes(jsonObject
				.getString("randomNumber"));
		byte[] randomRightBytes = new byte[8];
		for (int i = 0; i < 8; i++) {
			randomRightBytes[i] = (byte) (randomLeftBytes[i] ^ 0xff);
		}
		LOG.info("随机数异或：[{}]", ByteUtil.bytesToHex(randomRightBytes));
		String keyLeft = ByteUtil.bytesToHex(DES.encrypt(trackDesKey,
				randomLeftBytes, "desede", "desede/ECB/NoPadding"));
		LOG.info("左密钥：[{}]", keyLeft);
		String keyRight = ByteUtil.bytesToHex(DES.encrypt(trackDesKey,
				randomRightBytes, "desede", "desede/ECB/NoPadding"));
		LOG.info("右密钥：[{}]", keyRight);
		String subkey = keyLeft + keyRight;
		LOG.info("子密钥：[{}]", subkey);

		// 取二三磁道
		String track2 = null;
		String track3 = null;
		if (null != jsonObject.getString("terminalId")
				&& !"002".equals(jsonObject.getString("terminalId").substring(
						1, 4))) {
			byte[] trackBytes = DES.decrypt(DES.initKey(subkey),
					ByteUtil.hexToBytes(jsonObject.getString("encTracks")),
					"desede", "desede/ECB/NoPadding");
			String track = ByteUtil.bytesToHex(trackBytes);
			LOG.debug("解密后的磁道信息：[{}]", track);

			if ("1".equals(jsonObject.getString("cardType")))
				track2 = track.replace("F", "");
			else if (trackBytes.length <= 24)
				track2 = track.replace("F", "");
			else {
				track2 = track.substring(0, 48).replace("F", "");
				track3 = track.substring(48).replace("F", "");
			}
		} else {
			byte[] track2Bytes = DES.decrypt(DES.initKey(subkey),
					ByteUtil.hexToBytes(jsonObject.getString("track2Data")),
					"desede", "desede/ECB/NoPadding");
			track2 = ByteUtil.bytesToHex(track2Bytes).replace("F", "");
			LOG.debug("解密后的2磁道信息：[{}]", track2);
			if (null != jsonObject.getString("track3Data")
					&& jsonObject.getString("track3Data").length() > 0) {
				byte[] track3Bytes = DES
						.decrypt(DES.initKey(subkey),
								ByteUtil.hexToBytes(jsonObject
										.getString("track3Data")), "desede",
								"desede/ECB/NoPadding");
				track3 = ByteUtil.bytesToHex(track3Bytes).replace("F", "");
				LOG.debug("解密后的3磁道信息：[{}]", track3);
			}
		}
		String pin = null;
		if (null != jsonObject.getString("Pin")
				&& jsonObject.getString("Pin").length() > 0) {
			if (null == jsonObject.getString("pinType")
					|| !"1".equals(jsonObject.getString("pinType"))) {
				if (6 != jsonObject.getString("Pin").length())
					throw new RuntimeException("密码长度不正确");
				pin = StringUtils.rightPad(jsonObject.getString("Pin"), 8);
			} else {
				byte[] pinString = DES.decrypt(trackDesKey,
						ByteUtil.hexToBytes(jsonObject.getString("Pin")),
						"desede", "desede/ECB/NoPadding");
				String crdNo = jsonObject.getString("accountNumber");
				int panLen = crdNo.length();// 账号长度
				int begLen = panLen - 13;// 账号开始截取位置
				String panNo = "0000" + crdNo.substring(begLen, panLen - 1);// 最右12位账号(不含检查位)
				byte[] panNoBytes = ByteUtil.hexToBytes(panNo);
				byte[] pinBytes = pinString;
				for (int i = 0; i < 8; i++) {
					panNoBytes[i] = (byte) (pinBytes[i] ^ panNoBytes[i]);
				}
				pin = StringUtils.rightPad(ByteUtil.bytesToHex(panNoBytes)
						.substring(2, 8), 8);
			}
		}
		Map<String, String> retMap = new HashMap<String, String>();
		retMap.put("track2", track2);
		retMap.put("track3", track3);
		retMap.put("pin", pin);
		return retMap;
	}
}
