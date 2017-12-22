package org.zfun.leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

/*
Given a set of candidate numbers (C) (without duplicates) and a target number (T), find all unique combinations in C where the candidate numbers sums to T.

The same repeated number may be chosen from C unlimited number of times.

Note:
All numbers (including target) will be positive integers.
The solution set must not contain duplicate combinations.
For example, given candidate set [2, 3, 6, 7] and target 7, 
A solution set is: 
[
  [7],
  [2, 2, 3]
]
 */
public class CombinationSum {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> combSumList = new ArrayList<>();

        if (candidates == null || candidates.length == 0)
            return combSumList;

        List<Integer> eligibleCandidates = new ArrayList<>();
        for (int i = 0; i < candidates.length; i++) {
            if (candidates[i] <= target)
                eligibleCandidates.add(candidates[i]);
        }
        // Make sure eligibleCandidates are not empty
        if (eligibleCandidates.size() == 0)
            return combSumList;
        // sort condidates desc
        Collections.sort(eligibleCandidates);

        // do depth first search
        Map<Integer, List<Integer>> combSumMap = new HashMap<>();
        Stack<Integer> path = new Stack<>();
        dfs(eligibleCandidates, target, path, combSumMap);

        // generate combSumList from combSumMap
        for (List<Integer> l : combSumMap.values())
            combSumList.add(l);
        return combSumList;
    }

    public void dfs(List<Integer> eligibleCandidates, int target, Stack<Integer> path,
            Map<Integer, List<Integer>> combSumMap) {
        for (int i = 0; i < eligibleCandidates.size(); i++) {
            Integer candidate = eligibleCandidates.get(i);
            if (target - candidate == 0) {
                // matched
                List<Integer> list = new ArrayList<>(path);
                list.add(candidate);
                Collections.sort(list);
                // remove duplicate
                combSumMap.put(list.hashCode(), list);
            } else if (target - candidate > 0) {
                // 
                path.push(eligibleCandidates.get(i));
                dfs(eligibleCandidates, target-candidate, path, combSumMap);
                path.pop();
            }
        }
    }

    public void format(List<List<Integer>> combSumList) {
        System.out.println("[");
        for (List<Integer> l : combSumList) {
            System.out.println(l.stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(",", "[", "]"))
                    );
        }
        System.out.println("]");
    }

    public static void main(String[] args) {
        CombinationSum obj = new CombinationSum();
        
        int[] candidates = {2, 3,6, 7};
        List<List<Integer>> combSumList = obj.combinationSum(candidates, 7);
        obj.format(combSumList);
    }
}
