package com.git.ganksquad.exceptions;

/**
 * The base runtime exception for all Reimu Exceptions
 */
public class ReimuRuntimeException extends Exception {

	private static final long serialVersionUID = -1621389320517765084L;

	public ReimuRuntimeException() {}

	public ReimuRuntimeException(String message) {

		super(message);
	}
}
