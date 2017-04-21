package org.zfun.leetcode;

public class LongestPalindrome {

    public String longestPalindrome(String s) {
        String longestPalidrome = "";
        if (s.length() > 0)
            longestPalidrome = "" + s.charAt(0);
        for (int i=0; i<s.length(); i++) {
            // case 1: i is the right index
            int left = i-1, right = i;
            Integer matchedLeft = null, matchedRight = null;
            while (left >= 0 && right < s.length()) {
                if (s.charAt(left) == s.charAt(right)) {
                    matchedLeft = left;
                    matchedRight = right;
                    left -= 1;
                    right += 1;
                } else {
                    break;
                }
            }
            if (matchedLeft != null && matchedRight != null) {
                String palindrome = "";
                for (int j = matchedLeft; j <= matchedRight; j++)
                    palindrome += s.charAt(j);
                if (palindrome.length() > longestPalidrome.length())
                    longestPalidrome = palindrome;
            }
            
            // case 2: i is the middle index
            left = i-1;
            right = i+1;
            matchedLeft = null;
            matchedRight = null;
            while (left >= 0 && right < s.length()) {
                if (s.charAt(left) == s.charAt(right)) {
                    matchedLeft = left;
                    matchedRight = right;
                    left -= 1;
                    right += 1;
                } else {
                    break;
                }
            }
            if (matchedLeft != null && matchedRight != null) {
                String palindrome = "";
                for (int j = matchedLeft; j <= matchedRight; j++)
                    palindrome += s.charAt(j);
                if (palindrome.length() > longestPalidrome.length())
                    longestPalidrome = palindrome;
            }
        }
        return longestPalidrome;
    }
    
    public static void main(String[] args) {
        // my algorith is (n squre) or (n cube)
        // Manacher algorith is (n)
        // http://articles.leetcode.com/longest-palindromic-substring-part-ii
        
        LongestPalindrome obj = new LongestPalindrome();
        String str = "babad";
        String lp = obj.longestPalindrome(str);
        System.out.println("Longest palindrome for " + str + "=" + lp);

        str = "cbbd";
        lp = obj.longestPalindrome(str);
        System.out.println("Longest palindrome for " + str + "=" + lp);

        str = "a";
        lp = obj.longestPalindrome(str);
        System.out.println("Longest palindrome for " + str + "=" + lp);

        str = "";
        lp = obj.longestPalindrome(str);
        System.out.println("Longest palindrome for " + str + "=" + lp);

        str = "abcde";
        lp = obj.longestPalindrome(str);
        System.out.println("Longest palindrome for " + str + "=" + lp);
    }
}
