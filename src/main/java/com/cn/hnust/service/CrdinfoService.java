package com.cn.hnust.service;

import com.cn.hnust.pojo.Crdinfo;
import com.cn.hnust.mapper.CrdinfoMapper;
import com.cn.hnust.mapper.BaseMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * 
 * CrdinfoService业务实现类
 * 
 **/
@Service
public class CrdinfoService extends BaseService<Crdinfo> {
	@Resource
	private CrdinfoMapper crdinfoMapper;

	@Override
	protected BaseMapper<Crdinfo> getEntityMapper() {
		return crdinfoMapper;
	}
}