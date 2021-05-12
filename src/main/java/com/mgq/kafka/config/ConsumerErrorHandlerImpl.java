package com.mgq.kafka.config;

import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.messaging.Message;

/**
 * @author MaGuangQi
 * @description
 * @date 2021-05-11 17:15
 **/
public class ConsumerErrorHandlerImpl implements KafkaListenerErrorHandler {
    @Override
    public Object handleError(Message<?> message, ListenerExecutionFailedException exception) {
        System.out.println("消息消费异常,消息是"+message.getPayload()+";异常是"+exception.getMessage());
        return "Fail";
    }
}
