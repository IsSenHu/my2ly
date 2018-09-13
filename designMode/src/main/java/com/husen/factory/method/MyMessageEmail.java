package com.husen.factory.method;

/**
 * 工厂方法模式 email产品
 * Created by HuSen on 2018/7/5 14:10.
 */
public class MyMessageEmail extends MyAbstractMessage {
    @Override
    public void sendMessage() throws Exception {
        if (null == getMessageParam() || null == getMessageParam().get("EMAIL")
                || "".equals(getMessageParam().get("EMAIL"))) {
            throw new Exception("发送短信,需要传入EMAIL参数");
        }
        System.out.println("我是邮件，发送通知给" + getMessageParam().get("EMAIL"));
    }
}
