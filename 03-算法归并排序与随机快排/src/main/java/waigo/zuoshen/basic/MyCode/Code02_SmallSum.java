package waigo.zuoshen.basic.MyCode;

/**
 * author waigo
 * create 2021-02-08 20:06
 */

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 在一个数组中，一个数左边比它小的数的总和，叫数的小和，所有数的小和累加起来，叫数组小和。求数组小和。
 * 例子： [1,3,4,2,5]
 * 1左边比1小的数：没有
 * 3左边比3小的数：1
 * 4左边比4小的数：1、3
 * 2左边比2小的数：1
 * 5左边比5小的数：1、3、4、 2
 * 所以数组的小和为1+1+3+1+1+3+4+2=16
 * 思路：数组小和是一个数左边比它小的数的总和，叫数的小和，所有数的小和累加起来，也就是一个数它参与几次
 * 数组小和，就是找它后面会有多少比它大的，有几个比它大的就乘以几次，这样做的好处是一次遍历能够将这次的
 * 结果给保存下来，这也是归并的精髓，去除掉无谓的比较，将每次遍历的结果保存下来，从而起到降低时间复杂度的
 * 好处。
 * 只有左边比右边小的时候才需要加数组小和，因为右边是有序的，比右边第一个小就会比右边所有的都要小
 * 等于右边第一个时，将右边的写入help，因为右边剩下的可能会比这个要大
 */
public class Code02_SmallSum {
    public static int getSmallSum(int[] nums) {
        return mergeSort(nums, 0, nums.length - 1);
    }

    /**
     * 比起普通的归并排序，这里需要将发挥这一次归并的小和
     *
     * @param nums
     * @param p
     * @param r
     * @return
     * T(n) = 2*T(n/2)+O(N) a=2,b=2,d=1  log(b,a)=1==d -->O(N*logN)
     * Master定理，求递归的时间复杂度
     * 如果T(n) = a*T(n/b)+O(N^d)
     *      log(b,a) > d -->O(N^log(b,a))
     *      log(b,a) < d -->O(N^d)
     *      log(b,a) == d -->O(N^d*logN)
     */
    public static int mergeSort(int[] nums, int p, int r) {
        if (p < r) {
            int mid = p + ((r - p) >> 1);
            return mergeSort(nums, p, mid) +//左边排序
                    mergeSort(nums, mid + 1, r) +//右边排序
                    merge(nums, p, mid, r);
        }
        return 0;
    }

    public static int merge(int[] nums, int p, int m, int r) {
        int[] help = new int[r - p + 1];//辅助数组
        int i = p, j = m + 1;//分别指向两部分的第一个
        int helpTemp = 0;
        int res = 0;
        while (i <= m && j <= r) {//经过这轮merge后这些数都会在一边，所以是不会重复记小和的
            //nums[lTop]<nums[rTop],计算右边有n个，res+=n*nums[lTop],写入左边
            //nums[lTop]>=nums[rTop],写入右边，不用加res，因为右边没有比这个nums[lTop]大的
            res += nums[i] < nums[j] ? (r - j + 1) * nums[i] : 0;//[j...r]是右边
            help[helpTemp++] = nums[i] >= nums[j] ? nums[j++] : nums[i++];
        }
        //左边溢出，将右边全部放入
        while (j <= r) help[helpTemp++] = nums[j++];
        while (i <= m) help[helpTemp++] = nums[i++];
        //刷回
        while (--helpTemp >= 0) nums[helpTemp + p] = help[helpTemp];
        return res;
    }

    //for test
    public static int test(int[] nums) {
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            int specNum = nums[i];
            //往前找有几个小于它的加入res
            for (int j = i - 1; j >= 0; j--) {
                if (nums[j] < specNum) res += nums[j];
            }
        }
        return res;
    }
}
