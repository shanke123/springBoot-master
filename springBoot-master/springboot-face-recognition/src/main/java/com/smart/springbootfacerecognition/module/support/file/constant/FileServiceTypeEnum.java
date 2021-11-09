package com.smart.springbootfacerecognition.module.support.file.constant;

public enum FileServiceTypeEnum {

    /**
     * 本地文件服务
     */
    LOCAL(1, FileServiceNameConst.LOCAL, "本地文件服务");

    private Integer locationType;

    private String serviceName;

    private String desc;

    FileServiceTypeEnum(Integer locationType, String serviceName, String desc) {
        this.locationType = locationType;
        this.serviceName = serviceName;
        this.desc = desc;
    }

    public String getServiceName() {
        return serviceName;
    }

    public Integer getValue() {
        return this.locationType;
    }

    public String getDesc() {
        return this.desc;
    }
}
