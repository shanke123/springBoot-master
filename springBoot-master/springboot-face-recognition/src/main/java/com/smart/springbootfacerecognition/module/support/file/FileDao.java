package com.smart.springbootfacerecognition.module.support.file;


import com.smart.springbootfacerecognition.module.support.file.domain.entity.FileEntity;
import com.smart.springbootfacerecognition.module.support.file.domain.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface FileDao {

    /**
     * 批量添加上传文件
     *
     * @param fileDTOList
     * @return
     */
    Integer insertFileEntityBatch(List<FileEntity> fileDTOList);

    int insert(FileEntity entity);

    FileEntity selectFileByUser(UserEntity userEntity);
}
