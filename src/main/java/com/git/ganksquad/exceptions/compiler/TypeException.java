package com.git.ganksquad.exceptions.compiler;

import com.git.ganksquad.data.types.ReimuType;

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
}
