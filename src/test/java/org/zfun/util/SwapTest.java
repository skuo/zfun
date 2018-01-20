package org.zfun.util;

import org.junit.Test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class SwapTest {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Box {
        int size;
    }
    
    @Test
    public void testSwap() {
        int a = 1;
        int b = 1;
        swap(a, b);
        System.out.println(String.format("a=%s, b=%s", a, b));
        
        Integer x = 10;
        Integer y = 20;
        swap(x, y);
        System.out.println(String.format("x=%s, y=%s", x, y));

        Box b1 = new Box();
        b1.setSize(100);
        Box b2 = new Box(200);
        swap(b1, b2);
        System.out.println(String.format("b1.size=%s, b2.size=%s", b1.getSize(), b2.getSize()));
        
        Integer z = 50;
        incrInteger(z);
        System.out.println(String.format("z=%d", z));
    }
    
    private void swap(int i1, int i2) {
        int temp = i1;
        i1 = i2;
        i2 = temp;
    }
    
    private void swap(Integer i1, Integer i2) {
        Integer temp = i1;
        i1 = i2;
        i2 = temp;
    }
    
    private void swap(Box b1, Box b2) {
        int tempSize = b1.getSize();
        b1.setSize(b2.size);
        b2.setSize(tempSize);
    }
    
    private void incrInteger(Integer i) {
        i++;
    }
}
