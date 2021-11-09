package com.smart.springbootfacerecognition.module.business.persion;

import com.google.gson.Gson;
import com.smart.springbootfacerecognition.exception.FebsException;
import com.smart.springbootfacerecognition.module.business.mqtt.MsgWriter;
import com.smart.springbootfacerecognition.module.business.mqtt.domain.Image;
import com.smart.springbootfacerecognition.module.business.mqtt.domain.Params;
import com.smart.springbootfacerecognition.module.business.mqtt.domain.PayloadBody;
import com.smart.springbootfacerecognition.module.business.persion.domain.entity.PersionEntity;
import com.smart.springbootfacerecognition.exception.FebsResponse;
import com.smart.springbootfacerecognition.module.business.subscribe.SubscribeService;
import com.smart.springbootfacerecognition.module.support.file.domain.dto.FileAddDTO;
import com.smart.springbootfacerecognition.util.GenerateUniqueIdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Slf4j
@Controller
public class PersonController {


    @Autowired
    private MsgWriter msgWriter;

    @Autowired
    private PersionService persionService;

    @RequestMapping("/toPersion")
    public String toPersion() {
        return "persion";
    }

    @RequestMapping("/toPersionzz")
    public String toPersionzz() {
        return "index";
    }

    @ResponseBody
    @RequestMapping("/persion")
    public String Persion() {
        return "persion";
    }


    @ResponseBody
    @PostMapping("/api/persion/save")
    public FebsResponse savePersion(@Valid @RequestBody PersionEntity addDTO) throws FebsException {
        // RequestTokenBO requestToken = SmartRequestTokenUtil.getRequestUser();

        msgWriter.sendToMqtt(getSqtData(addDTO),"attendance/jiminate3/ec43d839-23e8-4291-8726-defcdc04c037/request/1");


        return persionService.savePersion(addDTO);
    }


    public String getSqtData(PersionEntity persionEntity){
        /*String url = "http://120.27.10.246:8900/qrcodeImg/2021-11-04/test.jpg";
        String exit = "jpg";
        String name = "刘燚";*/

        //			   	<Context path="/qrcodeImg" docBase="C:\qrcode" reloadable="true" crossContext="true"></Context>

        String url = persionEntity.getFilePath();
        String exit = persionEntity.getFileType();
        String name = persionEntity.getUserName();
        String idcard = persionEntity.getIdcard();

        Integer trdID = new Integer(GenerateUniqueIdUtil.getGuid());

        //trdID = 123;


        Image image = Image.builder()
                .type(Image.TYPE)
                .url(url)
                .ext("." + exit)
                .build();

        Params params = Params.builder()
                .id(Params.PARAMS_ID)
                .idCardNo(idcard)
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
