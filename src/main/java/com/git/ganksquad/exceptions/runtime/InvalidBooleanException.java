package com.git.ganksquad.exceptions.runtime;

import com.git.ganksquad.data.Data;

/**
 * Raised when an expression cannot be reduced to a boolean.
 */
public class InvalidBooleanException extends ReimuRuntimeException {

	private static final long serialVersionUID = -5045662217944146416L;

	public InvalidBooleanException() { }

	public InvalidBooleanException(String message) { 
		super(message);
	}

	public InvalidBooleanException(Data condition) {
		super("Expression evaluated to " + condition.toString() + " which is not a boolean");
	}
}
