package com.husen.factory.method;

/**
 * 工厂方法模式 工厂接口
 * Created by HuSen on 2018/7/5 11:33.
 */
public interface IMyMessageFactory {
    IMyMessage createMessage(String messageType);
}
