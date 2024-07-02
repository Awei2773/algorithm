package waigo.zuosheng.advanced.MyCode;

/**
 * author waigo
 * create 2021-06-12 11:57
 */

/**
 * Given an integer array nums where every element appears three times except for one
 * , which appears exactly once. Find the single element and return it.
 *
 * You must implement a solution with a linear runtime complexity and use only constant extra space.
 *
 *  
 *
 * Example 1:
 *
 * Input: nums = [2,2,3,2]
 * Output: 3
 * Example 2:
 *
 * Input: nums = [0,1,0,1,0,1,99]
 * Output: 99
 *  
 *
 * Constraints:
 *
 * 1 <= nums.length <= 3 * 104
 * -231 <= nums[i] <= 231 - 1
 * Each element in nums appears exactly three times except for one element which appears once.
 */
public class Code06_SingleNumberII {
    public static int singleNumber(int[] nums) {
        int ans = 0;
        for(int i = 0;i<32;i++){
            //为每一位都进行一个不进位的加和，这是K进制的异或
            int bit = 0;
            for(int j = 0;j<nums.length;j++){
                //nums[i]      (nums[i]>>i)&1-->当前操作的那一位
                bit+=(nums[j]>>i)&1;
            }
            bit %= 3;
            ans+=(bit<<i);
        }
        return ans;
    }

    public static void main(String[] args) {

        System.out.println(singleNumber(new int[]{0,1,0,1,0,1,99}));
    }
}
