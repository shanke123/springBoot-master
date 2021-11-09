package com.smart.springbootfacerecognition.module.business.subscribe;

import com.smart.springbootfacerecognition.exception.FebsException;
import com.smart.springbootfacerecognition.exception.FebsResponse;
import com.smart.springbootfacerecognition.module.business.subscribe.domain.entity.SubscribeEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class SubscribeService {

    private String message;

    @Autowired
    private SubscribeDao subscribeDao;

    public FebsResponse saveSubscribe(SubscribeEntity subscribeEntity) throws FebsException {
        try {

            subscribeEntity.setCreateTime(new Date());
            int insert = subscribeDao.insert(subscribeEntity);
            return new FebsResponse().code("200").message("新增sub成功").status("success");

        } catch (Exception e) {
            message = "上传失败";
            log.error(message, e);
            throw new FebsException(message);
        }

    }
}
