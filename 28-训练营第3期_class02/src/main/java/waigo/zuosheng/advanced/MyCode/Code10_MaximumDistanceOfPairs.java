package waigo.zuosheng.advanced.MyCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * author waigo
 * create 2021-05-09 10:48
 */
/*
You are given two non-increasing 0-indexed integer arrays nums1​​​​​​ and nums2​​​​​​.
A pair of indices (i, j), where 0 <= i < nums1.length and 0 <= j < nums2.length, is valid if both i <= j and nums1[i] <= nums2[j]. The distance of the pair is j - i​​​​.
Return the maximum distance of any valid pair (i, j). If there are no valid pairs, return 0.
An array arr is non-increasing if arr[i-1] >= arr[i] for every 1 <= i < arr.length.


Example 1:

Input: nums1 = [55,30,5,4,2], nums2 = [100,20,10,10,5]
Output: 2
Explanation: The valid pairs are (0,0), (2,2), (2,3), (2,4), (3,3), (3,4), and (4,4).
The maximum distance is 2 with pair (2,4).
Example 2:

Input: nums1 = [2,2,2], nums2 = [10,10,1]
Output: 1
Explanation: The valid pairs are (0,0), (0,1), and (1,1).
The maximum distance is 1 with pair (0,1).
Example 3:

Input: nums1 = [30,29,19,5], nums2 = [25,25,25,25,25]
Output: 2
Explanation: The valid pairs are (2,2), (2,3), (2,4), (3,3), and (3,4).
The maximum distance is 2 with pair (2,4).
Example 4:

Input: nums1 = [5,4], nums2 = [3,2]
Output: 0
Explanation: There are no valid pairs, so return 0.
 

Constraints:

1 <= nums1.length <= 10^5
1 <= nums2.length <= 10^5
1 <= nums1[i], nums2[j] <= 10^5
Both nums1 and nums2 are non-increasing.

思路：
方法一:遍历+二分
枚举nums1中所有位置的答案，找到最大值，最终答案必在其中
所以问题就转换成了如何求解nums2的(i....length)这个区间中<nums1[i]的最左位置在哪？
这可以nums2有序的特点使用二分法求解，所以这个方法时间复杂度为O(N1*log(N2)),空间复杂度O(1)

其实这种方法已经能通过了。

方法二:双指针
是否需要每次都去查找？上一个位置能不能给当前位置留下点啥？
因为这两个数组都是非递增的，所以很容易就想到能不能找到各个位置之间从右到左或者从左到右
求解的时候有没有单调性。

从左到右很明显是没有单调性的，因为i的移动，j可能也得移动
int[] a = {55,30,5,4,2};
int[] b = {100,20,10,10,5};
比如一开始，i在55这里，将j也只能推到100这里，下一步，i右移的时候，j也得右移
这就又得全部重新看了。

从右到左考虑是否有单调性？
j一开始已经在最后了，如果说j能够一直往前而不会再往后走了，那就爽了，找到单调性了。

最后一个i将j推到了某个位置，此时nums2[j+1]<nums1[i]或者j+1越界了
现在思考i-1位置，因为nums1[i-1]<=nums[i],所以此时nums2[j+1]必定<nums1[i-1]
也就是说i-1位置不可能将j推到它现在更加后面的位置，单调性就找到了。

根据这个单调性，使用两个指针将时间优化到了O(N1+N2),因为两个指针都不会回退
*/
public class Code10_MaximumDistanceOfPairs {
    public static int maxDistance(int[] nums1, int[] nums2) {
        int ans = 0;
        for(int i = 0;i<nums1.length;i++){
            ans = Math.max(
                    ans,mostRightBiggerTarget(nums2,i,nums2.length-1,nums1[i])-i
            );
        }
        return ans;
    }
    //找出在nums2上的，L...R中，<target的最左位置
    public static int mostRightBiggerTarget(int[] nums2,int L,int R,int target){
        if(L>=R) return L;
        int ans = R+1;
        while (L<=R){
            int mid = L+((R-L)>>1);
            if(nums2[mid]<target){
                ans=mid;
                R = mid-1;
            }else{
                //大于等于往右走,不记录答案
                L = mid+1;
            }
        }
        return ans-1;
    }

    //双指针方法
    public static int maxDistance2(int[] nums1, int[] nums2){
        int j = nums2.length-1;
        int ans = 0;
        for(int i = nums1.length-1;i>=0;i--){
            while (j-1>=i&&nums2[j]<nums1[i])
                j--;
            ans = Math.max(ans,j-i);
        }
        return ans;
    }
    public static void main(String[] args) {
        int[] a = {55,30,5,4,2};
        int[] b = {100,20,10,10,5};
        System.out.println(maxDistance(a,b));
        System.out.println(maxDistance2(a,b));

        int testTimes = 10000000;

    }
}
