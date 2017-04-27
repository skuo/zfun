package org.zfun.leetcode;

import java.util.Stack;

/** 
Given a string containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.

The brackets must close in the correct order, "()" and "()[]{}" are all valid but "(]" and "([)]" are not.
*/
public class ValidParentheses {

    public boolean isValid(String s) {
        boolean isValid = true;
        Stack<Character> parens = new Stack<>();
        
        for (int i=0; i<s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(' || c == '{' || c == '[' ) {
                parens.push(c);
            } else {
                if (parens.isEmpty()) {
                    // can be empty here
                    isValid = false;
                    break;
                }
                char openParen = parens.pop();
                if ( (c == ')' && openParen != '(')
                        || (c == '}' && openParen != '{')
                        || (c == ']' && openParen != '[') ){
                    isValid = false;
                    break;
                }
            }
        }
        
        // If isValid == true and the stack is empty, the isValid remains true
        if (!isValid || !parens.isEmpty())
            isValid = false;
        
        return isValid;
    }
    
    public static void main(String[] args) {
        ValidParentheses obj = new ValidParentheses();
        String s = "(){}[]";
        System.out.println("Is \"" + s + "\" valid = " + obj.isValid(s));

        s = "()}";
        System.out.println("Is \"" + s + "\" valid = " + obj.isValid(s));

        s = "(";
        System.out.println("Is \"" + s + "\" valid = " + obj.isValid(s));
    }
}
