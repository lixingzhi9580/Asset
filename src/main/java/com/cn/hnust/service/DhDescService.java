package com.cn.hnust.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cn.hnust.mapper.BaseMapper;
import com.cn.hnust.mapper.DhDescMapper;
import com.cn.hnust.pojo.DhDesc;

/**
 * 
 * DhDescService业务实现类
 * 
 **/
@Service
public class DhDescService extends BaseService<DhDesc> {
	@Resource
	private DhDescMapper dhDescMapper;

	@Override
	protected BaseMapper<DhDesc> getEntityMapper() {
		return dhDescMapper;
	}

	public int delectBySn(String sn) {
		return dhDescMapper.delectBySn(sn);
	}
	
	public List<DhDesc> selectBySn(String gameOrdersn){
		return dhDescMapper.selectBySn(gameOrdersn);
	}
}