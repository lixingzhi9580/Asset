package com.cn.hnust.codegen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自动生成MyBatis的实体类、实体映射XML文件、Mapper
 *
 * @author li_xz
 * @date 2016-04-01
 * @version v1.0
 */
public class EntityUtil {

	// 如果为null，遍历所有表
	private String tableName = "COMP_XML_TABLE";
	// 如果为null，则驼峰命名
	private String beanName = null;
	// 是否开启驼峰命名转换
	private final boolean processField = true;

	private final static String _path = "d:\\work\\myAsset\\mycode\\";
	private final String _package = "com.cn.hnust.";
	private final String bean = "pojo";
	private final String mapper = "mapper";
	private final String xml = "mapping";
	private final String service = "service";

	private final String mapper_extends = "BaseMapper";
	private final String driverName = "com.mysql.jdbc.Driver";
	private final String service_extends = "BaseService";

	private final String user = "root";

	private final String password = "root";

	private final String url = "jdbc:mysql://127.0.0.1:3306/asset?characterEncoding=utf8";

	private final String type_char = "char";

	private final String type_date = "date";

	private final String type_timestamp = "timestamp";

	private final String type_int = "int";

	private final String type_bigint = "bigint";

	private final String type_text = "text";

	private final String type_bit = "bit";

	private final String type_decimal = "decimal";

	private final String type_blob = "blob";

	private final String bean_path = _path + bean;

	private final String mapper_path = _path + mapper;

	private final String xml_path = _path + xml;

	private final String service_path = _path + service;

	private final String bean_package = _package + bean;

	private final String mapper_package = _package + mapper;

	private final String service_package = _package + service;
	private String mapperName = null;
	private String serviceName = null;

	private Connection conn = null;

	private void init() throws ClassNotFoundException, SQLException {
		Class.forName(driverName);
		conn = DriverManager.getConnection(url, user, password);
	}

	/**
	 * 获取所有的表
	 *
	 * @return
	 * @throws SQLException
	 */
	private List<String> getTables() throws SQLException {
		List<String> tables = new ArrayList<String>();
		if (null != tableName) {
			tables.add(tableName);
			return tables;
		}
		PreparedStatement pstate = conn.prepareStatement("show tables");
		ResultSet results = pstate.executeQuery();
		while (results.next()) {
			String tableName = results.getString(1);
			// if ( tableName.toLowerCase().startsWith("yy_") ) {
			tables.add(tableName);
			// }
		}
		return tables;
	}

	private void processTable(String table) {
		if (null != beanName) {
			mapperName = beanName + "Mapper";
			serviceName = beanName + "Service";
			return;
		}
		StringBuffer sb = new StringBuffer(table.length());
		String tableNew = table.toLowerCase();
		String[] tables = tableNew.split("_");
		String temp = null;
		for (int i = 0; i < tables.length; i++) {
			temp = tables[i].trim();
			sb.append(temp.substring(0, 1).toUpperCase()).append(
					temp.substring(1));
		}
		beanName = sb.toString();
		mapperName = beanName + "Mapper";
		serviceName = beanName + "Service";
	}

	/**
	 * 获取列类型
	 * 
	 * @param type
	 * @return
	 */
	private String processType(String type) {
		if (type.indexOf(type_char) > -1) {
			return "String";
		} else if (type.indexOf(type_bigint) > -1) {
			return "Long";
		} else if (type.indexOf(type_int) > -1) {
			return "Integer";
		} else if (type.indexOf(type_date) > -1) {
			return "java.util.Date";
		} else if (type.indexOf(type_text) > -1) {
			return "String";
		} else if (type.indexOf(type_timestamp) > -1) {
			return "java.util.Date";
		} else if (type.indexOf(type_bit) > -1) {
			return "Boolean";
		} else if (type.indexOf(type_decimal) > -1) {
			return "java.math.BigDecimal";
		} else if (type.indexOf(type_blob) > -1) {
			return "byte[]";
		}
		return null;
	}

	/**
	 * 驼峰命名
	 * 
	 * @param field
	 * @return
	 */
	private String processField(String field) {
		if (!processField) {
			return field;
		}
		StringBuffer sb = new StringBuffer(field.length());
		// field = field.toLowerCase();
		String[] fields = field.split("_");
		String temp = null;
		sb.append(fields[0]);
		for (int i = 1; i < fields.length; i++) {
			temp = fields[i].trim();
			sb.append(temp.substring(0, 1).toUpperCase()).append(
					temp.substring(1));
		}
		return sb.toString();
	}

	/**
	 * 将实体类名首字母改为小写
	 *
	 * @param beanName
	 * @return
	 */
	private String processResultMapId(String beanName) {
		return beanName.substring(0, 1).toLowerCase() + beanName.substring(1);
	}

	/**
	 * 构建类上面的注释
	 *
	 * @param bw
	 * @param text
	 * @return
	 * @throws IOException
	 */
	private BufferedWriter buildClassComment(BufferedWriter bw, String text)
			throws IOException {
		bw.newLine();
		bw.newLine();
		bw.write("/**");
		bw.newLine();
		bw.write(" * ");
		bw.newLine();
		bw.write(" * " + text);
		bw.newLine();
		bw.write(" * ");
		bw.newLine();
		bw.write(" **/");
		return bw;
	}

	/**
	 * 构建方法上面的注释
	 *
	 * @param bw
	 * @param text
	 * @return
	 * @throws IOException
	 */
	// private BufferedWriter buildMethodComment(BufferedWriter bw, String text)
	// throws IOException {
	// bw.newLine();
	// bw.write("\t/**");
	// bw.newLine();
	// bw.write("\t * " + text);
	// bw.newLine();
	// bw.write("\t **/");
	// return bw;
	// }

	/**
	 * 生成实体类
	 *
	 * @param columns
	 * @param types
	 * @param comments
	 * @throws IOException
	 */
	private void buildEntityBean(List<String> columns, List<String> types,
			List<String> comments, String tableComment) throws IOException {
		File folder = new File(bean_path);
		if (!folder.exists()) {
			folder.mkdir();
		}

		File beanFile = new File(bean_path, beanName + ".java");
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(beanFile)));
		bw.write("package " + bean_package + ";");
		bw.newLine();
		// bw.write("import java.io.Serializable;");
		// bw.newLine();
		// bw.write("import lombok.Data;");
		// bw.write("import javax.persistence.Entity;");
		bw = buildClassComment(bw, tableComment);
		// bw.newLine();
		// bw.write("@SuppressWarnings(\"serial\")");
		bw.newLine();
		// bw.write("@Entity");
		// bw.write("@Data");
		// bw.newLine();
		bw.write("public class " + beanName + " {");
		bw.newLine();
		bw.newLine();
		int size = columns.size();
		for (int i = 0; i < size; i++) {
			bw.write("\t/**" + comments.get(i) + "**/");
			bw.newLine();
			bw.write("\tprivate " + processType(types.get(i)) + " "
					+ processField(columns.get(i)) + ";");
			bw.newLine();
			bw.newLine();
		}
		bw.newLine();
		// 生成get 和 set方法
		String tempField = null;
		String _tempField = null;
		String tempType = null;
		for (int i = 0; i < size; i++) {
			tempType = processType(types.get(i));
			_tempField = processField(columns.get(i));
			tempField = _tempField.substring(0, 1).toUpperCase()
					+ _tempField.substring(1);
			bw.newLine();
			bw.write("\tpublic void set" + tempField + "(" + tempType + " "
					+ _tempField + "){");
			bw.newLine();
			bw.write("\t\tthis." + _tempField + " = " + _tempField + ";");
			bw.newLine();
			bw.write("\t}");
			bw.newLine();
			bw.newLine();
			bw.write("\tpublic " + tempType + " get" + tempField + "(){");
			bw.newLine();
			bw.write("\t\treturn this." + _tempField + ";");
			bw.newLine();
			bw.write("\t}");
			bw.newLine();
		}
		bw.newLine();
		bw.write("}");
		bw.newLine();
		bw.flush();
		bw.close();
	}

	/**
	 * 构建Mapper文件
	 *
	 * @throws IOException
	 */
	private void buildMapper() throws IOException {
		File folder = new File(mapper_path);
		if (!folder.exists()) {
			folder.mkdirs();
		}

		File mapperFile = new File(mapper_path, mapperName + ".java");
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(mapperFile), "utf-8"));
		bw.write("package " + mapper_package + ";");
		bw.newLine();
		bw.newLine();
		bw.write("import " + bean_package + "." + beanName + ";");
		bw.newLine();
		bw.write("import org.springframework.stereotype.Repository;");
		bw = buildClassComment(bw, mapperName + "数据库操作接口类");
		bw.newLine();
		bw.write("@Repository");
		bw.newLine();
		bw.write("public interface " + mapperName + " extends "
				+ mapper_extends + "<" + beanName + "> {");
		bw.newLine();
		bw.newLine();
		bw.write("}");
		bw.flush();
		bw.close();
	}

	/**
	 * 构建Service文件
	 *
	 * @throws IOException
	 */
	private void buildService() throws IOException {
		File folder = new File(service_path);
		if (!folder.exists()) {
			folder.mkdirs();
		}

		File mapperFile = new File(service_path, serviceName + ".java");
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(mapperFile), "utf-8"));
		bw.write("package " + service_package + ";");
		bw.newLine();
		bw.newLine();
		bw.write("import " + bean_package + "." + beanName + ";");
		bw.newLine();
		bw.write("import " + mapper_package + "." + mapperName + ";");
		bw.newLine();
		bw.write("import " + mapper_package + "." + mapper_extends + ";");
		bw.newLine();
		bw.write("import org.springframework.stereotype.Service;");
		bw.newLine();
		bw.write("import javax.annotation.Resource;");
		bw = buildClassComment(bw, serviceName + "业务实现类");
		bw.newLine();
		bw.write("@Service");
		bw.newLine();
		bw.write("public class " + serviceName + " extends " + service_extends
				+ "<" + beanName + "> {");
		bw.newLine();
		bw.write("\t@Resource");
		bw.newLine();
		bw.write("\tprivate " + mapperName + " "
				+ processResultMapId(mapperName) + ";");
		bw.newLine();
		bw.newLine();
		bw.write("\t@Override");
		bw.newLine();
		bw.write("\tprotected " + mapper_extends + "<" + beanName
				+ "> getEntityMapper() {");
		bw.newLine();
		bw.write("\t\treturn " + processResultMapId(mapperName) + ";");
		bw.newLine();
		bw.write("\t}");
		bw.newLine();
		bw.write("}");
		bw.flush();
		bw.close();
	}

	/**
	 * 构建实体类映射XML文件
	 *
	 * @param columns
	 * @param types
	 * @param comments
	 * @throws IOException
	 */
	private void buildMapperXml(List<String> columns, List<String> types,
			List<String> comments) throws IOException {
		File folder = new File(xml_path);
		if (!folder.exists()) {
			folder.mkdirs();
		}

		File mapperXmlFile = new File(xml_path, mapperName + ".xml");
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(mapperXmlFile)));
		bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		bw.newLine();
		bw.write("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" ");
		bw.newLine();
		bw.write("    \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");
		bw.newLine();
		bw.write("<mapper namespace=\"" + mapper_package + "." + mapperName
				+ "\">");
		bw.newLine();
		bw.newLine();

		bw.write("\t<!--实体映射-->");
		bw.newLine();
		bw.write("\t<resultMap id=\"" + this.processResultMapId(beanName)
				+ "\" type=\"" + bean_package + "." + beanName + "\">");
		bw.newLine();
		bw.write("\t\t<!--" + comments.get(0) + "-->");
		bw.newLine();
		bw.write("\t\t<id property=\"" + this.processField(columns.get(0))
				+ "\" column=\"" + columns.get(0) + "\" />");
		bw.newLine();
		int size = columns.size();
		for (int i = 1; i < size; i++) {
			bw.write("\t\t<!--" + comments.get(i) + "-->");
			bw.newLine();
			bw.write("\t\t<result property=\""
					+ this.processField(columns.get(i)) + "\" column=\""
					+ columns.get(i) + "\" />");
			bw.newLine();
		}
		bw.write("\t</resultMap>");

		bw.newLine();
		bw.newLine();
		bw.newLine();

		// 下面开始写SqlMapper中的方法
		buildSQL(bw, columns, types);

		bw.write("</mapper>");
		bw.flush();
		bw.close();
	}

	private void buildSQL(BufferedWriter bw, List<String> columns,
			List<String> types) throws IOException {
		int size = columns.size();
		// 通用结果列
		bw.write("\t<!-- 通用查询结果列-->");
		bw.newLine();
		bw.write("\t<sql id=\"Base_Column_List\">");
		bw.newLine();

		bw.write("\t");
		for (int i = 0; i < size; i++) {
			bw.write("\t" + columns.get(i));
			if (i != size - 1) {
				bw.write(",");
			}
		}

		bw.newLine();
		bw.write("\t</sql>");
		bw.newLine();
		bw.newLine();

		// 通用查询条件
		bw.write("\t<!-- 通用查询条件-->");
		bw.newLine();
		bw.write("\t<sql id=\"dynamicWhere\">");
		bw.newLine();

		bw.write("\t\t<where>");
		bw.newLine();
		for (int i = 0; i < size; i++) {
			bw.write("\t\t\t<if test=\"");
			bw.write(this.processField(columns.get(i)));
			bw.write(" != null\"> AND " + columns.get(i));
			bw.write(" = #{"+this.processField(columns.get(i))+"} </if>");
			bw.newLine();
		}
		bw.write("\t\t</where>");
		bw.newLine();
		bw.write("\t</sql>");
		bw.newLine();
		bw.newLine();
		
		// 查询所有数据
		bw.write("\t<!-- 查询所有数据 -->");
		bw.newLine();
		bw.write("\t<select id=\"selectAll\" resultMap=\""
				+ processResultMapId(beanName)
				+ "\">");
		bw.newLine();
		bw.write("\t\t SELECT");
		bw.newLine();
		bw.write("\t\t <include refid=\"Base_Column_List\" />");
		bw.newLine();
		bw.write("\t\t FROM " + tableName);
		bw.newLine();
		bw.write("\t</select>");
		bw.newLine();
		bw.newLine();
		// 查询完
		
		// 查询（根据主键ID查询）
		bw.write("\t<!-- 查询（根据主键ID查询） -->");
		bw.newLine();
		bw.write("\t<select id=\"selectByPrimaryKey\" resultMap=\""
				+ processResultMapId(beanName)
				+ "\" parameterType=\"java.lang." + processType(types.get(0))
				+ "\">");
		bw.newLine();
		bw.write("\t\t SELECT");
		bw.newLine();
		bw.write("\t\t <include refid=\"Base_Column_List\" />");
		bw.newLine();
		bw.write("\t\t FROM " + tableName);
		bw.newLine();
		bw.write("\t\t WHERE " + columns.get(0) + " = #{"
				+ processField(columns.get(0)) + "}");
		bw.newLine();
		bw.write("\t</select>");
		bw.newLine();
		bw.newLine();
		// 查询完

		// 查询（根据主键ID查询）
		bw.write("\t<!-- 查询（根据实体查询） -->");
		bw.newLine();
		bw.write("\t<select id=\"selectBySelected\" resultMap=\""
				+ processResultMapId(beanName)
				+ "\">");
		bw.newLine();
		bw.write("\t\t SELECT");
		bw.newLine();
		bw.write("\t\t <include refid=\"Base_Column_List\" />");
		bw.newLine();
		bw.write("\t\t FROM " + tableName);
		bw.newLine();
		bw.write("\t\t <include refid=\"dynamicWhere\"/>");
		bw.newLine();
		bw.write("\t</select>");
		bw.newLine();
		bw.newLine();
		// 查询完
		
		
		// 删除（根据主键ID删除）
		bw.write("\t<!--删除：根据主键ID删除-->");
		bw.newLine();
		bw.write("\t<delete id=\"deleteByPrimaryKey\" parameterType=\"java.lang."
				+ processType(types.get(0)) + "\">");
		bw.newLine();
		bw.write("\t\t DELETE FROM " + tableName);
		bw.newLine();
		bw.write("\t\t WHERE " + columns.get(0) + " = #{"
				+ processField(columns.get(0)) + "}");
		bw.newLine();
		bw.write("\t</delete>");
		bw.newLine();
		bw.newLine();
		// 删除完

		// 添加insert方法
		bw.write("\t<!-- 添加 -->");
		bw.newLine();
		bw.write("\t<insert id=\"insert\" parameterType=\"" + bean_package
				+ "." + beanName + "\">");
		bw.newLine();
		bw.write("\t\t INSERT INTO " + tableName);
		bw.newLine();
		bw.write(" \t\t\t(");
		for (int i = 0; i < size; i++) {
			bw.write(columns.get(i));
			if (i != size - 1) {
				bw.write(",");
			}
		}
		bw.write(") ");
		bw.newLine();
		bw.write("\t\t VALUES ");
		bw.newLine();
		bw.write(" \t\t\t(");
		for (int i = 0; i < size; i++) {
			bw.write("#{" + processField(columns.get(i)) + "}");
			if (i != size - 1) {
				bw.write(",");
			}
		}
		bw.write(") ");
		bw.newLine();
		bw.write("\t</insert>");
		bw.newLine();
		bw.newLine();
		// 添加insert完

		// --------------- insert方法（匹配有值的字段）
		bw.write("\t<!-- 添加 （匹配有值的字段）-->");
		bw.newLine();
		bw.write("\t<insert id=\"insertSelective\" parameterType=\""
				+ bean_package + "." + beanName + "\">");
		bw.newLine();
		bw.write("\t\t INSERT INTO " + tableName);
		bw.newLine();
		bw.write("\t\t <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\" >");
		bw.newLine();

		String tempField = null;
		for (int i = 0; i < size; i++) {
			tempField = processField(columns.get(i));
			bw.write("\t\t\t<if test=\"" + tempField + " != null\">");
			bw.newLine();
			bw.write("\t\t\t\t " + columns.get(i) + ",");
			bw.newLine();
			bw.write("\t\t\t</if>");
			bw.newLine();
		}

		bw.newLine();
		bw.write("\t\t </trim>");
		bw.newLine();

		bw.write("\t\t <trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\" >");
		bw.newLine();

		tempField = null;
		for (int i = 0; i < size; i++) {
			tempField = processField(columns.get(i));
			bw.write("\t\t\t<if test=\"" + tempField + "!=null\">");
			bw.newLine();
			bw.write("\t\t\t\t #{" + tempField + "},");
			bw.newLine();
			bw.write("\t\t\t</if>");
			bw.newLine();
		}

		bw.write("\t\t </trim>");
		bw.newLine();
		bw.write("\t</insert>");
		bw.newLine();
		bw.newLine();
		// --------------- 完毕

		// --------------- replaceInsert方法（匹配有值的字段）
		bw.write("\t<!-- 新增或修改 （匹配有值的字段）-->");
		bw.newLine();
		bw.write("\t<insert id=\"replaceInsert\" parameterType=\""
				+ bean_package + "." + beanName + "\">");
		bw.newLine();
		bw.write("\t\t REPLACE INTO " + tableName);
		bw.newLine();
		bw.write("\t\t <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\" >");
		bw.newLine();

		tempField = null;
		for (int i = 0; i < size; i++) {
			tempField = processField(columns.get(i));
			bw.write("\t\t\t<if test=\"" + tempField + " != null\">");
			bw.newLine();
			bw.write("\t\t\t\t " + columns.get(i) + ",");
			bw.newLine();
			bw.write("\t\t\t</if>");
			bw.newLine();
		}

		bw.newLine();
		bw.write("\t\t </trim>");
		bw.newLine();

		bw.write("\t\t <trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\" >");
		bw.newLine();

		tempField = null;
		for (int i = 0; i < size; i++) {
			tempField = processField(columns.get(i));
			bw.write("\t\t\t<if test=\"" + tempField + "!=null\">");
			bw.newLine();
			bw.write("\t\t\t\t #{" + tempField + "},");
			bw.newLine();
			bw.write("\t\t\t</if>");
			bw.newLine();
		}

		bw.write("\t\t </trim>");
		bw.newLine();
		bw.write("\t</insert>");
		bw.newLine();
		bw.newLine();
		// --------------- 完毕

		// 修改update方法
		bw.write("\t<!-- 修 改-->");
		bw.newLine();
		bw.write("\t<update id=\"updateByPrimaryKeySelective\" parameterType=\""
				+ bean_package + "." + beanName + "\">");
		bw.newLine();
		bw.write("\t\t UPDATE " + tableName);
		bw.newLine();
		bw.write(" \t\t <set> ");
		bw.newLine();

		tempField = null;
		for (int i = 1; i < size; i++) {
			tempField = processField(columns.get(i));
			bw.write("\t\t\t<if test=\"" + tempField + " != null\">");
			bw.newLine();
			bw.write("\t\t\t\t " + columns.get(i) + " = #{" + tempField + "},");
			bw.newLine();
			bw.write("\t\t\t</if>");
			bw.newLine();
		}

		bw.newLine();
		bw.write(" \t\t </set>");
		bw.newLine();
		bw.write("\t\t WHERE " + columns.get(0) + " = #{"
				+ processField(columns.get(0)) + "}");
		bw.newLine();
		bw.write("\t</update>");
		bw.newLine();
		bw.newLine();
		// update方法完毕

		// ----- 修改（匹配有值的字段）
		bw.write("\t<!-- 修 改-->");
		bw.newLine();
		bw.write("\t<update id=\"updateByPrimaryKey\" parameterType=\""
				+ bean_package + "." + beanName + "\">");
		bw.newLine();
		bw.write("\t\t UPDATE " + tableName);
		bw.newLine();
		bw.write("\t\t SET ");

		bw.newLine();
		tempField = null;
		for (int i = 1; i < size; i++) {
			tempField = processField(columns.get(i));
			bw.write("\t\t\t " + columns.get(i) + " = #{" + tempField + "}");
			if (i != size - 1) {
				bw.write(",");
			}
			bw.newLine();
		}

		bw.write("\t\t WHERE " + columns.get(0) + " = #{"
				+ processField(columns.get(0)) + "}");
		bw.newLine();
		bw.write("\t</update>");
		bw.newLine();
		bw.newLine();
		
		
		// ----- 批量插入
		bw.write("\t<!-- 批量插入-->");
		bw.newLine();
		bw.write("\t<insert id=\"insertBatch\" parameterType=\"java.util.List\">");
		bw.newLine();
		bw.write("\t\t INSERT INTO " + tableName);
		bw.newLine();
		bw.write("\t\t\t(");
		for (int i = 0; i < size; i++) {
			bw.write(columns.get(i));
			if (i != size - 1) {
				bw.write(",");
			}
		}
		bw.write(") ");
		bw.newLine();
		bw.write("\t\t VALUES ");
		bw.newLine();
		bw.write("\t\t <foreach collection =\"list\" item=\"reddemCode\" index= \"index\" separator =\",\">");
		bw.newLine();
		bw.write("\t\t\t(");
		bw.newLine();
		for (int i = 0; i < size; i++) {
			bw.write("\t\t\t\t#{reddemCode." + processField(columns.get(i)) + "}");
			if (i != size - 1) {
				bw.write(",");
			}
			bw.newLine();
		}
		bw.write("\t\t\t)");
		bw.newLine();
		bw.write("\t\t </foreach>");
		bw.newLine();
		bw.write("\t</insert>");
		bw.newLine();
		bw.newLine();
		// 批量insert完
	}

	/**
	 * 获取所有的数据库表注释
	 *
	 * @return
	 * @throws SQLException
	 */
	private Map<String, String> getTableComment() throws SQLException {
		Map<String, String> maps = new HashMap<String, String>();
		PreparedStatement pstate = conn.prepareStatement("show table status");
		ResultSet results = pstate.executeQuery();
		while (results.next()) {
			String tableName = results.getString("NAME");
			String comment = results.getString("COMMENT");
			maps.put(tableName, comment);
		}
		return maps;
	}

	public void generate() throws ClassNotFoundException, SQLException,
			IOException {
		init();
		String prefix = "show full fields from ";
		List<String> columns = null;
		List<String> types = null;
		List<String> comments = null;
		PreparedStatement pstate = null;
		List<String> tables = getTables();
		Map<String, String> tableComments = getTableComment();
		for (String table : tables) {
			columns = new ArrayList<String>();
			types = new ArrayList<String>();
			comments = new ArrayList<String>();
			pstate = conn.prepareStatement(prefix + table);
			ResultSet results = pstate.executeQuery();
			while (results.next()) {
				columns.add(results.getString("FIELD"));
				types.add(results.getString("TYPE"));
				comments.add(results.getString("COMMENT"));
			}
			tableName = table;
			processTable(table);
			// this.outputBaseBean();
			String tableComment = tableComments.get(tableName);
			buildEntityBean(columns, types, comments, tableComment);
			buildMapper();
			buildMapperXml(columns, types, comments);
			buildService();
		}
		conn.close();
	}

	public static void main(String[] args) {
		try {
			new EntityUtil().generate();
			// 自动打开生成文件的目录
			Runtime.getRuntime().exec("cmd /c start explorer " + _path);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}