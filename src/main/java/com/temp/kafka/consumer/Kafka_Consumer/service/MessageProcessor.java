package com.temp.kafka.consumer.Kafka_Consumer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.temp.kafka.consumer.Kafka_Consumer.pojo.Product;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
@AllArgsConstructor
public class MessageProcessor {
    private final ObjectMapper objectMapper;
    public void processor(ConsumerRecord<Integer, String> record) throws JsonProcessingException {
        Product product = objectMapper.readValue(record.value(), Product.class);
        switch (product.productStatus()){
            case AVAILABLE -> {
                saveProduct(product);
            }
            case NOT_AVAILABLE -> {
                validateProduct(product);
                saveProduct(product);
            }
        }

    }

    private void validateProduct(Product product) {
        System.out.println("validate product " + product);
    }

    private void saveProduct(Product product) {
        System.out.println("product saved " + product);
    }
}
