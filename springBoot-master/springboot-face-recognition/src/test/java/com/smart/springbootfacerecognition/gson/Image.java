package com.smart.springbootfacerecognition.gson;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Image {

    public static final String TYPE = "URL";

    private String type;
    private String url;
    private String ext;
}
