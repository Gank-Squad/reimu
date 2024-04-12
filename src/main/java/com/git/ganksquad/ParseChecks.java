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

		for(Object o : items) {
			
			if(o == null) {
				
				throw new NullPointerException("Parsed a null item");
			}
			
			if(o instanceof List) {
				
				RequiredAllNotNull((List<?>)o);
			}
		}
	}

}
