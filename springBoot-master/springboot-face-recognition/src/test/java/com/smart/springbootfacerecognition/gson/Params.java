package com.smart.springbootfacerecognition.gson;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Params {
    public static final Integer PARAMS_ID = 0;


    private Integer id;
    private Integer trdID;
    private String name;
    private Image image;

}
