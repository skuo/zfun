package org.zfun.topcoder;

/**
One day, Jamie noticed that many English words only use the letters A and B. Examples of such words include "AB" (short for abdominal), 
"BAA" (the noise a sheep makes), "AA" (a type of lava), and "ABBA" (a Swedish pop sensation).

Inspired by this observation, Jamie created a simple game. You are given two s: initial and target. The goal of the game 
is to find a sequence of valid moves that will change initial into target. There are two types of valid moves:

Add the letter A to the end of the string.
Reverse the string and then add the letter B to the end of the string.
Return "Possible" (quotes for clarity) if there is a sequence of valid moves that will change initial into target. Otherwise, return "Impossible".

Constraints
- The length of initial will be between 1 and 999, inclusive.
- The length of target will be between 2 and 1000, inclusive.
- target will be longer than initial.
- Each character in initial and each character in target will be either 'A' or 'B'.
*/
public class ABBA {

    public String canObtain(String initial, String target) {
        boolean found = false;

        found = dfs(initial, target);
        
        if (found)
            return "Possible";
        else
            return "Impossible";
    }
    
    private boolean dfs(String str, String target) {
        // search termination condition
        if (str.length() == target.length())
            return false;
        
        // adding "A"
        String str1 = str + "A";
        if (str1.equals(target))
            return true;
        boolean found = dfs(str1, target);
        if (found)
            return true;
        
        // reverse string and add "B"
        str1 = new StringBuilder(str).reverse().append("B").toString();
        if (str1.equals(target))
            return true;
        found = dfs(str1, target);
        return found;
    }
    
    public static void main(String[] args) {
        ABBA obj = new ABBA();
        
        String initial = "B";
        String target = "ABBA";        
        String str = obj.canObtain(initial, target);
        System.out.println("From " + initial + " to " + target + " is " + str);
        
        initial = "AB";
        target = "ABB";        
        str = obj.canObtain(initial, target);
        System.out.println("From " + initial + " to " + target + " is " + str);
        
        initial = "BBAB";
        target = "ABABABABB";        
        str = obj.canObtain(initial, target);
        System.out.println("From " + initial + " to " + target + " is " + str);
        
        initial = "BBBBABABBBBBBA";
        target = "BBBBABABBABBBBBBABABBBBBBBBABAABBBAA";        
        str = obj.canObtain(initial, target);
        System.out.println("From " + initial + " to " + target + " is " + str);
        
        initial = "A";
        target = "BB";        
        str = obj.canObtain(initial, target);
        System.out.println("From " + initial + " to " + target + " is " + str);
    }
}
