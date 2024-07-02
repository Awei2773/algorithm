package waigo.zuoshen.basic.MyCode;

/**
 * author waigo
 * create 2021-02-18 14:49
 */

import java.util.Arrays;

/**
 * 基数排序：只能用来排非负的整数(这些整数都要是同一个进制的)，若是改写成也能排负数的会非常麻烦
 * 算法思想：生成十个队列，序号0~9，这就是10个桶
 * 1)找到位数最大的那个数
 * 2)位数最多那个数有多少位就遍历多少遍，从最低位开始
 * 3)遍历到的那位数是多少就进几号桶
 * 4)遍历结束后从桶里倒出来,按照顺序写回原数组
 * 5)遍历结束后数组有序
 * 112 32 34 23 1
 * --> 最长的是三位，遍历三次
 * =======================
 * -->第1次<--
 * 112-->2-->进二号桶(队列)
 * 32-->2-->进二号桶(队列)
 * 34-->4-->进四号桶(队列)
 * 23-->3-->进三号桶(队列)
 * 1-->1-->进一号桶(队列)
 * -->出桶--<
 * -->1号桶先出
 * 1
 * -->2号桶出(是队列，所以先进先出，说明基数排序能保证稳定性)
 * 1 112 32
 * -->3号桶出
 * 1 112 32 23
 * -->4号桶出
 * 1 112 32 23 34
 * 可以看见结果这一轮，个位上有序
 * -->第2次<--
 * 1 112 23 32 34
 * -->第3次<--
 * 1 23 32 34 112
 */
public class Code03_RadixSort {
    /*返回一个数组中最大那个数的位数*/
    public static int maxBits(int[] arr) {
        int res = 0;
        if (arr != null && arr.length > 0) {
            int max = Integer.MIN_VALUE;
            for (int ele : arr)
                max = Math.max(max, ele);
            do res++; while ((max /= 10) > 0);
        }
        return res;
    }

    /*取出指定位数的数字
        getSpeBitNum(10,1) -> 0
        getSpeBitNum(10,2) -> 1
     */
    public static int getSpeBitNum(int num, int bit) {
        //234234想拿到第三位，做法，将第三位变成最后一位，使用/移动小数点位置两位，然后%拿到个位
        return (num / (int) Math.pow(10, bit - 1)) % 10;//用/去掉高位，%10拿到最低位
    }

    public static void radixSort(int[] arr) {
        if (arr == null || arr.length < 2) return;
        int maxbits = maxBits(arr);
        int[] help = new int[arr.length];
        for (int i = 0; i < maxbits; i++) {//O(maxbits*N) = O(N)
            int[] count = new int[10];
            for (int j = 0; j < arr.length; j++)
                count[getSpeBitNum(arr[j], i + 1)]++;
            //count现在模拟了进桶了，每个桶中进了几个数就为几
            for (int j = 1; j < 10; j++)
                count[j] += count[j - 1];
            //count变成了count'了，i下标里存着的数表示i桶之前的桶中有几个数
            //出桶，从右往左遍历，这样按照桶中先入先出的法则，碰到的肯定是某个桶中最后出来的数
            for (int j = arr.length - 1; j >= 0; j--)
                //几号桶
                //int bucketNum = getSpeBitNum(arr[j], i + 1);
                //查看count',那个桶里表示这个包括这个桶在内前面有几个数，因为这个数是这个桶中最后一个加入的，
                // 肯定最后一个出，所以放在最后,假如这个桶中是6，表示这个桶和之前的桶中共有6个数，下标0~5，
                //这个桶出来最后的肯定放在5,然后当前桶减一，因为放入一个了最后下标要变成4了
                help[count[getSpeBitNum(arr[j], i + 1)]-- - 1] = arr[j];
            //写回arr
            for (int j = 0; j < arr.length; j++)
                arr[j] = help[j];
        }
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random());
        }
        return arr;
    }

    // for test
    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    // for test
    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100000;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            radixSort(arr1);
            Arrays.sort(arr2);
            if (!isEqual(arr1, arr2)) {
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }
}
