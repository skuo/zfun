package org.zfun.leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
Given a collection of intervals, merge all overlapping intervals.

For example,
Given [1,3],[2,6],[8,10],[15,18],
return [1,6],[8,10],[15,18].
 */
public class MergeIntervals {
    public class Interval {
        int start;
        int end;

        Interval() {
            start = 0;
            end = 0;
        }

        Interval(int s, int e) {
            start = s;
            end = e;
        }
    }
    
    public List<Interval> merge(List<Interval> intervals) {
        if (intervals == null || intervals.size() == 0)
            return intervals;
        
        // sort intervals
        List<Interval> sortedIntervals = intervals.stream()
            .sorted((i1, i2) -> {
                return i1.start - i2.start;
            })
            .collect(Collectors.toList());
        
        // iterate and merge
        List<Interval> mergedList = new ArrayList<>();
        Interval first = sortedIntervals.get(0);
        for (int i=1; i<sortedIntervals.size(); i++) {
            Interval second = sortedIntervals.get(i);
            // does it overlaps
            if (first.end >= second.start) {
                first.end = Math.max(first.end, second.end);
            } else {
                mergedList.add(first);
                first = second;
            }
        }
        // add the remaining first
        mergedList.add(first);
        return mergedList;   
    }
    
    public static void main(String[] args) {
        MergeIntervals obj = new MergeIntervals();
        
        List<Interval> intervals = new ArrayList<>();
        intervals.add(obj.new Interval(1,3));
        intervals.add(obj.new Interval(2,6));
        intervals.add(obj.new Interval(8,10));
        intervals.add(obj.new Interval(15,18));
        List<Interval> mergedIntervals = obj.merge(intervals);
        System.out.println(mergedIntervals.stream()
                .map(i -> "[" + i.start + "," + i.end + "]")
                .collect(Collectors.joining(", ", "[", "]"))
                );
    }
}

