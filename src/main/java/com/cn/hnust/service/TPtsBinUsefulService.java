package com.cn.hnust.service;

import com.cn.hnust.pojo.TPtsBinUseful;
import com.cn.hnust.mapper.TPtsBinUsefulMapper;
import com.cn.hnust.mapper.BaseMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * 
 * TPtsBinUsefulService业务实现类
 * 
 **/
@Service
public class TPtsBinUsefulService extends BaseService<TPtsBinUseful> {
	@Resource
	private TPtsBinUsefulMapper tPtsBinUsefulMapper;

	@Override
	protected BaseMapper<TPtsBinUseful> getEntityMapper() {
		return tPtsBinUsefulMapper;
	}
}