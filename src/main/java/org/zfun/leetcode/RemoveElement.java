package org.zfun.leetcode;

/*
Given an array and a value, remove all instances of that value in-place and return the new length.

Do not allocate extra space for another array, you must do this by modifying the input array in-place with O(1) extra memory.

The order of elements can be changed. It doesn't matter what you leave beyond the new length.

Example:

Given nums = [3,2,2,3], val = 3,

Your function should return length = 2, with the first two elements of nums being 2.
 */
public class RemoveElement {
    public int removeElement(int[] nums, int val) {
        int len = 0;
        
        // (0) empty string
        if (nums == null || nums.length == 0)
            return 0;
        
        for (int i=0; i<nums.length; i++) {
            if (nums[i] != val) {
                nums[len++] = nums[i];
            }
        }
        
        return len;
    }

    public void printNums(int[] nums, int len) {
        StringBuffer sb = new StringBuffer("[");
        for (int i=0; i<len; i++) {
            if (i > 0)
                sb.append(",");
            sb.append(nums[i]);
        }
        sb.append("]");
        System.out.println(sb.toString());
    }

    public static void main(String[] args) {
        RemoveElement obj = new RemoveElement();        
        
        int[] nums = null;
        int len = obj.removeElement(nums, 3);
        obj.printNums(nums,len);
        
        nums = new int[0];
        len = obj.removeElement(nums, 3);
        obj.printNums(nums,len);
        
        int[] nums1 = {3};
        len = obj.removeElement(nums1, 3);
        obj.printNums(nums1,len);
        
        int[] nums2 = {3, 2, 2, 3};
        len = obj.removeElement(nums2, 3);
        obj.printNums(nums2,len);
        
        int[] nums3 = {3, 2, 2, 3, 4};
        len = obj.removeElement(nums3, 3);
        obj.printNums(nums3,len);
    }

}
