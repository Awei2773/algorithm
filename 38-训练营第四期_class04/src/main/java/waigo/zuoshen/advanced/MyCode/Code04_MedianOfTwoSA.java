package waigo.zuoshen.advanced.MyCode;

/**
 * author waigo
 * create 2021-06-16 20:01
 */
@SuppressWarnings("Duplicates")
public class Code04_MedianOfTwoSA {
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        if(nums1.length==0&&nums2.length==0) return 0;
        int len = nums1.length+nums2.length;
        int len_2 = len>>1;//下中点或者中点
        if(nums1.length==0){
            return (len&1)==0?((nums2[len_2]+nums2[len_2-1])*1.0)/2:nums2[len_2];
        }
        if(nums2.length==0){
            return (len&1)==0?((nums1[len_2]+nums1[len_2-1])*1.0)/2:nums1[len_2];
        }
        return (len&1)==0?((getKthMinNumber(nums1,nums2,len_2+1)+getKthMinNumber(nums1,nums2,len_2))*1.0)/2:getKthMinNumber(nums1,nums2,len_2+1);
    }
    //这里的K是序号
    public static int getKthMinNumber(int[] arr1, int[] arr2, int K) {
        if (arr1 == null || arr2 == null || K < 0 || K > arr1.length + arr2.length) return -1;//过滤违法输入
        //确保arr1是长数组
        int[] longArr = arr1.length > arr2.length ? arr1 : arr2;
        int[] shortArr = arr1.length > arr2.length ? arr2 : arr1;
        int longLen = longArr.length;
        int shortLen = shortArr.length;
        if (K <= shortLen) {
            return getUpMidium(shortArr, 0, K - 1, longArr, 0, K - 1);
        }
        int lhBegin = K - shortLen - 1;//长数组可能区的开始
        if (K <= longLen) {
            if (longArr[lhBegin] >= shortArr[shortLen - 1]) {
                return longArr[lhBegin];
            }
            return getUpMidium(shortArr, 0, shortLen - 1, longArr, lhBegin + 1, K - 1);
        }
        //扣一下最后一个区的左边界
        int shBegin = K - longLen - 1;
        if (shortArr[shBegin] >= longArr[longLen - 1]) {
            return shortArr[shBegin];
        }
        if (longArr[lhBegin] >= shortArr[shortLen - 1]) {
            return longArr[lhBegin];
        }
        return getUpMidium(shortArr, shBegin + 1, shortArr.length - 1, longArr, lhBegin + 1, longArr.length - 1);
    }

    //arr1的[l1...r1]和arr2的[l2....r2]一定等长
    private static int getUpMidium(int[] arr1, int l1, int r1, int[] arr2, int l2, int r2) {
        while (l1 < r1) {
            int mid1 = (l1 + r1) >> 1;
            int mid2 = (l2 + r2) >> 1;
            if (arr1[mid1] == arr2[mid2]) return arr1[mid1];
            boolean isEven = ((r1 - l1 + 1) & 1) == 0;
            if (arr1[mid1] > arr2[mid2]) {
                if (!isEven && arr2[mid2] >= arr1[mid1 - 1]) {
                    return arr2[mid2];
                }
                l2 = mid2+1;
                r1 = isEven ? mid1 : (mid1 - 1);
            } else {
                if (!isEven && arr1[mid1] >= arr2[mid2 - 1]) {
                    return arr1[mid1];
                }
                l1 = mid1 + 1;//别想当然，一定画图
                r2 = isEven ? mid2 : (mid2 - 1);
            }
        }
        return Math.min(arr1[r1], arr2[r2]);
    }

    public static void main(String[] args) {
        int[] a = {1,1,1,1,1,1,1,1,1,1,4,4};
        int[] b = {1,3,4,4,4,4,4,4,4,4,4};
        System.out.println(findMedianSortedArrays(b,a));
    }
}
