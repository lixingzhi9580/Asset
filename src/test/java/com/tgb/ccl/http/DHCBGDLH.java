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
public class DHCBGDLH {
	private final static Logger logger = LoggerFactory.getLogger(DHCBGDLH.class);
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
		
//		Host: xy2.cbg.163.com
//		User-Agent: Mozilla/5.0 (Windows NT 6.3; Win64; x64; rv:45.0) Gecko/20100101 Firefox/45.0
//		Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
//		Accept-Language: zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3
//		Accept-Encoding: gzip, deflate
//		Referer: http://xy2.cbg.163.com/cgi-bin/equipquery.py
//		Cookie: last_login_roleid=289424653; sid=000CGXk5bvvXS2N7X0BZH8A8WLCkpEEz6ZrhWmRSiMs; msg_box_flag=1; _ntes_nnid=eade3f92833a8bd4818b492f0a06e887,1459155340162; _ntes_nuid=eade3f92833a8bd4818b492f0a06e887; usertrack=c+5+hVb89aQJF32OBAjMAg==; _ga=GA1.2.326398438.1459418558; vinfo_n_f_l_n3=83b56a1c61c8caca.1.22.1459508955678.1461153411706.1461294453486; __oc_uuid=2dea1b70-f7fa-11e5-9618-3de84bd83647; __utma=187553192.326398438.1459418558.1459508965.1459683242.2; __utmz=187553192.1459683242.2.2.utmcsr=vip.reg.163.com|utmccn=(referral)|utmcmd=referral|utmcct=/index.do; area_td_id=3; area_id=1; NTES_REPLY_NICKNAME=233lxz257%40163.com%7C215971469%7C-3877947346500852276%7C2883309385%7Chttp%3A%2F%2Fmimg.126.net%2Fp%2Fbutter%2F1008031648%2Fimg%2Fface_big.gif%7C%7C1%7C2; latest_views=121_1268101-121_1268936-121_1266219-121_1274433-121_1274434-121_1274435-121_1274436-121_1274437-121_1274438-121_1274439-121_1251459-121_1274657-121_1274659-121_1220697; __gads=ID=0c69a4a85bb85144:T=1460113325:S=ALNI_MYxSeQWqj1425M3RKZUd2oHmSak-A; vjuids=b6bf93aa9.153f58a29b4.0.0962278de73e18; vjlast=1460113386.1460466322.11; nteslogger_exit_time=1460308346558; P_INFO=233lxz257@163.com|1461306536|0|cbg|11&14|bej&1461303470&exchange#bej&null#10#0#0|158557&0|xy2&cbg|233lxz257@163.com; last_login_roleid=289424653; overall_sid=000Vicv7CmdHlIEZXdEPwWQMvif4xLlq9CiqF8io9Al; cbg_login_ck=000OqM770df6eKNehMI5YVGoe9ftZzaaUQyfMsmK-Sq; reg_captcha_ck=00072_Pbyc8rSX1P711kVdpcatuw6HqC4kh4FMd5MrU; NTES_SESS=mQeifeDHxl7g7PNIXWLPCi_HZp.jdN3Xk_hWDARESRC4Ke8zgMqQWi.qV0mzZxwkvERcu1sQ9NFsgfi8xZSefc8APfGSykBPGddCxMiRoROR9v7QvRQh3NmMeCZL__mTRZvD818L2cKwS1z0YwJWeF16XzkOZ6Tlz1OLIh.c9ORhIEHI1g.l7mOiu; S_INFO=1461306536|0|3&100##|233lxz257; ANTICSRF=a2b51798e2fe48e3480aec9b93a84b3d; is_user_login=1; login_user_icon=60; login_user_roleid=289424653; login_user_level=13150; new_msg_num=0; unpaid_order_num=0; unpaid_order_price_total=0.00
//		Connection: keep-alive
		
		
		
		
		int page = 1;
		while (page > 0) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("kindid", "14");//男衣服
//			map.put("kindid", "10");// 男帽子
			map.put("ori_qiangfa", "17");// 原始强法 强三尸
			map.put("act", "overall_search_equip");
			
//			map.put("kindid", "12");// 项链
//			map.put("lianhua_attr", "55");// 炼化属性 三尸狂暴
			
			map.put("page", page);
			map.put("server_type", "3");
			map.put("kangxing_logic", "and");
			map.put("lianhua_logic", "and");
			map.put("special_skill_logic", "and");
			// map.put("equip_level", "16");

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
