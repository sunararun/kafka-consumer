package com.temp.kafka.consumer.Kafka_Consumer.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.temp.kafka.consumer.Kafka_Consumer.pojo.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;

import java.util.concurrent.ExecutionException;

import static com.temp.kafka.consumer.Kafka_Consumer.constant.KafkaConstant.NEW_TOPIC;
import static com.temp.kafka.consumer.Kafka_Consumer.constant.ProductStatus.AVAILABLE;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EmbeddedKafka(topics = NEW_TOPIC,ports = 9092)
class CustomKafkaListenerTest {
    @Autowired
    private KafkaTemplate<Integer,String> kafkaTemplate;
    @Autowired
    private KafkaListenerEndpointRegistry endpointRegistry;
    @Autowired
    private EmbeddedKafkaBroker kafkaBroker;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        for (MessageListenerContainer container: endpointRegistry.getAllListenerContainers()){
            ContainerTestUtils.waitForAssignment(container,kafkaBroker.getPartitionsPerTopic());
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void consumeRecord() throws JsonProcessingException, ExecutionException, InterruptedException {
        String jsonString = objectMapper.writeValueAsString(new Product(768, "SAMSUNG"
                , "TABLET", AVAILABLE));
        var sendResult = kafkaTemplate.send(NEW_TOPIC, jsonString).get();
        System.out.println("value is **************** "+sendResult.getProducerRecord().value());

    }
}