package org.zfun.leetcode;

import java.util.HashMap;
import java.util.Map;

/*
Given a string, find the length of the longest substring without repeating characters.

Examples:

Given "abcabcbb", the answer is "abc", which the length is 3.

Given "bbbbb", the answer is "b", with the length of 1.

Given "pwwkew", the answer is "wke", with the length of 3. Note that the answer must be a substring, 
"pwke" is a subsequence and not a substring.
 */
public class LenOfLongestNonRepeatingSubstring {

    public int lengthOfLongestSubstring(String s) {
        int maxLen = 0;
        int start = 0;
        Map<Character, Integer> charPos = new HashMap<>();
        for (int pos = 0; pos < s.length(); pos++) {
            char c = s.charAt(pos);
            // if char not in map
            Integer lastPos = charPos.get(c);
            if (lastPos != null) {
                // char is repeated
                for (int j = start; j <= lastPos; j++) {
                    char ch = s.charAt(j);
                    charPos.remove(ch);
                }
                start = lastPos + 1;
            }
            charPos.put(c, pos);
            // set maxLen to charPos.size() if it is smaller
            if (maxLen < charPos.size())
                maxLen = charPos.size();
        }
        return maxLen;
    }

    public static void main(String[] args) {
        LenOfLongestNonRepeatingSubstring obj = new LenOfLongestNonRepeatingSubstring();
        String str = "abcabcbb";
        int maxLen = obj.lengthOfLongestSubstring(str);
        // ans=3
        System.out.println("maxLen for " + str + "=" + maxLen);

        str = "bbbbb";
        maxLen = obj.lengthOfLongestSubstring(str);
        // ans=1
        System.out.println("maxLen for " + str + "=" + maxLen);

        str = "pwwkew";
        maxLen = obj.lengthOfLongestSubstring(str);
        // ans=3
        System.out.println("maxLen for " + str + "=" + maxLen);

        str = "";
        maxLen = obj.lengthOfLongestSubstring(str);
        // ans=0
        System.out.println("maxLen for " + str + "=" + maxLen);

        str = "c";
        maxLen = obj.lengthOfLongestSubstring(str);
        // ans=1
        System.out.println("maxLen for " + str + "=" + maxLen);

        str = "abcdbefda";
        maxLen = obj.lengthOfLongestSubstring(str);
        // ans=5
        System.out.println("maxLen for " + str + "=" + maxLen);
    }
}
