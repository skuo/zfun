package org.zfun.ten;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class VarTest {
    
    @Test
    public void testNewFeatures() {
        var name = "Local variable type inference";
        assertEquals(name, "Local variable type inference");
    }

}
