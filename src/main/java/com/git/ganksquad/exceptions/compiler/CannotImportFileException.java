package com.git.ganksquad.exceptions.compiler;

public class CannotImportFileException extends ReimuCompileException {


	private static final long serialVersionUID = -6097307986107773403L;

	public CannotImportFileException() {
		super();
	}

	public CannotImportFileException(Exception e) {
		super(e);
	}

	public CannotImportFileException(String functionName) {
		super(String.format("Can not import file %s", functionName));
	}
	public CannotImportFileException(String functionName, String reason) {
		super(String.format("Can not import file %s because %s", functionName, reason));
	}
	public CannotImportFileException(String functionName, Exception reason) {
		super(String.format("Can not import file %s because %s", functionName, reason.toString()));
	}
}
