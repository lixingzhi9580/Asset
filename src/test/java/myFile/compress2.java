package myFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.xmltags.XMLScriptBuilder;
import org.apache.ibatis.session.Configuration;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.session.Configuration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import QrBath.QrBathMyTest;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
public class compress2 {
	private static final Logger LOG = LoggerFactory.getLogger(compress2.class);

	public static void main(String[] args) throws IOException {
		Configuration configuration = new Configuration();
		String url="file:///C:/Users/lixingzhi/Desktop/新建文件夹/1111.xml";
		InputStream inputStream = compress2.getUrlAsStream(url);///////////// 读取URL资源中的xml
        XMLMapperBuilder mapperParser = new XMLMapperBuilder(inputStream, configuration, url, configuration.getSqlFragments());
        mapperParser.parse();
	}

	public static InputStream getUrlAsStream(String urlString) throws IOException {
	    URL url = new URL(urlString); ////// 说明支持多种文件读取方式
	    URLConnection conn = url.openConnection();
	    return conn.getInputStream();
	  }
}