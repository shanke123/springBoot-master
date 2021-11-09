package com.smart.springbootfacerecognition.module.business.subscribe.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscribeEntity {

    private Long subscribeId;

    private Long id;

    private String method;

    private Long status;

    private String data;


    /**
     * 创建时间
     */
    private Date createTime;
}
