package com.husen.factory.method;

/**
 * 工厂方法模式 oa待办产品
 * Created by HuSen on 2018/7/5 14:11.
 */
public class MyMessageOaTodo extends MyAbstractMessage {
    @Override
    public void sendMessage() throws Exception {
        if (null == getMessageParam()
                || null == getMessageParam().get("OAUSERNAME")
                || "".equals(getMessageParam().get("OAUSERNAME"))) {
            throw new Exception("发送OA待办,需要传入OAUSERNAME参数");
        }
        System.out.println("我是OA待办，发送通知给" + getMessageParam().get("OAUSERNAME"));
    }
}
