package com.git.ganksquad.expressions;

import com.git.ganksquad.ReimuRuntime;
import com.git.ganksquad.data.BooleanData;
import com.git.ganksquad.data.Data;

public class BooleanLiteral implements Expression {

	public boolean value = false;
	
	public BooleanLiteral() {}

	public BooleanLiteral(boolean value) {

		this.value = value;
	}
	
	public static BooleanLiteral fromString(String str) {
		
		return new BooleanLiteral(Boolean.parseBoolean(str));
	}

	@Override
	public Data eval(ReimuRuntime runtime) {
		
		return new BooleanData(this.value);
	}

	@Override
	public String toString() {
		return Boolean.toString(this.value);
	}
}
