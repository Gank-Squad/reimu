package com.git.ganksquad;

import java.util.List;

public class ParseChecks {


	public static void RequiredAllNotNull(List<?> items) throws NullPointerException {

		for(Object o : items) {

			if(o == null) {

				throw new NullPointerException("Parsed a list which contains null");
			}
		}
	}

	public static void RequiredNotNull(Object... items) throws NullPointerException {
		
		if(items == null || items.length == 0) return;
		
		int i = 0;

		for(Object o : items) {
			
			if(o == null) {
				
				throw new NullPointerException("Parsed a null item at " + i);
			}
			
			if(o instanceof List) {
				
				RequiredAllNotNull((List<?>)o);
			}

			i++;
		}
	}
	
	public static String getUnquotedOrFail(String  s) {
		
		if(s.length() < 2) {
			
			throw new IllegalArgumentException("String must be at least 2 chars to be quoted");
		}
		
		if(s.charAt(0) == '"' && s.charAt(s.length() - 1) == '"') {
			
			return s.substring(1, s.length() - 1);
		}

		throw new IllegalArgumentException("String is not quoted");
	}

}
