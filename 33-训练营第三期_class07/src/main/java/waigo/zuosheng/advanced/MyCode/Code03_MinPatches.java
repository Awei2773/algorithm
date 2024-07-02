package waigo.zuosheng.advanced.MyCode;

/**
 * author waigo
 * create 2021-05-29 10:42
 */

/**
 * 给定一个有序的正数数组arr和一个正数range，如果可以自由选择arr中的数字，想累加得 到 1~range 范围上所有的数，
 * 返回arr最少还缺几个数。
 * 【举例】
 * arr = {1,2,3,7}，range = 15
 * 想累加得到 1~15 范围上所有的数，arr 还缺 14 这个数，所以返回1 arr = {1,5,7}，range = 15
 * 想累加得到 1~15 范围上所有的数，arr 还缺 2 和 4，所以返回2
 *
 * 思路：贪心策略，每次加range+1是最经济，数组能榨就榨点，不够就补，绝对是最少的添加
 */
public class Code03_MinPatches {
    public static int minPatches(int[] arr,int range){
        if(arr==null) arr = new int[]{};
        //当前在0位置，想要的肯定是1
        int curRange = 0;
        int patch = 0;
        for (int i = 0; i < arr.length; i++) {
            while (arr[i]>(curRange+1)){
                curRange+=curRange+1;
                patch++;
                if(curRange>=range) return patch;
            }
            curRange+=arr[i];
            if(curRange>=range) return patch;
        }
        //如果数组利用完了还是没有到range，那就得最大利益的加了
        while (curRange<range){
            curRange+=curRange+1;
            patch++;
        }
        return patch;
    }

    public static void main(String[] args) {
        System.out.println(minPatches(new int[]{1,5,7},15));
    }
}
