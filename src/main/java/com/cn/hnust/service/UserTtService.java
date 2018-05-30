package com.cn.hnust.service;

import com.cn.hnust.pojo.UserTt;
import com.cn.hnust.mapper.UserTtMapper;
import com.cn.hnust.mapper.BaseMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * 
 * UserTtService业务实现类
 * 
 **/
@Service
public class UserTtService extends BaseService<UserTt> {
	@Resource
	private UserTtMapper userTtMapper;

	@Override
	protected BaseMapper<UserTt> getEntityMapper() {
		return userTtMapper;
	}
}