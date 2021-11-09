package com.smart.springbootfacerecognition.module.business.subscribe;

import com.smart.springbootfacerecognition.module.business.subscribe.domain.entity.SubscribeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface SubscribeDao {
    int insert(SubscribeEntity subscribeEntity);
}
