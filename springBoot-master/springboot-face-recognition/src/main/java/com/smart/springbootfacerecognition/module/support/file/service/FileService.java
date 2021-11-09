package com.smart.springbootfacerecognition.module.support.file.service;


import com.smart.springbootfacerecognition.exception.FebsException;
import com.smart.springbootfacerecognition.exception.FebsResponse;
import com.smart.springbootfacerecognition.module.support.file.FileDao;
import com.smart.springbootfacerecognition.module.support.file.constant.FileModuleTypeEnum;
import com.smart.springbootfacerecognition.module.support.file.constant.FileServiceTypeEnum;
import com.smart.springbootfacerecognition.module.support.file.domain.dto.FileAddDTO;
import com.smart.springbootfacerecognition.module.support.file.domain.dto.UploadDTO;
import com.smart.springbootfacerecognition.module.support.file.domain.entity.FileEntity;
import com.smart.springbootfacerecognition.module.support.file.domain.entity.UserEntity;
import com.smart.springbootfacerecognition.module.support.file.domain.vo.UploadVO;
import com.smart.springbootfacerecognition.util.SmartBaseEnumUtil;
import com.smart.springbootfacerecognition.util.SmartBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Slf4j
@Service
public class FileService {


    private String message;

    @Autowired
    @Qualifier("local")
    private IFileService fileService;

    //@Autowired
    //private PersonService personService;

    @Autowired
    private FileDao fileDao;

    @Autowired
    private HttpSession session;

    public FebsResponse fileUpload(MultipartFile file, FileServiceTypeEnum local, Integer moduleType) throws FebsException {
        //return new FebsResponse().data("").code("200").message("上传成功").status("success");


        try {
            FileModuleTypeEnum moduleTypeEnum = SmartBaseEnumUtil.getEnumByValue(moduleType, FileModuleTypeEnum.class);

            if (null == moduleTypeEnum) {
                message = "文件目录类型错误";
                throw new FebsException(message);
            }
            UploadVO uploadVO = fileService.fileUpload(file, moduleTypeEnum.getPath());
           // return fileService.fileUpload(file, moduleTypeEnum.getPath());


            //掉第三方接口上传
            //personService.addPersion(uploadVO.getBase64());



            FileAddDTO fileAddDTO = FileAddDTO.builder()
                    .fileName(uploadVO.getFileName())
                    .filePath(uploadVO.getFilePath())
                    .fileSize(String.valueOf(uploadVO.getFileSize()))

                    .build();

            this.saveFile(fileAddDTO);
            return new FebsResponse().data(fileAddDTO).code("200").message("上传成功").status("success");

        }catch(Exception e){
            message = "上传失败";
            log.error(message, e);
            throw new FebsException(message);
        }


    }

    /**
     * 系统文件保存通用接口
     * @param addDTO
     * @return
     */
    public FebsResponse saveFile(FileAddDTO addDTO) {
        FileEntity entity = SmartBeanUtil.copy(addDTO,FileEntity.class);
        //entity.setCreaterUser(requestToken.getRequestUserId());
        entity.setCreateTime(new Date());
        entity.setUpdateTime(new Date());

        log.info("entity : {}",entity);
        fileDao.insert(entity);
        return new FebsResponse().code("200").message("上传文件成功").status("success");

    }

    public FebsResponse getFile() {


        FileEntity file = fileDao.selectFileByUser(UserEntity.builder().cardUserId("0002").build());
        return new FebsResponse().data(file).code("200").message("上传文件成功").status("success");
    }

    public FebsResponse fileUpload1(UploadDTO uploadDTO, FileServiceTypeEnum local) throws FebsException {

        try {
            FileModuleTypeEnum moduleTypeEnum = SmartBaseEnumUtil.getEnumByValue(uploadDTO.getModuleType(), FileModuleTypeEnum.class);

            if (null == moduleTypeEnum) {
                message = "文件目录类型错误";
                throw new FebsException(message);
            }
            UploadVO uploadVO = fileService.fileUpload(uploadDTO.getFile(), moduleTypeEnum.getPath());
            // return fileService.fileUpload(file, moduleTypeEnum.getPath());


            //掉第三方接口上传
            //personService.addPersion(uploadVO.getBase64());



            FileAddDTO fileAddDTO = FileAddDTO.builder()
                    .fileName(uploadVO.getFileName())
                    .filePath(uploadVO.getFilePath())
                    .fileSize(String.valueOf(uploadVO.getFileSize()))
                    .fileType(uploadVO.getFileType())
                    .idcard(uploadDTO.getIdcard())
                    .build();

            this.saveFile(fileAddDTO);
            return new FebsResponse().data(fileAddDTO).code("200").message("上传成功").status("success");

        }catch(Exception e){
            message = "上传失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
}
