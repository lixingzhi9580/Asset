package com.cn.life.wangzherongyao;

import java.util.HashMap;
import java.util.Map;

public class WangZheRongYao {

	
	public static void main(String[] args) {
		Map<String, Integer> map = new HashMap<>();
		// i代表时间
		for (int i = 1; i < 60; i++) {
			// y代表冷却比例
			System.out.print("原始时间["+i+"]\t");
			for (int y = 1; y <= 40; y++) {
				map.put(i + "==" + y, i * (100 - y) / 100);
				// System.out.println(String.format("原始时间[%s],冷却[%s],最后时间[%s]",i
				// , y, i*(100-y)/100));
				if (y != 1) {
					if (map.get(i + "==" + y) < map.get(i + "==" + (y - 1))) {
						System.out.print(String.format(
								"[%s]→[%s]\t", y, i * (100 - y)
										/ 100));
					}
				}
			}
			System.out.println();
		}
	}
}
