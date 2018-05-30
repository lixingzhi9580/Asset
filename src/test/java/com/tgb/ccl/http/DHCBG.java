package com.tgb.ccl.http;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cn.hnust.pojo.DHFirstMenu;
import com.cn.hnust.pojo.DHVo;
import com.cn.hnust.service.DHFirstMenuService;
import com.cn.http.common.HttpConfig;
import com.cn.http.common.Utils;
import com.cn.http.exception.HttpProcessException;
import com.cn.http.httpclient.HttpClientUtil;
import com.google.gson.Gson;

@RunWith(SpringJUnit4ClassRunner.class)
// 表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class DHCBG {
	private final static Logger logger = LoggerFactory.getLogger(DHCBG.class);
	@Resource
	private DHFirstMenuService dHFirstMenuService;

	@Test
	public void test() {

		// aaa(map);
		// map.put("kindid", "10");//男帽子
		// map.put("ori_qiangfa", "17");//原始强法 强三尸
		// map.remove("lianhua_attr");
		// aaa(map);
		// map.put("kindid", "14");//男衣服
		aaa();
	}

	public void aaa() {
		int page = 1;
		while (page > 0) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("kindid", "14");//男衣服
//			map.put("kindid", "10");// 男帽子
			map.put("ori_qiangfa", "17");// 原始强法 强三尸
//			map.put("act", "overall_search_equip");//装备
			
//			map.put("kindid", "12");// 项链
//			map.put("lianhua_attr", "55");// 炼化属性 三尸狂暴
			
			map.put("page", page);
			map.put("server_type", "3");
			map.put("kangxing_logic", "and");
			map.put("lianhua_logic", "and");
			map.put("special_skill_logic", "and");
			// map.put("equip_level", "16");//等级

			
			
			
//			map.put("act", "overall_search_shenbing");//神兵
//					page=1
//					server_type=3
//					equip_type=1651,1652,1653,1654,1655,1656,1657,1658,1659,3251,3252,3253,3254,3255,3256
//					equip_level=4,5,6
//					special_skill_logic=and
			
			
			
			
			
			
			
			
			
			String loginUrl = "http://xy2.cbg.163.com/cgi-bin/search.py?"
					+ Utils.buildParas(map);

			HttpConfig config = HttpConfig.custom().url(loginUrl)
					.outenc("UTF-8").map(map);
			// 获取参数
			String loginform = null;
			try {
				loginform = HttpClientUtil.get(config);
			} catch (HttpProcessException e1) {
				logger.error("睡眠失败", e1);
				try {
					Thread.sleep(10000);
					continue;
				} catch (InterruptedException e2) {
					logger.error("睡眠失败", e2);
					continue;
				}
			}// 可以用.send(config)代替，但是推荐使用明确的get方法
			logger.info(loginform);

			int idx = loginform.indexOf("status");
			String status = loginform.substring(idx + 9, idx + 10);
			if (StringUtils.isEmpty(status) || !"0".equals(status)) {
				Map<String, String> mapSts = new HashMap<>();
				mapSts = new Gson().fromJson(loginform, Map.class);
				logger.info("第{}页，返回失败原为：{}", page, new Gson().toJson(mapSts));
				try {
					Thread.sleep(10000);
					continue;
				} catch (InterruptedException e3) {
					logger.error("睡眠失败", e3);
					continue;
				}
			}
			DHVo dHVo = new Gson().fromJson(loginform, DHVo.class);
			if (null == dHVo.getMsg() || dHVo.getMsg().size() == 0) {
				page = -1;
			}
			logger.debug(new Gson().toJson(dHVo));
			logger.debug(new Gson().toJson(dHVo.getPaging()));
			List<DHFirstMenu> dHFirstMenus = dHVo.getMsg();
			if (dHFirstMenus.size() == 0) {
				continue;
			}

			for (DHFirstMenu dHFirstMenu : dHFirstMenus) {
				dHFirstMenuService.replaceInsert(dHFirstMenu);
			}
			logger.info("第{}页读取成功", page);
			if (page >= dHVo.getPaging().getTotal_pages()) {
				page = -1;
			}
			page++;
			try {
				Thread.sleep(3000);
				continue;
			} catch (InterruptedException e3) {
				logger.error("睡眠失败", e3);
				continue;
			}
		}
	}
}
