package com.py.yshs.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController extends BaseController{

	@RequestMapping("/")
	public String index(){
		return "index";
	}
}
