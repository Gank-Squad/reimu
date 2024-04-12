package com.git.ganksquad.exceptions.compiler;

public class RedeclarationException extends ReimuCompileException {

	private static final long serialVersionUID = 1966509911218594514L;

	public RedeclarationException() {}

	public RedeclarationException(String message) {
		super(message);
	}
	
	public static RedeclarationException fromVariableDeclaration(String symbol) {
		
		return new RedeclarationException(
				String.format("Symbol %s was already declared in this scope", symbol));
	}
}
