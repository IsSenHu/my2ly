package com.husen.config;

import com.google.common.cache.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by HuSen on 2018/9/17 13:31.
 */
public class TestGuavaCache {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        LoadingCache<Integer, String> strCache = CacheBuilder.newBuilder()
                //设置并发级别为8，并发级别是指可以同时写缓存的线程数
                .concurrencyLevel(8)
                //设置写缓存后8秒过期
                .expireAfterWrite(8, TimeUnit.SECONDS)
                //设置缓存容器的初始容量为10
                .initialCapacity(10)
                //设置缓存的最大容量为100，超过100之后就会按照LRU最近最少使用算法来移除缓存
                .maximumSize(100)
                //设置要统计缓存的命中率
                .recordStats()
                //设置缓存的移除通知
                .removalListener(removalNotification -> System.out.println(removalNotification.getKey() + "已经被移除，原因是:" + removalNotification.getCause()))
                //build方法可以指定CacheLoader，在缓存不存在时通过CacheLoader的实现自动加载缓存
                .build(new CacheLoader<Integer, String>() {
                    @Override
                    public String load(Integer key) {
                        System.out.println("加载数据:" + key);
                        return key + ":cache-value";
                    }
                });
        for(int i = 0; i < 20; i++) {
            String str = strCache.get(1);
            System.out.println(str);
            TimeUnit.SECONDS.sleep(1);
        }

        System.out.println(strCache.stats().toString());
    }
}
