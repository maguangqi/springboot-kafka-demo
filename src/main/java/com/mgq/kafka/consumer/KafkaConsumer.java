package com.mgq.kafka.consumer;

import com.mgq.kafka.pojo.DataPackage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author MaGuangQi
 * @description
 * @date 2021-05-10 17:33
 **/
@Component
public class KafkaConsumer {
    /**
     * 方法描述:批量消费
     *
     * @param records list
     * @return void
     */
    //@KafkaListener(topics = {"testKafka"},groupId = "group1")
    public void onMessageGroup1(List<ConsumerRecord<?, ?>> records) {
        //批量消费,接收时用list
        for (ConsumerRecord<?, ?> record : records) {
            System.out.println("我是消费者组group1中的消费者1:topic是:" + record.topic() + "分区是:" + record.partition() + "值是:" + record.value());
        }
    }

    /**
     * 方法描述:手动提交
     *
     * @param record         记录
     * @param acknowledgment 手动提交
     *                       AckMode有以下几个
     *                       RECORD 				 # 每处理一条commit一次
     *                       BATCH(默认) 			# 每次poll的时候批量提交一次，频率取决于每次poll的调用频率
     *                       TIME 					# 每次间隔ackTime的时间去commit
     *                       COUNT					# 累积达到ackCount次的ack去commit
     *                       COUNT_TIME				# ackTime或ackCount哪个条件先满足，就commit
     *                       MANUAL					# listener负责ack，但是背后也是批量上去
     *                       MANUAL_IMMEDIATE		# listner负责ack，每调用一次，就立即commit
     *                       注意:需要配置 spring.kafka.listener.ack-mode=manual
     *                       同时关闭自动提交 spring.kafka.consumer.enable-auto-commit=true
     * @return void
     */
    //@KafkaListener(topics = {"testKafka"},groupId = "group1" )
    public void onMessageManualCommit(ConsumerRecord<String, DataPackage> record, Acknowledgment acknowledgment) {
        try {
            System.out.println("接收到消息:topic是:" + record.topic() + "分区是:" + record.partition() + "值是:" + record.value().getCollectMethod());
        } finally {
            acknowledgment.acknowledge();
        }
    }

    /**
     * 方法描述:自定义反序列化
     * 需要配置的地方:spring.kafka.consumer.value-deserializer=org.springframework.kafka.support.serializer.JsonDeserializer
     * 特别注意:配置此属性,需要让pojo类让kafka trust; *代表所有包名
     * spring.kafka.consumer.properties.spring.json.trusted.packages=*
     *
     * @param record         记录
     * @param acknowledgment 手动ack使用
     * @return
     */
    //@KafkaListener(topics = {"testKafka"}, groupId = "group1")
    public void customDeserializer(ConsumerRecord<String, DataPackage> record, Acknowledgment acknowledgment) {
        try {
            System.out.println("接收到消息:topic是:" + record.topic() + "分区是:" + record.partition() + "值是:" + record.value().getCollectMethod());
        } finally {
            acknowledgment.acknowledge();
        }
    }

    /**
     * 方法描述:kafkaListener生命周期管理
     * 此处不让kafkaListener自动启动,演示如何在我们指定的时间点开始工作，或者在我们指定的时间点停止工作
     * 也就是说支持管理KafkaListener
     * @param record
     * @return
     */
    //@KafkaListener(id = "myListener1",topics = {"testKafka"}, groupId = "group1",autoStartup = "false")
    public void lifeManageKafkaListener(ConsumerRecord<String, DataPackage> record) {
        System.out.println("接收到消息:topic是:" + record.topic() + "分区是:" + record.partition() + "值是:" + record.value().getCollectMethod());
    }
    /**
     * 方法描述:消费消息中的header
     * @return
     */
    @KafkaListener(id = "myListener2",topics = {"testKafka"}, groupId = "group1")
    public void listenerHeader(@Payload String data,
                               @Header(name = KafkaHeaders.RECEIVED_MESSAGE_KEY, required = false) Integer key,
                               @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                               @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                               @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long ts,
                               @Header(name = "hello") String hello) {
        System.out.println("接收到消息:topic是:" + topic + "分区是:" + partition + "值是:" + data +";hello头的值是:"+hello);
    }
}
