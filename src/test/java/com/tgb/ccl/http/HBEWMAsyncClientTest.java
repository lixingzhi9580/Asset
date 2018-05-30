//package com.tgb.ccl.http;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//import org.apache.http.Header;
//import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.cn.http.common.HttpConfig;
//import com.cn.http.common.HttpHeader;
//import com.cn.http.common.HttpHeader.Headers;
//import com.cn.http.exception.HttpProcessException;
//import com.cn.http.httpclient.HttpAsyncClientUtil;
//import com.cn.http.httpclient.HttpAsyncClientUtil.IHandler;
//import com.cn.http.httpclient.builder.HACB;
//
///** 
// * 
// * @author arron
// * @date 2015年11月1日 下午2:23:18 
// * @version 1.0 
// */
//public class HBEWMAsyncClientTest {
//	private final static Logger logger = LoggerFactory.getLogger(HBEWMAsyncClientTest.class);
//	
//	public static void testMutilTask() throws HttpProcessException{
//		// URL列表数组
//		String[] urls = {
//				"http://blog.csdn.net/xiaoxian8023/article/details/49862725",
//				"http://blog.csdn.net/xiaoxian8023/article/details/49834643",
//				"http://blog.csdn.net/xiaoxian8023/article/details/49834615",
//				"http://blog.csdn.net/xiaoxian8023/article/details/49834589",
//				"http://blog.csdn.net/xiaoxian8023/article/details/49785417",
//				};
//		
//		String[] imgurls ={"http://ss.bdimg.com/static/superman/img/logo/logo_white_fe6da1ec.png",
//		"https://scontent-hkg3-1.xx.fbcdn.net/hphotos-xaf1/t39.2365-6/11057093_824152007634067_1766252919_n.png"};
//
//		// 设置header信息
//		long start = System.currentTimeMillis();
//            try {
//				int pagecount = urls.length;
//				ExecutorService executors = Executors.newFixedThreadPool(25);
//				CountDownLatch countDownLatch = new CountDownLatch(pagecount*10);
//				for(int i = 0; i< 1;i++){
//					CloseableHttpAsyncClient client= HACB.custom().timeout(10000).ssl().build();
//				    //启动线程抓取
//				    executors.execute(new GetRunnable(HttpConfig.custom().url("http://vc.counect.com/vcupe/getPay.do")));
//				    executors.execute(new GetRunnable(HttpConfig.custom().asynclient(client).url("http://vc.counect.com/vcupe/getPay.do").map(map)));
//				}
//				countDownLatch.await();
//				executors.shutdown();
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//	        } finally {
//	            System.out.println("线程" + Thread.currentThread().getName() + ", 所有线程已完成，开始进入下一步！");
//	        }
//	         
//	        long end = System.currentTimeMillis();
//	        System.out.println("总耗时（毫秒）： -> " + (end - start));
//	}
//	
//	 static class GetRunnable implements Runnable {
//	        private HttpConfig config = null;
//	        public GetRunnable(HttpConfig config){
//	        	this.config = config;
//	        }
//	        
//	        @Override
//	        public void run() {
//	            try {
//	            	if(config.out()==null){
//	            		HttpAsyncClientUtil.get(config);
//	            	}else{
//	            		HttpAsyncClientUtil.down(config);
//	            	}
//	            } catch (HttpProcessException e) {
//	            }
//	        }
//	    }  
//	
//	public static void main(String[] args) throws Exception {
////		testOne();
//		testMutilTask();
//	}
//}