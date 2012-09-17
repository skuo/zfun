package org.zfun.expression;


import static org.junit.Assert.assertEquals;

import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;
import org.apache.commons.jexl2.Script;
import org.junit.Before;
import org.junit.Test;

public class JexlTest {
    private Account account;
    
    public class Account {
        public Float balance = 0F;
        
        public Account(Float balance) {
            this.balance = balance;
        }

        public Float getBalance() {
            return balance;
        }

        public void setBalance(Float balance) {
            this.balance = balance;
        }
    }
    
    @Before
    public void setup() {
        account = new Account(100F);
    }
    
    @Test
    public void testExpression() {
        // simple addition
        JexlEngine jexl = new JexlEngine();
        String jexlExp = "1 + 2";
        Expression e = jexl.createExpression(jexlExp);
        Object o = e.evaluate(null);
        assertEquals(o, Integer.valueOf(3));
        
        // simple addition with variable
        jexlExp = "x + y";
        Expression e2 = jexl.createExpression(jexlExp);
        JexlContext jc = new MapContext();
        jc.set("x", new Integer(2));
        jc.set("y", new Integer(4));
        o = e2.evaluate(jc);
        assertEquals(o, Integer.valueOf(6));
        
        // simple relational expression
        jexlExp = "x > y";
        Expression e3 = jexl.createExpression(jexlExp);
        o = e3.evaluate(jc);
        assertEquals(o, Boolean.FALSE);
        
        // re-evaluate e and e2 to make sure changing jexlExp does not change them
        o = e.evaluate(null);
        assertEquals(o, Integer.valueOf(3));
        o = e2.evaluate(jc);
        assertEquals(o, Integer.valueOf(6));

        // complex relational expression
        Expression e4 = jexl.createExpression("x > y || z > y");
        jc.set("z", new Integer(6));
        o = e4.evaluate(jc);
        assertEquals(o, Boolean.TRUE);

        // knarly relational expression
        Expression e5 = jexl.createExpression("(x > y || z > y) && (z > x)");
        o = e5.evaluate(jc);
        assertEquals(o, Boolean.TRUE);
        
        // expression with object.  account.balance and account.getBalance() are equivalent
        Expression e6 = jexl.createExpression("account.balance > 90.0 && account.getBalance() < 110");
        jc.set("account", account);
        o = e6.evaluate(jc);
        assertEquals(o, Boolean.TRUE);
    }
    
    @Test
    public void testScript() {
        JexlEngine jexl = new JexlEngine();
        String scriptText = "if ((x * 2) == 5) { y = 1.1; } else { y = 2.3; }";
        Script s1 = jexl.createScript(scriptText);
        JexlContext jc = new MapContext();
        // test the true branch
        jc.set("x", new Float(2.5));
        Object o = s1.execute(jc);
        assertEquals(o, Float.valueOf(1.1F));
        
        // test the false branch
        jc.set("x", new Float(3));
        o = s1.execute(jc);
        assertEquals(o, Float.valueOf(2.3F));
        
        // with object, return object can be any object
        scriptText = "if (account.balance > 100) { account.setBalance(account.balance - 10); return account; } else return false;";
        Script s2 = jexl.createScript(scriptText);
        jc.set("account", account);
        // false branch
        o = s2.execute(jc);
        assertEquals(Boolean.FALSE, o);
        // true branch
        account.setBalance(110F);
        o = s2.execute(jc);
        assertEquals(account.getBalance(), Float.valueOf(100F));
        
        // account.balance is the same as account.getBalance().  But account.balance = is not equivalent to account.setBalance()
        // always use setter explicitly
        assertEquals(account.getBalance(), Float.valueOf(100F));        
        // scriptText = "account.setBalance(10.0); temp = (account.balance + 20.0); account.balance = temp; return account;"; // failed script
        scriptText = "account.setBalance(10.0); return account;";
        Script s3 = jexl.createScript(scriptText);
        o = s3.execute(jc);
        assertEquals(Float.valueOf(10F), account.getBalance());        
    }
    
}
