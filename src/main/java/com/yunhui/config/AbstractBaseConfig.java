package com.yunhui.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class AbstractBaseConfig {

    private String ip;
    private Integer port;

    /**
     * 设置ip
     * @return
     */
    public abstract String setIp();

    /**
     * 设置port
     * @return
     */
    public abstract Integer setPort();



}
