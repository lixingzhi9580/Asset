package QrBath;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cn.hnust.controller.QrBathController;
import com.cn.hnust.pojo.TPtsBinUseful;
import com.cn.hnust.service.TPtsBinUsefulService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class QrBathMyTest {
	private static final Logger logger = LoggerFactory.getLogger(QrBathMyTest.class);
	@Resource
	private QrBathController qrBathController;

	@Resource
	private TPtsBinUsefulService tPtsBinUsefulService;

	
	@Test
	public void saveMsg(){
//		for(int i=20171208;i<20171213;i++){
			String fileName="C:\\Users\\lixingzhi\\Desktop\\新建文件夹 (4)\\aaa1.txt";
			qrBathController.pressQeBath1(fileName);
//		}
		
		
	}
	
	@Test
	public void test(){
		List<TPtsBinUseful> ptsBinList = tPtsBinUsefulService.selectAll();
		String ptsBinLists=new Gson().toJson(ptsBinList);
		System.err.println(ptsBinLists);
		List<TPtsBinUseful> ptsBinListl=new Gson().fromJson(ptsBinLists,new TypeToken<List<TPtsBinUseful>>(){}.getType());
		
		System.out.println(ptsBinListl.get(0).getUuid());
		
	}
}