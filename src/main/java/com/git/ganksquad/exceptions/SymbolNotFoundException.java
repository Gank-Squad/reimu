package com.git.ganksquad.exceptions;

public class SymbolNotFoundException extends ReimuRuntimeException {

	private static final long serialVersionUID = -3592705562496563800L;

	public SymbolNotFoundException() {}

	public SymbolNotFoundException(String message) {
		super(message);
	}
}
