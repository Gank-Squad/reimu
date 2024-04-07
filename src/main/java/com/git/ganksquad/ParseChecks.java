package com.git.ganksquad;

public class ParseChecks {

	public static void RequiredNotNull(Object... items) throws NullPointerException {
		
		if(items == null || items.length == 0) return;

		for(Object o : items) {
			
			if(o == null) {
				
				new NullPointerException();
			}
		}
	}
}
