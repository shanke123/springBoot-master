package com.smart.springbootfacerecognition.module.support.file.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity implements Serializable {

    private static final long serialVersionUID = -4852732617765810959L;


    /**
     * 账户状态
     */
    public static final String STATUS_VALID = "0";

    private String cardNo;

    private String cardUserId;

    /**
     * 卡状态0正常 1挂失 2出院退卡
     */
    private String cardStatus;


    private Date cardTime;


    private String catrdOp;

    private BigDecimal cardAmount;

    /**
     * 余额
     */
    private BigDecimal cardCash;

    /**
     * 卡类型  0职工 1病人 2病人家属
     */
    private String cardType;


    private String xm;

    private String wxpwd;

}
