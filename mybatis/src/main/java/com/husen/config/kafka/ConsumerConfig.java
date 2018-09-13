//package com.husen.config.kafka;
//
//import com.husen.service.kafka.ConsumerListener;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//import org.springframework.kafka.listener.KafkaMessageListenerContainer;
//import org.springframework.kafka.listener.config.ContainerProperties;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Created by HuSen on 2018/7/31 10:39.
// */
//@Configuration
//public class ConsumerConfig {
//    private static final Map<String, Object> consumerProperties = new HashMap<>(10);
//
//    static {
//        consumerProperties.put("bootstrap.servers", "192.168.162.129:9092");
//        consumerProperties.put("group.id", "order-beta");
//        consumerProperties.put("enable.auto.commit", true);
//        consumerProperties.put("session.timeout.ms", 15000);
//    }
//
//
//
//    @Bean
//    public ConsumerListener messageListener() {
//        return new ConsumerListener();
//    }
//
//    @Bean
//    public ContainerProperties containerProperties(ConsumerListener messageListener) {
//        ContainerProperties containerProperties = new ContainerProperties("test");
//        containerProperties.setMessageListener(messageListener);
//        return containerProperties;
//    }
//
//    @Bean
//    public KafkaMessageListenerContainer<String, Object> listenerContainer(ContainerProperties containerProperties) {
//        return new KafkaMessageListenerContainer<>(new DefaultKafkaConsumerFactory<>(consumerProperties, null, null), containerProperties);
//    }
//}
