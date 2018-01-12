package org.zfun.leetcode;

/*
Given an unsorted integer array, find the first missing positive integer.

For example,
Given [1,2,0] return 3,
and [3,4,-1,1] return 2.
 */
public class FirstMissingPositive {
    public int firstMissingPositive(int[] nums) {
        // nums should be from [1..nums.length]
        int[] posInts = new int[nums.length+1];
        // set value in posInts
        for (int i=0; i<nums.length; i++) {
            if (0 < nums[i] && nums[i] <= nums.length)
                posInts[nums[i]] = nums[i];
        }
        // iterate and find the first missing posInt
        int missingPosInt = nums.length + 1;
        for (int i=1; i<=nums.length; i++) {
            if (posInts[i] == 0) {
                missingPosInt = i;
                break;
            }
        }
        return missingPosInt;
    }
    
    public static void main(String[] args) {
        FirstMissingPositive obj = new FirstMissingPositive();
        
        int[] nums = {1,2,0};
        int missingPos = obj.firstMissingPositive(nums);
        System.out.println(missingPos); // 3
    }
}
