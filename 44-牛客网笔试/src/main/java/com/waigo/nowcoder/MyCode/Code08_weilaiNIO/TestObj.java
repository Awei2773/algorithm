package com.waigo.nowcoder.MyCode.Code08_weilaiNIO;

import java.io.IOException;

/**
 * author waigo
 * create 2021-09-01 11:05
 */
public class TestObj{
    public static void main(String[] args) {
        Integer i = 129;//底层调用的是Integer.valueOf
        //valueOf()如果超过了缓存范围就是new出来的
        System.out.println(Math.floor(-8.1));//向下取整，在数值轴上往左看
        System.out.println(Math.ceil(-8.1));//向上取整，往右看
        System.out.println(Math.round(-8.1));//四舍五入
    }

}
