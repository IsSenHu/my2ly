package com.husen.proxy.cglib;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Cglib子类代理工厂
 * 对UserDao在内存中动态构建一个子类对象
 * 在Spring的AOP编程中:
 * 如果加入容器的目标对象有实现接口,用JDK代理
 * 如果目标对象没有实现接口,用Cglib代理
 * Created by HuSen on 2018/7/5 11:09.
 */
public class ProxyFactory implements MethodInterceptor {

    /**
     * 维护的目标对象
     */
    private Object object;

    public ProxyFactory(Object object) {
        this.object = object;
    }

    /**
     * 给目标对象创建一个代理对象
     * @return 代理对象
     */
    public Object getProxyObject(){
        //1，工具类
        Enhancer en = new Enhancer();
        //2，设置父类
        en.setSuperclass(object.getClass());
        //3，设置回调函数
        en.setCallback(this);
        //4，创建子类，代理对象
        return en.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("开始事务...");
        //执行目标对象的方法
        Object returnObj = method.invoke(object, objects);
        System.out.println("提交事务...");
        return returnObj;
    }

    public static void main(String[] args) {
        UserDao proxy = (UserDao) new ProxyFactory(new UserDao()).getProxyObject();
        System.out.println(proxy.getClass());
        proxy.save();
    }
}
