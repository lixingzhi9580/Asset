package com.cn.hnust.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cn.hnust.pojo.DhDesc;

/**
 * 
 * DhDescMapper数据库操作接口类
 * 
 **/
@Repository
public interface DhDescMapper extends BaseMapper<DhDesc> {
	
	public int delectBySn(String sn);
	public List<DhDesc> selectBySn(String gameOrdersn);
}