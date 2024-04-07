package com.git.ganksquad.expressions;

import com.git.ganksquad.ReimuRuntime;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.NoneData;
import com.git.ganksquad.exceptions.ReimuRuntimeException;

public class NoneExpression implements Expression {

	public static NoneExpression get() {
		return new NoneExpression();
	}

	@Override
	public Data eval(ReimuRuntime runtime) throws ReimuRuntimeException {
		return  NoneData.instance;
	}
	
	@Override
	public String toString() {
		return "NoneExpression()";
	}
}
