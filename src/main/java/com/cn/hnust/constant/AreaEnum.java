package com.cn.hnust.constant;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum AreaEnum {
	Zero("0", "人工"),
	PROV("2", "省"),
	CITY("3", "市"),
	DIST("4", "区"),
	
	
	PROV1("20", "国同省"),
	CITY1("30", "国同市"),
	DIST1("40", "国同区"),
	
	PROV2("21", "省同省"),
	CITY2("31", "省同市"),
	DIST2("41", "省同区"),
	
	PROV3("22", "市同省"),
	CITY3("32", "市同市"),
	DIST3("42", "市同区"),
	
	PROV4("23", "区同省"),
	CITY4("33", "区同市"),
	DIST4("43", "区同区");

	
	private static final Map<String, AreaEnum> lookup = new HashMap<>();

	static {
		for (AreaEnum e : EnumSet.allOf(AreaEnum.class)) {
			lookup.put(e.getCode(), e);
		}
	}

	private String code;
	private String label;
	
	private AreaEnum(String code, String label) {
		this.code = code;
		this.label = label;
	}

	/**
	 * 获得指定状态的代码
	 * 
	 * @return 状态代码
	 */
	public String getCode() {
		return code;
	}

	public String getLabel(){
		return label;
	}
	
	/**
	 * 根据代码获得状态名称
	 * 
	 * @param code
	 * @return 状态名称
	 */
	public static AreaEnum get(String code) {
		return lookup.get(code);
	}
	
	@Override
	public String toString() {
		StringBuilder s=new StringBuilder();
		s.append(code).append(",").append(label);
		return s.toString();
	}

}
