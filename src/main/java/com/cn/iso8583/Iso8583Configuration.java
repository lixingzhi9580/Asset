package com.cn.iso8583;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.ClassPathResource;

/**
 * 获取ISO8583协议配置文件，获取路径classpath
 * @author kousl
 */
public class Iso8583Configuration {
	
	private static final String CONFIG_ISO8583_PATH = "iso8583" + File.separator;
	private static final ConcurrentHashMap<String, Iso8583> configurations = new ConcurrentHashMap<String, Iso8583>();
	
	private Iso8583Configuration() {
	}
	
	public static Iso8583 getIso8583(String iso8583Name) {
		String iso8583UpName = iso8583Name.toUpperCase();
		if (!configurations.containsKey(iso8583UpName)) {
			loadXML(iso8583UpName);
		}
		return configurations.get(iso8583UpName);
	}
	
	private  synchronized static void loadXML(String iso8583Name)
			throws Iso8583Exception {
		if (configurations.containsKey(iso8583Name)){
			return;
		}
		InputStream is;
		try {
			is = new ClassPathResource(CONFIG_ISO8583_PATH + iso8583Name+"_8583.XML").getInputStream();
		} catch (IOException e) {
			throw new Iso8583Exception("load 8583 configuration  failed", e);
		}
		SAXReader saxReader = new SAXReader();
		Element iso8583Root;
		try {
			iso8583Root = saxReader.read(is).getRootElement();
		} catch (DocumentException e) {
			throw new Iso8583Exception("read 8583 configuration  failed", e);
		}
		Item8583 item = null;
		Element eItem = null;

		ArrayList<Item8583> headItems = new ArrayList<Item8583>();
		Element header = iso8583Root.element(Iso8583Constants.HEADER);
		@SuppressWarnings("rawtypes")
		Iterator it = null;
		if (header != null) {
		    it = header.elementIterator(Iso8583Constants.ITEM);
			while (it.hasNext()) {
				eItem = (Element) it.next();
				item = new Item8583();
				elementToItem(item, eItem);
				headItems.add(item);
			}
		}
		ArrayList<Item8583> bodyItems = new ArrayList<Item8583>();
		it = iso8583Root.elementIterator(Iso8583Constants.ITEM);
		while (it.hasNext()) {
			eItem = (Element) it.next();
			item = new Item8583();
			elementToItem(item, eItem);
			bodyItems.add(item);
		}
		Iso8583 iso8583 = new Iso8583();
		iso8583.setHead(headItems);
		iso8583.setBody(bodyItems);
		checkIso8583(iso8583);
		//configurations.put(iso8583Name, iso8583);
		configurations.putIfAbsent(iso8583Name,
				iso8583);
	}

	private static void checkIso8583(Iso8583 iso8583) throws Iso8583Exception {
		if (iso8583.getBody().size() < 129)
			throw new Iso8583Exception(
					"8583 configuration must contain 129 fileds");
		int i = 0;
		int fieldId;
		for (Item8583 itemHead : iso8583.getHead()) {
			checkItem(itemHead);
		}
		boolean containZero = false;
		boolean containOne = false;
		for (Item8583 item : iso8583.getBody()) {
			try {
				fieldId = Integer.parseInt(item.getFieldId());
			} catch (NumberFormatException e) {
				throw new Iso8583Exception("Item [index: " + i
						+ "]'s field id must be integer, actual: "
						+ item.getFieldId());
			}
			if (fieldId != i) {
				throw new Iso8583Exception("Item [fieldId: " + fieldId
						+ "]'s field id must be " + i + ", actual: "
						+ item.getFieldId());
			}
			if (fieldId == 0) {
				containZero = true;
			}
			if (fieldId == 1) {
				containOne = true;
			}
			checkItem(item);
			i++;
		}
		if (!containZero) {
			throw new Iso8583Exception(
					"8583 configuration must contain item with field id: 0");
		}
		if (!containOne) {
			throw new Iso8583Exception(
					"8583 configuration must contain item with field id: 1");
		}

	}

	private static void checkItem(Item8583 item) throws Iso8583Exception {
		String itemAttr = null;
		if (StringUtils.isEmpty(item.getPropertyName())) {
			throw new Iso8583Exception(Iso8583Constants.PROP_NAME
					+ " for item [id: " + item.getFieldId() + "] is missed.");
		}
		itemAttr = item.getLengthType();
		if (StringUtils.isEmpty(itemAttr)) {
			throw new Iso8583Exception(Iso8583Constants.LENGTH_TYPE
					+ " for item [id: " + item.getFieldId() + "] is missed.");
		}

		if (itemAttr.equals(Iso8583Constants.LENGTH_TYPE_CONST)) {
			String lenAttr = item.getLength();
			if (StringUtils.isEmpty(lenAttr) || !StringUtils.isNumeric(lenAttr)) {
				throw new Iso8583Exception(Iso8583Constants.LENGTH
						+ " for item [id: " + item.getFieldId()
						+ "] is invalid.");
			}
		} else if (itemAttr.equals(Iso8583Constants.LENGTH_TYPE_VAR2)
				|| itemAttr.equals(Iso8583Constants.LENGTH_TYPE_VAR3)) {
			String varType = item.getVarType();
			if (StringUtils.isEmpty(varType)
					|| StringUtils.equals(varType,
							Iso8583Constants.VAR_TYPE_BIN)
					|| StringUtils.equals(varType,
							Iso8583Constants.VAR_TYPE_CHAR)) {
			} else {
				throw new Iso8583Exception("Var type for item [id: "
						+ item.getFieldId() + "] is invalid, expected '"
						+ Iso8583Constants.VAR_TYPE_BIN + "' or '"
						+ Iso8583Constants.VAR_TYPE_CHAR + "'");
			}
		} else {
			throw new Iso8583Exception("Length type for item [id: "
					+ item.getFieldId() + "] is invalid, expected '"
					+ Iso8583Constants.LENGTH_TYPE_CONST + "' or '"
					+ Iso8583Constants.LENGTH_TYPE_VAR2 + "' or '"
					+ Iso8583Constants.LENGTH_TYPE_VAR3 + "'");
		}

		itemAttr = item.getDataType();
		if (StringUtils.isEmpty(itemAttr)) {
			throw new Iso8583Exception("Data type for item [id: "
					+ item.getFieldId() + "] is missed.");
		}

		if (itemAttr.equals(Iso8583Constants.DATA_TYPE_ASCBCD)
				|| itemAttr.equals(Iso8583Constants.DATA_TYPE_BIT)
				|| itemAttr.equals(Iso8583Constants.DATA_TYPE_CHARASCII)
				|| itemAttr.equals(Iso8583Constants.DATA_TYPE_NUMASCII)
				|| itemAttr.equals(Iso8583Constants.DATA_TYPE_NUMBCD)) {
		} else {
			throw new Iso8583Exception("Data type for item [id: "
					+ item.getFieldId() + "] is invalid, expected '"
					+ Iso8583Constants.DATA_TYPE_ASCBCD + "' or '"
					+ Iso8583Constants.DATA_TYPE_BIT + "' or '"
					+ Iso8583Constants.DATA_TYPE_CHARASCII + "' or '"
					+ Iso8583Constants.DATA_TYPE_NUMASCII + "' or '"
					+ Iso8583Constants.DATA_TYPE_NUMBCD + "'");
		}

		itemAttr = item.getAlignMode();
		if (StringUtils.isEmpty(itemAttr)
				|| StringUtils.equals(itemAttr,
						Iso8583Constants.ALIGN_MODE_LEFT)
				|| StringUtils.equals(itemAttr,
						Iso8583Constants.ALIGN_MODE_RIGHT)) {
		} else {
			throw new Iso8583Exception("Align mode for item [id: "
					+ item.getFieldId() + "] is invalid, expected '"
					+ Iso8583Constants.ALIGN_MODE_LEFT + "' or '"
					+ Iso8583Constants.ALIGN_MODE_RIGHT + "'");
		}

	}

	private static void elementToItem(Item8583 headItem, Element ele) {
		headItem.setAlignMode(ele.attributeValue(Iso8583Constants.ALIGN_MODE));
		headItem.setDataType(ele.attributeValue(Iso8583Constants.DATA_TYPE));
		headItem.setFieldId(ele.attributeValue(Iso8583Constants.FIELD_ID));
		headItem.setLength(ele.attributeValue(Iso8583Constants.LENGTH));
		headItem.setLengthType(ele.attributeValue(Iso8583Constants.LENGTH_TYPE));
		headItem.setPropertyName(ele.attributeValue(Iso8583Constants.PROP_NAME));
		headItem.setVarType(ele.attributeValue(Iso8583Constants.VAR_TYPE));
		headItem.setConvertor(ele.attributeValue(Iso8583Constants.CONVERT));
	}
}
