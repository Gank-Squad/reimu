package com.git.ganksquad.exceptions.runtime;

import com.git.ganksquad.data.types.ReimuType;

public class CannotCastException extends ReimuRuntimeException {

	private static final long serialVersionUID = -7044022843939060405L;

	public  CannotCastException() {}

	public CannotCastException(String message) {
		super(message);
	}
	public CannotCastException(String message, ReimuType t1, ReimuType t2) {
		super(String.format(message, t1, t2));
	}
	
	public CannotCastException(ReimuType t1, ReimuType t2) {
		super(String.format("Cannot cast %s to %s", t1, t2));
	}
}
