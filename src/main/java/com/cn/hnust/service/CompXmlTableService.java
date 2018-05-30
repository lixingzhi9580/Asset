package com.cn.hnust.service;

import com.cn.hnust.pojo.CompXmlTable;
import com.cn.hnust.mapper.CompXmlTableMapper;
import com.cn.hnust.mapper.BaseMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * 
 * CompXmlTableService业务实现类
 * 
 **/
@Service
public class CompXmlTableService extends BaseService<CompXmlTable> {
	@Resource
	private CompXmlTableMapper compXmlTableMapper;

	@Override
	protected BaseMapper<CompXmlTable> getEntityMapper() {
		return compXmlTableMapper;
	}
}