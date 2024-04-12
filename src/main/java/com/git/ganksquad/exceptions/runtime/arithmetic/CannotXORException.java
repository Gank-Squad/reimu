package com.git.ganksquad.exceptions.runtime.arithmetic;

import com.git.ganksquad.data.Data;

/**
 * Raised when there is an arithmetic error
 */
public class CannotXORException extends CannotArithmeticException {

	private static final long serialVersionUID = -3848293274433872152L;

	public CannotXORException() {}

	public CannotXORException(Data left, Data right) {

		super(left, right, "Cannot XOR: ");
	}
}
