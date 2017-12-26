package org.zfun.leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
Given a collection of distinct numbers, return all possible permutations.

For example,
[1,2,3] have the following permutations:
[
  [1,2,3],
  [1,3,2],
  [2,1,3],
  [2,3,1],
  [3,1,2],
  [3,2,1]
]
 */
public class Permutations {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> permutations = new ArrayList<>();
        
        // handle empty array
        if (nums == null || nums.length == 0)
            return permutations;

        List<Integer> path = new ArrayList<>();
        dfs(nums, path, permutations);
        
        return permutations;
    }
    
    public void dfs(int[] nums, List<Integer> path, List<List<Integer>> permutations) {
        for (int i=0; i< nums.length; i++) {
            int num = nums[i];
            if (!path.contains(num)) {
                path.add(num);
                if (path.size() < nums.length) 
                    dfs(nums, path, permutations);
                else 
                    permutations.add(new ArrayList<>(path));
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
        Permutations obj = new Permutations();
        
        int[] nums = {1, 2, 3};
        List<List<Integer>> permutations = obj.permute(nums);
        obj.print(permutations);
        
    }

}
