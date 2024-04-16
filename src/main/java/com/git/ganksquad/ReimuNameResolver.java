package com.git.ganksquad;

import java.util.Arrays;
import java.util.List;

import com.git.ganksquad.data.types.ReimuType;

public class ReimuNameResolver {
	
	public static String getFunctionName(String name, List<ReimuType> params) {
		
		StringBuilder sb = new StringBuilder(name).append('<');
		
		for(ReimuType t : params) {
			sb.append(t.getLookupName()).append(';');
		}

		sb.append(">");
		
		return sb.toString();
	}

	
	public static String getFormatedName(Object a, Object... param) {
		return String.format("%s%s", 
				a.getClass().getSimpleName(),
				Arrays.toString(param));
}
}
