package org.zfun.eight;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Test;

public class StreamTest {

	@Test
    public void testStream() {
		List<String> names = Arrays.asList("ddd2","aaa2","bbb1","aaa1","bbb3","ccc","bbb2","ddd1");
		// filter and collect
		List<String> names1 = names.stream()
		    .filter((s) -> s.startsWith("a"))
		    .collect(Collectors.toList());
		assertEquals(Arrays.asList("aaa2","aaa1"), names1);
		// sort, filter and collect
		List<String> names2 = names.stream()
				.sorted()
			    .filter((s) -> s.startsWith("a"))
			    .collect(Collectors.toList());
		assertEquals(Arrays.asList("aaa1","aaa2"), names2);
		// map, sorted, filter and collect
		List<String> names3 = names.stream()
				.map((s) -> "_" + s.toUpperCase())
				.sorted()
				.filter(s -> s.startsWith("_A"))
				.collect(Collectors.toList());
		assertEquals(Arrays.asList("_AAA1","_AAA2"), names3);
		// match
		assertTrue(names.stream()
				.anyMatch((s) -> s.startsWith("a")));
		assertFalse(names.stream()
				.allMatch((s) -> s.startsWith("a")));
		assertTrue(names.stream()
				.noneMatch((s) -> s.startsWith("_")));
		// count
		assertEquals(3, names.stream()
				.map((s) -> "_" + s.toUpperCase())
				.sorted()
				.filter(s -> s.startsWith("_B"))
				.count());
		// reduce a T not an Optional
		assertEquals("#_AAA1#_AAA2", names.stream()
		        .map((s) -> "_" + s.toUpperCase())
		        .sorted()
		        .filter(s -> s.startsWith("_A"))
		        .reduce("",(s1,s2) -> s1 + "#" + s2)
                );
        // to count, String stream must first be mapped to Long
        assertEquals(3, names.stream()
                .map((s) -> "_" + s.toUpperCase())
                .sorted()
                .filter(s -> s.startsWith("_B"))
                .mapToLong(s -> 1L)
                .sum()
                );
		// the original names list is untouched
		assertEquals(Arrays.asList("ddd2","aaa2","bbb1","aaa1","bbb3","ccc","bbb2","ddd1"), names);
	}
	
	@Test
	public void testMap() {
	    Map<Integer, String> map = new HashMap<>();
	    for (int i = 0; i < 10; i++) {
	        map.putIfAbsent(i, "val" + i);
	    }
	    //
	    map.computeIfPresent(3, (k,v) -> v + k);
	    assertEquals("val33",map.get(3));
	    map.computeIfPresent(9, (k,v) -> null);
	    assertFalse(map.containsKey(9));
	    map.computeIfAbsent(23, (k) -> "val" + k);
	    assertEquals("val23",map.get(23));
	    map.merge(9, "val9", (value, newValue) -> value.concat(newValue));
	    assertEquals("val9", map.get(9));
	    map.merge(9, "concat", (value, newValue) -> value.concat(newValue));
	    assertEquals("val9concat", map.get(9));
	}
}
