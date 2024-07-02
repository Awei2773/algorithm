package waigo.zuoshen.advanced.MyCode;

/**
 * author waigo
 * create 2021-05-14 19:08
 */

/**
 * 给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 * 示例 1：
 *
 * 输入：nums = [-2,1,-3,4,-1,2,1,-5,4]
 * 输出：6
 * 解释：连续子数组 [4,-1,2,1] 的和最大，为 6 。
 * 示例 2：
 *
 * 输入：nums = [1]
 * 输出：1
 * 示例 3：
 *
 * 输入：nums = [0]
 * 输出：0
 * 示例 4：
 *
 * 输入：nums = [-1]
 * 输出：-1
 * 示例 5：
 *
 * 输入：nums = [-100000]
 * 输出：-100000
 *  
 * 提示：
 * 1 <= nums.length <= 3 * 10^4
 * -10^5 <= nums[i] <= 10^5
 *
 * 思路：
 * 思考答案具有哪些性质
 * 1.首先作为答案这个子数组肯定是和最大的
 * 2.假设这个答案不仅是和最大的，还是长度最长的
 * 这个答案子数组有哪些性质，它的左右肯定无法再变长了，也就是左右无法再加入新的元素，不管是多长的和都是负数
 * 所以如何能够把符合这个性质的子数组的和都抓住。
 * 由于左右都是负数，所以只要为负数就将和设置为0，重新去抓答案，最终终究会抓到那个和最大且最长的子数组的和。
 */
public class Code06_SubArrayMaxSum {
    public static int maxSubArray(int[] nums) {
        int max = Integer.MIN_VALUE;
        int cur = 0;
        for(int i = 0;i<nums.length;i++){
            cur+=nums[i];
            max = Math.max(cur,max);
            cur = cur<0?0:cur;
        }
        return max;
    }

    public static void main(String[] args) {
        int[] a = {-2,-3,-5,-1};
        System.out.println(maxSubArray(a));
    }
}
