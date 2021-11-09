package com.smart.springbootfacerecognition.module.business.persion.domain.entity;

import lombok.Data;

import java.util.Date;

@Data
public class PersionEntity {


    private Long persionId;


    /**
     * 用户名
     */
    private String userName;

    private String idcard;

    private String phone;

    private String q1;

    private String q2;



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
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}
