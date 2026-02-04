package com.temp.kafka.consumer.Kafka_Consumer.listener;

import com.temp.kafka.consumer.Kafka_Consumer.service.MessageProcessor;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.temp.kafka.consumer.Kafka_Consumer.constant.KafkaConstant.NEW_TOPIC;


@Component
@AllArgsConstructor
public class KafkaConListener {
    private final MessageProcessor messageProcessor;

    @KafkaListener(topics = NEW_TOPIC)
    public void consumeRecord(ConsumerRecord<Integer,String> record)  {
        messageProcessor.processor(record);
    }

}
