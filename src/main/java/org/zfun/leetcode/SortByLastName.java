package org.zfun.leetcode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SortByLastName {

    // Print out the name sorted by lastName, which is the second word without duplicates
    public static void main(String[] args) {
        List<String> names = new LinkedList<>(Arrays.asList("Jonathan Smith", "Bill O'Reilly", "Thomas Sowell Sr.",
                "Jonathan Smith", "Rose Friedmann"));
        Set<String> nameSet = new HashSet<>(names);
        // sort
        List<String> sortedNames = nameSet.stream()
                .sorted((n1, n2) -> {
                    String[] tokens1 = n1.split(" ");
                    String[] tokens2 = n2.split(" ");
                    return tokens1[1].compareTo(tokens2[1]);
                })
                .collect(Collectors.toList());
        // print
        System.out.println(sortedNames.stream()
            .map(Object::toString)
            .collect(Collectors.joining(",","[","]"))
            )
        ;
        
    }
}
