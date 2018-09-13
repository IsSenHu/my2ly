package com.husen.proxy.proxy;

/**
 * 静态代理
 * Created by HuSen on 2018/7/5 10:31.
 */
public class UserDaoProxy implements IUserDao {

    /**
     * 接收保存目标对象
     */
    private IUserDao target;

    public UserDaoProxy(IUserDao target) {
        this.target = target;
    }

    @Override
    public void save() {
        System.out.println("开始事务...");
        target.save();
        System.out.println("提交事务...");
    }

    public static void main(String[] args) {
        UserDaoProxy proxy = new UserDaoProxy(new UserDao());
        proxy.save();
    }
}
