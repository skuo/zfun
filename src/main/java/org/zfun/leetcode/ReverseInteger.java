package org.zfun.leetcode;

public class ReverseInteger {

    public int reverse(int num) {
        // set multiplier to make reversal uniform
        int multiplier = 1;
        if (num < 0)
            multiplier = -1;
        // get absolute number
        String intStr = Integer.toString(Math.abs(num));
        StringBuilder sb = new StringBuilder();
        for (int i=intStr.length()-1; i>=0; i--)
            sb.append(intStr.charAt(i));
        int reversedNum = Integer.valueOf(sb.toString());
        return reversedNum * multiplier;
    }
    
    public static void main(String[] args) {
        ReverseInteger obj = new ReverseInteger();
        int num = 123;
        System.out.println(num + " ==> " + obj.reverse(num));
        num = -123;
        System.out.println(num + " ==> " + obj.reverse(num));
        num = 0;
        System.out.println(num + " ==> " + obj.reverse(num));
        num = 1534236469;
        System.out.println(num + " ==> " + obj.reverse(num));
    }
}
