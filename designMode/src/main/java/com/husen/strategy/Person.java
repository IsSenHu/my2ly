package com.husen.strategy;

/**
 * 应用场景类
 * Created by HuSen on 2018/7/6 17:15.
 */
public class Person {
    private String name;
    private Integer age;
    private Car car;

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public void driver(Car car){
        System.out.print(name +"  "+ age+" 岁 "+" 开着");
        car.run();
    }
}
