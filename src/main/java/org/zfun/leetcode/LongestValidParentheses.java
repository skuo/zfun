package org.zfun.leetcode;

import java.util.Stack;

/*
Given a string containing just the characters '(' and ')', find the length of the longest valid (well-formed) parentheses substring.

For "(()", the longest valid parentheses substring is "()", which has length = 2.

Another example is ")()())", where the longest valid parentheses substring is "()()", which has length = 4.
 */
public class LongestValidParentheses {

    public int longestValidParentheses(String s) {
        int maxans = 0;
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else {
                stack.pop();
                if (stack.empty()) {
                    stack.push(i);
                } else {
                    maxans = Math.max(maxans, i - stack.peek());
                }
            }
        }
        return maxans;
    }

    public static void main(String[] args) {
        LongestValidParentheses obj = new LongestValidParentheses();

        String s = "(()(((()";
        System.out.println("Is \"" + s + "\" valid = " + obj.longestValidParentheses(s));

        s = ")()())[()][]}()[";
        System.out.println("Is \"" + s + "\" valid = " + obj.longestValidParentheses(s));

        s = "(){}[]";
        System.out.println("Is \"" + s + "\" valid = " + obj.longestValidParentheses(s));

        s = "()(()";
        System.out.println("Is \"" + s + "\" valid = " + obj.longestValidParentheses(s));

        s = "()}";
        System.out.println("Is \"" + s + "\" valid = " + obj.longestValidParentheses(s));

        s = ")()())[";
        System.out.println("Is \"" + s + "\" valid = " + obj.longestValidParentheses(s));
    }
}
