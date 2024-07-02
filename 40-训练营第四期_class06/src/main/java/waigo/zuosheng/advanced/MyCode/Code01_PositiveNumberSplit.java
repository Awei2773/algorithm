package waigo.zuosheng.advanced.MyCode;

/**
 * author waigo
 * create 2021-06-27 11:31
 */

import java.util.*;

/**
 * 给定一个正数1，裂开的方法有一种，(1) 给定一个正数2，裂开的方法有两种，(1和1)、(2) 给定一个正数3，
 * 裂开的方法有三种，(1、1、1)、(1、2)、(3) 给定一个正数4，裂开的方法有五种，
 * (1、1、1、1)、(1、1、2)、(1、3)、(2、2)、 (4)
 * 给定一个正数n，求裂开的方法数，裂开的数需要保持一个非递减的序列。 动态规划优化状态依赖的技巧
 */
@SuppressWarnings("Duplicates")
public class Code01_PositiveNumberSplit {
    public static int getWays(int num){
        if(num<=0) return 0;
        Set<String> res = new HashSet<>();
        force(res,new ArrayList<Integer>(),num);
        return res.size();
    }
    //1.暴力尝试,那就是将num拆开成全排列，然后对路径进行排序后去重肯定能得到答案
    public static void force(Set<String> res, List<Integer> path, int rest){
        if(rest==0){
            res.add(resolveListToStr(path));
            return;
        }
        //枚举，当前裂出来的可能性为1~rest
        for(int splitNum = 1;splitNum<=rest;splitNum++){
            path.add(splitNum);
            force(res,path,rest-splitNum);
            path.remove(path.size()-1);//轨迹擦除
        }
    }

    private static String resolveListToStr(List<Integer> path) {
        Integer[] paths = path.toArray(new Integer[]{});
        Arrays.sort(paths);
        StringBuilder s = new StringBuilder();
        for (Integer curNum : paths) {
            s.append(curNum+"-");
        }
        s.deleteCharAt(s.length()-1);
        return s.toString();
    }
    //2.为什么暴力解需要记录路径？这样会造成无法动归的，如何给它把路径给省了，那就是每次裂出一个后要
    //带着往下传，每次裂出来的不会比上一次的小，这样就不会出现递减的序列了，自然就用不到收集去重了
    public static int optimize(int num){
        if(num<0) return 0;
        return process(1,num);
    }

    private static int process(int pre, int rest) {
        if(rest==0){
            return 1;
        }
        int ways = 0;
        for(int splitNum = pre;splitNum<=rest;splitNum++){
            ways+=process(splitNum,rest-splitNum);
        }
        return ways;
    }
    //出现枚举行为，改严格动归
    public static int dpWays1(int num){
        if(num<=0) return 0;
        //pre取值范围1~num,rest取值范围0~num,pre也设计成从0~num,第一行不填
        //这是一个正方形表
        int N = num + 1;
        int[][] dp = new int[N][N];//pre做行，rest做列
        //pre>rest的地方全是0
        for(int row = 1;row<N;row++){
            dp[row][0] = 1;
        }
        for(int row = N-1;row>=1;row--){
            for(int col = row;col<N;col++){
                int ways = 0;
                for(int splitNum = row;splitNum<=col;splitNum++){
                    ways+=process(splitNum,col-splitNum);
                }
                dp[row][col] = ways;
            }
        }
        return dp[1][N-1];
    }
    //枚举的优化，每个位置依赖左边那个col = (col-row),row=row位置牵引出来的一条左下角斜线的所有位置
    public static int dpWays2(int num){
        if(num<=0) return 0;
        //pre取值范围1~num,rest取值范围0~num,pre也设计成从0~num,第一行不填
        //这是一个正方形表
        int N = num + 1;
        int[][] dp = new int[N][N];//pre做行，rest做列
        //pre>rest的地方全是0
        for(int row = 1;row<N;row++){
            dp[row][0] = 1;
        }
        for(int row = N-1;row>=1;row--){
            for(int col = row;col<N;col++){
                dp[row][col] = (row+1<N?dp[row+1][col]:0)+dp[row][col-row];
            }
        }
        return dp[1][N-1];
    }
    public static void main(String[] args) {
//        System.out.println(getWays(25));
//        System.out.println(optimize(25));
        System.out.println(dpWays2(50));
        System.out.println(dpWays1(50));
    }
}
