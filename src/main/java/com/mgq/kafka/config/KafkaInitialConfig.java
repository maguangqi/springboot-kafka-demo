package com.mgq.kafka.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.messaging.Message;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author MaGuangQi
 * @description
 * @date 2021-05-08 16:07
 **/
@Configuration
public class KafkaInitialConfig {
    @Autowired
    Environment environment;
    @Bean
    public NewTopic initialTopic() {
        return new NewTopic("testKafka", 4, (short) 2);
    }

    // 如果要修改分区数，只需修改配置值重启项目即可
    // 修改分区数并不会导致数据的丢失，但是分区数只能增大不能减小
   /* @Bean
    public NewTopic updateTopic() {
        return new NewTopic("testKafka", 6, (short) 2);
    }*/

    @Bean
    public KafkaAdmin admin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,environment.getProperty("spring.kafka.bootstrap-servers"));
        return new KafkaAdmin(configs);
    }

    @Bean
    public KafkaListenerErrorHandler errorHandler() {
       return (message, exception) -> {
           System.out.println("消费异常消息是:"+message+";异常是:"+exception);
           return null;
       };
    }
}
