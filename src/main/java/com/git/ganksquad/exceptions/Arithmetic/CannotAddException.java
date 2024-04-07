package com.git.ganksquad.exceptions.Arithmetic;

import com.git.ganksquad.data.Data;

public class CannotAddException extends CannotArithmeticException {

	private static final long serialVersionUID = -3848293274433872152L;

	public CannotAddException() {}

	public CannotAddException(Data left, Data right) {

		super(left, right, "Cannot add: ");
	}
}
