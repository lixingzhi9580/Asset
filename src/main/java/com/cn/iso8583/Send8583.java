//package com.cn.iso8583;
//
//import java.util.Map;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.cn.encryption.utilsIn.ByteUtil;
//
//public class Send8583 {
//	private static final Logger LOG = LoggerFactory
//			.getLogger(Send8583.class);
//	public static void main(String[] args) {
//		// 组装请求参数
//				Map<String, Object> map8583 = putConsumeMap8583(jsonObject, tranData);
//				LOG.debug("8583Map请求报文：[{}]", map8583);
//
//				// 转成8583报文
//				byte[] reqbytes = Parse8583.encode(map8583);
//				LOG.debug("8583请求报文：[{}]", ByteUtil.bytesToHex(reqbytes));
//				// 发送8583请求到交易系统
//				byte[] bytes = commonService.send8583(reqbytes);
//	}
//	
//}
