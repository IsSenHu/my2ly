package com.husen.singleton;

/**
 * 只适合单线程环境（不好）
 * Created by HuSen on 2018/7/5 15:38.
 */
public class One {
    private static One instance = null;
    public static One getInstance(){
        if(instance == null){
            instance = new One();
        }
        return instance;
    }
}
