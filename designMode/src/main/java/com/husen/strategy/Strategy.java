package com.husen.strategy;

/**
 * 运行环境类
 *
 * 在软件开发中常常遇到这种情况，实现某一个功能有多种算法或者策略，我们可以根据应用场景的不同选择不同的算法或者策略来完成该功能。
 * 把一个类(A)中经常改变或者将来可能改变的部分提取出来，作为一个接口(B)，然后在类(A)中包含这个接口(B)，
 * 这样类(A)的实例在运行时就可以随意调用实现了这个接口的类(C)的行为。
 * 比如定义一系列的算法,把每一个算法封装起来, 并且使它们可相互替换，使得算法可独立于使用它的客户而变化。这就是策略模式。
 *
 * 优点:
* 　　　　1、可以动态的改变对象的行为
 * 缺点:
* 　　　　1、客户端必须知道所有的策略类，并自行决定使用哪一个策略类
* 　　　　2、策略模式将造成产生很多策略类
 * Created by HuSen on 2018/7/6 17:16.
 */
public class Strategy {
    public static void main(String[] args) {
        Car smallCar = new SmallCar("路虎","黑色");
        Car bussCar = new BussCar("公交车","白色");
        Person p1 = new Person("小明", 20);
        p1.driver(smallCar);
        p1.driver(bussCar);
    }
}
