package com.husen.factory.method;

import java.util.HashMap;
import java.util.Map;

/**
 * 工厂方法模式 工厂实现
 * 提供一个用于创建对象的接口(工厂接口)，让其实现类(工厂实现类)决定实例化哪一个类(产品类)，并且由该实现类创建对应类的实例。
 * 可以一定程度上解耦，消费者和产品实现类隔离开，只依赖产品接口(抽象产品)，产品实现类如何改动与消费者完全无关。
 * 可以一定程度增加扩展性，若增加一个产品实现，只需要实现产品接口，修改工厂创建产品的方法，消费者可以无感知（若消费者不关心具体产品是什么的情况）。
 * 可以一定程度增加代码的封装性、可读性。清楚的代码结构，对于消费者来说很少的代码量就可以完成很多工作。
 * 另外，抽象工厂才是实际意义的工厂模式，工厂方法只是抽象工厂的一个比较常见的情况。
 *
 * 消费者不关心它所要创建对象的类(产品类)的时候。
 * 消费者知道它所要创建对象的类(产品类)，但不关心如何创建的时候。
 *
 * 提供一个产品类的接口。产品类均要实现这个接口(也可以是abstract类，即抽象产品)。
 * 提供一个工厂类的接口。工厂类均要实现这个接口(即抽象工厂)。
 * 由工厂实现类创建产品类的实例。工厂实现类应有一个方法，用来实例化产品类。
 * Created by HuSen on 2018/7/5 11:34.
 */
public class MyMessageFactory implements IMyMessageFactory {
    @Override
    public IMyMessage createMessage(String messageType) {
        // 这里的方式是：消费者知道自己想要什么产品；若生产何种产品完全由工厂决定，则这里不应该传入控制生产的参数。
        MyAbstractMessage myMessage;
        Map<String, Object> messageParam = new HashMap<String, Object>();
        // 根据某些条件去选择究竟创建哪一个具体的实现对象，条件可以传入的，也可以从其它途径获取。
        if ("SMS".equals(messageType)) {
            myMessage = new MyMessageSms();
            messageParam.put("PHONENUM", "123456789");
        } else if ("OA".equals(messageType)) {
                myMessage = new MyMessageOaTodo();
                messageParam.put("OAUSERNAME", "testUser");
        } else if ("EMAIL".equals(messageType)) {
                myMessage = new MyMessageEmail();
                messageParam.put("EMAIL", "test@test.com");
        } else {
            myMessage = new MyMessageEmail();
            messageParam.put("EMAIL", "test@test.com");
        }
        myMessage.setMessageParam(messageParam);
        return myMessage;
    }

    public static void main(String[] args) throws Exception {
        IMyMessageFactory factory = new MyMessageFactory();
        IMyMessage myMessage = factory.createMessage("SMS");
        myMessage.sendMessage();
        factory.createMessage("OA").sendMessage();
    }
}
