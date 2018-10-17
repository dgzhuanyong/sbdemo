package com.py.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.py.entity.God;
import com.py.mapper.GodMapper;

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
