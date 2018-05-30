package demoTest.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cn.hnust.constant.AreaEnum;
import com.cn.hnust.controller.AreaController;
import com.cn.hnust.controller.BaseAreaController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class AreaTestMyBatis {
	@Resource
	private BaseAreaController baseAreaController;
	@Resource
	private AreaController areaController;
	
	@Test
	public void addArea() {
		System.out.println(1);
//		ID,PID,NAME,拼接SearchCode，searchName，AreaTyp，Prov，City，Dist
//		areaController.addSName();
//		areaController.checkCj();
	}
	
	@Test
	public void addBaseArea(){
		//人工匹配，一般是国(国第一个参数可能会变，更具对应表的值更改，国第二个参数是1，不变)
		baseAreaController.addFist("0", "1");
		//同省省名字匹配
		baseAreaController.addArea(AreaEnum.PROV,  "1",true);
		//同市市名字匹配
		baseAreaController.addArea(AreaEnum.CITY,  "2",true);
		//同区区名字匹配
		baseAreaController.addArea(AreaEnum.DIST,  "3",true);
		//同省跨市区名字名字匹配
		baseAreaController.addArea(AreaEnum.DIST,  "2",true);
		//同国省名字匹配
		baseAreaController.addArea(AreaEnum.PROV,  "1",false);
		//同省市名字匹配
		baseAreaController.addArea(AreaEnum.CITY,  "2",false);
		//同市区名字匹配
		baseAreaController.addArea(AreaEnum.DIST,  "3",false);
		//同国跨省市名字名字匹配
		baseAreaController.addArea(AreaEnum.DIST,  "2",false);
		//省转区
		baseAreaController.updateToDist(AreaEnum.PROV, "2");
		//市转区
		baseAreaController.updateToDist(AreaEnum.CITY, "3");
	}
}