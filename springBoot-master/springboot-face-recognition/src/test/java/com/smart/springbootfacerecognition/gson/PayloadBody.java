package com.smart.springbootfacerecognition.gson;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayloadBody {
    public static final Integer PAYLOAD_BODY_ID = 1;
    public static final String PAYLOAD_BODY_METHOD = "face.set";


    private Integer id;
    private String method;
    private Params params;
}
