package com.husen.proxy.proxy;

/**
 * Created by HuSen on 2018/7/5 10:30.
 */
public class UserDao implements IUserDao {
    @Override
    public void save() {
        System.out.println("----已经保存数据!----");
    }
}
