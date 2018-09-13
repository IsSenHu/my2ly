package com.husen.sort;

import java.util.Arrays;

/**
 * Created by HuSen on 2018/8/23 11:33.
 */
public class SortMain {

    public static int partition(int[] array, int lo, int hi) {
        //TODO 三数取中

        //用来记录基准值
        int key = array[lo];

        while (lo < hi) {
            while (array[hi] >= key && hi > lo) {
                hi--;
            }
            array[lo] = array[hi];
            while (array[lo] <= key && hi > lo) {
                lo++;
            }
            array[hi] = array[lo];
        }

        //最后确定了基准值的位置并且赋值给这个位置
        array[hi] = key;
        return hi;
    }

    public static void sort(int[] array, int lo, int hi) {
        if(lo >= hi) {
            return;
        }
        int index = partition(array, lo, hi);
        sort(array, lo, index - 1);
        sort(array, index + 1, hi);
    }

    public static void main(String[] args) {
        int[] array = new int[]{3, 1, 2, 9, 6, 7};
        sort(array, 0, array.length - 1);
        System.out.println(Arrays.toString(array));
    }

}
