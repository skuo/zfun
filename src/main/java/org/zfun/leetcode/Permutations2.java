package org.zfun.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
Given a collection of numbers that might contain duplicates, return all possible unique permutations.

For example,
[1,1,2] have the following unique permutations:
[
  [1,1,2],
  [1,2,1],
  [2,1,1]
]
*/
public class Permutations2 {
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> permutations = new ArrayList<>();

        // handle empty array
        if (nums == null || nums.length == 0)
            return permutations;

        // Create a numsIndex to permute
        int[] numsIndex = new int[nums.length];
        for (int i=0; i<nums.length; i++)
            numsIndex[i] = i;
        
        Map<Integer, List<Integer>> permutationMap = new HashMap<>();
        List<Integer> path = new ArrayList<>();
        dfs(numsIndex, nums, path, permutationMap);        

        for (List<Integer> l : permutationMap.values())
            permutations.add(l);
        
        return permutations;
    }

    public void dfs(int[] numsIndex, int[] nums, List<Integer> path, Map<Integer, List<Integer>> permutationMap) {
        for (int i=0; i< numsIndex.length; i++) {
            int index = numsIndex[i];
            if (!path.contains(index)) {
                path.add(index);
                if (path.size() < numsIndex.length) 
                    dfs(numsIndex, nums, path, permutationMap);
                else {
                    // turn index into value
                    List<Integer> l = new ArrayList<>();
                    for (int j=0; j<path.size(); j++)
                        l.add(nums[path.get(j)]);
                    permutationMap.put(l.hashCode(), l);
                }
                path.remove(path.size()-1);
            }
        }
    }
    
    public void print(List<List<Integer>> permutations) {
        System.out.println(permutations.stream()
                .map(l -> l.stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(",", "[", "]")
                        ))
                .collect(Collectors.joining("\n", "[\n", "\n]"))
                );
    }
    
    public static void main(String[] args) {
        Permutations2 obj = new Permutations2();
        
        int[] nums = {1, 1, 2};
        List<List<Integer>> permutations = obj.permuteUnique(nums);
        obj.print(permutations);
        
    }

}
