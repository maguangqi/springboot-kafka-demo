package com.mgq.kafka.product;

import com.mgq.kafka.pojo.DataPackage;
import com.mgq.kafka.pojo.KpiData;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaFailureCallback;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author MaGuangQi
 * @description
 * @date 2021-05-08 15:56
 **/

@RestController
public class KafkaProducer {
    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * 方法描述: 简单发送消息
     *
     * @param message 消息
     * @return void
     */

    @GetMapping("kafka/simple/{message}")
    public void simpleSend(@PathVariable String message) {
        kafkaTemplate.send("testKafka", message);
    }

    /**
     * 方法描述: 带回调方法的异步发送消息
     *
     * @param message 消息内容
     * @return void
     */
    @GetMapping("kafka/callback/{message}")
    public void callbackSend(@PathVariable String message) {
        kafkaTemplate.send("testKafka", message).addCallback(success -> {
            // 消息发送到的topic
            String topic = success.getRecordMetadata().topic();
            // 消息发送到的分区
            int partition = success.getRecordMetadata().partition();
            // 消息在分区内的offset
            long offset = success.getRecordMetadata().offset();
            System.out.println("发送消息成功:" + topic + "-" + partition + "-" + offset);
        }, (KafkaFailureCallback<Integer, String>) ex -> {
            ProducerRecord<Integer, String> failed = ex.getFailedProducerRecord();
            System.out.println("发送失败,异常是:" + ex.getMessage());
            System.out.println("发送失败的内容是:" + failed.value());
        });
    }

    /**
     * 方法描述: 同步发送
     *
     * @param message 消息内容
     * @return void
     */
    @GetMapping("kafka/syn_send/{message}")
    public void sendToKafka(@PathVariable String message) {

        try {
            kafkaTemplate.send("testKafka", message).get(10, TimeUnit.SECONDS);
//            handleSuccess(data);
            System.out.println("发送成功");
        } catch (ExecutionException e) {
            System.out.println("发送失败");
        } catch (TimeoutException | InterruptedException e) {
            //handleFailure(data, record, e);
            System.out.println("发送超时");
        }
    }

    /**
     * 方法描述: 事务消息
     * 声明事务：后面报错消息不会发出去
     * 注意:需要配置 spring.kafka.producer.acks=-1,否则无法保证幂等性
     * 同时需要配置 spring.kafka.producer.transaction-id-prefix=xxx,来开启事务
     * 否则会抛出 java.lang.IllegalStateException: Producer factory does not support transactions
     *
     * @param message 消息内容
     * @return void
     */
    @GetMapping("kafka/transaction/{message}")
    public void sendToKafkaTransaction(@PathVariable String message) {
        kafkaTemplate.executeInTransaction(kafkaOperations -> {
            kafkaOperations.send("testKafka", message);
            throw new RuntimeException("fail");
        });
    }

    /**
     * 方法描述:自定义序列化 使用JsonSerializer序列化
     *此处将消息进行JSON序列化
     * 需要配置的地方:
     * 生产者:JsonSerializer是spring提供的.
     * spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JsonSerializer
     * @return
     */
    @GetMapping("kafka/serializable")
    public void serializable() {
        DataPackage dataPackage = new DataPackage();
        dataPackage.setCollectMethod("AGENT");
        dataPackage.setCollectTime(System.currentTimeMillis());
        KpiData kpiData = new KpiData();
        kpiData.setData("22");
        kpiData.setKpiCode("200213141241");
        Map<String, List<KpiData>> kpiDatas = new HashMap<>();
        kpiDatas.put(kpiData.getKpiCode(), Arrays.asList(kpiData));
        dataPackage.setKpiDatas(kpiDatas);
        kafkaTemplate.send("testKafka", dataPackage).addCallback(success -> {
            System.out.println("发送成功");
        }, failure -> {
            System.out.println("发送失败" + failure.getMessage());
        });

    }
    /**
     * 方法描述:发送有header的消息(kafka支持在消息头设置信息)
     * @return void
     */
    @GetMapping("kafka/sendHeader")
    public void sendHeader() {

        String data = "12341";
        //构建Message,使用MessageBuilder类,需要在header中设置发送的topic
        //也可以设置自定义的header key
        Message<String> message = MessageBuilder.withPayload(data)
                .setHeader(KafkaHeaders.TOPIC, "testKafka")
                .setHeader("hello","value")
                .build();
        kafkaTemplate.send(message).addCallback(success -> {
            System.out.println("发送成功");
        }, failure -> {
            System.out.println("发送失败" + failure.getMessage());
        });

    }
    @GetMapping("kafka/serializable2")
    public void serializable2() {

        KpiData kpiData = new KpiData();
        kpiData.setData("22");
        kpiData.setKpiCode("200213141241");

        kafkaTemplate.send("testKafka", kpiData).addCallback(success -> {
            System.out.println("发送成功");
        }, failure -> {
            System.out.println("发送失败" + failure.getMessage());
        });

    }
}
