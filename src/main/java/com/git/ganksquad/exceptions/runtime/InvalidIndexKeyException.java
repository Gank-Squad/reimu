package com.git.ganksquad.exceptions.runtime;

public class InvalidIndexKeyException extends ReimuRuntimeException {


	private static final long serialVersionUID = -1717641616523545154L;

	public InvalidIndexKeyException() {
	}

	public InvalidIndexKeyException(String message) {
		super(message);
	}
	
	public static InvalidIndexKeyException invalidType(Class<?> t) {
		
		return new InvalidIndexKeyException(
				String.format("Cannot use type %s as a key",
						t.getSimpleName()
						));
	}
}
