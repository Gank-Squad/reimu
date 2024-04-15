package com.git.ganksquad.exceptions.compiler;

import com.git.ganksquad.data.types.FunctionType;
import com.git.ganksquad.data.types.ReimuType;
import com.git.ganksquad.expressions.InvokeFunctionExpression;

public class TypeException extends ReimuCompileException {

	private static final long serialVersionUID = -3831994548942978930L;

	public TypeException() {
		super();
	}
	public TypeException(String message) {
		super(message);
	}

	public TypeException(String message, ReimuType t1) {
		super(String.format(message, t1.toString()));
	}

	public TypeException(String message, ReimuType t1,  ReimuType t2) {
		super(String.format(message, t1.toString(), t2.toString()));
	}
	public TypeException(String string, String name, ReimuType t) {
		super(String.format(string, name, t.toString()));
	}
	public TypeException(String string, String name, ReimuType type, ReimuType t) {
		super(String.format(string, name, type.toString(), t.toString()));
	}
	public TypeException(String string, FunctionType func, InvokeFunctionExpression invokeFunctionExpression) {
		super(String.format(string, func, invokeFunctionExpression.toString()));
	}
}
