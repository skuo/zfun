package org.zfun.leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

/*
Given a collection of candidate numbers (C) and a target number (T), find all unique combinations in C where the candidate numbers sums to T.

Each number in C may only be used once in the combination.

Note:
All numbers (including target) will be positive integers.
The solution set must not contain duplicate combinations.
For example, given candidate set [10, 1, 2, 7, 6, 1, 5] and target 8, 
A solution set is: 
[
  [1, 7],
  [1, 2, 5],
  [2, 6],
  [1, 1, 6]
]
 */
public class CombinationSum2 {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
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
        // sort condidates
        Collections.sort(eligibleCandidates);

        // do depth first search
        Map<Integer, List<Integer>> combSumMap = new HashMap<>();
        Stack<Integer> path = new Stack<>();
        Set<Integer> usedElements = new HashSet<>();
        dfs(eligibleCandidates, target, path, usedElements, combSumMap);

        // generate combSumList from combSumMap
        for (List<Integer> l : combSumMap.values())
            combSumList.add(l);
        return combSumList;
    }

    public void dfs(List<Integer> eligibleCandidates, int target, Stack<Integer> path, Set<Integer> usedElements,
            Map<Integer, List<Integer>> combSumMap) {
        for (int i = 0; i < eligibleCandidates.size(); i++) {
            Integer candidate = eligibleCandidates.get(i);
            if (usedElements.contains(i)) {
                // used already, skip
                continue;
            }
            else if (target - candidate == 0) {
                // matched
                List<Integer> list = new ArrayList<>(path);
                list.add(candidate);
                Collections.sort(list);
                // remove duplicate
                combSumMap.put(list.hashCode(), list);
            } else if (target - candidate > 0) {
                // 
                path.push(candidate);
                usedElements.add(i);
                dfs(eligibleCandidates, target-candidate, path, usedElements, combSumMap);
                path.pop();
                usedElements.remove(i);
            } else {
                // candidate is bigger than target, stop search
                return;
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
        System.out.println("]\n");
    }

    public static void main(String[] args) {
        CombinationSum2 obj = new CombinationSum2();
        
        int[] candidates = {2, 3,6, 7};
        List<List<Integer>> combSumList = obj.combinationSum2(candidates, 7);
        obj.format(combSumList);
        
        int[] candidates2 = {10, 1, 2, 7, 6, 1, 5};
        combSumList = obj.combinationSum2(candidates2, 8);
        obj.format(combSumList);
        
        
    }

}
