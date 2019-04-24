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

    public Uid selectUid(String ip,Integer port){
        try {
            return mapper.selectUid(ip,port);
        } catch (Exception e) {
            log.error("selectUid exception", e);
            throw new RuntimeException("selectUid exception",e);
        }
    }


    public int replaceInto(String ip,Integer port){
        try {
            return mapper.replaceInto(ip,port);
        } catch (Exception e) {
            log.error("replaceInto exception", e);
            throw new RuntimeException("replaceInto exception",e);
        }
    }

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