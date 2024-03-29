package com.yunhui.config;

import com.yunhui.core.uid.PreAsyReloadAtomicUidImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

/**
 * Title: InitSystemConfig.java <br>
 * Description: <br>
 * Create DateTime: 2019年04月24日 14:53 <br>
 *
 * @author yun
 */
@Component
@Slf4j
@Order(value = 0)
public class InitSystemConfig implements CommandLineRunner {

    @Autowired
    AppProperties appProperties;

    @Autowired
    AbstractBaseConfig abstractBaseConfig;

    @Autowired
    PreAsyReloadAtomicUidImpl atomicUid;

    @Override
    public void run(String... args) throws Exception {
        String ip = InetAddress.getLocalHost().getHostAddress();
        int port = appProperties.getPort();
        abstractBaseConfig.setIp(ip);
        abstractBaseConfig.setPort(port);
        log.info("init abstractBaseConfig:" + abstractBaseConfig);

        //初始化AtomicUid
//        atomicUid.init(abstractBaseConfig);
//        log.info("init atomicUid:[{},{}]",atomicUid.getUidCache(),atomicUid.getMaxId());

    }


}
