package com.cn.hnust.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cn.hnust.pojo.Crdinfo;
import com.cn.hnust.pojo.QrBath;
import com.cn.hnust.service.MessageService;
import com.cn.hnust.service.QrBathService;
import com.cn.hnust.utils.CommonsMethods;
import com.cn.hnust.utils.DateUtils;

@Controller
@RequestMapping("hunst/qrBath")
public class QrBathController {
	private static final Logger logger = LoggerFactory.getLogger(QrBathController.class);
	@Resource
	private QrBathService qrBathService;
	
	@Resource
	private MessageService messageService;
	
	private final static String DATE_PATTERN="yyyy-MM-dd HH:mm:ss";
	
	public void pressQeBath(String fileName){
		List<String> msg=CommonsMethods.readMsg(fileName);
		List<QrBath> qrBathMap=this.parserMsg(msg);
		int i=qrBathMap.size();
		int y=100000;
		while(i>y){
			List<QrBath> qrBath=qrBathMap.subList(i-y, i);
			qrBathService.insertBatch(qrBath);
			i=i-y;
		}
		List<QrBath> qrBath2=qrBathMap.subList(0, i);
		qrBathService.insertBatch(qrBath2);
	}
	
	/**
	 * 解析
	 * @param list
	 */
	public List<QrBath> parserMsg(List<String> list){
		logger.info("parserMsgStart");
		List<QrBath> qrBathMap=new ArrayList<>();
		int i=0;
		Date d1 = null;
		Date d2 = null;
		for(String lin:list){
			try {
				String[] lins=lin.split("\\|");
				if(lins.length==1){
					if(i==0){
						d1=DateUtils.parseToDate(lin, DATE_PATTERN);
					}
					if(i==1){
						d2=DateUtils.parseToDate(lin, DATE_PATTERN);
						QrBath qrBath=new QrBath();
						qrBath.setTranDt(DateUtils.formatDate(d1, DATE_PATTERN));
						qrBath.setTranTm(DateUtils.formatDate(d2, DATE_PATTERN));
						Long diff = d2.getTime() - d1.getTime();
						long days = diff / (1000 * 60 * 60 * 24);  
						long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);  
						long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
						long s = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60)-minutes*(1000* 60))/(1000);
						qrBath.setChah(Long.toString(hours));
						qrBath.setCham(Long.toString(minutes));
						qrBath.setChas(Long.toString(s));
						
						qrBath.setCha(Long.toString(diff));
						qrBath.setRunDt(DateUtils.formatDate(DateUtils.parseToDate(lin, DATE_PATTERN),"yyyyMMdd"));
						qrBathMap.add(qrBath);
					}
				}
				if(lins.length==2){
					if(i==0){
						d1=DateUtils.parseToDate(lins[1], DATE_PATTERN);
					}
					if(i==1){
						d2=DateUtils.parseToDate(lins[1], DATE_PATTERN);
						QrBath qrBath=new QrBath();
						qrBath.setTranDt(DateUtils.formatDate(d1, DATE_PATTERN));
						qrBath.setTranTm(DateUtils.formatDate(d2, DATE_PATTERN));
						Long diff = d2.getTime() - d1.getTime();
						long days = diff / (1000 * 60 * 60 * 24);  
						long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);  
						long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
						long s = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60)-minutes*(1000* 60))/(1000);
						qrBath.setChah(Long.toString(hours));
						qrBath.setCham(Long.toString(minutes));
						qrBath.setChas(Long.toString(s));
						qrBath.setCha(Long.toString(diff));
						qrBath.setRunDt(DateUtils.formatDate(DateUtils.parseToDate(lins[1], DATE_PATTERN),"yyyyMMdd"));
						qrBathMap.add(qrBath);
					}
				}
			} catch (Exception e) {
				logger.info(lin);
				logger.error("",e);
			}
			if(i==0){
				i=1;
			}else{
				i=0;
			}
			
		}
		logger.info("parserMsgEnd");
		return qrBathMap;
	}
	
	
	public void pressQeBath1(String fileName){
		List<String> msg=CommonsMethods.readMsg(fileName);
		List<QrBath> qrBathMap=this.parserMsg1(msg);
		int i=qrBathMap.size();
		int y=100000;
		while(i>y){
			List<QrBath> qrBath=qrBathMap.subList(i-y, i);
			qrBathService.insertBatch(qrBath);
			i=i-y;
		}
		List<QrBath> qrBath2=qrBathMap.subList(0, i);
		qrBathService.insertBatch(qrBath2);
	}
	
	/**
	 * 解析
	 * @param list
	 */
	public List<QrBath> parserMsg1(List<String> list){
		logger.info("parserMsgStart");
		List<QrBath> qrBathList=new ArrayList<>();
		Map<String,QrBath> qrBathMap=new HashMap<>();
		for(String lin:list){
			try {
				//|2017-12-15 10:57:00.000|cae1f026c12b447f84c055d3d469a913|INFO -[--二维码定时查询--begin]-[c.s.t.b.t.b.q.ResultQueryTaskScheduler]-[cronScheduler_Worker-10]
				if(lin.indexOf("begin")>0){
					int i=lin.indexOf("2017");
					String dtm=lin.substring(i, i+23);
					int y=lin.indexOf("cronScheduler_Worker");
					String key=lin.substring(y);
					QrBath qrBath=new QrBath();
					Date strDtmDt=DateUtils.parseToDate(dtm, DATE_PATTERN);
					String strDtm=DateUtils.formatDate(strDtmDt, DATE_PATTERN);
					qrBath.setTranDt(strDtm);
					qrBathMap.put(key, qrBath);
					qrBath.setRunDt(DateUtils.formatDate(DateUtils.parseToDate(dtm, DATE_PATTERN),"yyyyMMdd"));
				}else{
					int i=lin.indexOf("2017");
					String dtm=lin.substring(i, i+23);
					int y=lin.indexOf("cronScheduler_Worker");
					String key=lin.substring(y);
					Date d2=DateUtils.parseToDate(dtm, DATE_PATTERN);
					String strDtm=DateUtils.formatDate(d2, DATE_PATTERN);
					QrBath qrBath=qrBathMap.get(key);
					qrBath.setTranTm(strDtm);
					qrBathMap.remove(key);
					Date d1=DateUtils.parseToDate(qrBath.getTranDt(), DATE_PATTERN);
					Long diff = d2.getTime() - d1.getTime();
					long days = diff / (1000 * 60 * 60 * 24);  
					long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);  
					long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
					long s = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60)-minutes*(1000* 60))/(1000);
					qrBath.setChah(Long.toString(hours));
					qrBath.setCham(Long.toString(minutes));
					qrBath.setChas(Long.toString(s));
					qrBath.setCha(Long.toString(diff));
					qrBath.setRunDt(DateUtils.formatDate(DateUtils.parseToDate(dtm, DATE_PATTERN),"yyyyMMdd"));
					qrBathList.add(qrBath);
				}
			} catch (Exception e) {
				logger.info(lin);
				logger.error("",e);
			}
		}
		logger.info("parserMsgEnd");
		return qrBathList;
	}
}