package com.waigo.nowcoder.MyCode.Code06_360;

/**
 * author waigo
 * create 2021-08-29 15:21
 */

import java.util.Arrays;
import java.util.Scanner;

/**
 * 又到了一学期一次的大学生期末考试。但很多人期末考试的卷面成绩是不能及格的，
 * 需要靠较高的平时成绩来拖上去。平时成绩与期末考试的占比已经确定，假设平时
 * 成绩占比为p，期末考试占比为q，平时分为a，期末考试分数为b，
 * 则总成绩为(p*a+q*b)/100。（平时分与期末成绩都是整数，但总成绩可以是小数。）
 * 饶老师心肠特别好，他希望自己的学生及格率尽可能的高。但他也坚持期末
 * 考试分数更高的学生平时成绩也一定要更高。饶老师想知道在这种情况下，
 * 他们班的最大及格人数是多少（及格是指总成绩不低于60分）。
 * 输入描述
 * 第一行三个正整数n，p，q（1<=n<=200,0<=p<=100,0<=q<=100,p+q=100）
 *
 * 第二行n个正整数表示n个学生的期末考试分数（0<=分数<=100）
 *
 * 输出描述
 * 仅一行，一个正整数，表示最大及格人数。
 *
 *
 * 样例输入
 * 2 50 50
 * 50 50
 * 样例输出
 * 2
 *
 * 提示
 * 输入样例2
 *
 * 2 20 80
 *
 * 51 50
 *
 * 输出样例2
 *
 * 1
 *
 *
 *
 * 样例1中，两名同学，考试分数相同，平时分可以分配为100 100，两人都能及格。
 *
 * 样例2中，两名同学，第一位同学因为考试成绩高于第二位同学，故平时分也需要高于第二位同学；
 * 假设第一位同学平时分为100分，第二位同学最高只能得到99分的平时分，无论如何都无法及格。
 */
public class Main1 {
    /**
     *
     * @param n 几个学生
     * @param p 平时分占比
     * @param q 期末考试占比
     * @param scores 学生成绩
     * @return 最大合格人数
     */
    //贪心策略，第一名平时分为100，然后依次降低，每次降低一分
    public static int getMaxPassNum(int n,int p,int q,int[] scores){
        Arrays.sort(scores);
        int num = 0;
        int pre = 101;
        int curComm = 101;//上一个人的平时分
        for(int i = n-1;i>=0;i--){
            if(scores[i]<pre){
                curComm--;
            }
            if((p*curComm+q*scores[i])/100.0>=60.0F){
                num++;
            }
            pre = scores[i];
        }
        return num;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] firLine = sc.nextLine().split(" ");
        int n = Integer.valueOf(firLine[0]);
        int p = Integer.valueOf(firLine[1]);
        int q = Integer.valueOf(firLine[2]);
        int[] scores = new int[n];
        String[] scs = sc.nextLine().split(" ");
        for(int i = 0;i<n;i++){
            scores[i] = Integer.valueOf(scs[i]);
        }
        System.out.println(getMaxPassNum(n,p,q,scores));
    }
}
