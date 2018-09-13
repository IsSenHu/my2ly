//package com.husen.config.kafka;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.core.DefaultKafkaProducerFactory;
//import org.springframework.kafka.core.KafkaTemplate;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * producer配置
// * Created by HuSen on 2018/7/31 10:26.
// */
//@Configuration
//public class ProducerConfig {
//    private static final Map<String, Object> producerProperties = new HashMap<>(10);
//    private static final String DEFAULT_TOPIC = "test";
//
//    static {
//        producerProperties.put("bootstrap.servers", "192.168.162.128:9092");
//        producerProperties.put("retries", 10);
//        producerProperties.put("batch.size", 1638);
//        producerProperties.put("linger.ms", 1);
//        producerProperties.put("buffer.memory", 33554432);
//        producerProperties.put("acks", "all");
//        producerProperties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//        producerProperties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//    }
//
//    @Bean
//    public DefaultKafkaProducerFactory<String, Object> producerFactory() {
//        return new DefaultKafkaProducerFactory<>(producerProperties);
//    }
//
//    @Bean
//    public KafkaTemplate<String, Object> kafkaTemplate(DefaultKafkaProducerFactory<String, Object> defaultKafkaProducerFactory) {
//        KafkaTemplate<String, Object> kafkaTemplate = new KafkaTemplate<>(defaultKafkaProducerFactory);
//        kafkaTemplate.setDefaultTopic(DEFAULT_TOPIC);
//        return kafkaTemplate;
//    }
//}
