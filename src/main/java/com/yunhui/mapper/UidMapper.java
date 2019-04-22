package com.yunhui.mapper;

import com.yunhui.bean.Uid;

public interface UidMapper {

    int insertSelective(Uid record);

    Uid selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Uid record);
}