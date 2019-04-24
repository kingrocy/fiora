package com.yunhui;

import com.yunhui.core.AtomicUid;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Title: AppTest.java <br>
 * Description: <br>
 * Copyright (c) 聚阿网络科技版权所有 2019 <br>
 * Create DateTime: 2019年04月24日 15:42 <br>
 *
 * @author yun
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes =Application.class )
public class AppTest {

    @Autowired
    AtomicUid atomicUid;

    @Test
    public void test(){

        for (int i = 0; i <50 ; i++) {
            System.out.println(atomicUid.get());
        }

    }
}
