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
import com.git.ganksquad.exceptions.runtime.ReimuRuntimeException;

public class ForLoopExpression implements Expression {
	
	public BlockExpression assignment;
	public Expression condition;
	public BlockExpression update;
	public BlockExpression body;
	
	public ForLoopExpression(BlockExpression as, Expression con, BlockExpression up, BlockExpression body) {
		
		this.assignment = as;
		this.condition= con;
		this.update = up;
		this.body = body;
	}
	
	public static ForLoopExpression from(List<Expression> as, Expression con, List<Expression> up, BlockExpression body) {
		
		ParseChecks.RequiredNotNull(as, con, up, body);

		return new ForLoopExpression(
				BlockExpression.fromList(as),
				con,
				BlockExpression.fromList(up),
				body);
	}

	public static ForLoopExpression from(BlockExpression as, Expression con, BlockExpression up, BlockExpression body) {
		
		ParseChecks.RequiredNotNull(as, con, up, body);

		return new ForLoopExpression(as, con, up, body);
	}


	@Override
	public ReimuType typeCheck(ReimuTypeResolver resolver) throws ReimuCompileException {

		ReimuTypeResolver scope = resolver.subScope();

		this.assignment.typeCheckPartial(scope);
		this.update.typeCheckPartial(scope);
		
		ReimuType t = this.condition.typeCheck(scope);
		
		if(t == SpecialType.VOID) {
			
			throw new ReimuCompileException("Cannot have void expression in for loop");
		}

		return this.body.typeCheckPartial(scope);
	}

	@Override
	public Data eval(ReimuRuntime reimuRuntime) throws ReimuRuntimeException {
		
		this.trace();

		ReimuRuntime scope = reimuRuntime.subScope();
		
		this.assignment.evalPartial(scope);

		Data c = this.condition.eval(scope);
		
		if(!BooleanEvaluable.throwOrEval(c)) {
			
			return NoneData.instance;
		}
		
		while (true) {
			
			this.body.evalPartial(scope);

			if(scope.hasReturnValue()) {
				reimuRuntime.setReturnValue(scope.getReturnValue());
				return reimuRuntime.getReturnValue();
			}

			this.update.evalPartial(scope);
			
			c = condition.eval(scope);

			if(!BooleanEvaluable.throwOrEval(c)) {

				return NoneData.instance;
			}
		}
	}

	@Override
	public String toString() {
		return this.formatToString(this.assignment, this.condition, this.update, this.body);
	}
}
