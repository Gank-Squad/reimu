package com.git.ganksquad.expressions;

import java.util.List;

import com.git.ganksquad.ParseChecks;
import com.git.ganksquad.ReimuRuntime;
import com.git.ganksquad.ReimuTypeResolver;
import com.git.ganksquad.data.BooleanEvaluable;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.impl.NoneData;
import com.git.ganksquad.data.types.SpecialType;
import com.git.ganksquad.data.types.ReimuType;
import com.git.ganksquad.exceptions.compiler.ReimuCompileException;
import com.git.ganksquad.exceptions.compiler.TypeException;
import com.git.ganksquad.exceptions.runtime.ReimuRuntimeException;

public class IfExpression implements Expression {
	
	private static final byte UNKNWON_EVAL = 0;
	private static final byte TRUE_EVAL = 1;
	private static final byte FALSE_EVAL = 2;

	private byte wasTrue = UNKNWON_EVAL;

	public Expression condition;
	public BlockExpression body;
	
	public IfExpression(Expression cond, BlockExpression body) {
		
		this.condition = cond;
		this.body = body;
	}
	
	@Override
	public ReimuType typeCheck(ReimuTypeResolver resolver) throws ReimuCompileException  {
		
		ReimuType t = this.condition.typeCheck(resolver);
		
		if(t == SpecialType.VOID) {
			
			throw new TypeException("If statement cannot work with VOID condition");
		}
		
		return this.body.typeCheck(resolver);
	}
	
	public static IfExpression from(Expression cond, BlockExpression body) {
		
		ParseChecks.RequiredNotNull(cond, body);

		return new IfExpression(cond, body);
	}

	public static IfExpression from(Expression cond, List<Expression> body) {
		
		ParseChecks.RequiredNotNull(cond, body);

		return new IfExpression(cond, BlockExpression.fromList(body));
	}
	
	/**
	 * Returns a boolean indicating if this if statement was true or not
	 * @return the result of the expression
	 * @throws ReimuRuntimeException If the if statement has not been evaluated yet
	 */
	public boolean conditionWasTrue() throws ReimuRuntimeException {
		
		if(this.wasTrue == UNKNWON_EVAL) {

			throw new ReimuRuntimeException("Cannot check if statement was true when it has not been run yet");
		}
		
		return this.wasTrue == TRUE_EVAL;
	}

	@Override
	public Data eval(ReimuRuntime reimuRuntime) throws ReimuRuntimeException {
		
		Data c = this.condition.eval(reimuRuntime);
		
		if(BooleanEvaluable.throwOrEval(c)) {
			
			this.wasTrue = TRUE_EVAL;
			
			return body.eval(reimuRuntime);
		}

		this.wasTrue = FALSE_EVAL;

		return NoneData.instance;
	}

	@Override
	public String toString() {
		return this.formatToString(this.condition, this.body);
	}
}
