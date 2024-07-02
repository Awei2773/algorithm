package waigo.zuoshen.basic.MyCode;


import java.util.logging.Logger;

/**
 * author waigo
 * create 2021-02-08 12:25
 */
public class Code05_BSNearLeft {
    /**
     * 2) 在一个有序数组中，找>=某个数最左侧的位置
     * 二分法技巧，不管三七二十一，分块，分完之后定义l,r的值，而且通过l=mid+1，r=mid-1来变化
     * 这里需要求不小于某个值的最左边的下标，可能第一个mid就指向这个值，而改变之后就找不到这个值了
     * 所以需要多一个变量index来记录上一个合法的mid
     */
    public static int nearestLeftIdx(int[] sortedAry, int num) {
        //[1,2,3,4,4,4,5,6]  num=4
        int l = 0, r = sortedAry.length - 1;
        int index = -1, mid;
        while (l <= r) {
            //r+l有风险，可能溢出，而且位运算比起算术运算来说虽然都是常数时间的操作，但是会更加快
            mid = l + ((r - l) >> 1);//等同于(r+l)/2
            //可能最左边不是这个数，继续往左二分，不过需要记一下mid，可能这就是最左了
            if (sortedAry[mid] >= num) {
                index = mid;
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return index;
    }

    //时间复杂度高，但是容易实现的方法
    //for test
    public static int test(int[] sortedAry, int num) {
        int i = 0;
        for (; i < sortedAry.length; i++)
            if (sortedAry[i] >= num) break;
        return i>=sortedAry.length?-1:i;
    }

    //随机数组，产生测试数据
    //取值：[-maxVal,maxVal],数组大小:[1,size]
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

    //对[p...r]进行排序
    public static void quickSort(int[] array, int p, int r) {
        if (p < r) {
            int i = p - 1;//[p..i]表示小于等于pivot,[i+1...j-1]表示大于pivot
            int pivot = array[r];
            for (int j = p; j < r; j++) {
                if (array[j] <= pivot) swap(array, ++i, j);
            }
            swap(array,i+1,r);
            //小于等于的快排
            quickSort(array, p, i);
            //大于的快排
            quickSort(array, i + 2, r);
        }
    }


    public static void swap(int[] array, int aIdx, int bIdx) {
        if (aIdx != bIdx) {
            array[aIdx] ^= array[bIdx];//a = a^b
            array[bIdx] ^= array[aIdx];//b = b^a=b^(a^b)=a
            array[aIdx] ^= array[bIdx];//a = a^b=(a^b)^a=b
        }
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
        for(int i = 0;i < aryX.length;i++){
            if(aryX[i]!=aryY[i]) return false;
        }
        return true;
    }
}
