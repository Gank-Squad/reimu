package com.git.ganksquad.exceptions;

import com.git.ganksquad.data.Data;

public class CannotDivideException extends CannotArithmeticException {

	private static final long serialVersionUID = -3768190514501018626L;

	public CannotDivideException() {}

	public CannotDivideException(Data left, Data right) {

		super(left, right, "Cannot divide: ");
	}
}
