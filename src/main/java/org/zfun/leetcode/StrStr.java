package org.zfun.leetcode;

/*
Return the index of the first occurrence of needle in haystack, or -1 if needle is not part of haystack.

Example 1:

Input: haystack = "hello", needle = "ll"
Output: 2
Example 2:

Input: haystack = "aaaaa", needle = "bba"
Output: -1
 */
public class StrStr {
    public int strStr(String haystack, String needle) {
        // Make sure haystack and needle are not null
        if (haystack == null || needle == null)
            return -1;
        
        // If needle is "", return 0 as haystack is not null
        if (needle.length() == 0) 
                return 0;
        
        // haystack needs to be longer than needle
        if (haystack.length() < needle.length())
            return -1;
        
        // find the needle in the haystack
        for (int i=0; i<= haystack.length()-needle.length(); i++) {
            if (haystack.charAt(i) == needle.charAt(0)) {
                // see if the rest match
                boolean matched = true;
                for (int j=1; j<=needle.length()-1; j++) {
                    if (haystack.charAt(i+j) != needle.charAt(j)) {
                        matched = false;
                        break;
                    }
                }
                if (matched)
                    return i;
            }
        }
        
        return -1;
    }

    public static void main(String[] args) {
        StrStr obj = new StrStr();
        
        System.out.print(obj.strStr(null, null) + "\n");            // -1
        System.out.print(obj.strStr(null, "") + "\n");              // -1
        System.out.print(obj.strStr("a", "a") + "\n");              // 0
        System.out.print(obj.strStr("", "a") + "\n");               // -1
        System.out.print(obj.strStr("ab", "ab") + "\n");            // 0
        System.out.print(obj.strStr("abc", "abd") + "\n");          // -1
        System.out.print(obj.strStr("ll", "hello") + "\n");         // -1
        System.out.print(obj.strStr("hello", "ll") + "\n");         // 2
        System.out.print(obj.strStr("hello world", "ll") + "\n");   // 2
        System.out.print(obj.strStr("aaaaa","bba") + "\n");         // -1

    }
}
