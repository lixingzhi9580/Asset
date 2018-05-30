package com.tgb.ccl.http;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cn.hnust.service.DHFirstMenuService;
import com.cn.hnust.utils.TraderLogUtil;
import com.cn.http.HttpClient;
import com.cn.http.MD5;
import com.cn.http.common.HttpConfig;
import com.cn.http.exception.HttpProcessException;
import com.cn.http.httpclient.HttpClientUtil;

@RunWith(SpringJUnit4ClassRunner.class)
// 表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class HBERM {
	private final static Logger logger = LoggerFactory.getLogger(HBERM.class);
	@Resource
	private DHFirstMenuService dHFirstMenuService;
	private static final int readTimeout = 80000;

	@Test
	public void test() throws InterruptedException {

		ExecutorService service = Executors.newFixedThreadPool(25);
		for (int i = 0; i < 100; i++) {
			System.out.println("创建线程" + i);
			Runnable run = new Runnable() {
				@Override
				public void run() {
					for (int j = 0; j < 1; j++) {
						String uuid = UUID.randomUUID().toString().replace("-", "");
						TraderLogUtil.initUUID(uuid);
						Map<String, Object> contentData = new HashMap<String, Object>();
						contentData.put("p", "150024");// partnerId
						contentData.put("t", String.valueOf(System.currentTimeMillis()));// 时间戳
						contentData.put("r", "01");// 随机字符串
						contentData
								.put("n",
										"http://118.194.198.6:30091/lemon-gateway-out/cupe/trandata_update");// 通知地址
						contentData.put("p0", "1");// 扩展属性
						contentData.put("p1", "91005293");// 机具号
						contentData.put("p2", uuid);// 商户订单号
						contentData.put("p3", "000000000055");// 金额
						signData(contentData); // 商户代码
						try {
							HttpConfig config = HttpConfig.custom().url("http://vc.counect.com/vcupe/getPay.do").outenc("UTF-8").map(contentData);
							String loginform = null;
							try {
								loginform = HttpClientUtil.post(config);
							} catch (HttpProcessException e1) {
							}// 可以用.send(config)代替，但是推荐使用明确的get方法
							logger.info(loginform);
//							HttpClient hc = new HttpClient(
//									"http://vc.counect.com/vcupe/getPay.do", readTimeout,
//									readTimeout);
//							
//							int status = hc.send(contentData, "UTF-8");
//							if (200 == status) {
//							} else {
//								logger.info("非200小福返回");
//							}
						} catch (Exception e) {
							if (!(e instanceof SocketTimeoutException)) {
								logger.error("SocketTimeoutException小福同步交易请求，发送失败", e);
							} else {
								logger.error("Exception小福同步交易请求，发送失败", e);
							}
						}
					}
				}
			};
			// 在未来某个时间执行给定的命令
			service.execute(run);
		}
		// 关闭启动线程
		service.shutdown();
		// 等待子线程结束，再继续执行下面的代码
		service.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
		System.out.println("all thread complete");
		
		
		/*int ermCount = 1;
		while (ermCount > 0) {
			String uuid = UUID.randomUUID().toString().replace("-", "");
			TraderLogUtil.initUUID(uuid);
			Map<String, String> contentData = new HashMap<String, String>();
			contentData.put("p", "150024");// partnerId
			contentData.put("t", String.valueOf(System.currentTimeMillis()));// 时间戳
			contentData.put("r", "01");// 随机字符串
			contentData
					.put("n",
							"http://118.194.198.6:30091/lemon-gateway-out/cupe/trandata_update");// 通知地址
			contentData.put("p0", "1");// 扩展属性
			contentData.put("p1", "91005293");// 机具号
			contentData.put("p2", uuid);// 商户订单号
			contentData.put("p3", "000000000055");// 金额
			signData(contentData); // 商户代码
			try {
				HttpClient hc = new HttpClient(
						"http://vc.counect.com/vcupe/getPay.do", readTimeout,
						readTimeout);
				int status = hc.send(contentData, "UTF-8");
				if (200 == status) {
				} else {
					logger.info("非200小福返回");
				}
			} catch (Exception e) {
				if (!(e instanceof SocketTimeoutException)) {
					logger.error("SocketTimeoutException小福同步交易请求，发送失败", e);
				} else {
					logger.error("Exception小福同步交易请求，发送失败", e);
				}
			}
			if (ermCount == 102) {
				ermCount = -2;
			}
			ermCount++;
		}*/
	}

	public void signData(Map<String, Object> contentData) {
		String k = String.format("%s%s%s%s%s%s%s%s%s", contentData.get("n"),
				contentData.get("p"), contentData.get("p0"),
				contentData.get("p1"), contentData.get("p2"),
				contentData.get("p3"), contentData.get("r"),
				contentData.get("t"), "EC66578AE61AA2CD7ED56627359D785D")
				.replace("null", "");
		String s = MD5.GetMD5Code(k).toUpperCase();
		contentData.put("s", s); // 签名
	}
}
