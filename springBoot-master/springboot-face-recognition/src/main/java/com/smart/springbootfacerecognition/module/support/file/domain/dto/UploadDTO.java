package com.smart.springbootfacerecognition.module.support.file.domain.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadDTO {

    @NotBlank(message = "文件路径不能为空")
    private MultipartFile file;

    @NotBlank(message = "上传类型不能为空")
    private Integer moduleType;

    @NotBlank(message = "身份证号不能为空")
    private String idcard;
}
