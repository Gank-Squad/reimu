package com.git.ganksquad.exceptions;

import com.git.ganksquad.data.Data;

public class CannotArithmeticException extends ReimuRuntimeException {

	private static final long serialVersionUID = -7167552441003421791L;

	public CannotArithmeticException() { }

	public CannotArithmeticException(Data left, Data right, String message) {

		super(message + left.getClass().getSimpleName() + " and " + right.getClass().getSimpleName());
	}

	public CannotArithmeticException(Data left, Data right) {

		this(left, right, "Cannot perform arithmetic between: ");
	}
}
