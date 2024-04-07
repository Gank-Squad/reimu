package com.git.ganksquad.data;

import com.git.ganksquad.exceptions.InvalidBooleanException;
import com.git.ganksquad.expressions.Expression;

public interface BooleanEvaluatable {

	/**
	 * Evaluate the data as a boolean
	 * @return
	 */
	public boolean evalAsBool();
	
	
	public static boolean throwOrEval(Data e) throws InvalidBooleanException {

		if(e instanceof BooleanEvaluatable) {
			
			return ((BooleanEvaluatable)e).evalAsBool();
		}

		throw new InvalidBooleanException(e);
	}
}
