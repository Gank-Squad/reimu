package com.git.ganksquad.expressions;

import com.git.ganksquad.ParseChecks;
import com.git.ganksquad.ReimuRuntime;
import com.git.ganksquad.data.BooleanEvaluable;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.impl.NoneData;
import com.git.ganksquad.exceptions.ReimuRuntimeException;

public class ForLoopExpression implements Expression {
	
	public Expression assignment;
	public Expression condition;
	public Expression update;
	public BlockExpression body;
	
	public ForLoopExpression(Expression as, Expression con, Expression up, BlockExpression body) {
		
		this.assignment = as;
		this.condition= con;
		this.update = up;
		this.body = body;
	}
	
	public static ForLoopExpression from(Expression as, Expression con, Expression up, BlockExpression body) {
		
		ParseChecks.RequiredNotNull(as, con, up, body);

		return new ForLoopExpression(as, con, up, body);
	}

	@Override
	public Data eval(ReimuRuntime reimuRuntime) throws ReimuRuntimeException {
		
		ReimuRuntime scope = reimuRuntime.subScope();
		
		this.assignment.eval(scope);

		Data c = this.condition.eval(scope);
		
		if(!BooleanEvaluable.throwOrEval(c)) {
			
			return NoneData.instance;
		}
		
		while (true) {
			
			this.body.evalPartial(scope);

			this.update.eval(scope);
			
			c = condition.eval(scope);

			if(!BooleanEvaluable.throwOrEval(c)) {

				return NoneData.instance;
			}
		}
	}

}
