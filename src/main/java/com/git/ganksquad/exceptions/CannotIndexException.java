package com.git.ganksquad.exceptions;

public class CannotIndexException extends ReimuRuntimeException {

	private static final long serialVersionUID = 6008203414624262136L;

	public  CannotIndexException() {}

	public CannotIndexException(String message) {
		super(message);
	}
	
	public static CannotIndexException typeNotIndexable(Class<?> t) {
		
		return new CannotIndexException(
				String.format("Value of type %s is not indexable", t.getSimpleName()));
	}

	public static CannotIndexException typeNotIndexableBy(Class<?> t, Class<?> t2) {
		
		return new CannotIndexException(
				String.format("Value of type %s is not indexable by type %s", 
						t.getSimpleName(),
						t2.getSimpleName()));
	}
}
