package com.husen.service.rabbit;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;

/**
 * Created by HuSen on 2018/7/3 11:19.
 */
public class DirectService implements ChannelAwareMessageListener {
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        System.out.println("######DirectService:" + new String(message.getBody(), "UTF-8"));
        System.out.println("######channel:" + channel.getChannelNumber());
    }
}
