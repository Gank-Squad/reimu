package com.git.ganksquad.expressions;

import java.util.ArrayList;
import java.util.List;

import com.git.ganksquad.ParseChecks;
import com.git.ganksquad.ReimuRuntime;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.impl.FunctionData;
import com.git.ganksquad.exceptions.ReimuRuntimeException;

public class InvokeFunctionExpression implements Expression {
	
	public String symbol;
	public List<Expression> args ;
	
	public InvokeFunctionExpression(String symbol, List<Expression> args) {
		
		this.symbol = symbol;
		this.args = args;
	}
	
	public static InvokeFunctionExpression from(String name, List<Expression> args) {
		
		ParseChecks.RequiredNotNull(name, args);

		return new InvokeFunctionExpression(name, args);
	}

	@Override
	public Data eval(ReimuRuntime reimuRuntime) throws ReimuRuntimeException {
		
		FunctionData data = reimuRuntime.derefFunction(this.symbol, this.args.size());
		
		if(data.scope == null) {
			
			throw new ReimuRuntimeException("Cannot invoke function declared with no scope");
		}
		
		List<Data> argData = new ArrayList<>(this.args.size());
		
		for(Expression e : this.args) {

			argData.add(e.eval(reimuRuntime));
		}
		
		// data.scope holds the runtime from when the function was declared
		// we create a subscope of that with our parameter bindings to evaluate it
		ReimuRuntime rt = data.scope.subScope(
				data.params.iterator(),
				argData.iterator());
		
		return data.body.eval(rt);
	}

}
