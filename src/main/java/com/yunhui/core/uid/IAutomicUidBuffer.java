package com.yunhui.core.uid;

import com.yunhui.bean.Uid;
import com.yunhui.config.AbstractBaseConfig;
import com.yunhui.core.UidBuffer;
import com.yunhui.service.UidService;

/**
 * 
 * Created on 2023-10-26
 */
public interface IAutomicUidBuffer extends IAutomicUid {

    UidBuffer loadSyn();

    default UidBuffer getUidBuffer(UidService uidService, AbstractBaseConfig config, int buffer) {
        Uid uid = uidService.updateAndGetUid(config);
        Long id = uid.getId();
        UidBuffer uidBuffer_temp = new UidBuffer();
        uidBuffer_temp.setStart((id - 1) * buffer + 1);
        uidBuffer_temp.setEnd((id) * buffer);
        return uidBuffer_temp;
    }
}
