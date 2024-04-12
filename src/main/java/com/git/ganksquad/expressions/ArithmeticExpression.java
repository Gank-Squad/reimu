package com.git.ganksquad.expressions;

import com.git.ganksquad.Operator;
import com.git.ganksquad.ParseChecks;
import com.git.ganksquad.ReimuRuntime;
import com.git.ganksquad.ReimuTypeResolver;
import com.git.ganksquad.data.ArithmeticData;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.types.SpecialType;
import com.git.ganksquad.data.types.ReimuType;
import com.git.ganksquad.exceptions.compiler.ReimuCompileException;
import com.git.ganksquad.exceptions.compiler.TypeException;
import com.git.ganksquad.exceptions.runtime.ReimuRuntimeException;

public class ArithmeticExpression implements Expression {
	
	public Expression left;
	public Expression right;
	public byte operator;
	
	public ArithmeticExpression(byte op, Expression left, Expression right) {
		
		this.left = left;
		this.right = right;
		this.operator= op;
	}

	public static ArithmeticExpression add(Expression left, Expression right) {
		
		ParseChecks.RequiredNotNull(left, right);

		return new ArithmeticExpression(Operator.ADD, left, right);
	}

	public static ArithmeticExpression sub(Expression left, Expression right) {
		
		ParseChecks.RequiredNotNull(left, right);

		return new ArithmeticExpression(Operator.SUB, left, right);
	}

	public static ArithmeticExpression mul(Expression left, Expression right) {
		
		ParseChecks.RequiredNotNull(left, right);
		
		return new ArithmeticExpression(Operator.MUL, left, right);
	}

	public static ArithmeticExpression div(Expression left, Expression right) {
		
		ParseChecks.RequiredNotNull(left, right);
		
		return new ArithmeticExpression(Operator.DIV, left, right);
	}

	public static ArithmeticExpression mod(Expression left, Expression right) {
		
		ParseChecks.RequiredNotNull(left, right);
		
		return new ArithmeticExpression(Operator.MOD, left, right);
	}

	public static ArithmeticExpression xor(Expression left, Expression right) {
		
		ParseChecks.RequiredNotNull(left, right);
		
		return new ArithmeticExpression(Operator.XOR, left, right);
	}

	public static ArithmeticExpression or(Expression left, Expression right) {
		
		ParseChecks.RequiredNotNull(left, right);
		
		return new ArithmeticExpression(Operator.OR, left, right);
	}

	public static ArithmeticExpression and(Expression left, Expression right) {
		
		ParseChecks.RequiredNotNull(left, right);
		
		return new ArithmeticExpression(Operator.AND, left, right);
	}
	
	
	@Override
	public ReimuType typeCheck(ReimuTypeResolver resolver) throws ReimuCompileException {
		
		ReimuType l = this.left.typeCheck(resolver);
		ReimuType r = this.right.typeCheck(resolver);
		
		if(l == SpecialType.VOID || r == SpecialType.VOID) {

			throw new TypeException("Cannot do arithmetic with: %s and %s", l, r);
		}
		
		if(l.isEqualType(r)) {
			return l;
		}
		
		if(l.isAssignableFrom(r)) {
			return l;
		}

		if(r.isAssignableFrom(l)) {
			return r;
		}
		
		throw new TypeException("Cannot do arithmetic with: %s and %s because they are not the same type and not assignable", l, r);
	}

	@Override
	public Data eval(ReimuRuntime runtime) throws ReimuRuntimeException {
		
		Data lData = left.eval(runtime);
		Data rData = right.eval(runtime);
		
		switch (this.operator) {

		case Operator.ADD:
			return ArithmeticData.add(lData, rData);

		case Operator.SUB:
			return ArithmeticData.sub(lData, rData);

		case Operator.DIV:
			return ArithmeticData.div(lData, rData);

		case Operator.MUL:
			return ArithmeticData.mul(lData, rData);

		case Operator.MOD:
			return ArithmeticData.mod(lData, rData);

		case Operator.XOR:
			return ArithmeticData.xor(lData, rData);

		case Operator.OR:
			return ArithmeticData.or(lData, rData);

		case Operator.AND:
			return ArithmeticData.and(lData, rData);

		default:
			throw new RuntimeException(String.format("Invalid operator given to %s", this.getClass().getSimpleName()));
		}
	}

	@Override
	public String toString() {
		return this.formatToString(
				Operator.opString(this.operator),
				this.left,
				this.right);
	}

}
