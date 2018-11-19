package com.cn.encryption.demoIm;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cn.encryption.utilsIn.ByteUtil;
import com.cn.encryption.utilsIn.DES;
import com.cn.encryption.utilsIn.PtsConstants;
import com.cn.encryption.utilsIn.Util;
import com.google.gson.Gson;

public class HposServiceTest {
	private final static Logger LOG = LoggerFactory.getLogger(HposServiceTest.class);

	
	public static void main(String[] args) throws Exception {
		String msg="MjE=|n+VvGaNCO1r6vJWbtuITxYs1tIiztFQ0Vh2vhRO52SkK9A7KWBA6YcCC5MzKVkOWI61Bs23GVnHYDxg8E3l1dXdNkoFoKo/kg/VA2Der+yCurUwD5PSVuM9MTGquPgLoLPH5CccQ0ARk+bQgQ6V6pbV1tOeHr0Tuwq5xoQfLgrA=|1axCpyfWW6LbTaHkjPrxP2PcEWvvNd16x7ABecm6FgEHplXI73c2hGtTHQkj0yVaTiK2vE74YBgGEUg8n0iyziJ9/7wJ8FciJp/3zdORPLBiYgMS7/bHAggVA20GrqnqViCKv27yCN5Imt8EcMGDTx9NCj4Hb8Vf/1C/jye7DKhKd5IzP75Dsrtza4exFZVetuBLPZN+22gsGRdGscDGU4g/+42lHIXHe8NWZuuaeO19MxOeR6UEX4aG+wvUxDFrMaXFap/v2yHotst5jJulH8tRPsGdLMQD+qilTFO5xiC3cwdXibr4rqsdksg0F/6iNKTMWgZBtEn+o0U69Te3YJFg3xU6nJzJsYd1pgVX1xgyoYNNpCHDnuNtpS5USddvEWIHjfzpNSyuAGlUTQR6uocLvuTNb768LFt7shKaPvUwoT85ulZPheP5XNMCe5vYa+uSdXfmW0b7A0e983c1DqT4kdsLznBp7fk/fhkHTlYrM6YehnurKfjDEKyYGzQ9ES3BGI+kVS0cBLKPqswRWD4cmt2RwnZElT6w7dHBhqXUh9/fX/oNs4T8zpFYvtls9CDJXo1Nui94tqzMLW6TtoHEl75xiFsZzpoYYya6ZwAA5qWwWRiPji4zOmIOA1Pqv3GtbtLnf9FDXtSfC8HGMC/XFR9IlRt0IOzGk8e29aXcphci0goousLLl1GIRZRx9No1tJqHSAaoYZVu2YTPN75VUujHtVgxAl19UyhFEjx+Ug91nbHdX94XIbiFo5HTVNolFWEixFt1Xsp9ygwd6A/MgdHu2ataZajQExLeSCmgtTPr6Mnenssms9T46QXA3JxSqRe45oLxVOlTY71sd9ltqVq6bvGEXATxZbkBfYSnHKBAgdJMn2yHnfbZlp0cUGnhZppt7nvSAVLRkt+l4097wRFtNs0Uu7PjSQUAYgzHr1aEy6X364BRrHxUHsZPJMTH4qTN1GkuXtBBWtIerUStcZ7g+y+f+Vrqn/T9Q2nQtrdNfNJnD4Z2Ki17jEqtyYpEFt5UCqrT2mkjMTWuOzsbauwjs2dKCCAPkJwUYl9yWbSjsuORl0cVlJWxtfBf49XGxkk6nG2DS4xWN9YNHdrME+PAA4rs166CkNbgzOIYiQhbLE9esoCUIbFjU+3TRHuz5ylrRpB0jS+FgBuM1aOPRKlNKV6GmRys8+58of37E5bPC2FMUo16pgkZCuywApjVvQ7/WK/gRahILKSej5j24TPRh8J+WI3GRBTGEZjoQVB3jMjOZ++QMoSqkQKJDRGcprIS1f6IX+tEb+t4bnluGoirsOZO";
		byte[] key=Util.getDesKey(msg);
		String json = Util.getJson(msg, key);
//		String json ="{'track2Data':'','ic55Data':'9F26087E206A7694CBE3E49F2701809F101307021703A00000010A010000000000B09896579F3704153600009F360200FB950500000000009A031807309C01009F02060000000450005F2A02015682027C009F1A0201569F03060000000000009F3303E0E1C09F34030000009F3501229F1E0831323334353637388408A0000003330101029F090200309F410400000001','mccList':'5712','mobileNumber':'','cardType':'3','cardSeriNo':'00','merchantOrderId':'20180730104813156','terminalId':'A020124200000000','accountNumber':'6227180000099576','userId':'700000002724657','expiryDate':'2103','clientType':'2','track3Data':'','pinType':'1','factID':'','encTracks':'3458510EF4986CF28CAE9B5EE3E19F761820EA33750F9D5E','transAmt':'450.00','merchantOrderTime':'20180730104819','randomNumber':'61CF1D1CEB37C94E','action':'purchase_receivablesToCard','functionKey':'RECEIVABLES','tradeType':'02','terminalInfo':'504930353804023033051830303030323430333632353138313032353239383633323606063039393537360708413736344639323608083233303732202020'}";
		LOG.info("json：[{}]", json);
		JSONObject jsonObject = JSON.parseObject(json);
		Map<String, Object> responseMap = dealConsumeMsg(jsonObject);
//		LOG.info("responseMap：[{}]", responseMap);
	}

	/**
	 * 发送,解析8583报文
	 *
	 * @param jsonObject
	 * @param tranData
	 * @param signaturePath
	 * @return
	 */
	private static Map<String, Object> dealConsumeMsg(JSONObject jsonObject) {
		HposTrandata tranData=putHpos(jsonObject);
		// 组装请求参数
		Map<String, Object> map8583 = putConsumeMap8583(jsonObject,tranData);
		LOG.info("8583Map请求报文：[{}]", map8583);

		// 转成8583报文
		byte[] reqbytes = Parse8583.encode(map8583);
		LOG.info("8583请求报文：[{}]", ByteUtil.bytesToHex(reqbytes));
		// 发送8583请求到交易系统
		byte[] bytes = send8583(reqbytes);

		Map<String, Object> resMap8583 = Parse8583.decode(bytes);
		LOG.info("8583Map响应报文：[{}]", resMap8583);
		return resMap8583;
	}
	
	private static HposTrandata putHpos(JSONObject jsonObject){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HHmmss");
		String[] curtime = sdf.format(new Date()).split(" ");
		String uuid = java.util.UUID.randomUUID().toString().replace("-", "");
		HposTrandata tranData = new HposTrandata();
		tranData.setTranDt(curtime[0]);
		tranData.setTranTm(curtime[1]);
		tranData.setUuid(uuid);
		tranData.setMno(jsonObject.getString("userId"));
		tranData.setOrdNo(jsonObject.getString("merchantOrderId"));
		tranData.setTranAmt(new BigDecimal(jsonObject.getString("transAmt").replace(",", "")));
		tranData.setTrmSn(jsonObject.getString("terminalId"));
		tranData.setTranTyp(jsonObject.getString("isGotMoneyToady"));
		tranData.setMobile(jsonObject.getString("mobileNumber"));
		tranData.setPosSeqNo("123456");
		tranData.setBatNo(tranData.getTranDt().substring(2));
		return tranData;
	}
	
	
	/**
	 * 组装8583Map
	 *
	 * @param jsonObject
	 * @param tranData
	 * @return
	 */
	private static Map<String, Object> putConsumeMap8583(JSONObject jsonObject,
												  HposTrandata tranData) {
		Map<String, Object> map8583 = new HashMap<String, Object>();
		Map<String, String> encryptJsonMap = encryptJson(jsonObject);
		String pin = encryptJsonMap.get("pin");
		String track2 = encryptJsonMap.get("track2");
		String track3 = encryptJsonMap.get("track3");
		String tradeType = jsonObject.getString("tradeType");
		//将商户 mcc放入118域
		String mccList = jsonObject.getString("mccList");
		Map<String,String> mccMap = new HashMap<>();
		if (StringUtils.isNotBlank(jsonObject.getString("merchantOrderId"))) {
			mccMap.put("ordNo",jsonObject.getString("merchantOrderId"));
		}
		if (StringUtils.isNotBlank(mccList)) {
			mccMap.put("mccList", mccList);
		}
		if (!mccMap.isEmpty()) {
			map8583.put("qrtypeAdContent", new Gson().toJson(mccMap));
			LOG.info("交易前置118域存放数据：{}",mccMap);
		}
		map8583.put("msgId", "02".equals(tradeType) ? "0910" : "0900");
		map8583.put("msgCode", "900000");
		map8583.put("tranAmt", StringUtils.leftPad(
				String.valueOf(tranData.getTranAmt()
						.multiply(new BigDecimal("100")).longValue()), 12, "0"));
		map8583.put("serialNumber", tranData.getPosSeqNo());
		map8583.put("expdate", jsonObject.getString("expiryDate"));

		String cType = jsonObject.getString("cardType");
		// 组装22域
		StringBuffer sb = new StringBuffer("0");
		if ("1".equals(cType))
			sb.append("5");
		else if ("3".equals(cType))
			sb.append("7");
		else
			sb.append("2");

		if (null != jsonObject.getString("Pin")
				&& jsonObject.getString("Pin").length() > 0) {
			if (null != pin)
				map8583.put("pin", pin.getBytes());
			sb.append("1");
		} else
			sb.append("2");

		map8583.put("inMod", sb.toString());
		map8583.put("conMod", "1".equals(tranData.getTranTyp()) ? "92" : "93");
		map8583.put("track2", track2);
		if (null != track3)
			map8583.put("track3", track3);
		map8583.put("deviceSn", tranData.getTrmSn());
		map8583.put("mno", tranData.getMno());
		map8583.put("ccy", "156");
		map8583.put("securInfo", "2600000000000000");
		map8583.put("tranRsv", "2" + tranData.getRemoteAddr());
		map8583.put("operatId", "01");
		map8583.put("infoSyncParam", "2000000000");
		map8583.put("f60", String.format("%s%s%s%s%s", "22",
				tranData.getBatNo(), "000", "3".equals(cType) ? 6 : 5, "01"));
		map8583.put("appNo", "000000");
		map8583.put("f122", tranData.getUuid());
		map8583.put("pacFlag", "1");
		map8583.put("appVer", StringUtils.rightPad("21", 10));
		map8583.put("pinCapCode", "06");
		map8583.put("primaryAccount", jsonObject.getString("accountNumber"));
		map8583.put("f102","03");
		String icdata = jsonObject.getString("ic55Data");
		if (null != icdata && icdata.length() > 0) {
			map8583.put("icdata", icdata);
			String cardSn = jsonObject.getString("cardSeriNo");
			if (null == cardSn || cardSn.length() < 1)
				cardSn = "001";
			else if (2 == cardSn.length())
				cardSn = "0" + cardSn;
			map8583.put("cardSn", cardSn);
		}
		map8583.put("expApp", jsonObject.getString("terminalInfo"));
		map8583.put("memLev",jsonObject.getString("noPassWord"));
		return map8583;
	}
	
	/**
	 * 发送8583报文
	 * @param reqbytes
	 * @return
	 */
	public static byte[] send8583(byte[] reqbytes){
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		String head = "6000010000600001313091";
		byte[] reqheadbytes = ByteUtil.hexToBytes(head);
		try {
			out.write(ByteUtil.shortToBytes((short) (reqheadbytes.length + reqbytes.length)));
			out.write(reqheadbytes);
			out.write(reqbytes);
			out.flush();
			out.close();
		} catch (IOException e) {
			LOG.info("发送8583报文出错",e);
		}

		// 调线下交易系统
		byte[] resbytes = SocketClient.syncShortConnection(
				"127.0.0.1",6666,60000,
				out.toByteArray());
		byte[] bytes = new byte[resbytes.length - 12];
		System.arraycopy(resbytes, 12, bytes, 0, bytes.length);
		LOG.info("返回解压报文：" + ByteUtil.bytesToHex(bytes));
		return bytes;
	}
	
	/**
	 * 解密磁道和密码
	 * 
	 * @param jsonObject
	 * @return
	 */
	private static Map<String, String> encryptJson(JSONObject jsonObject) {
		byte[] trackDesKey = null;
		if ("002".equals(jsonObject.getString("terminalId").substring(1, 4))) {
			trackDesKey = PtsConstants.TRACK_DESKEY_NEWLAND;
		}if ("003".equals(jsonObject.getString("terminalId").substring(1, 4))) {
			trackDesKey = PtsConstants.TRACK_DESKEY_LD;
		} else {
			trackDesKey = PtsConstants.TRACK_DESKEY;
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
			LOG.info("解密后的磁道信息：[{}]", track);

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
			LOG.info("解密后的2磁道信息：[{}]", track2);
			if (null != jsonObject.getString("track3Data")
					&& jsonObject.getString("track3Data").length() > 0) {
				byte[] track3Bytes = DES
						.decrypt(DES.initKey(subkey),
								ByteUtil.hexToBytes(jsonObject
										.getString("track3Data")), "desede",
								"desede/ECB/NoPadding");
				track3 = ByteUtil.bytesToHex(track3Bytes).replace("F", "");
				LOG.info("解密后的3磁道信息：[{}]", track3);
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
				LOG.info("解密后的pinString信息：[{}]", ByteUtil.bytesToHex(pinString));
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
