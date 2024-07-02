package waigo.zuoshen.basic.MyCode;

import java.util.logging.Logger;

/**
 * author waigo
 * create 2021-02-09 11:12
 */
public class Code04_BSExist {
    public static int bsExist(int[] sortedAry, int specifyNum) {
        if (sortedAry != null) {
            int left = 0, right = sortedAry.length - 1, mid;
            while (left <= right) {
                mid = left + ((right - left) >> 1);
                if (specifyNum < sortedAry[mid]) {
                    right = mid - 1;
                } else if (specifyNum > sortedAry[mid]) {
                    left = mid + 1;
                } else {
                    return mid;
                }
            }
        }
        return -1;
    }

    public static int[] generalRandomArray(int maxSize, int maxValue) {
        int size = (int) (Math.random() * maxSize);//0~maxSize-1
        int[] ary = new int[size];
        for (int i = 0; i < size; i++) {
            ary[i] = (int) (Math.random() * maxValue) - (int) (Math.random() * maxValue);
        }
        return ary;
    }

    public static void swap(int[] ary, int aIdx, int bIdx) {
        int temp = ary[aIdx];
        ary[aIdx] = ary[bIdx];
        ary[bIdx] = temp;
    }

    //ary在p...r上玩荷兰国旗
    public static int[] netherlandFlag(int[] ary, int p, int r) {
        int sTop = p - 1, hLow = r;//sTop表示小于区的头，hLow表示大于区的头
        int pivot = ary[r];
        int j = p;
        while (j < hLow) {
            if (ary[j] < pivot) {
                swap(ary, ++sTop, j);
            } else if (ary[j] > pivot) {
                swap(ary, --hLow, j--);//j留在原地，因为还没看过
            }
            ++j;
        }
        swap(ary, r, hLow++);//pivot和大于区的第一个交换
        return new int[]{sTop, hLow};
    }

    public static void process(int[] ary, int p, int r) {
        if (p < r) {
            //<=STop为小于区,sTop<j<hLow表示等于区,>=hLow表示大于区
            int randomIdx = (int) (Math.random() * (r - p + 1)) + p;//0~r-p，r-p+1就是size，这里数组下标就是0~size-1
            //不过不要忘记加上p，因为是从p到r,太蠢了，排了好久才看出来
            swap(ary, randomIdx, r);
            int[] flags = netherlandFlag(ary, p, r);
            int sTop = flags[0], hLow = flags[1];
            //左右递归
            process(ary, p, sTop);
            process(ary, hLow, r);
        }
    }

    public static void quickSort(int[] ary) {
        process(ary, 0, ary.length - 1);
    }
    //for test
    public static boolean isExist(int[] ary,int specifyNum){
        for(int i = 0;i < ary.length;i++){
            if(ary[i]==specifyNum) return true;
        }
        return false;
    }

    public static void main(String[] args) {
        int times = 100000, maxValue = 100, maxSize = 100;
        int i = 0;
        for(;i < times;i++){
            int[] ary = generalRandomArray(maxSize, maxValue);
            quickSort(ary);
            int specifyNum = (int) (Math.random() * maxValue) - (int) (Math.random() * maxValue);
            int idx = bsExist(ary, specifyNum);
            boolean myExist = idx != -1;
            boolean exist = isExist(ary, specifyNum);
            if(exist!=myExist){
                Logger.getGlobal().info("oops");
            }
        }
        if(i == times) Logger.getGlobal().info("finish!!!");
    }
}
