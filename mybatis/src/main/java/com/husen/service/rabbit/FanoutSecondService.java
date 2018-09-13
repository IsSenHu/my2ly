package com.husen.service.rabbit;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * Created by HuSen on 2018/7/3 10:45.
 */
public class FanoutSecondService implements MessageListener {
    @Override
    public void onMessage(Message message) {
        System.out.println("######FanoutSecondService:" + message);
    }
}
