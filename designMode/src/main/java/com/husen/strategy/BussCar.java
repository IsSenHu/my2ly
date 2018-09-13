package com.husen.strategy;

/**
 * Created by HuSen on 2018/7/6 17:14.
 */
public class BussCar extends Car {
    public BussCar(String name, String color) {
        super(name, color);
    }

    @Override
    public void run() {
        System.out.println(color +" " + name  +"在缓慢的行驶。。。");
    }
}
