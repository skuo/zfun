package org.zfun.leetcode;

import java.util.Arrays;

/*
 Implement next permutation, which rearranges numbers into the lexicographically next greater permutation of numbers.

If such arrangement is not possible, it must rearrange it as the lowest possible order (ie, sorted in ascending order).

The replacement must be in-place, do not allocate extra memory.

Here are some examples. Inputs are in the left-hand column and its corresponding outputs are in the right-hand column.
1,2,3 → 1,3,2
3,2,1 → 1,2,3
1,1,5 → 1,5,1
 */
public class NextPermutation {
    public void nextPermutation(int[] nums) {
        // Empty array or array of 1 element
        if (nums == null || nums.length <= 1)
            return;
        
        // start with the second least significant digit and permutate
        int index = -1;
        for (int i=nums.length-2; i >=0; i--) {
            int leastMaxIndex = -1, leastMax = Integer.MAX_VALUE;
            for (int j=i+1; j < nums.length; j++) {
                if (nums[i] < nums[j]) {
                    if (leastMax > nums[j]) {
                        leastMax = nums[j];
                        leastMaxIndex = j;
                    }
                }
            }
            if (leastMaxIndex != -1) {
                // swap
                int x = nums[leastMaxIndex];
                nums[leastMaxIndex] = nums[i];
                nums[i] = x;
                index = i;
                break;
            }
        }
               
        if (index != -1) {
            // sort the rest of the digits, and replace
            int[] restNums = new int[nums.length - index - 1];
            for (int j = 0; j < nums.length - index - 1; j++)
                restNums[j] = nums[index + j + 1];
            Arrays.sort(restNums);
            for (int j = 0; j < nums.length - index - 1; j++)
                nums[index + j + 1] = restNums[j];
        } else {
            // if not permutated, then reverse the sorted array
            int start = 0, end = nums.length - 1;
            while (start < end) {
                int last = nums[end];
                nums[end] = nums[start];
                nums[start] = last;
                start++;
                end--;
            }
        }
    }

    public static void main(String[] args) {
        NextPermutation obj = new NextPermutation();
        int[] nums12 = {2,3,0,2,4,1}; // [2,3,0,4,1,2]
        obj.nextPermutation(nums12);
        System.out.println(Arrays.toString(nums12));
        
        int[] nums11 = { 4, 2, 0, 2, 3, 2, 0 }; // [4,2,0,3,0,2,2]
        obj.nextPermutation(nums11);
        System.out.println(Arrays.toString(nums11));

        int[] nums10 = { 2, 3, 1 }; // 312
        obj.nextPermutation(nums10);
        System.out.println(Arrays.toString(nums10));

        int[] nums8 = { 2, 3, 2 }; // 322
        obj.nextPermutation(nums8);
        System.out.println(Arrays.toString(nums8));

        int[] nums9 = { 1, 3, 2 }; // 213
        obj.nextPermutation(nums9);
        System.out.println(Arrays.toString(nums9));

        int[] nums = null; // null
        obj.nextPermutation(nums);
        System.out.println(Arrays.toString(nums));

        int[] nums1 = {};
        obj.nextPermutation(nums1); // []
        System.out.println(Arrays.toString(nums1));

        int[] nums2 = { 1 };
        obj.nextPermutation(nums2); // 1
        System.out.println(Arrays.toString(nums2));

        int[] nums3 = { 1, 2, 3 }; // 132
        obj.nextPermutation(nums3);
        System.out.println(Arrays.toString(nums3));

        int[] nums4 = { 3, 2, 1 }; // 123
        obj.nextPermutation(nums4);
        System.out.println(Arrays.toString(nums4));

        int[] nums5 = { 1, 1, 5 }; // 151
        obj.nextPermutation(nums5);
        System.out.println(Arrays.toString(nums5));

        int[] nums6 = { 1, 5 }; // 51
        obj.nextPermutation(nums6);
        System.out.println(Arrays.toString(nums6));

        int[] nums7 = { 5, 4, 2, 1 }; // 1245
        obj.nextPermutation(nums7);
        System.out.println(Arrays.toString(nums7));
    }
}
