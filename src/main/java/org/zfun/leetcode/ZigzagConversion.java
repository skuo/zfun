package org.zfun.leetcode;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ZigzagConversion {

    public String convert(String s, int numRows) {
        int len = s.length();
        int numZzCols = Math.max(0, numRows - 2);
        int zzBlockSize = numRows + numZzCols;
        
        List<ZzCoord> zzCoords = new LinkedList<>();
        for (int i=0; i< len; i++) {
            Coord coord = calcCoord(i, numRows, numZzCols, zzBlockSize);
            zzCoords.add(new ZzCoord(coord, s.charAt(i), i));
        }
        
        zzCoords.sort(
                (coord1, coord2) -> {
                    if (coord1.getCoord().getX() == coord2.getCoord().getX())
                        return coord1.getCoord().getY() - coord2.getCoord().getY();
                    else
                        return coord1.getCoord().getX() - coord2.getCoord().getX();
                });
        
        StringBuilder sb = new StringBuilder();
        for (ZzCoord zzCoord : zzCoords)
            sb.append(zzCoord.getC());
            
        return sb.toString();
    }
    
    private Coord calcCoord(int i, int numRows, int numZzCols, int zzBlockSize) {
        int quotient = i/zzBlockSize;
        int remainder = i % zzBlockSize;
        
        // calculate y
        int remainderCols = 0;
        if (remainder >= numRows)
            remainderCols += (remainder - numRows + 1);
        int y = quotient * (1+numZzCols) + remainderCols;
        
        // calculate x
        int x = remainder;
        if (remainder >= numRows)
            x = (numRows-1) - (remainder - numRows + 1);
        
        return new Coord(x,y);
    }
    
    //@Data
    //@AllArgsConstructor
    private class ZzCoord {
        Coord coord;
        char c;
        int index;
        
        public Coord getCoord() { return coord; }
        public char getC() { return c; }
        
        public ZzCoord(Coord coord, char c, int index) {
            this.coord = coord;
            this.c = c;
            this.index = index;
        }
    }
    
    //@Data
    //@AllArgsConstructor
    private class Coord {
        int x;
        int y;
        
        public int getX() { return x; }
        public int getY() { return y; }
        
        public Coord(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) {
        ZigzagConversion obj = new ZigzagConversion();
        String str = "PAYPALISHIRING";
        String convertedStr = obj.convert(str, 3);
        //str=PAYPALISHIRING, convertedStr=PAHNAPLSIIGYIR
        System.out.println("str=" + str + ", convertedStr=" + convertedStr);
        assert(convertedStr.equals("PAHNAPLSIIGYIR"));
        
        str = "A";
        convertedStr = obj.convert(str, 1);
        System.out.println("str=" + str + ", convertedStr=" + convertedStr);
        assert(convertedStr.equals("A"));

        str = "ABC";
        convertedStr = obj.convert(str, 2);
        System.out.println("str=" + str + ", convertedStr=" + convertedStr);
        assert(convertedStr.equals("ACB"));

        str = "ABCD";
        convertedStr = obj.convert(str, 2);
        System.out.println("str=" + str + ", convertedStr=" + convertedStr);
        assert(convertedStr.equals("ACBD"));

        str = "ABCDE";
        convertedStr = obj.convert(str, 4);
        System.out.println("str=" + str + ", convertedStr=" + convertedStr);
        assert(convertedStr.equals("ABCED"));

    }
    
}
