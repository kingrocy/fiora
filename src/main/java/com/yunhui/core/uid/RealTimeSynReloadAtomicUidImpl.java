package com.yunhui.core.uid;

import com.yunhui.config.AbstractBaseConfig;
import com.yunhui.core.UidBuffer;
import com.yunhui.service.UidService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * Created on 2023-10-26
 */
@Component
@Getter
@Slf4j
public class RealTimeSynReloadAtomicUidImpl implements IAutomicUidBuffer {


    private AtomicLong currentUid = new AtomicLong();

    private UidBuffer uidBuffer;

    private Lock lock = new ReentrantLock();

    private int buffer = 1000;

    @Autowired
    UidService uidService;

    @Autowired
    AbstractBaseConfig config;

    @Override
    public long getUid() {
        try {
            boolean tryLock = lock.tryLock(1, TimeUnit.SECONDS);
            if (tryLock) {
                try {
                    if (uidBuffer == null || currentUid.get() > uidBuffer.getEnd()) {
                        uidBuffer = loadSyn();
                        currentUid.set(uidBuffer.getStart());
                    }
                    return currentUid.getAndIncrement();
                } catch (Exception e) {
                    throw new RuntimeException("get uid error", e);
                } finally {
                    lock.unlock();
                }
            }
            throw new RuntimeException("get uid fail,tryLock:fail");
        } catch (InterruptedException e) {
            throw new RuntimeException("tryLock error", e);
        }
    }

    @Override
    public UidBuffer loadSyn() {
        return getUidBuffer(uidService, config, buffer);
    }
}
