package com.mgq.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author MaGuangQi
 * @description 多个消费者组会同时消费同一topic下的内容,每个组内的消费者会消费指定分区的内容
 * 例如有2个消费者组consumer1和consumer2,都消费topic1的消息
 * consumer1和consumer2都会消费到同一条消息
 * consumer1若有多个consumer,这多个consumer又会消费不同分区的内容.(消费者分区是固定的)
 * 我是消费者组group1中的消费者2:topic是:testKafka分区是:1值是:helloworld6
 * 我是消费者组group2中的消费者2:topic是:testKafka分区是:1值是:helloworld6
 * 我是消费者组group2中的消费者1:topic是:testKafka分区是:2值是:helloworld6
 * 我是消费者组group1中的消费者1:topic是:testKafka分区是:2值是:helloworld6
 * 我是消费者组group2中的消费者1:topic是:testKafka分区是:3值是:helloworld7
 * 我是消费者组group1中的消费者1:topic是:testKafka分区是:3值是:helloworld7
 * @date 2021-05-08 15:57
 **/
//@Component
public class KafkaSimpleConsumer {
    @KafkaListener(topics = {"testKafka"},groupId = "group1")
    public void onMessageGroup1(ConsumerRecord<?, ?> record) {
        //简单消费,打印消费的topic,消费的分区和消费的值
        System.out.println("我是消费者组group1中的消费者1:topic是:"+record.topic()+"分区是:"+record.partition()+"值是:"+record.value());
    }
    @KafkaListener(topics = {"testKafka"},groupId = "group1")
    public void onMessageGroup1C(ConsumerRecord<?, ?> record) {
        //简单消费,打印消费的topic,消费的分区和消费的值
        System.out.println("我是消费者组group1中的消费者2:topic是:"+record.topic()+"分区是:"+record.partition()+"值是:"+record.value());
    }
    @KafkaListener(topics = {"testKafka"},groupId = "group2")
    public void onMessageGroup2(ConsumerRecord<?, ?> record) {
        //简单消费,打印消费的topic,消费的分区和消费的值
        System.out.println("我是消费者组group2中的消费者1:topic是:"+record.topic()+"分区是:"+record.partition()+"值是:"+record.value());
    }
    @KafkaListener(topics = {"testKafka"},groupId = "group2")
    public void onMessageGroup2C(ConsumerRecord<?, ?> record) {
        //简单消费,打印消费的topic,消费的分区和消费的值
        System.out.println("我是消费者组group2中的消费者2:topic是:"+record.topic()+"分区是:"+record.partition()+"值是:"+record.value());
    }
}
