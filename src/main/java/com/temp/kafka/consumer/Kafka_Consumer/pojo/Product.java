package com.temp.kafka.consumer.Kafka_Consumer.pojo;


import com.temp.kafka.consumer.Kafka_Consumer.constant.ProductStatus;

public record Product(Integer productId, String brand, String productName, ProductStatus productStatus) {
}
