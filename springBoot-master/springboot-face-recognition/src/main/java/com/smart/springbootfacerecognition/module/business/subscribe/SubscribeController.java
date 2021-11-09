package com.smart.springbootfacerecognition.module.business.subscribe;

import com.smart.springbootfacerecognition.exception.FebsException;
import com.smart.springbootfacerecognition.exception.FebsResponse;
import com.smart.springbootfacerecognition.module.business.subscribe.domain.entity.SubscribeEntity;
import com.smart.springbootfacerecognition.module.support.file.domain.dto.FileAddDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
public class SubscribeController {

    @Autowired
    private SubscribeService subscribeService;

    @PostMapping("/api/subscribe/save")
    public FebsResponse saveSubscribe(@RequestBody SubscribeEntity subscribeEntity) throws FebsException {
        return subscribeService.saveSubscribe(subscribeEntity);
    }
}
