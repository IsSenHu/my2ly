package com.husen.strategy;

/**
 * 具体策略实现子类
 * Created by HuSen on 2018/7/6 17:12.
 */
public class SmallCar extends Car {
    public SmallCar(String name, String color) {
        super(name, color);
    }

    @Override
    public void run() {
        System.out.println(color +" " + name  +"在高速的行驶。。。");
    }
}
