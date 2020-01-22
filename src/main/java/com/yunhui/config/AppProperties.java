package com.yunhui.config;

import lombok.Getter;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AppProperties implements ApplicationListener<WebServerInitializedEvent> {


    private int port;


    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        this.port=event.getWebServer().getPort();
    }
}
