package com.cn.hnust.controller;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cn.hnust.constant.AreaEnum;
import com.cn.hnust.pojo.Area;
import com.cn.hnust.pojo.BaseArea;
import com.cn.hnust.service.AreaService;
import com.cn.hnust.service.BaseAreaService;

@Controller
@RequestMapping("hunst/area")
public class BaseAreaController {
	@Resource
	private AreaService areaService;
	@Resource
	private BaseAreaService baseAreaService;

	private static final String[] areaName = new String[] { "特别行政区", "自治区",
			"自治州", "特区", "地区", "新区","城区", "旧区", "直辖区", "直辖市", "直辖县", "省", "市", "区",
			"县","州" };

	/**
	 * 人工匹配记录
	 */
	public void addFist(String areaId, String baseAreaId) {
		Area area = areaService.selectByPrimaryKey(areaId);
		BaseArea baseArea = baseAreaService.selectByPrimaryKey(baseAreaId);
		BaseArea baseAreaQ=new BaseArea();
		updateBaseArea(baseAreaQ,baseArea, area, AreaEnum.Zero);
		baseAreaService.updateByPrimaryKeySelective(baseAreaQ);
	}

	/**
	 * 找同名的省市区
	 * 
	 * @param areaEnum
	 */
	public void addArea(AreaEnum areaEnum, String chackArea,boolean check) {
		// 获取所有的待匹配地市
		List<BaseArea> baseAreaList = baseAreaService.selectAll();
		List<Area> areaList = areaService.selectAll();

		// 转换成key，value的map,省下查询
		Map<String, BaseArea> baseAreaMap = baseAreaList.stream().collect(
				Collectors.toMap(BaseArea::getAREAID, Function.identity()));
		Map<String, Area> areaMap = areaList.stream().collect(
				Collectors.toMap(Area::getCode, Function.identity()));
		addArea(areaEnum, baseAreaList, areaList, baseAreaMap, areaMap,
				chackArea,check);
	}

	/**
	 * 转换到区
	 * 
	 * @param areaEnum
	 */
	public void updateToDist(AreaEnum areaEnum, String chackArea) {
		// 获取所有的待匹配地市
		List<BaseArea> baseAreaList = baseAreaService.selectAll();
		List<Area> areaList = areaService.selectAll();

		// 转换成key，value的map,省下查询
		Map<String, BaseArea> baseAreaMap = baseAreaList.stream().collect(
				Collectors.toMap(BaseArea::getAREAID, Function.identity()));
		Map<String, Area> areaMap = areaList.stream().collect(
				Collectors.toMap(Area::getCode, Function.identity()));
		updateToDist(areaEnum, baseAreaList, areaList, baseAreaMap, areaMap,
				chackArea);
	}

	/**
	 * 随机找同级下的同级
	 * 
	 * @param areaEnum
	 * @param baseAreaList
	 * @param areaList
	 * @param baseAreaMap
	 * @param areaMap
	 */
	private void updateToDist(AreaEnum areaEnum, List<BaseArea> baseAreaList,
			List<Area> areaList, Map<String, BaseArea> baseAreaMap,
			Map<String, Area> areaMap, String areaCheckNum) {
		for (BaseArea baseArea : baseAreaList) {
			if (null==baseArea.getOutSEARCHCODEDist()||baseArea.getOutSEARCHCODEDist().split("_").length==4) {
				continue;
			}
			String bp = baseArea.getOutSEARCHCODE();
			for (Area area : areaList) {
				if (AreaEnum.DIST.getCode().equals(area.getAreaTyp())
						&& areaEnum.getCode().equals(baseArea.getAREATYP())) {
					String p = area.getSearchCode();
					if (checkSearchCode(bp, p, areaCheckNum)) {
						updateBaseAreaDist(baseArea, area);
					}
				}
			}
		}
	}

	/**
	 * 找同名的省市区
	 * 
	 * @param areaEnum
	 * @param baseAreaList
	 * @param areaList
	 * @param baseAreaMap
	 * @param areaMap
	 */
	private void addArea(AreaEnum areaEnum, List<BaseArea> baseAreaList,
			List<Area> areaList, Map<String, BaseArea> baseAreaMap,
			Map<String, Area> areaMap, String chackArea, boolean chack) {
		for (BaseArea baseArea : baseAreaList) {
			BaseArea baseAreaP = baseAreaMap.get(baseArea.getPARENTID());
			if (null == baseAreaP) {
				continue;
			}
			String bp = baseAreaP.getOutSEARCHCODE();
			BaseArea baseAreaQ=new BaseArea();
			for (Area area : areaList) {
				if (areaEnum.getCode().equals(baseArea.getAREATYP())
						&& areaEnum.getCode().equals(area.getAreaTyp())) {
					Area areaP = areaMap.get(area.getParentId());
					if (null == areaP) {
						continue;
					}

					if (StringUtils.isNotBlank(area.getErrMsg())) {
						continue;
					}
					
					String p = areaP.getSearchCode();
					if (checkSearchCode(bp, p, chackArea)) {
						String baseDist = replaceAreaName(baseArea.getAREANM());
						String dist = replaceAreaName(area.getAreaNm());
						if(StringUtils.isBlank(dist)){
							continue;
						}
						//修改之前的名字大于修改后的名字，取大名字
						if(StringUtils.isNotBlank(baseAreaQ.getOutAREANM())){
							String distBq = replaceAreaName(baseAreaQ.getOutAREANM());
							if(StringUtils.isBlank(distBq)||distBq.length()>dist.length()){
								continue;
							}
						}
						if (chack
								&& (StringUtils
										.isBlank(baseArea.getOutAreaId()) && (baseDist
										.indexOf(dist) >= 0 || dist
										.indexOf(baseDist) >= 0))
								&& (StringUtils.isNotBlank(baseDist) && StringUtils
										.isNotBlank(dist))) {
							updateBaseArea(
									baseAreaQ,
									baseArea,
									area,
									AreaEnum.get(areaEnum.getCode() + chackArea));
						}
						if (!chack&&StringUtils.isBlank(baseArea.getOutAreaId())) {
							updateBaseArea(baseAreaQ,
									baseArea,
									area,
									AreaEnum.get(areaEnum.getCode()
											+ Integer.toString(Integer
													.parseInt(chackArea) - 1)));
						}
					}
				}
			}
			if(StringUtils.isNotBlank(baseAreaQ.getAREAID())){
				baseAreaService.updateByPrimaryKeySelective(baseAreaQ);
			}
		}
	}

	/**
	 * 对比地址路径
	 * 
	 * @param p1
	 * @param p2
	 * @param ii
	 * @return
	 */
	private static boolean checkSearchCode(String p1, String p2, String ii) {
		int i = Integer.parseInt(ii);
		if (StringUtils.isBlank(p1) || StringUtils.isBlank(p2)) {
			return false;
		}
		String[] p1s = p1.split("_");
		String[] p2s = p2.split("_");
		int y = p1s.length < p2s.length ? p1s.length : p2s.length;
		if (i > y) {
			return false;
		}
		for (int x = 0; x <= y; x++) {
			if (x == i) {
				return true;
			}
			if (p1s[x].equals(p2s[x])) {
				continue;
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * 替换特殊字符
	 * 
	 * @param area
	 * @return
	 */
	private String replaceAreaName(String area) {
		String rtn = area;
		for (String name : areaName) {
			if("州".equals(name)&&area.indexOf("州市")>=0){
				continue;
			}
			rtn = rtn.replaceAll(name, "");
		}
		return rtn;
	}

	private void updateBaseArea(BaseArea baseArea,BaseArea baseAreaB, Area area, AreaEnum areaEnum) {
		baseArea.setAREAID(baseAreaB.getAREAID());
		baseArea.setOutAreaId(area.getCode());
		baseArea.setOutAREANM(area.getAreaNm());
		baseArea.setOutParentId(area.getParentId());
		baseArea.setOutSEARCHCODE(area.getSearchCode());
		baseArea.setOutSEARCHName(area.getSearchName());
		baseArea.setOutSpFlg(areaEnum.getCode());
		baseArea.setOutSpMsg(areaEnum.getLabel());
		baseArea.setOutSEARCHCODEDist(area.getSearchCode());
		baseArea.setOutSEARCHNameDist(area.getSearchName());
	}

	private void updateBaseAreaDist(BaseArea baseArea, Area area) {
		baseArea.setOutSEARCHCODEDist(area.getSearchCode());
		baseArea.setOutSEARCHNameDist(area.getSearchName());
		baseAreaService.updateByPrimaryKeySelective(baseArea);
	}
}