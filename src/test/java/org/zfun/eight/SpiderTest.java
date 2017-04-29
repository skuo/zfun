package org.zfun.eight;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

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
		// static method and constructor references
		CompareFunction<String, Integer> strCompFunc = String::compareTo;
		assertEquals(Integer.valueOf(-1), strCompFunc.compareTo("a", "b"));		
		
		List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");
		// Showing lambda is indeed a FunctionalInterface
		Comparator<String> strComp = (a,b) -> b.compareTo(a);
		Collections.sort(names, strComp);
		assertEquals(expDescendingNames, names);
		// a neat way to create a different String comparator (by length)
		Comparator<String> lenComp = (a,b) -> a.length() - b.length();
		Collections.sort(names, lenComp);
		assertEquals(4, names.get(0).length());
		assertEquals(5, names.get(3).length());
	}
	
	@Test
	public void testMethodReferences() {
		// static method reference
		Converter<String, Integer> converter = Integer::valueOf;
		Integer converted = converter.convert("123");
		assertEquals(Integer.valueOf(123), converted);
		
		// object method reference
		Spider spider = new Spider();
		Converter<String, String> converter2 = spider::startsWith;
		assertEquals("B",converter2.convert("Black Widow"));		
	}
	
	/*
	 * Represents a predicate (boolean-valued function) of one argument. 
	 * This is a functional interface whose functional method is test(Object).
	 * 
	 * Methods: and(), isEquals(), negate(), or(), test().
	 */
	@Test
	public void testPredicate() {
		Predicate<String> predicate = (s) -> s.length() > 0;
		assertTrue(predicate.test("foo"));              // true
		assertFalse(predicate.negate().test("foo"));     // false

		Predicate<Boolean> nonNull = Objects::nonNull;
		Predicate<Boolean> isNull = Objects::isNull;
		assertTrue(nonNull.test(Boolean.TRUE));
		assertFalse(nonNull.test(null));
		assertFalse(isNull.test(Boolean.FALSE));
		assertTrue(isNull.test(null));
		
		Predicate<String> isEmpty = String::isEmpty;
		assertTrue(isEmpty.test(""));
		//assertTrue(isEmpty.test(null));  // this throws NullPointerException, does not check for null
	}
	
	/*
	 * Represents a function that accepts one argument and produces a result.
	 * This is a functional interface whose functional method is apply(Object).
	 * 
	 * Methods: andThen(), apply(), compose(), identity()
	 */
	@Test
	public void testFunction() {
		Function<String, Integer> toInteger = Integer::valueOf;
		Function<String, String> backToString = toInteger.andThen(String::valueOf);
		BiFunction<String, String, String> strConcat = (x, y) -> {
		    return x+y;
		};

		assertEquals("123",backToString.apply("123"));     // "123"
		assertEquals("BinaryFunction", strConcat.apply("Binary", "Function"));
	}
	
	/*
	 * Supplier represents a supplier of results. 
	 * Consumer accepts a single input argument and returns no result.
	 */
	@Test
	public void testSupplierConsumer() {
		Supplier<Spider> spiderSupplier = Spider::new;
		Spider spider = spiderSupplier.get();
		spider.setSpecies("Black Widow");
		
		Consumer<Spider> spiderConsumer = (s) -> s.setNote("yuck! " + s.getSpecies());
		spiderConsumer.accept(spider);
		
		assertEquals("yuck! Black Widow", spider.getNote());
	}
	
	@Test
	public void testOptional() {
		Spider spider = new Spider();
		// if note == null, an empty Optional is returned
		Optional<String> spiderNote = spider.getSpiderNote();
		assertFalse(spiderNote.isPresent());
		assertEquals("No pertinent note", spiderNote.orElse("No pertinent note"));
		// set note to return an Optional
		spider.setNote("Black widow is here");
		spiderNote = spider.getSpiderNote();
		assertTrue(spiderNote.isPresent());
		assertEquals("Black widow is here", spiderNote.get());
	}
	
	@Test
	public void testAtomicAccumulator() {
	    LongAccumulator acc = new LongAccumulator((x,y) -> x+y, 0L);
	    assertEquals(0L, acc.longValue());
	    acc.accumulate(1L);
        assertEquals(1L, acc.longValue());
        acc.accumulate(2L);
        assertEquals(3L, acc.longValue());	    
	}
}
