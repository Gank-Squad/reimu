package com.git.ganksquad.expressions;

import com.git.ganksquad.ReimuRuntime;
import com.git.ganksquad.data.BooleanEvaluatable;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.impl.NoneData;
import com.git.ganksquad.exceptions.ReimuRuntimeException;

public class WhileExpression implements Expression {
	
	public Expression condExpression;
	public BlockExpression bodyExpression;
	
	public WhileExpression(Expression cond, BlockExpression body) {
		
		this.condExpression = cond;
		this.bodyExpression = body;
	}
	
	public static WhileExpression from(Expression cond, BlockExpression body) {
		
		return new WhileExpression(cond, body);
	}

	@Override
	public Data eval(ReimuRuntime reimuRuntime) throws ReimuRuntimeException {
		
		ReimuRuntime scope = reimuRuntime.subScope();
		
		Data r = NoneData.instance;

		while(true) {

			Data c = this.condExpression.eval(scope);

			if(BooleanEvaluatable.throwOrEval(c)) {

				r = this.bodyExpression.evalPartial(scope);
				
				continue;
			}

			break;
		}
		
		return r;
	}

	@Override
	public String toString() {
		return this.formatToString(this.condExpression, this.bodyExpression);
	}
}
