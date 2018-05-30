package com.cn.hnust.utils.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cn.hnust.utils.TraderLogUtil;
import com.cn.hnust.utils.Utils;

/**
 * 标签:
 * 说明： 本站点博文除特殊说明转载自xxx外，均为博主原创内容
 * 转载请注明来源:KL博客-《java多线程并行处理List集合数据》
 * 本文链接地址:http://www.kailing.pub/article/index/arcid/126.html
 * @author lixingzhi[li_xz1@suixingpay.com]
 *
 */
public class MutilThreadTask {
	private static final Logger LOG = LoggerFactory.getLogger(MutilThreadTask.class);

	public static void main(String[] args) {
		List<Object> list = new ArrayList();
		for (int i = 0; i < 4; i++) {
			list.add(i + "test.png");
		}
		List<Object> list2 = new ArrayList();
		for (int i = 4; i < 8; i++) {
			list2.add(i + "test.png");
		}
		
		List<Future> futures = new ArrayList();
		TraderLogUtil.initUUID("1");
		for(int i = 0; i < 5; i++){
//			TraderLogUtil.initUUID();
//			long startTime = System.currentTimeMillis();
//			new MutilThreadTask().handleListMutiSchedule(list, 2);
//			LOG.info("handleListMutiSchedule调用时间：[{}]", System.currentTimeMillis() - startTime);
			
			long startTime1 = System.currentTimeMillis();
			TraderLogUtil.initUUID("2");
			List<Future> fs=new MutilThreadTask().handleListMutiThread(list, 2);
			TraderLogUtil.initUUID("3");
			List<Future> fs2=new MutilThreadTask().handleListMutiThread(list2, 2);
			futures.addAll(fs);
			futures.addAll(fs2);
			LOG.info("handleListMutiThread调用时间：[{}]", System.currentTimeMillis() - startTime1);
		}
		System.out.println(futures.size());
		try {
			for (Future future : futures) {
				Object listsf = future.get();
				LOG.info("listsf:[{}]", listsf);
			}
		}  catch (Exception e) {
			e.printStackTrace();
		}  finally {
			futures.clear();
		}
		LOG.info("futures:[{}]", futures);
	}

	// ScheduledExecutorService多线程并行处理list数据集
	public void handleListMutiSchedule(List list, int taskCount) {
		LOG.info("begin====================================");
		ScheduledExecutorService executorService = Executors.newScheduledThreadPool(taskCount);
		try {
			int start = 0;
			int listSize = list.size();
			int remainder = listSize % taskCount;
			int taskDataSize = listSize / taskCount;
			// 平均分配task任务
			for (int i = 0; i < taskCount; i++, start += taskDataSize) {
				int end = start + taskDataSize;
				// 最后如果有分配不均的，多余部分交给最后一个任务处理
				if (i == taskCount - 1) {
					if (remainder != 0) {
						end = listSize;
					}
				}
				executorService.schedule(new Task(list, start, end), 0, TimeUnit.SECONDS);
			}
		} finally {
			executorService.shutdown();
		}
		LOG.info("end====================================");
	}

	// ExecutorService多线程并行处理list数据集
	public List<Future> handleListMutiThread(List list, int taskCount) {
		LOG.info("begin====================================");
		int start = 0;
		ExecutorService ex = Executors.newFixedThreadPool(taskCount);
		try {
			int listSize = list.size();
			int remainder = listSize % taskCount;
			int taskDataSize = listSize / taskCount;
			List<Future> futures = new ArrayList();
			// 平均分配task任务
			for (int i = 0; i < taskCount; i++, start += taskDataSize) {
				int end = start + taskDataSize;
				// 最后如果有分配不均的，多余部分交给最后一个任务处理
				if (i == taskCount - 1) {
					if (remainder != 0) {
						end = listSize;
					}
				}
				long startTime1 = System.currentTimeMillis();
				Future future = ex.submit(new Task(list, start, end));
				futures.add(future);
				LOG.info("lxz调用时间：[{}]", System.currentTimeMillis() - startTime1);
			}
return futures;
			// 处理
//			for (Future future : futures) {
//				Object listsf = future.get();
//				LOG.info("listsf:[{}]", listsf);
//			}
//			LOG.info("futures:[{}]", futures);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ex.shutdown();
		}
		LOG.info("end====================================");
		return null;
	}

	/**
	 * Task任务执行单元
	 */
	private class Task implements Callable<List<Object>> {
		private List<Object> list;
		private int start;
		private int end;

		public Task(List<Object> list, int start, int end) {
			this.list = list;
			this.start = start;
			this.end = end;
		}

		@Override
		public List<Object> call() throws Exception {
			Object obj = null;
			List<Object> retList = new ArrayList();
			for (int i = start; i < end; i++) {
				obj = list.get(i);
				String uuid=Utils.getUUid();
				LOG.info(uuid+"==="+Thread.currentThread() + "当前处理：" + obj);
				Thread.sleep(1000);//
				// System.out.println(UfileUtil.uploadUFile(obj.toString(),new
				// FileInputStream("E:\\test.png")));
				MyPo t2 = new MyPo();
				t2.setA(uuid);
				t2.setB("" + obj);
				t2.setC(""+Thread.currentThread());
				retList.add(t2);
			}
			// 返回处理结果
			return retList;
		}
	}

	public class MyPo {
		public String a;
		public String b;
		public String c;
		public String getA() {
			return a;
		}

		public void setA(String a) {
			this.a = a;
		}

		public String getB() {
			return b;
		}

		public void setB(String b) {
			this.b = b;
		}

		public String getC() {
			return c;
		}

		public void setC(String c) {
			this.c = c;
		}

		@Override
		public String toString() {
			return "MyPo [a=" + a + ", b=" + b + ", c=" + c + "]";
		}

	}
}
