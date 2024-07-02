package waigo.zuoshen.advanced.MyCode;

/**
 * author waigo
 * create 2021-05-05 12:28
 */
/*
有一些排成一行的正方形。每个正方形已经被染成红色或者绿色。现在可以选择任意一个正方形然后用这两种颜色的任意一种进行染色,
这个正方形的颜色将 会被覆盖。目标是在完成染色之后,每个红色R都比每个绿色G距离最左侧近。 返回最少需要涂染几个正方形。
如样例所示: s = RGRGR 我们涂染之后变成RRRGG满足要求了,涂染的个数为2,没有比这个更好的涂染方案。

题目理解：
就是把左边都变成R，右边都是G，R...G...这种形式

*/
public class Code05_RedAndGreen {
    //RGRGR->RRRGG
    //思路：可以枚举每个分割线，就是将分割线左边的全变成R，右边的全变成G，反正最后的答案必是R...|G...这样的，肯定对应一条分割线的答案
    //所以答案必能抓住，那么问题就变成了找到目前分割线左边有几个G，右边有几个R了
    //人为规定，分割线上的元素属于分割线的右端，所以分割线为0~length

    //将上面两个需要求的变量记录为LG和RR，看看能否快速根据每个位置计算出来
    //N=LG+LR+RR+RG
    //Right_ALL=RR+RG
    //一路记录着LG，所以LG已知，目前处在i位置，所以Right_All=N-i+1
    //如果知道G_All=LG+RG那么就可以算出RG，然后RR=Right_All-RG,这样将两个变量的求解变成O(1)，整体时间复杂度能够变成O(N)
    public static int minPaint(String s) {
        char[] chars = s.toCharArray();
        //1.求G_All
        int G_All = 0;
        for (int i = 0; i<chars.length;i++)
            G_All+=chars[i]=='G'?1:0;
        //2.遍历分割线，抓答案
        int LG = 0;
        int N = chars.length;
        int ans = N;
        for(int i = 0; i<= N; i++){
            int RR = (N - i)-(G_All-LG);
            ans = Math.min(ans,LG+ RR);
            LG+=i<N&&chars[i]=='G'?1:0;
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(minPaint("RGRGR"));
    }
}
