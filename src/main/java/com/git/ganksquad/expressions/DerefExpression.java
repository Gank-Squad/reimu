package com.git.ganksquad.expressions;

import com.git.ganksquad.ReimuRuntime;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.exceptions.ReimuRuntimeException;

public class DerefExpression implements Expression {
	
	public String symbol;
	
	public DerefExpression(String name) {

		this.symbol = name;
	}
	
	public static DerefExpression fromString(String name) {
		
		return new DerefExpression(name);
	}

	@Override
	public Data eval(ReimuRuntime reimuRuntime) throws ReimuRuntimeException {
		
		Data r = reimuRuntime.deref(this.symbol);
		
		if(r == null) {
			throw new ReimuRuntimeException("deref was null???");
		}
		
		return r;
	}

	@Override
	public String toString() {
		return this.formatToString(this.symbol);
	}
}