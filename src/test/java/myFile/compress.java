package myFile;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.session.Configuration;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cn.hnust.pojo.CompXmlTable;
import com.cn.hnust.service.CompXmlTableService;
import com.cn.hnust.utils.CommonsMethods;

import QrBath.QrBathMyTest;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.OrderByElement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.util.TablesNamesFinder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class compress {
	
	private static final Logger logger = LoggerFactory.getLogger(compress.class);

	
	@Autowired
	private CompXmlTableService compXmlTableService;
	
	@Test
	public void addArea(){
//		List<File> files=test("C:\\mbs_table");
		List<File> files=test("C:\\Users\\lixingzhi\\Desktop\\新建文件夹\\");
		
		 for(File f:files){
//			 logger.info(f.getAbsolutePath());
			 List<CompXmlTable> s=getTableList(f);
			 for(CompXmlTable c:s){
//				 logger.info(c.toString());
				 try{
					 compXmlTableService.insertSelective(c);
				 }catch (Exception e) {
					 logger.info("求处理"+c.toString());
				}
			 }
		 }
	}

	private static List<File> test(String fileDir) {
		List<File> fileList = new ArrayList<File>();
		File file = new File(fileDir);
		File[] files = file.listFiles();// 获取目录下的所有文件或文件夹
		if (files == null) {// 如果目录为空，直接退出
			return null;
		}
		// 遍历，目录下的所有文件
		for (File f : files) {
			if (f.isFile()) {
				fileList.add(f);
			} else if (f.isDirectory()) {
				fileList.addAll(test(f.getAbsolutePath()));
			}
		}
		return fileList;
	}

	private static List<CompXmlTable> getTableList(File f){
		List<CompXmlTable> tableLists = new ArrayList<>();
		SAXReader reader = new SAXReader();
		List<Element> carnames = new ArrayList<>();
		
		try {
			Document document = reader.read(f);
			Element node = document.getRootElement();
			carnames = node.elements("select");
		} catch (Exception e) {
			CompXmlTable po = new CompXmlTable();
			po.setFILE(f.getAbsolutePath());
			po.setFLG("4");
			tableLists.add(po);
			return tableLists;
		}
		for (Element cname : carnames) {
			CompXmlTable po = new CompXmlTable();
			po.setFILE(f.getAbsolutePath());
			
			try {
				String sql = cname.getStringValue().replaceAll("\r|\n", " ").toLowerCase();
				po.setSQLTXT(sql);
//				logger.info("=====开始=================");
//				sql = sql.replaceAll("select(.*?)from", "select 1 from");
//				sql = sql.replaceAll("#(.*?)}", "2");
				po.setSQLTXT1(sql);
				Select statement = (Select) CCJSqlParserUtil.parse(sql);
				TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
				List<String> tableList = tablesNamesFinder.getTableList(statement);
				if(null!=tableList){
					po.setTABLESLIST(tableList.toString());
					po.setNUM(tableList.size());
				}
				po.setFLG("1");
			} catch (Exception e) {
				try{
					String sql = cname.getStringValue().replaceAll("\r|\n", " ").toLowerCase();
					sql = sql.replaceAll("select(.*?)from", "select 1 from");
					sql = sql.replaceAll("where.*", "");
					po.setSQLTXT2(sql);
					Select statement = (Select) CCJSqlParserUtil.parse(sql);
					TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
					List<String> tableList = tablesNamesFinder.getTableList(statement);
					if(null!=tableList){
						po.setTABLESLIST(tableList.toString());
						po.setNUM(tableList.size());
					}
					po.setFLG("2");
				} catch (Exception e2) {
					try{
						List<Element> carnames1=new ArrayList<>();
						carnames1=cname.elements("where");
						if(null!=carnames1&&carnames1.size()>0){
							for(Element et:carnames1){
								cname.remove(et);
							}
							String sql = cname.getStringValue().replaceAll("\r|\n", " ").toLowerCase();
							sql = sql.replaceAll("select(.*?)from", "select 1 from");
							sql = sql.replaceAll("where.*", "");
							po.setSQLTXT2(sql);
							Select statement = (Select) CCJSqlParserUtil.parse(sql);
							TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
							List<String> tableList = tablesNamesFinder.getTableList(statement);
							if(null!=tableList){
								po.setTABLESLIST(tableList.toString());
								po.setNUM(tableList.size());
							}
							po.setFLG("3");
						}
					} catch (Exception e3) {
						po.setFLG("4");
					}
				}
			}
			tableLists.add(po);
//			logger.info(po.toString());
//			logger.info("=====结束=================");
		}
		
		return tableLists;
	}
}