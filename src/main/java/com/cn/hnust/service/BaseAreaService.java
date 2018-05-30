package com.cn.hnust.service;

import com.cn.hnust.pojo.BaseArea;
import com.cn.hnust.mapper.BaseAreaMapper;
import com.cn.hnust.mapper.BaseMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * 
 * BaseAreaService业务实现类
 * 
 **/
@Service
public class BaseAreaService extends BaseService<BaseArea> {
	@Resource
	private BaseAreaMapper baseAreaMapper;

	@Override
	protected BaseMapper<BaseArea> getEntityMapper() {
		return baseAreaMapper;
	}
}