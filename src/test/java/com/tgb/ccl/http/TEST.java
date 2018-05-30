package com.tgb.ccl.http;


public class TEST {
	public static void main(String[] args){
		// 启动线程  
		long p1=8699899004000027L;
		for (int i = 0; i < 20; i++) {
//			long aa=p1++;
//			System.out.println( aa+"");
			Thread t = new Thread(new RunnableTest());  
			t.start(); 
//			Thread b = new Thread(new RunnableTest());  
//			b.start(); 
		}
	}
}
