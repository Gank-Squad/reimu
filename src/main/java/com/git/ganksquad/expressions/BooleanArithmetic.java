package com.git.ganksquad.expressions;

import com.git.ganksquad.Operator;
import com.git.ganksquad.ParseChecks;
import com.git.ganksquad.ReimuRuntime;
import com.git.ganksquad.ReimuTypeResolver;
import com.git.ganksquad.data.BooleanEvaluable;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.impl.BooleanData;
import com.git.ganksquad.data.types.PrimitiveType;
import com.git.ganksquad.data.types.ReimuType;
import com.git.ganksquad.exceptions.compiler.ReimuCompileException;
import com.git.ganksquad.exceptions.compiler.TypeException;
import com.git.ganksquad.exceptions.runtime.ReimuRuntimeException;

public class BooleanArithmetic implements Expression {
	
	public byte op;
	public Expression left;
	public Expression right;
	
	public BooleanArithmetic(byte op, Expression l, Expression r) {
		this.left = l;
		this.right = r;
		this.op = op;
	}

	public static BooleanArithmetic or(Expression l, Expression r) {
		
		ParseChecks.RequiredNotNull(l, r);

		return new BooleanArithmetic(Operator.BOOL_OR, l, r);
	}

	public static BooleanArithmetic and(Expression l, Expression r) {
		
		ParseChecks.RequiredNotNull(l, r);

		return new BooleanArithmetic(Operator.BOOL_AND, l, r);
	}
	public static BooleanArithmetic not(Expression l) {
		
		ParseChecks.RequiredNotNull(l);

		return new BooleanArithmetic(Operator.BOOL_NOT, l, NoneExpression.get());
	}

	@Override
	public ReimuType typeCheck(ReimuTypeResolver resolver) throws ReimuCompileException {
		
		ReimuType l = this.left.typeCheck(resolver);
		ReimuType r = this.right.typeCheck(resolver);
		
		if(!l.canBeBool() || !r.canBeBool()) {
			
			throw new TypeException("%s and %s cannot be used as a bool", l, r);
		}
		
		return PrimitiveType.BOOLEAN;
	}

	@Override
	public Data eval(ReimuRuntime reimuRuntime) throws ReimuRuntimeException {
		
		Data l = this.left.eval(reimuRuntime);
		
		if(!(l instanceof BooleanEvaluable)) {
			
			throw new ReimuRuntimeException("Cannot use non-boolean evaluable data in bool operation");
		}

		BooleanEvaluable le = (BooleanEvaluable)l;
		BooleanEvaluable re;
		
		switch (this.op) {

		case Operator.BOOL_AND:
			
			if(le.evalAsBool()) {
				
				Data r = this.right.eval(reimuRuntime);

				if(!(r instanceof BooleanEvaluable)) {

					throw new ReimuRuntimeException("Cannot use non-boolean evaluable data in bool operation");
				}
				
				re = (BooleanEvaluable)r;

				return new BooleanData(re.evalAsBool());
			}
		
			return new BooleanData(false);
			
			
		case Operator.BOOL_NOT:
			
			return new BooleanData(!le.evalAsBool());
			
		case Operator.BOOL_OR:
			
			if(!le.evalAsBool()) {
				
				Data r = this.right.eval(reimuRuntime);

				if(!(r instanceof BooleanEvaluable)) {

					throw new ReimuRuntimeException("Cannot use non-boolean evaluable data in bool operation");
				}
				
				re = (BooleanEvaluable)r;

				return new BooleanData(re.evalAsBool());
			}
		
			return new BooleanData(true);

		default:
			throw new ReimuRuntimeException(String.format("Bad operator %s", Byte.toString(op)));
		}
	}
	
	@Override
	public String toString() {
		return this.formatToString(this.left, this.op, this.right);
	}

}
