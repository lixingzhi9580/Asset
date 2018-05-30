package com.cn.hnust.codegen;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 自动生成MyBatis的实体类、实体映射XML文件、Mapper
 *
 * @author li_xz
 * @date 2016-04-01
 * @version v1.0
 */
public class CopyOfCopyOfEntityUtils {

	private final String driverName = "com.mysql.jdbc.Driver";

	private final String user = "root";

	private final String password = "querya18f";

	private final String url = "jdbc:mysql://127.0.0.1:3306/asset?characterEncoding=utf8";

	private Connection conn = null;

	private void init() throws ClassNotFoundException, SQLException {
		Class.forName(driverName);
		conn = DriverManager.getConnection(url, user, password);
	}

	public void generate() throws ClassNotFoundException, SQLException,
			IOException {
		init();
		System.out.println("-----");
		String prefix2 = "select tran_dt,flg,num from hbewm2";
		PreparedStatement pstate2 = conn.prepareStatement(prefix2);
		ResultSet results2 = pstate2.executeQuery();

		Map<String, int[]> map = new HashMap<String, int[]>();
		while (results2.next()) {
			String tran_dt = results2.getString("tran_dt");
			String flg = results2.getString("flg");
			int num = results2.getInt("num");
			if (!map.containsKey(tran_dt)) {
				int[] a = new int[2];
				map.put(tran_dt, a);
			}

			int[] val = map.get(tran_dt);
			if ("S".equals(flg)) {
				val[0] += num;
			} else {
				val[1] += num;
			}
			

			map.put(tran_dt, val);

		}
		for (String key : map.keySet()) {
			double a = map.get(key)[1] / (map.get(key)[0] + map.get(key)[1]
					+ 0.0);
			System.out.println(key+"=="+a);
		}
		pstate2.close();
		results2.close();
		conn.close();
	}

	public static void main(String[] args) {
		try {
			new CopyOfCopyOfEntityUtils().generate();
			// 自动打开生成文件的目录
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}