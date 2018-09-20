package com.husen.test;

import com.husen.service.UserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by HuSen on 2018/7/9 17:00.
 */
public class DubboTest {
    public static void main(String[] args) {
//        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("dubbo.xml");
//        context.start();
//        UserService userService = (UserService) context.getBean("userService");
//        userService.findAll();
        //[a-zA-Z0-9_]{6,15}
        String regex = "^\\s*$|^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z]+$)(?![a-z0-9]+$)(?![a-z]+$)(?![0-9]+$)[a-zA-Z0-9]{8,30}$";
        boolean b = "0Aa12345".matches(regex);
        System.out.println(b);
    }
}
