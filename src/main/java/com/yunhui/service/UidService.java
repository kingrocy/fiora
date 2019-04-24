package com.yunhui.service;

import com.yunhui.bean.Uid;
import com.yunhui.config.AbstractBaseConfig;

public interface UidService {


    /**
     * 更新号段 并获取 同一时刻最多有一个线程去更新
     * @param config
     * @return
     */
    Uid updateAndGetUid(AbstractBaseConfig config);



}
