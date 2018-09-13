//package com.husen.config.amqp;
//
//import org.springframework.amqp.core.*;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * RabbitMQ Topic模式配置
// * Created by HuSen on 2018/7/3 9:58.
// */
//@Configuration
//public class TopicAmqpConfig {
//    private static final String TOPIC_QUEUE = "topic.queue";
//    private static final String TOPIC_EXCHANGE = "topic.exchange";
//    private static final String TOPIC_ROUTE_KEY = "ltt.hs.*";
//
//    @Bean
//    public Queue queue(){
//        return new Queue(TOPIC_QUEUE);
//    }
//
//    @Bean
//    public TopicExchange topicExchange(){
//        return new TopicExchange(TOPIC_EXCHANGE);
//    }
//
//    @Bean
//    public Binding binding(){
//        return BindingBuilder.bind(queue()).to(topicExchange()).with(TOPIC_ROUTE_KEY);
//    }
//
//    @Bean
//    public MessageListener messageListener() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
//        return (MessageListener) Class.forName("com.husen.service.rabbit.TopicService").newInstance();
//    }
//
//    @Bean
//    public SimpleMessageListenerContainer simpleMessageListenerContainer(ConnectionFactory connectionFactory) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
//        container.setQueues(queue());
//        container.setMessageListener(messageListener());
//        return container;
//    }
//}
