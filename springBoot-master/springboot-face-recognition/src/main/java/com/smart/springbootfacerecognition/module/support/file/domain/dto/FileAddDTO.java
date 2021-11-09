package com.smart.springbootfacerecognition.module.support.file.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;


/**
* @Description:    文件保存DTO
* @Author:         sbq
* @CreateDate:     2019/9/11 15:05
* @Version:        1.0
*/

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FileAddDTO {

    private Long fileId;


    /**
     * 用户CARDID
     */
    private String idcard;


    /**
     * 用户名
     */
    private String userName;



    /**
     * 文件大小 k
     */
    private String fileSize;

    private String fileType;

    @NotBlank(message = "文件名称不能为空")
    private String fileName;

    @NotBlank(message = "文件路径不能为空")
    private String filePath;
}
