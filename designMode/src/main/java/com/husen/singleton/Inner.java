package com.husen.singleton;

/**
 * 静态内部类。（建议使用）
 * 定义一个私有的内部类，在第一次用这个嵌套类时，会创建一个实例。
 * 而类型为SingletonHolder的类，只有在Singleton.getInstance()中调用，不调用Singleton.getInstance()就不会创建实例。
 * 由于私有的属性，他人无法使用SingleHolder。
 *
 * 优点：达到了lazy loading的效果，即按需创建实例。
 * Created by HuSen on 2018/7/5 15:51.
 */
public class Inner {
    private static class SingletonHolder{
        private static final Inner instance = new Inner();
    }

    public static Inner getInstance(){
        return SingletonHolder.instance;
    }
}
