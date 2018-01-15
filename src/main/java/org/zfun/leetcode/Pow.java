package org.zfun.leetcode;
/*
Implement pow(x, n).

Example 1:

Input: 2.00000, 10
Output: 1024.00000
Example 2:

Input: 2.10000, 3
Output: 9.26100
 */
public class Pow {
    public double myPow(double x, int n) {
        if (x == 1)
            return 1;
        
        double factor = 1;
        // special handling
        if (n == Integer.MIN_VALUE) {
            n = Integer.MIN_VALUE + 1;
            factor = x;
        }
            
        
        // make every thing one way
        if (n < 0) {
            x = 1/x;
            n = -1 * n;
        }
        
        return factor * dfs(x, n);
    }
    
    public double dfs(double x, int n) {
        if (n == 0)
            return 1;
        
        double result = dfs(x, n/2);
        if (n%2 == 1) 
            return x * result * result;
        else 
            return result * result;
    }
    
    public static void main(String[] args) {
        Pow obj = new Pow();
        
        System.out.println(obj.myPow(2, 10)); // 1024
        System.out.println(obj.myPow(2.1, 3)); // 9.261
        
    }
}
