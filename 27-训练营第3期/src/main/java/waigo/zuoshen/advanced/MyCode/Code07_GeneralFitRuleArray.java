package waigo.zuoshen.advanced.MyCode;

import java.util.Arrays;

/**
 * author waigo
 * create 2021-05-06 12:44
 */
/*
给定一个正整数M，请构造出一个长度为M的数组arr，要求
对任意的i、j、k三个位置，如果i<j<k，都有arr[i] + arr[k] != 2*arr[j]
返回构造出的arr


思路分析：
注意这里的2倍关系，可以知道想要恒满足这个条件arr[i]+arr[k]必为奇数
假设将数组分为左边为奇数，右边为偶数的两部分，那么分情况讨论
1.i,k都在奇半区，只要这个奇半区能够使得arr[i] + arr[k] != 2*arr[j]成立就好，无法恒成立
2.i,k都在偶半区，只要这个偶半区能够使得arr[i] + arr[k] != 2*arr[j]成立就好，无法恒成立
3.i,k一个在奇半区，一个在偶半区，这是能够恒成立的，因为arr[i]+arr[k]为奇数


所以问题就变成了，如何整出左右两个满足条件的奇偶分区来
数组1：[1]，一个必定符合要求
数组2：[1,3]，两个也符合要求
对于数组2，能否靠他整出一些长点合法的来？

证明一个合法数组arr进行奇偶变换后依然合法
//奇变换证明
在arr中对任意的i、j、k三个位置，如果i<j<k，都有arr[i] + arr[k] != 2*arr[j]，现在arr的数都进行了奇变换
arr[i] + arr[k] = (2*arr[i]-1)+(2*arr[k]-1)=2*(arr[i]+arr[k])-2 != 2*(2*arr[j])-2 != 2*(2*arr[j]-1)
所以奇变换成立，同理可证偶变换也成立，这下舒服了，我只要长度为a的一个合法的数组i1，通过奇偶变换能整出长度为2a的合法数组i2

用原来的数为基底去进行奇偶变换整出新的数据来[1*2-1,3*2-1],[1*2,3*2]->[1,5,2,6]合法
....
一生二，二生三，三生万物，通过二分找生成的合法数组，找回到1，此题可解,时间复杂度为O(logN)。
*/
public class Code07_GeneralFitRuleArray {
    //时间复杂度分析，外层每次乘以2逼近，总共有logN次
    //内层时间频度为seedLen的长度，也就是说这里的时间频度T(n)=一个q=2的等比数列的前logN项和
    //T(n)=n-1,所以时间复杂度为O(N),空间复杂度为O(1)
    public static int[] generalArray(int M){
        int[] res = new int[M];
        res[0] = 1;
        for(int seedLen = 1;seedLen<M;seedLen<<=1){//基底每次两倍跳跃的，1->2->4这样
            int write = seedLen;
            for(int x = 0;x<seedLen;x++){
                if(write<M)
                    res[write++] = res[x]<<1;//可能超出界限了，不用填了
                res[x] = res[x]*2-1;
            }
            if(seedLen>=((M+1)>>1)) break;//防止M靠近Integer.MAX_VALUE,乘二就溢出了
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(generalArray(15)));
    }
}
