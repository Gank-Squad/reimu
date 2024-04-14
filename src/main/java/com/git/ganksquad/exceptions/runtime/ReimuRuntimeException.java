package com.git.ganksquad.exceptions.runtime;

import com.git.ganksquad.data.types.ReimuType;
import com.git.ganksquad.exceptions.ReimuException;

/**
 * The base runtime exception for all Reimu Exceptions
 */
public class ReimuRuntimeException extends ReimuException {

	private static final long serialVersionUID = -1621389320517765084L;

	public ReimuRuntimeException() {}

	public ReimuRuntimeException(Exception e) {
		
		super(e);
	}

	public ReimuRuntimeException(String message) {

		super(message);
	}
	public ReimuRuntimeException(String message, ReimuType a, ReimuType fmt) {

		super(String.format(message, a, fmt));
	}
}
