package com.smart.springbootfacerecognition.module.support.file.domain.entity;

import lombok.Data;

import java.util.Date;

@Data
public class FileEntity {


    private Long fileId;



    private String idcard;


    /**
     * 用户名
     */
    private String userName;

    /**
     * 文件名
     */
    private String fileName;

    private String fileType;

    /**
     * 文件大小 k
     */
    private String fileSize;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createTime;

}
