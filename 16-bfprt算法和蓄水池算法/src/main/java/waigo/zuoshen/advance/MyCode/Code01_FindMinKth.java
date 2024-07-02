package waigo.zuoshen.advance.MyCode;

import java.util.Arrays;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * author waigo
 * create 2021-03-14 10:32
 */
/*
在无序数组中求第K小的数
1）改写快排的方法
2）bfprt算法
*/
@SuppressWarnings("Duplicates")
public class Code01_FindMinKth {
    public static int[] findMinKthAlls(int[] arr, int k) {
        if (arr == null || k > arr.length) return null;
        if (k == 0) return new int[0];
        int[] res = new int[k];
        findMinKth(arr, k);//找到k小的数之后，k位置前的数肯定都是小于k的
        System.arraycopy(arr, 0, res, 0, k);
        return res;
    }

    //找到arr中第k小的
    public static int findMinKth(int[] arr, int k) {
        return process(arr, 0, arr.length - 1, k - 1);
    }

    //从L...R中找到排好序后index位置的元素的值
    public static int process(int[] arr, int L, int R, int index) {
        if (L == R) {
            return arr[L];
        }
        swap(arr, R, L + (int) (Math.random() * (R - L + 1)));
        int[] lowAndTop = partition(arr, L, R);
        if (index < lowAndTop[0]) {
            return process(arr, L, lowAndTop[0] - 1, index);
        } else if (index > lowAndTop[1]) {
            return process(arr, lowAndTop[1] + 1, R, index);
        }
        return arr[lowAndTop[0]];
    }

    public static int[] partition(int[] arr, int L, int R) {
        //minTop指向小区的顶，maxLow指向大区的左边界
        int minTop = L - 1, maxLow = R;
        int pivot = arr[R];
        for (int j = L; j < maxLow; j++) {
            if (arr[j] < pivot) {
                swap(arr, ++minTop, j);//小区顶前移
            } else if (arr[j] > pivot) {
                swap(arr, --maxLow, j--);//大区底左移，j停在原地，从高处换来的还没看过
            }
        }
        swap(arr, maxLow++, R);
        return new int[]{minTop + 1, maxLow - 1};
    }

    public static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    public static int findMinKth1(int[] arr, int k){
        return bfprt(arr,0,arr.length-1,k-1);
    }
    //严格将时间复杂度确定在O(N)找到第K小的数所在下标，bfprt算法
    //返回L...R有序之后index位置的值
    //时间复杂度分析
    /*
    递归式：T(n) = mediumOfMids(T(?))+partition2(O(N))+T(?)
    mediumOfMids分析：
    1.每5个小样本进行一个排序O(1)*N/5 = O(N)
    2.所有小样本的中间数形成的数组mArr找中间数T(N/5)
    递归式变成了
    T(n) = mediumOfMids(T(N/5))+partition2(O(N))+T(?)
    经过这一番精挑细选出来的pivot有点东西的，它能让我们接下来partition之后的子调用T(7N/10)
    这个7N/10样本量怎么确定的呢？
    假如说index打在了左半边，也就是淘汰了右半边？
    那么右边最少淘汰了多少呢？因为我们要算时间复杂度就要估计左边最多样本量，最差情况。
    由于精挑细选，根据pivot进行partition之后它右边至少有3N/10的元素，因为pivot是mArr的中间数
    mArr数据量是N/5,而pivot会小于其中的N/10(就是一半),每个N/10又都会有2个大于等于它的，所以右边至少有3N/10
    那么左边至多就是N-3N/10 = 7N/10
    递归式最终变成：
    T(n) = T(N/5)+O(N)+T(7N/10)
    这个递归式根据数学计算最终时间复杂度是O(N)，这可不是概率问题，是严格证明的

    bfprt算法开创了尝试算法的时代，只要将递归式列出来看看这个递归式最终的时间复杂度是否
    小于O(N)就能确定要不要继续想下去。
    */
    public static int bfprt(int[] arr, int L, int R, int index) {
        if (L == R) return arr[L];
        int pivot = mediumOfMids(arr, L, R);
        int[] mTAndBl = partition2(arr, L, R, pivot);
        if (index < mTAndBl[0]) {
            return bfprt(arr, L, mTAndBl[0] - 1, index);
        } else if (index > mTAndBl[1]) {
            return bfprt(arr, mTAndBl[1] + 1, R, index);
        }
        return arr[mTAndBl[0]];
    }

    private static int[] partition2(int[] arr, int L, int R, int pivot) {
        int minTop = L - 1, bigLow = R + 1;
        for (int i = L; i < bigLow; i++) {
            if (arr[i] < pivot) {
                swap(arr, ++minTop, i);
            } else if (arr[i] > pivot) {
                swap(arr, --bigLow, i--);
            }
        }
        return new int[]{minTop + 1, bigLow - 1};
    }

    //返回L...R中所有5个小数组的中间数的数组的中间数
    private static int mediumOfMids(int[] arr, int L, int R) {
        //将arr数组5个1组分为差不多N/5组，最后不够的自己一组
        int sectionSize = R - L + 1;
        int offset = sectionSize % 5 == 0 ? 0 : 1;
        int mArrSize = sectionSize / 5 + offset;
        int[] mArr = new int[mArrSize];
        for (int i = 0; i < mArrSize; i++) {
            //每个小组都用插入排序排好后抓出中位数进mArr
            //第一组：L+0~L+4，第二组:L+5~L+9，第三组：L+10~L+14...
            int teamFirst = L+i * 5;//别忘了偏移量L
            mArr[i] = getMedium(arr,teamFirst,Math.min(teamFirst+4, R));
        }
        //找到mArr的中位数作为partition的pivot
        return bfprt(mArr, 0, mArrSize - 1, mArrSize / 2);
    }
    public static int getMedium(int[] arr,int L,int R){
        insertionSort(arr,L,R);
        return arr[(L+R)>>1];//小样本，最多5个，所以不用L+(R-L)>>1
    }
    public static void insertionSort(int[] arr, int L, int R) {
        for (int i = L + 1; i <= R; i++) {
            int insertNum = arr[i];
            int ii = i - 1;
            while (ii >= L && insertNum < arr[ii]) {
                arr[ii + 1] = arr[ii--];//后移
            }
            arr[ii + 1] = insertNum;
        }
    }

    public static void main(String[] args) {
        int[] a = {3, 4, 1, 2, 4, 3};
//        partition(a, 0, a.length - 1);
//        System.out.println(Arrays.toString(a));
        System.out.println(findMinKth1(a, 1));
//        System.out.println(Arrays.toString(findMinKthAlls(a, 3)));
    }
}
