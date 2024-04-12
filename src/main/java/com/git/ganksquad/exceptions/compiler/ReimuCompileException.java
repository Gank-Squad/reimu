package com.git.ganksquad.exceptions.compiler;

import com.git.ganksquad.exceptions.ReimuException;

public class ReimuCompileException extends ReimuException {

	private static final long serialVersionUID = 284645946002476799L;
	
	public ReimuCompileException() {
		super();
	}

	public ReimuCompileException(Exception e) {
		super(e);
	}

	public ReimuCompileException(String message) {
		super(message);
	}

}
