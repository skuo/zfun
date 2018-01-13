package org.zfun.leetcode;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/*
Given a matrix of m x n elements (m rows, n columns), return all elements of the matrix in spiral order.

For example,
Given the following matrix:

[
 [ 1, 2, 3 ],
 [ 4, 5, 6 ],
 [ 7, 8, 9 ]
]
You should return [1,2,3,6,9,8,7,4,5].
 */
public class SpiralMatrix {
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> list = new LinkedList<>();
        if (matrix == null || matrix.length == 0)
            return list;
        
        int numRows = matrix.length;
        int numColumns = matrix[0].length;
        int totalInts = numRows * numColumns;
        int level = 0;
        int x = 0, y = 0;
        String direction = "right"; // right -> down -> left -> up
        for (int i=0; i<totalInts; i++) {
            list.add(matrix[x][y]);
            if (direction.equals("right")) {
                if (y < numColumns-level-1) 
                    y += 1;
                else {
                    direction = "down";
                    x += 1;
                }
            } else if (direction.equals("down")) {
                if (x < numRows-level-1)
                    x += 1;
                else {
                    direction = "left";
                    y -= 1;
                }
            } else if (direction.equals("left")) {
                if (y > level)
                    y -= 1;
                else {
                    direction = "up";
                    x -= 1;
                }
            } else {
                if (x > level+1)
                    x -= 1;
                else {
                    direction = "right";
                    y += 1;
                    level++;
                }
            }
        }
        
        return list;
    }
    
    public static void main(String[] args) {
        SpiralMatrix obj = new SpiralMatrix();
        
        int[][] matrix = new int[4][4];
        matrix[0][0] = 1;
        matrix[0][1] = 2;
        matrix[0][2] = 3;
        matrix[0][3] = 4;
        matrix[1][0] = 5;
        matrix[1][1] = 6;
        matrix[1][2] = 7;
        matrix[1][3] = 8;
        matrix[2][0] = 9;
        matrix[2][1] = 10;
        matrix[2][2] = 11;
        matrix[2][3] = 12;
        matrix[3][0] = 13;
        matrix[3][1] = 14;
        matrix[3][2] = 15;
        matrix[3][3] = 16;
        
        List<Integer> list = obj.spiralOrder(matrix);
        System.out.println(list.stream()
                .map(Object::toString)
                .collect(Collectors.joining(",", "[", "]"))
                );
    }
}
