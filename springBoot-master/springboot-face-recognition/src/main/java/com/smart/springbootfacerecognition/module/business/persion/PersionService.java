package com.smart.springbootfacerecognition.module.business.persion;

import com.smart.springbootfacerecognition.exception.FebsException;
import com.smart.springbootfacerecognition.exception.FebsResponse;
import com.smart.springbootfacerecognition.module.business.persion.domain.entity.PersionEntity;
import com.smart.springbootfacerecognition.module.business.subscribe.SubscribeDao;
import com.smart.springbootfacerecognition.module.business.subscribe.domain.entity.SubscribeEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class PersionService {


    private String message;

    @Autowired
    private PersionDao persionDao;

    public FebsResponse savePersion(PersionEntity persionEntity) throws FebsException {
        try {

            persionEntity.setCreateTime(new Date());
            persionEntity.setUpdateTime(new Date());
            int insert = persionDao.insert(persionEntity);
            return new FebsResponse().code("200").message("新增persion成功").status("success");

        } catch (Exception e) {
            message = "上传失败";
            log.error(message, e);
            throw new FebsException(message);
        }

    }

}
