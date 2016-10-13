package org.zfun.eight;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.Test;

public class StreamTest {

	@Test
    public void testStream() {
	    List<String> empty = Arrays.asList();
	    // match.  Stream works well with an empty list
	    assertFalse(empty.stream()
	            .anyMatch((s) -> s.equals("aaa")));
	    
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
        // to sum, String stream must first be mapped to Long
        assertEquals(3, names.stream()
                .map((s) -> "_" + s.toUpperCase())
                .sorted()
                .filter(s -> s.startsWith("_B"))
                .mapToLong(s -> 1L)
                .sum()
                );
        // join
        assertEquals("[\"ddd2\",\"aaa2\",\"bbb1\",\"aaa1\",\"bbb3\",\"ccc\",\"bbb2\",\"ddd1\"]",
                names.stream()
                .map(s -> "\"" + s + "\"")
                .collect(Collectors.joining(",","[","]"))
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
	    // compute will create a new entry if the key does not exist
	    map.compute(99, (k,v) -> v == null ? k.toString() : k+v);
        assertEquals("99",map.get(99));
        map.compute(99, (k,v) -> v == null ? k.toString() : k+v);
        assertEquals("9999",map.get(99));
        //
	    map.computeIfPresent(3, (k,v) -> v + k);
	    assertEquals("val33",map.get(3));
	    map.computeIfPresent(9, (k,v) -> null);
	    assertFalse(map.containsKey(9));
	    map.computeIfAbsent(23, (k) -> "val" + k);
	    assertEquals("val23",map.get(23));
        map.computeIfAbsent(24, (k) -> "val24");
        assertEquals("val24",map.get(24));
	    map.merge(9, "val9", (value, newValue) -> value.concat(newValue));
	    assertEquals("val9", map.get(9));
	    map.merge(9, "concat", (value, newValue) -> value.concat(newValue));
	    assertEquals("val9concat", map.get(9));
        map.merge(9, "lambda", (value, newValue) -> 
          {
              if (newValue.length() < value.length())
                  return newValue;
              else
                  return value;
          } 
          );
        assertEquals("lambda", map.get(9));
	}
	
	@Test
	public void testStudents() {
	    List<Student> students = new ArrayList<>();
	    students.add(new Student(1L, "First Alpha", 18));
	    students.add(new Student(2L, "Second Bravo", 18));
	    students.add(new Student(3L, "Second Beta", 20));
	    students.add(new Student(4L, "Fourth Delta", 21));
	    
	    List<Long> ids = students.stream()
	            .filter((s) -> s.getName().startsWith("Second"))
	            .map((s) -> s.getId())
	            .collect(Collectors.toList());
	    assertEquals(Arrays.asList(2L, 3L), ids);
	    
        tryHashMap(students);
    }

    private void tryHashMap(List<Student> students) {
        // Build a HashMap
	    Map<Long, Student> studentMap =	students.stream()
	                .collect(Collectors.toMap(Student::getId, Function.identity()));
	    assertEquals(4, studentMap.size());
	    assertEquals("First Alpha", studentMap.get(1L).getName());
	    assertEquals("Fourth Delta", studentMap.get(4L).getName());
	    
	    // Build a HashMap where value is a list
	    Map<Integer, List<Student>> studentsByAge = students.stream()
	            .collect(Collectors.groupingBy(Student::getAge));
	    assertEquals(3, studentsByAge.size());
	    assertEquals(2, studentsByAge.get(18).size());
	    assertEquals(1, studentsByAge.get(20).size());
	    assertEquals(1, studentsByAge.get(21).size());
	   
        // Build a HashMap where value is a set of name
        Map<Integer, Set<String>> map = students.stream()
                .collect(
                        Collectors.groupingBy(
                                Student::getAge, 
                                Collectors.mapping(
                                        Student::getName, 
                                        Collectors.toSet())));
        assertEquals(3, map.size());
        assertEquals(2, map.get(18).size());
        assertEquals(1, map.get(20).size());
        assertEquals(1, map.get(21).size());
    }
	
	public class Student {
	    private Long id;
	    private String name;
	    private int age;
	    
	    public Student(Long id, String name, int age) {
	        this.id = id;
	        this.name = name;
	        this.age = age;
	    }
	    
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
	    
	}
}
