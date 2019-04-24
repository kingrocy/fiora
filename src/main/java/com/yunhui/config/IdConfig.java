package com.yunhui.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Title: IdConfig.java <br>
 * Description: <br>
 * Copyright (c) 聚阿网络科技版权所有 2019 <br>
 * Create DateTime: 2019年04月24日 15:24 <br>
 *
 * @author yun
 */
@Configuration
public class IdConfig {

    @Bean
    public AbstractBaseConfig abstractBaseConfig(){
        return new AbstractBaseConfig() {
            @Override
            public String setIp() {
                return null;
            }

            @Override
            public Integer setPort() {
                return null;
            }
        };
    }

}
