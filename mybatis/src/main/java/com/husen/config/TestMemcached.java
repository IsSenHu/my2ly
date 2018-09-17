package com.husen.config;

import net.spy.memcached.MemcachedClient;
import net.spy.memcached.internal.GetFuture;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by HuSen on 2018/9/17 16:45.
 */
public class TestMemcached {
    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException, TimeoutException {
        MemcachedClient memcachedClient = new MemcachedClient(new InetSocketAddress("118.24.38.46", 11211));
        memcachedClient.add("test", 5000, "胡森");
        GetFuture<Object> future = memcachedClient.asyncGet("test");
        System.out.println(future.get(2, TimeUnit.SECONDS));
        memcachedClient.shutdown();
    }
}
