package com.git.ganksquad.exceptions.compiler;

public class NotAFunctionException extends ReimuCompileException {

	private static final long serialVersionUID = -3165825025326231433L;

	public NotAFunctionException() {
		super();
	}

	public NotAFunctionException(Exception e) {
		super(e);
	}

	public NotAFunctionException(String functionName) {
		super(String.format("%s is not a function type", functionName));
	}
}
