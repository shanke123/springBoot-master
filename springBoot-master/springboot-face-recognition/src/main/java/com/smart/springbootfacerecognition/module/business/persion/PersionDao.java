package com.smart.springbootfacerecognition.module.business.persion;

import com.smart.springbootfacerecognition.module.business.persion.domain.entity.PersionEntity;
import com.smart.springbootfacerecognition.module.support.file.domain.entity.FileEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface PersionDao {


    int insert(PersionEntity entity);
}
