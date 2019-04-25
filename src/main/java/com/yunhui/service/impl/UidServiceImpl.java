package com.yunhui.service.impl;
import com.yunhui.bean.Uid;
import com.yunhui.config.AbstractBaseConfig;
import com.yunhui.dao.UidDAO;
import com.yunhui.service.UidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UidServiceImpl implements UidService {

    @Autowired
    UidDAO uidDAO;


    @Override
    public Uid updateAndGetUid(AbstractBaseConfig config) {
        uidDAO.replaceInto(config.getIp(),config.getPort());
        return uidDAO.selectUid(config.getIp(),config.getPort());
    }
}
