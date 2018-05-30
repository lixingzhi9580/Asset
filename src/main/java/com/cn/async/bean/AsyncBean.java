package com.cn.async.bean;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.cn.async.enums.ErrorType;



/**
 * 
 * @author Jacky Yu
 *
 * @param <AsyncRequest>
 * @param <AsyncResponse>
 */
public class AsyncBean<AsyncRequest,AsyncResponse>
	extends SyncBean<AsyncRequest,AsyncResponse> {
	//
	
	private static final long ASYNC_WAIT_TIMEOUT_DEFAULT = 5000L;
	
	//
	private transient final CountDownLatch countDown = new CountDownLatch(1);

	private long bornTime ;
	
	public long getBornTime() {
		return bornTime;
	}
	public void setBornTime(long bornTime) {
		this.bornTime = bornTime;
	}
	public AsyncResponse getResponse() {
		return this.getResponse(ASYNC_WAIT_TIMEOUT_DEFAULT);
	}
	public AsyncResponse getResponse(long timeout) {
		try {
			boolean flag = countDown.await(timeout, TimeUnit.MILLISECONDS);
			if(!flag)
			{
				countDown.countDown();
				//超时
				this.setErrorType(ErrorType.TIMEOUT_ERROR);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return super.getResponse();
	}
	public void setResponse(AsyncResponse response) {
		super.setResponse(response);
		countDown.countDown();
	}
}
