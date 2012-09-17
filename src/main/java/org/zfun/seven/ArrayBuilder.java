package org.zfun.seven;

import java.util.List;

public class ArrayBuilder {

	// compile-time: [unchecked] Possible heap pollution from parameterized varag type T
	// run-time: [unchecked] unchecked generic array creation for varags parameter of type List<String[]
	public static <T> void addToList(List<T> listArg, T... elements) {
		for (T x : elements)
			listArg.add(x);
	}
	
	@SuppressWarnings("unchecked")
	// run-time: [unchecked] unchecked generic array creation for varags parameter of type List<String[]
	public static <T> void addToLists2(List<T> listArg, T... elements) {
		for (T x : elements)
			listArg.add(x);
	}
	
	@SafeVarargs
	// no warning is generated either at compiled- or run-time
	public static <T> void addToLists3(List<T> listArg, T... elements) {
		for (T x : elements)
			listArg.add(x);
	}
	
}
