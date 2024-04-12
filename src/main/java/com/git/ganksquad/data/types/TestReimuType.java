package com.git.ganksquad.data.types;

import com.git.ganksquad.expressions.Expression.ReimuType;

public interface TestReimuType {

	/**
	 * 
	 * @return true if the type could be treated as a boolean, otherwise false
	 */
	public boolean isBooleanEvaluable();

	
	public static TestReimuType NUMERIC = PrimitiveType.INT;
}
