package com.husen.strategy;

/**
 * 具体策略父类
 * 每个车都具有的相同的属性和行为
 * Created by HuSen on 2018/7/6 17:11.
 */
public class Car implements CarFunction{
    protected String name;
    protected String color;
    @Override
    public void run() {
        System.out.println(color +" " + name  +"在行驶。。。");
    }

    public Car(String name, String color) {
        this.name = name;
        this.color = color;
    }
}
