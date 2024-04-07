package com.git.ganksquad.exceptions;

import com.git.ganksquad.data.Data;

public class CannotSubtractException extends CannotArithmeticException {

	private static final long serialVersionUID = 6396285975204413477L;

	public CannotSubtractException() {}

	public CannotSubtractException(Data left, Data right) {

		super(left, right, "Cannot subtract: ");
	}
}
