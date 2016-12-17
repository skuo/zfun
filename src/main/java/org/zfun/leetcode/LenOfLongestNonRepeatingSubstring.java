package org.zfun.leetcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LenOfLongestNonRepeatingSubstring {

    public int lengthOfLongestSubstring(String s) {
        int maxLen = 0;
        Map<Character,Integer> charPos = new HashMap<>();
        Set<Character> charToDelete = new HashSet<>();
        for (int pos=0; pos<s.length(); pos++) {
            char c = s.charAt(pos);
            // if char not in map
            Integer lastPos = charPos.get(c);
            if (lastPos != null) {
                // char is repeated
                // Create a new set containing the keys whose whose pos <= lastPos
                charToDelete.clear();
                for (Map.Entry<Character, Integer> entry : charPos.entrySet()) {
                    if (entry.getValue() <= lastPos)
                        charToDelete.add(entry.getKey());
                }
                // Loop and remove charToDelete
                for (Character ch : charToDelete)
                    charPos.remove(ch);
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
