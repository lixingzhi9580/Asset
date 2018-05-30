package com.cn.async.context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cn.async.bean.AsyncBean;
import com.cn.async.dto.AsyncRequest;
import com.cn.async.dto.AsyncResponse;


public class AsyncContextHelper {
	private static final Logger logger = LoggerFactory.getLogger(AsyncContextHelper.class);

	private static final ConcurrentHashMap<String, AsyncBean<AsyncRequest,AsyncResponse>> hashMap = new ConcurrentHashMap<String, AsyncBean<AsyncRequest,AsyncResponse>>();
	
	private static long aliveTime = 10L*60*1000;
	
	private static long destoryDelay = 5L*60*1000;
	
	static{
		Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(new TimeOutTask(), destoryDelay, destoryDelay, TimeUnit.MILLISECONDS);
	}
	
//	private static final ConcurrentHashMap<String, Thread> threadMap = new ConcurrentHashMap<String, Thread>();
//	
//	private static long timeout;
//	
//	private static final long DEFAULT_TIMEOUT = 10000L;
//	
//	static{
//		timeout = DEFAULT_TIMEOUT;
//	}
	
	public static void set(String key,AsyncBean<AsyncRequest,AsyncResponse> value)
	{
		if(null==value)
		{
			return;
		}
		if(value.getBornTime()<=0)
		{
			value.setBornTime(System.currentTimeMillis());
		}
		hashMap.put(key, value);
	}
	
	public static AsyncBean<AsyncRequest,AsyncResponse> get(String key)
	{
		if(null==key)
		{
			return null;
		}
		return hashMap.remove(key);
	}
	
//	public static void lock(String key)
//	{
//		Thread curr = Thread.currentThread();
//		if(null!=threadMap.get(key))
//		{
//			threadMap.get(key).notify();
//			threadMap.remove(key);
//		}
//		Thread tempThread = threadMap.putIfAbsent(key, curr);
//		if(null!=tempThread&&tempThread != curr)
//		{
//			threadMap.remove(key);
//			return;
//		}
//		synchronized (curr) {
//			try {
//				if(null!=threadMap.get(key))
//				{
//					curr.wait(timeout);
//				}
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
////				e.printStackTrace();
//				logger.error("thread wait error",e);
//			}
//			threadMap.remove(key);
//		}
//		
//	}
//	
//	public static void unlock(String key)
//	{
//		Thread lockThread = threadMap.get(key);
//		if(null!=lockThread)
//		{
//			synchronized (lockThread) {
//				threadMap.remove(key);
//				lockThread.notify();
//			}
//		}else{
//			//回执先回�?
//			Thread curr = Thread.currentThread();
//			Thread tempThread = threadMap.putIfAbsent(key, curr);
//			if(null!=tempThread&&tempThread != curr)
//			{
//				if(null!=tempThread)
//				{
//					synchronized (tempThread) {
//						threadMap.remove(key);
//						tempThread.notify();
//					}
//				}
//			}
//		}
//		
//	}
	
	private static final class TimeOutTask implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			logger.info("异步消息缓存队列清除超时对象任务�?�?");
			try {
				for(Map.Entry<String,AsyncBean<AsyncRequest,AsyncResponse>> entry:hashMap.entrySet())
				{
					String key = entry.getKey();
					AsyncBean<AsyncRequest,AsyncResponse> value = entry.getValue();
					if(null!=value)
					{
						long currentTime = System.currentTimeMillis();
						long bornTime = value.getBornTime();
						if(currentTime - bornTime > aliveTime)
						{
							logger.warn("异步消息缓存队列清除超时对象,key=["+key+"]");
							hashMap.remove(key);
						}
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("AsyncContextHelper destory timeouted AsyncBean task error",e);
			}
			logger.info("异步消息缓存队列清除超时对象任务结束");
		}
		
	}
	
}
