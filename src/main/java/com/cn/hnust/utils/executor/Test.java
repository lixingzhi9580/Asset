package com.cn.hnust.utils.executor;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Test {
	   public static int count=0;
	    public static void main(String[] args) {
	        //初始化两个线城池大小的任务调度服务
	        ScheduledExecutorService executorService= Executors.newScheduledThreadPool(2);
	        //任务一：0秒后开始执行，之后每秒执行一次
	        final ScheduledFuture test1= executorService.scheduleAtFixedRate(new Runnable() {
	            public void run() {
	                System.out.println("任务一执行第"+(count++)+"次   "+Thread.currentThread());
	            }
	        },0, 1,TimeUnit.SECONDS);

	        //任务二：6秒后开始执行，并返回执行结果
	       final   ScheduledFuture test2 = executorService.schedule(new Callable() {
	            public Object call()  {
	                System.out.println("任务二执行，传递执行结果给任务三  "+Thread.currentThread());
	                return "任务二已执行完，请知晓！";
	            }
	        },0,TimeUnit.SECONDS);

	        //任务三：8秒后执行，打印任务二的结果，终止任务一
	        executorService.schedule(new Runnable() {
	            public void run() {
	                try {
	                	//get方法会阻塞，需要等线程返回
	                    System.out.println(test2.get());
	                }catch (Exception e){
	                    e.printStackTrace();
	                }
	                System.out.println("任务三执行，任务一终止  "+Thread.currentThread());
	               test1.cancel(true);
	            }
	        },8,TimeUnit.SECONDS);

	        System.out.println("我是最先执行的吗？不一定，虽然我是主线程  "+Thread.currentThread());
	    }
}
