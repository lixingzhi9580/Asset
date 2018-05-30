package org.zsl.testmybatis;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cn.hnust.pojo.UserT;
import com.cn.hnust.service.UserTService;
import com.cn.hnust.service.UserTtService;
import com.google.gson.Gson;

@RunWith(SpringJUnit4ClassRunner.class)
// 表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class TestMyBatis {
	private final static Logger logger = LoggerFactory.getLogger(TestMyBatis.class);
	// private ApplicationContext ac = null;
	@Resource
	private UserTService userService;
	@Resource
	private UserTtService usertService;

	// @Before
	// public void before() {
	// ac = new ClassPathXmlApplicationContext("applicationContext.xml");
	// userService = (IUserService) ac.getBean("userService");
	// }

	@Test
	public void test1() {
		UserT user = userService.selectByPrimaryKey("1");
		user.setAge(123);
		userService.replaceInsert(user);
		
		UserT user1 = userService.selectByPrimaryKey("2");
		logger.info(new Gson().toJson(user1));
	}
}