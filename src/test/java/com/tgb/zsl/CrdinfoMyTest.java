package com.tgb.zsl;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cn.hnust.controller.CrdinfoController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class CrdinfoMyTest {
	private static final Logger logger = LoggerFactory.getLogger(CrdinfoMyTest.class);
	@Resource
	private CrdinfoController crdinfoController;

	@Test
	public void saveMsg(){
		for(int i=20170912;i<20170930;i++){
			String fileName="F:\\work\\131\\"+i+"\\"+"server.log";
			String tofileName="F:\\work\\444\\131\\"+i+"\\"+"server.txt";
			crdinfoController.pressCrd(fileName,tofileName);
		}
	}
}