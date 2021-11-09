package com.smart.springbootfacerecognition.module.support.file;


import com.smart.springbootfacerecognition.exception.FebsException;
import com.smart.springbootfacerecognition.exception.FebsResponse;
import com.smart.springbootfacerecognition.module.support.file.constant.FileServiceTypeEnum;
import com.smart.springbootfacerecognition.module.support.file.domain.dto.FileAddDTO;
import com.smart.springbootfacerecognition.module.support.file.domain.dto.UploadDTO;
import com.smart.springbootfacerecognition.module.support.file.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Slf4j
@RestController
public class FileController {

    private String message;

    @Autowired
    private FileService fileService;

    @PostMapping("/api/file/localUpload/{moduleType}")
    public FebsResponse localUpload(MultipartFile file, @PathVariable Integer moduleType) throws FebsException {
        return fileService.fileUpload(file, FileServiceTypeEnum.LOCAL, moduleType);
    }

    @PostMapping("/api/file/localUpload")
    public FebsResponse localUpload1(UploadDTO uploadDTO) throws FebsException {
        return fileService.fileUpload1(uploadDTO,FileServiceTypeEnum.LOCAL);
    }

    @PostMapping("/api/file/save")
    public FebsResponse saveFile(@Valid @RequestBody FileAddDTO addDTO) {
       // RequestTokenBO requestToken = SmartRequestTokenUtil.getRequestUser();
        return fileService.saveFile(addDTO);
    }

    @ResponseBody
    @PostMapping("/api/file/get")
    public FebsResponse getFile() {
        // RequestTokenBO requestToken = SmartRequestTokenUtil.getRequestUser();

        return fileService.getFile();
    }
}
