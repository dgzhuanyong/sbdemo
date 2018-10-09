package com.py.yshs.mapper;

import com.py.yshs.entity.God;

public interface GodMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(God record);

    int insertSelective(God record);

    God selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(God record);

    int updateByPrimaryKey(God record);
    
    //查询账号
    God selectByLoginName(String loginName);
    
    
    
    
}