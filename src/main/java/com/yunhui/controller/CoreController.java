package com.yunhui.controller;

import com.yunhui.core.AtomicUid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Title: CoreController.java <br>
 * Description: <br>
 * Copyright (c) 聚阿网络科技版权所有 2019 <br>
 * Create DateTime: 2019年04月25日 10:08 <br>
 *
 * @author yun
 */
@RestController
@RequestMapping("core")
@Slf4j
public class CoreController {

    @Autowired
    AtomicUid atomicUid;


    @GetMapping("uid")
    public Long uid(){
        long id = atomicUid.get();
        log.info("id:"+id);
        return id;
    }


}
