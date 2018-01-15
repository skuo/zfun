package org.zfun.leetcode;

import java.util.LinkedList;
import java.util.List;

/*
Given a m x n grid filled with non-negative numbers, find a path from top left to bottom right which minimizes the sum of all numbers along its path.

Note: You can only move either down or right at any point in time.

Example 1:
[[1,3,1],
 [1,5,1],
 [4,2,1]]
Given the above grid map, return 7. Because the path 1→3→1→1→1 minimizes the sum.
 */
public class MinPathSum {
    public int minPathSum(int[][] grid) {
        if (grid == null || grid.length == 0)
            return 0;

        int[][] minGridSum = new int[grid.length][grid[0].length];
        List<Integer> sums = new LinkedList<>();
        int x = 0, y = 0, sum = 0;
        dfs(grid, sum, x, y, sums, minGridSum);
        
        int min = Integer.MAX_VALUE;
        for (Integer s : sums)
            min = Math.min(min, s);
        return min;
    }
    
    public void dfs(int[][] grid, int sum, int x, int y, List<Integer> sums, int[][] minGridSum) {
        sum += grid[x][y];
        // have we been here with smaller sum?
        if (minGridSum[x][y] != 0 && minGridSum[x][y] <= sum)
            return;
        else
            minGridSum[x][y] = sum;
        
        if (x==grid.length-1 && y==grid[0].length-1) {
            // destination
            sums.add(sum);
            return;
        }
        
        // move down if possible
        if (x+1 < grid.length) 
            dfs(grid, sum, x+1, y, sums, minGridSum);
        // move right if possible
        if (y+1 < grid[0].length) 
            dfs(grid, sum, x, y+1, sums, minGridSum);
    }
    
    public static void main(String[] args) {
        MinPathSum obj = new MinPathSum();
        
        int[][] grid = new int[3][3];
        grid[0][0] = 1;
        grid[0][1] = 3;
        grid[0][2] = 1;
        grid[1][0] = 1;
        grid[1][1] = 5;
        grid[1][2] = 1;
        grid[2][0] = 4;
        grid[2][1] = 2;
        grid[2][2] = 1;
        
        System.out.println(obj.minPathSum(grid)); // 7
    }
}
