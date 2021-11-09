package com.smart.springbootfacerecognition.gson;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.google.gson.Gson;
import com.smart.springbootfacerecognition.module.business.subscribe.domain.entity.SubscribeEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Date;

@Slf4j
public class GsonTest {

    @Test
    public void test1(){
        String url = "http://120.27.10.246:8900/qrcodeImg/2021-11-04/test.jpg";
        String exit = "jpg";
        String name = "刘燚";

        Integer trdID = new Integer(GenerateUniqueIdUtil.getGuid());

        trdID = 123;


        Image image = Image.builder()
                .type(Image.TYPE)
                .url(url)
                .ext("." + exit)
                .build();

        Params params = Params.builder()
                .id(Params.PARAMS_ID)
                .name(name)
                //最大长度为10
                .trdID(trdID)
                .image(image)
                .build();


        PayloadBody payloadBody = PayloadBody.builder()
                .id(PayloadBody.PAYLOAD_BODY_ID)
                .method(PayloadBody.PAYLOAD_BODY_METHOD)
                .params(params)
                .build();


        Gson gson = new Gson();
        String userJson = gson.toJson(payloadBody);
        System.out.println(userJson);
    }

    public static void main(String[] args) {

        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        //1456477967151665152
        //1456478037569835008
        long nextId = snowflake.nextId();
        log.info("nextId:{}",nextId);
    }


    @Test
    public void test2(){
        //1636093811447
        long time = new Date().getTime();
        System.out.println(time);
    }


    @Test
    public void test3(){
        /*SubscribeEntity subscribeEntity = new SubscribeEntity();
        Gson gson = new Gson();
        String userJson = gson.toJson(subscribeEntity);
        System.out.println(userJson);*/


        String json2 = "{\"id\":1,\"method\":\"face.set\",\"status\":200,\"data\":{\"message\":\"ok\"}}";
        Gson gson2 = new Gson();
        SubscribeDTO subscribeEntity1 = gson2.fromJson(json2, SubscribeDTO.class);
        System.out.println(subscribeEntity1.toString());

    }

}
