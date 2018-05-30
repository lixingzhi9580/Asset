//package com.cn.quartz.test;
//
//import java.util.Date;
//
//import org.quartz.CronScheduleBuilder;
//import org.quartz.CronTrigger;
//import org.quartz.JobBuilder;
//import org.quartz.JobDetail;
//import org.quartz.JobKey;
//import org.quartz.Scheduler;
//import org.quartz.SchedulerException;
//import org.quartz.SchedulerFactory;
//import org.quartz.SimpleScheduleBuilder;
//import org.quartz.SimpleTrigger;
//import org.quartz.TriggerBuilder;
//
//import com.cn.quartz.job.CronJob;
//import com.cn.quartz.job.SimpleJob;
//
//public class QuartzTest {
//
//	public static void main(String[] args) {
//		SchedulerFactory factory = new org.quartz.impl.StdSchedulerFactory();
//		try {
//			// myjob group1
//			// myjob001 group001
//			QuartzTest test = new QuartzTest();
//			Scheduler scheduler = factory.getScheduler();
//			test.createSimpleJob(scheduler, new JobKey("myjob001", "group1"));
//			test.createCronJob(scheduler, new JobKey("myjob002", "group1"));
//			// scheduler.deleteJob(new JobKey("myjob001", "group1"));//
//			// 通过jobkey删除以创建的job
//
//		} catch (SchedulerException e) {
//			e.printStackTrace();
//		} finally {
//			System.exit(0);
//		}
//	}
//
//	/**
//	 * 创建普通job
//	 * 
//	 * @param scheduler
//	 * @param jobKey
//	 * @throws SchedulerException
//	 */
//	public void createSimpleJob(Scheduler scheduler, JobKey jobKey) throws SchedulerException {
//		// -----------Quartz2.0.2--------------//
//		// 如果不存在名为“myjob”，组名为“group1”的Job，则添加进去
//
//		JobDetail jobDetail = scheduler.getJobDetail(jobKey);
//		if (jobDetail == null) {
//			Class clazz = SimpleJob.class;// 指定定时执行的job
//			JobDetail myJob = JobBuilder.newJob(clazz).withIdentity("myjob001", "group1").build();
//			SimpleTrigger trigger = TriggerBuilder
//					.newTrigger()
//					.withIdentity("mytrigger", "trigger-group")
//					.startAt(new Date())
//					.withSchedule(
//							SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5)
//									.repeatForever()).build();
//			scheduler.scheduleJob(myJob, trigger);
//		}
//	}
//
//	/**
//	 * 通过表达式创建cronjob
//	 * 
//	 * @param scheduler
//	 * @param jobKey
//	 * @throws SchedulerException
//	 */
//	public void createCronJob(Scheduler scheduler, JobKey jobKey) throws SchedulerException {
//		JobDetail jobDetail = scheduler.getJobDetail(jobKey);
//		if (jobDetail == null) {
//			Class clazz = CronJob.class;// 指定定时执行的job
//			JobDetail myJob = JobBuilder.newJob(clazz).withIdentity("myjob002", "group1").build();
//			CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1")
//					.withSchedule(CronScheduleBuilder.cronSchedule("0/15 * * * * ?")).build();
//			scheduler.scheduleJob(myJob, trigger);
//		}
//
//	}
//}