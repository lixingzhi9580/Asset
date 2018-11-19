package com.cn.iso8583;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;


public enum PayType {
	/** 挥卡 */
	HK("0"),
	/** HCE */
	HCE("1"),
	/** Apple Pay */
	APPLEPAY("2"),
	/** 三星 Pay */
	SXPAY("3"),
	/** 华为 Pay */
	HWPAY("4"),
	/** 小米 Pay */
	XMPAY("5"),
	/** 中兴 Pay */
	ZXPAY("6"),
	/** 联想 Pay */
	LXPAY("7"),
	/** 咕咚 Pay */
	GDPAY("8");
	
	
	private static final Map<String,PayType> lookup = new HashMap<String,PayType>();
	
	static{
		for(PayType e:EnumSet.allOf(PayType.class)){
			lookup.put(e.getCode(), e);
		}
	} 
	
	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	private PayType(String code){
		this.code = code;
	}
	
	/**
	 * 根据代码获得状态名称
	 * 
	 * @param code
	 * @return 状态名称
	 */
	public static PayType get(String code) {
		return lookup.get(code);
	}

	public String getValue() {
		return this.code;
	}

	public String getDisplayName() {
		return name();
	}
	
}
