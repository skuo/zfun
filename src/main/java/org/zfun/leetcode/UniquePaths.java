package org.zfun.leetcode;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/*
A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram below).

The robot can only move either down or right at any point in time. The robot is trying to reach the bottom-right corner 
of the grid (marked 'Finish' in the diagram below).

How many possible unique paths are there?

Above is a 3 x 7 grid. How many possible unique paths are there?

Note: m and n will be at most 100.
 */

// Submission Result: Time Limit ExceededMore Details 
//Last executed input:
//23
//12

public class UniquePaths {
    public int uniquePaths(int m, int n) {
        if (m <= 0 || n <= 0)
            return 0;

        int[][] matrix = new int[100][100];
        matrix[0][0] = 1;
        // bfs
        Queue<List<Integer>> coords = new LinkedList<>();
        coords.add(Arrays.asList(1, 0));
        coords.add(Arrays.asList(0, 1));
        while (!coords.isEmpty()) {
            List<Integer> coord = coords.remove();
            int x = coord.get(0);
            int y = coord.get(1);
            int sum = matrix[x][y];
            if (sum != 0)
                // already calculated
                continue;
            if (x - 1 >= 0)
                sum += matrix[x - 1][y];
            if (y - 1 >= 0)
                sum += matrix[x][y - 1];
            matrix[x][y] = sum;

            // find the next coords
            if (x< m-1)
                coords.add(Arrays.asList(x+1, y));
            if (y < n-1)
                coords.add(Arrays.asList(x, y+1));
        }
        return matrix[m-1][n-1];
    }

    public int uniquePaths1(int m, int n) {
        if (m <= 0 || n <= 0)
            return 0;

        int x = 0, y = 0, count = 0;
        count = dfs(m, n, x, y, count);

        return count;
    }

    public int dfs(int m, int n, int x, int y, int count) {
        // termination condition
        // once the robot is at the bottom or the right edge, there
        // is no more degree of freedom
        if (x == m - 1 || y == n - 1) {
            count = count + 1;
            return count;
        }

        // go down (x+1) if possible
        if (x < m - 1)
            count = dfs(m, n, x + 1, y, count);

        // go right (y+1) if possible
        if (y < n - 1)
            count = dfs(m, n, x, y + 1, count);

        return count;
    }

    public static void main(String[] args) {
        UniquePaths obj = new UniquePaths();

        System.out.println(obj.uniquePaths(2, 3)); // 3
        System.out.println(obj.uniquePaths(5, 5)); // 70
        System.out.println(obj.uniquePaths(23, 12)); // 193536720

        //System.out.println(obj.uniquePaths1(5, 5)); // 70
        //System.out.println(obj.uniquePaths1(23, 12)); // 193536720
    }
}
