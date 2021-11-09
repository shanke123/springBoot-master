package com.smart.springbootfacerecognition.module.business.subscribe.domain.dto;

import lombok.Data;

@Data
public class SubscribeDTO {
    private Long id;

    private String method;

    private Long status;

   // private String data;

    private SubscribeData data;
}
