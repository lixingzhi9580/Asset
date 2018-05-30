package com.cn.hnust.controller;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cn.hnust.constant.AreaEnum;
import com.cn.hnust.pojo.Area;
import com.cn.hnust.service.AreaService;

@Controller
@RequestMapping("hunst/area")
public class AreaController {
	@Resource
	private AreaService areaService;

	/**
	 * ID,PID,NAME, 生成SearchCode，searchName，AreaTyp，Prov，City，Dist
	 * 
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	public void addSName() {
		List<Area> areaList = areaService.selectAll();
		Map<String, Area> map = new HashMap<>();
		for (Area area : areaList) {
			String code = area.getCode();
			map.put(code, area);
		}

		for (Area area : areaList) {
			try {
				area.setAreaNm(area.getAreaNm().replace(" ", "").trim());
				Field f = Area.class.getDeclaredField("parentId");
				f.setAccessible(true);
				Field f1 = Area.class.getDeclaredField("code");
				f1.setAccessible(true);
				Field f2 = Area.class.getDeclaredField("areaNm");
				f2.setAccessible(true);

				String searchCode = getPCdeo(area, f, f1, map).replace(" ", "").trim();
				area.setSearchCode(searchCode);
				String searchName = getPCdeo(area, f, f2, map).replace(" ", "").trim();
				area.setSearchName(searchName);
				String[] searchNames = searchName.split("_");
				area.setProv(searchNames.length > 1 ? searchNames[1] : "");
				area.setCity(searchNames.length > 2 ? searchNames[2] : "");
				area.setDist(searchNames.length > 3 ? searchNames[3] : "");
				area.setAreaTyp(Integer.toString(searchNames.length));
			} catch (Exception e) {
				area.setErrMsg(e.getMessage());
			}
			areaService.updateByPrimaryKey(area);
		}
	}

	private <T> String getPCdeo(T t, Field f1, Field f2, Map<String, T> map) throws Exception {
		String p = (String) f1.get(t);
		String v = (String) f2.get(t);
		if (StringUtils.isNotBlank(p)) {
			String prtn = getPCdeo(map.get(p), f1, f2, map);
			return new StringBuilder(prtn).append("_").append(v).toString();
		} else {
			return new StringBuilder(v).toString();
		}
	}
	
	/**
	 * 层级检查
	 */
	public void checkCj() {
		List<Area> areaList = areaService.selectAll();
		for (Area area : areaList) {
			if (AreaEnum.PROV.getCode().equals(area.getAreaTyp())) {
				boolean b = false;
				for (Area area1 : areaList) {
					if (AreaEnum.CITY.getCode().equals(area1.getAreaTyp())
							&& area.getCode().equals(area1.getParentId())) {
						for (Area area2 : areaList) {
							if (AreaEnum.DIST.getCode().equals(
									area2.getAreaTyp())
									&& area1.getCode().equals(
											area2.getParentId())) {
								b = true;
								break;
							}
						}
					}
					if (b) {
						break;
					}
				}
				if (!b) {
					area.setErrMsg("单独省市,本省无完整层级");
					areaService.updateByPrimaryKeySelective(area);
				}
			}
			if (AreaEnum.PROV.getCode().equals(area.getAreaTyp())) {
				boolean b = false;
				for (Area area1 : areaList) {
					if (AreaEnum.CITY.getCode().equals(area1.getAreaTyp())
							&& area.getCode().equals(area1.getParentId())) {
						b = true;
						break;
					}
				}
				if (!b) {
					area.setErrMsg("单独省，无下级市");
					areaService.updateByPrimaryKeySelective(area);
				}
			}
			if (AreaEnum.CITY.getCode().equals(area.getAreaTyp())) {
				boolean b = false;
				for (Area area1 : areaList) {
					if (AreaEnum.DIST.getCode().equals(area1.getAreaTyp())
							&& area.getCode().equals(area1.getParentId())) {
						b = true;
						break;
					}
				}
				if (!b) {
					area.setErrMsg("单独市，无下级区");
					areaService.updateByPrimaryKeySelective(area);
				}
			}
		}

	}
}