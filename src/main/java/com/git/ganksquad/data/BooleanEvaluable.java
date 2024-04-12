package com.git.ganksquad.data;

import com.git.ganksquad.exceptions.runtime.InvalidBooleanException;

/**
 * Represents a generic data which can be reduced to a boolean value.
 */
public interface BooleanEvaluable {

	/**
	 * Evaluate the data as a boolean
	 * @return
	 */
	public boolean evalAsBool();
	
	
	/**
	 * Evaluate the data as a boolean if it is {@link BooleanEvaluable}
	 * Otherwise throws {@link InvalidBooleanException}
	 * @param e The data to reduce
	 * @return True or False if the data is {@link InvalidBooleanException}
	 * @throws InvalidBooleanException
	 */
	public static boolean throwOrEval(Data e) throws InvalidBooleanException {

		if(e instanceof BooleanEvaluable) {
			
			return ((BooleanEvaluable)e).evalAsBool();
		}

		throw new InvalidBooleanException(e);
	}
}
