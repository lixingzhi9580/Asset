package com.cn.hnust.service;

import com.cn.hnust.pojo.QrBath;
import com.cn.hnust.mapper.QrBathMapper;
import com.cn.hnust.mapper.BaseMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * 
 * QrBathService业务实现类
 * 
 **/
@Service
public class QrBathService extends BaseService<QrBath> {
	@Resource
	private QrBathMapper qrBathMapper;

	@Override
	protected BaseMapper<QrBath> getEntityMapper() {
		return qrBathMapper;
	}
}