package com.waigo.nowcoder.MyCode.Code02_meituan;

/**
 * author waigo
 * create 2021-08-15 11:25
 */
public class Main4 {
    public static void main(String[] args) {
//        System.out.println(0);
        int a = 64231;
        float NL1 = 0.02f;
        float NL2 = 0.04f;
        float NL1_2 = 0.06f;

        float ML1 = 0.04f;
        float ML2 = 0.08f;
        float ML1_2 = 0.12f;
        System.out.println(a*ML1_2);
    }
}

/**
 * 年薪和月薪两种
 * 1.年薪员工+普通错误 --> 扣2%
 * 2.年薪员工+严重错误 --> 扣4%
 * 3.月薪员工+普通错误 --> 扣4%
 * 4.月薪员工+严重错误 --> 扣8%
 * 犯错类型（无错误：L0，普通错误：L1，严重错误L2，普通错误以及严重错误:L1，L2）、
 * 输出扣款数目，不保留小数位数。
 *
 * 年薪制（N）/月薪制（M）
 * 依次输入员工工资类型、薪资数，犯错类型：
 *
 * N
 * 250000  -->5000(N+L1)
 * L1
 * 输入输出的数字都是整数
 *
 * N
 * 250000  -->10000(N+L2)
 * L2
 *
 * N
 * 250000  -->15000 (N+L1,L2)
 * L1
 *
 *
 */