package com.git.ganksquad.expressions;

import com.git.ganksquad.ReimuRuntime;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.impl.IntegerData;

public class IntegerLiteral implements Expression {
	
	public int value = 0;
	
	public IntegerLiteral() {}

	public IntegerLiteral(int value) {

		this.value = value;
	}
	
	public static IntegerLiteral fromString(String str) {
		
		return new IntegerLiteral(Integer.parseInt(str));
	}

	public static IntegerLiteral zero() {
		
		return new IntegerLiteral(0);
	}
	
	@Override
	public Data eval(ReimuRuntime runtime) {

		return new IntegerData(this.value);
	}
	
	@Override
	public String toString() {
		return this.formatToString(this.value);
	}
}
