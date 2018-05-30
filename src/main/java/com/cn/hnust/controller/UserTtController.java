package com.cn.hnust.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cn.hnust.pojo.UserTt;
import com.cn.hnust.service.UserTtService;
import com.cn.hnust.utils.Utils;

@Controller
@RequestMapping("hunst/user")
public class UserTtController {
	private final static Logger logger = LoggerFactory.getLogger(UserTtController.class);
	@Resource
	private UserTtService userTtService;

	/**
	 * 添加或更新商户资质信息。
	 * @param request
	 * @param userTt
	 * @return
	 */
	@RequestMapping("saveUser")
	public String save(HttpServletRequest request, UserTt userTt) {
		String msg = "";
		try {
			if (userTt.getUuid() == null || userTt.getUuid().length() == 0) {
				userTt.setUuid(Utils.getUUid());
				int ad = userTtService.insertSelective(userTt);
				msg = String.format("新增{}行成功!", ad);
			} else {
				int up = userTtService.updateByPrimaryKeySelective(userTt);
				msg = String.format("修改{}行成功!", up);
			}
			request.setAttribute("", Utils.Success);
		} catch (Exception e) {
			msg = String.format("修改或添加失败，原因为：{}!",e.getMessage());
			request.setAttribute("", Utils.Fail);
			logger.error("",e);
		}
		request.setAttribute("msg", msg);
		return "hunst/saveUser";
	}

//	/**
//	 * 删除商户资质信息
//	 * @param request
//	 * @param response
//	 * @throws Exception
//	 */
//	@RequestMapping("del")
//	public void del(HttpServletRequest request)
//	{
//		String preUrl= Utils.getPrePage(request);
//		String msg="";
//		try{
//			String[] lAryId =request.getParameterValues("uuid");
//			int larnum=lAryId.length;
//			if(larnum==1){
//				int de=userTtService.deleteByPrimaryKey(lAryId[0]);
//				msg = String.format("单条删除{}行成功!", de);
//			}else{
//				int des=userTtService.deleteByPrimaryKey(lAryId);
//				if(des==larnum)
//					msg = String.format("删除{}行成功!", des);
//				else
//					msg = String.format("要删除{}行，实际删除{}行!",larnum, des);
//			}
//			request.setAttribute("", Utils.Success);
//		}catch(Exception e){
//			msg = String.format("删除失败，原因为：{}!",e.getMessage());
//			request.setAttribute("", Utils.Fail);
//			logger.error("",e);
//		}
//		return request.;
//	}	
	@RequestMapping("/selectByPrimaryKey")
	public String selectByPrimaryKey(HttpServletRequest request) {
		String uuid = request.getParameter("uuid");
		UserTt ret = this.userTtService.selectByPrimaryKey(uuid);
		request.setAttribute("userTt", ret);
		return "hunst/showUser";
	}
}