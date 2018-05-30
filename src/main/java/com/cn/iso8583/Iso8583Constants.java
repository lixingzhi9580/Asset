package com.cn.iso8583;

public class Iso8583Constants {
	public static final String HEADER = "POSHeader";
	public static final int HEADER_INDEX = 300;

	public static final String ITEM = "Item";
	
	public static final String FIELD_ID = "field_id";
	
	public static final String PROP_NAME = "key_name";
	
	public static final String LENGTH_TYPE = "length_type";
	public static final String LENGTH_TYPE_CONST = "CONST";
	public static final String LENGTH_TYPE_VAR2 = "VAR2";
	public static final String LENGTH_TYPE_VAR3 = "VAR3";
	public static final String LENGTH = "length";

	public static final String DATA_TYPE = "data_type";
	public static final String DATA_TYPE_ASCBCD = "ASCBCD";
	public static final String DATA_TYPE_BIT = "BIT";
	public static final String DATA_TYPE_CHARASCII = "CharASCII";
	public static final String DATA_TYPE_NUMASCII = "NumASCII";
	public static final String DATA_TYPE_NUMBCD = "NumBCD";
	
	public static final String VAR_TYPE = "var_type";
	public static final String VAR_TYPE_BIN = "bin";
	public static final String VAR_TYPE_CHAR = "char";
	
	public static final String ALIGN_MODE = "align_mode";
	public static final String ALIGN_MODE_LEFT = "left";
	public static final String ALIGN_MODE_RIGHT = "right";
	
	public static final String CONVERT = "convert";
	
	public static final String HANDLER_METHOD = "pro_dll";
	
	public static final String HANDLER_FUNCTION = "pro_func";
}
