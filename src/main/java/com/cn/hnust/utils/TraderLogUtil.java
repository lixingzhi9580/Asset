package com.cn.hnust.utils;

import org.slf4j.MDC;

/**
 * @description: 打印日志增加UUID
 * @author li_xz
 */
public class TraderLogUtil {
	public static void init(TraderLogUtil.LogUtilKey key, String value) {
		MDC.remove(key.name());
		MDC.put(key.name(), value);
	}

	public static void initUUID(String uuid) {
		init(TraderLogUtil.LogUtilKey.tradenum, uuid);
	}
	
	public static void initUUID() {
		init(TraderLogUtil.LogUtilKey.tradenum, Utils.getUUid());
	}

	public static void initTradeCode(String tradeCode) {
		init(TraderLogUtil.LogUtilKey.tradecode, tradeCode);
	}

	public static void initSystemName(String sytemName) {
		init(TraderLogUtil.LogUtilKey.system, sytemName);
	}

	public static void cleanAll() {
		MDC.clear();
	}
	
	public static enum LogUtilKey {
		system, tradecode, tradenum;
	}
}