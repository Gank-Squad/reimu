package com.git.ganksquad.exceptions;

public class InvalidIterStateException extends ReimuRuntimeException {

	private static final long serialVersionUID = 3593172822821587459L;

	public InvalidIterStateException() {
	}

	public InvalidIterStateException(String message) {
		super(message);
	}
	
	public static InvalidIterStateException expectedWas(Class<?> expected, Class<?> was) {
		
		return new InvalidIterStateException(
				String.format("was expecting %s but got %s",
						expected.getSimpleName(),
						was.getSimpleName()));
	}
}