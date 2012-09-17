package org.zfun.seven;

import java.util.ArrayList;
import java.util.List;

public class Coin {

	@SuppressWarnings("unused")
	public void diamond() {
		// diamond declaration inference
		List<String> strs = new ArrayList<>();
		strs.add(new String("one"));
		// strs.add(1l); // this fails as long cannot be added to List<String>
		
		// X is Integer, T is inferred as String
		MyClass<Integer> intStr_v6 = new MyClass<Integer>("");
		
		// X is inferred as Integer, T is inferred as String
		MyClass<Integer> intStr_v7 = new MyClass<>("");
	}

	public int DayToInt(String day) {
		int i = 0;
		String lowerCaseDay = day.toLowerCase();
		switch (lowerCaseDay) {
		case "monday":
			i = 1;
			break;
		case "tuesday":
			i = 1;
			break;
		case "wednesday":
			i = 1;
			break;
		case "thursday":
			i = 1;
			break;
		case "friday":
			i = 1;
			break;
		case "saturday":
			i = 1;
			break;
		default:
			i = 0;
		}
		return i;
	}
	
	private class MyClass<X> {
		<T> MyClass(T t) {
			
		}
	}
}
