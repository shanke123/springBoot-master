package com.smart.springbootfacerecognition.module.business.mqtt.config;

import com.google.gson.Gson;
import com.smart.springbootfacerecognition.exception.FebsException;
import com.smart.springbootfacerecognition.module.business.subscribe.SubscribeService;
import com.smart.springbootfacerecognition.module.business.subscribe.domain.dto.SubscribeDTO;
import com.smart.springbootfacerecognition.module.business.subscribe.domain.entity.SubscribeEntity;
import com.smart.springbootfacerecognition.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.*;

@Slf4j
@Configuration
@IntegrationComponentScan
public class MqttReceiveConfig1 {

    @Value("${spring.mqtt.username}")
    private String username;

    @Value("${spring.mqtt.password}")
    private String password;

    @Value("${spring.mqtt.url}")
    private String hostUrl;

    @Value("${spring.mqtt.client.id}")
    private String clientId;

    @Value("${spring.mqtt.default.publish.topic}")
    private String defaultTopic;

    @Value("${spring.mqtt.completionTimeout}")
    private int completionTimeout ;   //连接超时


    @Bean(name = "receiveMqttConnectOptions")
    public MqttConnectOptions getMqttConnectOptions(){
        MqttConnectOptions mqttConnectOptions=new MqttConnectOptions();
        mqttConnectOptions.setUserName(username);
        mqttConnectOptions.setPassword(password.toCharArray());
        mqttConnectOptions.setServerURIs(new String[]{hostUrl});
        mqttConnectOptions.setKeepAliveInterval(2);
        return mqttConnectOptions;
    }

    @Bean(name = "receiveMqttClientFactory")
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(getMqttConnectOptions());
        return factory;
    }

    //接收通道
    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    //配置client,监听的topic
    @Bean
    public MessageProducer inbound() {
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(clientId+"_inbound", mqttClientFactory(),
                        "hello","hello1","attendance/jiminate3/ec43d839-23e8-4291-8726-defcdc04c037/response/+","attendance/jiminate3/ec43d839-23e8-4291-8726-defcdc04c037/request/1");
        adapter.setCompletionTimeout(completionTimeout);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }



   public Message payload() {
        return new Message(){

            @Override
            public Object getPayload() {
                return null;
            }

            @Override
            public MessageHeaders getHeaders() {
                return null;
            }
        };
    }



    //通过通道获取数据
    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler() {
        return new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {

                String topic = message.getHeaders().get("mqtt_receivedTopic").toString();

                String type = topic.substring(topic.lastIndexOf("/")+1, topic.length());
              /*  if("hello".equalsIgnoreCase(topic)){
                    System.out.println("hello,fuckXX,"+message.getPayload().toString());
                }else if("hello1".equalsIgnoreCase(topic)){
                    System.out.println("hello1,fuckXX,"+message.getPayload().toString());
                }else if("attendance/jiminate3/ec43d839-23e8-4291-8726-defcdc04c037/response/+".equalsIgnoreCase(topic)){
                    System.out.println("response/+,"+message.getPayload().toString());

                }else if("attendance/jiminate3/ec43d839-23e8-4291-8726-defcdc04c037/request/1".equalsIgnoreCase(topic)){
                    System.out.println("response/+,"+message.getPayload().toString());

                }*/


                if("attendance/jiminate3/ec43d839-23e8-4291-8726-defcdc04c037/request/1".equalsIgnoreCase(topic)){

                }else if("attendance/jiminate3/ec43d839-23e8-4291-8726-defcdc04c037/response/1".equalsIgnoreCase(topic)){

                    log.info("-----响应-----");
                    String topic1 = message.getHeaders().get("mqtt_receivedTopic").toString();
                    String msg = message.getPayload().toString();
                    log.info("\n----------------------------START---------------------------\n" +
                            "接收到订阅消息:\ntopic:" + topic1 + "\nmessage:" + msg +
                            "\n-----------------------------END----------------------------");

                    Gson gson = new Gson();
                    SubscribeDTO subscribeDTO = gson.fromJson(msg, SubscribeDTO.class);

                    log.info("subscribeDTO:{}",subscribeDTO);

                    ApplicationContext context = SpringUtil.context;  //获取Spring容器
                    SubscribeService subscribeService = context.getBean(SubscribeService.class);  //获取bean

                    SubscribeEntity build = SubscribeEntity.builder()
                            .id(subscribeDTO.getId())
                            .method(subscribeDTO.getMethod())
                            .status(subscribeDTO.getStatus())
                            .data(subscribeDTO.getData().getMessage())
                            .build();

                    try {
                        subscribeService.saveSubscribe(build);
                    } catch (FebsException e) {
                        e.printStackTrace();
                    }
                }


                String topic1 = message.getHeaders().get("mqtt_receivedTopic").toString();
                String msg = message.getPayload().toString();
                log.info("\n----------------------------START---------------------------\n" +
                        "接收到订阅消息:\ntopic:" + topic1 + "\nmessage:" + msg +
                        "\n-----------------------------END----------------------------");



                //topic:attendance/jiminate3/ec43d839-23e8-4291-8726-defcdc04c037/response/1
                //message:{"id":1,"method":"face.set","status":200,"data":{"message":"ok"}}

                //接收到的数据保存到全局

                //String json2 = "{\"id\":1,\"method\":\"face.set\",\"status\":200,\"data\":{\"message\":\"ok\"}}";



            }
        };
    }

}
