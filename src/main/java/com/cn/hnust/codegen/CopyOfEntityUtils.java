package com.cn.hnust.codegen;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

/**
 * 自动生成MyBatis的实体类、实体映射XML文件、Mapper
 *
 * @author li_xz
 * @date 2016-04-01
 * @version v1.0
 */
public class CopyOfEntityUtils {
	private final static Logger logger = LoggerFactory
			.getLogger(CopyOfEntityUtils.class);
	private final String driverName = "com.mysql.jdbc.Driver";

	private final String user = "root";

	private final String password = "querya18f";

	private final String url = "jdbc:mysql://127.0.0.1:3306/asset?characterEncoding=utf8";

	private Connection conn = null;

	private final String[] kangTypes = new String[] { "抗封印", "抗混乱", "抗遗忘",
			"抗昏睡" };
	private final String[] guanzhuTypes = new String[] { "反震程度", "反震率", "躲闪" };
	private final String[] keTypes = new String[] { "强力克" };
	private final String[] fashuTypes = new String[] { "强三尸虫", "强遗忘", "强鬼火",
			"三尸虫狂暴" };

	private StringBuilder kangTypeSb = new StringBuilder();
	private StringBuilder guanzhuTypeSb = new StringBuilder();
	private StringBuilder keTypeSB = new StringBuilder();
	private StringBuilder fashuTypeSB = new StringBuilder();
	private StringBuilder sb = new StringBuilder();
	double typeNum = 0;

	private void init() throws ClassNotFoundException, SQLException {
		Class.forName(driverName);
		conn = DriverManager.getConnection(url, user, password);
	}

	public void generate() throws Exception {
		init();

		double hl = 108.2;
		double fy = 96.1;
		double hs = 55.1;
		double yw = 134.1;

		Map<String, Double> kangZongMap = new HashMap<String, Double>();
		kangZongMap.put(kangTypes[0], fy);
		kangZongMap.put(kangTypes[1], hl);
		kangZongMap.put(kangTypes[2], yw);
		kangZongMap.put(kangTypes[3], hs);
		

		String prefix2 = "select kindid,game_ordersn,price,equip_name from dh_first_menu ";
		PreparedStatement pstate2 = conn.prepareStatement(prefix2);
		ResultSet results2 = pstate2.executeQuery();
		while (results2.next()) {
			String game_ordersn = results2.getString("game_ordersn");
			String price = results2.getString("price");
			String equip_name = results2.getString("equip_name");
			String kindid = results2.getString("kindid");

			String prefix = "select game_key,game_value from dh_desc where game_ordersn='"
					+ game_ordersn + "'";
			PreparedStatement pstate = conn.prepareStatement(prefix);
			ResultSet results = pstate.executeQuery();
			typeNum = 0;
			kangTypeSb = new StringBuilder();
			guanzhuTypeSb = new StringBuilder();
			keTypeSB = new StringBuilder();
			fashuTypeSB = new StringBuilder();
			sb = new StringBuilder();
			while (results.next()) {
				String game_key = results.getString("game_key");
				String game_value = results.getString("game_value");
				daying(game_key, game_value);
			}

			if (typeNum >= 58) {
				logger.info(
						"typeNum:[{}],kindid:[{}],fashuTypeSB:[{}],keTypeSB:[{}],guanzhuTypeSb:[{}],game_ordersn:[{}],price:[{}],equip_name:[{}],kangTypeSb:[{}],sb1:[{}]",
						typeNum, kindid, fashuTypeSB, keTypeSB, guanzhuTypeSb,
						game_ordersn, price, equip_name, kangTypeSb, sb);
//				logger.info("typeNum:[{}],kindid:[{}],fashuTypeSB:[{}]",typeNum,kindid,kangTypeSb);
			}

			pstate.close();
			results.close();
		}
		pstate2.close();
		results2.close();
		conn.close();
	}

	/**
	 * 打印，累计一些信息
	 * 
	 * @param game_key
	 * @param game_value
	 */
	public void daying(String game_key, String game_value) {
		for (String kangType : kangTypes) {
			if (game_key.indexOf(kangType) != -1) {
				typeNum = typeNum + Double.parseDouble(game_value);
				kangTypeSb.append(game_key).append(":").append(game_value)
						.append(";");
				
			}
		}
		for (String guanzhuType : guanzhuTypes) {
			if (game_key.indexOf(guanzhuType) != -1) {
				// typeNum=typeNum+ Double.parseDouble(game_value);
				guanzhuTypeSb.append(game_key).append(":").append(game_value)
						.append(";");
			}
		}
		for (String keType : keTypes) {
			if (game_key.indexOf(keType) != -1) {
				// typeNum=typeNum+ Double.parseDouble(game_value);
				keTypeSB.append(game_key).append(":").append(game_value)
						.append(";");
			}
		}

		for (String fashuType : fashuTypes) {
			if (game_key.indexOf(fashuType) != -1) {
				// typeNum=typeNum+ Double.parseDouble(game_value);
				fashuTypeSB.append(game_key).append(":").append(game_value)
						.append(";");
			}
		}

		sb.append(game_key).append(":").append(game_value).append(";");
	}

	public static void main(String[] args) {
		try {
			new CopyOfEntityUtils().generate();
			// 自动打开生成文件的目录
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}