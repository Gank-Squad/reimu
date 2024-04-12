package com.git.ganksquad.exceptions.compiler;

public class NoneTypeException extends ReimuCompileException {

	private static final long serialVersionUID = 8090824724218498227L;

	public NoneTypeException() {}

	public NoneTypeException(Exception e) {
		
		super(e);
	}

	public NoneTypeException(String message) {

		super(message);
	}
	
	public static NoneTypeException fromVar(String var) {
		
		return new NoneTypeException(String.format("Variable %s has None type", var));
	}
}
