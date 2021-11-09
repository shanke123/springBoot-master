package com.smart.springbootfacerecognition.gson;

import com.google.gson.Gson;
import com.smart.springbootfacerecognition.module.business.subscribe.domain.dto.SubscribeDTO;
import org.junit.Test;

public class Demo1 {

    @Test
    public void test1(){
       String msg=  "{\"id\":1,\"method\":\"face.set\",\"status\":200,\"data\":{\"message\":\"ok\"}}";
        Gson gson = new Gson();
        SubscribeDTO subscribeDTO = gson.fromJson(msg, SubscribeDTO.class);
        System.out.println(subscribeDTO);

    }
}
