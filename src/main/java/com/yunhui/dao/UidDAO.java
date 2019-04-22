package com.yunhui.dao;

import com.yunhui.bean.Uid;
import com.yunhui.mapper.UidMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UidDAO {

    @Autowired
    UidMapper mapper;

    public int addUid(Uid record){
        try {
            return mapper.insertSelective(record);
        } catch (Exception e) {
            log.error("addUid exception", e);
            throw new RuntimeException("addUid exception",e);
        }
    }

    public Uid getUid(Long id){
        try {
            return mapper.selectByPrimaryKey(id);
        } catch (Exception e) {
            log.error("getUid exception", e);
            throw new RuntimeException("getUid exception",e);
        }
    }

    public int updateUid(Uid record){
        try {
            return mapper.updateByPrimaryKeySelective(record);
        } catch (Exception e) {
            log.error("updateUid exception", e);
            throw new RuntimeException("updateUid exception",e);
        }
    }
}