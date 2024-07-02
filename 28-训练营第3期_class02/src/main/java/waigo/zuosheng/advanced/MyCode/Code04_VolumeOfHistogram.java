package waigo.zuosheng.advanced.MyCode;

/**
 * author waigo
 * create 2021-05-08 11:45
 */
/*
https://leetcode-cn.com/problems/volume-of-histogram-lcci/
Imagine a histogram (bar graph). Design an algorithm to compute
the volume of water it could hold if someone poured water across the top.
You can assume that each histogram bar has width 1.


The above elevation map is represented by array [0,1,0,2,1,0,1,3,2,1,2,1].
In this case, 6 units of water (blue section) are being trapped.

就是给你一个一维数组，这个一维数组里每个值可以看做是一个矩形的高度，
顺着数组下标递增的方向摆放这些矩形，会形成一个直方图，现在从上往下倒水，直方图的凹槽会
接住落下来的水，问你能接多少?

思路分析：
方法一:使用预处理结构解决
直方图能接的水的总数肯定是等于所有位置能接的水的总和这是毋庸置疑的，
那么只要能够计算出一个位置能够接收多少水这题就能解了。
一个位置能存多少水肯定得看从它这里看出去这个水平面的高度，就是左边最高点和右边最高点中低的那个
和自己这里高度的差值就是这个位置能够存水的高度。

1.如果这个差值小于等于0说明这个位置比起左右的栅栏还要高，肯定拦不住水，存水为0.
2.大于0，则差值就是这个位置存下来的水

从某个位置往前往后看找到最大值，如果直接遍历那么肯定是一个位置得算N^2，太慢了，可能过不了

想想看能不能优化？

1.能不能利用预处理结构将查找max(0~i-1)和max(i+1~N)优化到O(1)能找到？
这就看能不能整出一个前缀最大值数组和后缀最大值数组了
其中，前缀最大值数组中每个位置i的值表示0~i的最大值
其中，后缀最大值数组中每个位置i的值表示i~N的最大值
应该是可以实现的，只要从前到后或者从后到前依次生成就好了
只要这两个max能够O(1)找到，加上遍历的O(N)，这个算法能够优化到时间复杂度O(N),O(N)，
肯定能过了。

方法二:
思考在求max这上面有没有单调性，就是能不能在我不用将i前或者i后的所有值给看过一遍我也能起到
相同的作用？

我们现在在i位置，那么我想知道我左右的栅栏高度咋样，我其实只是想知道左右哪边更低然后低的那边是
多少这件事而已，对于高的，我只要知道你高就好了，具体多高我不关心。

对于只需要知道一遍具体多高的我们可以利用一个变量一直累加来得到，不过如果需要另一边和我这边这个
具体值的高度能够得出结论，那么就可以利用一下最值思想

比如说啥你那边最小的都比我这个小了，那你那边肯定所有的都比我这里的小这种话。

这里就是我们能不能找到一种情况，就是你那边最大值最小的情况都比我大了，那肯定栅栏的比我这边要高，
那栅栏自然就在我这边了，而我这边的栅栏是一直知道高度的，这就能够算出我这个位置的蓄水量了。

那咋拿到另一边的最大值的最小值呢？
我们知道最大值随着区域的变大是具有单调性的，这单调性就很棒，比如说此时0~1上的最大值是2
那么当区域变大的时候，比如0~5这个最大值肯定是要大于等于2的，反正不可能小于2，这岂不是就拿到了
最大值的最小情况。

根据这种思想就能够使用首尾指针来解决这道题了，我同时用两个指针以及两个变量分别存着左右两边的
小范围最值，这样我每次都可以将一边当做是最小的情况看看能不能结算另一边的结果，然后两个指针往中间
汇聚最终所有位置都看过一遍结果也就抓到了。
*/
public class Code04_VolumeOfHistogram {
    //方法一:
    public static int trap(int[] height) {
        if(height==null||height.length<3) return 0;
        //预处理结构
        int N = height.length;
        int[] preMax = new int[N];
        int[] posMax = new int[N];
        preMax[0] = height[0];
        posMax[N-1] = height[N-1];
        for(int i = 1;i<N;i++)
            preMax[i] = Math.max(preMax[i-1],height[i]);
        for(int i = N-2;i>=0;i--)
            posMax[i] = Math.max(posMax[i+1],height[i]);
        //遍历抓答案
        int waterAll = 0;
        for(int i = 1;i<N-1;i++){//0和N-1肯定存不住水，两边都没栅栏
            //Math.min(preMax[i-1],posMax[i+1]),左边0~i-1的最大值和右边i+1~N的栅栏谁矮就是水平面高度
            waterAll+=Math.max(Math.min(preMax[i-1],posMax[i+1])-height[i],0);
        }
        return waterAll;
    }

    //方法二：
    public static int trap1(int[] height){
        if(height==null||height.length<3) return 0;
        int L = 1;
        int R = height.length-2;
        int LMax = height[0];
        int RMax = height[height.length-1];
        int waterAll = 0;
        while(L<=R){
            if(LMax<=RMax){//右边的最大值的最小情况都大于左边的最大值，那可以结算L位置了
                waterAll += Math.max(LMax-height[L],0);
                LMax = Math.max(LMax,height[L++]);
            }else{//左边最大值的最小情况都大于右边，得结算R位置了
                waterAll += Math.max(RMax-height[R],0);
                RMax = Math.max(RMax,height[R--]);
            }
        }
        return waterAll;
    }
    public static void main(String[] args) {
        int[] a = {2,0,2};
        System.out.println(trap1(a));
    }
}
