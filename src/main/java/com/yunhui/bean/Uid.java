package com.yunhui.bean;

import lombok.Data;

import java.util.Date;

@Data
public class Uid {

    private Long id;

    private String ip;

    private Integer port;

    /**
	* 业务id
	*/
    private Integer bizId;

    private Date createTime;

    private Date updateTime;

}