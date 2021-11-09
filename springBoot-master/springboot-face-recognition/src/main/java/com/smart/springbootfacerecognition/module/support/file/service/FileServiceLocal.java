package com.smart.springbootfacerecognition.module.support.file.service;

import com.smart.springbootfacerecognition.exception.FebsException;
import com.smart.springbootfacerecognition.module.support.file.constant.FileServiceNameConst;
import com.smart.springbootfacerecognition.module.support.file.domain.vo.UploadVO;
import com.smart.springbootfacerecognition.util.DateUtils;
import com.smart.springbootfacerecognition.util.FileUtil;
import com.smart.springbootfacerecognition.util.RotateImageUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Date;


@Slf4j
@Service(FileServiceNameConst.LOCAL)
public class FileServiceLocal implements IFileService{

    private String message;


    @Value("${webConstant.maxUploadSize}")
    private String maxFileSize;


    @Value("${file-upload-service.path}")
    private String fileParentPath;

    @Value("${webConstant.compressionSize}")
    private String compressionSize;

    private static final Long DEFAULT_SIZE = 10 * 1024 * 1024L;



    @Override
    public UploadVO fileUpload(MultipartFile multipartFile, String path) throws FebsException, IOException {
        if (null == multipartFile) {
            message = "上传文件不存在";
            throw new FebsException(message);
        }
        Long maxSize = DEFAULT_SIZE;
       /* if (StringUtils.isNotEmpty(maxFileSize)) {
            String maxSizeStr = maxFileSize.toLowerCase().replace("mb", "");
            maxSize = Integer.valueOf(maxSizeStr) * 1024 * 1024L;
        }*/

        if (multipartFile.getSize() > maxSize) {
            message = "上传文件超过%s，请重新上传";
            throw new FebsException(String.format(message, maxFileSize));
        }

        String fileType = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".")+1);
        log.info("文件类型 : {}",fileType);

        // 添加判断，上传的文件是否是图片
        if(!"jpg|png|gif|bmp|jpeg".contains(multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".")+1))){
            message = "上传文件类型错误";
            throw new FebsException(message);
        }


        String filePath = fileParentPath;

        if (StringUtils.isNotEmpty(path)) {
            filePath = filePath + path + "/" + DateUtils.format(new Date())  + "/";
            //filePath: /home/upload/backUser/config/
            //  filePath /home/upload/
            //  path /backUser/config
            log.info("filePath: {} ",filePath);
        }
        File directory = new File(filePath);
        if (!directory.exists()) {
            // 目录不存在，新建
            directory.mkdirs();
        }
        UploadVO localUploadVO = new UploadVO();
        String newFileName;
        File fileTemp;
        String originalFileName;
        originalFileName = multipartFile.getOriginalFilename();
        newFileName = this.generateFileName(originalFileName);

        //20210819_135635.jpg
        log.info("originalFileName : {}",originalFileName);


        //fileTemp = new File(new File(filePath + newFileName).getAbsolutePath());

        String absolutePath = new File(filePath + newFileName).getAbsolutePath();
        String rotateAbsolutePath = new File(filePath + "_" + newFileName).getAbsolutePath();



        log.info("absolutePath : {}",absolutePath);
        log.info("rotateAbsolutePath : {}",rotateAbsolutePath);

        fileTemp = new File(absolutePath);


        InputStream inputStream = multipartFile.getInputStream();

        OutputStream os = null;
        try {

            //存到本地
            multipartFile.transferTo(fileTemp);
            log.info("保存后 inputStream : {}",inputStream);
            //复制本地图片  图片名+_
            FileUtil.saveBit(inputStream,rotateAbsolutePath);

            //旋转图片 并替换原文件
            RotateImageUtil.rotateImage(Arrays.asList(rotateAbsolutePath));

            // 使用压缩方法
            byte[] bytes = FileUtil.commpressPicCycle(FileUtil.fileToBytes(rotateAbsolutePath), Integer.valueOf(compressionSize), 0.5f);

            //图片转base64
            String imageToBase64 = FileUtil.ImageToBase64(bytes);
            //log.info("imageToBase64 : {}",imageToBase64);


            //压缩后保存到本地
            FileUtil.bytesToFile(bytes,rotateAbsolutePath);


           // localUploadVO.setUrl(urlParent + newFileName);
            localUploadVO.setFileName(newFileName);
            //localUploadVO.setFilePath(path + "/" + newFileName);
            localUploadVO.setFilePath(filePath + newFileName);
            localUploadVO.setFileSize(multipartFile.getSize());
            localUploadVO.setBase64(imageToBase64);
            localUploadVO.setFileType(fileType);

            return localUploadVO;
           // return new FebsResponse().data(localUploadVO).code("200").message("上传成功").status("success");
        } catch (IOException e) {
            if (fileTemp.exists() && fileTemp.isFile()) {
                fileTemp.delete();
            }
            log.error("", e);
            message = "上传失败";

            throw new FebsException(e.getMessage());
        }

    }
}
