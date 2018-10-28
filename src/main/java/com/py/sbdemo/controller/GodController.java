package com.py.sbdemo.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.py.sbdemo.entity.God;
import com.py.sbdemo.service.GodService;
import com.py.sbdemo.shiro.SaltUtil;
import com.py.sbdemo.utils.Utils;

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
		God logingod = getCurrentGod();
		//生成新的salt和password
		String salt = SaltUtil.getSalt();
		String password = SaltUtil.getPassWord(salt);
		god.setSalt(salt);
		god.setPassword(password);
		god.setStatus(false);
		god.setCreateUser(logingod.getId());
		god.setCreateTime(new Date());
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
		God logingod = getCurrentGod();
		god.setUpdateUser(logingod.getId());
		god.setUpdateTime(new Date());
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
	
	
	/***************************************************delete*********************************************************/
	
	/**
	 * 删除
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> delete(HttpServletRequest request) {
		//返回map
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (Exception e) {
			id = 0;
		}
		//判断用户是否存在
		God god = godService.selectByPrimaryKey(id);
		if(null == god) {
			resultMap.put("type", "delete");
			resultMap.put("code", "fail");
			return resultMap;
		}
		//有最后一次登录时间，说明账号已经使用，不能删除，只能禁用
		if(null != god.getLastLoginTime()) {
			resultMap.put("type", "delete");
			resultMap.put("code", "inuse");
			return resultMap;
		}
		godService.deleteByPrimaryKey(id);
		resultMap.put("type", "delete");
		resultMap.put("code", "success");
		return resultMap;
	}
	
	
	/**
	 * 批量删除
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "batchDelete")
	@ResponseBody
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> batchDelete(HttpServletRequest request,@RequestParam("ids[]") int[] ids) {
		//返回map
		Map<String, Object> resultMap = new HashMap<String, Object>();
		for (int id : ids) {
			//判断用户是否存在
			God god = godService.selectByPrimaryKey(id);
			if(null == god) {
				resultMap.put("type", "delete");
				resultMap.put("code", "fail");
				return resultMap;
			}
			//有最后一次登录时间，说明账号已经使用，不能删除，只能禁用
			if(null != god.getLastLoginTime()) {
				resultMap.put("type", "delete");
				resultMap.put("code", "inuse");
				return resultMap;
			}
			godService.deleteByPrimaryKey(id);
		}
		resultMap.put("type", "delete");
		resultMap.put("code", "success");
		return resultMap;
	}
	
	
	/***************************************************password*********************************************************/
	
	
	/**
	 * 批量重置密码
	 * @param request
	 * @param model
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "batchResetPassword")
	@ResponseBody
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> batchResetPassword(HttpServletRequest request,Model model,@RequestParam("ids[]") int[] ids) {
		//返回map
		Map<String, Object> resultMap = new HashMap<String, Object>();
		for (int id : ids) {
			//生成新的salt和password
			String salt = SaltUtil.getSalt();
			String password = SaltUtil.getPassWord(salt);
			God god = new God();
			god.setId(id);
			god.setSalt(salt);
			god.setPassword(password);
			godService.update(god);
		}
		resultMap.put("type", "reset");
		resultMap.put("code", "success");
		return resultMap;
	}
	
	/***************************************************status*********************************************************/
	
	/**
	 * 修改状态
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "updateStatus")
	@ResponseBody
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> updateStatus(HttpServletRequest request,Model model) {
		//返回map
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (Exception e) {
			id = 0;
		}
		//当String的值为"true"时返回ture，当为其他字符串时返回false
		boolean status = false;
		try {
			status = Boolean.parseBoolean(request.getParameter("status"));
		} catch (Exception e) {
			status = false;
		}
		
		God god = new God();
		god.setId(id);
		god.setStatus(status);
		godService.update(god);
		resultMap.put("type", "reset");
		resultMap.put("code", "success");
		return resultMap;
	}
	
	
	/***************************************************password*********************************************************/
	
	/**
	 * 跳转修改密码
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toUpdatePassWord")
	public String toUpdatePassWord(HttpServletRequest request,Model model) {
		//当前登录GOD
		God logingod = getCurrentGod();
		model.addAttribute("obj", logingod);
		return "jsp/god/updatePassWord";
	}
	
	
	/**
	 * 修改密码
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "updatePassWord")
	@ResponseBody
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> updatePassWord(HttpServletRequest request,Model model) {
		//当前登录GOD
		God logingod = getCurrentGod();
		//返回map
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String oldpass = request.getParameter("oldpass");		
		String newpass = request.getParameter("newpass");		
		String repeatpass = request.getParameter("repeatpass");		
		if(Utils.isNull(new String[] {oldpass,newpass,repeatpass})) {
			resultMap.put("type", "password");
			resultMap.put("code", "parameter");
			return resultMap;
		}
		//验证旧密码是否正确
		String password = SaltUtil.getPassWord(logingod.getSalt(), oldpass);
		if(!StringUtils.equals(password, logingod.getPassword())) {
			//旧密码不正确
			resultMap.put("type", "password");
			resultMap.put("code", "incorrect");
			return resultMap;
		}
		//验证新密码与旧密码是否一致
		password = SaltUtil.getPassWord(logingod.getSalt(), newpass);
		if(StringUtils.equals(password, logingod.getPassword())) {
			//新密码与旧密码不能相同
			resultMap.put("type", "password");
			resultMap.put("code", "agreement");
			return resultMap;
		}
		//验证新密码和重复密码是否一致
		if(!StringUtils.equals(newpass, repeatpass)) {
			//新密码和重复密码不一致
			resultMap.put("type", "password");
			resultMap.put("code", "atypism");
			return resultMap;
		}
		//修改密码
		logingod.setPassword(password);
		godService.update(logingod);
		resultMap.put("type", "password");
		resultMap.put("code", "success");
		return resultMap;
	}
	
	/***************************************************check*********************************************************/
	
	/**
	 *  检测重复
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "checkRepeat")
	@ResponseBody
	public boolean checkRepeat(HttpServletRequest request){
		Map<String, Object> searchMap = new HashMap<String, Object>();
		String oldLoginName = request.getParameter("oldLoginName");		
		String newLoginName = request.getParameter("newLoginName");
		if(Utils.isNull(oldLoginName)) {
			searchMap.put("loginName", newLoginName);
			long count = godService.checkRepeat(searchMap);
			if(count == 0) {
				return true;
			}else {
				return false;
			}
		}else {
			
			if(StringUtils.equals(oldLoginName, newLoginName)) {
				return true;
			}else {
				searchMap.put("loginName", newLoginName);
				long count = godService.checkRepeat(searchMap);
				if(count == 0) {
					return true;
				}else {
					return false;
				}
			}
		}
	}
	
	
	
	
}
