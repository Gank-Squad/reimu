package com.git.ganksquad.expressions;

import com.git.ganksquad.ReimuRuntime;
import com.git.ganksquad.data.Data;

public class StringLiteral implements Expression {

	public String value = "";
	
	public StringLiteral() {}

	public StringLiteral(String value) {

		this.value = value;
	}
	
	public static StringLiteral fromString(String str) {
		
		return new StringLiteral(str);
	}

	public static StringLiteral fromQuotedString(String str) {
		
		return new StringLiteral(str.substring(1, str.length() - 1));
	}

	@Override
	public Data eval(ReimuRuntime runtime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		return String.format("%s(%s)", 
				this.getClass().getSimpleName(),
				this.value);
	}
}
