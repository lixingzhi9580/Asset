package com.tgb.ccl.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cn.hnust.pojo.DHFirstMenu;
import com.cn.hnust.pojo.DhDesc;
import com.cn.hnust.service.DHFirstMenuService;
import com.cn.hnust.service.DhDescService;
import com.google.gson.Gson;

@RunWith(SpringJUnit4ClassRunner.class)
// 表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class DHSxJiSuan {
	private final static Logger logger = LoggerFactory
			.getLogger(DHSxJiSuan.class);
	@Resource
	private DHFirstMenuService dHFirstMenuService;
	@Resource
	private DhDescService dhDescService;
	private final String[] kangTypes = new String[] { "抗封印", "抗混乱", "抗遗忘",
			"抗昏睡", "反震率", "反震程度", "躲闪率", "强力克", "三尸虫狂暴几率" };
	
	private final String[] yaoQiuTypes = new String[] { "要求" };

	@Test
	public void test() {
		List<Map<String, String>> mzList = new ArrayList<>();
		List<Map<String, String>> xlList = new ArrayList<>();
		List<Map<String, String>> yfList = new ArrayList<>();
		// 获取所有装备
		List<DHFirstMenu> mzdHFirstMenus = dHFirstMenuService
				.getDHFirstMenus("10");
		addList(mzList, xlList, yfList, mzdHFirstMenus);
		List<DHFirstMenu> yfDHFirstMenus = dHFirstMenuService
				.getDHFirstMenus("14");
		addList(mzList, xlList, yfList, yfDHFirstMenus);
		List<DHFirstMenu> xlDHFirstMenus = dHFirstMenuService
				.getXlDHFirstMenus();
		
		addList(mzList, xlList, yfList, xlDHFirstMenus);

//		 Map<String, String> yijiansuxing = new HashMap<String, String>();
//		 yijiansuxing.put("equip_name", "自己的");
//		 yijiansuxing.put("kindid", "14");
//		 yijiansuxing.put("game_ordersn", "自己的");
//		 yijiansuxing.put("price", "0");
//		 xlList.add(yijiansuxing);

		logger.debug("帽子[{}]", new Gson().toJson(mzList));
		logger.debug("衣服[{}]", new Gson().toJson(yfList));
		logger.debug("项链[{}]", new Gson().toJson(xlList));
		logger.info("对比开始");
		int num = 0;
		List<Map<String, String>> zbList = new ArrayList<>();
		for (Map<String, String> mz : mzList) {
			for (Map<String, String> xl : xlList) {
				for (Map<String, String> yf : yfList) {
					num++;
					Double mz0 = zbKan(mz.get(kangTypes[0]));
					Double mz1 = zbKan(mz.get(kangTypes[1]));
					Double mz2 = zbKan(mz.get(kangTypes[2]));
					Double mz3 = zbKan(mz.get(kangTypes[3]));
					Double mz4 = zbKan(mz.get(kangTypes[4]));
					Double mz5 = zbKan(mz.get(kangTypes[5]));
					Double mz6 = zbKan(mz.get(kangTypes[6]));
					Double mz7 = zbKan(mz.get(kangTypes[7]));
					Double mz8 = zbKan(mz.get(kangTypes[8]));

					Double xl0 = zbKan(xl.get(kangTypes[0]));
					Double xl1 = zbKan(xl.get(kangTypes[1]));
					Double xl2 = zbKan(xl.get(kangTypes[2]));
					Double xl3 = zbKan(xl.get(kangTypes[3]));
					Double xl4 = zbKan(xl.get(kangTypes[4]));
					Double xl5 = zbKan(xl.get(kangTypes[5]));
					Double xl6 = zbKan(xl.get(kangTypes[6]));
					Double xl7 = zbKan(xl.get(kangTypes[7]));
					Double xl8 = zbKan(xl.get(kangTypes[8]));

					Double yf0 = zbKan(yf.get(kangTypes[0]));
					Double yf1 = zbKan(yf.get(kangTypes[1]));
					Double yf2 = zbKan(yf.get(kangTypes[2]));
					Double yf3 = zbKan(yf.get(kangTypes[3]));
					Double yf4 = zbKan(yf.get(kangTypes[4]));
					Double yf5 = zbKan(yf.get(kangTypes[5]));
					Double yf6 = zbKan(yf.get(kangTypes[6]));
					Double yf7 = zbKan(yf.get(kangTypes[7]));
					Double yf8 = zbKan(yf.get(kangTypes[8]));

					for (int i = 0; i < 4; i++) {
						for (int j = 0; j < 4; j++) {
							boolean flg = true;
							Double mxy0 = mz0 + xl0 + yf0;
							Double mxy1 = mz1 + xl1 + yf1;
							Double mxy2 = mz2 + xl2 + yf2;
							Double mxy3 = mz3 + xl3 + yf3;
							Double mxy4 = mz4 + xl4 + yf4;
							Double mxy5 = mz5 + xl5 + yf5;
							Double mxy6 = mz6 + xl6 + yf6;
							Double mxy7 = mz7 + xl7 + yf7;
							Double mxy8 = mz8 + xl8 + yf8;

							Double e = Double.parseDouble(yf.get("price"))
									+ Double.parseDouble(mz.get("price"))
									+ Double.parseDouble(xl.get("price"));
							// "抗封印", "抗混乱", "抗遗忘","抗昏睡"
							Double count0 = mxy0 + 96.1 + 1;
							Double count1 = mxy1 + 98.7 + 12;
							Double count2 = mxy2 + 134.1 + 3;
							Double count3 = mxy3 + 45.6 + 10;
							Double count4 = mxy4 + 5;
							Double count5 = mxy5;
							Double count6 = mxy6;
							Double count7 = mxy7;
							Double count8 = mxy8;

							Double tk = (double) 10;

							Double pp = (double) 30;
							Double qq = (double) 15;
							Double[] abcd = new Double[] { count0, count1,
									count2, count3, count4, count5, count6,
									count7, count8 };
							if (abcd[i] != abcd[j]) {
								String msg = abcd[0] + "==" + abcd[1] + "=="
										+ abcd[2] + "==" + abcd[3];
								abcd[i] = abcd[i] + pp;
								abcd[j] = abcd[j] + qq;
								String msg1 = abcd[0] + "==" + abcd[1] + "=="
										+ abcd[2] + "==" + abcd[3];

								if (abcd[0] < 125.4 + 2 * tk) {
									flg = false;
								} else if (abcd[1] < 125.4 + 2 * tk) {
									flg = false;
								} else if (abcd[2] < 145.4 + 2 * tk) {
									flg = false;
								} else if (abcd[3] < 0 + 2 * tk) {
									flg = false;
								} else if (abcd[4] < 70) {
									flg = false;
								} else if (abcd[5] < 50) {
									flg = false;
								}
//								else if (e>200000){
//									flg = false;
//								}
								
								if (flg) {
									Map<String, String> zbMap = new HashMap<String, String>();
									zbMap.put("game_ordersnyf",
											yf.get("game_ordersn"));
									zbMap.put("game_ordersnmz",
											mz.get("game_ordersn"));
									zbMap.put("game_ordersnxl",
											xl.get("game_ordersn"));
									zbMap.put(kangTypes[0], mxy0.toString());
									zbMap.put(kangTypes[1], mxy1.toString());
									zbMap.put(kangTypes[2], mxy2.toString());
									zbMap.put(kangTypes[3], mxy3.toString());
									zbMap.put("price", e.toString());
									zbList.add(zbMap);
									logger.debug("{}", msg);
									logger.debug("{}", msg1);
									logger.info(
											"【{}提高30；{}提高15】【{}{}】【{}{}】【{}{}】【{}{}】【{}{}】【{}{}】【{}{}】【{}{}】【{}{}】每一个集合[{}]",
											kangTypes[i],kangTypes[j],
											kangTypes[0],
											String.format("%.2f", abcd[0] - tk),
											kangTypes[1],
											String.format("%.2f", abcd[1] - tk),
											kangTypes[2],
											String.format("%.2f", abcd[2] - tk),
											kangTypes[3],
											String.format("%.2f", abcd[3] - tk),
											kangTypes[4],
											String.format("%.2f", abcd[4]),
											kangTypes[5],
											String.format("%.2f", abcd[5]),
											kangTypes[6],
											String.format("%.2f", abcd[6]),
											kangTypes[7],
											String.format("%.2f", abcd[7]),
											kangTypes[8],
											String.format("%.2f", abcd[8]),
											new Gson().toJson(zbMap));
									
									
									
									
									logger.debug(
											"总金额：[{}];|{}总属性为：[{}];{}总属性为：[{}];{}总属性为：[{}];{}总属性为：[{}];",
											e, kangTypes[0], mxy0,
											kangTypes[1], mxy1, kangTypes[2],
											mxy2, kangTypes[3], mxy3);
								}
							}
						}
					}

					logger.debug(
							"装备序号：[{}];那个部位：[{}];装备名称：[{}];金额：[{}];|{}属性为：[{}];{}属性为：[{}];{}属性为：[{}];{}属性为：[{}];",
							mz.get("game_ordersn"), mz.get("kindid"),
							mz.get("equip_name"), mz.get("price"),
							kangTypes[0], mz.get(kangTypes[0]), kangTypes[1],
							mz.get(kangTypes[1]), kangTypes[2],
							mz.get(kangTypes[2]), kangTypes[3],
							mz.get(kangTypes[3]));
					logger.debug(
							"装备序号：[{}];那个部位：[{}];装备名称：[{}];金额：[{}];|{}属性为：[{}];{}属性为：[{}];{}属性为：[{}];{}属性为：[{}];",
							xl.get("game_ordersn"), xl.get("kindid"),
							xl.get("equip_name"), xl.get("price"),
							kangTypes[0], xl.get(kangTypes[0]), kangTypes[1],
							xl.get(kangTypes[1]), kangTypes[2],
							xl.get(kangTypes[2]), kangTypes[3],
							xl.get(kangTypes[3]));
					logger.debug(
							"装备序号：[{}];那个部位：[{}];装备名称：[{}];金额：[{}];|{}属性为：[{}];{}属性为：[{}];{}属性为：[{}];{}属性为：[{}];",
							yf.get("game_ordersn"), yf.get("kindid"),
							yf.get("equip_name"), yf.get("price"),
							kangTypes[0], yf.get(kangTypes[0]), kangTypes[1],
							yf.get(kangTypes[1]), kangTypes[2],
							yf.get(kangTypes[2]), kangTypes[3],
							yf.get(kangTypes[3]));
				}
			}
		}
		logger.info("对比中【{}】", num);
		logger.info("最终[{}]", new Gson().toJson(zbList));
	}

	public void addList(List<Map<String, String>> mzList,
			List<Map<String, String>> xlList, List<Map<String, String>> yfList,
			List<DHFirstMenu> dHFirstMenus) {
		for (DHFirstMenu dHFirstMenu : dHFirstMenus) {
			// 获取每件装备属性
			List<DhDesc> dhDescs = dhDescService.selectBySn(dHFirstMenu
					.getGame_ordersn());
			// 声明一件装备属性，《属性，属性值》
			Map<String, String> yijiansuxing = new HashMap<String, String>();

			yijiansuxing.putAll(new Gson().fromJson(
					new Gson().toJson(dHFirstMenu), yijiansuxing.getClass()));
			for (DhDesc dhDesc : dhDescs) {
				// 循环所有要提取的属性
				for (String kangType : kangTypes) {
					// 判断获取的属性，在提取的属性里
					if (dhDesc.getGameKey().indexOf(kangType) != -1) {
						// 将提取的属性放入装备属性中
						yijiansuxing.put(kangType, dhDesc.getGameValue());
						logger.debug("属性[{}],[{}]", kangType,
								dhDesc.getGameValue());
					}
				}
			}
			// 将属性放入一件装备中
			// yijian.put(dHFirstMenu.getGame_ordersn(), yijiansuxing);
			logger.debug("装备[{}],[{}]", dHFirstMenu.getGame_ordersn(),
					new Gson().toJson(yijiansuxing));
			// 判断装备类型，放入不同的集合中
			switch (dHFirstMenu.getKindid()) {
			case "10":
				mzList.add(yijiansuxing);
				break;
			case "12":
				xlList.add(yijiansuxing);
				break;
			case "14":
				yfList.add(yijiansuxing);
				break;
			default:
				break;
			}
		}
	}

	public Double zbKan(String kan) {
		Double zb = (double) 0;
		if (null != kan) {
			zb = Double.parseDouble(kan);
		}
		return zb;
	}
}
