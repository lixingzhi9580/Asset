package com.cn.hnust.service;

import com.cn.hnust.pojo.MinshenArea;
import com.cn.hnust.mapper.MinshenAreaMapper;
import com.cn.hnust.mapper.BaseMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * 
 * MinshenAreaService业务实现类
 * 
 **/
@Service
public class MinshenAreaService extends BaseService<MinshenArea> {
	@Resource
	private MinshenAreaMapper minshenAreaMapper;

	@Override
	protected BaseMapper<MinshenArea> getEntityMapper() {
		return minshenAreaMapper;
	}
}