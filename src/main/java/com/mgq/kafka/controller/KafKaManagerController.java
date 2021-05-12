package com.mgq.kafka.controller;

import com.mgq.kafka.manage.KafkaManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

/**
 * @author MaGuangQi
 * kafka 管理类
 * @date 2021-05-08 17:25
 **/
@RestController
public class KafKaManagerController {
    @Autowired
    KafkaManager manager;
    /**
     * kafka管理
     */
    @Autowired
    private KafkaListenerEndpointRegistry registry;

    @GetMapping("describe")
    public void describeCluster() throws ExecutionException, InterruptedException {
        manager.describeCluster();
    }

    /**
     * 方法描述:列出topic
     */
    @GetMapping("listTopics")
    public void listTopics() throws ExecutionException, InterruptedException {
        manager.listTopics();
    }

    /**
     * 方法描述:启动指定的kafkaListener
     */
    @GetMapping("startListener")
    public void startListener() {
        registry.getListenerContainer("myListener1").start();
    }

    /**
     * 方法描述:关闭指定的kafkaListener
     */
    @GetMapping("stopListener")
    public void stopListener() {
        registry.getListenerContainer("myListener1").stop();
    }
}
