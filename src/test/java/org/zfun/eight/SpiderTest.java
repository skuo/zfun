package org.zfun.eight;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

public class SpiderTest {
	private final List<String> expDescendingNames = Arrays.asList("xenia", "peter", "mike", "anna");
	private final List<String> expAscendingNames = Arrays.asList("anna", "mike", "peter", "xenia");

	@Test
    public void testDefaultMethodForInterface() {
		// default method for interface
		Spider spider = new Spider();
        int i = 100;
        assertEquals(100, spider.calculate(i), 0.0001);
        assertEquals(4, spider.sqrt(16), 0.0001);
    }
	
	/*
	 * The syntax of a lambda expression is an argument list enclosed in parentheses, 
	 * an arrow token (->), and a function body. The body can be either a statement block 
	 * (enclosed in braces) or a single expression. 
	 */
	@Test
	public void testLambda() {
		List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");
		// descending sort
		Collections.sort(names,
				(a,b) -> b.compareTo(a));
		assertEquals(expDescendingNames, names);
		// ascending sort
		Collections.sort(names, (a,b) -> a.compareTo(b));
		assertEquals(expAscendingNames, names);
	}

	/*
	 * Each lambda corresponds to a given type, specified by an interface. 
	 * A so called functional interface must contain exactly one abstract method declaration. 
	 * Each lambda expression of that type will be matched to this abstract method. 
	 * Since default methods are not abstract you're free to add default methods to your functional interface.
	 */
	@Test
	public void testFunctionalInterface() {
		// FunctionalInterface
		CompareFunction<String, Integer> desCompFunc = (a,b) -> b.compareTo(a);
		assertEquals(Integer.valueOf(1), desCompFunc.compareTo("a", "b"));
		
		List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");
		// Showing lambda is indeed a FunctionalInterface
		Comparator<String> strComp = (a,b) -> b.compareTo(a);
		Collections.sort(names, strComp);
		assertEquals(expDescendingNames, names);
		Comparator<String> lenComp = (a,b) -> a.length() - b.length();
		Collections.sort(names, lenComp);
		assertEquals(4, names.get(0).length());
		assertEquals(5, names.get(3).length());
	}
	
	@Test
	public void testMethodReferences() {
		// static method and constructor references
		CompareFunction<String, Integer> strCompFunc = String::compareTo;
		assertEquals(Integer.valueOf(-1), strCompFunc.compareTo("a", "b"));		

		// object method and constructor references
		Spider spider = new Spider();
		Converter<String, String> converter = spider::startsWith;
		assertEquals("B",converter.convert("Black Widow"));		
		
	}
}
