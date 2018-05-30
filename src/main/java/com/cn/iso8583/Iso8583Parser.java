package com.cn.iso8583;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ISO8583报文解析器
 * <p>
 * 基于ISO8583规范实现的协议报文解析器，由于不同用户提供不同类型的报文格式，
 * 故实现统一的报文解析就需要对不同用户提供的报文格式进行统一的配置解析，
 * 这里采用XML配置方式定义不同用户的报文格式，
 * XML配置文件的查找路径是当前应用的ClassPath所指路径，
 * 以查找文件名前缀来定位XML文件，文件名命名规则是“前缀_8583.XML”，
 * 且前缀大小写不敏感
 * </p>
 * @version 1.0
 */
public class Iso8583Parser {
	
	private static Logger logger = LoggerFactory.getLogger(Iso8583Parser.class);
	
	/**
	 * 解析报文
	 * @see Iso8583Parser#parse(String, byte[], boolean)
	 * @param suffix XML文件名前缀
	 * @param message 原始二进制报文
	 * @return 解析后的报文数据，key对应配置文件中的key_name字段，
	 * value对应原始二进制报文中所对应域的值
	 * @throws CodecException 如果编码解码错误
	 * @throws IOException 如果读数据错误
	 * @throws Iso8583Exception 如果ISO8583协议数据不完整
	 */
	public static Map<String, Object> parse(String suffix, byte[] message)
			throws Iso8583Exception, IOException, CodecException{
		return parse(suffix,message,true);
	}

	/**
	 * 解析报文
	 * @param suffix XML文件名前缀
	 * @param message 原始二进制报文
	 * @param isParseHead 指原始二进制报文中是否包含header信息，
	 * 目前该值如果传false有风险，例如：如果原始二进制报文中存在header信息，
	 * 而isParseHead=false，这时header信息会被错误当作是body信息解析，
	 * 之后这段校验会更新
	 * @return 解析后的报文数据，key对应配置文件中的key_name字段，
	 * value对应原始二进制报文中所对应域的值
	 * @throws CodecException 如果编码解码错误
	 * @throws IOException 如果读数据错误
	 * @throws Iso8583Exception 如果ISO8583协议数据不完整
	 */
	public static Map<String, Object> parse(String suffix, byte[] message, 
			boolean isParseHead) throws Iso8583Exception, IOException, CodecException{
		Iso8583 configuration = Iso8583Configuration.getIso8583(suffix);
		InputStream is = new ByteArrayInputStream(message);
		Map<String, Object> body = new HashMap<String, Object>();
		if(isParseHead){
			parseHeader(configuration, is, body);
		}else{
			 // 检查configuration是否有header信息，有则跳过header总长度的字节数，
			 // 否则header信息会被错误当作是body信息解析，或者根本就不用这个参数
		}
		parseBody(configuration, is, body);
		return body;
	}
	
	/**
	 * 解析header信息
	 * @param configuration 根据前缀定位的XML配置内容
	 * @param is 原始二进制报文
	 * @param headMap 解析完header信息后的报文
	 * @throws IOException 如果读数据错误
	 * @throws CodecException 如果编码解码错误
	 * @throws Iso8583Exception 如果ISO8583协议数据不完整
	 */
	private static void parseHeader(Iso8583 configuration,
			InputStream is,Map<String, Object> headMap) throws IOException, CodecException,
			Iso8583Exception {
		List<Item8583> items = configuration.getHead();
		if (items != null && items.size() > 0) {
			for (Item8583 item : items) {
				String value = parse(is, item);
				headMap.put(item.getPropertyName(),value );
				if("35".equals(item.getFieldId()) || "36".equals(item.getFieldId()) || "14".equals(item.getFieldId()) || "52".equals(item.getFieldId())) {
					if(isVerbose()){
						logger.info("添加域 field_id=[{}],{}=[{}][{}]",item.getFieldId(),
							item.getPropertyName(),item.getLength(),value);
						logger.info("添加域 field_id=[{}],{}=[{}][************]",item.getFieldId(),
							item.getPropertyName(),item.getLength());
					}
				}else
					if(isVerbose()){
						logger.info("添加域 field_id=[{}],{}=[{}][{}]",item.getFieldId(),
							item.getPropertyName(),item.getLength(),value);
					}
			}
		}
	}

	/**
	 * 解析body信息
	 * @param configuration 根据前缀定位的XML配置内容
	 * @param is 原始二进制报文
	 * @param fields 解析完body信息后的报文
	 * @throws IOException 如果读数据错误
	 * @throws CodecException 如果编码解码错误
	 * @throws Iso8583Exception 如果ISO8583协议数据不完整
	 */
	private static void parseBody(Iso8583 configuration,
			InputStream is,Map<String, Object> fields) throws IOException, CodecException,
			Iso8583Exception {
		List<Item8583> items = configuration.getBody();
		Item8583 item = items.get(0);
		
		if(isVerbose()){
			logger.info(item.toString());
		}
		
		String value = parse(is, item);
		fields.put(item.getPropertyName(), value);
		if("2".equals(item.getFieldId())||"35".equals(item.getFieldId()) || "36".equals(item.getFieldId()) || "14".equals(item.getFieldId()) || "52".equals(item.getFieldId())) {
			if(isVerbose()){
				logger.info("添加域 field_id=[{}],{}=[{}][{}]",item.getFieldId(),
					item.getPropertyName(),item.getLength(),value);
				logger.info("添加域 field_id=[{}],{}=[{}][************]",item.getFieldId(),
					item.getPropertyName(),item.getLength());
			}
		}else
			if(isVerbose()){
				logger.info("添加域 field_id=[{}],{}=[{}][{}]",item.getFieldId(),
					item.getPropertyName(),item.getLength(),value);
			}
	    item = items.get(1);
		if (item == null) {
			throw new Iso8583Exception("Bitmap field is missed.");
		}
		String bitMap = getBitMap(is);
		fields.put(item.getPropertyName(), bitMap);
		if(isVerbose()){
			logger.info("Get 8583 BitMap: [" + bitMap+"]");
			logger.info("Get 8583 Hex BitMap: [" + AsciiCodec.binary2hex(bitMap) +"]");
		}
		for (int i = 1; i < bitMap.length(); i++) {
			if (bitMap.charAt(i) == '1') {
				item = items.get(i + 1);
				value =parse(is, item);
				fields.put(item.getPropertyName(), value);
				if("2".equals(item.getFieldId())||"35".equals(item.getFieldId()) || "36".equals(item.getFieldId()) || "14".equals(item.getFieldId()) || "52".equals(item.getFieldId())) {
					if(isVerbose()){
						logger.info("添加域 field_id=[{}],{}=[{}][{}]",item.getFieldId(),
							item.getPropertyName(),item.getLength(),value);
						logger.info("添加域 field_id=[{}],{}=[{}][************]",item.getFieldId(),
							item.getPropertyName(),item.getLength());
					}
				}else
					if(isVerbose()){
						logger.info("添加域 field_id=[{}],{}=[{}][{}]",item.getFieldId(),
							item.getPropertyName(),item.getLength(),value);
					}
			}
		}
	}

	/**
	 * 解析ISO8583对应的BitMap
	 * @param is 原始二进制报文
	 * @return ISO8583的BitMap对应的字符串
	 * @throws IOException 如果读数据错误
	 * @throws CodecException 如果编码解码错误
	 * @throws Iso8583Exception 如果ISO8583协议数据不完整
	 */
	private static String getBitMap(InputStream is) throws IOException,
			CodecException, Iso8583Exception {
		byte[] retBytes = new byte[8];
		int read = is.read(retBytes);
		if (read < 8) {
			throw new Iso8583Exception("Bitmap length is not enough, actual: "
					+ read + ", expected: 8");
		}

		// convert to hexMap
		String hexStr = AsciiCodec.bcd2AscStr(retBytes);
		// convert to bitMap
		String bitMap = AsciiCodec.hex2Binary(hexStr);
				
		if (bitMap.charAt(0) == '1') {
			String extHexStr;
			retBytes = new byte[8];
			read = is.read(retBytes);
			if (read < 8) {
				throw new Iso8583Exception(
						"1st bitmap length is not enough, actual: " + read
								+ ", expected: 8");
			}
			extHexStr = AsciiCodec.bcd2AscStr(retBytes);
			bitMap = bitMap + AsciiCodec.hex2Binary(extHexStr);
			hexStr += extHexStr;
		}
		return bitMap;
	}

	/**
	 * 解析对应的某一个域
	 * @param is 原始二进制报文
	 * @param field XML配置内容的某一Item
	 * @return XML配置内容的某一Item所对应的值
	 * @throws IOException 如果读数据错误
	 * @throws CodecException 如果编码解码错误
	 * @throws Iso8583Exception 如果ISO8583协议数据不完整
	 */
	private static String parse(InputStream is, Item8583 field)
			throws IOException, CodecException, Iso8583Exception {
		int length = 0;
		String data_value = "";

		String data_type = field.getDataType();
		String length_type = field.getLengthType();
		if (length_type.equals(Iso8583Constants.LENGTH_TYPE_CONST)) {
			length = Integer.parseInt(field.getLength());
		} else if (length_type.equals(Iso8583Constants.LENGTH_TYPE_VAR2)) {
			length = getHeaderLen(is, 2, field);
		} else if (length_type.equals(Iso8583Constants.LENGTH_TYPE_VAR3)) {
			length = getHeaderLen(is, 3, field);
			
		} else {
			throw new Iso8583Exception("Length type for item [id: "
					+ field.getFieldId() + "] is invalid, expected '"
					+ Iso8583Constants.LENGTH_TYPE_CONST + "' or '"
					+ Iso8583Constants.LENGTH_TYPE_VAR2 + "' or '"
					+ Iso8583Constants.LENGTH_TYPE_VAR3 + "'");
		}

		if (data_type.equals(Iso8583Constants.DATA_TYPE_CHARASCII)
				|| data_type.equals(Iso8583Constants.DATA_TYPE_NUMASCII)) {
			byte[] retBytes = new byte[length];
			int read = is.read(retBytes);
			if (read < length) {
				throw new Iso8583Exception("Malformed message, field[id: "
						+ field.getFieldId()
						+ "]'s length is not enough, actual: " + read
						+ ", expected: " + length);
			}

			if (field.getConvertor() != null
					&& StringUtils.equals(field.getConvertor(), "hex")) {
				data_value = AsciiCodec.bcd2AscStr(retBytes);
			} else {
				data_value = new String(retBytes, "gbk");
			}
		} else {
			data_value = readBcd(is, length, 2, field);
		}

		return data_value;
	}

	/**
	 * 解析Item为变长类型时所对应的实际长度
	 * @param is 原始二进制报文
	 * @param length 变长类型所对应的长度定义
	 * @param field XML配置内容的某一Item
	 * @return Item所对应的实际长度
	 * @throws IOException 如果读数据错误
	 * @throws CodecException 如果编码解码错误
	 * @throws Iso8583Exception 如果ISO8583协议数据不完整
	 */
	private static int getHeaderLen(InputStream is, final int length,
			Item8583 field) throws IOException, CodecException,
			Iso8583Exception {
		if (StringUtils.equals(field.getVarType(),
				Iso8583Constants.VAR_TYPE_BIN)) {
			return Integer.parseInt(readBcd(is, length, 2, null));
		} else {
			byte[] b = new byte[length];
			int read = is.read(b);
			if (read < length) {
				String id = field.getFieldId();
				throw new Iso8583Exception("Malformed message, field [id: "
						+ id + "]'s length is not enough, actual: " + read
						+ ", expected: " + length);
			}
			return Integer.parseInt(new String(b));
		}
	}

	/**
	 * 以BCD码的方式解析所对应的内容
	 * @param is 原始二进制报文
	 * @param length 要读取的字节长度
	 * @param radix 一个字节可以表示2个十进制数（4位二进制数表示一位10进制数），需再确认
	 * @param field XML配置内容的某一Item
	 * @return Item所对应的BCD码表示的内容
	 * @throws IOException 如果读数据错误
	 * @throws CodecException 如果编码解码错误
	 * @throws Iso8583Exception 如果ISO8583协议数据不完整
	 */
	private static String readBcd(InputStream is, final int length,
			final int radix, Item8583 field) throws IOException,
			CodecException, Iso8583Exception {
		
		int bcdLen = length / radix;
		int rsvLen = length % radix;
		byte[] b = null;
		if (rsvLen != 0) {
			bcdLen = bcdLen + 1;

			b = new byte[bcdLen];
			int read = is.read(b);
			if (read < bcdLen) {
				String id = field.getFieldId();
				throw new Iso8583Exception("Malformed message, field [id: "
						+ id + "]'s length is not enough, actual: " + read
						+ ", expected: " + bcdLen);
			}

			if (field != null
					&& StringUtils.equals(field.getAlignMode(),
							Iso8583Constants.ALIGN_MODE_LEFT)) {
					return StringUtils.substring(AsciiCodec.bcd2AscStr(b), 0,
						length);
			} else {
				return StringUtils.substring(AsciiCodec.bcd2AscStr(b), radix
						- rsvLen);
			}
		} else {
			b = new byte[bcdLen];
			int read = is.read(b);
			if (read < bcdLen) {
				String id = field.getFieldId();
				throw new Iso8583Exception("Malformed message, field [id: "
						+ id + "]'s length is not enough, actual: " + read
						+ ", expected: " + length);
			}
			return AsciiCodec.bcd2AscStr(b);
		}
	}

	public static boolean isVerbose() {
		return Iso8583Context.VERBOSE.get();
	}
}
