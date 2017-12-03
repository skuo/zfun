package org.zfun.leetcode;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LongestCommonPrefix {
    public String longestCommonPrefix(String[] strs) {
        String lcp = "";

        // (0) Check boundary condition
        if (strs == null || strs.length == 0)
            return lcp;

        if (strs.length == 1)
            return strs[0];

        // (1) sort the String[]
        List<String> strList = Arrays.asList(strs);
        List<String> sortedStrList = strList.stream().sorted().collect(Collectors.toList());
        // (2) Find the longest command prefix
        for (int i = 0; i < sortedStrList.size() - 1; i++) {
            String strA = sortedStrList.get(i);
            String strB = sortedStrList.get(i + 1);
            int minLen = Math.min(strA.length(), strB.length());
            // if maxLen <= lcp.length(), then cp cannot be lcp
            if (minLen <= lcp.length())
                continue;
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < minLen; j++) {
                if (strA.charAt(j) == strB.charAt(j))
                    sb.append(strA.charAt(j));
                else
                    break;
            }
            // compare cp with lcp
            if (sb.length() > lcp.length())
                lcp = sb.toString();
        }
        return lcp;
    }

    public String longestCommonPrefix2(String[] strs) {
        // (0) Check boundary condition
        if (strs == null || strs.length == 0)
            return "";

        if (strs.length == 1)
            return strs[0];

        // (1) compare char by char
        StringBuffer sb = new StringBuffer();
        int index = 0;
        boolean isValid = true;
        while (isValid) {
            Character c = null;
            try {
                for (int i = 0; i < strs.length; i++) {
                    if (c == null)
                        c = strs[i].charAt(index);
                    else if (!c.equals(strs[i].charAt(index)))
                        isValid = false;
                }
                if (isValid) {
                    sb.append(c);
                    index++;
                }
            } catch (Exception e) {
                // ok.  Pass the length of one of the strings
                isValid = false;
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        LongestCommonPrefix obj = new LongestCommonPrefix();

        String[] strs0 = null;
        String lcp = obj.longestCommonPrefix2(strs0);
        System.out.println("lcp=" + lcp);

        String[] strs1 = {};
        lcp = obj.longestCommonPrefix2(strs1);
        System.out.println("lcp=" + lcp);

        String[] strs2 = { "bc d" };
        lcp = obj.longestCommonPrefix2(strs2);
        System.out.println("lcp=" + lcp);

        String[] strs3 = { "a", "a", "b" };
        lcp = obj.longestCommonPrefix2(strs3);
        System.out.println("lcp=" + lcp);

        String[] strs4 = { "aaa", "aa bcd", "b" };
        lcp = obj.longestCommonPrefix2(strs4);
        System.out.println("lcp=" + lcp);

        String[] strs5 = { "aaa", "aa", "aaab" };
        lcp = obj.longestCommonPrefix2(strs5);
        System.out.println("lcp=" + lcp);
    }
}
