package com.cn.async.demo;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.cn.async.bean.AsyncBean;
import com.cn.async.context.AsyncContextHelper;
import com.cn.async.dto.AsyncRequest;
import com.cn.async.dto.AsyncResponse;

public class AsyncDemo {
	private static final Logger logger = LoggerFactory
			.getLogger(AsyncDemo.class);
	private int responseTimeout = 20000;

	public void asyncRequestServie() {
		try {
			AsyncBean<AsyncRequest, AsyncResponse> msg = new AsyncBean<AsyncRequest, AsyncResponse>();
			String reqkey = "reqkey";
			MDC.put("MSGFLOW", reqkey);// 日志增加UUID输出
			AsyncContextHelper.set(reqkey, msg);

			// 如下则不�?要等�?
			msg.setResponse(null);
			// 设置等待
			AsyncResponse response = msg.getResponse(responseTimeout);
			// 获取返回信息
			AsyncContextHelper.get(reqkey);

			// 判断下是否有异常
			if (true) {
				if (response == null) {
					// 超时以后的处�?
				} else {
					// 交互成功
					String res = new String(response.getTextBody(), "UTF-8");
				}
			}
		} catch (Exception e) {
			logger.error("接收交易系统报文处理异常", e);
		}
		logger.info("全渠道网关接收到交易报文结束");
	}

	public void asyncResponseSercice() throws UnsupportedEncodingException {
		String resmsg = "响应数据";
		String reqkey = "响应数据里获取的reqkey";
		DemoAsyncResponse response = new DemoAsyncResponse();
		response.setTextBody(resmsg.getBytes("UTF-8"));
		AsyncBean<AsyncRequest, AsyncResponse> oriBean = AsyncContextHelper
				.get(reqkey);
		if (null != oriBean) {
			oriBean.setResponse(response);
		} else {
			logger.warn("异步回执消息无法与申请匹�?,可能已经超时,key=" + reqkey);
		}
	}
}
