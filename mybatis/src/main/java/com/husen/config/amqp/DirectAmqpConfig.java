//package com.husen.config.amqp;
//
//import org.springframework.amqp.core.*;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
//import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * RabbitMQ direct模式配置
// * Created by HuSen on 2018/7/3 9:58.
// */
//@Configuration
//public class DirectAmqpConfig {
//    private static final String DIRECT_QUEUE = "direct.queue";
//    private static final String DIRECT_EXCHANGE = "direct.exchange";
//    private static final String ROUTE_KEY = "HuSenDeNiuNiu";
//    @Bean
//    public Queue queue(){
//        return new Queue(DIRECT_QUEUE);
//    }
//
//    @Bean
//    public DirectExchange directExchange(){
//        return new DirectExchange(DIRECT_EXCHANGE);
//    }
//
//    @Bean
//    public Binding binding(){
//        return BindingBuilder.bind(queue()).to(directExchange()).with(ROUTE_KEY);
//    }
//
//    @Bean
//    public MessageListener messageListener() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
//        return (MessageListener) Class.forName("com.husen.service.rabbit.DirectService").newInstance();
//    }
//
//    @Bean
//    public SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
//        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer(connectionFactory);
//        simpleMessageListenerContainer.setQueues(queue());
//        simpleMessageListenerContainer.setMessageListener(messageListener());
//        return simpleMessageListenerContainer;
//    }
//}
