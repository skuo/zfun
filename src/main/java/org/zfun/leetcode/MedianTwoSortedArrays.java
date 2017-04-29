package org.zfun.leetcode;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MedianTwoSortedArrays {

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        double median = 0;
        
        // calculate the element locations for the median
        int len1 = nums1.length;
        int len2 = nums2.length;
        int divident = (len1+len2) / 2;
        int remainder = (len1+len2) % 2;
        List<Integer> medIndexes = new LinkedList<>();
        if (remainder != 0) {
            medIndexes.add(divident+remainder);
        } else {
            medIndexes.add(divident);
            medIndexes.add(divident+1);
        }
        
        List<Integer> medians = new LinkedList<>();
        int count = 0;
        int idx1 = 0, idx2 = 0;
        while (idx1 < len1 && idx2 < len2) {
            count++; 
            if (nums1[idx1] <= nums2[idx2]) {
                idx1 = setMedians(nums1, medIndexes, medians, count, idx1);
            } else {
                idx2 = setMedians(nums2, medIndexes, medians, count, idx2);
            }
            // break if all medians are found
            if (medIndexes.size() == medians.size())
                break;
        }
        
        // Continue to find median in one of the remaining arrays
        if (medIndexes.size() != medians.size()) {
            // nums1 
            while(idx1 < len1) {
                count++;
                idx1 = setMedians(nums1, medIndexes, medians, count, idx1);
            }
            // nums2
            while(idx2 < len2) {
                count++;
                idx2 = setMedians(nums2, medIndexes, medians, count, idx2);
            }
        }
        
        // calculate median
        int sum = 0;
        for (Integer i : medians)
            sum += i;
        median = (1.0) * sum / medians.size();
        return median;
    }

    private int setMedians(int[] nums, List<Integer> medIndexes, List<Integer> medians, int count, int idx) {
        if (medIndexes.contains(count))
            medians.add(nums[idx]);
        return ++idx;
    }
    
    public static void main(String[] args) {
        MedianTwoSortedArrays obj = new MedianTwoSortedArrays();
        
        int[] nums1 = {1,3};
        int[] nums2 = {2};
        Double median = obj.findMedianSortedArrays(nums1, nums2);
        System.out.println("Median=" + median + " for " + Arrays.toString(nums1) + " " + Arrays.toString(nums2));
        
        int[] nums3 = {1,2};
        int[] nums4 = {3,4};
        median = obj.findMedianSortedArrays(nums3, nums4);
        System.out.println("Median=" + median + " for " + Arrays.toString(nums3) + " " + Arrays.toString(nums4));
        
        int[] nums5 = {1,2,7,9,10,11,12,13};
        int[] nums6 = {5,8};
        median = obj.findMedianSortedArrays(nums5, nums6);
        System.out.println("Median=" + median + " for " + Arrays.toString(nums5) + " " + Arrays.toString(nums6));
    }
}
