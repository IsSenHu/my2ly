//package com.husen.config.amqp;
//
//import org.springframework.amqp.core.*;
//import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitAdmin;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * RabbitMQ通用基础配置
// * Created by HuSen on 2018/7/2 18:44.
// */
//@Configuration
//public class AmqpCommonConfig {
//
//    private static final String HOST = "127.0.0.1";
//    private static final int PORT = 5672;
//    private static final String USERNAME = "husen";
//    private static final String PASSWORD = "521428Slyt";
//    private static final String VIRTUAL_HOST = "/";
//
//    @Bean
//    public ConnectionFactory connectionFactory(){
//        CachingConnectionFactory factory = new CachingConnectionFactory();
//        factory.setHost(HOST);
//        factory.setPort(PORT);
//        factory.setUsername(USERNAME);
//        factory.setPassword(PASSWORD);
//        factory.setVirtualHost(VIRTUAL_HOST);
//        //发布确认
//        factory.setPublisherConfirms(true);
//        //消息返回
//        factory.setPublisherReturns(true);
//        return factory;
//    }
//
//    @Bean
//    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory){
//        return new RabbitAdmin(connectionFactory);
//    }
//
//    @Bean
//    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
//        return new RabbitTemplate(connectionFactory);
//    }
//}
