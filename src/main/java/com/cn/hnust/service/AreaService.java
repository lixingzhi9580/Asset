package com.cn.hnust.service;

import com.cn.hnust.pojo.Area;
import com.cn.hnust.mapper.AreaMapper;
import com.cn.hnust.mapper.BaseMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * 
 * AreaService业务实现类
 * 
 **/
@Service
public class AreaService extends BaseService<Area> {
	@Resource
	private AreaMapper areaMapper;

	@Override
	protected BaseMapper<Area> getEntityMapper() {
		return areaMapper;
	}
}