package org.zfun.leetcode;

public class IsPolindrome {

    public boolean isPolidrome(String str) {
        boolean found = true;
        
        int left = 0, right = str.length()-1;
        
        while(left <= right) {
            if (str.charAt(left) == str.charAt(right)) {
                left++;
                right--;
            } else {
                found = false;
                break;
            }
        }
        
        return found;
    }
    
    public static void main (String[] args) {
        IsPolindrome obj = new IsPolindrome();
        
        Integer i = Integer.valueOf(123321);
        System.out.println("Is " + i + " a polindrome = " + obj.isPolidrome(i.toString()));
        
        i = Integer.valueOf(1234321);
        System.out.println("Is " + i + " a polindrome = " + obj.isPolidrome(i.toString()));
        
        i = Integer.valueOf(1234721);
        System.out.println("Is " + i + " a polindrome = " + obj.isPolidrome(i.toString()));
    }
}
