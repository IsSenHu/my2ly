package com.husen.factory.abstractFactory;

/**
 * 抽象工厂模式
 * 抽象工厂模式除了具有工厂方法模式的优点外，最主要的优点就是可以在类的内部对产品族进行约束。
 * 所谓的产品族，一般或多或少的都存在一定的关联，抽象工厂模式就可以在类内部对产品族的关联关系进行定义和描述，而不必专门引入一个新的类来进行管理。
 * 缺点:
 *      产品族的扩展将是一件十分费力的事情，假如产品族中需要增加一个新的产品，则几乎所有的工厂类都需要进行修改
 * 适用场景:
 *      当需要创建的对象是一系列相互关联或相互依赖的产品族时，便可以使用抽象工厂模式。
 * Created by HuSen on 2018/7/5 14:32.
 */
public class Client {
    public static void main(String[] args) {
        IFactory iFactory = new Factory();
        iFactory.createProduct1().show();
        iFactory.createProduct2().show();
    }
}

interface IProduct1 {
    public void show();
}
interface IProduct2 {
    public void show();
}

class Product1 implements IProduct1 {
    public void show() {
        System.out.println("这是1型产品");
    }
}
class Product2 implements IProduct2 {
    public void show() {
        System.out.println("这是2型产品");
    }
}

interface IFactory {
    public IProduct1 createProduct1();
    public IProduct2 createProduct2();
}
class Factory implements IFactory{
    public IProduct1 createProduct1() {
        return new Product1();
    }
    public IProduct2 createProduct2() {
        return new Product2();
    }
}
