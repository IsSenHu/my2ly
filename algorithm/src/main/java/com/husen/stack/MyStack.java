package com.husen.stack;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by HuSen on 2018/7/10 15:15.
 */
public class MyStack {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        Stack<String> stack = new Stack<>();
        long start1  = System.currentTimeMillis();
        for(int i = 0; i < 10000000; i++){
            stack.push("husen" + i);
        }
        int size = stack.size();
//        System.out.println(size);
        for(int i = 0; i < size; i++){
            stack.get(i);
        }
        System.out.println(stack.size());
        long end1 = System.currentTimeMillis();
        System.out.println("cost time:" + (end1 - start1));

        long start2 = System.currentTimeMillis();
        for(int i = 0; i < 10000000; i++){
            list.add("husen" + i);
        }

        int size2 = list.size();
        for(int i = 0; i < size2; i++){
            list.get(i);
        }
        System.out.println(size2);
        long end2 = System.currentTimeMillis();
        System.out.println("cost time:" + (end2 - start2));
    }
}