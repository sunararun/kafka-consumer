package com.temp.kafka.consumer.Kafka_Consumer.listener;

import com.temp.kafka.consumer.Kafka_Consumer.errorHandler.CustomErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.kafka.autoconfigure.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

@Configuration
//@AllArgsConstructor
public class CustomKafkaListener {
    @Autowired
    private KafkaProperties kafkaProperties;
    @Autowired
    private CustomErrorHandler errorHandler;

    @Bean
    public ConsumerFactory<Integer,String> createConsumerFactory(){
        return new DefaultKafkaConsumerFactory<>(kafkaProperties.buildConsumerProperties());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Integer,String> configurerFactory(){
        var factory = new ConcurrentKafkaListenerContainerFactory<Integer,String>();
        factory.setConsumerFactory(createConsumerFactory());
        factory.setCommonErrorHandler(errorHandler.errorHandler());
        return factory;

    }
}
