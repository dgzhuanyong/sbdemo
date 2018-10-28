package com.py.sbdemo.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.py.sbdemo.entity.God;
import com.py.sbdemo.service.GodService;
import com.py.sbdemo.shiro.SaltUtil;
import com.py.sbdemo.special.Constants;

@Controller
@RequestMapping(value = "/god")
public class GodController extends BaseController {
	
	@Autowired
	private GodService godService;
	
	/**
	 * 跳转列表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toList")
	public String toList(HttpServletRequest request,Model model) {
		return "god/godList";
	}
	
	/**
	 * 获取列表数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "toListData")
	@ResponseBody
	public Map<String,Object> toListData(HttpServletRequest request) {
		//返回map
		Map<String,Object> resultMap = new HashMap<String, Object>();
		//条件map
		Map<String,Object> searchMap = new HashMap<String, Object>();
		//获取分页和排序条件
		LayerPage(request);
		//获取搜索条件
		String conditionName = request.getParameter("conditionName");
		searchMap.put("conditionName", conditionName);
		//排序插件
		PageHelper.orderBy(orderTable+" "+orderStyle);
		//分页插件
		Page<?> page = PageHelper.startPage(pageNum, pageSize);
		//调用service
		List<God> list = godService.selectConditionList(searchMap);
		//返回layui数据
		resultMap.put("code", 0);
		resultMap.put("msg", "查询成功");
		resultMap.put("count", page.getTotal());
		resultMap.put("data", list);
		return resultMap;
	}
	
	
	/***************************************************form*********************************************************/
	
	/**
	 * 跳转form
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toForm")
	public String getUserList(HttpServletRequest request,Model model) {
		int id = 0;
		try {
			id= Integer.parseInt(request.getParameter("id"));
		} catch (Exception e) {
			id = 0;
		}
		//查询所有角色
		/*List<Role> list = roleService.selectConditionList(null);
		model.addAttribute("list", list);
		if(id > 0) {
			God god = godService.selectByPrimaryKey(id);
			model.addAttribute("obj", god);
			GodRole godRole = godRoleService.selectByGodId(id);
			model.addAttribute("godRole", godRole);
		}*/
		return "jsp/god/godForm";
	}
	
	
	/**
	 * 新增
	 * @param request
	 * @param commodityClassification
	 * @return
	 */
	@RequestMapping(value="insert")
	@ResponseBody
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> insert(HttpServletRequest request,@ModelAttribute("obj") God god) {
		//返回map
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//当前登录GOD
		God logingod=getCurrentGod();
		//生成新的salt和password
		String salt = SaltUtil.getSalt();
		String password = SaltUtil.getPassWord(salt);
		god.setSalt(salt);
		god.setPassword(password);
		god.setStatus(false);
		godService.insert(god);
		//添加中间表
		/*int role = 0;
		try {
			role = Integer.parseInt(request.getParameter("role"));
		} catch (Exception e) {
			role = 0;
		}
		GodRole godRole = new GodRole();
		godRole.setGodId(god.getId());
		godRole.setRoleId(role);
		godRoleService.insert(godRole);*/
		resultMap.put("type", "add");
		resultMap.put("code", "success");
		return resultMap;
	}
	
	/**
	 * 修改
	 * @param request
	 * @param commodityClassification
	 * @return
	 */
	@RequestMapping(value="update")
	@ResponseBody
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> update(HttpServletRequest request,@ModelAttribute("obj") God god) {
		//返回map
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//当前登录GOD
		God logingod=getCurrentGod();
		//god.setUpdateUser(logingod.getId());
		//god.setUpdateTime(new Date());
		godService.update(god);
		//添加中间表
		/*godRoleService.deleteByGodId(god.getId());
		int role = 0;
		try {
			role = Integer.parseInt(request.getParameter("role"));
		} catch (Exception e) {
			role = 0;
		}
		GodRole godRole = new GodRole();
		godRole.setGodId(god.getId());
		godRole.setRoleId(role);
		godRoleService.insert(godRole);*/
		resultMap.put("type", "update");
		resultMap.put("code", "success");
		return resultMap;
	}
	
	
	
}
