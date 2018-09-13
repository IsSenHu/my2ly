package com.husen.singleton;

/**
 * 多线程的情况可以用。（懒汉式，不好）
 * Created by HuSen on 2018/7/5 15:40.
 */
public class Two {
    private static Two instance = null;
    public static synchronized Two getInstance(){
        if(instance == null){
            instance = new Two();
        }
        return instance;
    }
}
