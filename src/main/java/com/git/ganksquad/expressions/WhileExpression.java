package com.git.ganksquad.expressions;

import com.git.ganksquad.ParseChecks;
import com.git.ganksquad.ReimuRuntime;
import com.git.ganksquad.ReimuTypeResolver;
import com.git.ganksquad.data.BooleanEvaluable;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.impl.NoneData;
import com.git.ganksquad.exceptions.compiler.ReimuCompileException;
import com.git.ganksquad.exceptions.compiler.TypeException;
import com.git.ganksquad.exceptions.runtime.ReimuRuntimeException;

public class WhileExpression implements Expression {
	
	public Expression condExpression;
	public BlockExpression bodyExpression;
	
	public WhileExpression(Expression cond, BlockExpression body) {
		
		this.condExpression = cond;
		this.bodyExpression = body;
	}
	
	public static WhileExpression from(Expression cond, BlockExpression body) {
		
		ParseChecks.RequiredNotNull(cond, body);

		return new WhileExpression(cond, body);
	}
	
	@Override
	public ReimuType typeCheck(ReimuTypeResolver resolver) throws ReimuCompileException {

		ReimuType t = this.condExpression.typeCheck(resolver);	

		switch(t) {

		case NONE:
			throw new TypeException("While loop condition must not be None type");

		default:

			if(!t.evalAsBool()) 
				throw new TypeException("While loop condition must be BooleanEvaluable type");

			return this.bodyExpression.typeCheck(resolver);
		
		}
	}

	@Override
	public Data eval(ReimuRuntime reimuRuntime) throws ReimuRuntimeException {
		
		ReimuRuntime scope = reimuRuntime.subScope();
		
		Data r = NoneData.instance;

		while(true) {

			Data c = this.condExpression.eval(scope);

			if(BooleanEvaluable.throwOrEval(c)) {

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
