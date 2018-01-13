package org.zfun.leetcode;

import java.util.Arrays;

/*
 Given an array of integers sorted in ascending order, find the starting and ending position of a given target value.

Your algorithm's runtime complexity must be in the order of O(log n).

If the target is not found in the array, return [-1, -1].

For example,
Given [5, 7, 7, 8, 8, 10] and target value 8,
return [3, 4].
 */
public class SearchForARange {
    public int[] searchRange(int[] nums, int target) {
        int[] range = {-1, -1};
        
        if (nums == null || nums.length == 0)
            return range;
        
        int pos = bSearch(nums, target, 0, nums.length-1);
        if (pos != -1) {
            for (int i=pos; i>= 0; i--) {
                if (nums[i] == target)
                    range[0] = i;
                else
                    break;
            }
            for (int i=pos; i < nums.length; i++) {
                if (nums[i] == target)
                    range[1] = i;
                else
                    break;
            }
        }
        
        return range;
    }
    
    public int bSearch(int[] nums, int target, int left, int right) {
        if (left <= right) {
            int pos = (left + right) / 2;
            if (nums[pos] == target)
                return pos;
            else if (target < nums[pos])
                return bSearch(nums, target, left, pos-1);
            else
                return bSearch(nums, target, pos+1, right);
        }
        
        return -1;
    }
    
    public void testBSearch(int[] nums) {
        int pos = bSearch(nums, 8, 0, nums.length-1);
        System.out.println(pos); // 3
        pos = bSearch(nums, 5, 0, nums.length-1);
        System.out.println(pos); // 0
        pos = bSearch(nums, 7, 0, nums.length-1);
        System.out.println(pos); // 1
        pos = bSearch(nums, 10, 0, nums.length-1);
        System.out.println(pos); // 5
        pos = bSearch(nums, 4, 0, nums.length-1);
        System.out.println(pos); // -1
        pos = bSearch(nums, 6, 0, nums.length-1);
        System.out.println(pos); // -1
        pos = bSearch(nums, 11, 0, nums.length-1);
        System.out.println(pos); // -1        
    }
    
    public static void main(String[] args) {
        SearchForARange obj = new SearchForARange();
        
        int[] nums = {5, 7, 7, 8, 8, 10};
        // obj.bsearch(nums);
        
        int[] range = obj.searchRange(nums, 8);
        System.out.println(Arrays.toString(range));
        range = obj.searchRange(nums, 7);
        System.out.println(Arrays.toString(range));
        range = obj.searchRange(nums, 5);
        System.out.println(Arrays.toString(range));
        range = obj.searchRange(nums, 10);
        System.out.println(Arrays.toString(range));
        range = obj.searchRange(nums, 4);
        System.out.println(Arrays.toString(range));
    }

}
