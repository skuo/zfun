package org.zfun.leetcode;
/*
You are given an n x n 2D matrix representing an image.

Rotate the image by 90 degrees (clockwise).

Note:
You have to rotate the image in-place, which means you have to modify the input 2D matrix directly. DO NOT allocate another 2D matrix and do the rotation.

Example 1:

Given input matrix = 
[
  [1,2,3],
  [4,5,6],
  [7,8,9]
],

rotate the input matrix in-place such that it becomes:
[
  [7,4,1],
  [8,5,2],
  [9,6,3]
]
Example 2:

Given input matrix =
[
  [ 5, 1, 9,11],
  [ 2, 4, 8,10],
  [13, 3, 6, 7],
  [15,14,12,16]
], 

rotate the input matrix in-place such that it becomes:
[
  [15,13, 2, 5],
  [14, 3, 4, 1],
  [12, 6, 8, 9],
  [16, 7,10,11]
]

 */
public class RotateImage {
    public void rotate(int[][] matrix) {
        // handle null or matrix with empty row
        if (matrix == null || matrix.length == 0)
            return;
        
        int dimension = matrix.length;
        int numRingsToRotate = dimension / 2;
        
        // divide matrix into rings
        for (int ring=0; ring<numRingsToRotate; ring++) {
            int ringDimension = dimension - (2*ring);
            int startX = ring, startY = ring;
            int x = 0, y = 0;
            for (int loc=0; loc<ringDimension-1; loc++) {
                x = 0;
                y = loc;
                int first = matrix[x+startX][y+startY];
                // for each location, rotate 4 times.
                for(int j=0; j< 4; j++) { 
                    // find the rotated coord
                    int newX = y;
                    int newY = ringDimension - x -1;
                    int second = matrix[newX+startX][newY+startY];
                    matrix[newX+startX][newY+startY] = first;
                    first = second;
                    x = newX;
                    y = newY;
                }                
            }
        }        
    }

    public void print(int[][] matrix) {
        System.out.println("[");
        int dim = matrix.length;
        for (int i=0; i<dim; i++) {
            StringBuilder sb = new StringBuilder("[");
            for (int j=0; j<dim; j++)
                sb.append(matrix[i][j] + " ");
            sb.append("]");
            System.out.println(sb.toString());
        }
        System.out.println("]\n");
    }
    
    public static void main(String[] args) {
        RotateImage obj = new RotateImage();
        
        int[][] matrix = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };        
        obj.rotate(matrix);
        obj.print(matrix);
        
        int[][] matrix2 = {
                { 5, 1, 9,11},
                { 2, 4, 8,10},
                {13, 3, 6, 7},
                {15,14,12,16}
        };
        obj.rotate(matrix2);
        obj.print(matrix2);

    }
}
