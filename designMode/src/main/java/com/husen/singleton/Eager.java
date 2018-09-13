package com.husen.singleton;

/**
 * 饿汉式（建议使用）
 * 注解：初试化静态的instance创建一次。如果我们在Singleton类里面写一个静态的方法不需要创建实例，它仍然会早早的创建一次实例。而降低内存的使用率。
 * 缺点：没有lazy loading的效果，从而降低内存的使用率。
 * Created by HuSen on 2018/7/5 15:49.
 */
public class Eager {
    private static Eager instance = new Eager();

    public static Eager getInstance() {
        return instance;
    }
}
