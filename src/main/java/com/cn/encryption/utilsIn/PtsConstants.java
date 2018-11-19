package com.cn.encryption.utilsIn;

import java.util.regex.Pattern;

public class PtsConstants {

	//----------------------主扫被扫标识----------------------------
	public static final String ACTIV_SCAN_CODE = "0";    //主扫
	public static final String PASSIVE_SCAN_CODE = "1";  //被扫
	
	//----------------------订单来源----------------------------
	public static final String OUTER_ORDER_SOURSE = "1";  //外部
	public static final String INNER_ORDER_SOURSE = "0";  //内部
	
	//----------------------mpos访问交易前置方式-------------------
	public static final String DIRECT_FLAG = "1";         //mpos等直接访问交易前置
	public static final String INDIRECT_FLAG = "0";       //mpos通过VV访问交易前置
	
	//--------------------------交易来源-------------------------
	public static final String TRADE_SOURCE_POS = "1";        //pos 老商服app交易来源为POS
	public static final String TRADE_SOURCE_VV = "2";         //VV
	public static final String TRADE_SOURCE_PREPOS = "3";     //手刷   mpos
	public static final String TRADE_SOURCE_INTERNET = "4";   //互联网
	public static final String TRADE_SOURCE_MECAPP = "5";     //新商服APP
	public static final String TRADE_SOURCE_TAIKA = "6";      //台卡
	public static final String TRADE_SOURCE_MBS = "7";        // MBS
	
	//支付来源  01大pos 02VV 03mpos 04VV钱包 05商服App 06商户收银台 07互联网
	public static final String PAY_SOURCE_POS = "01";              //大pos
	public static final String PAY_SOURCE_VV = "02";               //VV
	public static final String PAY_SOURCE_MPOS = "03";             //mpos
	public static final String PAY_SOURCE_VV_WALLET = "04";        //VV钱包
	public static final String PAY_SOURCE_SHFAPP = "05";           //商服APP(随行付收银台)
	public static final String PAY_SOURCE_MERCHAT_CASHIER = "06";  //商户收银台
	public static final String PAY_SOURCE_INTERNET = "07";         //互联网
	public static final String PAY_SOURCE_TAIKA = "08";            //台卡
	public static final String PAY_SOURCE_MBS = "09";              // MBS
	
	//送给交易系统的交易类型-0域
	public static final String VV_REVERSE_MSGID = "9200";          //商服app被扫,商户收银台被扫0域
	public static final String MSGID_9200 = "9200";                //0域  9200
	
	//送给交易系统的交易类型-3域
	public static final String SHFAPP_REVERSE_MSGCODE = "340000";  //商服app被扫3域
	public static final String VV_REVERSE_MSGCODE = "350000";      //商户收银台被扫3域
	public static final String INTERNET_MSGCODE = "370000";        //互联网二维码主扫3域
	public static final String NO_SN_MSGCODE = "380000";           //二维码主扫交易无终端3域
	public static final String NO_SN__REVERSE_MSGCODE = "390000";  //二维码被扫扫交易无终端3域
	public static final String COOPERATION_CANCEL = "400002";      //真功夫二维码撤销无终端3域
	public static final String COOPERATION_REFUND = "410002";      //真功夫二维码退货无终端3域
	
	// 交易状态
	public static final String TRAN_STS_SUCCESS = "S";             //成功
	public static final String TRAN_STS_FAIL = "F";                //失败
	public static final String TRAN_STS_UNPAID = "R";              //未支付
	
	// 交易标识
	public static final String TRAN_FLAG_N = "N";                 //正常
	public static final String TRAN_FLAG_L = "L";                 //临时
	public static final String TRAN_FLAG_D = "D";                 //被撤销
	public static final String TRAN_FLAG_R = "R";                 //二维码已全部退款
	
	/** 货币类型  CNY*/
	public static final String CURRENCY = "CNY";
	
	public static final String RSA_PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMyPHq2LOrKz0I1/\r/d1u1O9OTOJTPoQDNgwsxfocoX1I8HcOlNJHNsEQg+LsIrkG8hQOnLK55UiS+A5p\r93/4hHT+GyHVf7xciN+1OYaOptRfQMiVIRJfTRtZWj0cRN8Zw2U8MjvupI72pv9Q\rZV6t4mv5NGC3xiyaXfwq0u8yOnltAgMBAAECgYEAtfk35FCwhhrak5ZiA2O+P6jb\rUpeVTKECqrAF6usfajHB4VfyYmIBvpxvhyZj+U/JeLhLA9/Frds4mrIAADLXuRdl\rcQYyz9RK2y/q8WfN8XcAWWKydQTs1wGCcku3RS2rg0Sfrdinb0jq37KUWKqex3Mo\rg5KDR+ueZnBJxsoRJmECQQD4yxtFak8VkbfOHpWrmcHgRO6TZwZaNWExsXCQRnze\rWOTEVoPEhG7IwRPGIxfE0Zkf2cG+vfGKF65YCbzcHcI1AkEA0nv/7NpqgGszAjOZ\rdE7x5t8UfGdBe6wieZJToqqWE7NjbgyLmUl1YFz+Hu3qebrVkl1hNXLZ6Jv45fgi\r2ZjBWQJAIEh6qW86A9p8t0pQsYuqFKfdLVNZB6uViRU1PgNngJKYXMG9J2rn1TT4\rk+VJ2Eg6Tl+7PDz5cqnP/ayFzSovYQJAQzofr8LDKWkTzaw1YxSj5p1xqZpBLAL6\rr+GwnM/nRzuQkmGnZLo1pyWMdMyAi4jFFg6FMdEREF5gzPLIDr/fYQJBAIxcvKka\r43RpfMLcwDYmc0gglODFTuVFkjxZK9Xi/3JBrtwWxPHLkJd4leoPOq527MB9pUDm\rT87Eaa2vVKPl8cQ=";
	public static final String CHARSET = "UTF-8";
	/** 编码： GBK*/
	public static final String CHARSET_GBK = "GBK";
	
	public static final byte[] TRACK_DESKEY = DES.initKey("5A4F71033BC985491252FB6A2036CA74");
	public static final byte[] TRACK_DESKEY_NEWLAND = DES.initKey("7504825A300B4000145C110D1860576E");
	public static final byte[] TRACK_DESKEY_LD = DES.initKey("1506825A310B4060E45C1A0D1860576A");

	public static final Pattern uuidPattern = Pattern.compile("uuid=([a-zA-Z0-9]+)");
	
}
