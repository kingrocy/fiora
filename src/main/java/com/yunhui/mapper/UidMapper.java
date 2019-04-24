package com.yunhui.mapper;

import com.yunhui.bean.Uid;
import org.apache.ibatis.annotations.Param;


public interface UidMapper {

    int insertSelective(Uid record);

    Uid selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Uid record);

    int replaceInto(@Param("ip") String ip,@Param("port")Integer port);

    Uid selectUid(@Param("ip") String ip,@Param("port")Integer port);

}