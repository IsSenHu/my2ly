package com.husen.test;

import com.husen.service.UserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by HuSen on 2018/7/9 17:00.
 */
public class DubboTest {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("dubbo.xml");
        context.start();
        UserService userService = (UserService) context.getBean("userService");
        userService.findAll();
    }
}
