package com.cn.iso8583;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cn.hnust.pojo.TPtsBinUseful;
import com.cn.http.HttpClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Parse8583Test {
	static final Logger LOG = LoggerFactory.getLogger(HttpClient.class);

	public static void main(String[] args) throws Iso8583Exception, IOException, CodecException {
		System.out.println("lxzlxz" + new String(AsciiCodec
				.hexToByteArray("3336363231323134313130303030303030303033373D32323039313031383536393033313033")));

		// String str =
		// "2E02303432323030303130303030202020303030313030303020202030303030303030303030303030303030303030323030F23C44C1A8E098100000000001000041313635313035323930303030303638343836303030303030303030303030303036323930303231383132303635313535313530323132303635313032313831393131353431313032313030303630383438333631303331303834383336303030303338353130353239303030303036383438363D3139313131303130303539353535313939393939303535313530323132303635313337323330343630383336313033313534313131343436B1B1BEA9CCECCAA2BFFDC9CCC3B3D6D0D0C420202020202020202020202020202020202020202020313536703B8DCD49837DA13236303030303030303030303030303030323330303030303630303033303030303030303030303030313039315049303836010A3131362E363931343131020933392E39333630313904023034051B303030303135303430303030313530343130304130303035333234060630363834383607084346394534393137080A31302E3036202020202030333232306338613536303466353234313937623561386233353336623765333736343842414335334639";
		String str = "0910F02404C02040981000042000050AC440165442430077560302900000000000100000863808220402109306375442430077560302D22041010000071300000037303030303030303130333531373231353630393738373820202600000000000000001422180505000501123231302E312E3235332E353801023033413032303132343235303534353135360110323030303030303030303030303030303231202020202020202000297B226F72644E6F223A223230313830353035313535353137303530227D00326237343836393163373535303432633562633630653834636431373235643562";
		Iso8583Context iso8583Context = new Iso8583Context();
		iso8583Context.setVerbose(true);
		byte[] reqbytesBack = ConvHelper.hexToByteArray(str);
		Map<String, Object> bodyMap = Iso8583Parser.parse("pos", reqbytesBack);
		String track2 = (String) bodyMap.get("TRACK_2");
		String track3 = (String) bodyMap.get("TRACK_3");
		String cc = (String) bodyMap.get("PIN_DATA");
		String iCC_data = (String) bodyMap.get("iCC_data");
		String pinData = (String) bodyMap.get("PIN_DATA");
		String pin = "";
		if (null != pinData) {
			pin = new String(AsciiCodec.hexToByteArray(pinData));
		}
		System.out.println(track2);
		if (null != track2) {
			track2 = track2.replaceAll("D", "=");
		}
//		Map<String, String> binMap = SearchBin(track2, track3);
//		System.out.println(binMap.toString());
		// String crdNo=aa.substring(0,aa.indexOf("="));

		// System.out.println(aa+"--"+bb+"--"+pin+"--"+crdNo);
		Map<String, String> map = SAXUnionFiled55Utils.getUnionField55Map((String) bodyMap.get("iCC_data"));
		System.out.println(map.toString());

	}

	/**
	 * 针对二道磁 三道磁 卡号的校验方法
	 * @param track2
	 * @param track3
	 * @param cardNo
	 * @return Map<String,Object> retMap
	 * @throws AppException
	 */
	public static Map<String, String> SearchBin(String track2, String track3) {
		LOG.info("BankCardInfoCheckComponent.SearchBin.begin");
		boolean isHandInputCard = false;
		String retCrdTyp = null;
		String retCrdNm = null;
		String retExpDat = null;
		String retCardNo = null;
		String retBnkTyp = null;
		String retFitNo = null;
		String retFitCtt = null;
		String retBnkNm = null;
//		String retOrgCd = null;
		String retBnkCode = null;
		String retIntMod = null;
		String foreignCarFlag = null;
		// 如果第二第三磁道和卡号均为空则报错
		if (StringUtils.isBlank(track2)) {
			isHandInputCard = true;
		}
		String cardNo=null;
		if (isHandInputCard && StringUtils.isEmpty(cardNo)) {
			throw new RuntimeException("POSxxxx手输卡号必须输入卡号");
		}
		// search flag
		boolean schFlg = false;
		int i = 0;
		TPtsBinUseful item = null;
		LOG.info("BankCardInfoCheckComponent.cardBinLoad.begin");
		List<TPtsBinUseful> ptsBinList=new Gson().fromJson(aa,new TypeToken<List<TPtsBinUseful>>(){}.getType());

		
		LOG.info("BankCardInfoCheckComponent.cardBinLoad.end");
		for (; i < ptsBinList.size(); i++) {
			
			item = ptsBinList.get(i);
if("6c2602675cc44bb9aa6ff0b96942fe97".equals(item.getUuid())){
				System.out.println(item.getBnkValue());
			}
			int fitLen = Integer.parseInt(item.getFitLen());
			int crdLen = Integer.parseInt(item.getCrdLen());
			int crdOfs = 0;
			int cdCdOf = 0;
			int fitOfs = 0;
//			int vaDtOf = Integer.parseInt(item.getExpDtFlg());
			if (isHandInputCard) {
				String fitValue = StringUtils.substring(cardNo, 0, fitLen - (cdCdOf - fitOfs));
				String fit2 = item.getFitCtt().substring(0, fitLen - (cdCdOf - fitOfs));
				// 增加判断账号长度
				if (!StringUtils.equals(fitValue, fit2) || cardNo.length() != crdLen) {
					continue;
				}
			} else {
				String track = null;

				// 根据FIT所在磁道取对应磁道数据
//				if ("2".equals(item.getFitTrk())) {
					track = track2;
//				} else {
//					track = track3;
//				}
				// 如果磁道数据为空则说明FIT所在磁道不符，跳过
				if (StringUtils.isEmpty(track)) {
					continue;
				}

				// 磁道数据不够时就表示不匹配,跳过
				if (fitOfs + fitLen > track.length()) {
					continue;
				}
				if (fitLen > item.getFitCtt().length()) {
					throw new RuntimeException("POS310003磁道偏移量+长度大于总长度");
				}

				String fit1 = track.substring(fitOfs, fitOfs + fitLen - (crdOfs - fitOfs));
				String fit2 = item.getFitCtt().substring(0, fitLen - (crdOfs - fitOfs));
				try {
					cardNo = track.substring(fitOfs, track.indexOf("="));
				} catch (Exception e) {
					continue;
				}

				// 卡BIN不匹配，跳过
				if (!StringUtils.equals(fit1, fit2) || cardNo.length() != crdLen) {
					continue;
				}

				if ("2".equals(item.getCrdTrk()) && StringUtils.isEmpty(track2)) {
					throw new RuntimeException("POS310001二磁不能为空");
				}

			/*	if ("3".equals(item.getCrdTrk()) && StringUtils.isEmpty(track3)) {
					throw new AppException("POS310002", "三磁不能为空");
				}*/
				// 根据卡号所在磁道取对磁道数据
//				if ("2".equals(item.getCrdTrk())) {
					track = track2;
//				} else {
//					track = track3;
//				}
				// 处理卡号长度为0的情况
				if (crdLen > 0) {
					if (crdOfs + crdLen > track.length()) {
						throw new RuntimeException("POS310004三磁不能为空");
					}
					cardNo = track.substring(crdOfs, crdOfs + crdLen);
					if (cardNo.contains("=")) {
						cardNo = cardNo.split("=")[0];
					}
				} else {
					// 卡号长度为0,截取到=位置
					int idx = track.indexOf("=", crdOfs);
					if (idx == -1) {
						throw new RuntimeException("POS310006二磁道中没有'='字符");
					}

					cardNo = track.substring(crdOfs, idx);

				}
				// 处理有效期
//				if (vaDtOf >= 0) {
//					if (vaDtOf + 4 > track.length()) {
//						throw new AppException("POS310010", "卡有效期偏移量非法");
//					}
//					String expDat = track.substring(vaDtOf, vaDtOf + 4);
//					if (!StringUtils.isNumeric(expDat)) {
//						throw new AppException("POS310011", "卡有效期非法");
//					}
//
//					if (NumberUtils.toInt(expDat.substring(2, 2)) > 12) {
//						throw new AppException("POS310011", "卡有效期非法");
//					}
//					retExpDat = expDat;
//				} else {
					int idx = track2.indexOf("=");
					if (idx == -1 || idx == 0) {
						throw new RuntimeException("POS310006二磁道中没有'='字符");
					}
					String expDat = track2.substring(idx + 1, idx + 1 + 4);
					if (!StringUtils.isNumeric(expDat)) {
						throw new RuntimeException("POS310011卡有效期非法");
					}

					if (NumberUtils.toInt(expDat.substring(2, 2)) > 12) {
						throw new RuntimeException("POS310011卡有效期非法");
					}
					retExpDat = expDat;
				}
//			}
			// 已匹配到，设置schflg＝1
			schFlg = true;
			if (cardNo.contains("=")) {
				cardNo = cardNo.split("=")[0];
			}
			retCardNo = cardNo;
			retCrdTyp = item.getCrdTyp();
			retBnkTyp = item.getBnkTyp();
			retCrdNm = item.getCrdNm();
			retFitNo = item.getFitNo();
			retFitCtt = item.getFitCtt();
			retBnkNm = item.getBnkNm();
			retBnkTyp = item.getBnkTyp();
			retBnkCode = item.getBnkCode();
			retIntMod = item.getIntMod();
			foreignCarFlag = item.getForeignCarFlag();

			if (!(StringUtils.equals(item.getBnkTyp(), "0006"))) {
				break;
			}

			if (cdCdOf + 3 >= cardNo.length()) {
				throw new RuntimeException("POS310005cdCdOf + 3 >= cardNo.length");
			}

			break;
		}
		Map<String, String> retMap = null;
		if (schFlg) {
			retMap = new HashMap<String, String>();
			retMap.put("retCrdTyp", retCrdTyp);
			retMap.put("retBnkTyp", retBnkTyp);
			retMap.put("retCrdNm", retCrdNm);
			retMap.put("retExpDat", retExpDat);
			retMap.put("retCardNo", retCardNo);
			retMap.put("retFitNo", retFitNo);
			retMap.put("retFitCtt", retFitCtt);
			retMap.put("retBnkNm", retBnkNm);
			retMap.put("retBnkCode", retBnkCode);
			retMap.put("retIntMod", retIntMod);
			retMap.put("foreignCarFlag", foreignCarFlag);
		}

		LOG.info("BankCardInfoCheckComponent.SearchBin.end");
		return retMap;
	}
	
	
}