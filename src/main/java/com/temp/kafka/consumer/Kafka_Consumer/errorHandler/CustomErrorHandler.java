package com.temp.kafka.consumer.Kafka_Consumer.errorHandler;

import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConsumerRecordRecoverer;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

import java.util.List;
import java.util.zip.DataFormatException;

import static com.temp.kafka.consumer.Kafka_Consumer.constant.KafkaConstant.DLT_TOPIC;
import static com.temp.kafka.consumer.Kafka_Consumer.constant.KafkaConstant.PRO_RETRY_TOPIC;


@Configuration
//@AllArgsConstructor
public class CustomErrorHandler {
    @Autowired
    private  KafkaTemplate<Integer,String> kafkaTemplate;
    @Bean
    public DefaultErrorHandler errorHandler(){
        FixedBackOff backOff = new FixedBackOff(1_000,3);
       // DefaultErrorHandler handler = new DefaultErrorHandler(backOff);
       // DefaultErrorHandler handler = new DefaultErrorHandler(deadLetterPublishingRecoverer());
        DefaultErrorHandler handler = new DefaultErrorHandler(recordRecoverer());
        retryableErrors(handler);
        return handler;
    }

    private static void retryableErrors(DefaultErrorHandler handler) {
        var retryableErrors = List.of(RuntimeException.class);
        var nonRetryableErrors = List.of(DataFormatException.class);

        retryableErrors.forEach(handler::addRetryableExceptions);
        nonRetryableErrors.forEach(handler::addNotRetryableExceptions);
    }

    @Bean
    public DeadLetterPublishingRecoverer deadLetterPublishingRecoverer(){
        return new DeadLetterPublishingRecoverer(kafkaTemplate,(consumerRecord, e) -> {
            if (e instanceof RuntimeException){
                return new TopicPartition(PRO_RETRY_TOPIC,consumerRecord.partition());
            }else {
                return new TopicPartition(DLT_TOPIC,consumerRecord.partition());
            }
        });
    }
    @Bean
    public ConsumerRecordRecoverer recordRecoverer(){
        return (consumerRecord, e) -> {
            if (e instanceof RuntimeException){
                System.out.println("Runtime Exception ");
            }else {
                System.out.println("other exception");
            }
        };
    }
}
