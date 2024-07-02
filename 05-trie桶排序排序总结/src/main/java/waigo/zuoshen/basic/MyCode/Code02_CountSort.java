package waigo.zuoshen.basic.MyCode;

/**
 * author waigo
 * create 2021-02-18 11:19
 */

import java.util.Arrays;
import java.util.logging.Logger;

/**
 * 不基于比较的排序，桶排序思想下的计数排序
 * 对需要排序的数据有要求，要求是整数(大于0)而且范围较小
 * 比如对公司的员工根据年龄进行排序，和业务强相关，业务变动代码全得重写
 * 员工年龄[0~120]
 */
public class Code02_CountSort {

    public static void countSort(int[] eAry) {//数据取值范围为k,
        //找到数组中最大的那个数和最小的数，计算一个取值区间
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for (int ele : eAry) {
            max = Math.max(max, ele);
            min = Math.min(min, ele);
        }
        //开辟取值区间的数组，使得数组下标可以变成[min,max],注意实际上是[0,max - min]所以要加上偏移量min
        //0-->min
        //1-->min+1
        //length-->max这样对应
        //注意：下标就是对应的某个在排序数组中的值，遍历到这个值就让它对应的下标++，
        // 因为下标是从0开始的，需要减去偏移量min
        int[] count = new int[max - min + 1];
        for (int ele : eAry)
            count[ele - min]++;//计算每个数出现的次数，这里要减去偏移量
        //写回数组
        for (int i = 0, index = 0; i < count.length; i++) {
            while (count[i]-- > 0) eAry[index++] = i + min;//从下标还原值，需要加上偏移量
        }

    }

    public static int[] generalRandomAry(int maxSize, int maxValue) {
        int size = (int) (Math.random() * maxSize);//0~maxSize-1
        int[] ary = new int[size];
        for (int i = 0; i < ary.length; i++) {
            ary[i] = (int) (Math.random() * maxValue) - (int) (Math.random() * maxValue);
        }
        return ary;
    }

    public static boolean isEqlAry(int[] aryA, int[] aryB) {
        if (aryA == aryB) return true;
        if (aryA.length != aryB.length) return false;
        for (int i = 0; i < aryA.length; i++) {
            if (aryA[i] != aryB[i]) return false;
        }
        return true;
    }

    public static int[] cpyAry(int[] origin) {
        if (origin == null) return null;
        int[] newAry = new int[origin.length];
        for (int i = 0; i < newAry.length; i++) {
            newAry[i] = origin[i];
        }
        return newAry;
    }

    public static void main(String[] args) {
        int maxSize = 100, maxValue = 100, testTimes = 100000;
        int i = 0;
        for (; i < testTimes; i++) {
            int[] ary = generalRandomAry(maxSize, maxValue);
            int[] cpy = cpyAry(ary);
            Arrays.sort(cpy);
            countSort(ary);
            if(!isEqlAry(ary,cpy)){
                Logger.getGlobal().info("oops!!!");
                break;
            }
        }
        Logger.getGlobal().info(i==testTimes?"finish!!!":"fucking!!!");

    }

}
