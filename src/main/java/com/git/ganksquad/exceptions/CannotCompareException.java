package com.git.ganksquad.exceptions;

import com.git.ganksquad.data.Data;

public class CannotCompareException extends ReimuRuntimeException {

	private static final long serialVersionUID = 7025433477012104345L;

	public CannotCompareException() { }

	public CannotCompareException(Data left, Data right, String message) {

		super(message + left.getClass().getSimpleName() + " and " + right.getClass().getSimpleName());
	}

	public CannotCompareException(Data left, Data right) {

		this(left, right, "Cannot compare: ");
	}
}
