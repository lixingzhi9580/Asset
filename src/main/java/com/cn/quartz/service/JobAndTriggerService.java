//package com.cn.quartz.service;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.cn.quartz.bean.JobAndTrigger;
//import com.cn.quartz.mapper.JobAndTriggerMapper;
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
//
//
//@Service
//public class JobAndTriggerService {
//
//	@Autowired
//	private JobAndTriggerMapper jobAndTriggerMapper;
//	
//	public PageInfo<JobAndTrigger> getJobAndTriggerDetails(int pageNum, int pageSize) {
//		PageHelper.startPage(pageNum, pageSize);
//		List<JobAndTrigger> list = jobAndTriggerMapper.getJobAndTriggerDetails();
//		PageInfo<JobAndTrigger> page = new PageInfo<JobAndTrigger>(list);
//		return page;
//	}
//
//}