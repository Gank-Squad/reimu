package com.git.ganksquad.expressions;

import com.git.ganksquad.ParseChecks;
import com.git.ganksquad.ReimuRuntime;
import com.git.ganksquad.ReimuTypeResolver;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.types.ReimuType;
import com.git.ganksquad.exceptions.compiler.ReimuCompileException;
import com.git.ganksquad.exceptions.runtime.ReimuRuntimeException;

public class CastExpression implements Expression {
	
	public ReimuType resolveTo;
	public Expression expr;

	public CastExpression(ReimuType to, Expression exp) {
		
		this.resolveTo = to;
		this.expr = exp;
	}
	
	public static CastExpression from(ReimuType t, Expression e) {
		
		ParseChecks.RequiredNotNull(t, e);

		return new CastExpression(t, e);
	}

	@Override
	public ReimuType typeCheck(ReimuTypeResolver resolver) throws ReimuCompileException {
		
		this.expr.typeCheck(resolver);

		return  this.resolveTo;
	}

	@Override
	public Data eval(ReimuRuntime reimuRuntime) throws ReimuRuntimeException {
//		return this.expr.eval(reimuRuntime).castTo(resolveTo);
		return this.expr.eval(reimuRuntime);
	}

}
