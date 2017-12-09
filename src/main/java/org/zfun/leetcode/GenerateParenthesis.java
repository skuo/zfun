package org.zfun.leetcode;

import java.util.ArrayList;
import java.util.List;

public class GenerateParenthesis {
    /*
    Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.

    For example, given n = 3, a solution set is:

    [
      "((()))",
      "(()())",
      "(())()",
      "()(())",
      "()()()"
    ]
    */
    public List<String> generateParenthesis(int n) {
        List<String> parens = new ArrayList<>();
        
        int numOpens = n, numCloses = n;
        String path = "";
        if (n == 0)
            return parens;
        
        search(parens, path, numOpens, numCloses);
        
        return parens;
    }
    
    /**
     * If numOpens > 0, it is possible to add "(" to path.
     * if numCloses > numOpens, it is possible to add ")" to path since there is a "(" to be closed.
     * If numOpens == numCloses == 0, then add the path to list
     */
    public void search(List<String> parens, String path, int numOpens, int numCloses) {
        if (numOpens > 0)
            search(parens, path + "(", numOpens-1, numCloses);
        if (numCloses > numOpens)
            search(parens, path + ")", numOpens, numCloses-1);
        if (numOpens == 0 && numCloses == 0)
            parens.add(path);
        
    }
    
    public static void main(String[] args) {
        GenerateParenthesis obj = new GenerateParenthesis();
        List<String> parens;
        
        parens = obj.generateParenthesis(1);
        System.out.println(parens);
        
        parens = obj.generateParenthesis(2);
        System.out.println(parens);
        
        parens = obj.generateParenthesis(3);
        System.out.println(parens);
    }
}
