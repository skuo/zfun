package org.zfun.leetcode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ThreeSum {
    public List<List<Integer>> threeSum(int[] nums, int target) {
        List<List<Integer>> answers = new LinkedList<>();
        if (nums.length < 3)
            return answers;
     
        // sort nums
        Arrays.sort(nums);
        // Loop and move l and r closer to each other
        Set<String> listStrings = new HashSet<>();
        for (int i=0; i<nums.length-2; i++) {
            int l = i+1;
            int r = nums.length-1;
            while(l < r) {
                //System.out.println(nums[i] + "," + nums[l] + "," + nums[r]);
                int sum = nums[i] + nums[l] + nums[r];
                if (sum == target) {
                    List<Integer> intList = Arrays.asList(nums[i], nums[l], nums[r]); 
                    String listString = intList.toString();
                    // only keep unique triplets
                    if (!listStrings.contains(listString)) {
                        listStrings.add(listString);
                        answers.add(intList);
                    }
                    l++;
                } else if (sum > target) {
                    r--;
                } else if (sum < target) {
                    l++;
                }
            }
        }
        return answers;
    }
    
    public void print(List<List<Integer>> answers) {
        for (List<Integer> intList : answers) {
            for (int i : intList)
                System.out.print(i + ",");
            System.out.println();
        }        
    }
    
    public static void main(String[] args) {
        ThreeSum obj = new ThreeSum();
        int[] nums = new int[] {-1, 0, 1, 2, -1, -4};
        List<List<Integer>> answers = obj.threeSum(nums,0);
        obj.print(answers);
    }
}
