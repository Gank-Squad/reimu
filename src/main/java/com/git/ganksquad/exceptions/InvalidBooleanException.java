package com.git.ganksquad.exceptions;

import com.git.ganksquad.data.Data;

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
