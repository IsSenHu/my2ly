package com.husen.amqp;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by HuSen on 2018/7/3 10:05.
 */
public class Test {
    public static void main(String[] args) throws URISyntaxException {
        URI uri = new URI("amqp://husen:521428Slyt@localhost:5672");
        ConnectionFactory connectionFactory = new CachingConnectionFactory(uri);
        AmqpAdmin admin = new RabbitAdmin(connectionFactory);
        admin.declareQueue(new Queue("spring"));
        AmqpTemplate template = new RabbitTemplate(connectionFactory);
        template.convertAndSend("spring", "husen");
        String husen = (String) template.receiveAndConvert("spring");
        System.out.println(husen);
    }
}
