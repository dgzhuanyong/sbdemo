package com.py.sbdemo.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.py.sbdemo.special.Msg;

@Controller
public class HomeController extends BaseController{

	@RequestMapping(value= {"/","index"})
	public String index(){
		return "index";
	}
	
	@RequestMapping(value= "login")
	public String login(){
		return "login";
	}
	
	
	@RequestMapping(value= "godlogin")
	@ResponseBody
	public Msg godlogin(HttpServletRequest request,Msg msg){
		String loginName = request.getParameter("loginName");
		String password = request.getParameter("password");
		UsernamePasswordToken token = new UsernamePasswordToken(loginName,password,false);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
		} catch (UnknownAccountException e) {
			msg.setCode("0");
        	msg.setMsg("账号不存在");
        	return msg;
		}catch (DisabledAccountException e) {
			msg.setCode("0");
        	msg.setMsg("用户已禁用");
        	return msg;
		}catch (IncorrectCredentialsException e) {
			e.printStackTrace();
			msg.setCode("0");
        	msg.setMsg("密码不正确");
        	return msg;
		}
        msg.setCode("1");
    	msg.setMsg("success");
		return msg;
	}
	
}