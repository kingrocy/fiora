package com.yunhui.core.uid;

import com.yunhui.config.AbstractBaseConfig;
import com.yunhui.core.UidBuffer;
import com.yunhui.service.UidService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
@Getter
@Slf4j
public class PreAsyReloadAtomicUidImpl implements IAutomicUidBuffer {

    @Autowired
    UidService uidService;

    @Autowired
    AbstractBaseConfig config;

    private Lock lock = new ReentrantLock();

    private AtomicLong current = new AtomicLong(0);

    private volatile UidBuffer uidBuffer;

    private int buffer = 1000;

    private final double threshold = 0.6;

    //不使用原子引用会存在问题吗？貌似也没有 因为压根就没有多线程去更新的情况
    private volatile Future<UidBuffer> asyFuture;

    @Override
    public UidBuffer loadSyn() {
        return getUidBuffer(uidService, config, buffer);
    }

    private void loadAsy() {
        asyFuture = CompletableFuture.supplyAsync(() -> loadSyn(), Executors.newSingleThreadExecutor());
    }


    @Override
    public long getUid() {
        try {
            boolean tryLock = lock.tryLock(1, TimeUnit.SECONDS);
            if (tryLock) {
                try {
                    //先判断是否要初始化
                    if (uidBuffer == null) {
                        uidBuffer = loadSyn();
                        current.set(uidBuffer.getStart());
                    }
                    //在判断是否到达阈值 进行异步刷新
                    if (needReload()) {
                        loadAsy();
                    }
                    //判断一下是否要刷新号段
                    if (current.get() > uidBuffer.getEnd()) {
                        uidBuffer = getRefreshUidBuffer();
                        current.set(uidBuffer.getStart());
                    }
                    return current.getAndIncrement();
                } finally {
                    lock.unlock();
                }
            }
            throw new RuntimeException("get uid error");
        } catch (Exception e) {
            throw new RuntimeException("get uid exception", e);
        }
    }

    private UidBuffer getRefreshUidBuffer() throws ExecutionException, InterruptedException {
        if (asyFuture == null) {
            //理论上不存在
            return loadSyn();
        }
        UidBuffer buffer = asyFuture.get();
        asyFuture = null;
        return buffer;
    }

    private boolean needReload() {
        if (asyFuture != null) {
            return false;
        }
        return current.get() >= uidBuffer.getEnd() * threshold;
    }
}
