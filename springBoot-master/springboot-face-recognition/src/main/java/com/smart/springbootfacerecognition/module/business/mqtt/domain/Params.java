package com.smart.springbootfacerecognition.module.business.mqtt.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Params {
    public static final Integer PARAMS_ID = 0;


    private Integer id;
    private Integer trdID;
    private String name;
    private String idCardNo;
    private Image image;

}
