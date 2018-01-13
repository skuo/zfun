package org.zfun.leetcode;
/*
Find the contiguous subarray within an array (containing at least one number) which has the largest sum.

For example, given the array [-2,1,-3,4,-1,2,1,-5,4],
the contiguous subarray [4,-1,2,1] has the largest sum = 6.
 */
public class MaxSubArray {
    public int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0)
            return 0;
        
        // find the max 
        int max = Integer.MIN_VALUE;
        for (int i=0; i<nums.length; i++) 
            max = Math.max(max, nums[i]);
        
        // if all ints are negative or zero, then max is the best we can do
        if (max <= 0)
            return max;
        
        int sum = 0;
        for (int i=nums.length-1; i>=0; i--) {
            sum += nums[i];
            if (sum > max)
                max = sum;
            if (sum < 0)
                sum = 0;
        }
        
        return max;
    }

    public static void main(String[] args) {
        MaxSubArray obj = new MaxSubArray();
        
        int max = obj.maxSubArray(new int[] {-2, 1, -3, 4, -1, 2, 1, -5, 4});
        System.out.println(max); // 6
    }
}
