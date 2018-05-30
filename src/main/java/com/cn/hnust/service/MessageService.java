package com.cn.hnust.service;

import com.cn.hnust.pojo.Message;
import com.cn.hnust.mapper.MessageMapper;
import com.cn.hnust.mapper.BaseMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * 
 * MessageService业务实现类
 * 
 **/
@Service
public class MessageService extends BaseService<Message> {
	@Resource
	private MessageMapper messageMapper;

	@Override
	protected BaseMapper<Message> getEntityMapper() {
		return messageMapper;
	}
}