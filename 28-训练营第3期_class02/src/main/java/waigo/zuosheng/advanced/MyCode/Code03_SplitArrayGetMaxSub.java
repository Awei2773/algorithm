package waigo.zuosheng.advanced.MyCode;

/**
 * author waigo
 * create 2021-05-07 11:30
 */

/*
链接：https://ac.nowcoder.com/questionTerminal/5662139e24bc4ad3a3b81fb829219ebb?answerType=1
来源：牛客网
给定一个数组arr长度为N，你可以把任意长度大于0且小于N的前缀作为左部分，剩下的 作为右部分。
但是每种划分下都有左部分的最大值和右部分的最大值，请返回最大的， 左部分最大值减去右部分最大值的绝对值。

输入描述:
第一行输入一个整数N(N<100000)
第二行输入N个整数表示arr

输出描述:
输出左部分最大值减去右部分最大值的绝对值的最大值
示例1
输入
5
3 5 1 4 2
输出
3
说明
5-2
 */
public class Code03_SplitArrayGetMaxSub {
    //左部分长度要小于N也就是说左右部分都要有值
    //因为分区找最大，所以整体最大必会在左右区间其中一个中，想要最后的差值
    //最大，那么另一个分区的最大值需要尽可能的小
    //分析情况，
    //1.整体最大的值在左半区，那么右半区在有数的情况下最小的最值就是出现在
    //右半区只有一个数且是最后一个数的情况上，因为一旦多于这个，max只会变大不会变小
    //2.整体最大的值在右半区,同理
    //综上所述：找到整体最大值，然后分两种情况讨论一种是这个数在左半区，一种是在右半区
    //如果这个AllMax就是在0或者length-1位置也不用慌，因为答案不可能出现在以他们自己分区的情况
    public static int getMaxSub(int N,int[] arr){
        int all_max = Integer.MIN_VALUE;
        for(int i = 0;i<N;i++)
            all_max = Math.max(all_max,arr[i]);
        return Math.abs(all_max-(arr[0]<arr[N-1]?arr[0]:arr[N-1]));
    }

    public static void main(String[] args) {
        int[] a = {3,5,1,4,2};
        System.out.println(getMaxSub(a.length,a));
    }
}
