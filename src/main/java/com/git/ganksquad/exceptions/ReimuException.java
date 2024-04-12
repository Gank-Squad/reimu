package com.git.ganksquad.exceptions;

public class ReimuException extends Exception {

	private static final long serialVersionUID = 3691435317498593478L;

	public ReimuException() {}

	public ReimuException(Exception e) {
		
		super(e);
	}

	public ReimuException(String message) {

		super(message);
	}

	public static NullPointerException nullPtrFromVariableDeclaration(String symbol) {
		
		return new NullPointerException(
				String.format("Symbol %s was declared with a null value", symbol));
	}

	public static NullPointerException nullPtrFromVariableAssignment(String symbol) {
		
		return new NullPointerException(
				String.format("Symbol %s was assigned a null value", symbol));
	}

	public static NullPointerException nullPtrFromFuncDef() {
		
		return new NullPointerException("Tried to declare a function with a null value");
	}
}
