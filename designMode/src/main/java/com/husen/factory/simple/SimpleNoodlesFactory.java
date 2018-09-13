package com.husen.factory.simple;

/**
 * 它是一个具体的类，非接口 抽象类。有一个重要的create()方法，利用if或者 switch创建产品并返回。
 * create()方法通常是静态的，所以也称之为静态工厂。
 *
 * 缺点
 * 1 扩展性差（我想增加一种面条，除了新增一个面条产品类，还需要修改工厂类方法）
 * 2 不同的产品需要不同额外参数的时候 不支持。
 * Created by HuSen on 2018/7/5 11:27.
 */
public class SimpleNoodlesFactory {
    public static final int TYPE_LZ = 1;//兰州拉面
    public static final int TYPE_PM = 2;//泡面
    public static final int TYPE_GK = 3;//干扣面

    public static INoodles createNoodles(int type) {
        switch (type) {
            case TYPE_LZ:
                return new LzNoodles();
            case TYPE_PM:
                return new PaoNoodles();
            case TYPE_GK:
            default:
                return new GankouNoodles();
        }
    }

    public static void main(String[] args) {
        /**
         * 简单工厂模式
         * */
        SimpleNoodlesFactory.createNoodles(TYPE_LZ).desc();
    }
}
