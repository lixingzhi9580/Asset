package com.cn.iso8583;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * ISO8583报文组装器
 * <p>
 * 基于ISO8583规范实现的协议报文组装器，由于不同用户提供不同类型的报文格式， 故实现统一的报文组装就需要对不同用户提供的报文格式进行统一的配置管理，
 * 这里采用XML配置方式定义不同用户的报文格式， XML配置文件的查找路径是当前应用的ClassPath所指路径，
 * 以查找文件名前缀来定位XML文件，文件名命名规则是“前缀_8583.XML”， 且前缀大小写不敏感
 * </p>
 * 报文组装除清楚报文格式外，还需要知道报文要填充的域以及域所对应的值， 这些值需要调用方以参数方式传入 </P>
 * 
 * @version 1.0
 */
public class Iso8583Composer {

	private static Logger log = LoggerFactory.getLogger(Iso8583Composer.class);
	private final static int BITMAP_LENGTH = 128; // 默认128域都包含

	/**
	 * 组装报文
	 * 
	 * @param packet
	 *            报文要填充的域
	 * @param msg
	 *            报文要填充的域所对应的数据
	 * @param suffix
	 *            XML文件名前缀
	 * @param isComposeHead
	 *            是否组装报文头信息
	 * @return 组装完的ISO8583协议报文
	 */
	public static byte[] compose(Map<String, Object> packet,
			Map<String, Object> msg, String suffix, boolean isComposeHead) {

		if(isVerbose()){
			log.info("合作机构号:[{}]", suffix);
			log.info("isComposeHead:[{}]", isComposeHead);
		}
		Iso8583 iso8583 = Iso8583Configuration.getIso8583(suffix);
		char[] bitMapByte = StringUtils.repeat("0", BITMAP_LENGTH)
				.toCharArray();
		ByteArrayOutputStream out = new ByteArrayOutputStream(128);
		String fields="";
		if (packet.get("optionalFields")!=null) {
			
		    fields = packet.get("mustFields") + "|"
					+ packet.get("optionalFields");
		}else {
		    fields = (String) packet.get("mustFields");
		}
		String[] filedArray = StringUtils.split(fields, "\\|");
		int[] filedArray2 = new int[filedArray.length];
		for (int i = 0; i < filedArray.length; i++) {
			filedArray2[i] = Integer.valueOf(filedArray[i]);
		}
		Arrays.sort(filedArray2);
		// convert to 8583 msg
		convertToPlain(msg, out, bitMapByte, filedArray2, iso8583);
		// add msgTypCod

		// byte[] msgTypByte = HiPack8583Helper.fitPlain(HiItemHelper
		// .execExpression(msg, type_code).getBytes(), msgTypCodNode, log);
		Item8583 msgItem = iso8583.getBody().get(0);
		byte[] msgTypByte = fitPlain(
				((String) packet.get("typeCode")).getBytes(), msgItem, log);

		// add bitmap
		String bitMap = null;
		// if (BITMAP_LENGTH == 128) {
		// bitMapByte[0] = '1';
		// bitMap = AsciiCodec.binary2hex(new String(bitMapByte));
		// } else if (BITMAP_LENGTH == 64) {
		// bitMapByte[0] = '0';
		// bitMap = AsciiCodec.binary2hex(new String(bitMapByte, 0, 64));
		// } else {
		// 位图的长度,默认按实际组包, 若第一位为0位即为64位长
		if (bitMapByte[0] == '0') {
			bitMap = AsciiCodec.binary2hex(new String(bitMapByte, 0, 64));
		} else {
			bitMap = AsciiCodec.binary2hex(new String(bitMapByte));
		}
		// }
		byte[] headByte = null;
		if (isComposeHead) {
			ByteArrayOutputStream hOut = new ByteArrayOutputStream(64);
			packHeader(msg, hOut, iso8583);
			headByte = hOut.toByteArray();
		}
		byte[] outMapByte = AsciiCodec.ascStr2Bcd(bitMap);

		// add 8583 plain
		byte[] outByte = out.toByteArray();
		byte[] tmp = ArrayUtils.addAll(headByte, msgTypByte);
		tmp = ArrayUtils.addAll(tmp, outMapByte);
		return (byte[]) ArrayUtils.addAll(tmp, outByte);

		// pack 8583 header
		// if (this.pack_header) {
		// ByteArrayOutputStream hOut = new ByteArrayOutputStream(64);
		// packHeader(ctx, hOut, bodyLen, log);
		// byte[] hOutByte = hOut.toByteArray();
		//
		// // add header byte
		// plainOut.append(hOutByte, 0, hOutByte.length);
		// }

		// add body plain byte
		/*
		 * plainOut.append(msgTypByte, 0, msgTypByte.length);
		 * plainOut.append(outMapByte, 0, outMapByte.length);
		 * plainOut.append(outByte, 0, outByte.length);
		 */

		// save to HiMessage header
		// msg.setHeadItem(HiConstants.PLAIN_TEXT, plainOut);

		/*
		 * if (log.isInfoEnabled()) {
		 * log.info(sm.getString("HiPack8583.processOk", bitMap, plainOut
		 * .toString())); }
		 * 
		 * if (log.isDebugEnabled()) {
		 * log.debug("Pack8583: OK =======================");
		 * log.debug("Pack8583: bitMap [" + bitMap + "]");
		 * log.debug("Pack8583: pack [" + plainOut.toString() + "]");
		 * log.debug("doProcess(HiMessageContext) - end."); }
		 */

	}

	/**
	 * 填充ISO8583协议数据包
	 * 
	 * @param ctx
	 *            报文要填充的域所对应的数据
	 * @param out
	 *            ISO8583协议数据包
	 * @param bitMapByte
	 *            位图
	 * @param packetFiledId
	 *            报文要填充的域
	 * @param iso8583
	 *            ISO8583协议格式
	 * @throws Iso8583Exception
	 *             如果ISO8583协议格式/数据不完整或不合法
	 */
	private static void convertToPlain(Map<String, Object> ctx,
			ByteArrayOutputStream out, char[] bitMapByte, int[] packetFiledId,
			Iso8583 iso8583) throws Iso8583Exception {

		Item8583 itemNode;
		String fldName;
		String fldVal;
		byte[] valByte = null;
		int idx = 0;

		for (int fieldId : packetFiledId) {
			idx = fieldId - 1;

			itemNode = iso8583.getBody().get(fieldId);
			fldName = itemNode.getPropertyName();
			fldVal = (String) ctx.get(fldName);
			// if (fldVal == null) {
			// //必输判断
			// if (mustSet.contains(bitIdx)) {
			// throw new
			// Iso8583Exception(itemNode.getFieldId()+itemNode.getPropertyName()+"必须有值");
			// } else {
			// continue;
			// }
			// }
			if (fldVal == null) {
				continue;
			}
			if (StringUtils.equals(itemNode.getConvertor(), "hex")) {
				valByte = AsciiCodec.ascStr2Bcd(fldVal);
			} else {
				// if(fieldId == 88){
				try {
					valByte = fldVal.getBytes("gbk");
				} catch (UnsupportedEncodingException e) {
					log.error("编码异常", e);
				}

				// }else{

				// valByte = fldVal.getBytes();
				// }
			}
			
			valByte = fitPlain(valByte, itemNode, log);

			/**
			 * //原来处理接口 valByte = fitPlain(fldVal, itemNode, log);
			 */
			out.write(valByte, 0, valByte.length);

			bitMapByte[idx] = '1';
			String fid = String.valueOf(idx + 1);
			if(!"2".equals(fid) &&!"35".equals(fid) && !"36".equals(fid) && !"14".equals(fid) && !"52".equals(fid)){
				if(isVerbose()){
					log.info("packItemOk|{}|{}|{}|{}", fldName, fid, valByte.length, AsciiCodec.byteArrayToHex(valByte));
				}
			}else{
				if(isVerbose()){
					if(log.isDebugEnabled()){
						log.debug("packItemOk|{}|{}|{}|{}", fldName, fid, valByte.length, AsciiCodec.byteArrayToHex(valByte));
					}
					log.info("packItemOk|{}|{}|{}|************", fldName, fid, valByte.length);
				}
			}
		}

		// 标识128或64
		if (idx >= 64 && bitMapByte[0] == '0') {
			bitMapByte[0] = '1';
		}
	}

	/**
	 * 组装报文头
	 * 
	 * @param ctx
	 *            报文头要填充的域所对应的数据
	 * @param out
	 *            ISO8583协议数据包头部分
	 * @param iso8583
	 *            ISO8583协议格式
	 */
	private static void packHeader(Map<String, Object> ctx,
			ByteArrayOutputStream out, Iso8583 iso8583) {
		List<Item8583> headerRoot = iso8583.getHead();
		String fldName;
		String fldVal;
		byte[] valByte;
		for (Item8583 itemNode : headerRoot) {
			fldName = itemNode.getPropertyName();
			fldVal = (String) ctx.get(fldName);

			valByte = fitPlain(fldVal.getBytes(), itemNode, log);

			out.write(valByte, 0, valByte.length);
			
			if(isVerbose()){
				log.info("HiPack8583.packItemOk", fldName,
					String.valueOf(valByte.length), new String(valByte));
			}
		}

		if (isVerbose()) {
			log.info("<==============Pack 8583 Header End");
		}
	}

	// private synchronized void initCfgNode(HiMessageContext ctx)
	// throws HiException {
	// if (isInit) {
	// return;
	// }
	//
	// Logger log = HiLog.getLogger(ctx.getCurrentMsg());
	// if (log.isDebugEnabled()) {
	// log.debug("first initCfgNode - start.");
	// }
	//
	// Element cfgRoot = (Element) ctx.getProperty(HiConstants.CFG8583_NODE);
	// if (cfgRoot == null) {
	// throw new HiException(HiMessageCode.ERR_PACK8583_CFGNODE, "");
	// }
	//
	// msgTypCodNode = HiXmlHelper.selectSingleNode(cfgRoot,
	// HiConstants.CFG8583_ITEM_NAME,
	// HiConstants.CFG8583_ITEM_FIELD_ID, "0");
	// // bitMapNode = HiXmlHelper.selectSingleNode(cfgRoot,
	// // HiConstants.CFG8583_ITEM_NAME, HiConstants.CFG8583_ITEM_FIELD_ID,
	// // "1");
	// // bitMapLen =
	// //
	// Integer.parseInt(bitMapNode.attributeValue(HiConstants.CFG8583_ITEM_LENGTH));
	//
	// itemNodeMap.clear();
	//
	// putMapNode(cfgRoot, mustSet);
	// putMapNode(cfgRoot, optSet);
	//
	// checkHeaderCfg(cfgRoot);
	//
	// isInit = true;
	//
	// if (log.isDebugEnabled()) {
	// log.debug("must_fields [" + this.must_fields + "]");
	// log.debug("opt_fields [" + this.opt_fields + "]");
	// log.debug("first initCfgNode - end.");
	// }
	// }

	// private void putMapNode(Element cfgRoot, Set fieldSet) throws HiException
	// {
	// Element itemNode;
	// Integer fieldIdx;
	//
	// Iterator it = fieldSet.iterator();
	// while (it.hasNext()) {
	// fieldIdx = (Integer) it.next();
	//
	// itemNode = HiXmlHelper.selectSingleNode(cfgRoot,
	// HiConstants.CFG8583_ITEM_NAME,
	// HiConstants.CFG8583_ITEM_FIELD_ID, fieldIdx.toString());
	// if (itemNode == null) {
	// throw new HiException(HiMessageCode.INVALID_PACK8583_FIELD_ID,
	// String.valueOf(fieldIdx));
	// }
	//
	// itemNodeMap.put(fieldIdx, itemNode);
	//
	// }
	// }
	//
	// private void checkValidField(String fields, HashSet fldSet)
	// throws HiException {
	// Integer idx;
	// StringTokenizer tokenizer = new StringTokenizer(fields, "|");
	// while (tokenizer.hasMoreElements()) {
	// try {
	// idx = Integer.valueOf(tokenizer.nextToken());
	//
	// fldSet.add(idx);
	//
	// } catch (NumberFormatException ne) {
	// throw new HiException(HiMessageCode.INVALID_PACK8583_FIELDS,
	// getNodeName(), fields);
	// }
	// if (idx.intValue() <= 1 || idx.intValue() > bitMapLen) {
	// throw new HiException(HiMessageCode.TOOBIG_PACK8583_FIELD_ID,
	// fields);
	// }
	// }
	// }
	//
	// /**
	// * 若配置需组报文头，检查是否有配置Header节点
	// *
	// * @param cfgRoot
	// * @throws HiException
	// */
	// private void checkHeaderCfg(Element cfgRoot) throws HiException {
	// if (this.pack_header) {
	// headerRoot = HiXmlHelper.selectSingleNode(cfgRoot, "Header");
	// if (headerRoot == null) {
	// throw new HiException("", "8583配置文件没有Header配置节点");
	// }
	//
	// // 检查mLen_pos,hLen_pos
	// Element item;
	// if (hLen_pos != null) {
	// // item = HiXmlHelper.selectSingleNode(cfgRoot,
	// // HiConstants.CFG8583_ITEM_NAME,
	// // HiConstants.CFG8583_ITEM_FIELD_ID, hLen_pos);
	// item = HiXmlHelper.selectSingleNode(headerRoot,
	// HiConstants.CFG8583_ITEM_NAME,
	// HiConstants.CFG8583_ITEM_FIELD_ID, hLen_pos);
	// if (item == null) {
	// throw new HiException("", "8583配置文件没有该配置节点 field_id:"
	// + hLen_pos);
	// }
	// hLen_name = item
	// .attributeValue(HiConstants.CFG8583_ITEM_ETF_NAME);
	// // 预计算报文头的长度;header_len若不为-1，预先配置,不需要计算
	// if (this.header_len == -1) {
	// this.header_len = countHeaderLen();
	// }
	// }
	//
	// if (mLen_pos != null) {
	// // item = HiXmlHelper.selectSingleNode(cfgRoot,
	// // HiConstants.CFG8583_ITEM_NAME,
	// // HiConstants.CFG8583_ITEM_FIELD_ID, mLen_pos);
	// item = HiXmlHelper.selectSingleNode(headerRoot,
	// HiConstants.CFG8583_ITEM_NAME,
	// HiConstants.CFG8583_ITEM_FIELD_ID, mLen_pos);
	// if (item == null) {
	// throw new HiException("", "8583配置文件没有该配置节点 field_id:"
	// + mLen_pos);
	// }
	// mLen_name = item
	// .attributeValue(HiConstants.CFG8583_ITEM_ETF_NAME);
	// }
	// }
	//
	// }

	// private int countHeaderLen() {
	// if (headerRoot == null) {
	// return 0;
	// }
	// // todo count Header length
	// return 46;
	// }

	/**
	 * 根据Item配置节点格式转换域所对应的数据
	 * 
	 * @param valByte
	 *            域所对应的数据
	 * @param itemNode
	 *            Item8583节点
	 * @param log
	 *            日志
	 * @return 所传Item8583节点对应的报文字节流
	 * @throws Iso8583Exception
	 *             如果ISO8583协议格式/数据不完整或不合法
	 */
	private static byte[] fitPlain(byte[] valByte, Item8583 itemNode, Logger log)
			throws Iso8583Exception {
		String field_id = itemNode.getFieldId();
		String length_type = itemNode.getLengthType();

		String data_type = itemNode.getDataType();

		if (length_type.equals(Iso8583Constants.LENGTH_TYPE_CONST)) {
			int length = Integer.parseInt(itemNode.getLength());
			return fitConstPlain(itemNode, field_id, valByte, data_type,
					length, log);
		} else if (length_type.equals(Iso8583Constants.LENGTH_TYPE_VAR2)) {
			return fitVarPlain(itemNode, field_id, valByte, data_type, 2, log);
		} else if (length_type.equals(Iso8583Constants.LENGTH_TYPE_VAR3)) {
			return fitVarPlain(itemNode, field_id, valByte, data_type, 3, log);
		}

		throw new Iso8583Exception("不合法的LENGTH_TYPE" + "待组包的域,field_id["
				+ field_id + "], 该长度类型 length_type[" + length_type + "] 有误.");
	}

	/**
	 * 根据Item配置节点，对于length_type是CONST时，若长度不足时按指定的align_mode填充
	 * <ul>
	 * <li>当data_type的值为CharASCII, align_mode默认为左对齐, 填充数据（fill_asc）为空格</li>
	 * <li>当data_type的值为其它类型, align_mode默认为右对齐, 填充数据（fill_asc）为0</li>
	 * </ul>
	 * 
	 * @param itemNode
	 *            Item8583节点
	 * @param field_id
	 *            Item8583节点的field_id值
	 * @param valBytes
	 *            域所对应的数据
	 * @param data_type
	 *            Item8583节点的data_type值
	 * @param length
	 *            Item8583节点的length值
	 * @param log
	 *            日志
	 * @return 所传Item8583节点对应的报文字节流
	 * @throws Iso8583Exception
	 *             如果ISO8583协议格式/数据不完整或不合法
	 */
	private static byte[] fitConstPlain(Item8583 itemNode,
			final String field_id, byte[] valBytes, final String data_type,
			int length, Logger log) throws Iso8583Exception {
		int valLen = valBytes.length;

		// 默认 截取多余长度
		if (valLen > length) {
			valBytes = ArrayUtils.subarray(valBytes, 0, length);
		}

		// BCD前的数据长度为偶数
		if (!data_type.endsWith("ASCII") && length % 2 != 0) {
			length += 1;
		}

		if (valLen < length) {
			// 填充字符
			// String fill_asc = itemNode.attributeValue("fill_asc");
			String fill_asc = null;
			if (data_type.equals(Iso8583Constants.DATA_TYPE_CHARASCII)) {
				// default 空格 原字符左对齐
				valBytes = fillCharRight(valBytes, fill_asc,
						itemNode.getAlignMode(), length - valLen);

			} else {
				// default 0 原字符右对齐
				valBytes = fillCharLeft(valBytes, fill_asc,
						itemNode.getAlignMode(), length - valLen);
			}
		}

		if (!data_type.endsWith("ASCII")) {
			valBytes = ascStr2Bcd(valBytes);
		}
		// log.info("packet[" + field_id + "]" + new String(valBytes));
		return valBytes;
	}

	/**
	 * 根据Item配置节点，对于length_type是VAR时，若长度不足时按指定的align_mode填充
	 * <ul>
	 * <li>当data_type的值为ASCBCD或NUMBCD，align_mode默认为右对齐，填充数据（fill_asc）为0</li>
	 * <li>var_type取值char或bin, 默认为char</li>
	 * </ul>
	 * 
	 * @param itemNode
	 *            Item8583节点
	 * @param field_id
	 *            Item8583节点的field_id值
	 * @param valBytes
	 *            域所对应的数据
	 * @param data_type
	 *            Item8583节点的data_type值
	 * @param varLen
	 *            域所对应的数据长度的位数
	 * @param log
	 *            日志
	 * @return 所传Item8583节点对应的报文字节流
	 * @throws Iso8583Exception
	 *             如果ISO8583协议格式/数据不完整或不合法
	 */
	private static byte[] fitVarPlain(Item8583 itemNode, final String field_id,
			byte[] valBytes, final String data_type, final int varLen,
			Logger log) throws Iso8583Exception {
		// byte[] valBytes = value.getBytes();
		int valueLen = valBytes.length;

		int valueAllocLen = valueLen;
		int varAllocLen = varLen;

		// BCD, 长度奇数时,补足处理
		if (!data_type.endsWith("ASCII")) {
			if (valueLen % 2 != 0) {
				// default 0 原字符右对齐
				valBytes = fillCharLeft(valBytes, null,
						itemNode.getAlignMode(), 1);
				valueAllocLen += 1;

			}

			valBytes = ascStr2Bcd(valBytes);

			valueAllocLen = valueAllocLen / 2; // 数据内容BCD后实际宽度
		} else if (StringUtils.equals(data_type, "CharBinASCII")) {
			valBytes = AsciiCodec.ascStr2Bcd(new String(valBytes));
			valueLen = valBytes.length;
			valueAllocLen = valueLen;
		}

		// 前置长度
		String valLenStr = String.valueOf(valueLen);
		if (valLenStr.length() > varLen) {
			throw new Iso8583Exception("错误的var_len" + String.valueOf(varLen)+"-----------"
					+ String.valueOf(field_id));
		}

		byte[] valLenBytes;

		if (StringUtils.equals(itemNode.getVarType(),
				Iso8583Constants.VAR_TYPE_BIN)) {
			// 前置长度,偶数,BCD格式
			if (varLen % 2 != 0) {
				varAllocLen += 1;
			}

			if (valLenStr.length() < varAllocLen) {
				valLenStr = StringUtils.leftPad(valLenStr, varAllocLen, '0');
			}

			valLenBytes = AsciiCodec.ascStr2Bcd(valLenStr);

			varAllocLen = varAllocLen / 2; // 前置长度bcd后的实际宽度
		} else {
			if (valLenStr.length() < varLen) {
				valLenStr = StringUtils.leftPad(valLenStr, varLen, '0');
			}

			valLenBytes = valLenStr.getBytes();
		}

		// 组合变长域报文
		ByteBuffer bb = ByteBuffer.allocate(varAllocLen + valueAllocLen);
		bb.put(valLenBytes);
		bb.put(valBytes);

		return bb.array();
	}

	/**
	 * 填充字符, 默认从左填充0, 原字符右靠对齐
	 * 
	 * @param value
	 *            要填充的字符
	 * @param fill_asc
	 *            填充字符的ASC
	 * @param align_mode
	 *            填充方式
	 * @param repeat
	 *            填充长度
	 * @return 填充后的字符
	 */
	private static byte[] fillCharLeft(byte[] value, String fill_asc,
			String align_mode, int repeat) {
		if (repeat <= 0) {
			return value;
		}
		if (StringUtils.isEmpty(fill_asc)) {
			fill_asc = "48";// default 0的ASC
		}

		byte b = Integer.valueOf(fill_asc).byteValue();
		byte[] fillValue = new byte[value.length + repeat];
		// default 右对齐
		if (StringUtils.equals(align_mode, Iso8583Constants.ALIGN_MODE_LEFT)) {
			System.arraycopy(value, 0, fillValue, 0, value.length);
			for (int i = 0; i < repeat; i++) {
				fillValue[value.length + i] = b;
			}
		} else {
			for (int i = 0; i < repeat; i++) {
				fillValue[i] = b;
			}
			System.arraycopy(value, 0, fillValue, repeat, value.length);
		}
		return fillValue;
	}

	/**
	 * 填充字符, 默认从右填充空格, 原字符左对齐
	 * 
	 * @param value
	 *            要填充的字符
	 * @param fill_asc
	 *            填充字符的ASC
	 * @param align_mode
	 *            填充方式
	 * @param repeat
	 *            填充长度
	 * @return 填充后的字符
	 */
	private static byte[] fillCharRight(byte[] value, String fill_asc,
			String align_mode, int repeat) {
		if (repeat <= 0) {
			return value;
		}
		if (StringUtils.isEmpty(fill_asc)) {
			fill_asc = "32";// default 空格的ASC
		}

		byte b = Integer.valueOf(fill_asc).byteValue();
		byte[] fillValue = new byte[value.length + repeat];

		// default 左对齐
		if (StringUtils.equals(align_mode, Iso8583Constants.ALIGN_MODE_RIGHT)) {
			for (int i = 0; i < repeat; i++) {
				fillValue[i] = b;
			}
			System.arraycopy(value, 0, fillValue, repeat, value.length);
		} else {
			System.arraycopy(value, 0, fillValue, 0, value.length);
			for (int i = 0; i < repeat; i++) {
				fillValue[value.length + i] = b;
			}
		}
		return fillValue;
	}

	/**
	 * ascll码转bcd码
	 * 
	 * @param bytes
	 *            要转换的ascll码
	 * @return 转换后的bcd码
	 * @throws Iso8583Exception
	 *             如果ISO8583协议格式/数据不完整或不合法
	 */
	private static byte[] ascStr2Bcd(byte[] bytes) throws Iso8583Exception {
		try {
			bytes = AsciiCodec.decodeHex(bytes);
		} catch (CodecException e) {
			throw new Iso8583Exception("" + e);
		}

		return bytes;
	}

	public static boolean isVerbose() {
		return Iso8583Context.VERBOSE.get();
	}

}
