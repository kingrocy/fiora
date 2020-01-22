package com.yunhui.core;

import com.yunhui.bean.Uid;
import com.yunhui.config.AbstractBaseConfig;
import com.yunhui.service.UidService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
@Getter
@Slf4j
public class AtomicUid {

    @Autowired
    UidService uidService;

    @Autowired
    AbstractBaseConfig config;

    private AtomicLong uidCache = new AtomicLong();
    private volatile long maxId;

    private static final int multiple = 10;


    public AtomicUid() {
    }

    private void init(long id) {
        uidCache.set(id * multiple);
        maxId = (id + 1) * multiple;
    }

    public void init(AbstractBaseConfig config) {
        Uid uid = uidService.updateAndGetUid(config);
        //更新缓存
        init(uid.getId());
    }


    public long get() {
        for (; ; ) {
            long id = uidCache.incrementAndGet();
            if (id <= maxId) {
                return id;
            }
            //第一个线程从db取号
            synchronized (this) {
                if (uidCache.get() >= maxId) {
                    init(config);
                }
            }
        }
    }
}
