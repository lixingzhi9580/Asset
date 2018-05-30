package com.cn.hnust.mapper;

import java.util.List;

import com.cn.hnust.pojo.DHFirstMenu;

import org.springframework.stereotype.Repository;

/**
 * 
 * DHFirstMenuMapper数据库操作接口类
 * 
 **/
@Repository
public interface DHFirstMenuMapper extends BaseMapper<DHFirstMenu> {
	public List<DHFirstMenu> getDHFirstMenus(String kindid);
	public List<DHFirstMenu> getXlDHFirstMenus();
}