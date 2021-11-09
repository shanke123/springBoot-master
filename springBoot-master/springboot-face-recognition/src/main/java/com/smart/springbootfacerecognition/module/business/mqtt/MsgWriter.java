package com.smart.springbootfacerecognition.module.business.mqtt;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**
 * 消息发送接口
 */
@Component
@MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
public interface MsgWriter {
    void sendToMqtt(String data);
    void sendToMqtt(String payload, @Header(MqttHeaders.TOPIC) String topic);
    void sendToMqtt(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) int qos, String payload);
}