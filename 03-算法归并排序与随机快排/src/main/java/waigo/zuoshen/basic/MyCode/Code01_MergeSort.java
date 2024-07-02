package waigo.zuoshen.basic.MyCode;

import java.util.Arrays;
import java.util.Stack;
import java.util.logging.Logger;

/**
 * author waigo
 * create 2021-02-08 18:22
 */
@SuppressWarnings("Duplicates")
public class Code01_MergeSort {
    /**
     * 归并排序
     *
     * @param nums 待排序的数组
     * @param p    需要对[p...r]进行排序
     * @param r    时间频度：T(n) = 2*T(n/2)+O(N)
     *             使用Master公式：a = 2,b = 2,d = 1,log(b,a) = log(2,2) = 1
     *             log(2,2)==d,所以时间复杂度为O(N*logN)
     */
    public static void mergeSort(int[] nums, int p, int r) {
        if (p < r) {
            int mid = p + ((r - p) >> 1);
            mergeSort(nums, p, mid);//左边排序
            mergeSort(nums, mid + 1, r);
            merge(nums, p, mid, r);
        }
    }

    /**
     * 非递归版本实现
     */
    public static void mergeSort1(int[] nums) {
        int N = nums.length;
        int mergeSize = 1;//指的是左边有序的长度
        //每次merge的大小是2*mergeSize,所以若是mergeSize>N/2就表示上一次已经完成排序了
        // (错误，比如数组长度是3,3/2=1，很明显mergeSize=1后还没排序好)
        int i = 0;
        while (mergeSize < N) {//logN,每次乘以2追赶，相当于求N个节点的树高为logN
            int L = 0;
            if(i==3) return;
            i++;
            while (L < N) {
                //M表示这一次merge的中点，因为上一次完成了mergeSize的merge，所以M要在上一次完成了merge区间的最后
                //M-L+1表示L和M夹起来的区间[L...M]的大小等于mergeSize,M-L+1=mergeSize
                int M = mergeSize + L - 1;
                if (M >= N) break;//表示左边都不够mergeSize了，完成一个merge至少需要区间size>mergeSize
                //R表示这次merge的右边点，应该是一个mergeSize结尾，R-M = mergeSize,这个区间不包括M
                //但是可能这右边可能不够mergeSize大小了
                int R = Math.min(M + mergeSize, N - 1);
                merge(nums, L, M, R);
                L = R + 1;//开始下个区间
            }
            ////太细了，假如N的长度等于Integer.MAX_VALUE,那此时mergeSize乘以2会溢出的
            if (mergeSize > (N >> 1)) break;
            mergeSize <<= 1;//mergeSize *= 2
        }
    }

    //空间复杂度是说整个流程中最大占用的空间的大小，而不是所用到的空间的和，所以归并排序最大占用空间的复杂度不是压栈时使用的树高空间LogN
    //而是待排序区间的左右都有序后最后一趟开辟的help的空间是O(N)
    public static void merge(int[] nums, int p, int m, int r) {
        if (m > r || p > m) return;
        int[] help = new int[r - p + 1];
        int i = p, j = m + 1;//i指向左边第一个，j指向右边第一个
        int helpTemp = 0;
        while (i <= m && j <= r)
            help[helpTemp++] = nums[i] > nums[j] ? nums[j++] : nums[i++];
        //出来会是左边溢出或者右边溢出
        //左边溢出，将右边全部加入
        while (j <= r)
            help[helpTemp++] = nums[j++];
        //右边溢出，将左边全部加入
        while (i <= m)
            help[helpTemp++] = nums[i++];
        //刷回,此时helpTemp指向了数组.length那里，溢出了,注意help数组是存着[p..r]的数据的，所以helpTemp=0要刷回nums[p]
        while (--helpTemp >= 0) {//helpTemp=help.length-1要刷回nums[r]位置，help.length-1=r-p
            nums[helpTemp + p] = help[helpTemp];//所以，helpTemp+p就得到对应的nums的下标
        }


    }

    public static void printArray(int[] array) {
        StringBuilder aryStr = new StringBuilder("[");
        for (int i = 0; i < array.length; i++) {
            aryStr.append(array[i]);
            aryStr.append(",");
        }
        aryStr.deleteCharAt(aryStr.length() - 1);
        aryStr.append("]");
        Logger.getGlobal().info(aryStr.toString());
    }

    public static int[] generateRandomArray(int maxSize, int maxVal) {
        //Math.random()-->[0,1)
        //(int)Math.random()*(maxVal+1)-->[0,maxVal]
        int size = (int) (Math.random() * (maxSize)) + 1;
        int[] target = new int[size];
        for (int i = 0; i < size; i++) {
            target[i] = (int) (Math.random() * (maxVal + 1)) - (int) (Math.random() * (maxVal + 1));
        }
        return target;
    }

    public static int[] deepCopyAry(int[] origin) {
        int[] target = new int[origin.length];
        for (int i = 0; i < origin.length; i++) {
            target[i] = origin[i];
        }
        return target;
    }

    public static boolean isAryEqual(int[] aryX, int[] aryY) {
        if (aryX.length != aryY.length) return false;
        for (int i = 0; i < aryX.length; i++) {
            if (aryX[i] != aryY[i]) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{46,79,56,38,40,80,36,40,75,66,84,24};
        mergeSort1(nums);
        System.out.println(Arrays.toString(nums));
    }
}
