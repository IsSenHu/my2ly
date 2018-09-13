package com.husen.service.rabbit;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * Created by HuSen on 2018/7/3 13:03.
 */
public class TopicService implements MessageListener {
    @Override
    public void onMessage(Message message) {
        System.out.println("######TopicService:" + message);
    }
}
