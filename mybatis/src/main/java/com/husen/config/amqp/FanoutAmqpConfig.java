//package com.husen.config.amqp;
//
//import com.husen.service.rabbit.FanoutFirstService;
//import com.husen.service.rabbit.FanoutSecondService;
//import org.springframework.amqp.core.*;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * RabbitMQ fanout模式配置
// * Created by HuSen on 2018/7/3 9:59.
// */
//@Configuration
//public class FanoutAmqpConfig {
//    private static final String FIRST_FANOUT_QUEUE = "first.fanout.queue";
//    private static final String SECOND_FANOUT_QUQUE = "second.fanout.queue";
//    private static final String FANOUT_EXCHANGE = "fanout.exchange";
//
//    @Bean
//    public Queue first(){
//        return new Queue(FIRST_FANOUT_QUEUE);
//    }
//
//    @Bean
//    public Queue second(){
//        return new Queue(SECOND_FANOUT_QUQUE);
//    }
//
//    @Bean
//    public FanoutExchange fanoutExchange(){
//        return new FanoutExchange(FANOUT_EXCHANGE);
//    }
//
//    @Bean
//    public Binding bindingFirst(){
//        return BindingBuilder.bind(first()).to(fanoutExchange());
//    }
//
//    @Bean
//    public Binding bindingSecond(){
//        return BindingBuilder.bind(second()).to(fanoutExchange());
//    }
//
//    @Bean
//    public MessageListener messageFirstListener() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
//        return (MessageListener) Class.forName("com.husen.service.rabbit.FanoutFirstService").newInstance();
//    }
//
//    @Bean
//    public MessageListener messageSecondListener() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
//        return (MessageListener) Class.forName("com.husen.service.rabbit.FanoutSecondService").newInstance();
//    }
//
//    @Bean
//    public SimpleMessageListenerContainer simpleMessageListenerFirstContainer(ConnectionFactory connectionFactory) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
//        container.setQueues(first());
//        container.setMessageListener(messageFirstListener());
//        return container;
//    }
//
//    @Bean
//    public SimpleMessageListenerContainer simpleMessageListenerSecondContainer(ConnectionFactory connectionFactory){
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
//        container.setQueues(second());
//        container.setMessageListener(new FanoutSecondService());
//        return container;
//    }
//}
