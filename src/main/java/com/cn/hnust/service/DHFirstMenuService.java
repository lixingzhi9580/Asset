package com.cn.hnust.service;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cn.hnust.mapper.BaseMapper;
import com.cn.hnust.mapper.DHFirstMenuMapper;
import com.cn.hnust.pojo.DHFirstMenu;
import com.cn.hnust.pojo.DhDesc;
import com.cn.http.common.Utils;

/**
 * 
 * DHFirstMenuService业务实现类
 * 
 **/
@Service
public class DHFirstMenuService extends BaseService<DHFirstMenu> {
	private final static Logger logger = LoggerFactory.getLogger(DHFirstMenuService.class);
	
	@Resource
	private DHFirstMenuMapper dHFirstMenuMapper;
	
	@Resource
	private DhDescService dhDescService;  

	@Override
	protected BaseMapper<DHFirstMenu> getEntityMapper() {
		return dHFirstMenuMapper;
	}
	
	public List<DHFirstMenu> getDHFirstMenus(String kindid){
		return dHFirstMenuMapper.getDHFirstMenus(kindid);
	}
	
	public List<DHFirstMenu> getXlDHFirstMenus(){
		return dHFirstMenuMapper.getXlDHFirstMenus();
	}
	
	/**
	 * 添加 （匹配有值的字段）
	 **/
	public int replaceInsert(DHFirstMenu record) {
		String a=record.getOther_info();
		logger.debug(a);
		String b=Utils.convert(a);
		logger.debug(b);
		String c=b.replace("{\"desc\": \"","").replace("#cFEFF72", "").replace("#c4BF24B", "").replace("#cE43D31", "").replace("#Y", "");
		logger.debug(c);
		record.setOther_info(c);
		
		String[] e=c.split("#r");
		dhDescService.delectBySn(record.getGame_ordersn());
		for(String f:e){
			DhDesc dhDesc=new DhDesc();
			dhDesc.setGameOrdersn(record.getGame_ordersn());
			String[] g=f.split("\\+");
			for(int i=0;i<g.length;i++){
				switch (i) {
				case 0:
					dhDesc.setGameKey(g[i]);
					break;
				case 1:
					dhDesc.setGameValue(g[i].replace("%", ""));
					break;
				default:
					break;
				}
			}
			dhDescService.replaceInsert(dhDesc);
		}
		return getEntityMapper().replaceInsert(record);
	};
	
}