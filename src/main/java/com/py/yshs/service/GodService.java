package com.py.yshs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.py.yshs.entity.God;
import com.py.yshs.mapper.GodMapper;

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
