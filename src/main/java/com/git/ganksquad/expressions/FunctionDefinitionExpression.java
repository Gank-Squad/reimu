package com.git.ganksquad.expressions;

import java.util.List;

import com.git.ganksquad.ParseChecks;
import com.git.ganksquad.ReimuRuntime;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.impl.FunctionData;
import com.git.ganksquad.data.impl.NoneData;
import com.git.ganksquad.exceptions.ReimuRuntimeException;

public class FunctionDefinitionExpression implements Expression {
	
	public FunctionData func;
	
	public FunctionDefinitionExpression(FunctionData data) {
		
		this.func = data;
	}
	
	public static FunctionDefinitionExpression from(String name, List<String> args, BlockExpression body) {
		
		ParseChecks.RequiredNotNull(name, args, body);

		return new FunctionDefinitionExpression(new FunctionData(name, args, body));
	}

	@Override
	public Data eval(ReimuRuntime reimuRuntime) throws ReimuRuntimeException {
		
		this.func.scope = reimuRuntime;

		reimuRuntime.declareFunction(this.func);

		return NoneData.instance;
	}

	@Override
	public String toString() {
		return this.formatToString(func);
	}
}
