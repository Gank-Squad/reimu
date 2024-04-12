package com.git.ganksquad.exceptions.runtime;

public class NullAssignmentExpression extends ReimuRuntimeException {

	private static final long serialVersionUID = 5075016503598382087L;

	public NullAssignmentExpression() {}

	public NullAssignmentExpression(String message) {
		super(message);
	}
	
	public static NullAssignmentExpression fromVariableDeclaration(String symbol) {
		
		return new NullAssignmentExpression(
				String.format("Symbol %s was declared with a null value", symbol));
	}

	public static NullAssignmentExpression fromVariableAssignment(String symbol) {
		
		return new NullAssignmentExpression(
				String.format("Symbol %s was assigned a null value", symbol));
	}

	public static NullAssignmentExpression fromFuncDef() {
		
		return new NullAssignmentExpression("Tried to declare a function with a null value");
	}
}
