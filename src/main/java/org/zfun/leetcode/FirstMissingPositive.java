package org.zfun.leetcode;

/*
Given an unsorted integer array, find the first missing positive integer.

For example,
Given [1,2,0] return 3,
and [3,4,-1,1] return 2.
 */
public class FirstMissingPositive {
    public int firstMissingPositive(int[] nums) {
        if (nums == null || nums.length == 0)
            return 0;
        
        int sum = 0, min = Integer.MAX_VALUE, max = 0;
        for (int i=0; i<nums.length; i++) {
            if (nums[i] > 0) {
                sum += nums[i];
                min = Math.min(min, nums[i]);
                max = Math.min(max, nums[i]);
            }
        }
        
        int expectedTotal = (min+max)*(max-min+1)/2;
        int missingInt = expectedTotal - sum;
        
        // if missingInt == 0, then return max+1
        if (missingInt == 0)
            return max + 1;
        else
            return missingInt;
    }
    
    public static void main(String[] args) {
        FirstMissingPositive obj = new FirstMissingPositive();
        
        int[] nums = {1,2,0};
        int missingPos = obj.firstMissingPositive(nums);
    }
}
