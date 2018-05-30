package compress;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cn.compress.CompressUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class compress {
	@Test
	public void addArea(){
		
	}
	
	public static void main(String[] args) throws IOException {
		File file = new File("F:\\work");
		if (file.isDirectory()) {
			file(file);
		} else {
			CompressUtil.unCompressArchiveGz(file.getPath());
		}
	}
	
	
	public static void file(File file) throws IOException{
		File[] listFiles = file.listFiles();
		for (File fi : listFiles) {
			if (fi.isDirectory()) {
				file(fi);
			} else {
				CompressUtil.unCompressArchiveGz(fi.getPath());
			}
		}
	}
	
	
}