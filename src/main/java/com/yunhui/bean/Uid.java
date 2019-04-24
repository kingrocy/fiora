package com.yunhui.bean;

import java.util.Date;
import lombok.Data;

@Data
public class Uid {

    private Long id;

    private String ip;

    private Integer port;

    private Date updateTime;
}