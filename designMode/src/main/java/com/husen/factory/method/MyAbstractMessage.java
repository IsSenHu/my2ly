package com.husen.factory.method;

import java.util.Map;

/**
 * 工厂方法模式 虚拟产品类
 * Created by HuSen on 2018/7/5 14:09.
 */
public abstract class MyAbstractMessage implements IMyMessage {
    /**
     * 这里可以理解为生产产品所需要的原材料库。最好是个自定义的对象，这里为了不引起误解使用Map。
     */
    private Map<String, Object> messageParam;

    public Map<String, Object> getMessageParam() {
        return messageParam;
    }

    public void setMessageParam(Map<String, Object> messageParam) {
        this.messageParam = messageParam;
    }
}
