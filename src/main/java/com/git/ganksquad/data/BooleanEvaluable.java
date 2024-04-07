package com.git.ganksquad.data;

import com.git.ganksquad.exceptions.InvalidBooleanException;

public interface BooleanEvaluable {

	/**
	 * Evaluate the data as a boolean
	 * @return
	 */
	public boolean evalAsBool();
	
	
	public static boolean throwOrEval(Data e) throws InvalidBooleanException {

		if(e instanceof BooleanEvaluable) {
			
			return ((BooleanEvaluable)e).evalAsBool();
		}

		throw new InvalidBooleanException(e);
	}
}
