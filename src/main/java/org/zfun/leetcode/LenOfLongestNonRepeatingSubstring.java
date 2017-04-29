package org.zfun.leetcode;

import java.util.HashMap;
import java.util.Map;

public class LenOfLongestNonRepeatingSubstring {

    public int lengthOfLongestSubstring(String s) {
        int maxLen = 0;
        int start = 0;
        Map<Character,Integer> charPos = new HashMap<>();
        for (int pos=0; pos<s.length(); pos++) {
            char c = s.charAt(pos);
            // if char not in map
            Integer lastPos = charPos.get(c);
            if (lastPos != null) {
                // char is repeated
                for (int j=start; j<=lastPos; j++) {
                    char ch = s.charAt(j);
                    charPos.remove(ch);
                }
                start = lastPos+1;
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
