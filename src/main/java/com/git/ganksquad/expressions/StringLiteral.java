package com.git.ganksquad.expressions;

import com.git.ganksquad.ReimuRuntime;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.impl.StringData;

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

		return new StringData(this.value);
	}

	@Override
	public String toString() {
		return this.formatToString('"' + this.value + '"');
	}
}
