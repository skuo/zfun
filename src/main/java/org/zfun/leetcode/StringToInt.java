package org.zfun.leetcode;

public class StringToInt {

    public int myAtoi(String str) {
        // Not sure what to return if str == null
        if (str == null || str.length() == 0)
            return 0;
        
        // valid char is either a digit or '-'
        long result = 0;
        int minusCount = 0;
        int plusCount = 0;
        boolean digitSeen = false;
        for (int i=0; i<str.length(); i++) {
            Character c = str.charAt(i);
            if (c == '-' && !digitSeen)
                minusCount += 1;
            else if (c == '+' && !digitSeen)
                plusCount += 1;
            else if (c == ' ' && !digitSeen && minusCount == 0 && plusCount == 0)
                ;
            else if (Character.isDigit(c)) {
                result = Character.digit(c, 10) + result * 10;
                digitSeen = true;
                // It's getting too big
                if (result > Integer.MAX_VALUE) 
                    break;
            }
            else
                // end of valid int string
                break;
        }
        // check the number of '-' and '+'
        if (plusCount > 1)
            result = 0;
        if (minusCount > 1)
            result = 0;
        if (minusCount == 1 && plusCount == 1)
            result = 0;
        else if (minusCount == 1 && plusCount == 0)
            result = result * -1;
        
        // if result > Integer.max or < Integer.min
        if (result > Integer.MAX_VALUE)
            return Integer.MAX_VALUE;
        if (result < Integer.MIN_VALUE)
            return Integer.MIN_VALUE;
        return (int) result;
    }
    
    public static void main(String[] args) {
        StringToInt obj = new StringToInt();
        
        System.out.println(obj.myAtoi("9223372036854775809"));             // 2147483647
        System.out.println(obj.myAtoi("2147483648"));             // 2147483647
        System.out.println(obj.myAtoi(" +42"));             // 42
        System.out.println(obj.myAtoi(" +42 123"));             // 42
        System.out.println(obj.myAtoi("-+42"));             // 0
        System.out.println(obj.myAtoi("--42"));             // 42
        System.out.println(obj.myAtoi("---42"));             // 0
        System.out.println(obj.myAtoi("+42"));             // 42
        System.out.println(obj.myAtoi("+4+2"));             // 4
        System.out.println(obj.myAtoi("+++42"));             // 0
        System.out.println(obj.myAtoi(null));             // ?
        System.out.println(obj.myAtoi(""));             // ?
        System.out.println(obj.myAtoi(" 42"));             // 42
        System.out.println(obj.myAtoi("3.14159"));             // 3
        System.out.println(obj.myAtoi("31337 with words"));             // 31337
        System.out.println(obj.myAtoi("words and 2"));             // 0
        System.out.println(obj.myAtoi("-1"));             // -1
        System.out.println(obj.myAtoi("-42 negative"));             // -42
    }

}
