package com.git.ganksquad.exceptions.runtime.arithmetic;

import com.git.ganksquad.data.Data;

/**
 * Raised when there is an arithmetic error
 */
public class CannotORException extends CannotArithmeticException {

	private static final long serialVersionUID = -3848293274433872152L;

	public CannotORException() {}

	public CannotORException(Data left, Data right) {

		super(left, right, "Cannot OR: ");
	}
}
