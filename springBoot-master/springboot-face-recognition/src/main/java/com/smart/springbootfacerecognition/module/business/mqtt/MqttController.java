package com.smart.springbootfacerecognition.module.business.mqtt;

import com.google.gson.Gson;

import com.smart.springbootfacerecognition.module.business.mqtt.domain.Image;
import com.smart.springbootfacerecognition.module.business.mqtt.domain.Params;
import com.smart.springbootfacerecognition.module.business.mqtt.domain.PayloadBody;
import com.smart.springbootfacerecognition.util.GenerateUniqueIdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MqttController {
    @Autowired
    private MsgWriter msgWriter;
    @RequestMapping("/send")
    public String sendMqtt(String  sendData){
        String data  = "{\"id\":1,\"method\":\"meta.get\",\"params\":{}}";





        msgWriter.sendToMqtt(test1(),"attendance/jiminate3/ec43d839-23e8-4291-8726-defcdc04c037/request/1");
        //msgWriter.sendToMqtt(data,"attendance/jiminate3/ec43d839-23e8-4291-8726-defcdc04c037/request/1");

        return "OK";
    }


    public String test1(){
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
        String payloadJson = gson.toJson(payloadBody);
        System.out.println(payloadJson);
        return payloadJson;
    }
}
