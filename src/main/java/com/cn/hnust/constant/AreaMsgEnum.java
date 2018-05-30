package com.cn.hnust.constant;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum AreaMsgEnum {
	Zero("0", "人工调整", "0","人工调整"),
	One("1", "找1级", "4","找同级省"),
	Two("2", "找2级", "5","找同级市"),
	Three("3", "找3级", "6","找同级区"),
//	Four("4", "找4级", "", ""),
//	Five("5", "找上级省", "", ""),
//	Six("6", "找上级市", "", ""),
//	Seven("7", "找上级区", "", ""),
	;
	
	private static final Map<String, AreaMsgEnum> lookup = new HashMap<>();

	static {
		for (AreaMsgEnum e : EnumSet.allOf(AreaMsgEnum.class)) {
			lookup.put(e.getCode(), e);
		}
	}

	private String code;
	private String label;
	private String type;
	private String value;
	
	private AreaMsgEnum(String code, String label, String type, String value) {
		this.code = code;
		this.label = label;
		this.type = type;
		this.value = value;
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
	
	public String getValue(){
		return value;
	}
	
	public String getType(){
		return type;
	}
	
	/**
	 * 根据代码获得状态名称
	 * 
	 * @param code
	 * @return 状态名称
	 */
	public static AreaMsgEnum get(String code) {
		return lookup.get(code);
	}
	
	@Override
	public String toString() {
		StringBuilder s=new StringBuilder();
		s.append(code).append(",").append(label).append(",").append(type)
				.append(",").append(value);
		return s.toString();
	}

}
