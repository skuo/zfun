package org.zfun.leetcode;

/*
Given nums = [1,1,2],

Your function should return length = 2, with the first two elements of nums being 1 and 2 respectively.
It doesn't matter what you leave beyond the new length.
*/
public class RemoveDuplicates {
    public int removeDuplicates(int[] nums) {
        int len = 0;
        
        // (1) empty list
        if (nums == null || nums.length == 0)
            return 0;
        
        len = 1;
        int last = nums[0];
        for (int i=1; i<nums.length; i++) {
            if (nums[i] != last) {
                nums[len++] = nums[i];
                last = nums[i];
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
        RemoveDuplicates obj = new RemoveDuplicates();        
        
        int[] nums = null;
        int len = obj.removeDuplicates(nums);
        obj.printNums(nums,len);
        
        nums = new int[0];
        len = obj.removeDuplicates(nums);
        obj.printNums(nums,len);
        
        int[] nums1 = {1, 1, 2};
        len = obj.removeDuplicates(nums1);
        obj.printNums(nums1,len);
        
        int[] nums2 = {1, 1, 2, 3, 4, 4, 4, 5};
        len = obj.removeDuplicates(nums2);
        obj.printNums(nums2,len);
    }

}
