package com.tgb.ccl.http;

import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cn.hnust.service.DHFirstMenuService;
import com.cn.http.HttpClient;
import com.cn.http.MD5;
import com.cn.http.httpclient.HttpClientUtil;
import com.cn.http.httpclient.builder.HCB;

@RunWith(SpringJUnit4ClassRunner.class)
// 表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class CopyOfHBERM {
	private final static Logger logger = LoggerFactory.getLogger(CopyOfHBERM.class);
	@Resource
	private DHFirstMenuService dHFirstMenuService;
	private int readTimeout = 50000;
	@Test
	public void test() throws InterruptedException {
//		Map<String, Object> contentData = new HashMap<String, Object>();
//		contentData.put("p", "15010001");//partnerId
//		contentData.put("t", String.valueOf(System.currentTimeMillis()));//时间戳
//		contentData.put("r", "01");// 随机字符串
//		contentData.put("n", "http://118.194.198.6:30091/lemon-gateway-out/cupe/trandata_update");//通知地址
//		contentData.put("p0", "1");// 扩展属性
//		contentData.put("p1", "91005293");// 机具号
//		contentData.put("p2", UUID.randomUUID().toString().replace("-", ""));// 商户订单号
//		contentData.put("p3", "000000000055");// 金额
//		signData(contentData); // 商户代码
		
//		String url="https://www.facebook.com/";
//		HttpClient client= HCB.custom().timeout(10000).proxy("127.0.0.1", 8087).ssl().build();//采用默认方式（绕过证书验证）
//		//执行请求
//		resp = HttpClientUtil.get(config.client(client));
//		System.out.println("请求结果内容长度："+ resp.length());
		
//		HttpConfig config = HttpConfig.custom().url("https://svrapi.test.webank.com/l/api/oauth2/access_token?grant_type=client_credential&secret=sedfasdgfdhweqrferrg&app_id=wz123456&version=1.0.0").outenc("UTF-8");
//
//		
//		HttpConfig config =HttpConfig.custom().url("https://svrapi.test.webank.com/l/api/oauth2/access_token?grant_type=client_credential&secret=sedfasdgfdhweqrferrg&app_id=wz123456&version=1.0.0");
//		//获取参数
//		String loginform = HttpClientUtil.get(config);//可以用.send(config)代替，但是推荐使用明确的get方法
//		
//		
//		
////		HttpConfig config = HttpConfig.custom().url("http://vc.counect.com/vcupe/getPay.do").outenc("UTF-8").map(contentData);
//		// 获取参数
//		String loginform = null;
//		try {
//			loginform = HttpClientUtil.get(config);
//		} catch (HttpProcessException e1) {
//			logger.error("睡眠失败",e1);
//			try {
//				Thread.sleep(10000);
//			} catch (InterruptedException e2) {
//				logger.error("睡眠失败",e2);
//			}
//		}// 可以用.send(config)代替，但是推荐使用明确的get方法
//		logger.info(loginform);
		
	}
	
	public void signData(Map<String, Object> contentData) {
		String k = String.format("%s%s%s%s%s%s%s%s%s", contentData.get("n"),
				contentData.get("p"), contentData.get("p0"),
				contentData.get("p1"), contentData.get("p2"),
				contentData.get("p3"), contentData.get("r"),
				contentData.get("t"),
				"E10ADC3949BA59ABBE56E057F20F883E").replace("null", "");
		String s = MD5.GetMD5Code(k).toUpperCase();
		contentData.put("s", s); // 签名
	}
}
