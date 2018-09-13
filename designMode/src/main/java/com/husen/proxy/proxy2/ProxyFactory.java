package com.husen.proxy.proxy2;

import com.husen.proxy.proxy.IUserDao;
import com.husen.proxy.proxy.UserDao;
import java.lang.reflect.Proxy;

/**
 * 创建动态代理对象
 * 动态代理不需要实现接口，但是需要指定接口类型
 * 代理对象不需要实现接口,但是目标对象一定要实现接口,否则不能用动态代理
 * 动态代理有以下特点:
 * 1.代理对象,不需要实现接口
 * 2.代理对象的生成,是利用JDK的API,动态的在内存中构建代理对象(需要我们指定创建代理对象/目标对象实现的接口的类型)
 * 3.动态代理也叫做:JDK代理,接口代理
 * Created by HuSen on 2018/7/5 10:48.
 */
public class ProxyFactory {
    /**
     * 维护一个目标对象
     */
    private Object object;

    public ProxyFactory(Object object) {
        this.object = object;
    }

    /**
     * 给目标对象生成代理对象
     * @return 生成的代理对象
     */
    public Object getProxyInstance(){
        return Proxy.newProxyInstance(object.getClass().getClassLoader(), object.getClass().getInterfaces(), (proxy, method, args) -> {
            System.out.println("开始事务2");
            Object returnObj = method.invoke(object, args);
            System.out.println("提交事务2");
            return returnObj;
        });
    }

    public static void main(String[] args) {
        IUserDao userDao = new UserDao();
        System.out.println(userDao.getClass());
        ProxyFactory proxyFactory = new ProxyFactory(userDao);
        IUserDao proxy = (IUserDao) proxyFactory.getProxyInstance();
        System.out.println(proxy.getClass());
        proxy.save();
    }
}
