package com.husen.singleton;

/**
 * 加同步锁时，前后两次判断实例是否存在（可行）
 * 第一次判断 如果已经被实例化了 直接返回 就不用再加锁了
 * 第二次判断 为了防止多个线程都通过第一个循环 然后都可以去实例化对象
 * Created by HuSen on 2018/7/5 15:41.
 */
public class DoubleCheck {
    private static DoubleCheck instance = null;
    public static DoubleCheck getInstance(){
        if(instance == null){
            synchronized (DoubleCheck.class){
                if(instance == null){
                    instance = new DoubleCheck();
                }
            }
        }
        return instance;
    }
}
