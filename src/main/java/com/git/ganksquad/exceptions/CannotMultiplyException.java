package com.git.ganksquad.exceptions;

import com.git.ganksquad.data.Data;

public class CannotMultiplyException extends CannotArithmeticException {

	private static final long serialVersionUID = 2549164935383434161L;

	public CannotMultiplyException() {}

	public CannotMultiplyException(Data left, Data right) {

		super(left, right, "Cannot multiply: ");
	}
}
