package com.py.sbdemo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.py.sbdemo.entity.God;
import com.py.sbdemo.mapper.GodMapper;

@Service
public class GodService {
	
	@Autowired
	private GodMapper godMapper;
	
	public int insert(God record) {
		return godMapper.insertSelective(record);
	}
	
	public int update(God record) {
		return godMapper.updateByPrimaryKeySelective(record);
	}
	
	public God selectByPrimaryKey(Integer id) {
		return godMapper.selectByPrimaryKey(id);
	}
	
	
	/**
	 * 查询账号
	 * @param loginName
	 * @return
	 */
	public God selectByLoginName(String loginName) {
		return godMapper.selectByLoginName(loginName);
	}
	
	
	/**
	 * 根据条件查询列表
	 * @param searchMap
	 * @return
	 */
	public List<God> selectConditionList(Map<String, Object> searchMap){
		return godMapper.selectConditionList(searchMap);
	}
	
	/**
	 * 	检测重复
	 * @param searchMap
	 * @return
	 */
	public long checkRepeat(Map<String, Object> searchMap) {
		return godMapper.checkRepeat(searchMap);
	}
	
	
	
}
