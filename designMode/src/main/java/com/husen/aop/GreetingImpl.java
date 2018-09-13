package com.husen.aop;

/**
 * Created by HuSen on 2018/7/9 14:33.
 */
public class GreetingImpl implements Greeting{
    @Override
    public void sayHello(String name) {
        System.out.println("Hello:" + name);
        throw new RuntimeException();
    }
}
