package org.zfun.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
Given an array of strings, group anagrams together.

For example, given: ["eat", "tea", "tan", "ate", "nat", "bat"], 
Return:

[
  ["ate", "eat","tea"],
  ["nat","tan"],
  ["bat"]
]
Note: All inputs will be in lower-case.
 */
public class GroupAnagrams {
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<Integer, List<String>> anagramMap = new HashMap<>();

        for (String str : strs) {
            // turn string into char[], sort, get hash, put str into a map
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            Integer hashCode = Arrays.hashCode(chars);
            anagramMap.compute(hashCode, (k, v) -> {
                if (v == null)
                    v = new ArrayList<String>();
                v.add(str);
                return v;
            });
        }

        List<List<String>> anagramLists = new ArrayList<>();
        anagramMap.forEach((k, v) -> anagramLists.add(v));
        return anagramLists;
    }

    public void print(List<List<String>> anagramLists) {
        System.out.println(anagramLists.stream()
                .map(l -> l.stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(",", "  [", "]"))
                        )
                .collect(Collectors.joining(",\n", "[\n", "\n]"))
                );
    }

    public static void main(String[] args) {
        GroupAnagrams obj = new GroupAnagrams();

        String[] strs = { "eat", "tea", "tan", "ate", "nat", "bat" };
        List<List<String>> anagramLists = obj.groupAnagrams(strs);
        obj.print(anagramLists);
    }
}
