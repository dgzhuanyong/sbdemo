package com.py.sbdemo.mapper;

import java.util.List;
import java.util.Map;

import com.py.sbdemo.entity.God;

public interface GodMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(God record);

    int insertSelective(God record);

    God selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(God record);

    int updateByPrimaryKey(God record);
    
    //查询账号
    God selectByLoginName(String loginName);
    
    //根据条件查询列表
    List<God> selectConditionList(Map<String, Object> searchMap);
    
    //检测是否重复
    long checkRepeat(Map<String, Object> searchMap);
    
}