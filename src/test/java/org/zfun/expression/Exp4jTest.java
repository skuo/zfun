package org.zfun.expression;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.congrace.exp4j.Calculable;
import de.congrace.exp4j.ExpressionBuilder;
import de.congrace.exp4j.UnknownFunctionException;
import de.congrace.exp4j.UnparsableExpressionException;

public class Exp4jTest {

    @Test
    public void testCalc() throws UnknownFunctionException, UnparsableExpressionException {
        Double x = 1d;
        String xVarName = "x";
        Double y = 2d;
        String yVarName = "y";

        // calc
        Calculable calc = new ExpressionBuilder("x * y - 2").withVariable("x", 1).withVariable("y", 2).build();
        assertTrue(calc.calculate() == 0);
        x = 2d;
        calc.setVariable("x", x);
        double result = calc.calculate();
        assertEquals(2.0d, result, 0.001);
       
        // calc1
        x = 1d;
        Calculable calc1 = new ExpressionBuilder("x * y + 2").withVariableNames("x","y").build();
        calc1.setVariable(xVarName, x);
        calc1.setVariable(yVarName, y);
        assertEquals(4, calc1.calculate(), 0.001);
        assertEquals(2.0d, result, 0.001);  // no impact on calc
        x = 2d;  // does not change calc1
        assertEquals(4, calc1.calculate(), 0.001);
        calc1.setVariable(xVarName, x);
        assertEquals(6, calc1.calculate(), 0.001);        
    }
}
