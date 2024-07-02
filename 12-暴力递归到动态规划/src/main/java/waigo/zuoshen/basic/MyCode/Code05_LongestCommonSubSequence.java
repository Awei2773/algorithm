package waigo.zuoshen.basic.MyCode;

import java.util.HashMap;

/**
 * author waigo
 * create 2021-05-11 13:33
 */
//求两个字符串的最长子序列
/*
思考尝试的可变参数，本题要求的是最长公共子序列

其实，我发现很多时候在考虑用递归的时候，最大的难点在于确定递归函数的含义。而确定递归函数的含义的要诀就在于如何让父调用
获得此时对于他来说最舒服的返回值，关键要让父调用利用子调用的返回结果能够快速得到当前的答案，然后大事化小，小事化了。
当问题规模小到0,1的级别的时候就解决了。

所以，此题求的是最长子序列的长度，那么我递归函数就定义为，当str1从左到右数x个长度，str2从左到右数y个长度时，两个字符串的最长子序列。
注意，动态规划的关键在于用老问题来求解新答案。所以你求解的趋势一定要具有一个方向性，比如说从上到下，从左到右，从左上到左下这样子。
如果说你的递归定义的时候设置x,y为两个长度，但是不规定这个长度从哪里开始，到哪里结束的话，你就求不出这个位置的答案了。

写递归函数的窍门
1.解决base case，这才是我们能够解决的问题规模
2.大事化小，小事化了，寻常(x,y)位置就往上抛，计算机会帮助我们枚举可能性然后将一个大问题的解通过很多小问题来合成

代码分析
1.(0,y),(x,0)这些位置的答案很明显都是0，只有一个字符串有长度|base case
2.寻常位置:(x,y),分情况，将问题上抛
2.1
str.x==str.y，问题就变成了process(x-1,y-1)+1，答案变成了在当前位置相同的情况下的答案
(小贪心，只要我结尾位置相同，那么答案肯定出在涵盖结尾位置的字符串上)，可以上抛
2.2
str.x!=str.y,问题就变成了Math.max(process(x,y-1),process(x-1,y))
*/
public class Code05_LongestCommonSubSequence {
    public static int longestCommonSubsequence(String text1, String text2) {
        char[] t1Chars = text1.toCharArray();
        char[] t2Chars = text2.toCharArray();
        return process(t1Chars,t2Chars,t1Chars.length,t2Chars.length);
    }
    //返回text1长度x，text2长度为y情况下的最长公共子序列长度
    private static int process(char[] t1Chars, char[] t2Chars, int x, int y) {
        if(x==0||y==0) return 0;
        return t1Chars[x-1]==t2Chars[y-1]?(process(t1Chars, t2Chars, x-1, y-1)+1):
                Math.max(process(t1Chars, t2Chars, x-1, y),process(t1Chars, t2Chars, x, y-1));
    }
    //记忆化搜索
    public static int longestCommonSubsequence2(String text1, String text2) {
        char[] t1Chars = text1.toCharArray();
        char[] t2Chars = text2.toCharArray();
        HashMap<String,Integer> cache = new HashMap<>();
        return process2(t1Chars,t2Chars,t1Chars.length,t2Chars.length,cache);
    }
    private static int process2(char[] t1Chars, char[] t2Chars, int x, int y, HashMap<String, Integer> cache) {
        String key = x + "_" + y;
        if(cache.containsKey(key)) return cache.get(key);
        if(x==0||y==0) {
            cache.put(key,0);
            return 0;
        }
        int result = t1Chars[x - 1] == t2Chars[y - 1] ? (process2(t1Chars, t2Chars, x - 1, y - 1,cache) + 1)
                : Math.max(process2(t1Chars, t2Chars, x - 1, y,cache), process2(t1Chars, t2Chars, x, y - 1,cache));
        cache.put(key,result);
        return result;
    }
    //动态规划
    public static int longestCommonSubsequence3(String text1, String text2) {
        char[] t1Chars = text1.toCharArray();
        char[] t2Chars = text2.toCharArray();
        int X = t1Chars.length+1;//0~length
        int Y = t2Chars.length+1;
        int[][] dp = new int[X][Y];
        for(int x = 1;x<X;x++){
            for (int y = 1; y < Y; y++)
                dp[x][y] = t1Chars[x-1]==t2Chars[y-1]?dp[x-1][y-1]+1:Math.max(dp[x-1][y],dp[x][y-1]);
        }
        return dp[X-1][Y-1];
    }
    //矩阵压缩，空间优化O(Math.min(X,Y))，其中X为text1长度，Y为text2长度
    public static int longestCommonSubsequence4(String text1, String text2) {
        char[] t1Chars = text1.toCharArray();
        char[] t2Chars = text2.toCharArray();
        int X = t1Chars.length+1;//0~length
        int Y = t2Chars.length+1;
        //确定求值的方向
        if(X>Y){//从左到右
            int[] dp1 = new int[Y];
            int[] dp2 = new int[Y];
            int mode = 0;//当前对哪个表进行求值，0为dp2,1为dp1,初始状态，dp1是求好的，都是0
            for(int x = 1;x<X;x++){
                for(int y = 1;y<Y;y++){
                    if(mode==0){
                        dp2[y] = t1Chars[x-1]==t2Chars[y-1]?dp1[y-1]+1:Math.max(dp1[y],dp2[y-1]);
                    }else{
                        dp1[y] = t1Chars[x-1]==t2Chars[y-1]?dp2[y-1]+1:Math.max(dp2[y],dp1[y-1]);
                    }
                    if(y==Y-1) mode=mode==0?1:0;
                }
            }
            return mode==1?dp2[Y-1]:dp1[Y-1];
        }else{//从上到下
            int[] dp1 = new int[X];
            int[] dp2 = new int[X];
            int mode = 0;//当前对哪个表进行求值，0为dp2,1为dp1,初始状态，dp1是求好的，都是0
            for(int y = 1;y<Y;y++){
                for(int x = 1;x<X;x++){
                    if(mode==0){
                        dp2[x] = t1Chars[x-1]==t2Chars[y-1]?dp1[x-1]+1:Math.max(dp1[x],dp2[x-1]);
                    }else{
                        dp1[x] = t1Chars[x-1]==t2Chars[y-1]?dp2[x-1]+1:Math.max(dp2[x],dp1[x-1]);
                    }
                    if(x==X-1) mode=mode==0?1:0;
                }
            }
            return mode==1?dp2[X-1]:dp1[X-1];
        }
    }


    public static void main(String[] args) {
        String text2 = "1ab23";
        String text1 = "123ab4";
        System.out.println(longestCommonSubsequence4(text1, text2));
    }
}
