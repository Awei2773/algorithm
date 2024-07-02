package waigo.zuoshen.basic.MyCode;


import java.util.logging.Logger;

/**
 * author waigo
 * create 2021-02-11 23:13
 */
public class Code03_PartitionAndQuickSort {
    public static void swap(int[] arr, int aIdx, int bIdx) {
        if (aIdx != bIdx) {
            arr[aIdx] ^= arr[bIdx];
            arr[bIdx] ^= arr[aIdx];//b = b^(a^b) = a
            arr[aIdx] ^= arr[bIdx];
        }
    }

    /**
     * 将arr分为大于x,小于等于x的两部分，取x = arr[r]
     *
     * @param arr
     * @param p
     * @param r
     * @return 返回分界处下标
     */
    public static int partition(int[] arr, int p, int r) {
        int smallTop = p - 1;//指向一个小于等于x的下标
        for (int j = p; j < r; j++) {
            if (arr[j] <= arr[r]) {
                swap(arr, ++smallTop, j);
            }
        }
        swap(arr, smallTop + 1, r);//将pivot和大区第一个交换
        return smallTop + 1;
    }

    /**
     * @param arr
     * @param p
     * @param r
     * @return 下标0:小于x的top 下标1：大于x的low
     * 经过荷兰国旗处理，[p...smallTop] 小于x，[smallTop+1...bigLow-1]等于x，[bigLow...r]大于0
     */
    public static int[] netherlangsFlag(int[] arr, int p, int r) {
        int smallTop = p - 1, bigLow = r;
        int j = p, pivot = arr[r];
        while (j < bigLow) {
            if (arr[j] < pivot) swap(arr, ++smallTop, j);
            else if (arr[j] > pivot) swap(arr, --bigLow, j--);//这个bigLow位置的元素还没看过，所以j需要留在原地再看一遍
            j++;//等于的时候直接移动j不用进行交换操作
        }
        swap(arr, bigLow++, r);
        return new int[]{smallTop, bigLow};
    }

    public static void process1(int[] arr, int p, int r) {
        if (p < r) {
            int mid = partition(arr, p, r);
            process1(arr, 0, mid - 1);
            process1(arr, mid + 1, r);
        }
    }

    /**
     * quickSort1.0版本
     */
    public static void quickSort1(int[] arr) {
        process1(arr, 0, arr.length - 1);
    }

    public static void process2(int[] arr, int p, int r) {
        if (p < r) {
            int[] flag = netherlangsFlag(arr, p, r);
            int smallTop = flag[0], bigLow = flag[1];
            process2(arr, p, smallTop);
            process2(arr, bigLow, r);
        }
    }

    /**
     * quickSort2.0版本,使用荷兰国旗操作进行优化，一次将等于x的全部搞定
     *
     * @param arr
     */
    public static void quickSort2(int[] arr) {
        process2(arr, 0, arr.length - 1);
    }

    public static void process3(int[] arr, int p, int r) {
        if (p < r) {
            //(int) (Math.random() * (r - p + 1)) --> [0,r-p] + p -->[p,r]
            int pivotIdx = (int) (Math.random() * (r - p + 1)) + p;//[p,r]，+p别忘了，偏移量
            swap(arr, pivotIdx, r);//x放在r位置
            int[] flags = netherlangsFlag(arr, p, r);
            process3(arr, p, flags[0]);
            process3(arr, flags[1], r);
        }
    }

    /**
     * quickSort3.0版本，随机快排，对于快排1.0和2.0来说最坏情况就是数组已经有序的情况下，
     * 这样每次只会处理好一个位置，那么就是一个等差数列，时间复杂度是O(N^2)
     * 解决这个问题就是x不能直接取最后一个，使用一个随机的下标就可以了，这就是快排3.0
     *
     * @param arr
     */
    public static void quickSort3(int[] arr) {
        process3(arr, 0, arr.length - 1);
    }

    public static int[] generalRandomArray(int maxSize, int maxValue) {
        //size = [0,maxSize]
        int size = (int) (Math.random() * (maxSize + 1));
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = (int) (Math.random() * maxValue) - (int) (Math.random() * maxValue);
        }
        return array;
    }

    public static void printArray(int[] arr) {
        StringBuilder builder = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            builder.append(arr[i]);
            builder.append(",");
        }
        if (builder.length() > 2) {
            builder.deleteCharAt(builder.length() - 1);
        }
        builder.append("]");
        Logger.getGlobal().info(builder.toString());
    }

    public static int[] deepCopyArray(int[] arr) {
        int[] newArray = new int[arr.length];
        for (int i = 0; i < arr.length; i++)
            newArray[i] = arr[i];
        return newArray;
    }

    public static boolean isArrayEql(int[] arrA, int[] arrB) {
        if (arrA.length != arrB.length) return false;
        for (int i = 0; i < arrA.length; i++)
            if (arrA[i] != arrB[i]) return false;
        return true;
    }


}
