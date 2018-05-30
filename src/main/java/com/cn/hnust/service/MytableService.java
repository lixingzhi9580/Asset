package com.cn.hnust.service;

import com.cn.hnust.pojo.Mytable;
import com.cn.hnust.mapper.MytableMapper;
import com.cn.hnust.mapper.BaseMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * 
 * MytableService业务实现类
 * 
 **/
@Service
public class MytableService extends BaseService<Mytable> {
	@Resource
	private MytableMapper mytableMapper;

	@Override
	protected BaseMapper<Mytable> getEntityMapper() {
		return mytableMapper;
	}
}