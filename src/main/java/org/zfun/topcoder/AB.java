package org.zfun.topcoder;

/**
You are given two ints: N and K. We are interested in strings that satisfy the following conditions:

The string has exactly N characters, each of which is either 'A' or 'B'.
The string s has exactly K pairs (i, j) (0 <= i < j <= N-1) such that s[i] = 'A' and s[j] = 'B'.
If there exists a string that satisfies the conditions, find and return any such string. Otherwise, return an empty string.

Constraints
- N will be between 2 and 50, inclusive.
- K will be between 0 and N(N-1)/2, inclusive.
*/
public class AB {

    private static char[] AB = "AB".toCharArray();
    
    public String createString(int n, int k) {
        char[] chars = new char[n];
        
        // Check the constraints
        if (n< 2 || n > 50)
            return null;
        if (k < 0 || k > n*(n-1)/2)
            return null;
        
        // max score
        int maxScore = (n/2) * (n - (n/2));
        if (k > maxScore)
            return null;
        
        // initialize all chars to 'X'
        for (int i=0; i<n; i++)
            chars[i] = 'X';
        
        // this is basically binary search starting at char[n-1]
        chars[n-1] = 'B';
        chars = dfs(chars, n-1, n, k);
        if (chars == null)
            return null;
        else 
            return new String(chars);
    }
    
    public int score(char[] chars) {
        int score = 0;
        int numBSeen = 0;
        
        for (int i=chars.length-1; i>=0; i--) {
            if (chars[i] == 'B')
                numBSeen++;
            else if (chars[i] == 'A')
                score += numBSeen;
        }
        
        return score;
    }
    
    public char[] dfs(char[] chars, int level, int n, int k) {
        // level is from n-1 to 0, char[n-1] is already assigned to 'B', because 'A' at the end does not affect the score
        if (level <= 0)
            return null;
        
        // assign next level
        level--;
        // try A then B, calculate the score
        for (char c : AB) {
            chars[level] = c;
            int score = score(chars);
            // termination condition
            if (score == k)
                return chars;
            // dfs
            char[] charArray = dfs(chars, level, n, k);
            if (charArray == null) {
                chars[level] = 'X';
            }
            else
                return chars;
        }

        return null;
    }
    
    public static void main(String[] args) {
        AB obj = new AB();
        
        String str;
        int n = 3, k = 2;
        str = obj.createString(n, k);
        System.out.println(String.format("str=%s, n=%d, k=%d", str, n, k));

        n = 2;
        k = 0;
        str = obj.createString(n, k);
        System.out.println(String.format("str=%s, n=%d, k=%d", str, n, k));

        n = 5;
        k = 8;
        str = obj.createString(n, k);
        System.out.println(String.format("str=%s, n=%d, k=%d", str, n, k));

        n = 10;
        k = 12;
        str = obj.createString(n, k);
        System.out.println(String.format("str=%s, n=%d, k=%d", str, n, k));

        n = 10;
        k = 13;
        str = obj.createString(n, k);
        System.out.println(String.format("str=%s, n=%d, k=%d", str, n, k));

        n = 10;
        k = 31;
        str = obj.createString(n, k);
        System.out.println(String.format("str=%s, n=%d, k=%d", str, n, k));
    }
}
