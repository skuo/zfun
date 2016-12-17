package org.zfun.leetcode;

import java.util.HashMap;
import java.util.Map;

public class LenOfLongestNonRepeatingSubstring {

    public int lengthOfLongestSubstring(String s) {
        int maxLen = 0;
        Map<Character,Integer> charPos = new HashMap<>();
        for (int pos=0; pos<s.length(); pos++) {
            char c = s.charAt(pos);
            // if char not in map
            Integer lastPos = charPos.get(c);
            if (lastPos != null) {
                // char is repeated
                // Create a new Map and loop through charPos and add all entry whose pos > lastPos
                Map<Character,Integer> newCharPos = new HashMap<>();
                for (Map.Entry<Character, Integer> entry : charPos.entrySet()) {
                    if (entry.getValue() > lastPos)
                        newCharPos.put(entry.getKey(), entry.getValue());
                }
                charPos = newCharPos;
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
        System.out.println("maxLen for " + str + "=" + maxLen);

        str = "bbbbb";
        maxLen = obj.lengthOfLongestSubstring(str);
        System.out.println("maxLen for " + str + "=" + maxLen);

        str = "pwwkew";
        maxLen = obj.lengthOfLongestSubstring(str);
        System.out.println("maxLen for " + str + "=" + maxLen);

        str = "";
        maxLen = obj.lengthOfLongestSubstring(str);
        System.out.println("maxLen for " + str + "=" + maxLen);

        str = "c";
        maxLen = obj.lengthOfLongestSubstring(str);
        System.out.println("maxLen for " + str + "=" + maxLen);
}
}
