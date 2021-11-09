/*
package com.smart.springbootfacerecognition.module.business.mqtt.config;


import com.google.gson.Gson;
import com.smart.springbootfacerecognition.exception.FebsException;
import com.smart.springbootfacerecognition.module.business.subscribe.SubscribeService;
import com.smart.springbootfacerecognition.module.business.subscribe.domain.dto.SubscribeDTO;
import com.smart.springbootfacerecognition.module.business.subscribe.domain.entity.SubscribeEntity;
import com.smart.springbootfacerecognition.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

*/
/**
 * MQTT配置，消费者
 *//*


@Slf4j
@Configuration
public class MqttReceiverConfig {
    */
/**
     * 订阅的bean名称
     *//*

    public static final String CHANNEL_NAME_IN = "mqttInboundChannel";

    // 客户端与服务器之间的连接意外中断，服务器将发布客户端的“遗嘱”消息
    private static final byte[] WILL_DATA;
    static {
        WILL_DATA = "offline".getBytes();
    }

    @Value("${spring.mqtt.username}")
    private String username;

    @Value("${spring.mqtt.password}")
    private String password;

    @Value("${spring.mqtt.url}")
    private String url;

    @Value("${spring.mqtt.client.id}")
    private String clientId;

    @Value("${spring.mqtt.default.subscribe.topic}")
    private String defaultTopic;

    */
/**
     * MQTT连接器选项
     *//*

    @Bean
    public MqttConnectOptions getReceiverMqttConnectOptions(){
*/
/*        MqttConnectOptions options = new MqttConnectOptions();
        // 设置连接的用户名
        if(!username.trim().equals("")){
            options.setUserName(username);
        }
        // 设置连接的密码
        options.setPassword(password.toCharArray());
        // 设置连接的地址
        options.setServerURIs(StringUtils.split(url, ","));
        // 设置超时时间 单位为秒
        options.setConnectionTimeout(10);
        // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送心跳判断客户端是否在线
        // 但这个方法并没有重连的机制
        options.setKeepAliveInterval(20);

        return options;*//*


        MqttConnectOptions mqttConnectOptions=new MqttConnectOptions();
        mqttConnectOptions.setUserName(username);
        mqttConnectOptions.setPassword(password.toCharArray());
        mqttConnectOptions.setServerURIs(new String[]{url});
        mqttConnectOptions.setKeepAliveInterval(2);
        return mqttConnectOptions;
    }

    */
/**
     * MQTT客户端
     *//*

    @Bean
    public MqttPahoClientFactory receiverMqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(getReceiverMqttConnectOptions());
        return factory;
    }

    */
/**
     * MQTT信息通道（消费者）
     *//*

    @Bean(name = CHANNEL_NAME_IN)
    public MessageChannel mqttInboundChannel() {
        return new DirectChannel();
    }


    */
/**
     * MQTT消息订阅绑定（消费者）
     *//*

    @Bean
    public MessageProducer inbound() {
        // 可以同时消费（订阅）多个Topic
       */
/* MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(
                        clientId, receiverMqttClientFactory(),
                        StringUtils.split(defaultTopic, ","));*//*



        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(clientId+"_inbound", receiverMqttClientFactory(),
                        "attendance/jiminate3/ec43d839-23e8-4291-8726-defcdc04c037/response/+","attendance/jiminate3/ec43d839-23e8-4291-8726-defcdc04c037/request/1");

        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        // 设置订阅通道
        adapter.setOutputChannel(mqttInboundChannel());
        return adapter;
    }

    */
/**
     * MQTT消息处理器（消费者）
     *//*

    @Bean
    @ServiceActivator(inputChannel = CHANNEL_NAME_IN)
    public MessageHandler handler() {
        return new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                String topic = message.getHeaders().get("mqtt_receivedTopic").toString();
                String msg = message.getPayload().toString();
//                if ()
                System.out.println("\n--------------------START-------------------\n" +
                        "接收到订阅消息:\ntopic:" + topic + "\nmessage:" + msg +
                        "\n---------------------END--------------------");

                //接收到的数据保存到全局

                //String json2 = "{\"id\":1,\"method\":\"face.set\",\"status\":200,\"data\":{\"message\":\"ok\"}}";
                Gson gson = new Gson();
                SubscribeDTO subscribeDTO = gson.fromJson(msg, SubscribeDTO.class);




                log.info("subscribeDTO:{}",subscribeDTO);

                ApplicationContext context = SpringUtil.context;  //获取Spring容器
                SubscribeService subscribeService = context.getBean(SubscribeService.class);  //获取bean


            */
/*    SubscribeEntity build = SubscribeEntity.builder()
                        .id(subscribeDTO.getId())
                        .method(subscribeDTO.getMethod())
                        .status(subscribeDTO.getStatus())
                        .data(subscribeDTO.getData().getMessage())
                        .build();

                try {
                    subscribeService.saveSubscribe(build);
                } catch (FebsException e) {
                    e.printStackTrace();
                }*//*

            }
        };
    }
}
*/
