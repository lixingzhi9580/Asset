//package myFile;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.OutputStreamWriter;
//import java.util.Iterator;
//import java.util.List;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import org.dom4j.Attribute;
//import org.dom4j.Document;
//import org.dom4j.DocumentHelper;
//import org.dom4j.Element;
//import org.dom4j.io.OutputFormat;
//import org.dom4j.io.SAXReader;
//import org.dom4j.io.XMLWriter;
//import org.junit.Test;
//
//import com.cn.hnust.utils.CommonsMethods;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.StringReader;
//import java.util.ArrayList;
//import java.util.List;
//import net.sf.jsqlparser.JSQLParserException;
//import net.sf.jsqlparser.expression.Expression;
//import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
//import net.sf.jsqlparser.parser.CCJSqlParserManager;
//import net.sf.jsqlparser.parser.CCJSqlParserUtil;
//import net.sf.jsqlparser.schema.Column;
//import net.sf.jsqlparser.schema.Table;
//import net.sf.jsqlparser.statement.Statement;
//import net.sf.jsqlparser.statement.insert.Insert;
//import net.sf.jsqlparser.statement.select.Join;
//import net.sf.jsqlparser.statement.select.OrderByElement;
//import net.sf.jsqlparser.statement.select.PlainSelect;
//import net.sf.jsqlparser.statement.select.Select;
//import net.sf.jsqlparser.statement.select.SelectItem;
//import net.sf.jsqlparser.statement.update.Update;
//import net.sf.jsqlparser.util.TablesNamesFinder;
//
///**
// * @Name: DOM4JTest
// * @Description: 使用DOM4J解析XML测试类
// * @Author: XXX
// * @CreateDate: XXX
// * @Version: V1.0
// */
//public class DOM4JTest {
//	@Test
//	public void readXMLDemo() throws Exception {
//		SAXReader reader = new SAXReader();
////		Document document = reader.read(new File("src//TmsTresourceMapper.xml"));
//		Document document = reader.read(new File("C:\\Users\\lixingzhi\\Desktop\\新建文件夹\\TmsTresourceMapper.xml"));
//		
//		Element node = document.getRootElement();
//		List<Element> carnames = node.elements("select");
//		for (Element cname : carnames) {
//			String sql = cname.getText().replaceAll("\r|\n", "").toLowerCase();
//			System.out.println(sql);
//			sql = sql.replaceAll("select(.*?)from", "select 1 from");
//			System.out.println(sql);
//			sql = sql.replaceAll("where.*", "");
//
//			System.out.println(sql);
//			System.out.println("1====================");
//			Statement statement = (Statement) CCJSqlParserUtil.parse(sql);
//			Select selectStatement = (Select) statement;
//			TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
//			List<String> tableList = tablesNamesFinder.getTableList(selectStatement);
//			CommonsMethods.showList(tableList);
//			System.out.println("2====================");
//		}
//	}
//
//	
//    private static void test(String fileDir) {  
//        List<File> fileList = new ArrayList<File>();  
//        File file = new File(fileDir);  
//        File[] files = file.listFiles();// 获取目录下的所有文件或文件夹  
//        if (files == null) {// 如果目录为空，直接退出  
//            return;  
//        }  
//        // 遍历，目录下的所有文件  
//        for (File f : files) {  
//            if (f.isFile()) {  
//                fileList.add(f);  
//            } else if (f.isDirectory()) {  
//                System.out.println(f.getAbsolutePath());  
//                test(f.getAbsolutePath());  
//            }  
//        }  
//        for (File f1 : fileList) {  
//            System.out.println(f1.getName());  
//        }  
//    } 
//	
//	
//	
//	
//	@Test
//	public void createXMLDemo() throws Exception {
//		Document document = DocumentHelper.createDocument();
//		Element root = document.addElement("cars");
//		Element supercarElement = root.addElement("supercars").addAttribute("company", "Ferrai");
//
//		supercarElement.addElement("carname").addAttribute("type", "Ferrari 101").addText("Ferrari 101");
//
//		supercarElement.addElement("carname").addAttribute("type", "sports").addText("Ferrari 202");
//		// 写入到一个新的文件中
//		writer(document);
//	}
//
//	/**
//	 * 把document对象写入新的文件
//	 * 
//	 * @param document
//	 * @throws Exception
//	 */
//	public void writer(Document document) throws Exception {
//		// 紧凑的格式
//		// OutputFormat format = OutputFormat.createCompactFormat();
//		// 排版缩进的格式
//		OutputFormat format = OutputFormat.createPrettyPrint();
//		// 设置编码
//		format.setEncoding("UTF-8");
//		// 创建XMLWriter对象,指定了写出文件及编码格式
//		// XMLWriter writer = new XMLWriter(new FileWriter(new
//		// File("src//a.xml")),format);
//		XMLWriter writer = new XMLWriter(
//				new OutputStreamWriter(new FileOutputStream(new File("src//car.xml")), "UTF-8"), format);
//		// 写入
//		writer.write(document);
//		// 立即写入
//		writer.flush();
//		// 关闭操作
//		writer.close();
//	}
//
//	/**
//	 * 遍历当前节点元素下面的所有(元素的)子节点
//	 * 
//	 * @param node
//	 */
//	public void listNodes(Element node) {
//		System.out.println("当前节点的名称：：" + node.getName());
//		// 获取当前节点的所有属性节点
//		List<Attribute> list = node.attributes();
//		// 遍历属性节点
//		for (Attribute attr : list) {
//			System.out.println(attr.getText() + "-----" + attr.getName() + "---" + attr.getValue());
//		}
//
//		if (!(node.getTextTrim().equals(""))) {
//			System.out.println("文本内容：：：：" + node.getText());
//		}
//
//		// 当前节点下面子节点迭代器
//		Iterator<Element> it = node.elementIterator();
//		// 遍历
//		while (it.hasNext()) {
//			// 获取某个子节点对象
//			Element e = it.next();
//			// 对子节点进行遍历
//			listNodes(e);
//		}
//	}
//
//	/**
//	 * 介绍Element中的element方法和elements方法的使用
//	 * 
//	 * @param node
//	 */
//	public void elementMethod(Element node) {
//		List<Element> carnames = node.elements("select");
//		for (Element cname : carnames) {
//			String aa = cname.getText();
//			System.out.println(aa);
//		}
//	}
//}