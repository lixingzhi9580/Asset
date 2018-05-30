package com.cn.hnust.service;

import com.cn.hnust.pojo.UserT;
import com.cn.hnust.mapper.UserTMapper;
import com.cn.hnust.mapper.BaseMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * 
 * UserTService业务实现类
 * 
 **/
@Service
public class UserTService extends BaseService<UserT> {
	@Resource
	private UserTMapper userTMapper;

	@Override
	protected BaseMapper<UserT> getEntityMapper() {
		return userTMapper;
	}
}