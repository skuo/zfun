package org.zfun.leetcode;

/**
 * Given an integer, convert it to a roman numeral.
 * Input is guaranteed to be within the range from 1 to 3999.
 * 
 * @author terrancekuo
 *
 */
public class IntToRoman {

    public String intToRoman(int num) {
        StringBuilder sb = new StringBuilder();
        
        // thousands
        int quotient = num / 1000;
        int remainder = num % 1000;
        
        for (int i=0; i<quotient; i++)
            sb.append("M");
        
        // hundreds 100 -- 400 500 -- 900
        quotient = remainder / 100;
        remainder = remainder % 100;
        
        sb.append(quotientToRoman(quotient,"C","D","M"));
        
        // tens 10 -- 40 50 -- 90
        quotient = remainder / 10;
        remainder = remainder % 10;
        
        sb.append(quotientToRoman(quotient, "X", "L", "C"));
        
        // ones 1 -- 4 5 -- 9
        sb.append(quotientToRoman(remainder, "I", "V", "X"));
        
        return sb.toString();
    }
    
    private String quotientToRoman(int quotient, String one, String five, String ten) {
        StringBuilder sb = new StringBuilder();

        // 1 to 3
        if (quotient == 0) {
            ;
        } else if (quotient <= 3) {
            for (int i=0; i< quotient; i++)
                sb.append(one);
        } else if (quotient == 4) {
            sb.append(one);
            sb.append(five);
        } else if (quotient == 5) {
            sb.append(five);
        } else if (quotient <= 8) {
            sb.append(five);
            for (int i= 6; i<=quotient; i++)
                sb.append(one);
        } else if (quotient == 9) {
            sb.append(one);
            sb.append(ten);
        }
        return sb.toString();
    }
    
    public void validate(String roman, String expRoman) {
        if (!expRoman.equals(roman))
            System.out.println("Not validated: expRoman = " + expRoman + ", roman=" + roman);
    }
    
    public static void main(String[] args) {
        IntToRoman obj = new IntToRoman();
        
        String roman = obj.intToRoman(3);
        System.out.println(roman);
        obj.validate(roman, "III");

        roman = obj.intToRoman(49);
        System.out.println(roman);
        obj.validate(roman, "XLIX");
        
        roman = obj.intToRoman(879);
        System.out.println(roman);
        obj.validate(roman, "DCCCLXXIX");
        
        roman = obj.intToRoman(3999);
        System.out.println(roman);
        obj.validate(roman, "MMMCMXCIX");
    }
}
