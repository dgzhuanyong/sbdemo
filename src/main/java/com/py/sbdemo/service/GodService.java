package com.py.sbdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.py.sbdemo.entity.God;
import com.py.sbdemo.mapper.GodMapper;

@Service
public class GodService {
	
	@Autowired
	private GodMapper godMapper;
	
	/**
	 * 查询账号
	 * @param loginName
	 * @return
	 */
	public God selectByLoginName(String loginName) {
		return godMapper.selectByLoginName(loginName);
	}
	
}
