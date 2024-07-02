package waigo.zuosheng.advanced.MyCode;

import java.util.*;

/**
 * author waigo
 * create 2021-05-09 10:22
 */
/*
Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.
You may assume that each input would have exactly one solution, and you may not use the same element twice.
You can return the answer in any order.
 

Example 1:

Input: nums = [2,7,11,15], target = 9
Output: [0,1]
Output: Because nums[0] + nums[1] == 9, we return [0, 1].
Example 2:

Input: nums = [3,2,4], target = 6
Output: [1,2]
Example 3:

Input: nums = [3,3], target = 6
Output: [0,1]
*/
public class Code06_TwoSum {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer,Integer> valueToIndex = new HashMap<>();
        for(int i = 0;i<nums.length;i++){
            if(valueToIndex.containsKey(target-nums[i])){
                return new int[]{valueToIndex.get(target-nums[i]),i};
            }
            valueToIndex.put(nums[i],i);
        }
        return new int[]{};
    }

}
