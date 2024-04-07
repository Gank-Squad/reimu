package com.git.ganksquad.exceptions;

/**
 * Raised when trying to assign or deref a symbol which 
 * does not exist in this or the parent scope.
 */
public class SymbolNotFoundException extends ReimuRuntimeException {

	private static final long serialVersionUID = -3592705562496563800L;

	public SymbolNotFoundException() {}

	public SymbolNotFoundException(String message) {
		super(message);
	}
}
