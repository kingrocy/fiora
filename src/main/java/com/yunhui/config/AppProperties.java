package com.yunhui.config;

import lombok.Getter;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Title: AppProperties.java <br>
 * Description: <br>
 * Copyright (c) 聚阿网络科技版权所有 2019 <br>
 * Create DateTime: 2019年04月24日 15:18 <br>
 *
 * @author yun
 */
@Component
@Getter
public class AppProperties implements ApplicationListener<WebServerInitializedEvent> {


    private int port;


    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        this.port=event.getWebServer().getPort();
    }
}
