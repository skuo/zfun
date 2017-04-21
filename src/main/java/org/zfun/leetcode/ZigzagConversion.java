package org.zfun.leetcode;

public class ZigzagConversion {

    public String convert(String s, int numRows) {
        // determine the column of the two dimensional arrays
        int len = s.length();
        int quotient = len / (numRows + 1);
        int remainder = s.length() % (numRows + 1);
        int remainderColumns = (remainder == 0) ? 0 : 1;
        int numColumns = 2*quotient + remainderColumns;
        char[][] twoDChars = new char[numRows][numColumns];

        // is numRows is even, then there is no medianRow (= -1)
        boolean isEven = (numRows % 2 == 0) ? true : false;
        int medianRow = -1;
        if (!isEven)
            medianRow = (numRows-1)/2;
        
        // loop through the chars in s and assign them to array
        int row = 0, col = 0;
        for (int i=0; i<len; i++) {
            if (row < numRows) {
                twoDChars[row][col] = s.charAt(i);
                row++;
            } else {
                // determine whether to add the median row
                col++;
                if (medianRow == -1) {
                    row = 0;
                    twoDChars[row][col] = s.charAt(i);
                    row++;
                } else {
                    twoDChars[medianRow][col] = s.charAt(i);
                    row = 0;
                    col++;
                }
            }
        }
        
        // all right, read out the converted columns
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<numRows; i++)
            for (int j=0; j<numColumns; j++) {
                if (twoDChars[i][j] != '\u0000')
                    sb.append(twoDChars[i][j]);
            }
        
        return sb.toString();
    }
    
    public static void main(String[] args) {
        ZigzagConversion obj = new ZigzagConversion();
        String str = "PAYPALISHIRING";
        String convertedStr = obj.convert(str, 3);
        //str=PAYPALISHIRING, convertedStr=PAHNAPLSIIGYIR
        System.out.println("str=" + str + ", convertedStr=" + convertedStr);

        str = "ABC";
        convertedStr = obj.convert(str, 2);
        // str=ABC, convertedStr=ACB
        System.out.println("str=" + str + ", convertedStr=" + convertedStr);

        str = "ABCD";
        convertedStr = obj.convert(str, 2);
        //str=ABCD, convertedStr=ACBD
        System.out.println("str=" + str + ", convertedStr=" + convertedStr);
    }
}
