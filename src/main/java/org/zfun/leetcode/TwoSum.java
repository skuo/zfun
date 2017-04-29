package org.zfun.leetcode;

import java.util.HashMap;
import java.util.Map;

public class TwoSum {
    public int[] twoSum(int[] nums, int target) {
        int[] answerArray = null;
        Map<Integer, Integer> complements = new HashMap<>();

        // case 0: nums is null
        if (nums == null || nums.length == 0)
            return answerArray;
        
        // case 1: nums is not null
        for (int i=0; i< nums.length; i++) {
            int a = nums[i];
            int b = target - nums[i];
            Integer index = complements.get(b);
            if (index == null) {
                // complement not found
                complements.put(a, Integer.valueOf(i));
            }
            else {
                // found a complement
                answerArray = new int[2];
                answerArray[0] = index;
                answerArray[1] = i;
                return answerArray;
            }
        }
        
        // no match, return a null array
        return answerArray;
    }
    
    public void print(int[] answers) {
        for (int i=0; i<answers.length; i++)
            System.out.print(i + ",");
    }
    
    public static void main(String[] args) {
        TwoSum obj = new TwoSum();
        int[] nums = {2, 7, 11, 15};
        int[] answers = obj.twoSum(nums, 9);
        obj.print(answers);
    }
}
