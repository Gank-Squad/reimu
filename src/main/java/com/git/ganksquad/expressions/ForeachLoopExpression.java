package com.git.ganksquad.expressions;

import com.git.ganksquad.ParseChecks;
import com.git.ganksquad.ReimuRuntime;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.IterableData;
import com.git.ganksquad.data.impl.NoneData;
import com.git.ganksquad.exceptions.ReimuRuntimeException;

public class ForeachLoopExpression implements Expression {
	
	public String variable;
	public Expression iterable;
	public BlockExpression body;
	
	public ForeachLoopExpression(String variable, Expression it, BlockExpression body) {
		
		this.variable = variable;
		this.iterable = it;
		this.body = body;
	}
	
	public static ForeachLoopExpression from(String variable, Expression it, BlockExpression body) {
		
		ParseChecks.RequiredNotNull(variable, it, body);

		return new ForeachLoopExpression(variable, it, body);
	}

	@Override
	public Data eval(ReimuRuntime reimuRuntime) throws ReimuRuntimeException {
		
		Data n = this.iterable.eval(reimuRuntime);
		
		if(n == NoneData.instance) {
			
			return NoneData.instance;
		}
		
		if(!(n instanceof IterableData)) {
			
			throw new ReimuRuntimeException("Cannot iterate non-iterable data");
		}
		
		ReimuRuntime scope = reimuRuntime.subScope();
		
		scope.declare(this.variable);
		
		IterableData it = (IterableData)n;
		
		while (true) {
			
			n = it.next();
			
			if(n == NoneData.instance) {
				
				break;
			}
			
			scope.assign(variable, n);
			
			this.body.evalPartial(scope);
		}
		
		return NoneData.instance;
	}

}
