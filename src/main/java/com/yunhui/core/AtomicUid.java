package com.yunhui.core;

import com.yunhui.bean.Uid;
import com.yunhui.config.AbstractBaseConfig;
import com.yunhui.service.UidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class AtomicUid {

    @Autowired
    UidService uidService;

    @Autowired
    AbstractBaseConfig config;

    private AtomicLong uidCache=new AtomicLong();
    private long maxId;

    private static final int multiple=10;


    public AtomicUid (){}

    public void init(long id){
        uidCache.set(id*multiple);
        maxId=(id+1)*multiple;
    }

    public long get(){
        long id = uidCache.incrementAndGet();
        if(id<=maxId){
            return id;
        }
        //第一个线程从db取号
        Uid uid = uidService.updateAndGetUid(config);
        //更新缓存
        init(uid.getId());
        return get();
    }
}
