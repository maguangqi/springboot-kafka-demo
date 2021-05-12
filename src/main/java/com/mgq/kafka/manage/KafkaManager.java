package com.mgq.kafka.manage;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.Collection;
import java.util.concurrent.ExecutionException;

/**
 * @author MaGuangQi
 * @description
 * @date 2021-05-08 17:22
 **/
@Configuration
public class KafkaManager {
    @Autowired
    KafkaAdmin admin;

    /**
     * kafkaAdmin可以对kafka集群进行管理,如下是显示集群节点信息
     */
    public void describeCluster() throws ExecutionException, InterruptedException {
        AdminClient client = AdminClient.create(admin.getConfigurationProperties());
        KafkaFuture<Collection<Node>> nodes = client.describeCluster().nodes();
        for (Node node : nodes.get()) {
            System.out.println("id:" + node.id() + ",host:" + node.host());
        }
    }

    public void listTopics() throws ExecutionException, InterruptedException {
        AdminClient client = AdminClient.create(admin.getConfigurationProperties());
        client.listTopics().names().get().forEach(System.out::println);
    }
}
