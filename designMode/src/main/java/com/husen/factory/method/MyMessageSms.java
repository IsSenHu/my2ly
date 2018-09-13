package com.husen.factory.method;

/**
 * 工厂方法模式 sms产品
 * Created by HuSen on 2018/7/5 14:12.
 */
public class MyMessageSms extends MyAbstractMessage {
    @Override
    public void sendMessage() throws Exception {
        if (null == getMessageParam()
                || null == getMessageParam().get("PHONENUM")
                || "".equals(getMessageParam().get("PHONENUM"))) {
            throw new Exception("发送短信,需要传入PHONENUM参数");
        }
        System.out.println("我是短信，发送通知给" + getMessageParam().get("PHONENUM"));
    }
}
