package org.zfun.leetcode;

/*
Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.

(i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).

You are given a target value to search. If found in the array return its index, otherwise return -1.

You may assume no duplicate exists in the array.
 */
public class SearchRotatedSortedArray {
    public int search(int[] nums, int target) {
        if (nums == null || nums.length == 0)
            return -1;
        
        return bSearch(nums, target, 0, nums.length-1);
    }
    
    private int bSearch(int[] nums, int target, int left, int right) {
        if (left > right)
            return -1;
                
        int pos = (left+right)/2;
        if (nums[pos] == target)
            return pos;
        
        // find the segment that has pivot
        if (nums[left] > nums[pos]) {
            // left segment has the pivot or pos is the pivot
            // right segment is in ascending order
            if (nums[pos] <= target && target <= nums[right])
                return bSearch(nums, target, pos+1, right);
            else
                return bSearch(nums, target, left, pos-1);
        } else {
            // right segment has the pivot or pos is the pivot
            // left segment is in ascending order
            if (nums[left] <= target && target <= nums[pos])
                return bSearch(nums, target, left, pos-1);
            else
                return bSearch(nums, target, pos+1, right);
        }
    }
    
    public static void main(String[] args) {
        SearchRotatedSortedArray obj = new SearchRotatedSortedArray();
        int found = -1;

        int[] nums2 = {5, 1, 3};
        found = obj.search(nums2, 3);
        System.out.println("found=" + found);  // 2

        
        int[] nums = {4, 5, 6, 7, 10, 0, 1, 2};
        
        found = obj.search(nums, 8);
        System.out.println("found=" + found);  // -1

        found = obj.search(nums, 3);
        System.out.println("found=" + found);  // -1

        found = obj.search(nums, 4); 
        System.out.println("found=" + found);  // 0

        found = obj.search(nums, 1);
        System.out.println("found=" + found);  // 6
        
        found = obj.search(nums, 5);
        System.out.println("found=" + found);  // 1

        found = obj.search(nums, 10);
        System.out.println("found=" + found);  // 4

    }
}
