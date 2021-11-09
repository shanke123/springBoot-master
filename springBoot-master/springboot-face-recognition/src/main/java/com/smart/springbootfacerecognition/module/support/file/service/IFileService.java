package com.smart.springbootfacerecognition.module.support.file.service;


import com.smart.springbootfacerecognition.exception.FebsException;
import com.smart.springbootfacerecognition.module.support.file.domain.vo.UploadVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public interface IFileService {

    /**
     * 文件上传
     *
     * @param multipartFile
     * @param path
     * @return
     */
    UploadVO fileUpload(MultipartFile multipartFile, String path) throws FebsException, IOException;

    /**
     * 生成文件名字
     * 当前年月日时分秒 +32位 uuid + 文件格式后缀
     *
     * @param originalFileName
     * @return String
     */
    default String generateFileName(String originalFileName) {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddhhmms"));
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String fileType = originalFileName.substring(originalFileName.lastIndexOf("."));
        return time + uuid + fileType;
    }
}
